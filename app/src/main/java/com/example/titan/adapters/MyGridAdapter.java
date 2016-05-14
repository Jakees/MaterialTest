package com.example.titan.adapters;

/**
 * Created by TITAN on 2015/6/21.
 */

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.titan.materialtest.R;


public class MyGridAdapter extends  BaseAdapter  {
    private Context context;
    private int[] imgs = {R.drawable.img_technology,R.drawable.img_games,
            R.drawable.img_movies,R.drawable.img_animation,
            R.drawable.img_technology,R.drawable.img_games,
            R.drawable.img_movies,R.drawable.img_animation};  //数据源
    private LayoutInflater layoutInflater;

    public MyGridAdapter(Context context) {
        // TODO Auto-generated constructor stub
        this.context=context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if(imgs == null){
            return 0;
        }
        return imgs.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return imgs[position];
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    //根据位置得到视图

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        //此处仅仅在最初执行加载布局，之后可从convertView中获得holder对其进行设置
        if(convertView == null){
            holder = new ViewHolder();

            convertView = layoutInflater.inflate(R.layout.grid_item_info, null);

            holder.imageView = (ImageView)convertView.findViewById(R.id.grid_item);

            //绑定ViewHolder对象
            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }


        holder.imageView.setImageResource(imgs[position]);
        return convertView;

    }

    public class ViewHolder {
        public ImageView imageView;
    }

}