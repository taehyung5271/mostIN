package com.example.mostin.utils;

/**
 * 키보드 상태 변화를 감지하는 콜백 인터페이스
 * 
 * windowSoftInputMode="adjustResize"와 OnGlobalLayoutListener를 활용하여
 * 키보드의 표시/숨김 상태와 정확한 높이를 감지합니다.
 * 
 * @author Senior Android Developer
 */
public interface KeyboardStateListener {
    
    /**
     * 키보드가 나타날 때 호출됩니다.
     * 
     * @param keyboardHeight 키보드의 높이 (픽셀 단위)
     */
    void onKeyboardShown(int keyboardHeight);
    
    /**
     * 키보드가 사라질 때 호출됩니다.
     * 
     * @param previousKeyboardHeight 이전 키보드 높이 (픽셀 단위)
     */
    void onKeyboardHidden(int previousKeyboardHeight);
    
    /**
     * 키보드 높이가 변경될 때 호출됩니다 (IME 변경, 화면 회전 등).
     * 
     * @param oldHeight 이전 키보드 높이 (픽셀 단위)
     * @param newHeight 새로운 키보드 높이 (픽셀 단위)
     */
    void onKeyboardHeightChanged(int oldHeight, int newHeight);
}