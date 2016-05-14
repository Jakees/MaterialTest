package com.example.titan.data_base_op;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.titan.components.database.MyDBHelper;

/**
 * Created by TITAN on 2015/6/18.
 */

public class DBOperation {

    private static DBOperation instance;

    private MyDBHelper dbHelper;

    private DBOperation (MyDBHelper dbHelper){
        this.dbHelper = dbHelper;
    }

    public static synchronized DBOperation getInstance(MyDBHelper dbHelper) {
        if (instance == null) {
            instance = new DBOperation(dbHelper);
        }
        return instance;
    }

    public static synchronized DBOperation getInstance() {
        return instance;
    }

    //插入一条video到数据库
    public void insertIntoMyDB(ContentValues values){
        dbHelper.insert(values);
    }

    //插入一条用户到数据库
    public void insertUserIntoMyDB(ContentValues values){
        dbHelper.addUser(values);
    }
    //查询video
    public Cursor queryVideos(){
        //获取Cursor
        Cursor c = dbHelper.queryVideos();
        return c;
    }

    //查询用户
    public Cursor queryUser(){
        Cursor c = dbHelper.queryUser();
        return c;

    }
    //删除一条video
    public void deleteVideos(String videosID){
        dbHelper.deleteVideo(videosID);
    }

    //关闭查询
    public void close(){
        dbHelper.close();
    }

    //清空用户表数据
    public void clearUser(){
        dbHelper.dropUser();
    }
}