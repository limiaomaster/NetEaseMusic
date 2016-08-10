package com.stest.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.stest.InnerFragment.DynamicFragment;
import com.stest.InnerFragment.NearbyFragment;
import com.stest.InnerFragment.PartnerFragment;
import com.stest.neteasycloud.R;
import com.stest.view.NetEasyRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Limuyang on 2016/7/7.
 */
public class FriendFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    @ViewInject(R.id.friends_tab)
    private TabLayout friends_tab;
    @ViewInject(R.id.friends_viewPager)
    private ViewPager friends_viewPager;
    @ViewInject(R.id.refresh)
    private NetEasyRefreshLayout refreshLayout;
    @ViewInject(R.id.tv_refresh_indicator)
    private TextView textView;

    private List<String> mTitleList = new ArrayList<>(3);
    private List<Fragment> fragments = new ArrayList<>(3);
    private DynamicFragment dynamicFragment;
    private NearbyFragment nearbyFragment;
    private PartnerFragment partnerFragment;
    private View v;

    MyAdapter myAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void addView() {
        mTitleList.add("动态");
        mTitleList.add("附近");
        mTitleList.add("好友");
        if (dynamicFragment == null) {
            dynamicFragment = new DynamicFragment();
            fragments.add(dynamicFragment);
        }
        if (nearbyFragment == null) {
            nearbyFragment = new NearbyFragment();
            fragments.add(nearbyFragment);
        }
        if (partnerFragment == null) {
            partnerFragment = new PartnerFragment();
            fragments.add(partnerFragment);
        }

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (v != null) {
            ViewUtils.inject(this, v);
            return v;
        }
        v = inflater.inflate(R.layout.friend_fragment, container, false);
        ViewUtils.inject(this, v);
        addView();
        refreshLayout.setOnRefreshListener(this);   //  设置手势滑动监听器。
        refreshLayout.setColorSchemeResources(R.color.themeColor);  //  设置进度动画的颜色。
        myAdapter = new MyAdapter(getFragmentManager());
        myAdapter.notifyDataSetChanged();
        friends_viewPager.setAdapter(myAdapter);
        friends_viewPager.setOffscreenPageLimit(3);
        friends_tab.setTabMode(TabLayout.MODE_FIXED);
        friends_tab.setupWithViewPager(friends_viewPager);
        return v;
    }

    @Override
    public void onRefresh() {
        Log.v("onRefresh()","正在刷新，，，");
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mHandler1.sendEmptyMessage(1);
            }
        }).start();

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setVisibility(View.VISIBLE);
                if (refreshLayout.isRefreshing()) {
                    Log.v("onRefresh()","正在刷新，，，请勿操作，，，");
                    refreshLayout.setRefreshing(false); //  设置组件的刷洗状态。
                }
            }
        }, 2500);
        textView.setVisibility(View.INVISIBLE);*/
    }
    private Handler mHandler1 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    refreshLayout.setRefreshing(false);
                    myAdapter.notifyDataSetChanged();
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("刷新完成~");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            mHandler2.sendEmptyMessage(2);
                        }
                    }).start();
                    //swipeRefreshLayout.setEnabled(false);
                    break;
                default:
                    break;
            }
        }
    };

    private Handler mHandler2 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 2:
                    textView.setVisibility(View.INVISIBLE);
                    break;
            }
         }
    };


    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);
        }

    }
}
