package com.example.mostin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapSdk;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AlertDialog;

public class CommutingRegistrationFragment extends Fragment implements OnMapReadyCallback {
    private MapView mapView;
    private NaverMap naverMap;
    private Button btnClockIn; // 출근 버튼
    private SQLiteHelper dbHelper; // SQLiteHelper 인스턴스
    private static final int PERMISSION_REQUEST_CODE =1000;
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private FusedLocationSource locationSource;

    private boolean isWorking = false;
    private long clockInTime = 0;
    private static final long WORK_DURATION = 8 * 60 * 60 * 1000;

    private TextView recentEntry;
    private TextView recentDeparture;
    private TextView workPlaceText;

    private String employeeId;    // 로그인한 사용자 ID
    private String employeeName;  // 로그인한 사용자 이름
    private String workPlaceName; // 근무지 이름

    public CommutingRegistrationFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_commuting_registration, container, false);

        // SQLiteHelper 초기화
        dbHelper = new SQLiteHelper(requireContext());

        // TextView 초기화
        recentEntry = view.findViewById(R.id.recentEntry);
        recentDeparture = view.findViewById(R.id.recentDeparture);
        workPlaceText = view.findViewById(R.id.commuting_work_place);

        // 로그인 시 전달받은 사용자 정보 가져오기
        Bundle args = getArguments();
        if (args != null) {
            employeeId = args.getString("employee_id");
            employeeName = args.getString("employee_name");
            workPlaceName = args.getString("work_place_name");
            
            Log.d("CommutingRegistration", "Received data - ID: " + employeeId 
                + ", Name: " + employeeName 
                + ", Workplace: " + workPlaceName);

            if (employeeId != null && employeeName != null && workPlaceName != null) {
                // 근무지 이름 표시
                workPlaceText.setText(workPlaceName);
                
                // 사용자 정보가 있을 때만 출퇴근 관련 기능 초기화
                checkWorkingStatus();
                updateRecentAttendance();
            } else {
                Log.e("CommutingRegistration", "Some user data is null");
                Toast.makeText(requireContext(), "사용자 정보�� 불완전합니다.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.e("CommutingRegistration", "No arguments received");
            Toast.makeText(requireContext(), "사용자 정보를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show();
        }

        // 출근 버튼 초기화
        btnClockIn = view.findViewById(R.id.btn_clock_in);
        btnClockIn.setText("출근");
        btnClockIn.setOnClickListener(v -> handleWorkButton());

        // NaverMapSdk 초기화
        NaverMapSdk.getInstance(requireContext()).setClient(
                new NaverMapSdk.NaverCloudPlatformClient("s7yxuuh84o")
        );

        // 위치 추적을 위한 FusedLocationSource 초기화
        locationSource = new FusedLocationSource(this, PERMISSION_REQUEST_CODE);

        // MapView 초기화
        mapView = view.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;
        
        // 위치 소스 설정
        naverMap.setLocationSource(locationSource);
        
        // UI 설정
        naverMap.getUiSettings().setLocationButtonEnabled(true); // 현재 위치 버튼 활성화
        naverMap.getUiSettings().setZoomControlEnabled(true);    // 줌 컨트롤 활성화
        
        // 위치 추적 모드 설정
        naverMap.setLocationTrackingMode(LocationTrackingMode.NoFollow);

        // 현재 위치 표시 관련 설정
        naverMap.setIndoorEnabled(true);  // 실내 지도 활성화
        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_BUILDING, true);  // 건물 표시
        
        // 근무지 위치 로드 및 마커 표시
        loadWorkplaceLocation();

        // 위치 변경 리스너 설정
        naverMap.addOnLocationChangeListener(location -> {
            if (location != null && workPlaceName != null) {
                LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                
                // 근무지 위치 가져오기
                SQLiteHelper dbHelper = new SQLiteHelper(getContext());
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                String query = "SELECT latitude, longitude FROM " + SQLiteHelper.TABLE_WORK_PLACE + 
                              " WHERE work_place_name = ?";
                
                try {
                    Cursor cursor = db.rawQuery(query, new String[]{workPlaceName});
                    
                    if (cursor != null && cursor.moveToFirst()) {
                        double workLat = cursor.getDouble(cursor.getColumnIndexOrThrow("latitude"));
                        double workLng = cursor.getDouble(cursor.getColumnIndexOrThrow("longitude"));
                        LatLng workLocation = new LatLng(workLat, workLng);
                        
                        // 현재 위치와 근무지 사이의 거리 계산
                        double distance = getDistance(currentLocation, workLocation);
                        
                        // 300m 이내일 때만 출근 버튼 활성화
                        btnClockIn.setVisibility(distance <= 300 ? View.VISIBLE : View.GONE);
                        
                        Log.d("Location", "Distance to workplace: " + distance + "m");
                    }
                    if (cursor != null) {
                        cursor.close();
                    }
                } catch (Exception e) {
                    Log.e("Location", "Error querying workplace location: " + e.getMessage());
                } finally {
                    db.close();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                if (locationAccepted) {
                    // 권한이 허용됨
                    if (naverMap != null) {
                        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
                    }
                } else {
                    // 권한이 거부됨
                    Toast.makeText(requireContext(), 
                        "위치 권한이 필요합니다. 설정에서 권한을 허용해주세요.", 
                        Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void checkWorkingStatus() {
        // employeeId와 employeeName이 null이 아닌지 확인
        if (employeeId == null || employeeName == null) {
            Log.e("CommutingRegistration", "Employee info is null. ID: " + employeeId + ", Name: " + employeeName);
            return;
        }

        Cursor cursor = dbHelper.getTodayCommute(employeeId, employeeName);
        
        if (cursor != null && cursor.moveToFirst()) {
            String startTime = cursor.getString(cursor.getColumnIndexOrThrow("start_time"));
            String endTime = cursor.getString(cursor.getColumnIndexOrThrow("end_time"));
            
            if (startTime != null && endTime == null) {
                isWorking = true;
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                    String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                    Date clockIn = sdf.parse(currentDate + " " + startTime);
                    clockInTime = clockIn.getTime();
                    updateButtonStatus();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            cursor.close();
        }
    }

    private void handleWorkButton() {
        if (!isWorking) {
            // 출근 처리
            clockIn();
        } else {
            // 근무 중인 경우
            long currentTime = System.currentTimeMillis();
            long workedTime = currentTime - clockInTime;
            
            if (workedTime >= WORK_DURATION) {
                // 8시간 이상 근무한 경우 퇴근 처리
                clockOut();
            } else {
                // 남은 시간 계산 및 표시
                long remainingTime = WORK_DURATION - workedTime;
                long remainingHours = remainingTime / (60 * 60 * 1000);
                long remainingMinutes = (remainingTime % (60 * 60 * 1000)) / (60 * 1000);
                
                Toast.makeText(requireContext(), 
                    String.format("근무 종료까지 %d시간 %d분 남았습니다", remainingHours, remainingMinutes),
                    Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void clockIn() {
        // 현재 시간 가져오기
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        
        // DB에 출근 기록 저장
        dbHelper.saveCommute(employeeId, employeeName, workPlaceName);
        
        // UI 업데이트
        isWorking = true;
        clockInTime = System.currentTimeMillis();
        updateButtonStatus();
        
        // 최근 출근 시간 텍스트 즉시 업데이트
        recentEntry.setText("최근 출근 " + currentDate + " " + currentTime);
        recentDeparture.setText("최근 퇴근 기록 없음");
        
        Toast.makeText(requireContext(), "출근이 등록되었습니다!", Toast.LENGTH_SHORT).show();
    }

    private void clockOut() {
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        dbHelper.updateCommuteEndTime(employeeId, employeeName, currentDate);
        
        updateRecentAttendance();
        Toast.makeText(requireContext(), "퇴근이 등록되었습니다!", Toast.LENGTH_SHORT).show();

        Fragment calendarFragment = new AttendanceCalendarFragment();
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, calendarFragment)
                .addToBackStack(null)
                .commit();
    }

    private void updateButtonStatus() {
        if (isWorking) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - clockInTime >= WORK_DURATION) {
                btnClockIn.setText("퇴근");
            } else {
                btnClockIn.setText("출근중");
            }
        } else {
            btnClockIn.setText("출근");
        }
    }

    private double getDistance(LatLng from, LatLng to) {
        // 두 지점 사이 거리 ���산 (Haversine 공식 사용)
        double earthRadius = 6371000; // meters
        double dLat = Math.toRadians(to.latitude - from.latitude);
        double dLng = Math.toRadians(to.longitude - from.longitude);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(from.latitude)) * Math.cos(Math.toRadians(to.latitude)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c;
    }
    @Override
    public void onStart() {
        super.onStart();
        if (mapView != null) {
            mapView.onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null) {
            mapView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mapView != null) {
            mapView.onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mapView != null) {
            mapView.onStop();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mapView != null) {
            mapView.onDestroy();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mapView != null) {
            mapView.onSaveInstanceState(outState);
        }
    }

    private void updateRecentAttendance() {
        // employeeId와 employeeName이 null이 아닌지 확인
        if (employeeId == null || employeeName == null) {
            Log.e("CommutingRegistration", "Cannot update attendance: Employee info is null");
            recentEntry.setText("최근 출근 기록 없음");
            recentDeparture.setText("최근 퇴근 기록 없음");
            return;
        }

        String[] recentRecords = dbHelper.getRecentCommute(employeeId, employeeName);
        
        if (recentRecords[0] != null && !recentRecords[0].isEmpty()) {
            recentEntry.setText("최근 출근 " + recentRecords[0]);
        } else {
            recentEntry.setText("최근 출근 기록 없음");
        }
        
        if (recentRecords[1] != null && !recentRecords[1].isEmpty()) {
            recentDeparture.setText("최근 퇴근 " + recentRecords[1]);
        } else {
            recentDeparture.setText("최근 퇴근 기록 없음");
        }
    }

    private void loadWorkplaceLocation() {
        if (workPlaceName == null || workPlaceName.isEmpty()) {
            Log.e("CommutingRegistration", "Workplace name is null or empty");
            return;
        }

        SQLiteHelper dbHelper = new SQLiteHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        
        Log.d("CommutingRegistration", "Loading workplace location for: " + workPlaceName);
        
        String query = "SELECT latitude, longitude FROM " + SQLiteHelper.TABLE_WORK_PLACE + 
                      " WHERE work_place_name = ?";
        
        try {
            Cursor cursor = db.rawQuery(query, new String[]{workPlaceName});
            
            if (cursor != null && cursor.moveToFirst()) {
                double latitude = cursor.getDouble(cursor.getColumnIndexOrThrow("latitude"));
                double longitude = cursor.getDouble(cursor.getColumnIndexOrThrow("longitude"));
                
                Log.d("CommutingRegistration", "Found location: " + latitude + ", " + longitude);
                
                // 네이버 지도에 마커 추가
                LatLng workplaceLocation = new LatLng(latitude, longitude);
                Marker marker = new Marker();
                marker.setPosition(workplaceLocation);
                marker.setMap(naverMap);
                marker.setWidth(80);
                marker.setHeight(110);
                marker.setCaptionText(workPlaceName);
                marker.setCaptionTextSize(16);
                
                // 카메라 위치 이동 및 줌 설정
                CameraUpdate cameraUpdate = CameraUpdate.scrollAndZoomTo(
                    workplaceLocation,  // 마커 위치
                    15                  // 줌 레벨 (1-20, 값이 클수록 더 가까이 보임)
                )
                .animate(CameraAnimation.Easing, 1000);  // 1초 동안 부드럽게 이동
                
                naverMap.moveCamera(cameraUpdate);
                
                // 마커 클릭 이벤트 추가
                marker.setOnClickListener(overlay -> {
                    CameraUpdate clickUpdate = CameraUpdate.scrollTo(workplaceLocation)
                        .animate(CameraAnimation.Fly, 500);
                    naverMap.moveCamera(clickUpdate);
                    return true;
                });
            } else {
                Log.e("CommutingRegistration", "No location data found for workplace: " + workPlaceName);
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("CommutingRegistration", "Error loading workplace location: " + e.getMessage());
        } finally {
            db.close();
        }
    }

    // 사용자의 근무지 이름을 가��오는 메소드
    private String getWorkplaceName() {
        Bundle args = getArguments();
        if (args != null) {
            return args.getString("work_place_name", "");
        }
        return "";
    }

    private void checkLocationPermission() {
        if (requireActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED ||
            requireActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // 권한 요청
            requestPermissions(
                new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                },
                PERMISSION_REQUEST_CODE
            );
        }
    }

    private void checkLocationService() {
        LocationManager locationManager = (LocationManager) requireContext().getSystemService(Context.LOCATION_SERVICE);
        boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!gpsEnabled && !networkEnabled) {
            // 위치 서비스가 비활성화된 경우
            new AlertDialog.Builder(requireContext())
                .setMessage("위치 서비스가 비활성화되어 있습니다. 설정에서 위치 서비스를 켜주세요.")
                .setPositiveButton("설정", (dialog, which) -> {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                })
                .setNegativeButton("취소", null)
                .show();
        }
    }

}