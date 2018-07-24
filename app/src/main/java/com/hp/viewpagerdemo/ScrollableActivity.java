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

import java.util.ArrayList;
import java.util.List;

public class ScrollableActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private String[] TITLES = {"语文", "数学", "英语", "化学", "物理", "生物"};

    SwipeRefreshLayout mSwipeRefreshLayout;
    ScrollableLayout mScrollableLayout;
    TabLayout mTabLayout;
    ViewPager mViewPager;

    private ScrollableAdapter mAdapter;
    private int mCurrentIndex;
    Handler mHandler;
    private List<ListFragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollable);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mScrollableLayout = (ScrollableLayout) findViewById(R.id.scrollable_layout);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        mHandler = new Handler(Looper.getMainLooper());

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshDate();

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });

        mScrollableLayout.setOnScrollListener(new ScrollableLayout.OnScrollListener() {
            @Override
            public void onScroll(int currentY, int maxY) {
                if (currentY <= 0) {
                    mSwipeRefreshLayout.setEnabled(true);
                } else {
                    mSwipeRefreshLayout.setEnabled(false);
                }
            }
        });

        mAdapter = new ScrollableAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(this);
        loadData();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mCurrentIndex = position;
        mScrollableLayout.getHelper().setCurrentScrollableContainer(mFragments.get(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void loadData() {
        mFragments.clear();
        for (String title : TITLES) {
            mFragments.add(ListFragment.newInstance(title));
        }

        mAdapter.setTitles(TITLES, mFragments);
        mViewPager.setOffscreenPageLimit(TITLES.length - 1);
        mScrollableLayout.getHelper().setCurrentScrollableContainer(mFragments.get(mCurrentIndex));
    }

    private void refreshDate() {
        String[] newTitles = {"语文2", "数学2", "英语2"};
        final int currentIndex = mCurrentIndex > (newTitles.length - 1) ? (newTitles.length - 1) : mCurrentIndex;

        mFragments.clear();
        for (String title : newTitles) {
            mFragments.add(ListFragment.newInstance(title));
        }

        mAdapter.setTitles(newTitles, mFragments);
        mViewPager.setOffscreenPageLimit(newTitles.length - 1);
        mViewPager.setCurrentItem(currentIndex);
        mScrollableLayout.getHelper().setCurrentScrollableContainer(mFragments.get(currentIndex));
    }

}
