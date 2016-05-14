package com.example.titan.fragments;


import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.RequestQueue;

import com.example.titan.ListenerUtils.ClickListener;
import com.example.titan.ListenerUtils.RecyclerTouchListener;
import com.example.titan.activities.ActivityCheckMyVideos;
import com.example.titan.activities.ActivityPlayYouku;
import com.example.titan.adapters.AdapterYouku;
import com.example.titan.data_base_op.DBOperation;
import com.example.titan.extras.Requestor;
import com.example.titan.extras.SortListener;
import com.example.titan.extras.VideoSorter;
import com.example.titan.materialtest.R;
import com.example.titan.network.VolleySingleton;
import com.example.titan.pojo.UserStatue;
import com.example.titan.pojo.YouKuVideos;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentYouKuTest#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentYouKuTest extends Fragment implements SortListener, SwipeRefreshLayout.OnRefreshListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String CLICKED_POSITION = "position";
    private static final String STATE_VIDEOS = "state_videos";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView textVolleyError;
    private ProgressBar progressBar;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;

//    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private RecyclerView mRecyclerViewMovie;
    private AdapterYouku mAdapterYouku;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    ArrayList<YouKuVideos> listVideos = new ArrayList<>();

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentYouKuTest.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentYouKuTest newInstance(String param1, String param2,int position) {
        FragmentYouKuTest fragment = new FragmentYouKuTest();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putInt(CLICKED_POSITION, position);

        fragment.setArguments(args);
        return fragment;
    }

    public FragmentYouKuTest() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_you_ku_test, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.loadingBar);
        textVolleyError = (TextView) view.findViewById(R.id.textVolleyError);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeMovieHints);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);

        mRecyclerViewMovie = (RecyclerView) view.findViewById(R.id.recyclerViewMovie);

        mAdapterYouku = new AdapterYouku(getActivity());
        mRecyclerViewMovie.setAdapter(mAdapterYouku);


        //RecyclerView添加单击选择播放事件
        mRecyclerViewMovie.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),mRecyclerViewMovie,new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                final ArrayList<YouKuVideos> data = (ArrayList<YouKuVideos>) mAdapterYouku.getData();

                //选择播放方式
                new AlertDialog.Builder(getActivity()).setTitle("选择播放方式(默认网页)")
                        .setMessage("要使用优酷播放吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //检查是否安装了指定的播放app
                                PackageManager pm =   getActivity().getPackageManager();
                                boolean installed = false;
                                try {
                                    pm.getPackageInfo("com.example.playerdemo", PackageManager.GET_ACTIVITIES);
                                    installed = true;
                                } catch (PackageManager.NameNotFoundException e) {
                                    installed = false;
                                }
                                //判断是否安装了指定的播放app
                                if(installed){
//                                    Toast.makeText(getActivity(),"播放app: " + installed,Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent();
                                    intent.setComponent(new ComponentName("com.example.playerdemo", "com.example.playerdemo.PlayerActivity"));
                                    intent.putExtra("vid", data.get(position).getId());
                                    startActivity(intent);
                                }
                                else {

                                    new AlertDialog.Builder(getActivity()).setTitle("未找到优酷播放器")
                                            .setMessage("将使用网页播放")
                                            .setPositiveButton("确定", new DialogInterface.OnClickListener(){
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    ArrayList<YouKuVideos> data = (ArrayList<YouKuVideos>) mAdapterYouku.getData();
                                                    Intent intent = new Intent(getActivity(), ActivityPlayYouku.class);
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
                                Intent intent = new Intent(getActivity(), ActivityPlayYouku.class);
                                intent.putExtra("video_link",data.get(position).getLink());
                                startActivity(intent);
                            }
                        }).show();

            }

            //RecyclerView中的Item长按收藏事件
            @Override
            public void onLongClick(View view, int position) {
                final YouKuVideos currentVideos = mAdapterYouku.getData().get(position);
                //判断登录否
                if(UserStatue.getInstance().isUserLogined() == false){
                    Toast.makeText(getActivity(),"未登录！",Toast.LENGTH_SHORT).show();
                    return;
                }
                new AlertDialog.Builder(getActivity()).setTitle("操作提示")
                        .setMessage("确定要收藏该视频的所有信息吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
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
                                Toast.makeText(getActivity(),"收藏成功！",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("取消",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
            }
        }));

        mRecyclerViewMovie.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (textVolleyError.isShown()){
            textVolleyError.setVisibility(View.GONE);
        }

        if(savedInstanceState != null){

            listVideos = savedInstanceState.getParcelableArrayList(STATE_VIDEOS);

            mAdapterYouku.setData(listVideos);
        }else {

//            TaskLoadYoukuVideos task = new TaskLoadYoukuVideos(0,mAdapterYouku,textVolleyError,
//                    progressBar,null);
//
//            task.execute();

            Requestor.sendRequestBySearch(requestQueue, mAdapterYouku,textVolleyError,progressBar,null);

        }
        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            volleySingleton = VolleySingleton.getsInstance();

            requestQueue = volleySingleton.getmRequestQueue();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(STATE_VIDEOS,listVideos);
    }



    @Override
    public void sortByName() {
        VideoSorter videoSorter = new VideoSorter();

        listVideos = (ArrayList<YouKuVideos>) mAdapterYouku.getData();

        videoSorter.sortByName(listVideos);
        mAdapterYouku.notifyDataSetChanged();
    }

    @Override
    public void sortByID() {
        VideoSorter videoSorter = new VideoSorter();

        listVideos = (ArrayList<YouKuVideos>) mAdapterYouku.getData();

        videoSorter.sortByID(listVideos);
        mAdapterYouku.notifyDataSetChanged();
    }

    @Override
    public void sortByLink() {
        VideoSorter videoSorter = new VideoSorter();

        listVideos = (ArrayList<YouKuVideos>) mAdapterYouku.getData();

        videoSorter.sortByLink(listVideos);
        mAdapterYouku.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {

//        TaskLoadYoukuVideos task = new TaskLoadYoukuVideos(0,mAdapterYouku,textVolleyError,
//                null,mSwipeRefreshLayout);
//
//        task.execute();

        Requestor.sendRequestBySearch(requestQueue,mAdapterYouku,textVolleyError,null,mSwipeRefreshLayout);

    }

}
