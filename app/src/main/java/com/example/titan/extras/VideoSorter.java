package com.example.titan.extras;

import com.example.titan.pojo.YouKuVideos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by TITAN on 2015/4/29.
 */
public class VideoSorter {

    public void sortByName(ArrayList<YouKuVideos> youKuVideoses){

        Collections.sort(youKuVideoses,new Comparator<YouKuVideos>() {
            @Override
            public int compare(YouKuVideos lhs, YouKuVideos rhs) {
                return lhs.getTitle().compareTo(rhs.getTitle());
            }
        });

    }

    public void sortByID(ArrayList<YouKuVideos> youKuVideoses){

        Collections.sort(youKuVideoses,new Comparator<YouKuVideos>() {
            @Override
            public int compare(YouKuVideos lhs, YouKuVideos rhs) {
                return lhs.getId().compareTo(rhs.getId());
            }
        });

    }

    public void sortByLink(ArrayList<YouKuVideos> youKuVideoses){

        Collections.sort(youKuVideoses,new Comparator<YouKuVideos>() {
            @Override
            public int compare(YouKuVideos lhs, YouKuVideos rhs) {
                return lhs.getLink().compareTo(rhs.getLink());
            }
        });

    }

}
