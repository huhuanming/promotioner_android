package com.promotioner.main.ApiManager;

import retrofit.RestAdapter;

/**
 * Created by chen on 14-7-24.
 */
public class MainApiManager {

    public static String path = "http://10.0.0.4:3000/api";
    public static final RestAdapter restAdapter = new RestAdapter.Builder()
            .setServer(path)
            .build();
}
