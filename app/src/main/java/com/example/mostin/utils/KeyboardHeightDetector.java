package com.example.mostin.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.util.Log;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * OnGlobalLayoutListener를 사용하여 키보드 높이를 실시간으로 감지하는 유틸리티 클래스
 * 
 * 이 클래스는 다음과 같은 원리로 동작합니다:
 * 1. windowSoftInputMode="adjustResize" 설정 필요
 * 2. rootView.height와 visibleFrame.bottom의 차이로 키보드 높이 계산
 * 3. 키보드 상태 변화 시 등록된 리스너들에게 콜백 호출
 * 
 * 사용법:
 * <pre>
 * // Activity나 Fragment에서 사용
 * KeyboardHeightDetector detector = new KeyboardHeightDetector(activity);
 * detector.addKeyboardStateListener(new KeyboardStateListener() {
 *     @Override
 *     public void onKeyboardShown(int keyboardHeight) {
 *         // 키보드가 나타났을 때 처리
 *     }
 *     
 *     @Override
 *     public void onKeyboardHidden(int previousKeyboardHeight) {
 *         // 키보드가 숨겨졌을 때 처리
 *     }
 *     
 *     @Override
 *     public void onKeyboardHeightChanged(int oldHeight, int newHeight) {
 *         // 키보드 높이가 변경되었을 때 처리
 *     }
 * });
 * 
 * // 감지 시작
 * detector.start();
 * 
 * // 사용이 끝나면 반드시 정리
 * detector.stop();
 * </pre>
 * 
 * @author Senior Android Developer
 */
public class KeyboardHeightDetector {
    private static final String TAG = "KeyboardHeightDetector";
    
    /** 키보드로 인식하는 최소 높이 (픽셀) */
    private static final int MIN_KEYBOARD_HEIGHT_THRESHOLD = 200;
    
    /** 키보드 높이 변화로 인식하는 최소 차이값 (픽셀) */
    private static final int HEIGHT_CHANGE_THRESHOLD = 50;
    
    private final Activity activity;
    private final View rootView;
    private final CopyOnWriteArrayList<KeyboardStateListener> listeners;
    
    // 키보드 상태 추적 변수들
    private boolean isKeyboardVisible = false;
    private int currentKeyboardHeight = 0;
    private int lastKeyboardHeight = 0;
    
    private ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener;
    private boolean isDetectionActive = false;
    
    /**
     * KeyboardHeightDetector 생성자
     * 
     * @param activity 키보드를 감지할 Activity (windowSoftInputMode="adjustResize" 설정 필요)
     */
    public KeyboardHeightDetector(Activity activity) {
        this.activity = activity;
        this.rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        this.listeners = new CopyOnWriteArrayList<>();
        
        initializeGlobalLayoutListener();
    }
    
