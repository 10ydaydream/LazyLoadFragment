package com.daydream.lazyloadfragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.daydream.lazyloadfragment.fragment.Fragment1;
import com.daydream.lazyloadfragment.fragment.Fragment2;
import com.daydream.lazyloadfragment.fragment.Fragment3;
import com.daydream.lazyloadfragment.fragment.LazyLoadFragment;
import com.daydream.lazyloadfragment.fragment.LazyLoadPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ViewPager mViewPager;
    LazyLoadPagerAdapter mPagerAdapter;
    List<LazyLoadFragment> mPagerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initPager();
    }

    private void initView(){
        mViewPager = findViewById(R.id.viewpager);
    }

    private void initPager(){
        mPagerList = new ArrayList<>();
        mPagerList.add(new Fragment1());
        mPagerList.add(new Fragment2());
        mPagerList.add(new Fragment3());

        mPagerAdapter = new LazyLoadPagerAdapter(getSupportFragmentManager(), mPagerList);

        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(0);
    }
}
