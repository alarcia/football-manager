package com.damcotech.football_manager;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class MainActivity extends FragmentActivity {
    //tabbed variables
    private NonSwipeableViewPager Tab;
    private ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TabPagerAdapter tabAdapter = new TabPagerAdapter(getSupportFragmentManager());

        Tab = (NonSwipeableViewPager) findViewById(R.id.pager);

        Tab.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {

                        actionBar = getActionBar();
                        assert actionBar != null;
                        actionBar.setSelectedNavigationItem(position);
                    }
                });

        Tab.setAdapter(tabAdapter);

        //este metodo hace que se queden en memoria cargadas las 5 paginas
        Tab.setOffscreenPageLimit(4);

        actionBar = getActionBar();
        //Enable Tabs on Action Bar
        assert actionBar != null;
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {

            @Override
            public void onTabReselected(android.app.ActionBar.Tab tab,
                                        FragmentTransaction ft) {

            }

            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

                Tab.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(android.app.ActionBar.Tab tab,
                                        FragmentTransaction ft) {


            }
        };
        //Add New Tab
        actionBar.addTab(actionBar.newTab().setText("Estadisticas").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("Tacticas").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("Grabadora").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("Fichas").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("Reglamento").setTabListener(tabListener));

    }


}