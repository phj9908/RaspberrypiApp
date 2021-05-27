package org.techtown.capture.myipapplication0503_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;


import org.xwalk.core.XWalkPreferences;
import org.xwalk.core.XWalkView;

import java.util.function.IntUnaryOperator;

public class Web_Activity extends AppCompatActivity {

    WebView web;
    Button back_button;
    String url="";
    String html="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_);

        web = (WebView) findViewById(R.id.web);
        back_button = (Button) findViewById(R.id.back_button);

        web.setWebViewClient(new MyWebviewClient());
        WebSettings webSet = web.getSettings();
        webSet.setBuiltInZoomControls(true);
        webSet.setLoadWithOverviewMode(true);
        webSet.setUseWideViewPort(true);
        webSet.setJavaScriptEnabled(true);

        Intent web_intent = getIntent();
        String ip=web_intent.getExtras().getString("url");


        url = "<img src="+"'http://"+ip+"/stream/video.mjpeg'/></div></body></html>"; // or port number : 8080
        html ="<html><head><style type='text/css'>body{margin:auto auto;text-align:center;} img{width:100%25;} div{overflow: hidden;} </style></head><body><div>";
        web.loadData(html+url,"text/html",  "UTF-8");

//        web.loadUrl("http://"+ip+":8091/?action=stream");
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 전 화면으로 돌아감
            }
        });
    }



    class MyWebviewClient extends WebViewClient {  // 웹 뷰를 쓰기 위한 클래스 생성
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            return super.shouldOverrideUrlLoading(view, url);
        }
    }

}