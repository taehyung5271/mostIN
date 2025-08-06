package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.Xml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class StateSet {
    private static final boolean DEBUG = false;
    public static final String TAG = "ConstraintLayoutStates";
    ConstraintSet mDefaultConstraintSet;
    int mDefaultState = -1;
    int mCurrentStateId = -1;
    int mCurrentConstraintNumber = -1;
    private SparseArray<State> mStateList = new SparseArray<>();
    private SparseArray<ConstraintSet> mConstraintSetMap = new SparseArray<>();
    private ConstraintsChangedListener mConstraintsChangedListener = null;

    public StateSet(Context context, XmlPullParser parser) throws XmlPullParserException, IOException {
        load(context, parser);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0051  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void load(android.content.Context r11, org.xmlpull.v1.XmlPullParser r12) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r10 = this;
            android.util.AttributeSet r0 = android.util.Xml.asAttributeSet(r12)
            int[] r1 = androidx.constraintlayout.widget.R.styleable.StateSet
            android.content.res.TypedArray r1 = r11.obtainStyledAttributes(r0, r1)
            int r2 = r1.getIndexCount()
            r3 = 0
        Lf:
            if (r3 >= r2) goto L24
            int r4 = r1.getIndex(r3)
            int r5 = androidx.constraintlayout.widget.R.styleable.StateSet_defaultState
            if (r4 != r5) goto L21
            int r5 = r10.mDefaultState
            int r5 = r1.getResourceId(r4, r5)
            r10.mDefaultState = r5
        L21:
            int r3 = r3 + 1
            goto Lf
        L24:
            r1.recycle()
            r3 = 0
            r4 = 0
            r5 = 0
            int r6 = r12.getEventType()     // Catch: java.io.IOException -> La5 org.xmlpull.v1.XmlPullParserException -> Laa
        L2e:
            r7 = 1
            if (r6 == r7) goto La4
            java.lang.String r8 = "StateSet"
            switch(r6) {
                case 0: goto L98;
                case 1: goto L36;
                case 2: goto L45;
                case 3: goto L38;
                default: goto L36;
            }
        L36:
            goto L9e
        L38:
            java.lang.String r7 = r12.getName()     // Catch: java.io.IOException -> La5 org.xmlpull.v1.XmlPullParserException -> Laa
            boolean r7 = r8.equals(r7)     // Catch: java.io.IOException -> La5 org.xmlpull.v1.XmlPullParserException -> Laa
            if (r7 == 0) goto L43
            return
        L43:
            r3 = 0
            goto L9e
        L45:
            java.lang.String r9 = r12.getName()     // Catch: java.io.IOException -> La5 org.xmlpull.v1.XmlPullParserException -> Laa
            r3 = r9
            int r9 = r3.hashCode()     // Catch: java.io.IOException -> La5 org.xmlpull.v1.XmlPullParserException -> Laa
            switch(r9) {
                case 80204913: goto L6d;
                case 1301459538: goto L63;
                case 1382829617: goto L5c;
                case 1901439077: goto L52;
                default: goto L51;
            }     // Catch: java.io.IOException -> La5 org.xmlpull.v1.XmlPullParserException -> Laa
        L51:
            goto L77
        L52:
            java.lang.String r7 = "Variant"
            boolean r7 = r3.equals(r7)     // Catch: java.io.IOException -> La5 org.xmlpull.v1.XmlPullParserException -> Laa
            if (r7 == 0) goto L51
            r7 = 3
            goto L78
        L5c:
            boolean r8 = r3.equals(r8)     // Catch: java.io.IOException -> La5 org.xmlpull.v1.XmlPullParserException -> Laa
            if (r8 == 0) goto L51
            goto L78
        L63:
            java.lang.String r7 = "LayoutDescription"
            boolean r7 = r3.equals(r7)     // Catch: java.io.IOException -> La5 org.xmlpull.v1.XmlPullParserException -> Laa
            if (r7 == 0) goto L51
            r7 = 0
            goto L78
        L6d:
            java.lang.String r7 = "State"
            boolean r7 = r3.equals(r7)     // Catch: java.io.IOException -> La5 org.xmlpull.v1.XmlPullParserException -> Laa
            if (r7 == 0) goto L51
            r7 = 2
            goto L78
        L77:
            r7 = -1
        L78:
            switch(r7) {
                case 0: goto L96;
                case 1: goto L95;
                case 2: goto L87;
                case 3: goto L7c;
                default: goto L7b;
            }     // Catch: java.io.IOException -> La5 org.xmlpull.v1.XmlPullParserException -> Laa
        L7b:
            goto L97
        L7c:
            androidx.constraintlayout.widget.StateSet$Variant r7 = new androidx.constraintlayout.widget.StateSet$Variant     // Catch: java.io.IOException -> La5 org.xmlpull.v1.XmlPullParserException -> Laa
            r7.<init>(r11, r12)     // Catch: java.io.IOException -> La5 org.xmlpull.v1.XmlPullParserException -> Laa
            if (r5 == 0) goto L97
            r5.add(r7)     // Catch: java.io.IOException -> La5 org.xmlpull.v1.XmlPullParserException -> Laa
            goto L97
        L87:
            androidx.constraintlayout.widget.StateSet$State r7 = new androidx.constraintlayout.widget.StateSet$State     // Catch: java.io.IOException -> La5 org.xmlpull.v1.XmlPullParserException -> Laa
            r7.<init>(r11, r12)     // Catch: java.io.IOException -> La5 org.xmlpull.v1.XmlPullParserException -> Laa
            r5 = r7
            android.util.SparseArray<androidx.constraintlayout.widget.StateSet$State> r7 = r10.mStateList     // Catch: java.io.IOException -> La5 org.xmlpull.v1.XmlPullParserException -> Laa
            int r8 = r5.mId     // Catch: java.io.IOException -> La5 org.xmlpull.v1.XmlPullParserException -> Laa
            r7.put(r8, r5)     // Catch: java.io.IOException -> La5 org.xmlpull.v1.XmlPullParserException -> Laa
            goto L97
        L95:
            goto L97
        L96:
        L97:
            goto L9e
        L98:
            java.lang.String r7 = r12.getName()     // Catch: java.io.IOException -> La5 org.xmlpull.v1.XmlPullParserException -> Laa
            r4 = r7
        L9e:
            int r7 = r12.next()     // Catch: java.io.IOException -> La5 org.xmlpull.v1.XmlPullParserException -> Laa
            r6 = r7
            goto L2e
        La4:
            goto Lae
        La5:
            r4 = move-exception
            r4.printStackTrace()
            goto Laf
        Laa:
            r4 = move-exception
            r4.printStackTrace()
        Lae:
        Laf:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.widget.StateSet.load(android.content.Context, org.xmlpull.v1.XmlPullParser):void");
    }

    public boolean needsToChange(int id, float width, float height) {
        if (this.mCurrentStateId != id) {
            return true;
        }
        SparseArray<State> sparseArray = this.mStateList;
        State state = id == -1 ? sparseArray.valueAt(0) : sparseArray.get(this.mCurrentStateId);
        return (this.mCurrentConstraintNumber == -1 || !state.mVariants.get(this.mCurrentConstraintNumber).match(width, height)) && this.mCurrentConstraintNumber != state.findMatch(width, height);
    }

    public void setOnConstraintsChanged(ConstraintsChangedListener constraintsChangedListener) {
        this.mConstraintsChangedListener = constraintsChangedListener;
    }

    public int stateGetConstraintID(int id, int width, int height) {
        return updateConstraints(-1, id, width, height);
    }

    public int convertToConstraintSet(int currentConstrainSettId, int stateId, float width, float height) {
        State state = this.mStateList.get(stateId);
        if (state == null) {
            return stateId;
        }
        if (width == -1.0f || height == -1.0f) {
            if (state.mConstraintID == currentConstrainSettId) {
                return currentConstrainSettId;
            }
            Iterator<Variant> it = state.mVariants.iterator();
            while (it.hasNext()) {
                if (currentConstrainSettId == it.next().mConstraintID) {
                    return currentConstrainSettId;
                }
            }
            return state.mConstraintID;
        }
        Variant match = null;
        Iterator<Variant> it2 = state.mVariants.iterator();
        while (it2.hasNext()) {
            Variant mVariant = it2.next();
            if (mVariant.match(width, height)) {
                if (currentConstrainSettId == mVariant.mConstraintID) {
                    return currentConstrainSettId;
                }
                match = mVariant;
            }
        }
        if (match != null) {
            return match.mConstraintID;
        }
        return state.mConstraintID;
    }

    public int updateConstraints(int currentId, int id, float width, float height) {
        State state;
        int match;
        if (currentId == id) {
            if (id == -1) {
                state = this.mStateList.valueAt(0);
            } else {
                state = this.mStateList.get(this.mCurrentStateId);
            }
            if (state == null) {
                return -1;
            }
            if ((this.mCurrentConstraintNumber == -1 || !state.mVariants.get(currentId).match(width, height)) && currentId != (match = state.findMatch(width, height))) {
                return match == -1 ? state.mConstraintID : state.mVariants.get(match).mConstraintID;
            }
            return currentId;
        }
        State state2 = this.mStateList.get(id);
        if (state2 == null) {
            return -1;
        }
        int match2 = state2.findMatch(width, height);
        return match2 == -1 ? state2.mConstraintID : state2.mVariants.get(match2).mConstraintID;
    }

    static class State {
        int mConstraintID;
        int mId;
        boolean mIsLayout;
        ArrayList<Variant> mVariants = new ArrayList<>();

        public State(Context context, XmlPullParser parser) throws Resources.NotFoundException {
            this.mConstraintID = -1;
            this.mIsLayout = false;
            AttributeSet attrs = Xml.asAttributeSet(parser);
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.State);
            int N = a.getIndexCount();
            for (int i = 0; i < N; i++) {
                int attr = a.getIndex(i);
                if (attr == R.styleable.State_android_id) {
                    this.mId = a.getResourceId(attr, this.mId);
                } else if (attr == R.styleable.State_constraints) {
                    this.mConstraintID = a.getResourceId(attr, this.mConstraintID);
                    String type = context.getResources().getResourceTypeName(this.mConstraintID);
                    context.getResources().getResourceName(this.mConstraintID);
                    if ("layout".equals(type)) {
                        this.mIsLayout = true;
                    }
                }
            }
            a.recycle();
        }

        void add(Variant size) {
            this.mVariants.add(size);
        }

        public int findMatch(float width, float height) {
            for (int i = 0; i < this.mVariants.size(); i++) {
                if (this.mVariants.get(i).match(width, height)) {
                    return i;
                }
            }
            return -1;
        }
    }

    static class Variant {
        int mConstraintID;
        int mId;
        boolean mIsLayout;
        float mMaxHeight;
        float mMaxWidth;
        float mMinHeight;
        float mMinWidth;

        public Variant(Context context, XmlPullParser parser) throws Resources.NotFoundException {
            this.mMinWidth = Float.NaN;
            this.mMinHeight = Float.NaN;
            this.mMaxWidth = Float.NaN;
            this.mMaxHeight = Float.NaN;
            this.mConstraintID = -1;
            this.mIsLayout = false;
            AttributeSet attrs = Xml.asAttributeSet(parser);
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Variant);
            int N = a.getIndexCount();
            for (int i = 0; i < N; i++) {
                int attr = a.getIndex(i);
                if (attr == R.styleable.Variant_constraints) {
                    this.mConstraintID = a.getResourceId(attr, this.mConstraintID);
                    String type = context.getResources().getResourceTypeName(this.mConstraintID);
                    context.getResources().getResourceName(this.mConstraintID);
                    if ("layout".equals(type)) {
                        this.mIsLayout = true;
                    }
                } else if (attr == R.styleable.Variant_region_heightLessThan) {
                    this.mMaxHeight = a.getDimension(attr, this.mMaxHeight);
                } else if (attr == R.styleable.Variant_region_heightMoreThan) {
                    this.mMinHeight = a.getDimension(attr, this.mMinHeight);
                } else if (attr == R.styleable.Variant_region_widthLessThan) {
                    this.mMaxWidth = a.getDimension(attr, this.mMaxWidth);
                } else if (attr == R.styleable.Variant_region_widthMoreThan) {
                    this.mMinWidth = a.getDimension(attr, this.mMinWidth);
                } else {
                    Log.v("ConstraintLayoutStates", "Unknown tag");
                }
            }
            a.recycle();
        }

        boolean match(float widthDp, float heightDp) {
            if (!Float.isNaN(this.mMinWidth) && widthDp < this.mMinWidth) {
                return false;
            }
            if (!Float.isNaN(this.mMinHeight) && heightDp < this.mMinHeight) {
                return false;
            }
            if (Float.isNaN(this.mMaxWidth) || widthDp <= this.mMaxWidth) {
                return Float.isNaN(this.mMaxHeight) || heightDp <= this.mMaxHeight;
            }
            return false;
        }
    }
}
