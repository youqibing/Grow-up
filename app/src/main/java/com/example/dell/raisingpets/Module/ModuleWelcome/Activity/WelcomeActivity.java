package com.example.dell.raisingpets.Module.ModuleWelcome.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.dell.raisingpets.Module.ModuleWelcome.Fragment.WelcomeFragment_Four;
import com.example.dell.raisingpets.Module.ModuleWelcome.Fragment.WelcomeFragment_One;
import com.example.dell.raisingpets.Module.ModuleWelcome.Fragment.WelcomeFragment_Three;
import com.example.dell.raisingpets.Module.ModuleWelcome.Fragment.WelcomeFragment_Two;
import com.example.dell.raisingpets.R;

import com.example.dell.raisingpets.Util.ViewUtil;
import com.example.dell.raisingpets.Whole.UI.BaseActivity;
import com.example.floatingaction.CircularImageView.CircularImage;

import java.util.ArrayList;

/**
 * Created by dell on 2016/8/11.
 */

public class WelcomeActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    private ImageView[] indicatorViews;

    private static final int PAGE_NUM = 4;

    private WelcomeFragment_One welcomeFragment_one;
    private WelcomeFragment_Two welcomeFragment_two;
    private WelcomeFragment_Three welcomeFragment_three;
    private WelcomeFragment_Four welcomeFragment_four;

    private LinearLayout indicatorGroup;

    private boolean indicatorIsInited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);

        viewPager = (ViewPager)findViewById(R.id.pager);

        welcomeFragment_one = new WelcomeFragment_One();
        welcomeFragment_two = new WelcomeFragment_Two();
        welcomeFragment_three = new WelcomeFragment_Three();
        welcomeFragment_four = new WelcomeFragment_Four();

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(welcomeFragment_one);
        fragments.add(welcomeFragment_two);
        fragments.add(welcomeFragment_three);
        fragments.add(welcomeFragment_four);

        pagerAdapter = new WelcomePagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(this);

        indicatorGroup = (LinearLayout)findViewById(R.id.indicator_group);

        initIndicators(PAGE_NUM);
    }

    public void initIndicators(int size){
        if(indicatorIsInited){
            return;
        }
        if(size == 1){
            return;
        }
        indicatorViews = new ImageView[size];
        for(int i = 0; i < size; i++){
            ImageView imageView = new CircularImage(this);//CircularImage这个类就是绘制一个圆形
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewUtil.getDp2Px(R.dimen.indicator_size),  //layout_width
                    ViewUtil.getDp2Px(R.dimen.indicator_size)   //layout_height
            );
            params.setMargins(                  //四个参数左上右下
                    ViewUtil.getDp2Px(R.dimen.indicator_margin_left),
                    0, 0,
                    ViewUtil.getDp2Px(R.dimen.indicator_margin_bottom)
            );
            imageView.setLayoutParams(params);
            indicatorViews[i]= imageView;

            if(i == 0){     //这个if是保证开始时第一个指示器被选中
                indicatorViews[i].setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_checked));
            }else{
                indicatorViews[i].setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_unchecked));
            }

            indicatorGroup.addView(indicatorViews[i]);
        }
        indicatorIsInited = true;

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for(int i = 0; i<indicatorViews.length; i++){
            indicatorViews[position].setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_checked));

            if(position != i){
                indicatorViews[i].setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_unchecked));
            }

            /*
            if(position == (indicatorViews.length - 1)){//滑到第四张图片就移除所有的指示器
                indicatorGroup.removeAllViews();
            }
            */

        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class WelcomePagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragments;

        WelcomePagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
            super(fm);

            this.fragments = fragments;
        }


        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
