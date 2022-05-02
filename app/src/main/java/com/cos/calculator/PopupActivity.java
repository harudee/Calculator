package com.cos.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class PopupActivity extends AppCompatActivity {

    private static final String TAG = "PopupActivity";
    private PopupActivity mContext;

    private TextView tvRecode, tvExpression, tvAnswer;
    private LinearLayout llHistory;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_popup);

        init();
        initLr();
        initData();


    }

    private void init(){

        btnBack = findViewById(R.id.btn_back);
        tvRecode = findViewById(R.id.tv_recode);

        tvExpression = findViewById(R.id.tv_expression);//계산식
        tvAnswer = findViewById(R.id.tv_answer);//결과
        llHistory = findViewById(R.id.ll_history);//뿌릴 곳

    }

    private void initLr(){
        btnBack.setOnClickListener(view -> {
            Log.d(TAG, "initLr: 실행됨");
            finish(); //화면 날리기
        });

    }

    private void initData(){
        //파일 읽기
        String str = null;
        try {
            BufferedInputStream bis =
                    new BufferedInputStream(new FileInputStream(new File(getFilesDir() + "/recode.txt")));

            byte[] buff = new byte[9999]; //버퍼 배열

            int nRLen = bis.read(buff); //파일 크기

            str = new String(buff, 0, nRLen); //byte -> string

            bis.close();

        } catch (IOException e) {
            Log.d(TAG, "readRecode: 오류 발생 "+e);
            e.printStackTrace();
        }

        tvRecode.setText(str);


    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        //외부 클릭시 팝업창이 안 닫히게 설정
//        if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
//            return false;
//        }
//
//        return false;
//    }


}