package com.example.titan.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.titan.animations.AnimationUtils;
import com.example.titan.components.database.MyDBHelper;
import com.example.titan.data_base_op.DBOperation;
import com.example.titan.extras.SortListener;
import com.example.titan.fragments.FragmentCategory;
import com.example.titan.fragments.FragmentUpcoming;
import com.example.titan.fragments.FragmentYouKuTest;
import com.example.titan.fragments.NavigationDrawerFragment;
import com.example.titan.materialtest.R;
import com.example.titan.pojo.UserStatue;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;


public class MainActivity extends ActionBarActivity implements MaterialTabListener, View.OnClickListener, SearchView.OnQueryTextListener{

    private static final String TAG_SORT_NAME = "sortName";

    private static final String TAG_SORT_ID = "sortID";

    private static final String TAG_SORT_LINK = "sortLink";


    private MyDBHelper dbHelper;

    private DrawerLayout drawerLayout;

    private Toolbar toolBar;

    private MaterialTabHost tabHost;

    private ViewPager viewPager;

    private ViewPagerAdapter viewPagerAdapter;

//    private ViewPager mPager;
//    private SlidingTabLayout mTabs;

    public static final int MOVIES_SEARCH_RESULTS = 2;

    public static final int MOVIES_HITS = 1;

    public static final int MOVIES_UPCOMING = 0;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化数据库操作类
        dbHelper = new MyDBHelper(getApplicationContext());
        DBOperation dbOperation = DBOperation.getInstance(dbHelper);

        //读取存储配置文件获取保存的用户名密码
        sp = getSharedPreferences("app_data", Context.MODE_PRIVATE);

        String name = sp.getString("USER_NAME","null");
        String password = sp.getString("PASSWORD","null");

        if (name != "null" && password != "null"){

            //从SharedPreferences获取用户信息保存到UserStatue
            UserStatue userStatue = UserStatue.getInstance();
            userStatue.setUserInfoSaved(true);
            userStatue.setUserName(name);
            userStatue.setPassword(password);

        }

        //设置ToolBar
        toolBar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolBar);

        //设置加载MainActivity时候的ToolBar动画
        AnimationUtils.animateToolbarDroppingDown(toolBar);

        /**
         * 设置左抽屉栏drawerLayout
         */
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        /**
         * 通过FragmentManager找到并设置NavigationDrawerFragment
         */
        NavigationDrawerFragment navigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        navigationDrawerFragment.setUp(this,drawerLayout,toolBar);

        tabHost = (MaterialTabHost) findViewById(R.id.main_materialTabHost);
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(viewPagerAdapter);

        /**
         * 设置ViewPager切换时候的监听事件
         */
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){

            @Override
            public void onPageSelected(int position) {
                /**
                 * 页面滑动或者改变，顶部标签跟着改变
                 */
                tabHost.setSelectedNavigationItem(position);
            }
        });

        //设置顶部标签tabHost
        for (int i = 0; i < viewPagerAdapter.getCount(); i ++){
            tabHost.addTab(
                    tabHost.newTab()
                    .setText(viewPagerAdapter.getPageTitle(i))
                    .setTabListener(this));
//            tabHost.addTab(
//                    tabHost.newTab()
//                            .setIcon(viewPagerAdapter.getIcon(i))
//                            .setTabListener(this));
        }

        /**
         * 设置悬浮按钮
         */
        // in Activity Context
        ImageView icon = new ImageView(this); // Create an icon
        icon.setImageResource(R.drawable.ic_action_new);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .setBackgroundDrawable(R.drawable.selector_button_red)
                .build();

        actionButton.setAlpha(0.8f);

        ImageView iconSortName = new ImageView(this);
        iconSortName.setImageResource(R.drawable.ic_action_articles);

        ImageView iconSortID = new ImageView(this);
        iconSortID.setImageResource(R.drawable.ic_action_home);

        ImageView iconSortLink = new ImageView(this);
        iconSortLink.setImageResource(R.drawable.ic_action_personal);

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);

        SubActionButton buttonSortName = itemBuilder.setContentView(iconSortName).build();
        SubActionButton buttonSortID = itemBuilder.setContentView(iconSortID).build();
        SubActionButton buttonSortLink = itemBuilder.setContentView(iconSortLink).build();

        buttonSortName.setTag(TAG_SORT_NAME);
        buttonSortID.setTag(TAG_SORT_ID);
        buttonSortLink.setTag(TAG_SORT_LINK);

        buttonSortName.setOnClickListener(this);
        buttonSortID.setOnClickListener(this);
        buttonSortLink.setOnClickListener(this);

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(buttonSortName)
                .addSubActionView(buttonSortID)
                .addSubActionView(buttonSortLink)
                .attachTo(actionButton)
                .build();

    }

    public void saveUserInfo(UserStatue userStatue){
        SharedPreferences.Editor editor = sp.edit();

        editor.putString("USER_NAME",userStatue.getUserName());
        editor.putString("PASSWORD",userStatue.getPassword());
        editor.commit();
    }

    public void clearSaveUserInfo(){
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_main, menu);

