package com.example.titan.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.titan.materialtest.R;

import java.util.ArrayList;

/**
 * Created by TITAN on 2015/5/1.
 */
public class AdapterRecyclerItemAnimations extends RecyclerView.Adapter<AdapterRecyclerItemAnimations.Holder>{

    private ArrayList<String> mListData = new ArrayList<>();
    private LayoutInflater mLayoutInflater;


    public AdapterRecyclerItemAnimations(Context context){

        mLayoutInflater = LayoutInflater.from(context);

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View customRow = mLayoutInflater.inflate(R.layout.custom_row_items_animation,parent,false);

        Holder holder = new Holder(customRow);

        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {

        String data = mListData.get(position);

        holder.getTextItem().setText(data);

        holder.getButtonDelete().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                removeItem(position);

            }
        });

    }

    public void addItem(String item){

        mListData.add(item);

        notifyItemInserted(mListData.size());

    }


    public void removeItem(int position){
        mListData.remove(position);

        notifyItemRemoved(position);
        notifyItemRangeChanged(0,mListData.size());
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    public static class Holder extends RecyclerView.ViewHolder{


        private TextView textItem;
        private ImageButton buttonDelete;

        public TextView getTextItem() {
            return textItem;
        }

        public ImageButton getButtonDelete() {
            return buttonDelete;
        }

        public Holder(View itemView) {
            super(itemView);

            textItem = (TextView) itemView.findViewById(R.id.text_item);

            buttonDelete = (ImageButton) itemView.findViewById(R.id.button_delete);


        }
    }
}
