package com.example.titan.animations;

import android.support.v7.widget.RecyclerView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.titan.materialtest.MyApplication;

/**
 * Created by TITAN on 2015/5/1.
 */

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;


/**
 * Created by Windows on 04-03-2015.
 */
public class AnimationUtils {
    private static int counter = 0;
    private static String DEFAULT_HOLDER_ANIMATION = "BounceIn";
    private static Techniques animation = Techniques.BounceIn;

    public static void animate(RecyclerView.ViewHolder viewHolder){

        YoYo.with(animation)
                .duration(400)
                .playOn(viewHolder.itemView);

    }

    public static void setDEFAULT_HOLDER_ANIMATION(String DEFAULT_HOLDER_ANIMATION) {
        AnimationUtils.DEFAULT_HOLDER_ANIMATION = DEFAULT_HOLDER_ANIMATION;

        switch (DEFAULT_HOLDER_ANIMATION){

            case "BounceIn":
                animation = Techniques.BounceIn;
                break;
            case "Bounce":
                animation = Techniques.Bounce;
                break;
            case "SlideInLeft":
                animation = Techniques.SlideInLeft;
                break;
            case "ZoomIn":
                animation = Techniques.ZoomIn;
                break;
            case "ZoomInDown":
                animation = Techniques.ZoomInDown;
                break;
            case "ZoomInLeft":
                animation = Techniques.ZoomInLeft;
                break;
            case "ZoomInRight":
                animation = Techniques.ZoomInRight;
                break;
            case "ZoomInUp":
                animation = Techniques.ZoomInUp;
                break;
            case "ZoomOut":
                animation = Techniques.ZoomOut;
                break;
        }
    }

    public static void scaleXY(RecyclerView.ViewHolder holder) {
        holder.itemView.setScaleX(0);
        holder.itemView.setScaleY(0);

        PropertyValuesHolder propx = PropertyValuesHolder.ofFloat("scaleX", 1);
        PropertyValuesHolder propy = PropertyValuesHolder.ofFloat("scaleY", 1);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(holder.itemView, propx, propy);

        animator.setDuration(800);
        animator.start();
    }

    public static void scaleX(RecyclerView.ViewHolder holder) {
        holder.itemView.setScaleX(0);

        PropertyValuesHolder propx = PropertyValuesHolder.ofFloat("scaleX", 1);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(holder.itemView, propx);


        animator.setDuration(800);
        animator.start();
    }

    public static void scaleY(RecyclerView.ViewHolder holder) {
        holder.itemView.setScaleY(0);

        PropertyValuesHolder propy = PropertyValuesHolder.ofFloat("scaleY", 1);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(holder.itemView, propy);

        animator.setDuration(800);
        animator.start();
    }

    public static void animateToolbarDroppingDown(View containerToolbar) {

        containerToolbar.setRotationX(-45);
        containerToolbar.setAlpha(0.2F);
        containerToolbar.setPivotX(0.0F);
        containerToolbar.setPivotY(0.0F);

        Animator alpha = ObjectAnimator.ofFloat(containerToolbar, "alpha", 0.2F, 0.4F, 0.6F, 0.8F, 1.0F).setDuration(4000);
        Animator rotationX = ObjectAnimator.ofFloat(containerToolbar, "rotationX", -45, 45, -10, 30, 0, 20, 0, 5, 0).setDuration(5000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.playTogether(alpha, rotationX);
        animatorSet.start();
    }

}

