package com.daydream.lazyloadfragment.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class LazyLoadPagerAdapter extends FragmentPagerAdapter {

    private List<LazyLoadFragment> mList;
    private FragmentManager mFragmentManager;

    public LazyLoadPagerAdapter(FragmentManager fm, List<LazyLoadFragment> list) {
        super(fm);
        this.mFragmentManager= fm;
        this.mList= list;
    }

    @Override
    public Fragment getItem(int position) {
        if(mList.size()> position){
            return mList.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        if(mList== null){
            return 0;
        }
        return mList.size();
    }
}
