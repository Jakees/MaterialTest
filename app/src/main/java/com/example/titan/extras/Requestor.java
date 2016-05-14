package com.example.titan.extras;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.titan.adapters.AdapterYouku;
import com.example.titan.materialtest.R;

import org.json.JSONObject;


/**
 * Created by Windows on 02-03-2015.
 */
public class Requestor {

    private static final int SEARCH = 0;

    private static final int CATEGORY = 1;


    public static void sendRequestBySearch(RequestQueue requestQueue,final AdapterYouku adapter,
                                       final TextView textVolleyError,final ProgressBar progressBar,
                                       final SwipeRefreshLayout mswipeRefreshLayout){

        getJsonByGet(SEARCH,requestQueue,UrlEndPoints.getRequestUrlBySearch("keyword=忍龙"),
                adapter,textVolleyError,progressBar,mswipeRefreshLayout);

    }

    public static void sendRequestByCategory(RequestQueue requestQueue,final AdapterYouku adapter,
                                           final TextView textVolleyError,final ProgressBar progressBar,
                                           final SwipeRefreshLayout mswipeRefreshLayout){

        getJsonByGet(CATEGORY,requestQueue,UrlEndPoints.getRequestUrlByCategory("category=电视剧&genre=科幻"),
                adapter,textVolleyError,progressBar,mswipeRefreshLayout);

    }

    public static void sendRequestByMySearch(String keywords,RequestQueue requestQueue,final AdapterYouku adapter,
                                             final TextView textVolleyError,final ProgressBar progressBar,
                                             final SwipeRefreshLayout mswipeRefreshLayout){

        getJsonByGet(SEARCH,requestQueue,UrlEndPoints.getRequestUrlBySearch("keyword=" + keywords),
                adapter,textVolleyError,progressBar,mswipeRefreshLayout);

    }


    private static void getJsonByGet(final int flag,RequestQueue requestQueue,final String URL,final AdapterYouku adapter,
                              final TextView textVolleyError,final ProgressBar progressBar,
                              final SwipeRefreshLayout mswipeRefreshLayout){

        //开始发送请求显示进度条
        if (progressBar != null && progressBar.isShown() != true){
            progressBar.setVisibility(View.VISIBLE);
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET
                ,URL
                ,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //根据flag参数 调用不同解析数据方式
                switch (flag){

                    case 0:adapter.setData(YoukuParser.parseJSONBySearch(response));
                        break;

                    case 1:adapter.setData(YoukuParser.parseJSONByCategory(response));
                        break;

                }

                //获取请求成功停止显示进度条
                if (progressBar != null&& progressBar.isShown() == true){
                    progressBar.setVisibility(View.GONE);
                }

                //获取请求成功停止显示刷新控件
                if (mswipeRefreshLayout!= null && mswipeRefreshLayout.isRefreshing()){
                    mswipeRefreshLayout.setRefreshing(false);
                }

                //获取请求成功停止显示错误信息
                if (textVolleyError != null && textVolleyError.isShown() == true){
                    textVolleyError.setVisibility(View.GONE);
                }

            }
        }
                ,new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                //获取请求失败停止显示进度条
                if (progressBar != null && progressBar.isShown()){
                    progressBar.setVisibility(View.GONE);
                }

                //获取请求失败停止显示刷新控件
                if (mswipeRefreshLayout!= null && mswipeRefreshLayout.isRefreshing()){
                    mswipeRefreshLayout.setRefreshing(false);
                }

                //获取请求失败显示错误信息
                if (textVolleyError != null){
                    textVolleyError.setVisibility(View.VISIBLE);
                }

                if(error instanceof TimeoutError || error instanceof NoConnectionError){

                    textVolleyError.setText(R.string.error_timeout);

                }else if(error instanceof AuthFailureError){

                    textVolleyError.setText(R.string.error_auth_failure);

                }else if(error instanceof ServerError){

                    textVolleyError.setText(R.string.error_server_error);

                }else if(error instanceof NetworkError){

                    textVolleyError.setText(R.string.erroe_network);

                }else if(error instanceof ParseError){

                    textVolleyError.setText(R.string.erroe_parse_error);
                }

            }
        });

        requestQueue.add(request);

    }


}

