package org.techtown.capture.myipapplication0503_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;


import java.util.function.IntUnaryOperator;

public class Web_Activity extends AppCompatActivity {

    WebView web;
    Button back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_);

        web = (WebView) findViewById(R.id.web);
        back_button = (Button) findViewById(R.id.back_button);

        web.setWebViewClient(new MyWebviewClient());
        WebSettings webSet = web.getSettings();
        webSet.setBuiltInZoomControls(true);

        Intent web_intent = getIntent();
        String url=web_intent.getExtras().getString("url");

        web.loadUrl("http://"+url);

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