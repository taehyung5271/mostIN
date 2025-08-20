package com.example.mostin.utils;

import android.content.Context;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class NonScrollingGridLayoutManagerTest {
    private NonScrollingGridLayoutManager layoutManager;
    private Context context;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        context = RuntimeEnvironment.getApplication();
        layoutManager = new NonScrollingGridLayoutManager(context, 2);
    }

    @Test
    public void should_createInstance_when_constructorCalledWithValidParameters() {
        NonScrollingGridLayoutManager manager = new NonScrollingGridLayoutManager(context, 3);
        
        assertNotNull(manager);
    }

    @Test
    public void should_returnFalse_when_canScrollVerticallyCalled() {
        boolean result = layoutManager.canScrollVertically();
        
        assertFalse(result);
    }

    @Test
    public void should_inheritFromGridLayoutManager_when_instantiated() {
        assertTrue(layoutManager instanceof androidx.recyclerview.widget.GridLayoutManager);
    }

    @Test
    public void should_acceptDifferentSpanCounts_when_constructorCalled() {
        NonScrollingGridLayoutManager manager1 = new NonScrollingGridLayoutManager(context, 1);
        NonScrollingGridLayoutManager manager2 = new NonScrollingGridLayoutManager(context, 4);
        NonScrollingGridLayoutManager manager3 = new NonScrollingGridLayoutManager(context, 10);
        
        assertNotNull(manager1);
        assertNotNull(manager2);
        assertNotNull(manager3);
        
        // All should disable vertical scrolling regardless of span count
        assertFalse(manager1.canScrollVertically());
        assertFalse(manager2.canScrollVertically());
        assertFalse(manager3.canScrollVertically());
    }

    @Test
    public void should_maintainNonScrollingBehavior_when_multipleCallsMade() {
        // Test multiple calls to ensure consistent behavior
        assertFalse(layoutManager.canScrollVertically());
        assertFalse(layoutManager.canScrollVertically());
        assertFalse(layoutManager.canScrollVertically());
    }

    @Test
    public void should_workWithDifferentContexts_when_constructed() {
        Context appContext = RuntimeEnvironment.getApplication().getApplicationContext();
        NonScrollingGridLayoutManager managerWithAppContext = 
                new NonScrollingGridLayoutManager(appContext, 2);
        
        assertNotNull(managerWithAppContext);
        assertFalse(managerWithAppContext.canScrollVertically());
    }
}