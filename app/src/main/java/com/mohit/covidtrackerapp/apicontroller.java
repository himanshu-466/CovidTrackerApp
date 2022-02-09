package com.mohit.covidtrackerapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class apicontroller {
    public  static Retrofit retrofit = null;
    public static apiset getApiset()
    {
        if(retrofit==null)
        {
            retrofit = new Retrofit.Builder().baseUrl(apiset.BASEURl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit.create(apiset.class) ;
    }

}
