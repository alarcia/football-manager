package com.damcotech.football_manager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class Fichas extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View fichas = inflater.inflate(R.layout.fichas, container, false);
        WebView webView = (WebView) fichas.findViewById(R.id.webView);
        webView.loadUrl("file:///android_asset/www/html/fichas.html");


        return fichas;
    }
}


