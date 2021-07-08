// url연결하는 방법
// 1. Intent
// 2. Web View
// Web View는 오류가 나도 무시하기에 Intent를 쓰는게 좋다.
// ip주소 >> jsoup을 이용한 웹 파싱 , https://m.blog.naver.com/occidere/220851125347 참고

package org.techtown.capture.myipapplication0503_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;     // 파싱 할 때 필수
import org.jsoup.select.Elements;   // 파싱 할 때 필수
import org.jsoup.nodes.Document;    // 파싱 할 때 필수
import org.w3c.dom.Text;
import org.xwalk.core.XWalkView;

public class MainActivity extends AppCompatActivity {

    EditText url_edit;
    //Button button;
    String url="null";
    Button view_button,ip_button;
    ListView ip_list;
    //TextView textView;
    TextView hotspot_txt;

    ArrayList<String> ip_array = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    private String html_url="https://www.findip.kr/";  // 파싱 할 웹페이지
    private String html_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        url_edit = (EditText) findViewById(R.id.url_edit);
        //button = (Button) findViewById(R.id.button);
        ip_button = (Button) findViewById(R.id.ip_button);
        view_button = (Button) findViewById(R.id.view_button);
        ip_list = (ListView) findViewById(R.id.ip_list);
        //textView = (TextView) findViewById(R.id.textView);
        hotspot_txt = (TextView) findViewById(R.id.hotspot_txt);


//        int id = radioGroup.getCheckedRadioButtonId(); // 리턴값은 선택된 라디오버튼의 id값
//        RadioButton radioButton =(RadioButton)findViewById(id);
//
//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                if(radioButton.getText()== "Snapshot") {
//                    snapshot=true;
//                    stream = false;
//                }
//                else if(radioButton.getText()=="Streaming") {
//                    stream=true;
//                    snapshot=false;
//                }
//            }
//        });
    
        // 디버깅용
       ip_array.add("192.168.156.137"); 
        ip_array.add("192.168.134.137");
        ip_array.add("192.168.161.137");
        ip_array.add("192.168.0.1");
        ip_array.add("naver.com");


        // 어댑터 객체 생성
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,ip_array);

        ip_list.setChoiceMode(ListView.CHOICE_MODE_SINGLE); // 리스트뷰 중 하나의 항목을 선택

        ip_list.setAdapter(adapter); // 리스트뷰와 어댑터 연결
        ip_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {  // 항목 선택했을 때 수행할 동작
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String ip=ip_array.get(position); // Arraylist에서 position에 해당하는 데이터 얻어오기
                url_edit.setText(ip);

            }
        });


        view_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                url = url_edit.getText().toString();

                if(url == "null")
                    Toast.makeText(getApplicationContext(),"ip를 입력하세요",Toast.LENGTH_SHORT).show();
                else {

                    String edit_text = url_edit.getText().toString(); // editText창에 입력된 값 얻기
                    ip_array.add(edit_text);  // 입력된 값 ArrayList에 추가
                    adapter.notifyDataSetChanged(); // 모델 객체가 변경되었음을 어댑터뷰에 알리기

//                    url_edit.setText(""); // 입력된 값 지우기

                    Intent web_intent = new Intent(getApplicationContext(),Web_Activity.class);
                    web_intent.putExtra("url",url);
                    startActivity(web_intent);
                }
            }
        });



        hotspot_txt.setMovementMethod(new ScrollingMovementMethod());

        ip_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
                jsoupAsyncTask.execute();
            }
        });


    }
    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void>{ // 웹 파싱 클래스 생성

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try{
                Document doc = (Document) Jsoup.connect(html_url).header("User-Agent","Mozilla/5.0").get(); // 파싱하고 싶은 페이지 링크정보 get

//                String h1 = doc.select("h1").text(); // h1태그의 내용만 추출
                String h2 = doc.select("h2").text(); // 1번째 인덱스에 위치한 h2태그의 내용만 추출

//                Elements links = doc.select("p"); // select: 원하는 부분 파싱

                html_content = h2;

            } catch(IOException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected  void onPostExecute(Void result){
            hotspot_txt.setText(html_content);
        }
    }

}
