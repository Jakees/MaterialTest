package com.example.titan.pojo;

import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.titan.materialtest.MyApplication;

import java.util.ArrayList;

/**
 * Created by TITAN on 2015/5/4.
 */
public class FinalAllYoukuDatas {

    private static RecyclerView.ViewHolder MyViewHolder = null;
    private static ArrayList<YouKuVideos> MyData = new ArrayList<>();

    public void FinalListYoukuVideos(){}

    public static RecyclerView.ViewHolder getViewHolder() {
        return MyViewHolder;
    }

    public static void setViewHolder(RecyclerView.ViewHolder viewHolder) {

        Toast.makeText(MyApplication.getAppContext(),"Put Viewholder into FinalAllYoukuDatas",Toast.LENGTH_SHORT).show();

        MyViewHolder = viewHolder;
    }







    public static ArrayList<YouKuVideos> getData() {
        return MyData;
    }

    public static void setData(ArrayList<YouKuVideos> data) {
        MyData = data;
    }

}
