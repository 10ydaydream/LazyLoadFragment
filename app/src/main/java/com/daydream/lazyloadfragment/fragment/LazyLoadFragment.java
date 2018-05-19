package com.daydream.lazyloadfragment.fragment;

import android.support.v4.app.Fragment;

public class LazyLoadFragment extends Fragment {

    /*
    * 实现延迟加载的关键在于setUserVisibleHint方法
    * 在Fragment创建和显示时会调用setUserVisibleHint方法， 并且传入参数为true
    *
    * 还需要了解什么场景下setUserVisibleHint的调用和传入参数值
    * */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public boolean getUserVisibleHint() {
        return super.getUserVisibleHint();
    }
}
