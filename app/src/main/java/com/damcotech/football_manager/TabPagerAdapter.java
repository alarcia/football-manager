package com.damcotech.football_manager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

class TabPagerAdapter extends FragmentStatePagerAdapter {
    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                //Fragmento para el Tab de Estadisticas
                return new Estadisticas();
            case 1:
                //Fragmento para el Tab de Tacticas
                return new Tactica();
            case 2:
                //Fragmento para el Tab de Anotaciones
                return new Anotaciones();
            case 3:
                //Fragmento para el Tab de Reglamento
                return new Fichas();
            case 4:
                //Fragmento para el Tab de Fichas
                return new Reglamento();
        }
        return null;

    }

    @Override
    public int getCount() {
        return 5; //nº de Tabs
    }
}