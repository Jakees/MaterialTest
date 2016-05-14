package com.example.titan.ListenerUtils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by TITAN on 2015/5/2.
 */

//创建实现RecyclerView监听事件的类
public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

    private GestureDetector gestureDetector;
    private ClickListener clickListener;

    //Initialize the GestureDetector and specific MotionEvent handle methods.---> onSingleTapUp() & onLongPress()

    public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener ){

        this.clickListener = clickListener;
        //定义要处理的不同手势
        gestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener() {

            //Add another MotionEvent methods here and handle them.

            @Override
            public boolean onSingleTapUp(MotionEvent e) {

                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {

                View child = recyclerView.findChildViewUnder(e.getX(),e.getY());

                if (child != null && clickListener != null){

                    clickListener.onLongClick(child,recyclerView.getChildPosition(child));

                }

            }
        });
    }

    //拦截不同的手势事件进行处理.

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

        View child = rv.findChildViewUnder(e.getX(),e.getY());

        boolean b = gestureDetector.onTouchEvent(e);


        if (child != null && clickListener != null && b){

            clickListener.onClick(child,rv.getChildPosition(child));

        }

        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }
}