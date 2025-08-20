package com.example.mostin.repository;

import com.example.mostin.api.ApiClient;
import com.example.mostin.api.ApiService;
import com.example.mostin.models.WorkPlace;

import java.util.List;

import retrofit2.Call;

public class WorkplaceRepository {

    private ApiService apiService;

    public WorkplaceRepository() {
        apiService = ApiClient.getApiService();
    }

    public Call<List<WorkPlace>> getWorkPlaces() {
        return apiService.getAllWorkPlaces();
    }

    public Call<WorkPlace> getWorkPlaceByName(String workPlaceName) {
        return apiService.getWorkPlaceByName(workPlaceName);
    }
}
