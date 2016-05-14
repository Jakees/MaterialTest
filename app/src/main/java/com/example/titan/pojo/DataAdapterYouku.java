package com.example.titan.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TITAN on 2015/4/26.
 */
public class DataAdapterYouku {

    private static List<YouKuVideos> data = new ArrayList<>();

    public DataAdapterYouku(){}

    public void setData(List<YouKuVideos> data) {
        this.data = data;
    }

    public List<YouKuVideos> getData() {
        return data;
    }
}
