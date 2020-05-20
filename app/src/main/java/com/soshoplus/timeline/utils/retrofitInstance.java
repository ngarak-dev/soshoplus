/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressWarnings("WeakerAccess")
public class retrofitInstance {
    
    private static final String SOSHOPLUS_BASE_URL = "https://soshoplus.com/api/";
    //defining and declaring base url
    private static Retrofit retrofit;
    
    public static Retrofit getRetrofitInst () {
        //initializing instance
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(SOSHOPLUS_BASE_URL).build();
        }
        
        return retrofit;
    }
}
