package com.example.titan.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.titan.materialtest.R;


public class ActivityToSearch extends ActionBarActivity implements SearchView.OnQueryTextListener {

    private SearchView sv;
    private ListView lv;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

//        toolbar = (Toolbar) findViewById(R.id.app_bar_search);
//
//        setSupportActionBar(toolbar);
//
//        getSupportActionBar().setHomeButtonEnabled(true);
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sv = (SearchView) findViewById(R.id.sv);
        // 设置该SearchView默认是否自动缩小为图标
        sv.setIconifiedByDefault(false);
        // 为该SearchView组件设置事件监听器
        sv.setOnQueryTextListener(this);
        // 设置该SearchView显示搜索按钮
        sv.setSubmitButtonEnabled(true);
        // 设置该SearchView内默认显示的提示文本
        sv.setQueryHint("查找");

        sv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ActivityToSearch.this,MainActivity.class));

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sub, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        // 实际应用中应该在该方法内执行实际查询
        // 此处仅使用Toast显示用户输入的查询内容

        Intent intent = new Intent(this,ActivityYoukuSearch.class);

        intent.putExtra("keyword",query);

        startActivity(intent);


        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
//        Toast.makeText(SubActivity.this, "textChange--->" + newText,Toast.LENGTH_SHORT).show();
//        if (TextUtils.isEmpty(newText)) {
//            // 清除ListView的过滤
//            lv.clearTextFilter();
//        } else {
//            // 使用用户输入的内容对ListView的列表项进行过滤
//            lv.setFilterText(newText);
//        }
        return false;
    }
}
