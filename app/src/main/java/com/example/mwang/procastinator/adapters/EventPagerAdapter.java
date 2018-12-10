package com.example.mwang.procastinator.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class EventPagerAdapter extends FragmentStatePagerAdapter {
    List<Fragment> fragments=new ArrayList<>();
    ArrayList<String> titles=new ArrayList<>();

    public EventPagerAdapter(FragmentManager fm) {
        super(fm);
    }



    public void addFragment(Fragment f,String s){

        fragments.add(f);
        titles.add(s);
    }
    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
