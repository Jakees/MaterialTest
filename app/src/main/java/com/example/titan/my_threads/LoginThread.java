package com.example.titan.my_threads;

import android.os.Handler;

/**
 * Created by TITAN on 2015/6/21.
 */
public class LoginThread implements Runnable {

    private Handler handler;

    public LoginThread(Handler handler){
        this.handler = handler;
    }

    @Override
    public void run() {
        try{
            //模拟登录，从服务器获取登录资料等等
            Thread.sleep(800);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        //登录完成
        handler.sendEmptyMessage(0);
    }
}
