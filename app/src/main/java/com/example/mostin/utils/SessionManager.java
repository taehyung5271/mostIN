package com.example.mostin.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Calendar;

public class SessionManager {
    private static final String TAG = "SessionManager";
    private static final String PREF_NAME = "MostIN_Session";
    private static final String KEY_IS_AUTO_LOGIN_ENABLED = "auto_login_enabled";
    private static final String KEY_EMPLOYEE_ID = "employee_id";
    private static final String KEY_EMPLOYEE_NAME = "employee_name";
    private static final String KEY_EMPLOYEE_TYPE = "employee_type";
    private static final String KEY_WORK_PLACE_NAME = "work_place_name";
    private static final String KEY_LOGIN_TIMESTAMP = "login_timestamp";
    private static final String KEY_ENCRYPTION_KEY = "encryption_key";
    
    // 자동 로그인 유지 기간 (30일)
    private static final long AUTO_LOGIN_DURATION_MS = 30L * 24 * 60 * 60 * 1000;
    
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Context context;
    
    public SessionManager(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
        
        Log.d(TAG, "SessionManager initialized");
        Log.d(TAG, "Auto login enabled: " + prefs.getBoolean(KEY_IS_AUTO_LOGIN_ENABLED, false));
        Log.d(TAG, "Has employee ID: " + prefs.contains(KEY_EMPLOYEE_ID));
        Log.d(TAG, "Has login timestamp: " + prefs.contains(KEY_LOGIN_TIMESTAMP));
        Log.d(TAG, "Has encryption key: " + prefs.contains(KEY_ENCRYPTION_KEY));
        
        // 새 설치인지 확인 - 아무 데이터도 없으면 새 설치
        boolean isNewInstall = !prefs.contains(KEY_IS_AUTO_LOGIN_ENABLED) && 
                              !prefs.contains(KEY_EMPLOYEE_ID) &&
                              !prefs.contains(KEY_LOGIN_TIMESTAMP);
        
        Log.d(TAG, "Is new install: " + isNewInstall);
        
        if (isNewInstall) {
            Log.d(TAG, "New installation detected - ensuring clean state");
            editor.clear();
            editor.putBoolean(KEY_IS_AUTO_LOGIN_ENABLED, false);
            editor.apply();
            Log.d(TAG, "Set AUTO_LOGIN_ENABLED to false for new installation");
        }
        
        // 암호화 키가 없으면 생성
        if (!prefs.contains(KEY_ENCRYPTION_KEY)) {
            generateAndSaveEncryptionKey();
        }
    }
    
    /**
     * 자동 로그인 설정 저장
     */
    public void setAutoLoginEnabled(boolean enabled) {
        editor.putBoolean(KEY_IS_AUTO_LOGIN_ENABLED, enabled);
        editor.apply();
        
        if (!enabled) {
            clearSession();
        }
    }
    
    /**
     * 자동 로그인 설정 확인
     */
    public boolean isAutoLoginEnabled() {
        boolean enabled = prefs.getBoolean(KEY_IS_AUTO_LOGIN_ENABLED, false);
        Log.d(TAG, "isAutoLoginEnabled() returning: " + enabled);
        
        // 새 설치라면 강제로 false 반환
        if (isNewInstallation()) {
            Log.d(TAG, "New installation detected - forcing auto login to false");
            enabled = false;
            // 새 설치 시 자동으로 false로 설정
            setAutoLoginEnabled(false);
        }
        
        return enabled;
    }
    
    /**
     * 새 설치인지 확인
     */
    private boolean isNewInstallation() {
        boolean hasAutoLogin = prefs.contains(KEY_IS_AUTO_LOGIN_ENABLED);
        boolean hasEmployeeId = prefs.contains(KEY_EMPLOYEE_ID);
        boolean hasTimestamp = prefs.contains(KEY_LOGIN_TIMESTAMP);
        boolean hasEncryptionKey = prefs.contains(KEY_ENCRYPTION_KEY);
        
        Log.d(TAG, "=== NEW INSTALLATION CHECK ===");
        Log.d(TAG, "Has AUTO_LOGIN key: " + hasAutoLogin);
        Log.d(TAG, "Has EMPLOYEE_ID key: " + hasEmployeeId);
        Log.d(TAG, "Has LOGIN_TIMESTAMP key: " + hasTimestamp);
        Log.d(TAG, "Has ENCRYPTION_KEY key: " + hasEncryptionKey);
        
        // SharedPreferences에 아무 키도 없으면 새 설치
        boolean isNewInstall = !hasAutoLogin && !hasEmployeeId && !hasTimestamp && !hasEncryptionKey;
        
        Log.d(TAG, "Is new installation: " + isNewInstall);
        Log.d(TAG, "=== END NEW INSTALLATION CHECK ===");
        
        return isNewInstall;
    }
    
    /**
     * 로그인 세션 저장
     */
    public void createLoginSession(String employeeId, String employeeName, 
                                  String employeeType, String workPlaceName) {
        try {
            editor.putString(KEY_EMPLOYEE_ID, encrypt(employeeId));
            editor.putString(KEY_EMPLOYEE_NAME, encrypt(employeeName));
            editor.putString(KEY_EMPLOYEE_TYPE, encrypt(employeeType));
            if (workPlaceName != null) {
                editor.putString(KEY_WORK_PLACE_NAME, encrypt(workPlaceName));
            }
            editor.putLong(KEY_LOGIN_TIMESTAMP, System.currentTimeMillis());
            editor.apply();
            
            Log.d(TAG, "Login session created successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error creating login session: " + e.getMessage());
        }
    }
    
