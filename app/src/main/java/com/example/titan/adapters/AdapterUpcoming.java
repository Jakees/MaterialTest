package com.example.titan.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.titan.materialtest.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by TITAN on 2015/5/6.
 */
public class AdapterUpcoming extends RecyclerView.Adapter<AdapterUpcoming.MyViewHolder> {

    private final LayoutInflater inflater;

    private int[] img = {R.drawable.img_technology,R.drawable.img_games,
            R.drawable.img_movies,R.drawable.img_animation,};


    public AdapterUpcoming(Context context){

        this.inflater = LayoutInflater.from(context);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.custom_column_youku,parent,false);

        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

//        Bitmap bitmap = BitmapFactory.decodeResource(inflater.getContext().getResources(), img[position]);

        holder.imgTag.setImageResource(img[position]);

    }

    @Override
    public int getItemCount() {
        return img.length;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imgTag;
        public MyViewHolder(View itemView) {
            super(itemView);

            imgTag = (ImageView) itemView.findViewById(R.id.imgTag);

        }

        @Override
        public void onClick(View v) {

        }
    }

}
