package com.example.mostin.api;

import com.example.mostin.models.CommuteModel;
import com.example.mostin.models.EmployeeModel;
import com.example.mostin.models.GoodsModel;
import com.example.mostin.models.Ordering;
import com.example.mostin.models.WorkPlace;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/* loaded from: classes6.dex */
public interface ApiService {
    @POST("/api/commute/clock-in")
    Call<CommuteModel> clockIn(@Body Map<String, String> map);

    @PUT("/api/commute/clock-out")
    Call<CommuteModel> clockOut(@Body Map<String, String> map);

    @POST("/api/employees")
    Call<EmployeeModel> createEmployee(@Body EmployeeModel employeeModel);

    @POST("/api/goods")
    Call<GoodsModel> createGoods(@Body GoodsModel goodsModel);

    @POST("/api/orders")
    Call<Ordering> createOrder(@Body Ordering ordering);

    @DELETE("/api/employees/{id}")
    Call<Void> deleteEmployee(@Path("id") String str);

    @DELETE("/api/goods/{barcode}")
    Call<Void> deleteGoods(@Path("barcode") String str);

    @DELETE("/api/orders")
    Call<Void> deleteOrdersByDate(@Query("employeeId") String str, @Query("date") String str2);

    @GET("/api/employees")
    Call<List<EmployeeModel>> getAllEmployees();

    @GET("/api/goods")
    Call<List<GoodsModel>> getAllGoods();

    @GET("/api/workplaces")
    Call<List<WorkPlace>> getAllWorkPlaces();

    @GET("/api/commute/monthly")
    Call<List<CommuteModel>> getMonthlyCommute(@Query("employeeId") String str, @Query("year") int i, @Query("month") int i2);

    @GET("/api/orders/details")
    Call<List<Ordering>> getOrderDetailsByDate(@Query("employeeId") String str, @Query("date") String str2);

    @GET("/api/orders")
    Call<List<Ordering>> getOrdersByEmployee(@Query("employeeId") String str);

    @GET("/api/commute/recent")
    Call<CommuteModel> getRecentCommute(@Query("employeeId") String str, @Query("employeeName") String str2);

    @GET("/api/commute/today")
    Call<CommuteModel> getTodayCommute(@Query("employeeId") String str);

    @GET("/api/workplaces/{name}")
    Call<WorkPlace> getWorkPlaceByName(@Path("name") String str);

    @POST("/api/login")
    Call<EmployeeModel> login(@Body Map<String, String> map);

    @PUT("/api/employees/{id}")
    Call<EmployeeModel> updateEmployee(@Path("id") String str, @Body EmployeeModel employeeModel);

    @PUT("/api/goods/{barcode}")
    Call<GoodsModel> updateGoods(@Path("barcode") String str, @Body GoodsModel goodsModel);
}
