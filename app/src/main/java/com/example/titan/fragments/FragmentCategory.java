package com.example.titan.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.titan.activities.ActivityPlayYouku;
import com.example.titan.adapters.AdapterYouku;
import com.example.titan.extras.Requestor;
import com.example.titan.materialtest.R;
import com.example.titan.network.VolleySingleton;
import com.example.titan.pojo.YouKuVideos;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCategory#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCategory extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String STATE_CATEGORY_VIDEOS = "state_videos_category";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private TextView textVolleyError;
    private ProgressBar progressBar;

    //    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private RecyclerView mRecyclerView;
    private AdapterYouku mAdapterYouku;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    ArrayList<YouKuVideos> listVideos = new ArrayList<>();


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSearch.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentCategory newInstance(String param1, String param2) {
        FragmentCategory fragment = new FragmentCategory();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentCategory() {
        // Required empty public constructor
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        System.out.println("Category onCreateView>??????????????????<<<<<<<<<<<<<<<<<<<");
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_category, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.loadingBarCategory);
        textVolleyError = (TextView) view.findViewById(R.id.textVolleyErrorCategory);
        //设置刷新属性
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeMovieHintsCategory);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewMovieCategory);
        mAdapterYouku = new AdapterYouku(getActivity());
        mRecyclerView.setAdapter(mAdapterYouku);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 给recyclerView添加OnClick监听事件

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {

                new AlertDialog.Builder(getActivity()).setTitle("播放提示")
                        .setMessage("视频分类信息将使用WebView网页来加载")
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

            @Override
            public void onLongClick(View view, int position) {

                Toast.makeText(getActivity(), "onLongClick " + position, Toast.LENGTH_SHORT).show();

            }
        }));

        if (textVolleyError.isShown()){
            textVolleyError.setVisibility(View.GONE);
        }

        if(savedInstanceState != null){

//            System.out.println("Category onCreateView>savedInstanceState != null??????????????????<<<<<<<<<<<<<<<<<<<" + listVideos.size());
            listVideos = savedInstanceState.getParcelableArrayList(STATE_CATEGORY_VIDEOS);

            mAdapterYouku.setData(listVideos);
        }else {

//            TaskLoadYoukuVideos task = new TaskLoadYoukuVideos(1,mAdapterYouku,textVolleyError,
//                    progressBar,null);
//
//            task.execute();
//            System.out.println("Search onCreateView>sendRequestByCategory != null??????????????????<<<<<<<<<<<<<<<<<<<" );
            Requestor.sendRequestByCategory(requestQueue, mAdapterYouku, textVolleyError, progressBar,null);

        }
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(STATE_CATEGORY_VIDEOS,listVideos);
    }

    @Override
    public void onRefresh() {

//        TaskLoadYoukuVideos task = new TaskLoadYoukuVideos(1,mAdapterYouku,textVolleyError,
//                null,mSwipeRefreshLayout);
//
//        task.execute();
        Requestor.sendRequestByCategory(requestQueue,mAdapterYouku,textVolleyError,null,mSwipeRefreshLayout);

    }
}
