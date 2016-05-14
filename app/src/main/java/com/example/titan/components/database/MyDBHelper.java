package com.example.titan.components.database;

/**
 * Created by TITAN on 2015/6/18.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by TITAN on 2015/6/18.
 */


public class MyDBHelper extends SQLiteOpenHelper {

    private final static int VERSION = 1;
    private final static String DB_NAME = "my_videos.db";
    private final static String TABLE_VIDEO = "video";
    private final static String TABLE_USER = "user";
    private final static String CREATE_TBL_VIDEO = "create table video(id text, " +
            "title text, " +
            "link text, " +
            "thumbnail_url text)";

    private final static String CREATE_TBL_USER = "create table user(_id integer primary key autoincrement, " +
            "username text, " +
            "password text)";

    private SQLiteDatabase db;

    //SQLiteOpenHelper子类必须要的一个构造函数
    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int version) {
        //必须通过super 调用父类的构造函数
        super(context, name, factory, version);
    }
    //数据库的构造函数，传递三个参数的
    public MyDBHelper(Context context, String name, int version){
        this(context, name, null, version);
    }
    //数据库的构造函数，传递一个参数的， 数据库名字和版本号都固定
    public MyDBHelper(Context context){
        this(context, DB_NAME, null, VERSION);
    }


    // 回调函数，第一次创建时才会调用此函数，创建一个数据库表
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TBL_USER);
        db.execSQL(CREATE_TBL_VIDEO);

    }
    //回调函数，当你构造DBHelper的传递的Version与之前的Version调用此函数
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("update Database");

    }
    //插入用户方法
    public void addUser(ContentValues values){
        //获取SQLiteDatabase实例
        SQLiteDatabase db = getWritableDatabase();
        //插入数据库中
        db.insert(TABLE_USER, null, values);

        db.close();

    }
    //插入一条video方法
    public void insert(ContentValues values){
        //获取SQLiteDatabase实例
        SQLiteDatabase db = getWritableDatabase();
        //插入数据库中
        db.insert(TABLE_VIDEO, null, values);

        db.close();

    }

    //查询用户
    public Cursor queryUser(){
        db = getReadableDatabase();
        //获取Cursor
        Cursor c = db.query(TABLE_USER, null, null, null, null, null, null, null);
        return c;

    }
    //查询video
    public Cursor queryVideos(){
        db = getReadableDatabase();
        //获取Cursor
        Cursor c = db.query(TABLE_VIDEO, null, null, null, null, null, null, null);
        return c;

    }
//    //查询方法2
//    public Cursor queryByStudyOrLife(String condition){
//        db = getReadableDatabase();
//        //获取Cursor
//        Cursor c = db.query(TABLE_VIDEO, null, "category=?", new String[]{condition}, null, null, null);
//        return c;
//    }

    //根据唯一标识_id  来删除数据
    public void deleteVideo(String id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_VIDEO, "id=?", new String[]{id});
        db.close();

    }
    //更新数据库的内容
    public void update(ContentValues values, String whereClause, String[]whereArgs){
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_VIDEO, values, whereClause, whereArgs);
        db.close();
    }
    //关闭数据库
    public void close(){
        if(db != null){
            db.close();
        }
    }

    //清楚用户表
    public void dropUser(){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("user",null,null);
        db.close();

    }
}
