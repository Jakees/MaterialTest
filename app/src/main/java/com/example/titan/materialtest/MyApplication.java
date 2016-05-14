package com.example.titan.materialtest;

import android.app.Application;
import android.content.Context;

/**
 * Created by TITAN on 2015/4/24.
 */
public class MyApplication extends Application {

    public static final String API_KEY_YOUKU = "b10ab8588528b1b1";

    private static MyApplication sInstance;
    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
    }

    public static MyApplication getsInstance(){
        return sInstance;
    }


    public static Context getAppContext(){

        return sInstance.getApplicationContext();

    }


}
