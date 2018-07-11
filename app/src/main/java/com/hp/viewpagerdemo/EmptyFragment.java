package com.hp.viewpagerdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hp.viewpagerdemo.base.BaseLazyFragment;

/**
 * Created by zrg on 2018/7/9.
 */

public class EmptyFragment extends BaseLazyFragment {
    private static final String ARG_TITLE = "arg_title";

    TextView mTvClick;

    private String mTitle;

    public EmptyFragment() {
    }

    public static EmptyFragment newInstance(String title) {
        EmptyFragment fragment = new EmptyFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TITLE, title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle = getArguments().getString(ARG_TITLE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_empty, container, false);
        mTvClick = (TextView) view.findViewById(R.id.tv_click);
        mIsprepared = true;
        lazyLoad();
        return view;
    }

    @Override
    protected void lazyLoad() {
        if (!mIsprepared || !mIsVisible || mHasLoadedOnce) {
            return;
        }
        mHasLoadedOnce = true;
        //UI和业务逻辑
        Log.e("zrg", "lazyLoad: 当前的fragment mTitle=:" + mTitle);
        mTvClick.setText(mTitle);
    }

    public String getTitle() {
        return mTitle;
    }
}
