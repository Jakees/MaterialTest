package com.example.titan.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.titan.ListenerUtils.ClickListener;
import com.example.titan.ListenerUtils.RecyclerTouchListener;
import com.example.titan.adapters.MyAdapter;
import com.example.titan.adapters.MyAdapterSettings;
import com.example.titan.animations.AnimationUtils;
import com.example.titan.materialtest.MyApplication;
import com.example.titan.materialtest.R;
import com.example.titan.pojo.Information;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by TITAN on 2015/4/25.
 */
public class SettingsActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private MyAdapterSettings adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("设置");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.drawerListSettings);

        //Set up the content of the recyclerView

        adapter = new MyAdapterSettings(this,getData());

        //Put the adapter into the recyclerView

        recyclerView.setAdapter(adapter);

        // 给recyclerView添加OnClick监听事件

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,recyclerView,new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                if (position == 0){
                    showPopupWindow(view);
                }
            }
            @Override
            public void onLongClick(View view, int position) {

                Toast.makeText(MyApplication.getAppContext(),"onLongClick " + position,Toast.LENGTH_SHORT).show();

            }
        }));

        //Set the {@link LayoutManager} that this RecyclerView will use.
        //  new LinearLayoutManager(param)  --> @param context Current context, will be used to access resources.

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


    public static String[] getData(){

        String[] titles = {"设置视频滑动效果","设置XXX","设置XXX","设置XXX"};

        return titles;

    }


    private void showPopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(MyApplication.getAppContext()).inflate(
                R.layout.animation_setting_pop_window, null);
        // 设置按钮的点击事件
        Button button1 = (Button) contentView.findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AnimationUtils.setDEFAULT_HOLDER_ANIMATION("ZoomInLeft");
                Toast.makeText(MyApplication.getAppContext(), "设置完成",
                        Toast.LENGTH_SHORT).show();

            }
        });

        Button button2 = (Button) contentView.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AnimationUtils.setDEFAULT_HOLDER_ANIMATION("ZoomInRight");
                Toast.makeText(MyApplication.getAppContext(), "设置完成",
                        Toast.LENGTH_SHORT).show();

            }
        });

        Button button3 = (Button) contentView.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AnimationUtils.setDEFAULT_HOLDER_ANIMATION("ZoomInDown");
                Toast.makeText(MyApplication.getAppContext(), "设置完成",
                        Toast.LENGTH_SHORT).show();

            }
        });


        final PopupWindow popupWindow = new PopupWindow(contentView,
                350, 400, true);


        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.i("mengdd", "onTouch : ");

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.vector_android));


        // 设置好参数之后再show
        popupWindow.showAsDropDown(view);

    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vector_test, menu);
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
