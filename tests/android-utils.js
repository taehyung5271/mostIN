const { expect } = require('@playwright/test');

class AndroidTestUtils {
  constructor(page) {
    this.page = page;
  }

  // ADB 명령어를 통한 Android 디바이스 제어
  async executeAdbCommand(command) {
    const { exec } = require('child_process');
    return new Promise((resolve, reject) => {
      exec(`adb ${command}`, (error, stdout, stderr) => {
        if (error) {
          console.log(`ADB 오류: ${error}`);
          reject(error);
        } else {
          resolve(stdout.trim());
        }
      });
    });
  }

  // Android 앱 실행
  async launchApp(packageName = 'com.example.mostin', activityName = '.activities.LoginActivity') {
    try {
      await this.executeAdbCommand(`shell am start -n ${packageName}/${activityName}`);
      await this.page.waitForTimeout(3000); // 앱 로딩 대기
      console.log('✅ Android 앱 실행됨');
    } catch (error) {
      console.log('❌ 앱 실행 실패:', error);
    }
  }

  // 키보드 강제 표시/숨김
  async showKeyboard() {
    await this.executeAdbCommand('shell input keyevent KEYCODE_MENU');
    await this.page.waitForTimeout(500);
  }

  async hideKeyboard() {
    await this.executeAdbCommand('shell input keyevent KEYCODE_BACK');
    await this.page.waitForTimeout(500);
  }

  // 화면 터치 시뮬레이션
  async tapScreen(x, y) {
    await this.executeAdbCommand(`shell input tap ${x} ${y}`);
    await this.page.waitForTimeout(300);
  }

  // 텍스트 입력 (ADB를 통해)
  async inputText(text) {
    const escapedText = text.replace(/\s/g, '%s').replace(/'/g, "\\'");
    await this.executeAdbCommand(`shell input text "${escapedText}"`);
    await this.page.waitForTimeout(500);
  }

  // 디바이스 정보 가져오기
  async getDeviceInfo() {
    const screenSize = await this.executeAdbCommand('shell wm size');
    const density = await this.executeAdbCommand('shell wm density');
    const androidVersion = await this.executeAdbCommand('shell getprop ro.build.version.release');
    
    return {
      screenSize: screenSize.replace('Physical size: ', ''),
      density: density.replace('Physical density: ', ''),
      androidVersion
    };
  }

  // 키보드 높이 측정 (더 정확한 방법)
  async measureKeyboardHeight() {
    try {
      // 소프트 키보드 정보 가져오기
      const keyboardInfo = await this.executeAdbCommand('shell dumpsys input_method');
      const keyboardVisible = keyboardInfo.includes('mInputShown=true');
      
      if (keyboardVisible) {
        // 화면 크기와 현재 가시 영역 비교
        const displayInfo = await this.executeAdbCommand('shell dumpsys display');
        const sizeMatch = displayInfo.match(/cur=(\d+)x(\d+)/);
        
        if (sizeMatch) {
          const screenHeight = parseInt(sizeMatch[2]);
          const visibleHeight = await this.page.evaluate(() => window.innerHeight);
          const keyboardHeight = screenHeight - visibleHeight;
          
          console.log(`📏 키보드 높이 측정: ${keyboardHeight}px (화면: ${screenHeight}px, 가시영역: ${visibleHeight}px)`);
          return keyboardHeight;
        }
      }
      
      return 0;
    } catch (error) {
      console.log('키보드 높이 측정 실패:', error);
      return 0;
    }
  }

  // RecyclerView 스크롤 상태 확인
  async getRecyclerViewInfo() {
    return await this.page.evaluate(() => {
      const recyclerView = document.querySelector('[data-testid="adminGoodsRecyclerView"], .recycler-view');
      if (recyclerView) {
        return {
          scrollTop: recyclerView.scrollTop,
          scrollHeight: recyclerView.scrollHeight,
          clientHeight: recyclerView.clientHeight,
          paddingBottom: getComputedStyle(recyclerView).paddingBottom
        };
      }
      return null;
    });
  }

  // 텍스트필드 위치와 가시성 정밀 검사
  async analyzeTextFieldVisibility(selector) {
    const element = this.page.locator(selector);
    const isVisible = await element.isVisible();
    
    if (!isVisible) {
      return { visible: false, reason: 'Element not visible' };
    }

    const box = await element.boundingBox();
    const viewport = this.page.viewportSize();
    const keyboardHeight = await this.measureKeyboardHeight();
    
    const visibleAreaHeight = viewport.height - keyboardHeight;
    const elementBottom = box.y + box.height;
    
    const analysis = {
      visible: isVisible,
      position: box,
      viewport: viewport,
      keyboardHeight: keyboardHeight,
      visibleAreaHeight: visibleAreaHeight,
      elementBottom: elementBottom,
      isAboveKeyboard: elementBottom < visibleAreaHeight,
      distanceFromKeyboard: visibleAreaHeight - elementBottom
    };

    console.log('📊 텍스트필드 가시성 분석:', analysis);
    return analysis;
  }

  // 스크린샷 및 로그 저장
  async captureDebugInfo(testName) {
    const timestamp = new Date().toISOString().replace(/[:.]/g, '-');
    const filename = `${testName}-${timestamp}`;
    
    // 스크린샷
    await this.page.screenshot({ 
      path: `test-results/${filename}.png`,
      fullPage: true 
    });
    
    // 디바이스 정보
    const deviceInfo = await this.getDeviceInfo();
    
    // RecyclerView 정보
    const recyclerViewInfo = await this.getRecyclerViewInfo();
    
    // 키보드 정보
    const keyboardHeight = await this.measureKeyboardHeight();
    
    const debugInfo = {
      timestamp,
      testName,
      deviceInfo,
      recyclerViewInfo,
      keyboardHeight,
      viewport: this.page.viewportSize()
    };
    
    // JSON 파일로 저장
    const fs = require('fs');
    if (!fs.existsSync('test-results')) {
      fs.mkdirSync('test-results', { recursive: true });
    }
    
    fs.writeFileSync(
      `test-results/${filename}.json`,
      JSON.stringify(debugInfo, null, 2)
    );
    
    console.log(`💾 디버그 정보 저장: ${filename}`);
    return debugInfo;
  }

  // WebView 연결 확인
  async checkWebViewConnection() {
    try {
      // Chrome DevTools Protocol을 통한 WebView 연결 확인
      const webviews = await this.executeAdbCommand('shell cat /proc/net/unix | grep chrome_devtools_remote');
      const hasWebView = webviews.length > 0;
      
      console.log(hasWebView ? '✅ WebView 연결 감지됨' : '❌ WebView 연결 없음');
      return hasWebView;
    } catch (error) {
      console.log('WebView 연결 확인 실패:', error);
      return false;
    }
  }

  // 실제 사용자 터치 시뮬레이션 (더 정확함)
  async simulateUserTouch(selector) {
    const element = this.page.locator(selector);
    const box = await element.boundingBox();
    
    if (box) {
      const centerX = box.x + box.width / 2;
      const centerY = box.y + box.height / 2;
      
      // ADB를 통한 실제 터치
      await this.tapScreen(centerX, centerY);
      console.log(`👆 실제 터치 시뮬레이션: (${centerX}, ${centerY})`);
    }
  }
}

module.exports = { AndroidTestUtils };