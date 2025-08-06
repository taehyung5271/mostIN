package androidx.constraintlayout.core.motion.key;

import androidx.constraintlayout.core.motion.CustomVariable;
import androidx.constraintlayout.core.motion.MotionWidget;
import androidx.constraintlayout.core.motion.utils.FloatRect;
import androidx.constraintlayout.core.motion.utils.SplineSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

/* loaded from: classes.dex */
public class MotionKeyTrigger extends MotionKey {
    public static final String CROSS = "CROSS";
    public static final int KEY_TYPE = 5;
    public static final String NEGATIVE_CROSS = "negativeCross";
    public static final String POSITIVE_CROSS = "positiveCross";
    public static final String POST_LAYOUT = "postLayout";
    private static final String TAG = "KeyTrigger";
    public static final String TRIGGER_COLLISION_ID = "triggerCollisionId";
    public static final String TRIGGER_COLLISION_VIEW = "triggerCollisionView";
    public static final String TRIGGER_ID = "triggerID";
    public static final String TRIGGER_RECEIVER = "triggerReceiver";
    public static final String TRIGGER_SLACK = "triggerSlack";
    public static final int TYPE_CROSS = 312;
    public static final int TYPE_NEGATIVE_CROSS = 310;
    public static final int TYPE_POSITIVE_CROSS = 309;
    public static final int TYPE_POST_LAYOUT = 304;
    public static final int TYPE_TRIGGER_COLLISION_ID = 307;
    public static final int TYPE_TRIGGER_COLLISION_VIEW = 306;
    public static final int TYPE_TRIGGER_ID = 308;
    public static final int TYPE_TRIGGER_RECEIVER = 311;
    public static final int TYPE_TRIGGER_SLACK = 305;
    public static final int TYPE_VIEW_TRANSITION_ON_CROSS = 301;
    public static final int TYPE_VIEW_TRANSITION_ON_NEGATIVE_CROSS = 303;
    public static final int TYPE_VIEW_TRANSITION_ON_POSITIVE_CROSS = 302;
    public static final String VIEW_TRANSITION_ON_CROSS = "viewTransitionOnCross";
    public static final String VIEW_TRANSITION_ON_NEGATIVE_CROSS = "viewTransitionOnNegativeCross";
    public static final String VIEW_TRANSITION_ON_POSITIVE_CROSS = "viewTransitionOnPositiveCross";
    private float mFireLastPos;
    private int mCurveFit = -1;
    private String mCross = null;
    private int mTriggerReceiver = UNSET;
    private String mNegativeCross = null;
    private String mPositiveCross = null;
    private int mTriggerID = UNSET;
    private int mTriggerCollisionId = UNSET;
    float mTriggerSlack = 0.1f;
    private boolean mFireCrossReset = true;
    private boolean mFireNegativeReset = true;
    private boolean mFirePositiveReset = true;
    private float mFireThreshold = Float.NaN;
    private boolean mPostLayout = false;
    int mViewTransitionOnNegativeCross = UNSET;
    int mViewTransitionOnPositiveCross = UNSET;
    int mViewTransitionOnCross = UNSET;
    FloatRect mCollisionRect = new FloatRect();
    FloatRect mTargetRect = new FloatRect();

