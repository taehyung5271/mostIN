package com.example.mostin.utils;

import com.example.mostin.models.GoodsModel;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class GoodsCacheTest {
    private GoodsCache goodsCache;
    private List<GoodsModel> testGoodsList;

    @Before
    public void setUp() {
        goodsCache = GoodsCache.getInstance();
        goodsCache.clearCache(); // Clean state for each test
        
        testGoodsList = new ArrayList<>();
        testGoodsList.add(new GoodsModel("123456", "Test Product 1"));
        testGoodsList.add(new GoodsModel("789012", "Test Product 2"));
    }

    @Test
    public void should_returnSameInstance_when_getInstanceCalledMultipleTimes() {
        GoodsCache firstInstance = GoodsCache.getInstance();
        GoodsCache secondInstance = GoodsCache.getInstance();
        
        assertSame(firstInstance, secondInstance);
    }

    @Test
    public void should_returnNotNull_when_getInstanceCalled() {
        GoodsCache instance = GoodsCache.getInstance();
        
        assertNotNull(instance);
    }

    @Test
    public void should_returnFalse_when_isCacheValidCalledWithNoData() {
        boolean result = goodsCache.isCacheValid();
        
        assertFalse(result);
    }

    @Test
    public void should_returnFalse_when_isCacheValidCalledWithEmptyList() {
        List<GoodsModel> emptyList = new ArrayList<>();
        goodsCache.setCachedGoods(emptyList);
        
        boolean result = goodsCache.isCacheValid();
        
        assertFalse(result);
    }

    @Test
    public void should_returnTrue_when_isCacheValidCalledWithValidData() {
        goodsCache.setCachedGoods(testGoodsList);
        
        boolean result = goodsCache.isCacheValid();
        
        assertTrue(result);
    }

    @Test
    public void should_storeGoodsList_when_setCachedGoodsCalledWithValidData() {
        goodsCache.setCachedGoods(testGoodsList);
        
        List<GoodsModel> result = goodsCache.getCachedGoods();
        
        assertNotNull(result);
        assertEquals(testGoodsList, result);
        assertEquals(2, result.size());
    }

    @Test
    public void should_returnStoredGoods_when_getCachedGoodsCalledAfterSet() {
        goodsCache.setCachedGoods(testGoodsList);
        
        List<GoodsModel> result = goodsCache.getCachedGoods();
        
        assertEquals(testGoodsList.size(), result.size());
        assertEquals("Test Product 1", result.get(0).getName());
        assertEquals("123456", result.get(0).getBarcode());
    }

    @Test
    public void should_returnNull_when_getCachedGoodsCalledWithoutData() {
        List<GoodsModel> result = goodsCache.getCachedGoods();
        
        assertNull(result);
    }

    @Test
    public void should_clearCache_when_clearCacheCalled() {
        goodsCache.setCachedGoods(testGoodsList);
        assertTrue(goodsCache.isCacheValid());
        
        goodsCache.clearCache();
        
        assertFalse(goodsCache.isCacheValid());
        assertNull(goodsCache.getCachedGoods());
    }

    @Test
    public void should_overwriteExistingData_when_setCachedGoodsCalledMultipleTimes() {
        goodsCache.setCachedGoods(testGoodsList);
        
        List<GoodsModel> newGoodsList = new ArrayList<>();
        newGoodsList.add(new GoodsModel("999999", "New Product"));
        
        goodsCache.setCachedGoods(newGoodsList);
        
        List<GoodsModel> result = goodsCache.getCachedGoods();
        assertEquals(1, result.size());
        assertEquals("New Product", result.get(0).getName());
        assertEquals("999999", result.get(0).getBarcode());
    }

    @Test
    public void should_handleNullData_when_setCachedGoodsCalledWithNull() {
        goodsCache.setCachedGoods(null);
        
        assertFalse(goodsCache.isCacheValid());
        assertNull(goodsCache.getCachedGoods());
    }

    @Test
    public void should_maintainSingletonBehavior_when_multipleOperationsPerformed() {
        GoodsCache cache1 = GoodsCache.getInstance();
        cache1.setCachedGoods(testGoodsList);
        
        GoodsCache cache2 = GoodsCache.getInstance();
        List<GoodsModel> result = cache2.getCachedGoods();
        
        assertSame(cache1, cache2);
        assertEquals(testGoodsList, result);
        assertTrue(cache2.isCacheValid());
    }

    @Test
    public void should_persistDataAcrossInstances_when_singletonUsed() {
        GoodsCache.getInstance().setCachedGoods(testGoodsList);
        
        GoodsCache newReference = GoodsCache.getInstance();
        
        assertTrue(newReference.isCacheValid());
        assertEquals(2, newReference.getCachedGoods().size());
    }
}