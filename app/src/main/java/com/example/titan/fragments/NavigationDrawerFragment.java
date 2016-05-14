package com.example.titan.fragments;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.titan.ListenerUtils.ClickListener;
import com.example.titan.ListenerUtils.RecyclerTouchListener;
import com.example.titan.activities.ActivityCheckMyVideos;
import com.example.titan.activities.ActivityRegister;
import com.example.titan.activities.MainActivity;
import com.example.titan.adapters.MyAdapter;
import com.example.titan.data_base_op.DBOperation;
import com.example.titan.my_threads.LoginThread;
import com.example.titan.pojo.Information;
import com.example.titan.materialtest.R;
import com.example.titan.pojo.UserStatue;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */

/**
 * STEPS TO HANDLE THE RECYCLER CLICK
 *
 * 1.Create a class that extends RecyclerView.OnItemTouchListener.
 *
 * 2.Create an interface inside that class that supports click and long click and indicates the View that was clicked and the position where was clicked.
 *
 * 3.Create a GestureDetector to detect ACTION_UP single tap and Long Press events.
 *
 * 4.Return true from the SingleTap to indicate your GestureDetector hos consumed the event.
 *
 * 5.Find the ChildView containing the coordinates specified by the  MotionEvent and the ChildView is not null and the Listener is not null either fire a long click event.
 *
 *6.Use the OnInterceptTouchEvent of your RecyclerView to check if the ChildView is not null and the Listener id not null.and the Gesture Detector consume the touch event.
 */


public class NavigationDrawerFragment extends Fragment{
    private AlertDialog mLoginDialog;
    private ProgressDialog progressDialog;

    private MainActivity parentActivity;

    private LayoutInflater inflater;
    private ViewGroup container;
    private Button mLoginButton;
    private Button mCheckVideosButton;
    private ImageView mImageLogin;
    private RecyclerView mRecyclerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private MyAdapter adapter;


    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    //登录操作完成的处理,设置登录状态
                    UserStatue userStatue = UserStatue.getInstance();
                    userStatue.setUSER_STATE(UserStatue.LOGINED);

                    mLoginButton.setText("已登录");
                    mImageLogin.setImageResource(R.drawable.logined);
                    progressDialog.dismiss();
                    break;

            }
        }
    };

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //Find the mRecyclerView

        this.inflater = inflater;
        this.container = container;

        View layout =  inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        mRecyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);
        mLoginButton = (Button) layout.findViewById(R.id.btn_login);
        mImageLogin = (ImageView) layout.findViewById(R.id.image_login);
        mCheckVideosButton = (Button) layout.findViewById(R.id.btn_check_my_videos);

        //设置登录监听事件
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //检查是否已经登录（否则是登出操作）
                final UserStatue userStatue = UserStatue.getInstance();

                if(userStatue.isUserLogined()){

                    new AlertDialog.Builder(getActivity()).setTitle("登出提示")
                            .setMessage("确认登出吗？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //登出操作
                                    userStatue.setUSER_STATE(UserStatue.UNLOGINED);
                                    mLoginButton.setText("登录");
                                    mImageLogin.setImageResource(R.drawable.login);
                                }
                            })
                            .setNegativeButton("取消",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).show();
                }
                else{
                    //初始化登录框
                    initLoginDialog(UserStatue.getInstance());
                    mLoginDialog.show();
                }
            }
        });
        //设置按钮按下时候的颜色
        mCheckVideosButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //更改为按下时的颜色
                    v.setBackgroundColor(Color.parseColor("#ffaa7f71"));
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    //改为抬起时的颜色
                    v.setBackgroundColor(Color.parseColor("#ff7dd7fa"));
                }

                return false;
            }
        });
        //设置查看我的视频监听事件
        mCheckVideosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取用户状态类实例SingleTone
                UserStatue userStatue = UserStatue.getInstance();
                //判断用户是否已经登录
                if(userStatue.isUserLogined()){
                    Intent intent = new Intent(getActivity(),ActivityCheckMyVideos.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getActivity(),"您还未登录!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Set up the content of the recyclerView

        adapter = new MyAdapter(getActivity(),getData());

        //Put the adapter into the recyclerView

        mRecyclerView.setAdapter(adapter);

        // 给recyclerView添加OnClick监听事件

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),mRecyclerView,new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getActivity(),"未获得视频资源" + position,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getActivity(),"未获得视频资源" + position,Toast.LENGTH_SHORT).show();
            }
        }));
        //Set the {@link LayoutManager} that this RecyclerView will use.
        //  new LinearLayoutManager(param)  --> @param context Current context, will be used to access resources.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return layout;

    }

    //初始化登录对话框
    private void initLoginDialog(final UserStatue userStatue) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View viewLogin = inflater.inflate(R.layout.login,container,false);

        final EditText editTextUsername = (EditText) viewLogin.findViewById(R.id.et_user_name);
        final EditText editTextPassword = (EditText) viewLogin.findViewById(R.id.et_password);

        final Button loginButton = (Button) viewLogin.findViewById(R.id.btn_login);
        final CheckBox checkBox = (CheckBox) viewLogin.findViewById(R.id.checkbox_keep_user_info);
        TextView tvRegister = (TextView) viewLogin.findViewById(R.id.tv_register);

        //获取之前保存在UserState的UserInfo
        if(userStatue.isUserInfoSaved()){

            editTextUsername.setText(userStatue.getUserName());
            editTextPassword.setText(userStatue.getPassword());
//            radioButton.setSelected(true);
            checkBox.setChecked(true);
        }
        else{
//            Toast.makeText(getActivity(), "上次用户状态未保存" , Toast.LENGTH_SHORT).show();
        }

        //登录事件处理
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameFromDB;
                String passwordFromDB;
                String name;
                String password;

                name = editTextUsername.getText().toString();
                password = editTextPassword.getText().toString();

                //数据库获取用户
                DBOperation dbOperation = DBOperation.getInstance();
                Cursor c = dbOperation.queryUser();
                if(c.getCount() == 0){
                    Toast.makeText(getActivity(), "用户不存在，请先注册！", Toast.LENGTH_SHORT).show();
                    return;
                }

                while (c.moveToNext()){

                    nameFromDB = c.getString(c.getColumnIndex("username"));
                    passwordFromDB = c.getString(c.getColumnIndex("password"));
                    //用户信息校验
                    if(name.equals(nameFromDB) == false){
                        Toast.makeText(getActivity(), "用户名不存在！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if(password.equals(passwordFromDB) == false){
                        Toast.makeText(getActivity(), "密码错误！", Toast.LENGTH_SHORT).show();
                        editTextPassword.setText("");
                        return;
                    }else{
                        break;
                    }
                }
                //关闭数据库
                dbOperation.close();

                //模拟服务器登录显示进度框
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Loading...");
                progressDialog.setTitle("正在获取登录信息");
                progressDialog.show();
                Thread thread = new Thread(new LoginThread(handler));
                thread.start();

                //隐藏用户登录框
                mLoginDialog.dismiss();

                //登录时是否要记住用户名密码
                if(checkBox.isChecked()){
                    userStatue.setUserInfoSaved(true);
                    userStatue.setUserName(name);
                    userStatue.setPassword(password);
                    parentActivity.saveUserInfo(userStatue);
                }else {
                    //否则清楚重置上次记录的信息
                    userStatue.setUserInfoSaved(false);
                    userStatue.setUserName("");
                    userStatue.setPassword("");
                    parentActivity.clearSaveUserInfo();
                    //不清空EditText会自动保留上次输入的内容
                    editTextUsername.setText("");
                    editTextPassword.setText("");
                }

            }
        });

        //跳转到注册用户
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityRegister.class);
                startActivity(intent);
                mLoginDialog.dismiss();
            }
        });
        //设置登录对话框布局内容
        mBuilder.setView(viewLogin);
        mLoginDialog = mBuilder.create();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        //返回一个Boolean值-->此Drawer之前是否打开过
