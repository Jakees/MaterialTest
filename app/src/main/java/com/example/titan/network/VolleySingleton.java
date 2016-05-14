package com.example.titan.network;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.titan.materialtest.MyApplication;

/**
 * Created by TITAN on 2015/4/24.
 */
public class VolleySingleton {

    private static VolleySingleton sInstance = null;

    private ImageLoader imageLoader;

    private RequestQueue mRequestQueue;

    private VolleySingleton(){

        mRequestQueue = Volley.newRequestQueue(MyApplication.getsInstance());

        imageLoader = new ImageLoader(mRequestQueue,new ImageLoader.ImageCache() {

            //设置可用缓存的大小为应用最大可用内存的1/8
            private LruCache<String,Bitmap> cache = new LruCache<>((int)Runtime.getRuntime().maxMemory()/8);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {

                cache.put(url,bitmap);
            }
        });

    }
    public static VolleySingleton getsInstance(){
        if (sInstance == null){
            sInstance = new VolleySingleton();
        }
        return sInstance;
    }

    public RequestQueue getmRequestQueue(){

        return mRequestQueue;
    }

    public ImageLoader getImageLoader(){

        return imageLoader;
    }

}
