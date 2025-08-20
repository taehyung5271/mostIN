# 키보드 높이 감지 시스템 사용법

MostIN 프로젝트에서 OnGlobalLayoutListener를 사용한 키보드 높이 실시간 감지 시스템입니다.

## 📋 개요

이 시스템은 다음과 같은 구성 요소로 이루어져 있습니다:

- **`KeyboardStateListener`**: 키보드 상태 변화를 알리는 콜백 인터페이스
- **`KeyboardHeightDetector`**: 실제 키보드 높이를 감지하는 메인 클래스
- **`AdminGoodsFragment`**: 키보드 감지 시스템을 사용하는 예제 구현

## 🔧 기술 요구사항

### 1. AndroidManifest.xml 설정
Activity에 `windowSoftInputMode="adjustResize"` 설정이 필요합니다:

```xml
<activity
    android:name=".activities.AdminHomeScreen"
    android:windowSoftInputMode="adjustResize"
    android:exported="false" />
```

### 2. 동작 원리
- **rootView.height**와 **visibleFrame.bottom**의 차이로 키보드 높이 계산
- 키보드 최소 높이 임계값: **200픽셀**
- 키보드 높이 변화 감지 임계값: **50픽셀**

## 🚀 사용법

### 1. 기본 사용법

```java
public class YourFragment extends Fragment implements KeyboardStateListener {
    private KeyboardHeightDetector keyboardDetector;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.your_layout, container, false);
        
        // 키보드 감지 시스템 초기화
        initializeKeyboardDetector();
        
        return view;
    }
    
    private void initializeKeyboardDetector() {
        if (getActivity() == null) return;
        
        keyboardDetector = new KeyboardHeightDetector(getActivity());
        keyboardDetector.addKeyboardStateListener(this);
        keyboardDetector.start();
    }
    
    @Override
    public void onKeyboardShown(int keyboardHeight) {
        Log.i(TAG, "키보드 표시됨 - 높이: " + keyboardHeight + "px");
        // 키보드가 나타났을 때의 처리 로직
        // 예: RecyclerView 패딩 조정, 스크롤 위치 조정 등
    }
    
    @Override
    public void onKeyboardHidden(int previousKeyboardHeight) {
        Log.i(TAG, "키보드 숨김됨 - 이전 높이: " + previousKeyboardHeight + "px");
        // 키보드가 숨겨졌을 때의 처리 로직
        // 예: 패딩 리셋, 원래 화면 상태로 복원 등
    }
    
    @Override
    public void onKeyboardHeightChanged(int oldHeight, int newHeight) {
        Log.i(TAG, "키보드 높이 변경됨 - " + oldHeight + "px -> " + newHeight + "px");
        // 키보드 높이가 변경되었을 때의 처리 로직
        // 예: IME 변경, 화면 회전 등에 따른 재조정
    }
    
    // Fragment Lifecycle 메서드들
    @Override
    public void onResume() {
        super.onResume();
        if (keyboardDetector != null) {
            keyboardDetector.start();
        }
    }
    
    @Override
    public void onPause() {
        super.onPause();
        if (keyboardDetector != null) {
            keyboardDetector.stop();
        }
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (keyboardDetector != null) {
            keyboardDetector.removeKeyboardStateListener(this);
            keyboardDetector.stop();
            keyboardDetector = null;
        }
    }
}
```

### 2. Activity에서 사용하기

```java
public class YourActivity extends AppCompatActivity implements KeyboardStateListener {
    private KeyboardHeightDetector keyboardDetector;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.your_layout);
        
        // 키보드 감지 초기화
        keyboardDetector = new KeyboardHeightDetector(this);
        keyboardDetector.addKeyboardStateListener(this);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        if (keyboardDetector != null) {
            keyboardDetector.start();
        }
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        if (keyboardDetector != null) {
            keyboardDetector.stop();
        }
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (keyboardDetector != null) {
            keyboardDetector.removeKeyboardStateListener(this);
            keyboardDetector.stop();
        }
    }
    
    // KeyboardStateListener 구현...
}
```

