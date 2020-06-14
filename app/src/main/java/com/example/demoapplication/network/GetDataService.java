package com.example.demoapplication.network;

import com.example.demoapplication.model.ParkingCar;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GetDataService {

    @GET("/alloted/slots")
    Call<List<ParkingCar>> getAllParkingSlots();

    @GET("/allotedcar/{id}")
    Call<List<ParkingCar>> getParkingSlots(String id);

    @POST("/allot")
    Call <ParkingCar> allotNewparkingslot(@Body Map<String,String> body);

    @DELETE("/remove/{id}")
    Call<Boolean> remove(@Path("id") String id);
}


