package com.example.dell.raisingpets.View;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.example.dell.raisingpets.Module.ModuleBackgroud.UI.BackgroudFragment;
import com.example.dell.raisingpets.Module.ModuleCharacters.ui.CharacterFragment;
import com.example.dell.raisingpets.Module.ModuleMine.UI.MineFragment;
import com.example.dell.raisingpets.Module.ModuleProps.UI.PropsFragment;
import com.example.dell.raisingpets.R;
import com.example.dell.raisingpets.Whole.TitleFragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by root on 16-12-21.
 */

public class FragmentLayout {


    public FragmentLayout(Context context, ViewPager viewPager, TabLayout tabLayout, FragmentManager fragmentManager){

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new CharacterFragment());
        fragments.add(new BackgroudFragment());
        fragments.add(new PropsFragment());
        fragments.add(new MineFragment());

        TitleFragmentPagerAdapter adapter =
                new TitleFragmentPagerAdapter(fragmentManager, fragments, new String[]{"角色", "背景", "道具", "个人中心"});
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            Drawable d = null;
            switch (i) {
                case 0:
                    d = context.getResources().getDrawable(R.drawable.selector_characters);
                    break;
                case 1:
                    d = context.getResources().getDrawable(R.drawable.selector_backgroud);
                    break;
                case 2:
                    d = context.getResources().getDrawable(R.drawable.selector_props);
                    break;
                case 3:
                    d = context.getResources().getDrawable(R.drawable.selector_mine);
                    break;
            }
            tab.setIcon(d);
        }
    }
}
