package com.hp.viewpagerdemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;

import com.hp.viewpagerdemo.ListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zrg on 2018/7/9.
 */

public class ScrollableAdapter extends FragmentStatePagerAdapter {
    private String[] mTitles;
    private boolean mIsSorted;//是否排序完成
    private List<ListFragment> mFragments = new ArrayList<>();

    public ScrollableAdapter(FragmentManager fm) {
        super(fm);
    }

    public ScrollableAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        ListFragment fragment = ListFragment.newInstance(mTitles[position]);
        mFragments.add(fragment);
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
        mIsSorted = false;
        mFragments.clear();
        notifyDataSetChanged();
    }

    public List<ListFragment> getFragments() {
        if (!mIsSorted) {
            //需要排序
            List<ListFragment> fragments = new ArrayList<>();
            for (String title : mTitles) {
                for (ListFragment fragment : mFragments) {
                    if (TextUtils.equals(title, fragment.getTitle())) {
                        fragments.add(fragment);
                        break;
                    }
                }
            }
            //重新为mFragments赋值
            mFragments.clear();
            mFragments.addAll(fragments);
            mIsSorted = true;
        }

        return mFragments;
    }
}
