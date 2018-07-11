package com.hp.viewpagerdemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.hp.viewpagerdemo.EmptyFragment;

/**
 * Created by zrg on 2018/7/9.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private String[] mTitles;

    public ViewPagerAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        EmptyFragment fragment = EmptyFragment.newInstance(mTitles[position]);
        Log.e("zrg", "getItem: 当前位置position=" + position);
        return fragment;
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

    public void setTitles(String[] titles) {
        mTitles = titles;
        notifyDataSetChanged();
    }

}
