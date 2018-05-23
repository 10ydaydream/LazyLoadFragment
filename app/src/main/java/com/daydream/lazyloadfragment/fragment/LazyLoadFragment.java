package com.daydream.lazyloadfragment.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 * ViewPager + 多Fragment 会造成Fragment的生命周期回调与常规单独显示Fragment不同
 * 所以在ViewPager + 多Fragment的情况下， 需要重新定义生命周期过程内的操作
 * 将一些特定的操作（耗时的）放到在Fragment显示的时候才进行
 *
 * Fragment显示、隐藏的api有两种：setUserVisibleHint和通过FragmentTransaction的show和hide的方法
 * 后者实际显示和生命周期基本是相对应的
 * 
 * 注意：
 * 1.有可能setUserVisibleHint方法执行时，onCreateView、onViewCreated并未执行完（也就是UI没有创建好）
 * 2.当ViewPager中Fragment列表有3个及以上的实例时， 默认情况下系统只会保存当前显示Fragment实例和左右相邻的Fragment实例的Layout
 *  其他的Fragment会执行onPause->onStop->onDestroyView对layout进行回收来节省内存。
 *
 * */
public class LazyLoadFragment extends Fragment {

    public String TAG = "LazyLoadingFragment";

    /**
     * 上一次可见状态
     */
    protected boolean mLastVisible;

    /**
     * 标志是否第一次调用setUserVisibleHint方法
     */
    private boolean isFirstSetVisible = true;

    /**
     * 是否第一次可见
     * */
    private boolean isFirstVisible = false;

    /**
     * Fragment实例载入的Layout
     * */
    private View mRootView = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");
        return super.onCreateView(inflater, container, savedInstanceState);
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        /*在onCreateView之后调用*/
        super.onViewCreated(view, savedInstanceState);

        mRootView = view;

        /**
         * 第一次加载的时候， 可能会先调用了setUserVisibleHint方法， 再创建布局UI
         * */
        if (getUserVisibleHint() && isFirstVisible) {
            isFirstVisible = false;
            onVisible();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        /*
         * 调用setUserVisibleHint方法的情况：
         * 1.创建Fragment实例时，会调用一次，传入false
         * 2.当前Fragment可见，会调用一次，传入true
         * 3.当前Fragment从可见->不可见， 会调用一次，传入false
         * 4.当前Fragment不可见时，其他Fragment切换可见状态时也有可能导致当前Fragment会调用一次setUserVisibleHint方法，传入false；一般是相邻的Fragment实例可见状态变化的情况下发生
         * (也就是说受影响的是当前正在显示的Fragment以及左右相邻两个的Fragment)
         * */

        mLastVisible = getUserVisibleHint();
        super.setUserVisibleHint(isVisibleToUser);
        if (isFirstSetVisible) {
            mLastVisible = getUserVisibleHint();
            isFirstSetVisible = false;
            return;
        }
        if (mLastVisible == getUserVisibleHint()) {
            return;
        }
        /*
         * 1.第一次显示时， 由于setUserVisibleHint和onCreateView方法的调用顺序问题（先setUserVisibleHint， 后onCreateView）
         * 导致可能在这个判断就返回
         * 2.当Fragment切换到隐藏的状态时， 长时间没有切换回显示状态，系统可能会回收Fragment的Layout， 回收后再切换回显示状态的话需要重新加载Layout
         *
         * mRootView不为null时， 表示Fragment已经加载过layout
         * */
        Log.i(TAG, "setUserVisibleHint: mRootView:"+ mRootView);
        if (mRootView == null) {
            isFirstVisible = true;
            return;
        }else{
            isFirstVisible = false;
        }

        /*
        *
        * */
        if (getUserVisibleHint()) {
            onVisible();
        } else {
            onInvisible();
        }
    }

    /**
     * 不可见->可见
     */
    public void onVisible() {

    }

    /**
     * 可见->不可见
     */
    public void onInvisible() {

    }

    /*
    * 在多个Fragment的情况下， Android系统为了减少内存占用，会调用onDestroyView方法来销毁UI显示
    * 这也是为什么有时候从一个FragmentA切换到其他Fragment一段时间后，再切换回FragmentA时FragmentA的界面是空白
    * */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRootView = null;
    }
}
