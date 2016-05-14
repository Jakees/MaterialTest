package com.example.titan.extras;

/**
 * Created by TITAN on 2015/4/26.
 */
public class UrlEndPoints {

    public static final String URL_YOUKU_SEARCH = "https://openapi.youku.com/v2/searches/video/by_keyword.json";

    public static final String URL_YOUKU_CATEGORY = "https://openapi.youku.com/v2/shows/by_category.json";


    public static final String URL_CHAR_QUESTION = "?";

    public static final String URL_AMEPERCAND = "&";

    public static final String URL_CLIENT_ID = "client_id=b9b1f65c9119ab7f";



    public static String getRequestUrlBySearch(String limit){

        return UrlEndPoints.URL_YOUKU_SEARCH
                +UrlEndPoints.URL_CHAR_QUESTION
                +UrlEndPoints.URL_CLIENT_ID
                +UrlEndPoints.URL_AMEPERCAND
                +limit;

    }


    public static String getRequestUrlByCategory(String limit){

        return UrlEndPoints.URL_YOUKU_CATEGORY
                +UrlEndPoints.URL_CHAR_QUESTION
                +UrlEndPoints.URL_CLIENT_ID
                +UrlEndPoints.URL_AMEPERCAND
                +limit;

    }


}
