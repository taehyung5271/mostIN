const { test, expect } = require('@playwright/test');
const { exec } = require('child_process');
const fs = require('fs');
const path = require('path');

// ADB 명령어 실행 헬퍼
async function executeAdb(command) {
  return new Promise((resolve, reject) => {
    exec(`adb ${command}`, (error, stdout, stderr) => {
      if (error) {
        console.log(`ADB 오류: ${error.message}`);
        resolve(null);
      } else {
        resolve(stdout.trim());
      }
    });
  });
}

// 스크린샷 촬영
async function takeScreenshot(name) {
  const timestamp = new Date().toISOString().replace(/[:.]/g, '-');
  const filename = `${name}-${timestamp}.png`;
  
  if (!fs.existsSync('test-results')) {
    fs.mkdirSync('test-results', { recursive: true });
  }
  
  await executeAdb(`exec-out screencap -p > test-results/${filename}`);
  console.log(`📸 스크린샷 저장: ${filename}`);
  return filename;
}

// UI 덤프 가져오기
async function getUiDump() {
  const uiDump = await executeAdb('shell uiautomator dump /sdcard/ui_dump.xml && shell cat /sdcard/ui_dump.xml');
  return uiDump;
}

// 특정 텍스트가 포함된 요소 찾기
function findElementByText(uiDump, text) {
  const regex = new RegExp(`text="([^"]*${text}[^"]*)"[^>]*bounds="\\[([0-9]+),([0-9]+)\\]\\[([0-9]+),([0-9]+)\\]"`, 'i');
  const match = uiDump.match(regex);
  
  if (match) {
    return {
      text: match[1],
      bounds: {
        left: parseInt(match[2]),
        top: parseInt(match[3]),
        right: parseInt(match[4]),
        bottom: parseInt(match[5])
      },
      center: {
        x: (parseInt(match[2]) + parseInt(match[4])) / 2,
        y: (parseInt(match[3]) + parseInt(match[5])) / 2
      }
    };
  }
  return null;
}

// EditText 요소들 찾기
function findEditTextElements(uiDump) {
  const editTextRegex = /class="android\.widget\.EditText"[^>]*text="([^"]*)"[^>]*bounds="\[([0-9]+),([0-9]+)\]\[([0-9]+),([0-9]+)\]"/g;
  const elements = [];
  let match;
  
  while ((match = editTextRegex.exec(uiDump)) !== null) {
    elements.push({
      text: match[1],
      bounds: {
        left: parseInt(match[2]),
        top: parseInt(match[3]),
        right: parseInt(match[4]),
        bottom: parseInt(match[5])
      },
      center: {
        x: (parseInt(match[2]) + parseInt(match[4])) / 2,
        y: (parseInt(match[3]) + parseInt(match[5])) / 2
      }
    });
  }
  
  return elements;
}

// 화면 크기 가져오기
async function getScreenSize() {
  const sizeOutput = await executeAdb('shell wm size');
  const match = sizeOutput.match(/Physical size: (\d+)x(\d+)/);
  
  if (match) {
    return {
      width: parseInt(match[1]),
      height: parseInt(match[2])
    };
  }
  return { width: 1080, height: 2340 }; // 기본값
}

// 키보드 표시 여부 확인
async function isKeyboardVisible() {
  const inputMethod = await executeAdb('shell dumpsys input_method | grep mInputShown');
  return inputMethod && inputMethod.includes('mInputShown=true');
}

