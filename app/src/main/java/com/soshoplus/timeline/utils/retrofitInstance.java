/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.timeline.utils;

import io.reactivex.rxjava3.internal.schedulers.RxThreadFactory;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressWarnings("WeakerAccess")
public class retrofitInstance {
    
    private static final String SOSHOPLUS_BASE_URL = "https://soshoplus.com/api/";
    //defining and declaring base url
    private static Retrofit retrofit;
    private static Retrofit retrofit_;
    
//    public static Retrofit getRetrofitInst () {
//        //initializing instance
//        if (retrofit == null) {
//            retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(SOSHOPLUS_BASE_URL).build();
//        }
//
//        return retrofit;
//    }
    
    public static Retrofit getInstRxJava () {
        //initializing instance
        if (retrofit_ == null) {
            retrofit_ =
                    new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava3CallAdapterFactory
                                    .createWithScheduler(Schedulers.trampoline()))
                            .baseUrl(SOSHOPLUS_BASE_URL).build();
        }
        
        return retrofit_;
    }
}
