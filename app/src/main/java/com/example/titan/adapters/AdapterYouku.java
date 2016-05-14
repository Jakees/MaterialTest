package com.example.titan.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.titan.animations.AnimationUtils;
import com.example.titan.materialtest.MyApplication;
import com.example.titan.materialtest.R;
import com.example.titan.network.VolleySingleton;
import com.example.titan.pojo.FinalAllYoukuDatas;
import com.example.titan.pojo.Information;
import com.example.titan.pojo.YouKuVideos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by TITAN on 2015/4/26.
 */

public class AdapterYouku extends RecyclerView.Adapter<AdapterYouku.MyViewHolder> {

    private List<YouKuVideos> data = new ArrayList<>();

    private final LayoutInflater inflater;
    private VolleySingleton volleySingleton;

    private ImageLoader imageLoader;

    //Steps 1
    public AdapterYouku(Context context){

        //Create an inflater,to Obtains the LayoutInflater from the given context.then use this LayoutInflater to convert the specific XML into View.

        inflater = LayoutInflater.from(context);

        volleySingleton = VolleySingleton.getsInstance();

        imageLoader = volleySingleton.getImageLoader();

    }

    public void setData(List<YouKuVideos> data){

        this.data = data;

        notifyItemRangeChanged(0,data.size());

    }

    public List<YouKuVideos> getData(){
        return data;
    }

    //Steps 2
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = inflater.inflate(R.layout.custom_movie_youku, viewGroup, false);

        // To put the R.layout.custom_row into the MyViewHolder
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, int position) {

        //绑定当前RecyclerView中一个Item的数据
        YouKuVideos current = data.get(position);

        myViewHolder.title.setText(current.getTitle()+"\n"
        +current.getLink());

        String urlThumbnail = current.getThumbnail();

        //加载解析JSON中的图片数据
        if (urlThumbnail != null){

            imageLoader.get(urlThumbnail,new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    myViewHolder.imageViewThumbnail.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
        }

        //设置animate效果
        AnimationUtils.animate(myViewHolder);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    /**
     *Put Class & interface here.
     */

    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;
        ImageView imageViewThumbnail;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.movieMessage);
            imageViewThumbnail = (ImageView) itemView.findViewById(R.id.videoThumbnail);

        }

        @Override
        public void onClick(View v) {

        }
    }

}
