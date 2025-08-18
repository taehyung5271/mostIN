const { test, expect } = require('@playwright/test');

// Android 앱 테스트를 위한 유틸리티 함수들
const AndroidUtils = {
  async waitForApp(page) {
    // Android WebView가 로드될 때까지 대기
    await page.waitForLoadState('domcontentloaded');
    await page.waitForTimeout(2000);
  },

  async takeScreenshot(page, name) {
    await page.screenshot({ 
      path: `test-results/${name}-${Date.now()}.png`,
      fullPage: true 
    });
  },

  async getElementPosition(page, selector) {
    const element = await page.locator(selector);
    const box = await element.boundingBox();
    return box;
  },

  async isElementVisibleInViewport(page, selector) {
    const element = await page.locator(selector);
    const isVisible = await element.isVisible();
    if (!isVisible) return false;

    const box = await element.boundingBox();
    const viewport = page.viewportSize();
    
    return box && 
           box.y >= 0 && 
           box.y + box.height <= viewport.height &&
           box.x >= 0 && 
           box.x + box.width <= viewport.width;
  },

  async getKeyboardHeight(page) {
    // 키보드 높이를 추정하기 위해 viewport 변화 감지
    const viewportHeight = page.viewportSize().height;
    const windowInnerHeight = await page.evaluate(() => window.innerHeight);
    const keyboardHeight = Math.max(0, viewportHeight - windowInnerHeight);
    return keyboardHeight;
  }
};

