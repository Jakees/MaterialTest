package com.example.titan.activities;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.titan.ListenerUtils.ClickListener;
import com.example.titan.ListenerUtils.RecyclerTouchListener;
import com.example.titan.adapters.AdapterYouku;
import com.example.titan.data_base_op.DBOperation;
import com.example.titan.extras.Requestor;
import com.example.titan.materialtest.MyApplication;
import com.example.titan.materialtest.R;
import com.example.titan.network.VolleySingleton;
import com.example.titan.pojo.UserStatue;
import com.example.titan.pojo.YouKuVideos;

import java.util.ArrayList;

/**
 * Created by TITAN on 2015/5/9.
 */
public class ActivityYoukuSearch extends ActionBarActivity implements SwipeRefreshLayout.OnRefreshListener {


    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private AdapterYouku adapter;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private TextView textVolleyError;
    private ProgressBar progressBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    ArrayList<YouKuVideos> listVideos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        volleySingleton = VolleySingleton.getsInstance();
        requestQueue = volleySingleton.getmRequestQueue();
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        recyclerView = (RecyclerView) findViewById(R.id.youkuSearch);
        adapter = new AdapterYouku(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView.setAdapter(adapter);

        // 给recyclerView添加OnClick监听事件

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,recyclerView,new ClickListener() {
            @Override
            public void onClick(View view, int position) {

//                Toast.makeText(MyApplication.getAppContext(), "onClick " + position, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onLongClick(View view, int position) {

//                Toast.makeText(MyApplication.getAppContext(), "onLongClick " + position, Toast.LENGTH_SHORT).show();

            }
        }));

        //Set the {@link LayoutManager} that this RecyclerView will use.
        //  new LinearLayoutManager(param)  --> @param context Current context, will be used to access resources.

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        progressBar = (ProgressBar) findViewById(R.id.loadingBarSearch);
        textVolleyError = (TextView) findViewById(R.id.textVolleyErrorSearch);
        //设置刷新属性
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeMovieHintsSearch);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,recyclerView , new ClickListener() {
            @Override
            public void onClick(View view, final int position) {

                final ArrayList<YouKuVideos> data = (ArrayList<YouKuVideos>) adapter.getData();
                //选择播放方式
                new AlertDialog.Builder(ActivityYoukuSearch.this).setTitle("选择播放方式(默认网页)")
                        .setMessage("要使用优酷播放吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //检查是否安装了指定的播放app
                                PackageManager pm =   ActivityYoukuSearch.this.getPackageManager();
                                boolean installed = false;
                                try {
                                    pm.getPackageInfo("com.example.playerdemo", PackageManager.GET_ACTIVITIES);
                                    installed = true;
                                } catch (PackageManager.NameNotFoundException e) {
                                    installed = false;
                                }
                                //判断是否安装了指定的播放app
                                if(installed){
                                    Toast.makeText(ActivityYoukuSearch.this,"安装了播放app: " + installed,Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent();
                                    intent.setComponent(new ComponentName("com.example.playerdemo", "com.example.playerdemo.PlayerActivity"));
                                    intent.putExtra("vid", data.get(position).getId());
                                    startActivity(intent);
                                }
                                else {

                                    new AlertDialog.Builder(ActivityYoukuSearch.this).setTitle("未找到优酷播放器")
                                            .setMessage("将使用网页播放")
                                            .setPositiveButton("确定", new DialogInterface.OnClickListener(){

                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent = new Intent(ActivityYoukuSearch.this, ActivityPlayYouku.class);
                                                    intent.putExtra("video_link",data.get(position).getLink());
                                                    startActivity(intent);
                                                }
                                            })
                                            .show();
                                }
                            }
                        })
                        .setNegativeButton("默认方式",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ActivityYoukuSearch.this, ActivityPlayYouku.class);
                                intent.putExtra("video_link",data.get(position).getLink());
                                startActivity(intent);
                            }
                        }).show();

            }

            @Override
            public void onLongClick(View view, int position) {
                //判断登录否
                if(UserStatue.getInstance().isUserLogined() == false){
                    Toast.makeText(ActivityYoukuSearch.this,"未登录！",Toast.LENGTH_SHORT).show();
                    return;
                }
                final YouKuVideos currentVideos = adapter.getData().get(position);
                new AlertDialog.Builder(ActivityYoukuSearch.this).setTitle("操作提示")
                        .setMessage("确认收藏该视频的所有信息吗？")
                        .setPositiveButton("收藏", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //插入数据库
                                DBOperation DBOp = DBOperation.getInstance();
                                //实例化一个ContentValues， ContentValues是以键值对的形式，键是数据库的列名，值是要插入的值
                                ContentValues cValues = new ContentValues();
                                cValues.put("id", currentVideos.getId().trim());
                                cValues.put("title", currentVideos.getTitle().trim());
                                cValues.put("link", currentVideos.getLink().trim());
                                cValues.put("thumbnail_url", currentVideos.getThumbnail().trim());
                                //调用insert插入数据库
                                DBOp.insertIntoMyDB(cValues);
                            }
                        })
                        .setNegativeButton("取消",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
            }
        }));


        if (textVolleyError.isShown()){
            textVolleyError.setVisibility(View.GONE);
        }

        Requestor.sendRequestByMySearch(getIntent().getStringExtra("keyword"),requestQueue, adapter, textVolleyError, progressBar, null);

    }


    @Override
    public void onRefresh() {
        Requestor.sendRequestByMySearch(getIntent().getStringExtra("keyword"),requestQueue, adapter, textVolleyError, progressBar, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sub, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings){

            return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
