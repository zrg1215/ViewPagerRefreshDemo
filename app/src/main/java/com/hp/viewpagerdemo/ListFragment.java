package com.hp.viewpagerdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hp.viewpagerdemo.base.BaseLazyFragment;
import com.hp.viewpagerdemo.scrollablelayout.ScrollableHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zrg on 2018/7/9.
 */

public class ListFragment extends BaseLazyFragment implements ScrollableHelper.ScrollableContainer {
    private static final String ARG_TITLE = "arg_title";

    RecyclerView mRecyclerView;

    private String mTitle;

    public ListFragment() {
    }

    public static ListFragment newInstance(String title) {
        ListFragment fragment = new ListFragment();
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
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
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

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add("这是测试数据_" + i);
        }
        MyAdapter adapter = new MyAdapter(getContext(), list);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public View getScrollableView() {
        return mRecyclerView;
    }

    public String getTitle() {
        return mTitle;
    }

    //region Aadpter
    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private Context mContext;
        private List<String> mList;

        public MyAdapter(Context context, List<String> list) {
            mContext = context;
            mList = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.mTvTest.setText(mList.get(position));
        }

        @Override
        public int getItemCount() {
            return mList == null ? 0 : mList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView mTvTest;

            public MyViewHolder(View itemView) {
                super(itemView);
                mTvTest = (TextView) itemView.findViewById(android.R.id.text1);
            }
        }

    }
    //endregion
}
