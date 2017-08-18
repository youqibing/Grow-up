package com.example.dell.raisingpets.Whole;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2016/10/12.
 */

public class TitleFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments = null;

    private String[] titles;

    public TitleFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    public TitleFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments,String[] titles){
        super(fm);

        this.fragments = fragments;
        this.titles = titles;
    }



    /**
     * 获取索引位置的fragment
     * @param position 索引位置
     * @return fragment
     */
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        if(position < fragments.size()){
            fragment = fragments.get(position);
        }else {
            fragment = fragments.get(0);
        }

        Log.d("tag", "getItem is called");

        return fragment;
    }


    /**
     * 获取Fragment的数量
     * @return counts
     */
    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titles != null && titles.length > 0)
            return titles[position];
        return null;
    }
}
