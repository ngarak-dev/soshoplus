/*******************************************************************************
 * Ngara K. Android Application Developer
 * ngarakiringo@gmail.com
 * Copyright (c) 2020
 ******************************************************************************/

package com.soshoplus.lite.utils;

import de.adorsys.android.securestoragelibrary.SecurePreferences;
import dev.DevUtils;

public class constants {

    public static String userId = SecurePreferences.getStringValue(DevUtils.getContext(), "userId", "0");
    public static String timezone = SecurePreferences.getStringValue(DevUtils.getContext(), "timezone", "UTC");
    public static String accessToken = SecurePreferences.getStringValue(DevUtils.getContext(), "accessToken"
            , "0");

    /*initializing query*/
    public static queries rxJavaQueries = retrofitInstance.getInstRxJava().create(queries.class);

    /*..........*/
    public static String fetch_profile_user_data = "user_data";
}
