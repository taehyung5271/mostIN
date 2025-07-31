package com.example.mostin.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import com.example.mostin.R;
import com.example.mostin.api.ApiClient;
import com.example.mostin.api.ApiService;
import com.example.mostin.models.CommuteModel;
import com.example.mostin.models.WorkPlace;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapSdk;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.util.FusedLocationSource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* loaded from: classes6.dex */
public class CommutingRegistrationFragment extends Fragment implements OnMapReadyCallback {
    public static final String ARG_EMPLOYEE_ID = "employee_id";
    public static final String ARG_EMPLOYEE_NAME = "employee_name";
    public static final String ARG_WORK_PLACE_NAME = "work_place_name";
    private static final String[] PERMISSIONS = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"};
    private static final int PERMISSION_REQUEST_CODE = 1000;
    private static final long WORK_DURATION = 28800000;
    private Button btnClockIn;
    private String employeeId;
    private String employeeName;
    private FusedLocationSource locationSource;
    private MapView mapView;
    private NaverMap naverMap;
    private TextView recentDeparture;
    private TextView recentEntry;
    private String workPlaceName;
    private TextView workPlaceText;
    private boolean isWorking = false;
    private long clockInTime = 0;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_commuting_registration, container, false);
        this.recentEntry = (TextView) view.findViewById(R.id.recentEntry);
        this.recentDeparture = (TextView) view.findViewById(R.id.recentDeparture);
        this.workPlaceText = (TextView) view.findViewById(R.id.commuting_work_place);
        Bundle args = getArguments();
        if (args == null) {
            Log.e("CommutingRegistration", "No arguments received");
            Toast.makeText(requireContext(), "사용자 정보를 불러올 수 없습니다.", 0).show();
        } else {
            this.employeeId = args.getString(ARG_EMPLOYEE_ID);
            this.employeeName = args.getString(ARG_EMPLOYEE_NAME);
            this.workPlaceName = args.getString(ARG_WORK_PLACE_NAME);
            Log.d("CommutingRegistration", "Received data - ID: " + this.employeeId + ", Name: " + this.employeeName + ", Workplace: " + this.workPlaceName);
            if (this.employeeId == null || this.employeeName == null || this.workPlaceName == null) {
                Log.e("CommutingRegistration", "Some user data is null");
                Toast.makeText(requireContext(), "사용자 정보�� 불완전합니다.", 0).show();
            } else {
                this.workPlaceText.setText(this.workPlaceName);
                checkWorkingStatus();
                updateRecentAttendance();
            }
        }
        this.btnClockIn = (Button) view.findViewById(R.id.btn_clock_in);
        this.btnClockIn.setText("출근");
        this.btnClockIn.setOnClickListener(new View.OnClickListener() { // from class: com.example.mostin.fragments.CommutingRegistrationFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m117x4786afb(view2);
            }
        });
        NaverMapSdk.getInstance(requireContext()).setClient(new NaverMapSdk.NaverCloudPlatformClient("s7yxuuh84o"));
        this.locationSource = new FusedLocationSource(this, 1000);
        this.mapView = (MapView) view.findViewById(R.id.map_view);
        this.mapView.onCreate(savedInstanceState);
        this.mapView.getMapAsync(this);
        return view;
    }

    /* renamed from: lambda$onCreateView$0$com-example-mostin-fragments-CommutingRegistrationFragment, reason: not valid java name */
    /* synthetic */ void m117x4786afb(View v) {
        handleWorkButton();
    }

    @Override // com.naver.maps.map.OnMapReadyCallback
    public void onMapReady(NaverMap naverMap) {
        this.naverMap = naverMap;
        naverMap.setLocationSource(this.locationSource);
        naverMap.getUiSettings().setLocationButtonEnabled(true);
        naverMap.getUiSettings().setZoomControlEnabled(true);
        naverMap.setLocationTrackingMode(LocationTrackingMode.NoFollow);
        naverMap.setIndoorEnabled(true);
        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_BUILDING, true);
        loadWorkplaceLocation();
    }

    @Override // androidx.fragment.app.Fragment
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults.length > 0) {
                boolean locationAccepted = grantResults[0] == 0;
                if (locationAccepted) {
                    if (this.naverMap != null) {
                        this.naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
                        return;
                    }
                    return;
                }
                Toast.makeText(requireContext(), "위치 권한이 필요합니다. 설정에서 권한을 허용해주세요.", 1).show();
                return;
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void checkWorkingStatus() {
        if (this.employeeId == null) {
            Log.e("CommutingRegistration", "Employee ID is null, cannot check working status.");
            return;
        }
        ApiService apiService = ApiClient.getApiService();
        Call<CommuteModel> call = apiService.getTodayCommute(this.employeeId);
        call.enqueue(new Callback<CommuteModel>() { // from class: com.example.mostin.fragments.CommutingRegistrationFragment.1
            @Override // retrofit2.Callback
            public void onResponse(Call<CommuteModel> call2, Response<CommuteModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CommuteModel todayCommute = response.body();
                    if (todayCommute.getStartTime() != null && todayCommute.getEndTime() == null) {
                        CommutingRegistrationFragment.this.isWorking = true;
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                            Date clockIn = sdf.parse(todayCommute.getCommuteDay() + " " + todayCommute.getStartTime());
                            CommutingRegistrationFragment.this.clockInTime = clockIn.getTime();
                            CommutingRegistrationFragment.this.updateButtonStatus();
                            return;
                        } catch (Exception e) {
                            Log.e("CommutingRegistration", "Error parsing date", e);
                            return;
                        }
                    }
                    return;
                }
                Log.d("CommutingRegistration", "No commute record for today.");
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<CommuteModel> call2, Throwable t) {
                Log.e("CommutingRegistration", "Error fetching today's commute", t);
                Toast.makeText(CommutingRegistrationFragment.this.getContext(), "오늘의 출근 기록을 가져오는 데 실패했습니다.", 0).show();
            }
        });
    }

    private void handleWorkButton() {
        if (!this.isWorking) {
            clockIn();
            return;
        }
        long currentTime = System.currentTimeMillis();
        long workedTime = currentTime - this.clockInTime;
        if (workedTime >= WORK_DURATION) {
            clockOut();
            return;
        }
        long remainingTime = WORK_DURATION - workedTime;
        long remainingHours = remainingTime / 3600000;
        long remainingMinutes = (remainingTime % 3600000) / 60000;
        Toast.makeText(requireContext(), String.format("근무 종료까지 %d시간 %d분 남았습니다", Long.valueOf(remainingHours), Long.valueOf(remainingMinutes)), 0).show();
    }

    private void clockIn() {
        Map<String, String> payload = new HashMap<>();
        payload.put("employeeId", this.employeeId);
        payload.put("employeeName", this.employeeName);
        payload.put("workPlaceName", this.workPlaceName);
        ApiService apiService = ApiClient.getApiService();
        Call<CommuteModel> call = apiService.clockIn(payload);
        call.enqueue(new Callback<CommuteModel>() { // from class: com.example.mostin.fragments.CommutingRegistrationFragment.2
            @Override // retrofit2.Callback
            public void onResponse(Call<CommuteModel> call2, Response<CommuteModel> response) {
                if (response.isSuccessful()) {
                    CommutingRegistrationFragment.this.isWorking = true;
                    CommutingRegistrationFragment.this.clockInTime = System.currentTimeMillis();
                    CommutingRegistrationFragment.this.updateButtonStatus();
                    CommutingRegistrationFragment.this.updateRecentAttendance();
                    Toast.makeText(CommutingRegistrationFragment.this.getContext(), "출근이 등록되었습니다!", 0).show();
                    return;
                }
                Toast.makeText(CommutingRegistrationFragment.this.getContext(), "출근 등록에 실패했습니다.", 0).show();
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<CommuteModel> call2, Throwable t) {
                Log.e("CommutingRegistration", "Error during clock-in", t);
                Toast.makeText(CommutingRegistrationFragment.this.getContext(), "출근 등록 중 오류가 발생했습니다.", 0).show();
            }
        });
    }

    private void clockOut() {
        Map<String, String> payload = new HashMap<>();
        payload.put("employeeId", this.employeeId);
        ApiService apiService = ApiClient.getApiService();
        Call<CommuteModel> call = apiService.clockOut(payload);
        call.enqueue(new Callback<CommuteModel>() { // from class: com.example.mostin.fragments.CommutingRegistrationFragment.3
            @Override // retrofit2.Callback
            public void onResponse(Call<CommuteModel> call2, Response<CommuteModel> response) {
                if (response.isSuccessful()) {
                    CommutingRegistrationFragment.this.isWorking = false;
                    CommutingRegistrationFragment.this.updateRecentAttendance();
                    Toast.makeText(CommutingRegistrationFragment.this.getContext(), "퇴근이 등록되었습니다!", 0).show();
                    Fragment calendarFragment = new AttendanceCalendarFragment();
                    CommutingRegistrationFragment.this.requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, calendarFragment).addToBackStack(null).commit();
                    return;
                }
                Toast.makeText(CommutingRegistrationFragment.this.getContext(), "퇴근 등록에 실패했습니다.", 0).show();
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<CommuteModel> call2, Throwable t) {
                Log.e("CommutingRegistration", "Error during clock-out", t);
                Toast.makeText(CommutingRegistrationFragment.this.getContext(), "퇴근 등록 중 오류가 발생했습니다.", 0).show();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateButtonStatus() {
        if (this.isWorking) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - this.clockInTime >= WORK_DURATION) {
                this.btnClockIn.setText("퇴근");
                return;
            } else {
                this.btnClockIn.setText("출근중");
                return;
            }
        }
        this.btnClockIn.setText("출근");
    }

    private double getDistance(LatLng from, LatLng to) {
        double dLat = Math.toRadians(to.latitude - from.latitude);
        double dLng = Math.toRadians(to.longitude - from.longitude);
        double a = (Math.sin(dLat / 2.0d) * Math.sin(dLat / 2.0d)) + (Math.cos(Math.toRadians(from.latitude)) * Math.cos(Math.toRadians(to.latitude)) * Math.sin(dLng / 2.0d) * Math.sin(dLng / 2.0d));
        double c = Math.atan2(Math.sqrt(a), Math.sqrt(1.0d - a)) * 2.0d;
        return 6371000.0d * c;
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        if (this.mapView != null) {
            this.mapView.onStart();
        }
        checkLocationPermission();
        checkLocationService();
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (this.mapView != null) {
            this.mapView.onResume();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        if (this.mapView != null) {
            this.mapView.onPause();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        if (this.mapView != null) {
            this.mapView.onStop();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        if (this.mapView != null) {
            this.mapView.onDestroy();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (this.mapView != null) {
            this.mapView.onSaveInstanceState(outState);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRecentAttendance() {
        if (this.employeeId == null || this.employeeName == null) {
            Log.e("CommutingRegistration", "Cannot update attendance: Employee info is null");
            this.recentEntry.setText("최근 출근 기록 없음");
            this.recentDeparture.setText("최근 퇴근 기록 없음");
        } else {
            ApiService apiService = ApiClient.getApiService();
            Call<CommuteModel> call = apiService.getRecentCommute(this.employeeId, this.employeeName);
            call.enqueue(new Callback<CommuteModel>() { // from class: com.example.mostin.fragments.CommutingRegistrationFragment.4
                @Override // retrofit2.Callback
                public void onResponse(Call<CommuteModel> call2, Response<CommuteModel> response) {
                    if (!response.isSuccessful() || response.body() == null) {
                        CommutingRegistrationFragment.this.recentEntry.setText("최근 출근 기록 없음");
                        CommutingRegistrationFragment.this.recentDeparture.setText("최근 퇴근 기록 없음");
                        return;
                    }
                    CommuteModel recentCommute = response.body();
                    String startTime = recentCommute.getStartTime();
                    String endTime = recentCommute.getEndTime();
                    if (startTime == null || startTime.isEmpty()) {
                        CommutingRegistrationFragment.this.recentEntry.setText("최근 출근 기록 없음");
                    } else {
                        CommutingRegistrationFragment.this.recentEntry.setText("최근 출근 " + recentCommute.getCommuteDay() + " " + startTime);
                    }
                    if (endTime == null || endTime.isEmpty()) {
                        CommutingRegistrationFragment.this.recentDeparture.setText("최근 퇴근 기록 없음");
                    } else {
                        CommutingRegistrationFragment.this.recentDeparture.setText("최근 퇴근 " + recentCommute.getCommuteDay() + " " + endTime);
                    }
                }

                @Override // retrofit2.Callback
                public void onFailure(Call<CommuteModel> call2, Throwable t) {
                    Log.e("CommutingRegistration", "Error fetching recent commute", t);
                    Toast.makeText(CommutingRegistrationFragment.this.getContext(), "최근 출퇴근 기록을 가져오는 데 실패했습니다.", 0).show();
                }
            });
        }
    }

    private void loadWorkplaceLocation() {
        if (this.workPlaceName == null || this.workPlaceName.isEmpty()) {
            Log.e("CommutingRegistration", "Workplace name is null or empty");
            return;
        }
        ApiService apiService = ApiClient.getApiService();
        Call<WorkPlace> call = apiService.getWorkPlaceByName(this.workPlaceName);
        call.enqueue(new AnonymousClass5());
    }

    /* renamed from: com.example.mostin.fragments.CommutingRegistrationFragment$5, reason: invalid class name */
    class AnonymousClass5 implements Callback<WorkPlace> {
        AnonymousClass5() {
        }

        @Override // retrofit2.Callback
        public void onResponse(Call<WorkPlace> call, Response<WorkPlace> response) {
            if (!response.isSuccessful() || response.body() == null) {
                Log.e("CommutingRegistration", "No location data found for workplace: " + CommutingRegistrationFragment.this.workPlaceName);
                return;
            }
            WorkPlace workplace = response.body();
            double latitude = workplace.getLatitude();
            double longitude = workplace.getLongitude();
            Log.d("CommutingRegistration", "Found location: " + latitude + ", " + longitude);
            final LatLng workplaceLocation = new LatLng(latitude, longitude);
            Marker marker = new Marker();
            marker.setPosition(workplaceLocation);
            marker.setMap(CommutingRegistrationFragment.this.naverMap);
            marker.setWidth(80);
            marker.setHeight(110);
            marker.setCaptionText(CommutingRegistrationFragment.this.workPlaceName);
            marker.setCaptionTextSize(16.0f);
            CameraUpdate cameraUpdate = CameraUpdate.scrollAndZoomTo(workplaceLocation, 15.0d).animate(CameraAnimation.Easing, 1000L);
            CommutingRegistrationFragment.this.naverMap.moveCamera(cameraUpdate);
            marker.setOnClickListener(new Overlay.OnClickListener() { // from class: com.example.mostin.fragments.CommutingRegistrationFragment$5$$ExternalSyntheticLambda0
                @Override // com.naver.maps.map.overlay.Overlay.OnClickListener
                public final boolean onClick(Overlay overlay) {
                    return this.f$0.m118xf397bb4c(workplaceLocation, overlay);
                }
            });
        }

        /* renamed from: lambda$onResponse$0$com-example-mostin-fragments-CommutingRegistrationFragment$5, reason: not valid java name */
        /* synthetic */ boolean m118xf397bb4c(LatLng workplaceLocation, Overlay overlay) {
            CameraUpdate clickUpdate = CameraUpdate.scrollTo(workplaceLocation).animate(CameraAnimation.Fly, 500L);
            CommutingRegistrationFragment.this.naverMap.moveCamera(clickUpdate);
            return true;
        }

        @Override // retrofit2.Callback
        public void onFailure(Call<WorkPlace> call, Throwable t) {
            Log.e("CommutingRegistration", "Error loading workplace location", t);
        }
    }

    private String getWorkplaceName() {
        Bundle args = getArguments();
        return args != null ? args.getString(ARG_WORK_PLACE_NAME, "") : "";
    }

    private void checkLocationPermission() {
        if (requireActivity().checkSelfPermission("android.permission.ACCESS_FINE_LOCATION") != 0 || requireActivity().checkSelfPermission("android.permission.ACCESS_COARSE_LOCATION") != 0) {
            requestPermissions(new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"}, 1000);
        }
    }

    private void checkLocationService() {
        LocationManager locationManager = (LocationManager) requireContext().getSystemService("location");
        boolean gpsEnabled = locationManager.isProviderEnabled("gps");
        boolean networkEnabled = locationManager.isProviderEnabled("network");
        if (!gpsEnabled && !networkEnabled) {
            new AlertDialog.Builder(requireContext()).setMessage("위치 서비스가 비활성화되어 있습니다. 설정에서 위치 서비스를 켜주세요.").setPositiveButton("설정", new DialogInterface.OnClickListener() { // from class: com.example.mostin.fragments.CommutingRegistrationFragment$$ExternalSyntheticLambda1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.m116x5251f042(dialogInterface, i);
                }
            }).setNegativeButton("취소", (DialogInterface.OnClickListener) null).show();
        }
    }

    /* renamed from: lambda$checkLocationService$1$com-example-mostin-fragments-CommutingRegistrationFragment, reason: not valid java name */
    /* synthetic */ void m116x5251f042(DialogInterface dialog, int which) {
        startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
    }
}
