package com.example.titan.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.titan.materialtest.R;
import com.example.titan.pojo.Information;

import java.util.Collections;
import java.util.List;

/**
 * Created by TITAN on 2015/5/5.
 */
public class MyAdapterSettings extends RecyclerView.Adapter<MyAdapterSettings.MyViewHolder>{

    private String[] titles = null;

    private final LayoutInflater inflater;

    private Context context;

    //Steps 1
    public MyAdapterSettings(Context context, String[] titles){

        //Create an inflater,to Obtains the LayoutInflater from the given context.then use this LayoutInflater to convert the specific XML into View.

        inflater = LayoutInflater.from(context);

        this.titles = titles;
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

    //Steps 3
    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {

        myViewHolder.title.setText(titles[position]);

    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    /**
     *Put Class & interface here.
     */

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.listText);

        }

        @Override
        public void onClick(View v) {

        }
    }



}
