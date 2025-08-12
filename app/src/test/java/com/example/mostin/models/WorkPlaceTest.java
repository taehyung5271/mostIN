package com.example.mostin.models;

import org.junit.Test;
import static org.junit.Assert.*;

public class WorkPlaceTest {

    @Test
    public void should_setAndGetWorkPlaceName_when_validNameProvided() {
        // Given
        WorkPlace workPlace = new WorkPlace();
        String expectedName = "서울지점";

        // When
        workPlace.setWorkPlaceName(expectedName);

        // Then
        assertEquals(expectedName, workPlace.getWorkPlaceName());
    }

    @Test
    public void should_setAndGetLatitude_when_validLatitudeProvided() {
        // Given
        WorkPlace workPlace = new WorkPlace();
        double expectedLatitude = 37.5665;

        // When
        workPlace.setLatitude(expectedLatitude);

        // Then
        assertEquals(expectedLatitude, workPlace.getLatitude(), 0.0001);
    }

    @Test
    public void should_setAndGetLongitude_when_validLongitudeProvided() {
        // Given
        WorkPlace workPlace = new WorkPlace();
        double expectedLongitude = 126.9780;

        // When
        workPlace.setLongitude(expectedLongitude);

        // Then
        assertEquals(expectedLongitude, workPlace.getLongitude(), 0.0001);
    }

    @Test
    public void should_handleNullWorkPlaceName_when_nullNameProvided() {
        // Given
        WorkPlace workPlace = new WorkPlace();
        
        // When
        workPlace.setWorkPlaceName(null);

        // Then
        assertNull(workPlace.getWorkPlaceName());
    }

    @Test
    public void should_handleEmptyWorkPlaceName_when_emptyNameProvided() {
        // Given
        WorkPlace workPlace = new WorkPlace();

        // When
        workPlace.setWorkPlaceName("");

        // Then
        assertEquals("", workPlace.getWorkPlaceName());
    }

    @Test
    public void should_handleZeroCoordinates_when_zeroValuesProvided() {
        // Given
        WorkPlace workPlace = new WorkPlace();

        // When
        workPlace.setLatitude(0.0);
        workPlace.setLongitude(0.0);

        // Then
        assertEquals(0.0, workPlace.getLatitude(), 0.0001);
        assertEquals(0.0, workPlace.getLongitude(), 0.0001);
    }

    @Test
    public void should_handleNegativeCoordinates_when_negativeValuesProvided() {
        // Given
        WorkPlace workPlace = new WorkPlace();
        double negativeLatitude = -37.5665;
        double negativeLongitude = -126.9780;

        // When
        workPlace.setLatitude(negativeLatitude);
        workPlace.setLongitude(negativeLongitude);

        // Then
        assertEquals(negativeLatitude, workPlace.getLatitude(), 0.0001);
        assertEquals(negativeLongitude, workPlace.getLongitude(), 0.0001);
    }

    @Test
    public void should_handleExtremeCoordinates_when_extremeValuesProvided() {
        // Given
        WorkPlace workPlace = new WorkPlace();
        double maxLatitude = 90.0;
        double minLatitude = -90.0;
        double maxLongitude = 180.0;
        double minLongitude = -180.0;

        // When & Then for maximum values
        workPlace.setLatitude(maxLatitude);
        workPlace.setLongitude(maxLongitude);
        assertEquals(maxLatitude, workPlace.getLatitude(), 0.0001);
        assertEquals(maxLongitude, workPlace.getLongitude(), 0.0001);

        // When & Then for minimum values
        workPlace.setLatitude(minLatitude);
        workPlace.setLongitude(minLongitude);
        assertEquals(minLatitude, workPlace.getLatitude(), 0.0001);
        assertEquals(minLongitude, workPlace.getLongitude(), 0.0001);
    }

    @Test
    public void should_handlePreciseCoordinates_when_highPrecisionValuesProvided() {
        // Given
        WorkPlace workPlace = new WorkPlace();
        double preciseLatitude = 37.123456789;
        double preciseLongitude = 126.987654321;

        // When
        workPlace.setLatitude(preciseLatitude);
        workPlace.setLongitude(preciseLongitude);

        // Then
        assertEquals(preciseLatitude, workPlace.getLatitude(), 0.000000001);
        assertEquals(preciseLongitude, workPlace.getLongitude(), 0.000000001);
    }

    @Test
    public void should_preserveSpecialCharacters_when_workPlaceNameContainsSpecialChars() {
        // Given
        WorkPlace workPlace = new WorkPlace();
        String specialName = "서울지점@#$%^&*()";

        // When
        workPlace.setWorkPlaceName(specialName);

        // Then
        assertEquals(specialName, workPlace.getWorkPlaceName());
    }

    @Test
    public void should_handleLongWorkPlaceName_when_longNameProvided() {
        // Given
        WorkPlace workPlace = new WorkPlace();
        String longName = "서울특별시 강남구 테헤란로 123길 45-67 ABC빌딩 5층 개발팀 백엔드파트 시스템운영팀";

        // When
        workPlace.setWorkPlaceName(longName);

        // Then
        assertEquals(longName, workPlace.getWorkPlaceName());
    }

    @Test
    public void should_handleCompleteWorkPlaceData_when_allFieldsSet() {
        // Given
        WorkPlace workPlace = new WorkPlace();
        String expectedName = "부산지점";
        double expectedLatitude = 35.1796;
        double expectedLongitude = 129.0756;

        // When
        workPlace.setWorkPlaceName(expectedName);
        workPlace.setLatitude(expectedLatitude);
        workPlace.setLongitude(expectedLongitude);

        // Then
        assertEquals(expectedName, workPlace.getWorkPlaceName());
        assertEquals(expectedLatitude, workPlace.getLatitude(), 0.0001);
        assertEquals(expectedLongitude, workPlace.getLongitude(), 0.0001);
    }

    @Test
    public void should_handleDefaultValues_when_newInstanceCreated() {
        // Given & When
        WorkPlace workPlace = new WorkPlace();

        // Then - 기본값 확인 (primitive double은 0.0, String은 null)
        assertNull(workPlace.getWorkPlaceName());
        assertEquals(0.0, workPlace.getLatitude(), 0.0001);
        assertEquals(0.0, workPlace.getLongitude(), 0.0001);
    }

    @Test
    public void should_preserveWhitespace_when_workPlaceNameContainsWhitespace() {
        // Given
        WorkPlace workPlace = new WorkPlace();
        String nameWithWhitespace = "  서울 지점  ";

        // When
        workPlace.setWorkPlaceName(nameWithWhitespace);

        // Then
        assertEquals(nameWithWhitespace, workPlace.getWorkPlaceName());
    }
}