//        MenuItem item = menu.findItem(R.id.action_search);
//
//        SearchView searchView = (SearchView) item.getActionView();

//        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        switch (item.getItemId()){

            case R.id.action_settings:

                startActivity(new Intent(this,SettingsActivity.class));

//                drawerLayout.openDrawer(R.id.fragment_navigation_drawer_right);

                break;

            case R.id.action_search:

                startActivity(new Intent(this,ActivityToSearch.class));

                break;

//            case R.id.navigate_to_tab:
//
//                startActivity(new Intent(this,ActivityUsingTabLibrary.class));
//
//                break;
//
//            case R.id.recyclerAnimatedItems:
//
//                startActivity(new Intent(this,ActivityRecyclerItemAnimations.class));
//
//                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UserStatue.getInstance().setUSER_STATE(UserStatue.UNLOGINED);
    }

    /**
     * MaterialTabListener_Methods
     * @param materialTab
     */
    @Override
    public void onTabSelected(MaterialTab materialTab) {
        viewPager.setCurrentItem(materialTab.getPosition());
    }
    /**
     * MaterialTabListener_Methods
     * @param materialTab
     */
    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }
    /**
     * MaterialTabListener_Methods
     * @param materialTab
     */
    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }

    @Override
    public void onClick(View v) {
        Fragment fragment = (Fragment) viewPagerAdapter.instantiateItem(viewPager,viewPager.getCurrentItem());
        if (fragment instanceof SortListener){

            if (v.getTag().equals(TAG_SORT_NAME)){
                ((SortListener) fragment).sortByName();
            }
            if(v.getTag().equals(TAG_SORT_ID)){
                ((SortListener) fragment).sortByID();
            }
            if (v.getTag().equals(TAG_SORT_LINK)){
                ((SortListener) fragment).sortByLink();
            }

        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        return false;
    }


    private class ViewPagerAdapter extends FragmentPagerAdapter{


        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Step 2:
         *      Put the R.layout.fragment_my.xml into the ViewPagerAdapter
         */
        @Override
        public Fragment getItem(int position) {

            Fragment fragment = null;

            switch (position){

                case MOVIES_UPCOMING:

                    fragment = FragmentUpcoming.newInstance("", "");

                    break;

                case MOVIES_HITS:

                    fragment = FragmentCategory.newInstance("", "");
                    break;

                case MOVIES_SEARCH_RESULTS:

                    fragment = FragmentYouKuTest.newInstance("", "",position);
                    break;
            }

            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return getResources().getStringArray(R.array.tabs)[position];

        }

        @Override
        public int getCount() {
            return 3;
        }

//        private Drawable getIcon(int position){
//
//            Drawable drawable = getResources().getDrawable(icons[position]);
//
//            return drawable;
//
//        }

    }


    //    class ViewPagerAdapter extends FragmentStatePagerAdapter{
//
////        int[] icons = {R.drawable.ic_action_home,R.drawable.ic_action_articles,R.drawable.ic_action_personal};
//
//        int[] icons = {R.drawable.vector_android,R.drawable.vector_android,R.drawable.vector_android};
//
//        String[] tabText = getResources().getStringArray(R.array.tabs);
//
//        public ViewPagerAdapter(FragmentManager fm) {
//
//            super(fm);
//
//            tabText = getResources().getStringArray(R.array.tabs);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//
////            MyFragment myFragment = MyFragment.getInstance(position);
////
////            return myFragment;
//
//            Fragment fragment = null;
//
//            switch (position){
//
//                case MOVIES_SEARCH_RESULTS:
//
//                    fragment = FragmentSearch.newInstance("","");
//                    break;
//
//                case MOVIES_HITS:
//
//                    fragment = FragmentBoxOffice.newInstance("", "");
//                    break;
//
//                case MOVIES_UPCOMING:
//                    fragment = FragmentUpcoming.newInstance("", "");
//                    break;
//
//            }
//
//            return fragment;
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//
//            Drawable drawable = getResources().getDrawable(icons[position]);
//
//            drawable.setBounds(0,0,90,90);
//
//            ImageSpan imageSpan = new ImageSpan(drawable);
//
//            SpannableString spannableString = new SpannableString(" ");
//
//            spannableString.setSpan(imageSpan,0,spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//            return spannableString;
//
//        }
//
//        private Drawable getIcon(int position){
//
//            Drawable drawable = getResources().getDrawable(icons[position]);
//
//            return drawable;
//
//        }
//
//        @Override
//        public int getCount() {
//            return 3;
//        }
//    }


    /**
     * put them into onCreate Method
     */

    //        /**
//         * Set ViewPager
//         */
//        mPager = (ViewPager) findViewById(R.id.pager);
//
//        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
//
//
//        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
//
//
//        mTabs.setCustomTabView(R.layout.custom_tab_view,R.id.tabText);
//
//        mTabs.setDistributeEvenly(true);
//
//        mTabs.setSelectedIndicatorColors(getResources().getColor(R.color.colorAccent));
//
//        mTabs.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//
//        mTabs.setViewPager(mPager);
}


