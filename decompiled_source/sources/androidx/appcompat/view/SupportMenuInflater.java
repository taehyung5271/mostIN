package androidx.appcompat.view;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.InflateException;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import androidx.appcompat.R;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuItemWrapperICS;
import androidx.appcompat.widget.DrawableUtils;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.ActionProvider;
import androidx.core.view.MenuItemCompat;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class SupportMenuInflater extends MenuInflater {
    static final String LOG_TAG = "SupportMenuInflater";
    static final int NO_ID = 0;
    private static final String XML_GROUP = "group";
    private static final String XML_ITEM = "item";
    private static final String XML_MENU = "menu";
    final Object[] mActionProviderConstructorArguments;
    final Object[] mActionViewConstructorArguments;
    Context mContext;
    private Object mRealOwner;
    static final Class<?>[] ACTION_VIEW_CONSTRUCTOR_SIGNATURE = {Context.class};
    static final Class<?>[] ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE = ACTION_VIEW_CONSTRUCTOR_SIGNATURE;

    public SupportMenuInflater(Context context) {
        super(context);
        this.mContext = context;
        this.mActionViewConstructorArguments = new Object[]{context};
        this.mActionProviderConstructorArguments = this.mActionViewConstructorArguments;
    }

    @Override // android.view.MenuInflater
    public void inflate(int menuRes, Menu menu) {
        if (!(menu instanceof SupportMenu)) {
            super.inflate(menuRes, menu);
            return;
        }
        XmlResourceParser parser = null;
        boolean wasDispatchingItemsChanged = false;
        try {
            try {
                parser = this.mContext.getResources().getLayout(menuRes);
                AttributeSet attrs = Xml.asAttributeSet(parser);
                if (menu instanceof MenuBuilder) {
                    MenuBuilder menuBuilder = (MenuBuilder) menu;
                    if (menuBuilder.isDispatchingItemsChanged()) {
                        menuBuilder.stopDispatchingItemsChanged();
                        wasDispatchingItemsChanged = true;
                    }
                }
                parseMenu(parser, attrs, menu);
            } catch (IOException e) {
                throw new InflateException("Error inflating menu XML", e);
            } catch (XmlPullParserException e2) {
                throw new InflateException("Error inflating menu XML", e2);
            }
        } finally {
            if (wasDispatchingItemsChanged) {
                ((MenuBuilder) menu).startDispatchingItemsChanged();
            }
            if (parser != null) {
                parser.close();
            }
        }
    }

    private void parseMenu(XmlPullParser parser, AttributeSet attrs, Menu menu) throws XmlPullParserException, IllegalAccessException, IOException, IllegalArgumentException, InvocationTargetException {
        MenuState menuState = new MenuState(menu);
        int eventType = parser.getEventType();
        boolean lookingForEndOfUnknownTag = false;
        String unknownTagName = null;
        while (true) {
            if (eventType == 2) {
                String tagName = parser.getName();
                if (tagName.equals(XML_MENU)) {
                    eventType = parser.next();
                } else {
                    throw new RuntimeException("Expecting menu, got " + tagName);
                }
            } else {
                eventType = parser.next();
                if (eventType == 1) {
                    break;
                }
            }
        }
        boolean reachedEndOfMenu = false;
        while (!reachedEndOfMenu) {
            switch (eventType) {
                case 1:
                    throw new RuntimeException("Unexpected end of document");
                case 2:
                    if (!lookingForEndOfUnknownTag) {
                        String tagName2 = parser.getName();
                        if (tagName2.equals(XML_GROUP)) {
                            menuState.readGroup(attrs);
                            break;
                        } else if (tagName2.equals(XML_ITEM)) {
                            menuState.readItem(attrs);
                            break;
                        } else if (tagName2.equals(XML_MENU)) {
                            SubMenu subMenu = menuState.addSubMenuItem();
                            parseMenu(parser, attrs, subMenu);
                            break;
                        } else {
                            lookingForEndOfUnknownTag = true;
                            unknownTagName = tagName2;
                            break;
                        }
                    } else {
                        break;
                    }
                case 3:
                    String tagName3 = parser.getName();
                    if (lookingForEndOfUnknownTag && tagName3.equals(unknownTagName)) {
                        lookingForEndOfUnknownTag = false;
                        unknownTagName = null;
                        break;
                    } else if (tagName3.equals(XML_GROUP)) {
                        menuState.resetGroup();
                        break;
                    } else if (tagName3.equals(XML_ITEM)) {
                        if (!menuState.hasAddedItem()) {
                            if (menuState.itemActionProvider != null && menuState.itemActionProvider.hasSubMenu()) {
                                menuState.addSubMenuItem();
                                break;
                            } else {
                                menuState.addItem();
                                break;
                            }
                        } else {
                            break;
                        }
                    } else if (!tagName3.equals(XML_MENU)) {
                        break;
                    } else {
                        reachedEndOfMenu = true;
                        break;
                    }
                    break;
            }
            eventType = parser.next();
        }
    }

    Object getRealOwner() {
        if (this.mRealOwner == null) {
            this.mRealOwner = findRealOwner(this.mContext);
        }
        return this.mRealOwner;
    }

    private Object findRealOwner(Object owner) {
        if (!(owner instanceof Activity) && (owner instanceof ContextWrapper)) {
            return findRealOwner(((ContextWrapper) owner).getBaseContext());
        }
        return owner;
    }

    private static class InflatedOnMenuItemClickListener implements MenuItem.OnMenuItemClickListener {
        private static final Class<?>[] PARAM_TYPES = {MenuItem.class};
        private Method mMethod;
        private Object mRealOwner;

        public InflatedOnMenuItemClickListener(Object realOwner, String methodName) {
            this.mRealOwner = realOwner;
            Class<?> c = realOwner.getClass();
            try {
                this.mMethod = c.getMethod(methodName, PARAM_TYPES);
            } catch (Exception e) {
                InflateException ex = new InflateException("Couldn't resolve menu item onClick handler " + methodName + " in class " + c.getName());
                ex.initCause(e);
                throw ex;
            }
        }

        @Override // android.view.MenuItem.OnMenuItemClickListener
        public boolean onMenuItemClick(MenuItem item) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            try {
                if (this.mMethod.getReturnType() == Boolean.TYPE) {
                    return ((Boolean) this.mMethod.invoke(this.mRealOwner, item)).booleanValue();
                }
                this.mMethod.invoke(this.mRealOwner, item);
                return true;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private class MenuState {
        private static final int defaultGroupId = 0;
        private static final int defaultItemCategory = 0;
        private static final int defaultItemCheckable = 0;
        private static final boolean defaultItemChecked = false;
        private static final boolean defaultItemEnabled = true;
        private static final int defaultItemId = 0;
        private static final int defaultItemOrder = 0;
        private static final boolean defaultItemVisible = true;
        private int groupCategory;
        private int groupCheckable;
        private boolean groupEnabled;
        private int groupId;
        private int groupOrder;
        private boolean groupVisible;
        ActionProvider itemActionProvider;
        private String itemActionProviderClassName;
        private String itemActionViewClassName;
        private int itemActionViewLayout;
        private boolean itemAdded;
        private int itemAlphabeticModifiers;
        private char itemAlphabeticShortcut;
        private int itemCategoryOrder;
        private int itemCheckable;
        private boolean itemChecked;
        private CharSequence itemContentDescription;
        private boolean itemEnabled;
        private int itemIconResId;
        private ColorStateList itemIconTintList = null;
        private PorterDuff.Mode itemIconTintMode = null;
        private int itemId;
        private String itemListenerMethodName;
        private int itemNumericModifiers;
        private char itemNumericShortcut;
        private int itemShowAsAction;
        private CharSequence itemTitle;
        private CharSequence itemTitleCondensed;
        private CharSequence itemTooltipText;
        private boolean itemVisible;
        private Menu menu;

        public MenuState(Menu menu) {
            this.menu = menu;
            resetGroup();
        }

        public void resetGroup() {
            this.groupId = 0;
            this.groupCategory = 0;
            this.groupOrder = 0;
            this.groupCheckable = 0;
            this.groupVisible = true;
            this.groupEnabled = true;
        }

        public void readGroup(AttributeSet attrs) {
            TypedArray a = SupportMenuInflater.this.mContext.obtainStyledAttributes(attrs, R.styleable.MenuGroup);
            this.groupId = a.getResourceId(R.styleable.MenuGroup_android_id, 0);
            this.groupCategory = a.getInt(R.styleable.MenuGroup_android_menuCategory, 0);
            this.groupOrder = a.getInt(R.styleable.MenuGroup_android_orderInCategory, 0);
            this.groupCheckable = a.getInt(R.styleable.MenuGroup_android_checkableBehavior, 0);
            this.groupVisible = a.getBoolean(R.styleable.MenuGroup_android_visible, true);
            this.groupEnabled = a.getBoolean(R.styleable.MenuGroup_android_enabled, true);
            a.recycle();
        }

        public void readItem(AttributeSet attributeSet) {
            TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(SupportMenuInflater.this.mContext, attributeSet, R.styleable.MenuItem);
            this.itemId = tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.MenuItem_android_id, 0);
            this.itemCategoryOrder = ((-65536) & tintTypedArrayObtainStyledAttributes.getInt(R.styleable.MenuItem_android_menuCategory, this.groupCategory)) | (65535 & tintTypedArrayObtainStyledAttributes.getInt(R.styleable.MenuItem_android_orderInCategory, this.groupOrder));
            this.itemTitle = tintTypedArrayObtainStyledAttributes.getText(R.styleable.MenuItem_android_title);
            this.itemTitleCondensed = tintTypedArrayObtainStyledAttributes.getText(R.styleable.MenuItem_android_titleCondensed);
            this.itemIconResId = tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.MenuItem_android_icon, 0);
            this.itemAlphabeticShortcut = getShortcut(tintTypedArrayObtainStyledAttributes.getString(R.styleable.MenuItem_android_alphabeticShortcut));
            this.itemAlphabeticModifiers = tintTypedArrayObtainStyledAttributes.getInt(R.styleable.MenuItem_alphabeticModifiers, 4096);
            this.itemNumericShortcut = getShortcut(tintTypedArrayObtainStyledAttributes.getString(R.styleable.MenuItem_android_numericShortcut));
            this.itemNumericModifiers = tintTypedArrayObtainStyledAttributes.getInt(R.styleable.MenuItem_numericModifiers, 4096);
            if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.MenuItem_android_checkable)) {
                this.itemCheckable = tintTypedArrayObtainStyledAttributes.getBoolean(R.styleable.MenuItem_android_checkable, false) ? 1 : 0;
            } else {
                this.itemCheckable = this.groupCheckable;
            }
            this.itemChecked = tintTypedArrayObtainStyledAttributes.getBoolean(R.styleable.MenuItem_android_checked, false);
            this.itemVisible = tintTypedArrayObtainStyledAttributes.getBoolean(R.styleable.MenuItem_android_visible, this.groupVisible);
            this.itemEnabled = tintTypedArrayObtainStyledAttributes.getBoolean(R.styleable.MenuItem_android_enabled, this.groupEnabled);
            this.itemShowAsAction = tintTypedArrayObtainStyledAttributes.getInt(R.styleable.MenuItem_showAsAction, -1);
            this.itemListenerMethodName = tintTypedArrayObtainStyledAttributes.getString(R.styleable.MenuItem_android_onClick);
            this.itemActionViewLayout = tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.MenuItem_actionLayout, 0);
            this.itemActionViewClassName = tintTypedArrayObtainStyledAttributes.getString(R.styleable.MenuItem_actionViewClass);
            this.itemActionProviderClassName = tintTypedArrayObtainStyledAttributes.getString(R.styleable.MenuItem_actionProviderClass);
            boolean z = this.itemActionProviderClassName != null;
            if (z && this.itemActionViewLayout == 0 && this.itemActionViewClassName == null) {
                this.itemActionProvider = (ActionProvider) newInstance(this.itemActionProviderClassName, SupportMenuInflater.ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE, SupportMenuInflater.this.mActionProviderConstructorArguments);
            } else {
                if (z) {
                    Log.w(SupportMenuInflater.LOG_TAG, "Ignoring attribute 'actionProviderClass'. Action view already specified.");
                }
                this.itemActionProvider = null;
            }
            this.itemContentDescription = tintTypedArrayObtainStyledAttributes.getText(R.styleable.MenuItem_contentDescription);
            this.itemTooltipText = tintTypedArrayObtainStyledAttributes.getText(R.styleable.MenuItem_tooltipText);
            if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.MenuItem_iconTintMode)) {
                this.itemIconTintMode = DrawableUtils.parseTintMode(tintTypedArrayObtainStyledAttributes.getInt(R.styleable.MenuItem_iconTintMode, -1), this.itemIconTintMode);
            } else {
                this.itemIconTintMode = null;
            }
            if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.MenuItem_iconTint)) {
                this.itemIconTintList = tintTypedArrayObtainStyledAttributes.getColorStateList(R.styleable.MenuItem_iconTint);
            } else {
                this.itemIconTintList = null;
            }
            tintTypedArrayObtainStyledAttributes.recycle();
            this.itemAdded = false;
        }

        private char getShortcut(String shortcutString) {
            if (shortcutString == null) {
                return (char) 0;
            }
            return shortcutString.charAt(0);
        }

        private void setItem(MenuItem item) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            item.setChecked(this.itemChecked).setVisible(this.itemVisible).setEnabled(this.itemEnabled).setCheckable(this.itemCheckable >= 1).setTitleCondensed(this.itemTitleCondensed).setIcon(this.itemIconResId);
            if (this.itemShowAsAction >= 0) {
                item.setShowAsAction(this.itemShowAsAction);
            }
            if (this.itemListenerMethodName != null) {
                if (SupportMenuInflater.this.mContext.isRestricted()) {
                    throw new IllegalStateException("The android:onClick attribute cannot be used within a restricted context");
                }
                item.setOnMenuItemClickListener(new InflatedOnMenuItemClickListener(SupportMenuInflater.this.getRealOwner(), this.itemListenerMethodName));
            }
            if (this.itemCheckable >= 2) {
                if (item instanceof MenuItemImpl) {
                    ((MenuItemImpl) item).setExclusiveCheckable(true);
                } else if (item instanceof MenuItemWrapperICS) {
                    ((MenuItemWrapperICS) item).setExclusiveCheckable(true);
                }
            }
            boolean actionViewSpecified = false;
            if (this.itemActionViewClassName != null) {
                View actionView = (View) newInstance(this.itemActionViewClassName, SupportMenuInflater.ACTION_VIEW_CONSTRUCTOR_SIGNATURE, SupportMenuInflater.this.mActionViewConstructorArguments);
                item.setActionView(actionView);
                actionViewSpecified = true;
            }
            if (this.itemActionViewLayout > 0) {
                if (!actionViewSpecified) {
                    item.setActionView(this.itemActionViewLayout);
                } else {
                    Log.w(SupportMenuInflater.LOG_TAG, "Ignoring attribute 'itemActionViewLayout'. Action view already specified.");
                }
            }
            if (this.itemActionProvider != null) {
                MenuItemCompat.setActionProvider(item, this.itemActionProvider);
            }
            MenuItemCompat.setContentDescription(item, this.itemContentDescription);
            MenuItemCompat.setTooltipText(item, this.itemTooltipText);
            MenuItemCompat.setAlphabeticShortcut(item, this.itemAlphabeticShortcut, this.itemAlphabeticModifiers);
            MenuItemCompat.setNumericShortcut(item, this.itemNumericShortcut, this.itemNumericModifiers);
            if (this.itemIconTintMode != null) {
                MenuItemCompat.setIconTintMode(item, this.itemIconTintMode);
            }
            if (this.itemIconTintList != null) {
                MenuItemCompat.setIconTintList(item, this.itemIconTintList);
            }
        }

        public void addItem() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            this.itemAdded = true;
            setItem(this.menu.add(this.groupId, this.itemId, this.itemCategoryOrder, this.itemTitle));
        }

        public SubMenu addSubMenuItem() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            this.itemAdded = true;
            SubMenu subMenu = this.menu.addSubMenu(this.groupId, this.itemId, this.itemCategoryOrder, this.itemTitle);
            setItem(subMenu.getItem());
            return subMenu;
        }

        public boolean hasAddedItem() {
            return this.itemAdded;
        }

        private <T> T newInstance(String str, Class<?>[] clsArr, Object[] objArr) throws NoSuchMethodException, SecurityException {
            try {
                Constructor<?> constructor = Class.forName(str, false, SupportMenuInflater.this.mContext.getClassLoader()).getConstructor(clsArr);
                constructor.setAccessible(true);
                return (T) constructor.newInstance(objArr);
            } catch (Exception e) {
                Log.w(SupportMenuInflater.LOG_TAG, "Cannot instantiate class: " + str, e);
                return null;
            }
        }
    }
}
