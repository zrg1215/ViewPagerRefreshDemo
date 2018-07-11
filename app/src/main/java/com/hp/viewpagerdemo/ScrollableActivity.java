package com.hp.viewpagerdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;

import com.hp.viewpagerdemo.adapter.ScrollableAdapter;
import com.hp.viewpagerdemo.scrollablelayout.ScrollableLayout;

public class ScrollableActivity extends AppCompatActivity {
    private String[] TITLES = {"语文", "数学", "英语", "化学", "物理", "生物"};

    SwipeRefreshLayout mSwipeRefreshLayout;
    ScrollableLayout mScrollableLayout;
    TabLayout mTabLayout;
    ViewPager mViewPager;

    private ScrollableAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollable);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mScrollableLayout = (ScrollableLayout) findViewById(R.id.scrollable_layout);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

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
        mAdapter = new ScrollableAdapter(getSupportFragmentManager(), TITLES);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(TITLES.length - 1);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    private void loadData() {
        String[] newTitles = {"语文2", "数学2", "英语2", "化学2", "物理2", "生物2"};
        mAdapter.setTitles(newTitles);
    }
}
