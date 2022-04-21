package com.cos.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity222";

    private EditText ptResult;
    private TextView ptCurrResult;
    private Button btn[] = new Button[17];
    private ImageButton btnRecode, btnUndo, btnBackSpace;

    private boolean isEntered = false;
    private int cursorPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        ptResult.requestFocus();

        initLr();
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

    private void initLr() {

        for (int i = 0; i < 10; i++) {
            btn[i].setOnClickListener(view -> {
                Button btn = (Button) view;

                ptResult.append(btn.getText().toString());

                isEntered = false;

//                ptResult.setTextIsSelectable(true);//커서는 살리고
//                ptResult.setShowSoftInputOnFocus(false);//키보드는 숨기기
                ptResult.setSelection(ptResult.length());//커서를 제일뒤로

                String result = ptResult.getText().toString();

//                Log.d(TAG, "initLr: result3 " + result);

                if (result != null) {
                    double a = Double.valueOf(Eval.cal(result));
                    int b = (int) a;

                    if (a == b) {
                        ptCurrResult.setText(Integer.toString(b));
                    } else ptCurrResult.setText(Eval.cal(result));
                }

            });
        }

        for (int i = 10; i < 15; i++) {//사칙연산
            btn[i].setOnClickListener(view -> {

                Button btn = (Button) view;

                cursorPosition = ptResult.getSelectionStart();

                String a = btn.getText().toString();

                Log.d(TAG, "initLr: a "+a);

                String b = ptResult.getText().toString(); //전체글자에서
                StringBuffer sb = new StringBuffer(b);
                sb.insert(cursorPosition, a);

                Log.d(TAG, "initLr: sb " + sb);

                if (!isEntered) {
                    //처음 그리기
                    ptResult.setText(sb.toString());
                    ptResult.setSelection(cursorPosition+1);
                    ptCurrResult.setText("");

                    isEntered = true;

                } else{

                    sb.replace(cursorPosition-1, cursorPosition, ""); // 지우고 그리기
                    ptResult.setText(sb.toString());
                    ptResult.setSelection(cursorPosition);

                }

            });

        }

        btn[15].setOnClickListener(view -> { //enter

            if (ptResult.getText().length() == 0) return;

            isEntered = false;

            Button btn = (Button) view;

            ptResult.setSelection(ptResult.length());

            String result = ptResult.getText().toString();
            String result2 = Eval.cal(result);
            String str = btn.getText().toString(); //=

            if (result2 != null) {
                double a = Double.valueOf(Eval.cal(result2));
                int b = (int) a;

                ptResult.setText(Integer.toString(b));
                ptCurrResult.setText(result + str);
                ptResult.setSelection(ptResult.length());

            }


        });


        btn[16].setOnClickListener(view -> {
            //clear
            ptResult.setText("");
            ptCurrResult.setText("");
        });


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

    }

    private void initData() {

    }

}