    /**
     * GlobalLayoutListener 초기화
     * rootView의 높이 변화를 감지하여 키보드 높이를 계산합니다.
     */
    private void initializeGlobalLayoutListener() {
        globalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                calculateKeyboardHeight();
            }
        };
    }
    
    /**
     * 키보드 높이를 계산하고 상태 변화를 감지합니다.
     * 
     * 계산 방식:
     * - 전체 화면 높이에서 실제 보이는 영역의 높이를 빼면 키보드 높이
     * - windowSoftInputMode="adjustResize"가 설정된 경우에만 정확히 동작
     */
    private void calculateKeyboardHeight() {
        if (rootView == null || activity == null) {
            Log.w(TAG, "rootView 또는 activity가 null입니다. 키보드 감지를 건너뜁니다.");
            return;
        }
        
        Rect visibleFrame = new Rect();
        rootView.getWindowVisibleDisplayFrame(visibleFrame);
        
        int screenHeight = rootView.getHeight();
        int visibleHeight = visibleFrame.height();
        int keyboardHeight = screenHeight - visibleHeight;
        
        boolean isKeyboardCurrentlyVisible = keyboardHeight > MIN_KEYBOARD_HEIGHT_THRESHOLD;
        
        Log.d(TAG, String.format("키보드 감지 - 화면높이: %dpx, 가시영역: %dpx, 키보드높이: %dpx, 표시상태: %s", 
                screenHeight, visibleHeight, keyboardHeight, isKeyboardCurrentlyVisible));
        
        // 키보드 표시 상태가 변경된 경우
        if (isKeyboardCurrentlyVisible != isKeyboardVisible) {
            handleKeyboardVisibilityChange(isKeyboardCurrentlyVisible, keyboardHeight);
        }
        // 키보드가 보이는 상태에서 높이만 변경된 경우
        else if (isKeyboardCurrentlyVisible && 
                 Math.abs(keyboardHeight - currentKeyboardHeight) > HEIGHT_CHANGE_THRESHOLD) {
            handleKeyboardHeightChange(currentKeyboardHeight, keyboardHeight);
        }
        
        // 상태 업데이트
        isKeyboardVisible = isKeyboardCurrentlyVisible;
        if (isKeyboardCurrentlyVisible) {
            lastKeyboardHeight = currentKeyboardHeight;
            currentKeyboardHeight = keyboardHeight;
        }
    }
    
    /**
     * 키보드 표시/숨김 상태 변화를 처리합니다.
     * 
     * @param isVisible 현재 키보드가 보이는지 여부
     * @param keyboardHeight 현재 키보드 높이
     */
    private void handleKeyboardVisibilityChange(boolean isVisible, int keyboardHeight) {
        if (isVisible) {
            Log.i(TAG, "키보드가 나타났습니다. 높이: " + keyboardHeight + "px");
            currentKeyboardHeight = keyboardHeight;
            notifyKeyboardShown(keyboardHeight);
        } else {
            Log.i(TAG, "키보드가 숨겨졌습니다. 이전 높이: " + currentKeyboardHeight + "px");
            int previousHeight = currentKeyboardHeight;
            currentKeyboardHeight = 0;
            notifyKeyboardHidden(previousHeight);
        }
    }
    
    /**
     * 키보드 높이 변경을 처리합니다.
     * 
     * @param oldHeight 이전 키보드 높이
     * @param newHeight 새로운 키보드 높이
     */
    private void handleKeyboardHeightChange(int oldHeight, int newHeight) {
        Log.i(TAG, String.format("키보드 높이가 변경되었습니다. %dpx -> %dpx", oldHeight, newHeight));
        currentKeyboardHeight = newHeight;
        notifyKeyboardHeightChanged(oldHeight, newHeight);
    }
    
    /**
     * 키보드 상태 리스너를 추가합니다.
     * 
     * @param listener 추가할 KeyboardStateListener
     */
    public void addKeyboardStateListener(KeyboardStateListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
            Log.d(TAG, "키보드 상태 리스너가 추가되었습니다. 총 " + listeners.size() + "개");
        }
    }
    
    /**
     * 키보드 상태 리스너를 제거합니다.
     * 
     * @param listener 제거할 KeyboardStateListener
     */
    public void removeKeyboardStateListener(KeyboardStateListener listener) {
        if (listener != null && listeners.remove(listener)) {
            Log.d(TAG, "키보드 상태 리스너가 제거되었습니다. 남은 개수: " + listeners.size());
        }
    }
    
    /**
     * 키보드 감지를 시작합니다.
     * Activity의 onCreate나 onResume에서 호출하세요.
     */
    public void start() {
        if (!isDetectionActive && rootView != null && globalLayoutListener != null) {
            rootView.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);
            isDetectionActive = true;
            Log.i(TAG, "키보드 높이 감지가 시작되었습니다.");
        }
    }
    
    /**
     * 키보드 감지를 중지합니다.
     * Activity의 onDestroy나 onPause에서 반드시 호출하세요.
     */
    public void stop() {
        if (isDetectionActive && rootView != null && globalLayoutListener != null) {
            rootView.getViewTreeObserver().removeOnGlobalLayoutListener(globalLayoutListener);
            isDetectionActive = false;
            Log.i(TAG, "키보드 높이 감지가 중지되었습니다.");
        }
    }
    
    /**
     * 현재 키보드가 보이는지 여부를 반환합니다.
     * 
     * @return 키보드가 보이면 true, 그렇지 않으면 false
     */
    public boolean isKeyboardVisible() {
        return isKeyboardVisible;
    }
    
    /**
     * 현재 키보드 높이를 반환합니다.
     * 
     * @return 키보드 높이 (픽셀 단위), 키보드가 보이지 않으면 0
     */
    public int getCurrentKeyboardHeight() {
        return isKeyboardVisible ? currentKeyboardHeight : 0;
    }
    
    /**
     * 마지막으로 기록된 키보드 높이를 반환합니다.
     * 키보드가 현재 보이지 않더라도 이전에 측정된 높이를 반환합니다.
     * 
     * @return 마지막 키보드 높이 (픽셀 단위)
     */
    public int getLastKeyboardHeight() {
        return Math.max(currentKeyboardHeight, lastKeyboardHeight);
    }
    
    // 리스너 알림 메서드들
    private void notifyKeyboardShown(int keyboardHeight) {
        for (KeyboardStateListener listener : listeners) {
            try {
                listener.onKeyboardShown(keyboardHeight);
            } catch (Exception e) {
                Log.e(TAG, "키보드 표시 알림 중 오류 발생", e);
            }
        }
    }
    
    private void notifyKeyboardHidden(int previousKeyboardHeight) {
        for (KeyboardStateListener listener : listeners) {
            try {
                listener.onKeyboardHidden(previousKeyboardHeight);
            } catch (Exception e) {
                Log.e(TAG, "키보드 숨김 알림 중 오류 발생", e);
            }
        }
    }
    
    private void notifyKeyboardHeightChanged(int oldHeight, int newHeight) {
        for (KeyboardStateListener listener : listeners) {
            try {
                listener.onKeyboardHeightChanged(oldHeight, newHeight);
            } catch (Exception e) {
                Log.e(TAG, "키보드 높이 변경 알림 중 오류 발생", e);
            }
        }
    }
    
    /**
     * 현재 상태를 로그로 출력합니다. (디버깅용)
     */
    public void logCurrentState() {
        Log.d(TAG, String.format("키보드 상태 - 표시: %s, 현재높이: %dpx, 마지막높이: %dpx, 리스너수: %d, 감지활성: %s",
                isKeyboardVisible, currentKeyboardHeight, lastKeyboardHeight, 
                listeners.size(), isDetectionActive));
    }
}