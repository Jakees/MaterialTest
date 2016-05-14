package com.example.titan.extras;

/**
 * Created by TITAN on 2015/4/25.
 */
import android.os.Build;
public class Util {

    public static boolean isLoolipopOrGreater(){

        return Build.VERSION.SDK_INT>=21? true:false;
    }

    public static boolean isJellyBeanOrGreater(){

        return Build.VERSION.SDK_INT>=16? true:false;
    }

}
