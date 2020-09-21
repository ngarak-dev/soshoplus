/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.utils;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressWarnings("WeakerAccess")
public class retrofitInstance {
    //defining and declaring base url
    private static Retrofit retrofit;

    public static Retrofit getInstRxJava() {
        //initializing instance
        if (retrofit == null) {
            retrofit =
                    new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                            .baseUrl(constants.SOSHOPLUS_BASE_URL).build();
        }

        return retrofit;
    }
}
