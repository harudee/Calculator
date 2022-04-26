package com.cos.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class PopupActivity extends AppCompatActivity {

    private static final String TAG = "PopupActivity";
    private PopupActivity mContext;

    private TextView tvRecode;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);//popup의 title 제거
        setContentView(R.layout.activity_popup);

        init();
        initLr();
        initData();

    }

    private void init(){
        tvRecode = findViewById(R.id.tv_recode);
        btnBack = findViewById(R.id.btn_back);

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
//        //외부 클릭시 안 닫히게 설정
//        if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
//            return false;
//        }
//
//        return false;
//    }

}