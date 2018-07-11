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

public class ScrollableActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private String[] TITLES = {"语文", "数学", "英语", "化学", "物理", "生物"};

    SwipeRefreshLayout mSwipeRefreshLayout;
    ScrollableLayout mScrollableLayout;
    TabLayout mTabLayout;
    ViewPager mViewPager;

    private ScrollableAdapter mAdapter;
    private int mCurrentIndex;
    Handler mHandler;

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
        mScrollableLayout.getHelper().setCurrentScrollableContainer(mAdapter.getFragments().get(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void loadData() {
        mAdapter.setTitles(TITLES);
        mViewPager.setOffscreenPageLimit(TITLES.length - 1);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //adapter没有刷新完成，故需要延迟，
                mScrollableLayout.getHelper().setCurrentScrollableContainer(mAdapter.getFragments().get(mCurrentIndex));
            }
        }, 500);

    }

    private void refreshDate() {
        String[] newTitles = {"语文2", "数学2", "英语2"};
        final int currentIndex = mCurrentIndex > newTitles.length ? 0 : mCurrentIndex;
        mAdapter.setTitles(newTitles);
        mViewPager.setOffscreenPageLimit(newTitles.length - 1);
        mViewPager.setCurrentItem(currentIndex);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //adapter没有刷新完成，故需要延迟，
                mScrollableLayout.getHelper().setCurrentScrollableContainer(mAdapter.getFragments().get(currentIndex));
            }
        }, 500);
    }


}
