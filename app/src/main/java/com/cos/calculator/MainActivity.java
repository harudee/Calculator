package com.cos.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity222";
    private MainActivity mContext = this;

    private EditText ptResult;
    private TextView ptCurrResult;
    private Button btn[] = new Button[17];
    private ImageButton btnRecode, btnUndo, btnBackSpace;


    private boolean isEntered = false;
    private int cursorPosition;
    private String s = ""; //현재 입력값
    private int dotCount = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        ptResult.requestFocus();
        initListener();
        initData();
    }

    private void init() {
        btn[0] = findViewById(R.id.num_0);
        btn[1] = findViewById(R.id.num_1);
        btn[2] = findViewById(R.id.num_2);
        btn[3] = findViewById(R.id.num_3);
        btn[4] = findViewById(R.id.num_4);
        btn[5] = findViewById(R.id.num_5);
        btn[6] = findViewById(R.id.num_6);
        btn[7] = findViewById(R.id.num_7);
        btn[8] = findViewById(R.id.num_8);
        btn[9] = findViewById(R.id.num_9);

        btn[10] = findViewById(R.id.btn_multiple);
        btn[11] = findViewById(R.id.btn_division);
        btn[12] = findViewById(R.id.btn_plus);
        btn[13] = findViewById(R.id.btn_minus);
        btn[14] = findViewById(R.id.btn_dot);

        btn[15] = findViewById(R.id.btn_enter);
        btn[16] = findViewById(R.id.btn_clear);

        btnBackSpace = findViewById(R.id.btn_backSpace);
        btnRecode = findViewById(R.id.btn_recode); //계산기록
        btnUndo = findViewById(R.id.btn_undo); //실행취소-> enter 이전 단계로

        ptResult = findViewById(R.id.pt_result);//결과창
        ptCurrResult = findViewById(R.id.pt_currResult);//현재 계산상태

    }

    private void initListener() {

        //숫자입력
        for (int i = 0; i < 10; i++) {
            int I = i;
            btn[i].setOnClickListener(view -> {

                s+="";

                Button btn = (Button) view;
                cursorPosition = ptResult.getSelectionStart();
                String sentence = ptResult.getText().toString();

                char lastChar = 'f';
//                if(s != null) { //입력 됐는지 아닌지 확인
//                    lastChar = sentence.charAt(sentence.length()-1);
//                }

                if (lastChar == 'f') {
                    if (I == 0) { //최초 입력
                        if (s == null || dotCount == 1) {
                            ptResult.append("0");
                            ptResult.append(".");
                            dotCount = 0;

                            s += "0.";

                        } else {

                            StringBuffer sb = new StringBuffer(sentence);
                            sb.insert(cursorPosition, btn.getText().toString());

                            ptResult.setText(sb.toString());
                            ptResult.setSelection(cursorPosition + 1);

                            String result3 = ptResult.getText().toString();


                            double a = Double.valueOf(Eval.cal(result3));
                            int b = (int) a;

                            if (a == b) {
                                ptCurrResult.setText(Integer.toString(b));
                            } else ptCurrResult.setText(Eval.cal(result3));

                            s += btn.getText().toString();

                        }

                    } else {

                        StringBuffer sb = new StringBuffer(sentence);
                        sb.insert(cursorPosition, btn.getText().toString());

                        ptResult.setText(sb.toString());
                        ptResult.setSelection(cursorPosition + 1);

                        String result3 = ptResult.getText().toString();


                        double a = Double.valueOf(Eval.cal(result3));
                        int b = (int) a;

                        if (a == b) {
                            ptCurrResult.setText(Integer.toString(b));
                        } else ptCurrResult.setText(Eval.cal(result3));

                        s += btn.getText().toString();

                    }
                }

            });
        }

        //사칙연산
        for (int i = 10; i < 15; i++) {

            btn[i].setOnClickListener(view -> {

                Button btn = (Button) view;
                String str = btn.getText().toString();//입력
                String sentence = ptResult.getText().toString();
                cursorPosition = ptResult.getSelectionStart();
                ptCurrResult.setText("");

                if (cursorPosition == 0) return;

                char a = sentence.charAt(sentence.length() - 1); //입력된 마지막 글자
                if (a == '0' || a == '1' || a == '2' || a == '3' || a == '4' || a == '5' || a == '6' || a == '7' || a == '8' || a == '9') {

                    StringBuffer sb = new StringBuffer(sentence);
                    sb.insert(cursorPosition, btn.getText().toString());

                    ptResult.setText(sb.toString());
                    ptResult.setSelection(cursorPosition + 1);

                    s+=btn.getText().toString();

                }

                char b = str.charAt(str.length() - 1);
                if (a == b) return;

                if (a == '+' || a == '-' || a == '*' || a == '/') {
                    Log.d(TAG, "initLr: 바꿔줘...");

                    StringBuffer sb = new StringBuffer(sentence);
                    sb.replace(cursorPosition - 1, cursorPosition, str);


                    ptResult.setText(sb.toString());
                    ptResult.setSelection(cursorPosition);

                    s+=btn.getText().toString();

                }

            });

        }

        //clear : 전체 지우기
        btn[16].setOnClickListener(view -> {
            s = "";
            ptResult.setText("");
            ptCurrResult.setText("");

        });


        //한 개 지우기
        btnBackSpace.setOnClickListener(view -> {

            if (ptResult.length() == 0) return;

            isEntered = false;

            String a = ptResult.getText().toString();

            cursorPosition = ptResult.getSelectionStart();
            StringBuffer sb = new StringBuffer(a);
            sb.replace(cursorPosition - 1, cursorPosition, "");

            ptResult.setText(sb.toString());
            ptResult.setSelection(cursorPosition - 1);

            ptCurrResult.setText("");

        });

        //enter
        btn[15].setOnClickListener(view -> {

            s+="";

            if (ptResult.getText().length() == 0) return;

            isEntered = false;

            Button btn = (Button) view;

            ptResult.setSelection(ptResult.length());

            String result = ptResult.getText().toString();
            String result2 = Eval.cal(result);
            String str = btn.getText().toString(); //=

            if (result2 == null) return;

            double a = Double.valueOf(Eval.cal(result2));
            int b = (int) a;


            //계산 기록 저장
            String recode1 = Integer.toString(b);
            String recode2 = result+str;

            if(recode1 == recode2) return;
            ptResult.setText(recode1);
            ptCurrResult.setText(recode2);
            ptResult.setSelection(ptResult.length());

            saveRecode(recode2 +recode1+"\n");

            Log.d(TAG, "recode1: "+ recode1); //결과
            Log.d(TAG, "recode2: "+ recode2);
            Log.d(TAG, "reseult: "+ result); //= 직전
            Log.d(TAG, "reseult2: "+ result2);

            s = result;

        });

        //계산기록 확인
        btnRecode.setOnClickListener(view -> {
            //readRecode();
            //popupActivity 화면 띄우기
            Intent intent = new Intent(
                    mContext,
                    PopupActivity.class
            );
            startActivity(intent);

        });

        //실행취소
        btnUndo.setOnClickListener(v->{
            Log.d(TAG, "initListener: 실행취소 "+s);
            ptResult.setText(s);
            ptCurrResult.setText("");
            ptResult.setSelection(ptResult.length());

        });

//        ptResult.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                //변경이전 상태를 보여줌
//                Log.d(TAG, "변경 전 : "+charSequence);
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                //변경될 때 자동으로 실행
//                Log.d(TAG, "변경 중: "+charSequence);
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                //변경완료된 뒤 자동실행
//                Log.d(TAG, "변경 후: "+editable);
//
//            }
//        });

    }//initListener

    private void initData() {

//        AppDataBase db = Room.databaseBuilder(getApplicationContext(),
//                AppDataBase.class, "history_table").build();
//
//        HistoryDAO historyDAO = db.historyDAO();
//        List<History> histories = historyDAO.getAll();

    }

    private void saveRecode(String msg) {
        try {
            BufferedOutputStream bos =
                    new BufferedOutputStream(new FileOutputStream(new File(getFilesDir()+"/recode.txt"), true));
            bos.write(msg.getBytes());
            bos.close();
            Log.d(TAG, "saveRecode: 저장완료");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }




}//mainActivity