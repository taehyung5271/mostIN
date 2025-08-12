package com.example.mostin.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mostin.models.WorkPlace;
import com.example.mostin.repository.WorkplaceRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {

    private WorkplaceRepository workplaceRepository;
    private MutableLiveData<List<WorkPlace>> workplacesLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public HomeViewModel() {
        workplaceRepository = new WorkplaceRepository();
    }

    public LiveData<List<WorkPlace>> getWorkplacesLiveData() {
        return workplacesLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public void loadWorkplaces() {
        workplaceRepository.getWorkPlaces().enqueue(new Callback<List<WorkPlace>>() {
            @Override
            public void onResponse(Call<List<WorkPlace>> call, Response<List<WorkPlace>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    workplacesLiveData.postValue(response.body());
                } else {
                    errorLiveData.postValue("근무지 목록을 불러오지 못했습니다.");
                }
            }

            @Override
            public void onFailure(Call<List<WorkPlace>> call, Throwable t) {
                errorLiveData.postValue("네트워크 오류: " + t.getMessage());
            }
        });
    }
}
