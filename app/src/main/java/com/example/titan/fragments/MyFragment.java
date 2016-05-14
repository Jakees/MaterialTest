package com.example.titan.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.titan.materialtest.R;
import com.example.titan.network.VolleySingleton;

/**
 * Created by TITAN on 2015/4/24.
 */
public class MyFragment extends Fragment {

    private ImageView imageView;

    public static MyFragment getInstance(int position){

        MyFragment myFragment = new MyFragment();

        Bundle args = new Bundle();

        args.putInt("position",position);

        myFragment.setArguments(args);

        return myFragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_my,container,false);

        imageView = (ImageView) layout.findViewById(R.id.position);

        Bundle bundle = getArguments();


        if(bundle != null){

            imageView.setImageResource(R.drawable.nvidia2);

        }

        /**
         * Test VolleySingleton
         */

//        RequestQueue requestQueue = VolleySingleton.getsInstance().getmRequestQueue();
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET,"https://www.baidu.com/"
//         ,new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                Toast.makeText(getActivity(),response,Toast.LENGTH_SHORT).show();
//            }
//        },new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        requestQueue.add(stringRequest);
        return layout;
    }

}