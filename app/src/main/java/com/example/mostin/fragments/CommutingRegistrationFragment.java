package com.example.mostin.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.widget.NestedScrollView;
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
import com.naver.maps.map.util.FusedLocationSource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommutingRegistrationFragment extends Fragment implements OnMapReadyCallback {
    public static final String ARG_EMPLOYEE_ID = "employee_id";
    public static final String ARG_EMPLOYEE_NAME = "employee_name";
    public static final String ARG_WORK_PLACE_NAME = "work_place_name";
    private static final String TAG = "CommutingDebug";
    private static final int PERMISSION_REQUEST_CODE = 1000;
    private static final long WORK_DURATION_MS = 28800000; // 8 hours
    private static final SimpleDateFormat DATE_FORMAT;
    static {
        DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
        DATE_FORMAT.setTimeZone(java.util.TimeZone.getTimeZone("Asia/Seoul"));
    }
    private static final int MAX_DISTANCE_METERS = 2000;

    private Button btnClockInOut;
    private TextView tvRecentEntry;
    private TextView tvRecentDeparture;
    private TextView tvWorkDuration;
    private TextView tvWorkPlace;
    private TextView tvCurrentTime;
    private TextView tvCurrentDate;
    private TextView tvDistanceText;
    private MapView mapView;
    private NestedScrollView scrollView;

    private ApiService apiService;
    private FusedLocationSource locationSource;
    private NaverMap naverMap;

    private String employeeId;
    private String employeeName;
    private String workPlaceName;

    private boolean isWorking = false;
    private long clockInTime = 0;
    private LatLng workplaceLocation; // 근무지 위치
    private boolean isWorkingStatusChecked = false; // 근무 상태 확인 완료 여부
    
    // 근무시간 실시간 업데이트를 위한 Handler (1분마다 업데이트)
    private Handler workTimeUpdateHandler;
    private Runnable workTimeUpdateRunnable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiService = ApiClient.getApiService();
        locationSource = new FusedLocationSource(this, PERMISSION_REQUEST_CODE);

        Bundle args = getArguments();
        if (args != null) {
            employeeId = args.getString(ARG_EMPLOYEE_ID);
            employeeName = args.getString(ARG_EMPLOYEE_NAME);
            workPlaceName = args.getString(ARG_WORK_PLACE_NAME);
            Log.d(TAG, "onCreate: Arguments received - employeeId: " + employeeId + ", workPlaceName: " + workPlaceName);
        } else {
            Log.e(TAG, "onCreate: Arguments are null!");
        }
        
        // 근무시간 업데이트 Handler 초기화
        initializeWorkTimeUpdateHandler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_commuting_registration, container, false);
        
        // NestedScrollView 참조 저장 (레이아웃의 루트 뷰)
        if (view instanceof NestedScrollView) {
            scrollView = (NestedScrollView) view;
        }
        
        initializeViews(view, savedInstanceState);
        setupListeners(view); // 클릭 리스너 설정 호출 추가
        loadInitialData();
        return view;
    }

    private void setupListeners(View view) {
        if (btnClockInOut != null) {
            btnClockInOut.setOnClickListener(this::handleWorkButtonClick);
            Log.d(TAG, "setupListeners: Click listener set for btnClockInOut");
        } else {
            Log.e(TAG, "setupListeners: btnClockInOut is null, cannot set click listener");
        }
        
        // 위치 새로고침 버튼 리스너 추가
        Button btnRefreshLocation = view.findViewById(R.id.btn_refresh_location);
        if (btnRefreshLocation != null) {
            btnRefreshLocation.setOnClickListener(v -> {
                if (getContext() != null) {
                    Location lastLocation = locationSource.getLastLocation();
                    if (lastLocation != null) {
                        updateDistanceDisplay(lastLocation);
                        tryToUpdateButton();
                        Toast.makeText(getContext(), "위치가 새로고침되었습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "현재 위치를 가져올 수 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void initializeViews(View view, Bundle savedInstanceState) {
        try {
            Log.d(TAG, "initializeViews: Starting view initialization");
            
            tvRecentEntry = view.findViewById(R.id.text_clock_in_time);
            tvRecentDeparture = view.findViewById(R.id.text_clock_out_time);
            tvWorkDuration = view.findViewById(R.id.text_work_duration);
            tvWorkPlace = view.findViewById(R.id.commuting_work_place);
            tvCurrentTime = view.findViewById(R.id.text_current_time);
            tvCurrentDate = view.findViewById(R.id.text_current_date);
            tvDistanceText = view.findViewById(R.id.distance_text);
            btnClockInOut = view.findViewById(R.id.btn_clock_in_out);
            mapView = view.findViewById(R.id.map_view);

            // Null 체크 추가
            if (tvRecentEntry == null) Log.e(TAG, "tvRecentEntry is null!");
            if (tvRecentDeparture == null) Log.e(TAG, "tvRecentDeparture is null!");
            if (tvWorkDuration == null) Log.e(TAG, "tvWorkDuration is null!");
            if (tvWorkPlace == null) Log.e(TAG, "tvWorkPlace is null!");
            if (tvCurrentTime == null) Log.e(TAG, "tvCurrentTime is null!");
            if (tvCurrentDate == null) Log.e(TAG, "tvCurrentDate is null!");
            if (tvDistanceText == null) Log.e(TAG, "tvDistanceText is null!");
            if (btnClockInOut == null) Log.e(TAG, "btnClockInOut is null!");
            if (mapView == null) Log.e(TAG, "mapView is null!");
            if (scrollView == null) Log.e(TAG, "scrollView is null!");

            // 현재 시간과 날짜 설정
            updateCurrentDateTime();

            if (btnClockInOut != null) {
                btnClockInOut.setEnabled(false);
                btnClockInOut.setText("위치 확인 중...");
            }

            Log.d(TAG, "initializeViews: Initializing NaverMap SDK");
            if (getContext() != null) {
                NaverMapSdk.getInstance(getContext()).setClient(new NaverMapSdk.NaverCloudPlatformClient("s7yxuuh84o"));
            } else {
                Log.e(TAG, "initializeViews: Context is null, cannot initialize NaverMap SDK");
                return;
            }
            
            if (mapView != null) {
                mapView.onCreate(savedInstanceState);
                mapView.getMapAsync(this);
                Log.d(TAG, "initializeViews: MapView setup completed");
            } else {
                Log.e(TAG, "initializeViews: MapView is null, cannot initialize map");
            }
            
        } catch (Exception e) {
            Log.e(TAG, "initializeViews: Exception occurred", e);
            if (getContext() != null) {
                Toast.makeText(getContext(), "지도 초기화 오류: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void updateCurrentDateTime() {
        if (tvCurrentTime != null && tvCurrentDate != null) {
            // Asia/Seoul 시간대로 현재 시간 가져오기
            Calendar calendar = Calendar.getInstance(java.util.TimeZone.getTimeZone("Asia/Seoul"));
            
            // 시간 포맷 (HH:mm)
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.KOREA);
            timeFormat.setTimeZone(java.util.TimeZone.getTimeZone("Asia/Seoul"));
            String currentTime = timeFormat.format(calendar.getTime());
            
            // 날짜 포맷 (yyyy년 M월 d일 E요일)
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 M월 d일 E요일", Locale.KOREA);
            dateFormat.setTimeZone(java.util.TimeZone.getTimeZone("Asia/Seoul"));
            String currentDate = dateFormat.format(calendar.getTime());
            
            tvCurrentTime.setText(currentTime);
            tvCurrentDate.setText(currentDate);
            
            Log.d(TAG, "updateCurrentDateTime: Time=" + currentTime + ", Date=" + currentDate);
        }
    }

    private void loadInitialData() {
        if (workPlaceName != null && tvWorkPlace != null) {
            tvWorkPlace.setText(workPlaceName);
            updateRecentAttendance();
        } else {
            Log.e(TAG, "loadInitialData: workPlaceName or tvWorkPlace is null. workPlaceName=" + workPlaceName + ", tvWorkPlace=" + tvWorkPlace);
        }
    }

    @Override
    public void onMapReady(NaverMap naverMap) {
        Log.d(TAG, "onMapReady: Naver Map is ready.");
        this.naverMap = naverMap;
        naverMap.setLocationSource(locationSource);
        naverMap.getUiSettings().setLocationButtonEnabled(true);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);

        naverMap.addOnLocationChangeListener(location -> {
            Log.d(TAG, "onLocationChange: New location -> " + location.getLatitude() + ", " + location.getLongitude());
            updateDistanceDisplay(location);
            tryToUpdateButton();
        });

        // 지도뷰에 터치 리스너 설정하여 스크롤 충돌 해결
        if (mapView != null) {
            mapView.setOnTouchListener((v, event) -> {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        // 지도 터치 시 부모 스크롤 막기
                        if (scrollView != null) {
                            scrollView.requestDisallowInterceptTouchEvent(true);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        // 지도 터치 종료 시 부모 스크롤 허용
                        if (scrollView != null) {
                            scrollView.requestDisallowInterceptTouchEvent(false);
                        }
                        break;
                }
                return false; // 이벤트를 다른 리스너로 전달
            });
        }

        // Load data after map is ready
        loadWorkplaceLocation();
        checkWorkingStatus();
    }

    private void checkWorkingStatus() {
        if (employeeId == null) {
            Log.w(TAG, "checkWorkingStatus: Employee ID is null.");
            return;
        }
        Log.d(TAG, "checkWorkingStatus: Checking working status for " + employeeId);
        apiService.getRecentCommute(employeeId, employeeName).enqueue(new Callback<CommuteModel>() {
            @Override
            public void onResponse(Call<CommuteModel> call, Response<CommuteModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CommuteModel recentCommute = response.body();
                    if (recentCommute.getStartTime() != null && recentCommute.getEndTime() == null) {
                        String todayStr = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(new Date());
                        if (todayStr.equals(recentCommute.getCommuteDay())) {
                            isWorking = true;
                            try {
                                Date clockIn = DATE_FORMAT.parse(recentCommute.getCommuteDay() + " " + recentCommute.getStartTime());
                                clockInTime = Objects.requireNonNull(clockIn).getTime();
                                
                                // 이미 근무 중이면 근무시간 실시간 업데이트 시작 (1분마다)
                                startWorkTimeUpdate();
                            } catch (ParseException | NullPointerException e) {
                                isWorking = false;
                            }
                        } else {
                            isWorking = false;
                        }
                    } else {
                        isWorking = false;
                    }
                } else {
                    isWorking = false;
                }
                isWorkingStatusChecked = true;
                Log.d(TAG, "checkWorkingStatus: Finished. isWorking=" + isWorking);
                tryToUpdateButton();
            }

            @Override
            public void onFailure(Call<CommuteModel> call, Throwable t) {
                Log.e(TAG, "checkWorkingStatus: onFailure", t);
                isWorking = false;
                isWorkingStatusChecked = true;
                tryToUpdateButton();
            }
        });
    }

    private void loadWorkplaceLocation() {
        if (workPlaceName == null) {
            Log.e(TAG, "loadWorkplaceLocation: workPlaceName is null.");
            return;
        }
        Log.d(TAG, "loadWorkplaceLocation: Loading location for " + workPlaceName);
        apiService.getWorkPlaceByName(workPlaceName).enqueue(new Callback<WorkPlace>() {
            @Override
            public void onResponse(Call<WorkPlace> call, Response<WorkPlace> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WorkPlace workplace = response.body();
                    workplaceLocation = new LatLng(workplace.getLatitude(), workplace.getLongitude());
                    Log.d(TAG, "loadWorkplaceLocation: Location found: " + workplaceLocation);
                    setupMapMarker(workplaceLocation);
                    
                    // 근무지 위치가 로드된 후 현재 위치와의 거리 업데이트
                    Location lastLocation = locationSource.getLastLocation();
                    if (lastLocation != null) {
                        updateDistanceDisplay(lastLocation);
                    }
                } else {
                    Log.e(TAG, "loadWorkplaceLocation: Could not find location.");
                    if (getContext() != null) {
                        Toast.makeText(getContext(), "근무지 위치를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
                tryToUpdateButton();
            }

            @Override
            public void onFailure(Call<WorkPlace> call, Throwable t) {
                Log.e(TAG, "loadWorkplaceLocation: onFailure", t);
                if (getContext() != null) {
                    Toast.makeText(getContext(), "근무지 위치 로딩 중 오류 발생", Toast.LENGTH_SHORT).show();
                }
                tryToUpdateButton();
            }
        });
    }

    private void tryToUpdateButton() {
        if (isWorkingStatusChecked && workplaceLocation != null) {
            Log.d(TAG, "tryToUpdateButton: All conditions met. Updating button status.");
            updateButtonStatus();
        } else {
            Log.d(TAG, "tryToUpdateButton: Conditions not met. isWorkingStatusChecked=" + isWorkingStatusChecked + ", workplaceLocation=" + workplaceLocation);
        }
    }

    private void updateButtonStatus() {
        if (getActivity() == null) return; // Prevent crash if fragment is detached

        getActivity().runOnUiThread(() -> {
            btnClockInOut.setVisibility(View.VISIBLE); // 항상 버튼을 보이도록 설정
            Location lastLocation = locationSource.getLastLocation();
            Log.d(TAG, "updateButtonStatus: isWorking=" + isWorking + ", lastLocation=" + lastLocation);

            if (isWorking) {
                btnClockInOut.setEnabled(true);
                long workedTime = System.currentTimeMillis() - clockInTime;
                if (workedTime >= WORK_DURATION_MS) {
                    btnClockInOut.setText("퇴근");
                } else {
                    btnClockInOut.setText("출근중");
                }
                
                // Update work duration in real-time while working
                if (tvWorkDuration != null) {
                    String formattedDuration = formatWorkDuration(workedTime);
                    tvWorkDuration.setText(formattedDuration);
                }
            }
            else {
                if (lastLocation != null) {
                    LatLng currentUserLocation = new LatLng(lastLocation);
                    float distance = calculateDistance(currentUserLocation, workplaceLocation);
                    Log.d(TAG, "updateButtonStatus: Distance to workplace: " + distance + "m");
                    if (distance <= MAX_DISTANCE_METERS) {
                        btnClockInOut.setText("출근");
                        btnClockInOut.setEnabled(true);
                    } else {
                        btnClockInOut.setText("근무지에서 벗어남");
                        btnClockInOut.setEnabled(false);
                    }
                } else {
                    btnClockInOut.setText("위치 확인 중...");
                    btnClockInOut.setEnabled(false);
                }
            }
        });
    }

    private void updateDistanceDisplay(Location currentLocation) {
        if (currentLocation != null && workplaceLocation != null && tvDistanceText != null) {
            LatLng currentLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            float distance = calculateDistance(currentLatLng, workplaceLocation);
            
            String distanceText;
            if (distance < 1000) {
                // 1km 미만이면 미터 단위로 표시
                distanceText = String.format(Locale.KOREA, "현재 위치에서 %.0fm", distance);
            } else {
                // 1km 이상이면 킬로미터 단위로 표시
                distanceText = String.format(Locale.KOREA, "현재 위치에서 %.1fkm", distance / 1000);
            }
            
            tvDistanceText.setText(distanceText);
            Log.d(TAG, "updateDistanceDisplay: " + distanceText);
        }
    }

    private float calculateDistance(LatLng p1, LatLng p2) {
        if (p1 == null || p2 == null) return Float.MAX_VALUE;
        Location loc1 = new Location("");
        loc1.setLatitude(p1.latitude);
        loc1.setLongitude(p1.longitude);
        Location loc2 = new Location("");
        loc2.setLatitude(p2.latitude);
        loc2.setLongitude(p2.longitude);
        return loc1.distanceTo(loc2);
    }

    // Other methods (clockIn, clockOut, updateRecentAttendance, etc.) remain the same

    private void setupMapMarker(LatLng location) {
        Marker marker = new Marker();
        marker.setPosition(location);
        marker.setMap(naverMap);
        marker.setWidth(80);
        marker.setHeight(110);
        marker.setCaptionText(workPlaceName);
        marker.setCaptionTextSize(16.0f);

        CameraUpdate cameraUpdate = CameraUpdate.scrollAndZoomTo(location, 15.0).animate(CameraAnimation.Easing, 1000);
        naverMap.moveCamera(cameraUpdate);
    }

    private void handleWorkButtonClick(View view) {
        if (!isWorking) {
            clockIn();
        } else {
            long workedTime = System.currentTimeMillis() - clockInTime;
            if (workedTime >= WORK_DURATION_MS) {
                clockOut();
            } else {
                long remainingTime = WORK_DURATION_MS - workedTime;
                long remainingHours = remainingTime / 3600000;
                long remainingMinutes = (remainingTime % 3600000) / 60000;
                Toast.makeText(requireContext(), String.format("근무 종료까지 %d시간 %d분 남았습니다.", remainingHours, remainingMinutes), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void clockIn() {
        Map<String, String> payload = new HashMap<>();
        payload.put("employeeId", employeeId);
        payload.put("employeeName", employeeName);
        payload.put("workPlaceName", workPlaceName);

        // Asia/Seoul 시간으로 현재 시간 설정
        Date now = new Date();
        java.util.TimeZone seoulTimeZone = java.util.TimeZone.getTimeZone("Asia/Seoul");

        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        dayFormat.setTimeZone(seoulTimeZone);

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.KOREA);
        timeFormat.setTimeZone(seoulTimeZone);

        payload.put("commuteDay", dayFormat.format(now));
        payload.put("startTime", timeFormat.format(now));

        apiService.clockIn(payload).enqueue(new Callback<CommuteModel>() {
            @Override
            public void onResponse(Call<CommuteModel> call, Response<CommuteModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    isWorking = true;
                    CommuteModel newCommute = response.body();
                    try {
                        Date clockIn = DATE_FORMAT.parse(newCommute.getCommuteDay() + " " + newCommute.getStartTime());
                        if (clockIn != null) {
                            clockInTime = clockIn.getTime();
                        }
                    } catch (ParseException e) {
                        clockInTime = System.currentTimeMillis(); // Fallback
                    }
                    
                    // 출근 후 근무시간 실시간 업데이트 시작 (1분마다)
                    startWorkTimeUpdate();
                    
                    updateButtonStatus();
                    updateRecentAttendance(); // Refresh the display
                } else {
                    Toast.makeText(getContext(), "출근 등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommuteModel> call, Throwable t) {
                Log.e(TAG, "Error during clock-in.", t);
                Toast.makeText(getContext(), "출근 등록 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clockOut() {
        Map<String, String> payload = new HashMap<>();
        payload.put("employeeId", employeeId);

        // Asia/Seoul 시간으로 현재 시간 설정
        Date now = new Date();
        java.util.TimeZone seoulTimeZone = java.util.TimeZone.getTimeZone("Asia/Seoul");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.KOREA);
        timeFormat.setTimeZone(seoulTimeZone);
        payload.put("endTime", timeFormat.format(now));


        apiService.clockOut(payload).enqueue(new Callback<CommuteModel>() {
            @Override
            public void onResponse(Call<CommuteModel> call, Response<CommuteModel> response) {
                if (response.isSuccessful()) {
                    isWorking = false;
                    
                    // 퇴근 후 근무시간 실시간 업데이트 중지
                    stopWorkTimeUpdate();
                    
                    updateButtonStatus();
                    updateRecentAttendance();
                    Toast.makeText(getContext(), "퇴근이 등록되었습니다.", Toast.LENGTH_SHORT).show();

                    // 현재 날짜 정보를 Bundle에 담아 전달
                    Bundle bundle = new Bundle();
                    bundle.putString("employee_id", employeeId);
                    bundle.putString("employee_name", employeeName);

                    Calendar cal = Calendar.getInstance(java.util.TimeZone.getTimeZone("Asia/Seoul"));
                    bundle.putInt("year", cal.get(Calendar.YEAR));
                    bundle.putInt("month", cal.get(Calendar.MONTH));

                    AttendanceCalendarFragment calendarFragment = new AttendanceCalendarFragment();
                    calendarFragment.setArguments(bundle);

                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, calendarFragment)
                            .addToBackStack(null)
                            .commit();
                } else {
                    Toast.makeText(getContext(), "퇴근 등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommuteModel> call, Throwable t) {
                Log.e(TAG, "Error during clock-out.", t);
                Toast.makeText(getContext(), "퇴근 등록 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateRecentAttendance() {
        if (employeeId == null || employeeName == null) {
            return;
        }

        apiService.getRecentCommute(employeeId, employeeName).enqueue(new Callback<CommuteModel>() {
            @Override
            public void onResponse(Call<CommuteModel> call, Response<CommuteModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CommuteModel recentCommute = response.body();
                    String startTime = recentCommute.getStartTime();
                    String endTime = recentCommute.getEndTime();

                    // Update clock in time
                    if (startTime == null || startTime.isEmpty()) {
                        tvRecentEntry.setText("-");
                    } else {
                        // Format to "HH:mm" only
                        String displayTime = startTime.substring(0, 5);
                        tvRecentEntry.setText(displayTime);
                    }

                    // Update clock out time
                    if (endTime == null || endTime.isEmpty()) {
                        tvRecentDeparture.setText("-");
                    } else {
                        // Format to "HH:mm" only
                        String displayTime = endTime.substring(0, 5);
                        tvRecentDeparture.setText(displayTime);
                    }

                    // Calculate and update work duration
                    updateWorkDuration(startTime, endTime);
                } else {
                    tvRecentEntry.setText("-");
                    tvRecentDeparture.setText("-");
                    tvWorkDuration.setText("-");
                }
            }

            @Override
            public void onFailure(Call<CommuteModel> call, Throwable t) {
                Log.e(TAG, "Error fetching recent commute.", t);
                tvRecentEntry.setText("-");
                tvRecentDeparture.setText("-");
                tvWorkDuration.setText("-");
            }
        });
    }

    private void updateWorkDuration(String startTime, String endTime) {
        if (tvWorkDuration == null) {
            return;
        }

        if (startTime == null || startTime.isEmpty()) {
            tvWorkDuration.setText("-");
            return;
        }

        if (endTime == null || endTime.isEmpty()) {
            // If still working, calculate current work duration
            if (isWorking) {
                long currentTime = System.currentTimeMillis();
                long workDuration = currentTime - clockInTime;
                String formattedDuration = formatWorkDuration(workDuration);
                tvWorkDuration.setText(formattedDuration);
            } else {
                tvWorkDuration.setText("-");
            }
        } else {
            // Calculate total work duration
            try {
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.KOREA);
                timeFormat.setTimeZone(java.util.TimeZone.getTimeZone("Asia/Seoul"));
                
                Date startDate = timeFormat.parse(startTime);
                Date endDate = timeFormat.parse(endTime);
                
                if (startDate != null && endDate != null) {
                    long workDuration = endDate.getTime() - startDate.getTime();
                    String formattedDuration = formatWorkDuration(workDuration);
                    tvWorkDuration.setText(formattedDuration);
                } else {
                    tvWorkDuration.setText("-");
                }
            } catch (ParseException e) {
                Log.e(TAG, "Error parsing work duration times", e);
                tvWorkDuration.setText("-");
            }
        }
    }

    private String formatWorkDuration(long durationMs) {
        if (durationMs <= 0) {
            return "0h 0m";
        }
        
        long hours = durationMs / 3600000; // Convert to hours
        long minutes = (durationMs % 3600000) / 60000; // Remaining minutes
        
        return String.format(Locale.KOREA, "%dh %dm", hours, minutes);
    }

    private void checkLocationPermission() {
        String[] permissions = {"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"};
        if (requireActivity().checkSelfPermission(permissions[0]) != PackageManager.PERMISSION_GRANTED || requireActivity().checkSelfPermission(permissions[1]) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(permissions, PERMISSION_REQUEST_CODE);
        }
    }

    private void checkLocationService() {
        LocationManager locationManager = (LocationManager) requireContext().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null && !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            new AlertDialog.Builder(requireContext())
                .setTitle("위치 서비스 비활성화")
                .setMessage("앱 사용을 위해 위치 서비스가 필요합니다. 위치 설정을 활성화하시겠습니까?")
                .setPositiveButton("설정으로 이동", (dialog, which) -> {
                    Intent intent = new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
                    startActivity(intent);
                })
                .setNegativeButton("취소", null)
                .show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.length > 0) {
            if (grantResults[0] == 0) {
                if (naverMap != null) {
                    naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
                }
            } else {
                Toast.makeText(requireContext(), "위치 권한이 필요합니다. 설정에서 권한을 허용해주세요.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
        checkLocationPermission();
        checkLocationService();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        // 프래그먼트가 화면에 보일 때마다 현재 시간 업데이트
        updateCurrentDateTime();
    }
    
    /**
     * 근무시간 실시간 업데이트를 위한 Handler 초기화
     * 1분마다 근무시간을 업데이트합니다.
     */
    private void initializeWorkTimeUpdateHandler() {
        workTimeUpdateHandler = new Handler(Looper.getMainLooper());
        workTimeUpdateRunnable = new Runnable() {
            @Override
            public void run() {
                if (isWorking && tvWorkDuration != null) {
                    // 현재 근무시간 계산 및 업데이트
                    long workedTime = System.currentTimeMillis() - clockInTime;
                    String formattedDuration = formatWorkDuration(workedTime);
                    tvWorkDuration.setText(formattedDuration);
                    
                    Log.d(TAG, "Work time updated: " + formattedDuration);
                }
                
                // 1분 후 다시 실행 (60000ms = 1분)
                workTimeUpdateHandler.postDelayed(this, 60000);
            }
        };
    }
    
    /**
     * 근무시간 실시간 업데이트 시작
     */
    private void startWorkTimeUpdate() {
        if (workTimeUpdateHandler != null && workTimeUpdateRunnable != null) {
            Log.d(TAG, "Starting work time update (1 minute interval)");
            workTimeUpdateHandler.post(workTimeUpdateRunnable);
        }
    }
    
    /**
     * 근무시간 실시간 업데이트 중지
     */
    private void stopWorkTimeUpdate() {
        if (workTimeUpdateHandler != null && workTimeUpdateRunnable != null) {
            Log.d(TAG, "Stopping work time update");
            workTimeUpdateHandler.removeCallbacks(workTimeUpdateRunnable);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        
        // Fragment 파괴 시 근무시간 업데이트 Handler 정리
        stopWorkTimeUpdate();
        if (workTimeUpdateHandler != null) {
            workTimeUpdateHandler.removeCallbacksAndMessages(null);
            workTimeUpdateHandler = null;
        }
        
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}