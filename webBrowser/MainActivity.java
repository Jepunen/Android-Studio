package com.example.webbrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    WebView web;
    EditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchBar = (EditText) findViewById(R.id.searchBar);

        web = (WebView) findViewById(R.id.browser);
        web.setWebViewClient(new WebViewClient());
        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl("https://www.google.fi/");
        lastWebPage = web.getUrl();
    }

    String lastWebPage;
    String previousWebPage;

    public void seacrh(View v) {
        lastWebPage = web.getUrl();
        goToURL(searchBar.getText().toString());
    }

    private void goToURL(String s) {
        if ( s.equals("index.html") ) {
            web.loadUrl("file:///android_asset/index.html");
        } else {
            web.loadUrl("https://www." + s);
        }
    }

    public void refresh ( View v ) {
        String URL = web.getUrl();
        web.loadUrl(URL);
    }

    public void executeJavaScript(View v) {
        web.evaluateJavascript("javascript:shoutOut()", null);
    }

    public void initializeJavaScript(View v) {
        web.evaluateJavascript("javascript:initialize()", null);
    }

    public void goBack ( View v ) {
        previousWebPage = web.getUrl();
        web.loadUrl(lastWebPage);
    }

    public void goForward ( View v ) {
        lastWebPage = web.getUrl();
        web.loadUrl(previousWebPage);
    }

}
