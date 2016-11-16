package com.romariomkk.gl_proj2;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.romariomkk.gl_proj2.country.CountriesFragment;
import com.romariomkk.gl_proj2.show.ShowsFragment;
import com.romariomkk.gl_proj2.station.StationsFragment;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        initTabLayout();
    }

    void initToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setLogo(R.drawable.last_fm);
        toolbar.setOnClickListener((view)->{

        });
    }

    void initTabLayout(){
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(StationsFragment.newInstance("STATIONS"));
        adapter.addFragment(CountriesFragment.newInstance("COUNTRIES"));
        adapter.addFragment(ShowsFragment.newInstance("SHOWS"));
        viewPager.setAdapter(adapter);
    }

}