### 3. 고급 사용법 - 여러 리스너 관리

```java
// 여러 개의 리스너를 등록할 수 있습니다
KeyboardStateListener listener1 = new KeyboardStateListener() {
    @Override
    public void onKeyboardShown(int keyboardHeight) {
        // RecyclerView 패딩 조정
        recyclerView.setPadding(0, 0, 0, keyboardHeight);
    }
    
    @Override
    public void onKeyboardHidden(int previousKeyboardHeight) {
        // 패딩 리셋
        recyclerView.setPadding(0, 0, 0, 0);
    }
    
    @Override
    public void onKeyboardHeightChanged(int oldHeight, int newHeight) {
        // 패딩 재조정
        recyclerView.setPadding(0, 0, 0, newHeight);
    }
};

KeyboardStateListener listener2 = new KeyboardStateListener() {
    @Override
    public void onKeyboardShown(int keyboardHeight) {
        // 버튼 위치 조정
        adjustButtonPosition(keyboardHeight);
    }
    
    // 다른 메서드들...
};

keyboardDetector.addKeyboardStateListener(listener1);
keyboardDetector.addKeyboardStateListener(listener2);
```

## 📊 유틸리티 메서드들

### KeyboardHeightDetector 클래스의 주요 메서드

```java
// 현재 키보드가 표시되어 있는지 확인
boolean isVisible = keyboardDetector.isKeyboardVisible();

// 현재 키보드 높이 (키보드가 보이지 않으면 0)
int currentHeight = keyboardDetector.getCurrentKeyboardHeight();

// 마지막으로 측정된 키보드 높이 (키보드가 숨겨져도 값 유지)
int lastHeight = keyboardDetector.getLastKeyboardHeight();

// 현재 상태 로그 출력 (디버깅용)
keyboardDetector.logCurrentState();
```

## 🎯 RecyclerView와 함께 사용하기

상품 관리 화면처럼 RecyclerView가 포함된 화면에서의 활용 예시:

```java
@Override
public void onKeyboardShown(int keyboardHeight) {
    // 키보드 위로 RecyclerView 패딩 조정
    recyclerView.setPadding(0, 0, 0, keyboardHeight);
    
    // 포커스된 EditText로 스크롤 (선택사항)
    if (focusedPosition >= 0) {
        recyclerView.smoothScrollToPosition(focusedPosition);
    }
}

@Override
public void onKeyboardHidden(int previousKeyboardHeight) {
    // 패딩 제거하여 원래 상태로 복원
    recyclerView.setPadding(0, 0, 0, 0);
}
```

## ⚠️ 주의사항

1. **Activity 설정**: `windowSoftInputMode="adjustResize"`가 반드시 필요합니다.
2. **메모리 누수 방지**: `onDestroy()`에서 반드시 리스너 제거와 `stop()` 호출이 필요합니다.
3. **Lifecycle 관리**: `onResume()`/`onPause()`에서 `start()`/`stop()` 호출을 권장합니다.
4. **스레드 안전성**: 모든 콜백은 메인 스레드에서 호출됩니다.

## 🔍 디버깅

로그 태그는 다음과 같습니다:
- **KeyboardHeightDetector**: 키보드 감지 시스템의 로그
- **AdminGoodsFragment**: Fragment에서의 키보드 처리 로그

```java
// 디버깅을 위한 상태 확인
keyboardDetector.logCurrentState();
```

## 📈 성능 고려사항

- 키보드 감지는 GlobalLayoutListener를 사용하여 실시간으로 이루어집니다.
- 불필요한 콜백 호출을 방지하기 위해 임계값 기반 필터링을 적용합니다.
- 여러 리스너가 등록되어도 성능에 미치는 영향은 미미합니다.

---

**작성자**: Senior Android Developer  
**마지막 업데이트**: 2025-08-18  
**버전**: 1.0.0