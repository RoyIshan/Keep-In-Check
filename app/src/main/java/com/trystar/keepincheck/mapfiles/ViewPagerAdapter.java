package com.trystar.keepincheck.mapfiles;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    List<Location> list ;
    List<LocationFragment> fragmentList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm, List<Location> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int i) {
        LocationFragment fragment = LocationFragment.newInstance(list.get(i));
        fragmentList.add(fragment);
        return  fragment;
    }

    @Override
    public int getCount() {
        return list.size();
    }

}
