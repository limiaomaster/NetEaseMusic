package com.stest.neteasycloud;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.stest.fragment.DiscoFragment;
import com.stest.fragment.FriendFragment;
import com.stest.fragment.MusicFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Limuyang
 */
public class HomePageActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    //  滑出的导航视图
    @ViewInject(R.id.navigation_view)
    private NavigationView mNavigationView;

    //  最顶端的工具条
    @ViewInject(R.id.tool_bar)
    private Toolbar toolbar;

    //  工具条左边的控制导航滑出的按钮
    @ViewInject(R.id.drawerIcon)
    private ImageView drawerIcon;

    //  整个DrawerLayout
    @ViewInject(R.id.drawer)
    private DrawerLayout mDrawerLayout;

    //  工具条的第一个ImageView
    @ViewInject(R.id.bar_disco)
    private ImageView bar_disco;

    @ViewInject(R.id.bar_music)
    private ImageView bar_music;

    @ViewInject(R.id.bar_friends)
    private ImageView bar_friends;

    @ViewInject(R.id.view_pager)
    private ViewPager view_pager;

    @ViewInject(R.id.play_btn)
    private ImageView play_btn;

    @ViewInject(R.id.bottom_music_more)
    private LinearLayout bottom_music_more;

    @ViewInject(R.id.search_layout)
    private LinearLayout search_layout;

    private boolean isOpen;

    //ToolBar三个按钮对应的Fragment
    private List<Fragment> fragmentlist = new ArrayList<>(3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.home_page);
        ViewUtils.inject(this);
        addFragment();
        initWidgets();

        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                isOpen = true;
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                isOpen = false;
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, HomePageActivity.class);
        context.startActivity(intent);
    }

    private void initWidgets() {
        setSupportActionBar(toolbar);

        //  setItemIconTintList （null)设置menu菜单的icon颜色为本色，
        // 也可以在NavigationView布局文件中设置。
        mNavigationView.setItemIconTintList(null);

        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        view_pager.setAdapter(adapter);

        view_pager.addOnPageChangeListener(this);

        drawerIcon.setOnClickListener(this);

        //  给三个按钮添加监听器
        bar_disco.setOnClickListener(this);
        bar_music.setOnClickListener(this);
        bar_friends.setOnClickListener(this);

        search_layout.setOnClickListener(this);
        //初始化显示位置
        bar_music.setSelected(true);
        view_pager.setCurrentItem(1);

    }

    private void addFragment() {
        fragmentlist.add(new DiscoFragment());
        fragmentlist.add(new MusicFragment());
        fragmentlist.add(new FriendFragment());
    }



    //  由于实现了 View.OnClickListener 这个接口，所以要重写该接口中的所有方法。
    //  该接口中只有一个方法。
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.drawerIcon:
                if (!isOpen) {
                    //LEFT和RIGHT指的是现存DrawerLayout的方向
                    mDrawerLayout.openDrawer(GravityCompat.START);
                    isOpen = true;
                }
            case R.id.bar_disco:
                bar_disco.setSelected(true);
                bar_friends.setSelected(false);
                bar_music.setSelected(false);
                view_pager.setCurrentItem(0);
                break;
            case R.id.bar_music:
                bar_music.setSelected(true);
                bar_friends.setSelected(false);
                bar_disco.setSelected(false);
                view_pager.setCurrentItem(1);
                break;
            case R.id.bar_friends:
                bar_friends.setSelected(true);
                bar_music.setSelected(false);
                bar_disco.setSelected(false);
                view_pager.setCurrentItem(2);
                break;
            case R.id.search_layout:
                break;
        }

    }

    //  由于实现了ViewPager.OnPageChangeListener这个接口，所以要重写该接口中的所有方法。
    //  该接口中有三个方法。
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                bar_disco.setSelected(true);
                bar_friends.setSelected(false);
                bar_music.setSelected(false);
                break;
            case 1:
                bar_music.setSelected(true);
                bar_friends.setSelected(false);
                bar_disco.setSelected(false);
                break;
            case 2:
                bar_friends.setSelected(true);
                bar_music.setSelected(false);
                bar_disco.setSelected(false);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }






    class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentlist.get(position);
        }

        @Override
        public int getCount() {
            return fragmentlist.size();
        }
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mDrawerLayout.closeDrawer(mNavigationView);
            isOpen = false;
        }
        //return super.onKeyDown(keyCode, event);
        return  true;
    }
}