    /**
     * 저장된 로그인 세션 확인
     */
    public boolean isValidSession() {
        if (!isAutoLoginEnabled()) {
            return false;
        }
        
        if (!prefs.contains(KEY_EMPLOYEE_ID) || !prefs.contains(KEY_LOGIN_TIMESTAMP)) {
            return false;
        }
        
        long loginTimestamp = prefs.getLong(KEY_LOGIN_TIMESTAMP, 0);
        long currentTime = System.currentTimeMillis();
        
        // 로그인 시간이 30일을 초과했는지 확인
        if (currentTime - loginTimestamp > AUTO_LOGIN_DURATION_MS) {
            Log.d(TAG, "Session expired, clearing data");
            clearSession();
            return false;
        }
        
        return true;
    }
    
    /**
     * 저장된 사용자 정보 가져오기
     */
    public String getEmployeeId() {
        try {
            String encrypted = prefs.getString(KEY_EMPLOYEE_ID, null);
            String result = encrypted != null ? decrypt(encrypted) : null;
            Log.d(TAG, "getEmployeeId() returning: " + result);
            return result;
        } catch (Exception e) {
            Log.e(TAG, "Error getting employee ID: " + e.getMessage());
            return null;
        }
    }
    
    public String getEmployeeName() {
        try {
            String encrypted = prefs.getString(KEY_EMPLOYEE_NAME, null);
            return encrypted != null ? decrypt(encrypted) : null;
        } catch (Exception e) {
            Log.e(TAG, "Error getting employee name: " + e.getMessage());
            return null;
        }
    }
    
    public String getEmployeeType() {
        try {
            String encrypted = prefs.getString(KEY_EMPLOYEE_TYPE, null);
            String result = encrypted != null ? decrypt(encrypted) : null;
            Log.d(TAG, "getEmployeeType() returning: " + result);
            return result;
        } catch (Exception e) {
            Log.e(TAG, "Error getting employee type: " + e.getMessage());
            return null;
        }
    }
    
    public String getWorkPlaceName() {
        try {
            String encrypted = prefs.getString(KEY_WORK_PLACE_NAME, null);
            return encrypted != null ? decrypt(encrypted) : null;
        } catch (Exception e) {
            Log.e(TAG, "Error getting work place name: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * 세션 정보 삭제
     */
    public void clearSession() {
        editor.remove(KEY_EMPLOYEE_ID);
        editor.remove(KEY_EMPLOYEE_NAME);
        editor.remove(KEY_EMPLOYEE_TYPE);
        editor.remove(KEY_WORK_PLACE_NAME);
        editor.remove(KEY_LOGIN_TIMESTAMP);
        editor.apply();
        Log.d(TAG, "Session cleared");
    }
    
    /**
     * 로그아웃 처리
     */
    public void logout() {
        Log.d(TAG, "logout() called - clearing all session data");
        
        // 모든 세션 관련 데이터 완전 삭제
        editor.clear();
        editor.apply();
        
        Log.d(TAG, "User logged out - all session data cleared");
    }
    
    /**
     * 암호화 키 생성 및 저장
     */
    private void generateAndSaveEncryptionKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            SecretKey secretKey = keyGenerator.generateKey();
            String encodedKey = Base64.encodeToString(secretKey.getEncoded(), Base64.DEFAULT);
            
            editor.putString(KEY_ENCRYPTION_KEY, encodedKey);
            editor.apply();
            
            Log.d(TAG, "Encryption key generated and saved");
        } catch (Exception e) {
            Log.e(TAG, "Error generating encryption key: " + e.getMessage());
        }
    }
    
    /**
     * 데이터 암호화
     */
    private String encrypt(String data) throws Exception {
        String encodedKey = prefs.getString(KEY_ENCRYPTION_KEY, null);
        if (encodedKey == null) {
            throw new Exception("Encryption key not found");
        }
        
        byte[] decodedKey = Base64.decode(encodedKey, Base64.DEFAULT);
        SecretKey secretKey = new SecretKeySpec(decodedKey, "AES");
        
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        
        return Base64.encodeToString(encryptedData, Base64.DEFAULT);
    }
    
    /**
     * 데이터 복호화
     */
    private String decrypt(String encryptedData) throws Exception {
        String encodedKey = prefs.getString(KEY_ENCRYPTION_KEY, null);
        if (encodedKey == null) {
            throw new Exception("Encryption key not found");
        }
        
        byte[] decodedKey = Base64.decode(encodedKey, Base64.DEFAULT);
        SecretKey secretKey = new SecretKeySpec(decodedKey, "AES");
        
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedData = cipher.doFinal(Base64.decode(encryptedData, Base64.DEFAULT));
        
        return new String(decryptedData);
    }
    
    /**
     * 세션 정보 업데이트 (로그인 시간 갱신)
     */
    public void refreshSession() {
        if (isValidSession()) {
            editor.putLong(KEY_LOGIN_TIMESTAMP, System.currentTimeMillis());
            editor.apply();
            Log.d(TAG, "Session refreshed");
        }
    }
}