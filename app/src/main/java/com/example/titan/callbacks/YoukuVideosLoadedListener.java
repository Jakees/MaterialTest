package com.example.titan.callbacks;

/**
 * Created by TITAN on 2015/5/2.
 */

import java.util.ArrayList;
import com.example.titan.pojo.YouKuVideos;

/**
 * Created by Windows on 02-03-2015.
 */
public interface YoukuVideosLoadedListener {
    public void onYoukuVideosLoaded(ArrayList<YouKuVideos> listVideos);
}
