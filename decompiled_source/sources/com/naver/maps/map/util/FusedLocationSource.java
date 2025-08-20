package com.naver.maps.map.util;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Looper;
import android.view.WindowManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.naver.maps.geometry.MathUtils;
import com.naver.maps.map.LocationSource;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
public class FusedLocationSource implements LocationSource {
    private static final String[] a = {"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"};
    private final b b;
    private final a c;
    private final WeakReference<Activity> d;
    private final WeakReference<Fragment> e;
    private final int f;
    private ActivationHook g;
    private LocationSource.OnLocationChangedListener h;
    private boolean i;
    private boolean j;
    private Location k;
    private float l;
    private final Runnable m;

    public interface ActivationHook {
        void onBeforeActivate(Runnable runnable);
    }

    private static class b extends LocationCallback {
        private final FusedLocationSource a;

        private b(FusedLocationSource fusedLocationSource) {
            this.a = fusedLocationSource;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(Context context) {
            LocationServices.getFusedLocationProviderClient(context).requestLocationUpdates(new LocationRequest.Builder(100, 1000L).build(), this, Looper.getMainLooper());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(Context context) {
            LocationServices.getFusedLocationProviderClient(context).removeLocationUpdates(this);
        }

        @Override // com.google.android.gms.location.LocationCallback
        public void onLocationResult(LocationResult locationResult) {
            this.a.a(locationResult.getLastLocation());
        }
    }

    private static class a implements SensorEventListener {
        private final FusedLocationSource a;
        private final C0014a b = new C0014a();
        private final float[] c = new float[9];
        private final float[] d = new float[9];
        private final float[] e = new float[3];
        private Context f;
        private float[] g;
        private float[] h;

        /* renamed from: com.naver.maps.map.util.FusedLocationSource$a$a, reason: collision with other inner class name */
        private static class C0014a {
            private final double[] a;
            private final double[] b;
            private int c;
            private int d;

            private C0014a() {
                this.a = new double[40];
                this.b = new double[40];
                this.c = 0;
                this.d = 0;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public double a(double d) {
                this.a[this.d] = Math.cos(d);
                this.b[this.d] = Math.sin(d);
                int i = this.d + 1;
                this.d = i;
                if (i == 40) {
                    this.d = 0;
                }
                if (this.c < 40) {
                    this.c++;
                }
                double d2 = 0.0d;
                double d3 = 0.0d;
                for (int i2 = 0; i2 < this.c; i2++) {
                    d3 += this.a[i2];
                    d2 += this.b[i2];
                }
                return Math.atan2(d2 / this.c, d3 / this.c);
            }
        }

        public a(FusedLocationSource fusedLocationSource) {
            this.a = fusedLocationSource;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(Context context) {
            if (this.f != null) {
                return;
            }
            this.f = context;
            SensorManager sensorManager = (SensorManager) context.getSystemService("sensor");
            if (sensorManager == null) {
                return;
            }
            sensorManager.registerListener(this, sensorManager.getDefaultSensor(1), 1);
            sensorManager.registerListener(this, sensorManager.getDefaultSensor(2), 1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a() {
            if (this.f == null) {
                return;
            }
            SensorManager sensorManager = (SensorManager) this.f.getSystemService("sensor");
            if (sensorManager != null) {
                sensorManager.unregisterListener(this);
            }
            this.h = null;
            this.g = null;
            this.f = null;
        }

        @Override // android.hardware.SensorEventListener
        public void onSensorChanged(SensorEvent event) {
            int type = event.sensor.getType();
            if (type == 1) {
                if (this.h == null) {
                    this.h = new float[3];
                }
                this.h[0] = event.values[0];
                this.h[1] = event.values[1];
                this.h[2] = event.values[2];
            } else if (type == 2) {
                if (this.g == null) {
                    this.g = new float[3];
                }
                this.g[0] = event.values[0];
                this.g[1] = event.values[1];
                this.g[2] = event.values[2];
            } else {
                return;
            }
            if (this.h == null || this.g == null || !SensorManager.getRotationMatrix(this.c, this.d, this.h, this.g)) {
                return;
            }
            SensorManager.getOrientation(this.c, this.e);
            this.a.a((float) Math.toDegrees(this.b.a(a(this.e[0]))));
        }

        @Override // android.hardware.SensorEventListener
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        private double a(double d) {
            double d2;
            if (this.f == null) {
                return d;
            }
            switch (((WindowManager) this.f.getSystemService("window")).getDefaultDisplay().getRotation()) {
                case 1:
                    d2 = d + 90.0d;
                    break;
                case 2:
                    d2 = d + 180.0d;
                    break;
                case 3:
                    d2 = d + 270.0d;
                    break;
                default:
                    d2 = d;
                    break;
            }
            return MathUtils.wrap(d2, -180.0d, 180.0d);
        }
    }

    public FusedLocationSource(Fragment fragment, int permissionRequestCode) {
        this.b = new b();
        this.c = new a(this);
        this.l = Float.NaN;
        this.m = new Runnable() { // from class: com.naver.maps.map.util.FusedLocationSource.1
            @Override // java.lang.Runnable
            public void run() {
                Fragment fragment2;
                if (FusedLocationSource.this.c()) {
                    FusedLocationSource.this.d();
                    return;
                }
                if (FusedLocationSource.this.d != null) {
                    Activity activity = (Activity) FusedLocationSource.this.d.get();
                    if (activity != null) {
                        ActivityCompat.requestPermissions(activity, FusedLocationSource.a, FusedLocationSource.this.f);
                        return;
                    }
                    return;
                }
                if (FusedLocationSource.this.e != null && (fragment2 = (Fragment) FusedLocationSource.this.e.get()) != null) {
                    fragment2.requestPermissions(FusedLocationSource.a, FusedLocationSource.this.f);
                }
            }
        };
        this.d = null;
        this.e = new WeakReference<>(fragment);
        this.f = permissionRequestCode;
    }

    public FusedLocationSource(Activity activity, int permissionRequestCode) {
        this.b = new b();
        this.c = new a(this);
        this.l = Float.NaN;
        this.m = new Runnable() { // from class: com.naver.maps.map.util.FusedLocationSource.1
            @Override // java.lang.Runnable
            public void run() {
                Fragment fragment2;
                if (FusedLocationSource.this.c()) {
                    FusedLocationSource.this.d();
                    return;
                }
                if (FusedLocationSource.this.d != null) {
                    Activity activity2 = (Activity) FusedLocationSource.this.d.get();
                    if (activity2 != null) {
                        ActivityCompat.requestPermissions(activity2, FusedLocationSource.a, FusedLocationSource.this.f);
                        return;
                    }
                    return;
                }
                if (FusedLocationSource.this.e != null && (fragment2 = (Fragment) FusedLocationSource.this.e.get()) != null) {
                    fragment2.requestPermissions(FusedLocationSource.a, FusedLocationSource.this.f);
                }
            }
        };
        this.d = new WeakReference<>(activity);
        this.e = null;
        this.f = permissionRequestCode;
    }

    public boolean onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != this.f) {
            return false;
        }
        for (int i : grantResults) {
            if (i != 0) {
                return true;
            }
        }
        d();
        return true;
    }

    public boolean isActivated() {
        return this.i;
    }

    private Context b() {
        Activity context;
        if (this.e != null) {
            Fragment fragment = this.e.get();
            if (fragment == null) {
                return null;
            }
            context = fragment.getContext();
        } else {
            if (this.d == null) {
                return null;
            }
            context = this.d.get();
        }
        if (context == null) {
            return null;
        }
        return context.getApplicationContext();
    }

    public ActivationHook getActivationHook() {
        return this.g;
    }

    public void setActivationHook(ActivationHook activationHook) {
        this.g = activationHook;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean c() {
        Context contextB = b();
        if (contextB == null) {
            return false;
        }
        for (String str : a) {
            if (PermissionChecker.checkSelfPermission(contextB, str) == 0) {
                return true;
            }
        }
        return false;
    }

    @Override // com.naver.maps.map.LocationSource
    public void activate(LocationSource.OnLocationChangedListener listener) {
        this.h = listener;
        if (!this.i) {
            if (this.g == null) {
                this.m.run();
            } else {
                this.g.onBeforeActivate(this.m);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        Context contextB = b();
        if (contextB != null) {
            this.b.a(contextB);
            if (this.j) {
                this.c.a(contextB);
            }
            this.i = true;
        }
    }

    @Override // com.naver.maps.map.LocationSource
    public void deactivate() {
        if (this.i) {
            e();
        }
        this.h = null;
    }

    private void e() {
        Context contextB = b();
        if (contextB != null) {
            this.b.b(contextB);
            if (this.j) {
                this.c.a();
                this.l = Float.NaN;
            }
            this.i = false;
        }
    }

    public boolean isCompassEnabled() {
        return this.j;
    }

    public void setCompassEnabled(boolean enabled) {
        Context contextB;
        if (this.j == enabled) {
            return;
        }
        this.j = enabled;
        if (this.i && (contextB = b()) != null) {
            if (enabled) {
                this.c.a(contextB);
            } else {
                this.c.a();
                this.l = Float.NaN;
            }
        }
    }

    public Location getLastLocation() {
        return this.k;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Location location) {
        this.k = location;
        f();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(float f) {
        if (!Float.isNaN(this.l) && Math.abs(this.l - f) < 2.0f) {
            return;
        }
        this.l = f;
        f();
    }

    private void f() {
        if (this.h == null || this.k == null) {
            return;
        }
        if (!Float.isNaN(this.l)) {
            this.k.setBearing(this.l);
        }
        this.h.onLocationChanged(this.k);
    }
}
