package com.example.titan.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.titan.components.swipemenu.SwipeMenu;
import com.example.titan.components.swipemenu.SwipeMenuCreator;
import com.example.titan.components.swipemenu.SwipeMenuItem;
import com.example.titan.components.swipemenu.SwipeMenuListView;
import com.example.titan.data_base_op.DBOperation;
import com.example.titan.materialtest.R;
import com.example.titan.network.VolleySingleton;
import com.example.titan.pojo.YouKuVideos;

public class ActivityCheckMyVideos extends ActionBarActivity {

    private ArrayList<YouKuVideos> VideoList;
    private MyAdapter mAdapter;
    private Toolbar toolbar;

    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_my_videos);

        toolbar = (Toolbar) findViewById(R.id.app_bar);

        volleySingleton = VolleySingleton.getsInstance();
        imageLoader = volleySingleton.getImageLoader();

        toolbar.setTitle("我的收藏");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //create SwipeMenuListView
        SwipeMenuListView listView = (SwipeMenuListView) findViewById(R.id.listView);

        //初始化SwipeMenuCreator
        initSwipeMenuCreator(listView);
        //初始化My videos数据
        initVideoList();
        //适配器
        mAdapter = new MyAdapter();
        listView.setAdapter(mAdapter);

        //listener item click event
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // open
                        Toast.makeText(getApplicationContext(),"this is open on " + position  ,Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(ActivityCheckMyVideos.this, ActivityPlayYouku.class);

                        intent.putExtra("video_link",VideoList.get(position).getLink());

                        startActivity(intent);

                        break;
                    case 1:
                        // delete
                        new AlertDialog.Builder(ActivityCheckMyVideos.this).setTitle("操作提示")
                                .setMessage("确认要删除该条视频的所有信息吗？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //删除数据库对应数据
                                        DBOperation dbOperation = DBOperation.getInstance();

                                        dbOperation.deleteVideos(VideoList.get(position).getId().trim());

                                        VideoList.remove(position);
                                        mAdapter.notifyDataSetChanged();

                                    }
                                })
                                .setNegativeButton("取消",new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }).show();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }


    private void initSwipeMenuCreator(SwipeMenuListView listView) {

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(dp2px(90));
                // set item title
                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        listView.setMenuCreator(creator);
    }

    private void initVideoList() {
        VideoList = new ArrayList<YouKuVideos>();

        DBOperation DBOp = DBOperation.getInstance();
        Cursor c = DBOp.queryVideos();
        while(c.moveToNext()){
            YouKuVideos current = new YouKuVideos();

            current.setId(c.getString(c.getColumnIndex("id")));
            current.setTitle(c.getString(c.getColumnIndex("title")));
            current.setLink(c.getString(c.getColumnIndex("link")));
            current.setThumbnail(c.getString(c.getColumnIndex("thumbnail_url")));

            VideoList.add(current);
        }
        DBOp.close();
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return VideoList.size();
        }

        @Override
        public YouKuVideos getItem(int position) {
            return VideoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            // menu type count
            return 3;
        }

        @Override
        public int getItemViewType(int position) {
            // current menu type
            return position % 3;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(),
                        R.layout.item_list_app, null);
                new ViewHolder(convertView);
            }
            final ViewHolder holder = (ViewHolder) convertView.getTag();

            String urlThumbnail = VideoList.get(position).getThumbnail();
            //加载来自Thumbnail中的图片数据
            if (urlThumbnail != null){

                imageLoader.get(urlThumbnail,new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                        holder.iv_icon.setImageBitmap(response.getBitmap());
                    }
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
            }

            //video的title
            String title = VideoList.get(position).getTitle();
            holder.tv_name.setText(title);
            return convertView;
        }

        class ViewHolder {
            ImageView iv_icon;
            TextView tv_name;

            public ViewHolder(View view) {
                iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
                tv_name = (TextView) view.findViewById(R.id.tv_name);
                view.setTag(this);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_check_my_videos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
