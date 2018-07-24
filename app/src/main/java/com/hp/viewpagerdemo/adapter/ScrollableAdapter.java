package com.hp.viewpagerdemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.hp.viewpagerdemo.ListFragment;

import java.util.List;

/**
 * Created by zrg on 2018/7/9.
 */

public class ScrollableAdapter extends FragmentStatePagerAdapter {
    private String[] mTitles;
    private List<ListFragment> mFragments;

    public ScrollableAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Log.e("zrg", "getItem: 当前位置position=" + position);
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mTitles == null ? 0 : mTitles.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.e("zrg", "instantiateItem: 当前位置position=" + position);
        return super.instantiateItem(container, position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles == null ? "" : mTitles[position];
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void setTitles(String[] titles, List<ListFragment> fragments) {
        mTitles = titles;
        mFragments = fragments;
        notifyDataSetChanged();
    }
}
