package com.example.aburom.newsapp.adapters;

import com.example.aburom.newsapp.TabbedActivity;
import com.example.aburom.newsapp.fragments.FavoritesFragment;
import com.example.aburom.newsapp.fragments.HomeFragment;
import com.example.aburom.newsapp.fragments.NewsFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new NewsFragment();
                break;
            case 2:
                fragment = new FavoritesFragment();
                break;
            default:
                new HomeFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}