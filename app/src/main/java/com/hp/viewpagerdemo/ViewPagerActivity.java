package com.hp.viewpagerdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;

import com.hp.viewpagerdemo.adapter.ViewPagerAdapter;

public class ViewPagerActivity extends AppCompatActivity {
    private String[] TITLES = {"语文", "数学", "英语", "化学", "物理", "生物"};

    SwipeRefreshLayout mSwipeRefreshLayout;
    TabLayout mTabLayout;
    ViewPager mViewPager;

    private ViewPagerAdapter mAdapter;
    private int mCurrentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), TITLES);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(TITLES.length - 1);
        mTabLayout.setupWithViewPager(mViewPager);

        final Handler handler = new Handler(Looper.getMainLooper());
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void loadData() {
        String[] titles = {"语文1", "数学1", "英语1", "化学1", "物理1", "生物1"};
        mAdapter.setTitles(titles);
    }

    @Deprecated
    private void errorLoadData(){
        String[] titles = {"语文1", "数学1", "英语1", "化学1", "物理1", "生物1"};
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), titles);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(mCurrentIndex);
        mViewPager.setOffscreenPageLimit(titles.length - 1);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
