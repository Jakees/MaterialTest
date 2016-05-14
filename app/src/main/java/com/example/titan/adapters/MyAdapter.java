package com.example.titan.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.titan.pojo.Information;
import com.example.titan.materialtest.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by TITAN on 2015/4/17.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<Information> data =Collections.emptyList();


    private final LayoutInflater inflater;

    private Context context;

    //Steps 1
    public MyAdapter(Context context, List<Information> data){

        //Create an inflater,to Obtains the LayoutInflater from the given context.then use this LayoutInflater to convert the specific XML into View.

        //从一个Context中，获得一个布局填充器，使用这个填充器来把xml布局文件转为View对象
        inflater = LayoutInflater.from(context);

        this.data = data;
    }

    //Steps 2
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = inflater.inflate(R.layout.custom_row, viewGroup, false);

        context = view.getContext();

        // To put the R.layout.custom_row into the MyViewHolder
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    //Steps 3 设置一个ViewHolder中需现实的控件
    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {

        Information current = data.get(position);

        myViewHolder.title.setText(current.getTitle());

        myViewHolder.icon.setImageResource(current.getIconId());

    }

    //返回需显示的ViewHolder的数量
    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     *Put Class & interface here.
     */

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;
        ImageView icon;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.listText);

            icon = (ImageView) itemView.findViewById(R.id.listIcon);

        }

        @Override
        public void onClick(View v) {

        }
    }


}
