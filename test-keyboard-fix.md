# 키보드 가림 문제 해결 테스트 가이드

## 🔧 적용된 해결책

### 1. **NestedScrollView 기반 스크롤 시스템**
- `RecyclerView`를 `NestedScrollView`로 감싸서 더 정확한 스크롤 제어
- 키보드 높이에 따른 동적 패딩 조정

### 2. **정밀한 스크롤 계산**
```java
// 키보드 높이 추정: 화면의 45%
final int keyboardHeight = (int) (screenHeight * 0.45f);

// 가시 영역 계산
final int visibleHeight = screenHeight - keyboardHeight;

// 안전 영역 확보: 키보드 위 150px
final int safeZone = 150;

// 스크롤 위치 계산
final int targetY = (position * itemHeightPx) - (visibleHeight - safeZone);
```

### 3. **다중 시점 스크롤**
- 즉시 스크롤 (0ms)
- 보정 스크롤 (100ms, 300ms, 600ms 후)

### 4. **Manual Input Mode**
- `android:windowSoftInputMode="adjustNothing"`으로 설정
- 키보드 처리를 완전히 수동으로 제어

## 🧪 테스트 방법

### 1. **기본 테스트**
1. 앱 실행 → 관리자 로그인
2. 상품 관리 → 등록 버튼 클릭
3. 새로 생성된 바코드 텍스트필드 터치
4. **확인사항**: 텍스트필드가 키보드 바로 위에 표시되는지

### 2. **연속 입력 테스트**
1. 여러 개의 상품 등록 (등록 버튼 반복 클릭)
2. 리스트 하단의 텍스트필드들을 순차적으로 터치
3. **확인사항**: 각 텍스트필드가 키보드에 가려지지 않고 입력 가능한지

### 3. **스크롤 지속성 테스트**
1. 하단 텍스트필드에서 긴 텍스트 입력
2. 커서 이동이나 텍스트 편집 시도
3. **확인사항**: 입력 중에도 텍스트가 지속적으로 보이는지

## 🐛 문제 진단 방법

### 1. **로그 확인**
```bash
adb logcat | grep AdminGoodsFragment
```

다음 로그들을 확인:
- `키보드 표시됨: XXXpx`
- `NestedScrollView 패딩 조정: XXXpx`
- `키보드 안전 스크롤: position X, scrollY XXXpx`

### 2. **수동 측정**
```bash
# 화면 크기 확인
adb shell wm size

# 키보드 상태 확인
adb shell dumpsys input_method | grep mInputShown

# UI 덤프 (요소 위치 확인)
adb shell uiautomator dump /sdcard/ui_dump.xml
adb shell cat /sdcard/ui_dump.xml
```

## 📊 성공 기준

### ✅ 성공 조건
- 텍스트필드 터치 시 즉시 키보드 위로 이동
- 키보드 표시 중에도 입력 텍스트가 명확히 보임
- 스크롤이 자연스럽고 반응성이 좋음
- 키보드 숨김 시 올바른 위치로 복원

### ❌ 실패 조건
- 텍스트필드가 키보드에 가려짐
- 입력한 텍스트가 보이지 않음
- 스크롤이 동작하지 않거나 부자연스러움
- 키보드 숨김 후 레이아웃이 깨짐

## 🔄 추가 개선 방안

### 문제가 지속될 경우:

1. **키보드 높이 실측**:
```java
// ViewTreeObserver로 정확한 키보드 높이 측정
int keyboardHeight = screenHeight - visibleDisplayHeight;
```

2. **하드코딩된 값 조정**:
```java
// 기기별로 다를 수 있는 값들
final int itemHeight = 120; // 실제 아이템 높이에 맞게 조정
final int keyboardRatio = 0.45f; // 키보드 비율 조정 (0.4 ~ 0.5)
final int safeZone = 150; // 안전 영역 조정 (100 ~ 200)
```

3. **즉시 스크롤 강화**:
```java
// smoothScrollTo 대신 scrollTo 사용
nestedScrollView.scrollTo(0, scrollY); // 즉시 스크롤
```

4. **Focus 기반 스크롤**:
```java
// 포커스된 뷰를 기준으로 스크롤
View focusedView = getActivity().getCurrentFocus();
if (focusedView != null) {
    focusedView.getLocationOnScreen(location);
    // 해당 위치로 스크롤
}
```

## 🎯 최종 점검사항

- [ ] 바코드 필드 터치 시 키보드 위에 표시
- [ ] 상품명 필드 터치 시 키보드 위에 표시  
- [ ] 연속 입력 시 각 필드가 올바르게 스크롤
- [ ] 키보드 숨김 시 레이아웃 복원
- [ ] 입력 중 텍스트 가시성 유지
- [ ] 스크롤 애니메이션의 자연스러움
- [ ] 다양한 화면 크기에서의 동작
- [ ] 세로/가로 모드 전환 시 안정성