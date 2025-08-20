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
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class ConstraintLayoutStates {
    private static final boolean DEBUG = false;
    public static final String TAG = "ConstraintLayoutStates";
    private final ConstraintLayout mConstraintLayout;
    ConstraintSet mDefaultConstraintSet;
    int mCurrentStateId = -1;
    int mCurrentConstraintNumber = -1;
    private SparseArray<State> mStateList = new SparseArray<>();
    private SparseArray<ConstraintSet> mConstraintSetMap = new SparseArray<>();
    private ConstraintsChangedListener mConstraintsChangedListener = null;

    ConstraintLayoutStates(Context context, ConstraintLayout layout, int resourceID) throws XmlPullParserException, Resources.NotFoundException, IOException, NumberFormatException {
        this.mConstraintLayout = layout;
        load(context, resourceID);
    }

    public boolean needsToChange(int id, float width, float height) {
        if (this.mCurrentStateId != id) {
            return true;
        }
        SparseArray<State> sparseArray = this.mStateList;
        State state = id == -1 ? sparseArray.valueAt(0) : sparseArray.get(this.mCurrentStateId);
        return (this.mCurrentConstraintNumber == -1 || !state.mVariants.get(this.mCurrentConstraintNumber).match(width, height)) && this.mCurrentConstraintNumber != state.findMatch(width, height);
    }

    public void updateConstraints(int id, float width, float height) {
        State state;
        int match;
        if (this.mCurrentStateId == id) {
            if (id == -1) {
                state = this.mStateList.valueAt(0);
            } else {
                state = this.mStateList.get(this.mCurrentStateId);
            }
            if ((this.mCurrentConstraintNumber != -1 && state.mVariants.get(this.mCurrentConstraintNumber).match(width, height)) || this.mCurrentConstraintNumber == (match = state.findMatch(width, height))) {
                return;
            }
            ConstraintSet constraintSet = match == -1 ? this.mDefaultConstraintSet : state.mVariants.get(match).mConstraintSet;
            int cid = match == -1 ? state.mConstraintID : state.mVariants.get(match).mConstraintID;
            if (constraintSet == null) {
                return;
            }
            this.mCurrentConstraintNumber = match;
            if (this.mConstraintsChangedListener != null) {
                this.mConstraintsChangedListener.preLayoutChange(-1, cid);
            }
            constraintSet.applyTo(this.mConstraintLayout);
            if (this.mConstraintsChangedListener != null) {
                this.mConstraintsChangedListener.postLayoutChange(-1, cid);
                return;
            }
            return;
        }
        this.mCurrentStateId = id;
        State state2 = this.mStateList.get(this.mCurrentStateId);
        int match2 = state2.findMatch(width, height);
        ConstraintSet constraintSet2 = match2 == -1 ? state2.mConstraintSet : state2.mVariants.get(match2).mConstraintSet;
        int cid2 = match2 == -1 ? state2.mConstraintID : state2.mVariants.get(match2).mConstraintID;
        if (constraintSet2 == null) {
            Log.v("ConstraintLayoutStates", "NO Constraint set found ! id=" + id + ", dim =" + width + ", " + height);
            return;
        }
        this.mCurrentConstraintNumber = match2;
        if (this.mConstraintsChangedListener != null) {
            this.mConstraintsChangedListener.preLayoutChange(id, cid2);
        }
        constraintSet2.applyTo(this.mConstraintLayout);
        if (this.mConstraintsChangedListener != null) {
            this.mConstraintsChangedListener.postLayoutChange(id, cid2);
        }
    }

    public void setOnConstraintsChanged(ConstraintsChangedListener constraintsChangedListener) {
        this.mConstraintsChangedListener = constraintsChangedListener;
    }

    static class State {
        int mConstraintID;
        ConstraintSet mConstraintSet;
        int mId;
        ArrayList<Variant> mVariants = new ArrayList<>();

        public State(Context context, XmlPullParser parser) throws Resources.NotFoundException {
            this.mConstraintID = -1;
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
                        this.mConstraintSet = new ConstraintSet();
                        this.mConstraintSet.clone(context, this.mConstraintID);
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
        ConstraintSet mConstraintSet;
        int mId;
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
                        this.mConstraintSet = new ConstraintSet();
                        this.mConstraintSet.clone(context, this.mConstraintID);
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

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void load(android.content.Context r9, int r10) throws org.xmlpull.v1.XmlPullParserException, android.content.res.Resources.NotFoundException, java.io.IOException, java.lang.NumberFormatException {
        /*
            r8 = this;
            android.content.res.Resources r0 = r9.getResources()
            android.content.res.XmlResourceParser r1 = r0.getXml(r10)
            r2 = 0
            r3 = 0
            r4 = 0
            int r5 = r1.getEventType()     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
        Lf:
            r6 = 1
            if (r5 == r6) goto L89
            switch(r5) {
                case 0: goto L7d;
                case 1: goto L15;
                case 2: goto L1a;
                case 3: goto L17;
                default: goto L15;
            }     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
        L15:
            goto L83
        L17:
            r3 = 0
            goto L83
        L1a:
            java.lang.String r7 = r1.getName()     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
            r3 = r7
            int r7 = r3.hashCode()     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
            switch(r7) {
                case -1349929691: goto L4e;
                case 80204913: goto L44;
                case 1382829617: goto L3b;
                case 1657696882: goto L31;
                case 1901439077: goto L27;
                default: goto L26;
            }     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
        L26:
            goto L58
        L27:
            java.lang.String r6 = "Variant"
            boolean r6 = r3.equals(r6)     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
            if (r6 == 0) goto L26
            r6 = 3
            goto L59
        L31:
            java.lang.String r6 = "layoutDescription"
            boolean r6 = r3.equals(r6)     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
            if (r6 == 0) goto L26
            r6 = 0
            goto L59
        L3b:
            java.lang.String r7 = "StateSet"
            boolean r7 = r3.equals(r7)     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
            if (r7 == 0) goto L26
            goto L59
        L44:
            java.lang.String r6 = "State"
            boolean r6 = r3.equals(r6)     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
            if (r6 == 0) goto L26
            r6 = 2
            goto L59
        L4e:
            java.lang.String r6 = "ConstraintSet"
            boolean r6 = r3.equals(r6)     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
            if (r6 == 0) goto L26
            r6 = 4
            goto L59
        L58:
            r6 = -1
        L59:
            switch(r6) {
                case 0: goto L7b;
                case 1: goto L7a;
                case 2: goto L6c;
                case 3: goto L61;
                case 4: goto L5d;
                default: goto L5c;
            }     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
        L5c:
            goto L7c
        L5d:
            r8.parseConstraintSet(r9, r1)     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
            goto L7c
        L61:
            androidx.constraintlayout.widget.ConstraintLayoutStates$Variant r6 = new androidx.constraintlayout.widget.ConstraintLayoutStates$Variant     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
            r6.<init>(r9, r1)     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
            if (r4 == 0) goto L7c
            r4.add(r6)     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
            goto L7c
        L6c:
            androidx.constraintlayout.widget.ConstraintLayoutStates$State r6 = new androidx.constraintlayout.widget.ConstraintLayoutStates$State     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
            r6.<init>(r9, r1)     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
            r4 = r6
            android.util.SparseArray<androidx.constraintlayout.widget.ConstraintLayoutStates$State> r6 = r8.mStateList     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
            int r7 = r4.mId     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
            r6.put(r7, r4)     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
            goto L7c
        L7a:
            goto L7c
        L7b:
        L7c:
            goto L83
        L7d:
            java.lang.String r6 = r1.getName()     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
            r2 = r6
        L83:
            int r6 = r1.next()     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
            r5 = r6
            goto Lf
        L89:
            goto L93
        L8a:
            r4 = move-exception
            r4.printStackTrace()
            goto L94
        L8f:
            r4 = move-exception
            r4.printStackTrace()
        L93:
        L94:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.widget.ConstraintLayoutStates.load(android.content.Context, int):void");
    }

    private void parseConstraintSet(Context context, XmlPullParser parser) throws NumberFormatException {
        ConstraintSet set = new ConstraintSet();
        int count = parser.getAttributeCount();
        for (int i = 0; i < count; i++) {
            String name = parser.getAttributeName(i);
            String s = parser.getAttributeValue(i);
            if (name != null && s != null && "id".equals(name)) {
                int id = -1;
                if (s.contains("/")) {
                    String tmp = s.substring(s.indexOf(47) + 1);
                    id = context.getResources().getIdentifier(tmp, "id", context.getPackageName());
                }
                if (id == -1) {
                    if (s.length() > 1) {
                        id = Integer.parseInt(s.substring(1));
                    } else {
                        Log.e("ConstraintLayoutStates", "error in parsing id");
                    }
                }
                set.load(context, parser);
                this.mConstraintSetMap.put(id, set);
                return;
            }
        }
    }
}
