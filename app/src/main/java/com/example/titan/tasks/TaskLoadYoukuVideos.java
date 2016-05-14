package com.example.titan.tasks;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.titan.adapters.AdapterYouku;
import com.example.titan.extras.Requestor;
import com.example.titan.network.VolleySingleton;
import com.example.titan.pojo.FinalAllYoukuDatas;

/**
 * Created by TITAN on 2015/5/4.
 */


public class TaskLoadYoukuVideos extends AsyncTask<Void, Void, FinalAllYoukuDatas> {

    private VolleySingleton mVolleySingleton;
    private RequestQueue mRequestQueue;

    private TextView errorTextView;
    private ProgressBar mProgressBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private AdapterYouku mAdapterYouku;
    private FinalAllYoukuDatas finalListYoukuVideos;
    private int flag = -1;

    //构造TaskLoadYoukuVideos实例
    public TaskLoadYoukuVideos(int flag,AdapterYouku mAdapterYouku,TextView errorTextView,
                               ProgressBar mProgressBar,SwipeRefreshLayout mSwipeRefreshLayout) {

        this.flag = flag;

        this.mAdapterYouku = mAdapterYouku;

        this.errorTextView = errorTextView;

        this.mProgressBar = mProgressBar;

        this.mSwipeRefreshLayout = mSwipeRefreshLayout;

        mVolleySingleton = VolleySingleton.getsInstance();

        mRequestQueue = mVolleySingleton.getmRequestQueue();

        finalListYoukuVideos = new FinalAllYoukuDatas();
    }


    @Override
    protected FinalAllYoukuDatas doInBackground(Void... params) {

        switch (flag){
            case 0:Requestor.sendRequestBySearch(mRequestQueue,mAdapterYouku,errorTextView, mProgressBar,mSwipeRefreshLayout);
                break;

            case 1:Requestor.sendRequestByCategory(mRequestQueue,mAdapterYouku,errorTextView, mProgressBar,mSwipeRefreshLayout);
                break;
        }

        return finalListYoukuVideos;
    }


    @Override
    protected void onPostExecute(FinalAllYoukuDatas finalListYoukuVideos) {
//        System.out.println("From Search : data:" + finalListYoukuVideos.getData().size()+">>>>>>>>>>>>>>>>>>>>>>>>???????????????????????");
    }
}

