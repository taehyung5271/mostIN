package com.example.mostin.utils;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class AppCacheTest {
    private AppCache appCache;
    private List<String> testStringList;
    private List<Integer> testIntegerList;

    @Before
    public void setUp() {
        appCache = AppCache.getInstance();
        appCache.clearAllCaches(); // Clean state for each test
        
        testStringList = new ArrayList<>();
        testStringList.add("test1");
        testStringList.add("test2");
        
        testIntegerList = new ArrayList<>();
        testIntegerList.add(1);
        testIntegerList.add(2);
    }

    @Test
    public void should_returnSameInstance_when_getInstanceCalledMultipleTimes() {
        AppCache firstInstance = AppCache.getInstance();
        AppCache secondInstance = AppCache.getInstance();
        
        assertSame(firstInstance, secondInstance);
    }

    @Test
    public void should_returnNotNull_when_getInstanceCalled() {
        AppCache instance = AppCache.getInstance();
        
        assertNotNull(instance);
    }

    @Test
    public void should_returnStoredList_when_getCachedListCalledWithValidKey() {
        String key = "testKey";
        appCache.putCachedList(key, testStringList);
        
        List<String> result = appCache.getCachedList(key, String.class);
        
        assertNotNull(result);
        assertEquals(testStringList, result);
        assertEquals(2, result.size());
        assertEquals("test1", result.get(0));
    }

    @Test
    public void should_returnNull_when_getCachedListCalledWithInvalidKey() {
        String invalidKey = "nonExistentKey";
        
        List<String> result = appCache.getCachedList(invalidKey, String.class);
        
        assertNull(result);
    }

    @Test
    public void should_storeList_when_putCachedListCalledWithValidData() {
        String key = "putTestKey";
        appCache.putCachedList(key, testStringList);
        
        assertTrue(appCache.containsKey(key));
        List<String> result = appCache.getCachedList(key, String.class);
        assertEquals(testStringList, result);
    }

    @Test
    public void should_overwriteExistingList_when_putCachedListCalledWithExistingKey() {
        String key = "overwriteKey";
        appCache.putCachedList(key, testStringList);
        
        List<String> newList = new ArrayList<>();
        newList.add("newTest");
        appCache.putCachedList(key, newList);
        
        List<String> result = appCache.getCachedList(key, String.class);
        assertEquals(newList, result);
        assertEquals(1, result.size());
        assertEquals("newTest", result.get(0));
    }

    @Test
    public void should_returnTrue_when_containsKeyCalledWithExistingKey() {
        String key = "existingKey";
        appCache.putCachedList(key, testStringList);
        
        boolean result = appCache.containsKey(key);
        
        assertTrue(result);
    }

    @Test
    public void should_returnFalse_when_containsKeyCalledWithNonExistingKey() {
        String nonExistingKey = "nonExistingKey";
        
        boolean result = appCache.containsKey(nonExistingKey);
        
        assertFalse(result);
    }

    @Test
    public void should_removeSpecificCache_when_clearCacheCalledWithValidKey() {
        String key1 = "key1";
        String key2 = "key2";
        appCache.putCachedList(key1, testStringList);
        appCache.putCachedList(key2, testIntegerList);
        
        appCache.clearCache(key1);
        
        assertFalse(appCache.containsKey(key1));
        assertTrue(appCache.containsKey(key2));
    }

    @Test
    public void should_removeAllCaches_when_clearAllCachesCalled() {
        String key1 = "key1";
        String key2 = "key2";
        appCache.putCachedList(key1, testStringList);
        appCache.putCachedList(key2, testIntegerList);
        
        appCache.clearAllCaches();
        
        assertFalse(appCache.containsKey(key1));
        assertFalse(appCache.containsKey(key2));
    }

    @Test
    public void should_handleNullList_when_putCachedListCalledWithNull() {
        String key = "nullKey";
        appCache.putCachedList(key, null);
        
        assertTrue(appCache.containsKey(key));
        List<String> result = appCache.getCachedList(key, String.class);
        assertNull(result);
    }

    @Test
    public void should_handleDifferentTypes_when_storingMultipleListTypes() {
        String stringKey = "stringKey";
        String intKey = "intKey";
        appCache.putCachedList(stringKey, testStringList);
        appCache.putCachedList(intKey, testIntegerList);
        
        List<String> stringResult = appCache.getCachedList(stringKey, String.class);
        List<Integer> intResult = appCache.getCachedList(intKey, Integer.class);
        
        assertEquals(testStringList, stringResult);
        assertEquals(testIntegerList, intResult);
    }
}