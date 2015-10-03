package com.ducnd.demoslidingtab;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.TabLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.ducnd.myappcommon.R;

/**
 * Created by ducnd on 10/08/2015.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private Toolbar toolbar;
    private TabLayout slidingTab;
    private VIewPageAdapter adapter;
    private ViewPager viewpager;
    private NavigationView navigation;
    private InputMethodManager inputMethodManager;
    private DrawerLayout drawerLayout;
    private int Numboftabs =2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_slidingtab);
        initTotal();

    }

    private void initTotal() {
        initToolBar();
        initSlidingTab();
        initSlidingTab();

        initializeComponent();
        initViewpager();
    }

    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }
    private void initSlidingTab() {
        inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerlayout);
        slidingTab = (TabLayout)findViewById(R.id.slidingtab);
    }
    private void initializeComponent() {
        navigation = (NavigationView)findViewById(R.id.navigation);
        navigation.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open, R.string.close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }
    private void initViewpager() {
        viewpager = (ViewPager)findViewById(R.id.viewpager);
        adapter = new VIewPageAdapter(getSupportFragmentManager());
        viewpager.setAdapter(adapter);
//        viewpager.setOffscreenPageLimit(1);
        slidingTab.setupWithViewPager(viewpager);
        slidingTab.getTabAt(0).setIcon(R.drawable.ic_more_horiz_black_24dp);
        slidingTab.getTabAt(1).setIcon(R.drawable.ic_menu_black_24dp);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        drawerLayout.closeDrawers();
        return true;
    }
}
