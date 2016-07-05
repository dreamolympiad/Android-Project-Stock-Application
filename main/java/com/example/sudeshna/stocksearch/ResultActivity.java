package com.example.sudeshna.stocksearch;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {
    public static final String LOG_TAG = "ResultActivity";
    public static String query_symbol;

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(ResultActivity.this,
                    "Your orientation is portrait", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ResultActivity.this,
                    "Your orientation is landscape", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //final List<Fragment> fragmentList = new ArrayList<>();
        //fragmentList.add(new CurrentFragment());
        //fragmentList.add(new HistoricalFragment());
        //fragmentList.add(new NewsFragment());

        setContentView(R.layout.result_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.d(LOG_TAG, "onCreate");

        try {
            query_symbol = getIntent().getStringExtra(SMainActivity.json.getString("Symbol"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(LOG_TAG, SMainActivity.SYMBOL + " : " + query_symbol);

        class MyCustomAdapter extends FragmentStatePagerAdapter {
            private String fragments [] = {"Current","Historical","News"};
            public MyCustomAdapter(FragmentManager supportFragmentManager, Context applicationContext){
                super(supportFragmentManager);
            }
            @Override
            public Fragment getItem(int position){
                Log.d(LOG_TAG, "Fragment getItem position" + " : " + position);
                //return fragmentList.get(position);
                switch(position){
                    case 0:
                        return new CurrentFragment();
                    case 1:
                        return new HistoricalFragment();
                    case 2:
                        return new NewsFragment();
                    default:
                        return null;
                }
            }
            @Override
            public int getCount(){
                //Log.d(LOG_TAG, "Fragment getCount fragments.length"+ " : " + fragments.length);
                return fragments.length;
            }
            @Override
            public CharSequence getPageTitle(int position){
                return fragments[position];
            }
        }

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter( new MyCustomAdapter(getSupportFragmentManager(),
                getApplicationContext()));

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setOnTabSelectedListener( new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d(LOG_TAG, "onTabSelected position" + " : " + tab.getPosition());
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.d(LOG_TAG, "onTabUnselected position" + " : " + tab.getPosition());
                                                            //viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.d(LOG_TAG, "onTabReselected position" + " : " + tab.getPosition());
                //viewPager.setCurrentItem(tab.getPosition());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop");
    }
}
