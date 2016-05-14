package com.example.titan.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by TITAN on 2015/4/26.
 */
public class YouKuVideos implements Parcelable{
    /**
     *
     *
     https://openapi.youku.com/v2/searches/video/by_keyword.json?client_id=b10ab8588528b1b1&keyword=%27%E5%BF%8D%E9%BE%99%27
     client_id=b10ab8588528b1b1
     */


    private String id="";

    private String title="";

    private String link="";

    private String Thumbnail="";


    public YouKuVideos(){}

    public YouKuVideos(Parcel input){
        id = input.readString();

        title = input.readString();

        link = input.readString();

        Thumbnail = input.readString();
    }



    public String getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        Thumbnail = thumbnail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        System.out.println("writeToParcel>??????????????????<<<<<<<<<<<<<<<<<<<");
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(link);
        dest.writeString(Thumbnail);

    }

    public static final Creator<YouKuVideos> CREATOR = new Creator<YouKuVideos>() {
        @Override
        public YouKuVideos createFromParcel(Parcel source) {
            System.out.println(" Creator createFromParcel>??????????????????<<<<<<<<<<<<<<<<<<<");
            return new YouKuVideos(source);
        }

        @Override
        public YouKuVideos[] newArray(int size) {
            System.out.println("newArray createFromParcel>??????????????????<<<<<<<<<<<<<<<<<<<");
            return new YouKuVideos[size];
        }
    };
}