    public MotionKeyTrigger() {
        this.mType = 5;
        this.mCustom = new HashMap<>();
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey
    public void getAttributeNames(HashSet<String> attributes) {
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey
    public void addValues(HashMap<String, SplineSet> splines) {
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:38:0x007f  */
    @Override // androidx.constraintlayout.core.motion.utils.TypedValues
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public int getId(java.lang.String r3) {
        /*
            r2 = this;
            int r0 = r3.hashCode()
            r1 = -1
            switch(r0) {
                case -1594793529: goto L74;
                case -966421266: goto L69;
                case -786670827: goto L5f;
                case -648752941: goto L55;
                case -638126837: goto L4a;
                case -76025313: goto L40;
                case -9754574: goto L35;
                case 364489912: goto L2b;
                case 1301930599: goto L20;
                case 1401391082: goto L16;
                case 1535404999: goto La;
                default: goto L8;
            }
        L8:
            goto L7f
        La:
            java.lang.String r0 = "triggerReceiver"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L8
            r0 = 10
            goto L80
        L16:
            java.lang.String r0 = "postLayout"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L8
            r0 = 3
            goto L80
        L20:
            java.lang.String r0 = "viewTransitionOnCross"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L8
            r0 = 0
            goto L80
        L2b:
            java.lang.String r0 = "triggerSlack"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L8
            r0 = 4
            goto L80
        L35:
            java.lang.String r0 = "viewTransitionOnNegativeCross"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L8
            r0 = 2
            goto L80
        L40:
            java.lang.String r0 = "triggerCollisionView"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L8
            r0 = 5
            goto L80
        L4a:
            java.lang.String r0 = "negativeCross"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L8
            r0 = 9
            goto L80
        L55:
            java.lang.String r0 = "triggerID"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L8
            r0 = 7
            goto L80
        L5f:
            java.lang.String r0 = "triggerCollisionId"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L8
            r0 = 6
            goto L80
        L69:
            java.lang.String r0 = "viewTransitionOnPositiveCross"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L8
            r0 = 1
            goto L80
        L74:
            java.lang.String r0 = "positiveCross"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L8
            r0 = 8
            goto L80
        L7f:
            r0 = r1
        L80:
            switch(r0) {
                case 0: goto La2;
                case 1: goto L9f;
                case 2: goto L9c;
                case 3: goto L99;
                case 4: goto L96;
                case 5: goto L93;
                case 6: goto L90;
                case 7: goto L8d;
                case 8: goto L8a;
                case 9: goto L87;
                case 10: goto L84;
                default: goto L83;
            }
        L83:
            return r1
        L84:
            r0 = 311(0x137, float:4.36E-43)
            return r0
        L87:
            r0 = 310(0x136, float:4.34E-43)
            return r0
        L8a:
            r0 = 309(0x135, float:4.33E-43)
            return r0
        L8d:
            r0 = 308(0x134, float:4.32E-43)
            return r0
        L90:
            r0 = 307(0x133, float:4.3E-43)
            return r0
        L93:
            r0 = 306(0x132, float:4.29E-43)
            return r0
        L96:
            r0 = 305(0x131, float:4.27E-43)
            return r0
        L99:
            r0 = 304(0x130, float:4.26E-43)
            return r0
        L9c:
            r0 = 303(0x12f, float:4.25E-43)
            return r0
        L9f:
            r0 = 302(0x12e, float:4.23E-43)
            return r0
        La2:
            r0 = 301(0x12d, float:4.22E-43)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.motion.key.MotionKeyTrigger.getId(java.lang.String):int");
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey
    public MotionKeyTrigger copy(MotionKey src) {
        super.copy(src);
        MotionKeyTrigger k = (MotionKeyTrigger) src;
        this.mCurveFit = k.mCurveFit;
        this.mCross = k.mCross;
        this.mTriggerReceiver = k.mTriggerReceiver;
        this.mNegativeCross = k.mNegativeCross;
        this.mPositiveCross = k.mPositiveCross;
        this.mTriggerID = k.mTriggerID;
        this.mTriggerCollisionId = k.mTriggerCollisionId;
        this.mTriggerSlack = k.mTriggerSlack;
        this.mFireCrossReset = k.mFireCrossReset;
        this.mFireNegativeReset = k.mFireNegativeReset;
        this.mFirePositiveReset = k.mFirePositiveReset;
        this.mFireThreshold = k.mFireThreshold;
        this.mFireLastPos = k.mFireLastPos;
        this.mPostLayout = k.mPostLayout;
        this.mCollisionRect = k.mCollisionRect;
        this.mTargetRect = k.mTargetRect;
        return this;
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey
    /* renamed from: clone */
    public MotionKey mo35clone() {
        return new MotionKeyTrigger().copy((MotionKey) this);
    }

    private void fireCustom(String str, MotionWidget widget) {
        boolean callAll = str.length() == 1;
        if (!callAll) {
            str = str.substring(1).toLowerCase(Locale.ROOT);
        }
        for (String name : this.mCustom.keySet()) {
            String lowerCase = name.toLowerCase(Locale.ROOT);
            if (callAll || lowerCase.matches(str)) {
                CustomVariable custom = this.mCustom.get(name);
                if (custom != null) {
                    custom.applyToWidget(widget);
                }
            }
        }
    }

    public void conditionallyFire(float position, MotionWidget child) {
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey, androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int type, int value) {
        switch (type) {
            case 301:
                this.mViewTransitionOnCross = value;
                return true;
            case 302:
                this.mViewTransitionOnPositiveCross = value;
                return true;
            case 303:
                this.mViewTransitionOnNegativeCross = value;
                return true;
            case 304:
            case 305:
            case 306:
            case 309:
            case 310:
            default:
                return super.setValue(type, value);
            case 307:
                this.mTriggerCollisionId = value;
                return true;
            case 308:
                this.mTriggerID = toInt(Integer.valueOf(value));
                return true;
            case 311:
                this.mTriggerReceiver = value;
                return true;
        }
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey, androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int type, float value) {
        switch (type) {
            case 305:
                this.mTriggerSlack = value;
                return true;
            default:
                return super.setValue(type, value);
        }
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey, androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int type, String value) {
        switch (type) {
            case 309:
                this.mPositiveCross = value;
                return true;
            case 310:
                this.mNegativeCross = value;
                return true;
            case 311:
            default:
                return super.setValue(type, value);
            case 312:
                this.mCross = value;
                return true;
        }
    }

    @Override // androidx.constraintlayout.core.motion.key.MotionKey, androidx.constraintlayout.core.motion.utils.TypedValues
    public boolean setValue(int type, boolean value) {
        switch (type) {
            case 304:
                this.mPostLayout = value;
                return true;
            default:
                return super.setValue(type, value);
        }
    }
}
