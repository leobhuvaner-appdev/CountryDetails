package com.example.samplecountrydetails.network;

import com.example.samplecountrydetails.model.AllCountryDetailsListModel;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("rest/v2/all")
    Call<List<AllCountryDetailsListModel>> getAllCountryDetails();
}
