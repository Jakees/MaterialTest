package com.example.titan.activities;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import com.example.titan.materialtest.R;

import com.example.titan.adapters.AdapterRecyclerItemAnimations;

import jp.wasabeef.recyclerview.animators.FadeInAnimator;
import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;

/**
 * Created by TITAN on 2015/5/1.
 */
public class ActivityRecyclerItemAnimations extends ActionBarActivity {

    private EditText mInput;

    private RecyclerView mrecyclerView;

    private AdapterRecyclerItemAnimations mAdapter;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_item_animations);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("添加");

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mInput = (EditText) findViewById(R.id.text_input);
        mrecyclerView = (RecyclerView) findViewById(R.id.recyclerAnimatedItems);

//        DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
//
//        itemAnimator.setAddDuration(1000);
//        itemAnimator.setRemoveDuration(1000);

        FadeInLeftAnimator scaleInAnimator = new FadeInLeftAnimator();

        scaleInAnimator.setAddDuration(600);
        scaleInAnimator.setRemoveDuration(600);

        mrecyclerView.setItemAnimator(scaleInAnimator);
        mAdapter = new AdapterRecyclerItemAnimations(this);
        mrecyclerView.setAdapter(mAdapter);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    public void addItem(View view){

        if(mInput.getText() != null){
            String text = mInput.getText().toString();

            if(text != null && text.trim().length() > 0){
                mAdapter.addItem(mInput.getText().toString());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sub, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_settings:

                return true;

            case R.id.home:

                NavUtils.navigateUpFromSameTask(this);

                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