test.describe('AdminGoodsFragment 키보드 상호작용 테스트', () => {
  
  test.beforeEach(async ({ page }) => {
    // Android 앱 WebView 접속 (실제 앱이 실행 중이어야 함)
    await page.goto('http://localhost:8080'); // Android WebView URL
    await AndroidUtils.waitForApp(page);
  });

  test('등록 모드에서 텍스트필드 터치 시 키보드 위에 표시되는지 확인', async ({ page }) => {
    console.log('🧪 테스트 시작: 등록 모드 키보드 상호작용');
    
    // 스크린샷: 초기 상태
    await AndroidUtils.takeScreenshot(page, 'initial-state');
    
    // 관리자 로그인 (필요한 경우)
    try {
      const loginButton = page.locator('button:has-text("로그인")');
      if (await loginButton.isVisible()) {
        await page.fill('input[type="text"]', 'admin');
        await page.fill('input[type="password"]', 'admin123');
        await loginButton.click();
        await page.waitForTimeout(2000);
      }
    } catch (e) {
      console.log('로그인 단계 건너뜀');
    }

    // 상품 관리 페이지로 이동
    const goodsButton = page.locator('button:has-text("상품 관리"), [data-testid="goods-management"]');
    await goodsButton.click();
    await page.waitForTimeout(1000);
    
    // 스크린샷: 상품 관리 페이지
    await AndroidUtils.takeScreenshot(page, 'goods-management-page');

    // 등록 버튼 클릭
    const insertButton = page.locator('button:has-text("등록"), #insertBtn');
    await insertButton.click();
    await page.waitForTimeout(500);

    // 스크린샷: 등록 모드 활성화
    await AndroidUtils.takeScreenshot(page, 'insert-mode-activated');

    // 새로 생성된 텍스트필드들 찾기
    const barcodeFields = page.locator('input[placeholder*="바코드"], [hint*="바코드"]');
    const nameFields = page.locator('input[placeholder*="상품명"], [hint*="상품명"]');
    
    const barcodeCount = await barcodeFields.count();
    const nameCount = await nameFields.count();
    
    console.log(`📊 발견된 텍스트필드: 바코드 ${barcodeCount}개, 상품명 ${nameCount}개`);

    if (barcodeCount > 0) {
      // 마지막 바코드 필드 테스트
      const lastBarcodeField = barcodeFields.nth(barcodeCount - 1);
      
      console.log('🎯 마지막 바코드 필드 테스트 시작');
      
      // 터치 전 위치 확인
      const beforeTouchPosition = await AndroidUtils.getElementPosition(page, lastBarcodeField);
      console.log('터치 전 바코드 필드 위치:', beforeTouchPosition);
      
      // 스크린샷: 터치 전
      await AndroidUtils.takeScreenshot(page, 'before-touch-barcode');
      
      // 바코드 필드 터치
      await lastBarcodeField.click();
      await page.waitForTimeout(1000); // 키보드 애니메이션 대기
      
      // 스크린샷: 터치 후 (키보드 올라온 상태)
      await AndroidUtils.takeScreenshot(page, 'after-touch-barcode-keyboard-up');
      
      // 키보드 높이 측정
      const keyboardHeight = await AndroidUtils.getKeyboardHeight(page);
      console.log('🎹 감지된 키보드 높이:', keyboardHeight);
      
      // 터치 후 위치 확인
      const afterTouchPosition = await AndroidUtils.getElementPosition(page, lastBarcodeField);
      console.log('터치 후 바코드 필드 위치:', afterTouchPosition);
      
      // 필드가 키보드 위에 있는지 확인
      const viewport = page.viewportSize();
      const fieldBottomY = afterTouchPosition.y + afterTouchPosition.height;
      const keyboardTopY = viewport.height - keyboardHeight;
      
      console.log(`📏 위치 분석:
        - 필드 하단 Y: ${fieldBottomY}
        - 키보드 상단 Y: ${keyboardTopY}
        - 차이: ${keyboardTopY - fieldBottomY}px
        - 필드가 키보드 위에 있는가: ${fieldBottomY < keyboardTopY}`);
      
      // 텍스트 입력 테스트
      await lastBarcodeField.fill('TEST123');
      await page.waitForTimeout(500);
      
      // 스크린샷: 텍스트 입력 후
      await AndroidUtils.takeScreenshot(page, 'after-text-input-barcode');
      
      // 입력된 텍스트가 보이는지 확인
      const inputValue = await lastBarcodeField.inputValue();
      console.log('입력된 바코드 값:', inputValue);
      
      // 어설션: 필드가 키보드 위에 있어야 함
      expect(fieldBottomY).toBeLessThan(keyboardTopY - 20); // 20px 여유공간
      
      // 상품명 필드도 테스트
      if (nameCount > 0) {
        const lastNameField = nameFields.nth(nameCount - 1);
        
        console.log('🎯 마지막 상품명 필드 테스트 시작');
        
        await lastNameField.click();
        await page.waitForTimeout(1000);
        
        // 스크린샷: 상품명 필드 포커스
        await AndroidUtils.takeScreenshot(page, 'after-touch-name-field');
        
        const nameFieldPosition = await AndroidUtils.getElementPosition(page, lastNameField);
        const nameFieldBottomY = nameFieldPosition.y + nameFieldPosition.height;
        
        console.log(`📏 상품명 필드 위치 분석:
          - 필드 하단 Y: ${nameFieldBottomY}
          - 키보드 상단 Y: ${keyboardTopY}
          - 차이: ${keyboardTopY - nameFieldBottomY}px`);
        
        await lastNameField.fill('테스트 상품');
        await page.waitForTimeout(500);
        
        // 스크린샷: 상품명 입력 후
        await AndroidUtils.takeScreenshot(page, 'after-text-input-name');
        
        const nameValue = await lastNameField.inputValue();
        console.log('입력된 상품명:', nameValue);
        
        // 어설션: 상품명 필드도 키보드 위에 있어야 함
        expect(nameFieldBottomY).toBeLessThan(keyboardTopY - 20);
      }
      
    } else {
      console.log('❌ 텍스트필드를 찾을 수 없습니다');
      await AndroidUtils.takeScreenshot(page, 'no-textfields-found');
      throw new Error('등록 모드에서 텍스트필드를 찾을 수 없습니다');
    }
  });

  test('키보드 표시/숨김 시 스크롤 동작 확인', async ({ page }) => {
    console.log('🧪 테스트 시작: 키보드 표시/숨김 스크롤 동작');
    
    // 상품 관리 페이지로 이동
    await page.locator('button:has-text("상품 관리")').click();
    await page.waitForTimeout(1000);
    
    // 등록 모드 활성화
    await page.locator('#insertBtn').click();
    await page.waitForTimeout(500);
    
    const textFields = page.locator('input[type="text"]');
    const fieldCount = await textFields.count();
    
    if (fieldCount > 0) {
      const lastField = textFields.nth(fieldCount - 1);
      
      // 키보드 숨김 상태에서 필드 위치
      const initialPosition = await AndroidUtils.getElementPosition(page, lastField);
      console.log('초기 필드 위치:', initialPosition);
      
      // 필드 터치하여 키보드 표시
      await lastField.click();
      await page.waitForTimeout(1500); // 키보드 애니메이션 완료 대기
      
      // 키보드 표시 후 필드 위치
      const afterKeyboardPosition = await AndroidUtils.getElementPosition(page, lastField);
      console.log('키보드 표시 후 필드 위치:', afterKeyboardPosition);
      
      // 스크롤이 발생했는지 확인
      const scrollDelta = initialPosition.y - afterKeyboardPosition.y;
      console.log('스크롤 이동량:', scrollDelta);
      
      // 키보드 숨기기 (다른 영역 터치)
      await page.locator('body').click({ position: { x: 100, y: 100 } });
      await page.waitForTimeout(1000);
      
      // 키보드 숨김 후 위치
      const afterHidePosition = await AndroidUtils.getElementPosition(page, lastField);
      console.log('키보드 숨김 후 필드 위치:', afterHidePosition);
      
      // 어설션: 스크롤이 적절히 발생했는지 확인
      expect(Math.abs(scrollDelta)).toBeGreaterThan(50); // 최소 50px 스크롤
      
    }
  });

  test('다중 텍스트필드 순차 입력 테스트', async ({ page }) => {
    console.log('🧪 테스트 시작: 다중 텍스트필드 순차 입력');
    
    // 상품 관리 페이지로 이동하고 등록 모드 활성화
    await page.locator('button:has-text("상품 관리")').click();
    await page.waitForTimeout(1000);
    await page.locator('#insertBtn').click();
    await page.waitForTimeout(500);
    
    // 여러 개의 상품을 추가하여 스크롤 테스트
    for (let i = 0; i < 3; i++) {
      // 새 상품 추가
      if (i > 0) {
        await page.locator('#insertBtn').click();
        await page.waitForTimeout(500);
      }
      
      const barcodeFields = page.locator('input[placeholder*="바코드"]');
      const nameFields = page.locator('input[placeholder*="상품명"]');
      
      const currentBarcodeField = barcodeFields.nth(i);
      const currentNameField = nameFields.nth(i);
      
      // 바코드 입력
      await currentBarcodeField.click();
      await page.waitForTimeout(500);
      await currentBarcodeField.fill(`BARCODE${i + 1}`);
      
      // 스크린샷
      await AndroidUtils.takeScreenshot(page, `multi-input-barcode-${i + 1}`);
      
      // 상품명 입력
      await currentNameField.click();
      await page.waitForTimeout(500);
      await currentNameField.fill(`상품 ${i + 1}`);
      
      // 스크린샷
      await AndroidUtils.takeScreenshot(page, `multi-input-name-${i + 1}`);
      
      // 각 입력 후 필드가 보이는지 확인
      const isVisible = await AndroidUtils.isElementVisibleInViewport(page, currentNameField);
      expect(isVisible).toBeTruthy();
      
      console.log(`✅ 상품 ${i + 1} 입력 완료`);
    }
  });

});