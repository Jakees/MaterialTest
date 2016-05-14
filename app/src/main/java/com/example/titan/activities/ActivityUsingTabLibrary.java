package com.example.titan.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import com.example.titan.fragments.MyFragment;
import com.example.titan.materialtest.R;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

/**
 * Created by TITAN on 2015/4/20.
 */
public class ActivityUsingTabLibrary extends ActionBarActivity implements MaterialTabListener{

    private Toolbar toolbar;

    private MaterialTabHost tabHost;

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_using_tab_library);



        toolbar = (Toolbar) findViewById(R.id.app_bar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        tabHost = (MaterialTabHost) findViewById(R.id.materialTabHost);

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());



        viewPager.setAdapter(viewPagerAdapter);


        /**
         * Step 3:
         * 设置ViewPager切换时候的监听事件
         */
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){

            @Override
            public void onPageSelected(int position) {

                /**
                 * Step 4:
                 * 页面滑动或者改变，顶部标签跟着改变
                 */
                tabHost.setSelectedNavigationItem(position);
            }
        });

        /**
         * Step 1:
         * 给TabHost添加TabListener监听事件监听顶部标签所有事件。
         */

        for (int i = 0; i < viewPagerAdapter.getCount(); i ++){

            tabHost.addTab(
                    tabHost.newTab()
                    .setIcon(viewPagerAdapter.getIcon(i))
                    .setTabListener(this));

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_activity_using_tab_library,menu);

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

    @Override
    public void onTabSelected(MaterialTab materialTab) {

        /**
         * Step 2:
         * 新的tab选中时候，切换viewpager
         */
        viewPager.setCurrentItem(materialTab.getPosition());

    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }



    private class ViewPagerAdapter extends FragmentPagerAdapter{

        int[] icons = {R.drawable.ic_action_home,R.drawable.ic_action_articles,R.drawable.ic_action_personal
        ,R.drawable.ic_action_home,R.drawable.ic_action_articles,R.drawable.ic_action_personal};

        public ViewPagerAdapter(FragmentManager fm) {

            super(fm);

        }
        /**
         * Step 2:
         *      Put the R.layout.fragment_my.xml into the ViewPagerAdapter
         */
        @Override
        public Fragment getItem(int position) {

            return MyFragment.getInstance(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return getResources().getStringArray(R.array.tabs)[position];

        }

        @Override
        public int getCount() {
            return 3;
        }

        private Drawable getIcon(int position){

            Drawable drawable = getResources().getDrawable(icons[position]);

            return drawable;

        }

    }

    /**
     * Step 1:
     *      To set the R.layout.fragment_my.xml then add it into the ViewPager
     */

//    public static class MyFragment extends Fragment{
//
//        private ImageView imageView;
//
//        public static MyFragment getInstance(int position){
//
//            MyFragment myFragment = new MyFragment();
//
//            Bundle args = new Bundle();
//
//            args.putInt("position",position);
//
//            myFragment.setArguments(args);
//
//            return myFragment;
//
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//
//            View layout = inflater.inflate(R.layout.fragment_my,container,false);
//
//            imageView = (ImageView) layout.findViewById(R.id.position);
//
//            Bundle bundle = getArguments();
//
//
//            if(bundle != null){
//
//                imageView.setImageResource(R.drawable.nvidia2);
//
//            }
//
//            return layout;
//        }
//
//    }


}
