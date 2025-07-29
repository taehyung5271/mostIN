package com.example.mostin.api;

import com.example.mostin.models.EmployeeModel; // Assuming this is the correct Android model
import com.example.mostin.models.GoodsModel; // Assuming this is the correct Android model
import com.example.mostin.models.WorkPlace; // Assuming this is the correct Android model
import com.example.mostin.models.CommuteModel; // New Android model for Commute
import com.example.mostin.models.Ordering;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.DELETE;
import retrofit2.http.Query;

public interface ApiService {

    // Employee APIs
    @POST("/api/login")
    Call<EmployeeModel> login(@Body Map<String, String> credentials);

    @GET("/api/employees")
    Call<List<EmployeeModel>> getAllEmployees();

    @POST("/api/employees")
    Call<EmployeeModel> createEmployee(@Body EmployeeModel employee);

    @PUT("/api/employees/{id}")
    Call<EmployeeModel> updateEmployee(@retrofit2.http.Path("id") String id, @Body EmployeeModel employee);

    @DELETE("/api/employees/{id}")
    Call<Void> deleteEmployee(@retrofit2.http.Path("id") String id);

    // Goods APIs
    @GET("/api/goods")
    Call<List<GoodsModel>> getAllGoods();

    @POST("/api/goods")
    Call<GoodsModel> createGoods(@Body GoodsModel goods);

    @PUT("/api/goods/{barcode}")
    Call<GoodsModel> updateGoods(@retrofit2.http.Path("barcode") String barcode, @Body GoodsModel goods);

    @DELETE("/api/goods/{barcode}")
    Call<Void> deleteGoods(@retrofit2.http.Path("barcode") String barcode);

    // WorkPlace APIs
    @GET("/api/workplaces")
    Call<List<WorkPlace>> getAllWorkPlaces();

    @GET("/api/workplaces/{name}")
    Call<WorkPlace> getWorkPlaceByName(@retrofit2.http.Path("name") String name);

    // Commute APIs
    @POST("/api/commute/clock-in")
    Call<CommuteModel> clockIn(@Body Map<String, String> payload);

    @PUT("/api/commute/clock-out")
    Call<CommuteModel> clockOut(@Body Map<String, String> payload);

    @GET("/api/commute/today")
    Call<CommuteModel> getTodayCommute(@Query("employeeId") String employeeId);

    @GET("/api/commute/recent")
    Call<CommuteModel> getRecentCommute(@Query("employeeId") String employeeId, @Query("employeeName") String employeeName);

    @GET("/api/commute/monthly")
    Call<List<CommuteModel>> getMonthlyCommute(@Query("employeeId") String employeeId, @Query("year") int year, @Query("month") int month);

    // Ordering APIs
    @POST("/api/orders")
    Call<Ordering> createOrder(@Body Ordering order);

    @GET("/api/orders")
    Call<List<Ordering>> getOrdersByEmployee(@Query("employeeId") String employeeId);

    @GET("/api/orders/details")
    Call<List<Ordering>> getOrderDetailsByDate(@Query("employeeId") String employeeId, @Query("date") String date);

    @DELETE("/api/orders")
    Call<Void> deleteOrdersByDate(@Query("employeeId") String employeeId, @Query("date") String date);
}