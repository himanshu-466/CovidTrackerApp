package com.mohit.covidtrackerapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface apiset {
    static  final String BASEURl ="https://corona.lmao.ninja/v2/";

    @GET("countries")
    Call<List<countrydata>> getCountryData();
}