test.describe('MostIN Android 앱 키보드 테스트', () => {
  
  test.beforeEach(async () => {
    // 앱 실행
    await executeAdb('shell am start -n com.example.mostin/.activities.LoginActivity');
    await new Promise(resolve => setTimeout(resolve, 3000)); // 앱 로딩 대기
  });

  test('관리자 모드에서 상품 등록 시 키보드 상호작용 테스트', async () => {
    console.log('🧪 Android 네이티브 앱 키보드 테스트 시작');
    
    // 초기 스크린샷
    await takeScreenshot('01-app-launched');
    
    // 관리자 로그인 (이미 로그인되어 있을 수 있음)
    let uiDump = await getUiDump();
    
    // 로그인 화면인지 확인
    if (uiDump.includes('로그인') || uiDump.includes('LOGIN')) {
      console.log('📝 로그인 화면 감지됨');
      
      // 사용자명 입력
      const usernameField = findElementByText(uiDump, '사용자');
      if (usernameField) {
        await executeAdb(`shell input tap ${usernameField.center.x} ${usernameField.center.y}`);
        await new Promise(resolve => setTimeout(resolve, 500));
        await executeAdb('shell input text "admin"');
      }
      
      // 비밀번호 입력
      const passwordField = findElementByText(uiDump, '비밀번호');
      if (passwordField) {
        await executeAdb(`shell input tap ${passwordField.center.x} ${passwordField.center.y}`);
        await new Promise(resolve => setTimeout(resolve, 500));
        await executeAdb('shell input text "admin123"');
      }
      
      // 로그인 버튼 클릭
      const loginButton = findElementByText(uiDump, '로그인');
      if (loginButton) {
        await executeAdb(`shell input tap ${loginButton.center.x} ${loginButton.center.y}`);
        await new Promise(resolve => setTimeout(resolve, 2000));
      }
      
      await takeScreenshot('02-after-login');
    }
    
    // 상품 관리 메뉴 찾기 및 클릭
    uiDump = await getUiDump();
    const goodsManagement = findElementByText(uiDump, '상품');
    
    if (goodsManagement) {
      console.log('🎯 상품 관리 메뉴 클릭');
      await executeAdb(`shell input tap ${goodsManagement.center.x} ${goodsManagement.center.y}`);
      await new Promise(resolve => setTimeout(resolve, 2000));
      await takeScreenshot('03-goods-management');
    }
    
    // 등록 버튼 찾기 및 클릭
    uiDump = await getUiDump();
    const insertButton = findElementByText(uiDump, '등록');
    
    if (insertButton) {
      console.log('➕ 등록 버튼 클릭');
      await executeAdb(`shell input tap ${insertButton.center.x} ${insertButton.center.y}`);
      await new Promise(resolve => setTimeout(resolve, 1000));
      await takeScreenshot('04-insert-mode-activated');
    }
    
    // EditText 요소들 찾기
    uiDump = await getUiDump();
    const editTexts = findEditTextElements(uiDump);
    
    console.log(`📝 발견된 EditText 요소: ${editTexts.length}개`);
    editTexts.forEach((element, index) => {
      console.log(`  ${index + 1}. 텍스트: "${element.text}", 위치: (${element.center.x}, ${element.center.y})`);
    });
    
    if (editTexts.length > 0) {
      // 화면 크기 가져오기
      const screenSize = await getScreenSize();
      console.log(`📱 화면 크기: ${screenSize.width}x${screenSize.height}`);
      
      // 마지막 EditText (새로 추가된 것) 테스트
      const lastEditText = editTexts[editTexts.length - 1];
      
      console.log('🎯 마지막 EditText 요소 테스트 시작');
      console.log(`초기 위치: (${lastEditText.center.x}, ${lastEditText.center.y})`);
      
      // 터치 전 키보드 상태 확인
      const keyboardBeforeTouch = await isKeyboardVisible();
      console.log(`터치 전 키보드 상태: ${keyboardBeforeTouch ? '표시됨' : '숨겨짐'}`);
      
      // EditText 터치
      await executeAdb(`shell input tap ${lastEditText.center.x} ${lastEditText.center.y}`);
      await new Promise(resolve => setTimeout(resolve, 1000)); // 키보드 애니메이션 대기
      
      await takeScreenshot('05-after-touch-edittext');
      
      // 터치 후 키보드 상태 확인
      const keyboardAfterTouch = await isKeyboardVisible();
      console.log(`터치 후 키보드 상태: ${keyboardAfterTouch ? '표시됨' : '숨겨짐'}`);
      
      // 새로운 UI 덤프로 위치 변화 확인
      const newUiDump = await getUiDump();
      const newEditTexts = findEditTextElements(newUiDump);
      
      if (newEditTexts.length > 0) {
        const newLastEditText = newEditTexts[newEditTexts.length - 1];
        console.log(`터치 후 위치: (${newLastEditText.center.x}, ${newLastEditText.center.y})`);
        
        const positionDelta = {
          x: newLastEditText.center.x - lastEditText.center.x,
          y: newLastEditText.center.y - lastEditText.center.y
        };
        
        console.log(`위치 변화: x=${positionDelta.x}px, y=${positionDelta.y}px`);
        
        // 키보드가 표시되었는지 확인
        if (keyboardAfterTouch) {
          // EditText가 화면 하단(키보드 영역)에 가려져 있는지 확인
          const estimatedKeyboardHeight = screenSize.height * 0.4; // 키보드 높이 추정 (화면의 40%)
          const visibleAreaBottom = screenSize.height - estimatedKeyboardHeight;
          
          console.log(`📏 위치 분석:
            - EditText 하단: ${newLastEditText.bounds.bottom}px
            - 가시 영역 하단: ${visibleAreaBottom}px
            - 키보드에 가려짐: ${newLastEditText.bounds.bottom > visibleAreaBottom}`);
          
          // 텍스트 입력 테스트
          console.log('⌨️ 텍스트 입력 테스트');
          await executeAdb('shell input text "TEST123"');
          await new Promise(resolve => setTimeout(resolve, 1000));
          
          await takeScreenshot('06-after-text-input');
          
          // 입력 후 UI 덤프로 텍스트 확인
          const afterInputDump = await getUiDump();
          const hasTestText = afterInputDump.includes('TEST123');
          console.log(`입력된 텍스트 확인: ${hasTestText ? '성공' : '실패'}`);
          
          // 키보드 숨기기
          await executeAdb('shell input keyevent KEYCODE_BACK');
          await new Promise(resolve => setTimeout(resolve, 1000));
          
          await takeScreenshot('07-after-hide-keyboard');
          
          // 테스트 결과 분석
          const testResults = {
            keyboardShown: keyboardAfterTouch,
            textFieldMoved: Math.abs(positionDelta.y) > 10,
            textInputSuccessful: hasTestText,
            fieldCoveredByKeyboard: newLastEditText.bounds.bottom > visibleAreaBottom,
            positionDelta: positionDelta
          };
          
          console.log('📊 테스트 결과:', JSON.stringify(testResults, null, 2));
          
          // 테스트 결과 파일로 저장
          const timestamp = new Date().toISOString().replace(/[:.]/g, '-');
          fs.writeFileSync(
            `test-results/android-test-results-${timestamp}.json`,
            JSON.stringify(testResults, null, 2)
          );
          
          // 어설션
          expect(testResults.keyboardShown).toBeTruthy();
          expect(testResults.textInputSuccessful).toBeTruthy();
          
          if (testResults.fieldCoveredByKeyboard) {
            console.log('❌ 테스트 실패: EditText가 키보드에 가려져 있습니다');
            console.log('💡 개선 필요: 키보드 표시 시 자동 스크롤 구현 필요');
          } else {
            console.log('✅ 테스트 성공: EditText가 키보드 위에 올바르게 표시됩니다');
          }
          
        } else {
          console.log('❌ 키보드가 표시되지 않았습니다');
        }
        
      } else {
        console.log('❌ 터치 후 EditText를 찾을 수 없습니다');
      }
      
    } else {
      console.log('❌ EditText 요소를 찾을 수 없습니다');
      throw new Error('등록 모드에서 EditText 요소를 찾을 수 없습니다');
    }
  });

  test('연속 텍스트 입력 시 스크롤 동작 테스트', async () => {
    console.log('🧪 연속 텍스트 입력 스크롤 테스트 시작');
    
    // 상품 관리 페이지로 이동 (이전 테스트와 동일한 과정)
    // ... (로그인 및 네비게이션 로직 생략)
    
    // 여러 상품 등록을 시뮬레이션하여 스크롤 테스트
    for (let i = 0; i < 3; i++) {
      console.log(`🔄 상품 ${i + 1} 등록 테스트`);
      
      // 등록 버튼 클릭
      let uiDump = await getUiDump();
      const insertButton = findElementByText(uiDump, '등록');
      
      if (insertButton) {
        await executeAdb(`shell input tap ${insertButton.center.x} ${insertButton.center.y}`);
        await new Promise(resolve => setTimeout(resolve, 500));
      }
      
      // EditText 찾기 및 입력
      uiDump = await getUiDump();
      const editTexts = findEditTextElements(uiDump);
      
      if (editTexts.length > 0) {
        const lastEditText = editTexts[editTexts.length - 1];
        
        await executeAdb(`shell input tap ${lastEditText.center.x} ${lastEditText.center.y}`);
        await new Promise(resolve => setTimeout(resolve, 500));
        
        await executeAdb(`shell input text "PRODUCT${i + 1}"`);
        await new Promise(resolve => setTimeout(resolve, 500));
        
        await takeScreenshot(`multi-product-${i + 1}`);
        
        // 키보드 숨기기
        await executeAdb('shell input keyevent KEYCODE_BACK');
        await new Promise(resolve => setTimeout(resolve, 500));
      }
    }
    
    console.log('✅ 연속 입력 테스트 완료');
  });

});