//        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(),KEY_USR_LEARNED_DRAWER,"false"));
//
//        if(savedInstanceState != null){
//            mFromSavedInstanceState = true;
//        }

    }

    public static List<Information> getData(){

        List<Information> data = new ArrayList<>();

        int[] icons = {R.drawable.img_youku,
                R.drawable.img_baidu,
                R.drawable.img_tencent,
                R.drawable.img_aiqiyi,
        };

        String[] titles = {"Youku","Baidu","Tencent","Aiqiyi"};

        for(int i =0;i<4; i++){
            Information current = new Information();

            current.setIconId(icons[i%4]);

            current.setTitle(titles[i%4]);

            data.add(current);
        }
        return data;

    }


    //Set up the drawerLayout
    public void setUp(MainActivity parentActivity,DrawerLayout drawerLayout, final Toolbar toolBar) {

        this.mDrawerLayout = drawerLayout;

        this.parentActivity = parentActivity;

        mDrawerToggle = new ActionBarDrawerToggle(getActivity(),drawerLayout,toolBar,R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {

                super.onDrawerOpened(drawerView);

                getActivity().invalidateOptionsMenu();

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                getActivity().invalidateOptionsMenu();

            }

            //设置左抽屉栏滑动时候ToolBar的透明效果
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                if(slideOffset < 0.5){

                    toolBar.setAlpha(1 - slideOffset);
                }
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();

    }

//    //创建实现RecyclerView监听事件的类
//    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{
//
//        private GestureDetector gestureDetector;
//        private ClickListener clickListener;
//
//        //Initialize the GestureDetector and specific MotionEvent handle methods.---> onSingleTapUp() & onLongPress()
//
//        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener ){
//
//
//            this.clickListener = clickListener;
//
//            gestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener() {
//
//                //Add another MotionEvent methods here and handle them.
//
//                @Override
//                public boolean onSingleTapUp(MotionEvent e) {
//
//                    return true;
//                }
//
//                @Override
//                public void onLongPress(MotionEvent e) {
//
//                    View child = recyclerView.findChildViewUnder(e.getX(),e.getY());
//
//                    if (child != null && clickListener != null){
//
//                        clickListener.onLongClick(child,recyclerView.getChildPosition(child));
//
//                    }
//
//                }
//            });
//        }
//
//        //Intercept the MotionEvent and let the GestureDetector to handle them.
//
//        @Override
//        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
//
//            View child = rv.findChildViewUnder(e.getX(),e.getY());
//
//            boolean b = gestureDetector.onTouchEvent(e);
//
//
//            if (child != null && clickListener != null && b){
//
//                clickListener.onClick(child,rv.getChildPosition(child));
//
//            }
//
//            return false;
//        }
//
//        @Override
//        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
//
//        }
//    }
//
//    public static interface ClickListener{
//        public void onClick(View view,int position);
//
//        public void onLongClick(View view,int position);
//    }

}
