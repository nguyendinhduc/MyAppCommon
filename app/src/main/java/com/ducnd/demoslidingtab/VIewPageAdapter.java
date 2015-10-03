package com.ducnd.demoslidingtab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by ducnd on 10/08/2015.
 */
public class VIewPageAdapter extends FragmentPagerAdapter {
    private int numberOfTab;
    private ArrayList<Fragment> fragments;

    public VIewPageAdapter(FragmentManager fm) {
        super(fm);
        this.fragments = new ArrayList<Fragment>();
        this.fragments.add(new FragmentTa1());
        this.fragments.add(new FragmentTab2());
    }

    @Override
    public Fragment getItem(int position) {
       return fragments.get(position);

    }


    @Override
    public int getCount() {
        return fragments.size();
    }
}
