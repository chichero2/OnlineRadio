package com.romariomkk.gl_proj2.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by romariomkk on 14.11.2016.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter{

    private List<Fragment> fragList = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragList.get(position);
    }

    @Override
    public int getCount() {
        return fragList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    void addFragment(Fragment frag){
        fragList.add(frag);
        titles.add(frag.getArguments().getString("title"));
    }
}