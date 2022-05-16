package com.cos.calculator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    private TextView ptCurrResult, printHexValue, printDecValue, printOctValue, printBinValue;
    //private Button btn[] = new Button[17];
    private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnDot, btnPlus, btnMinus, btnMultiple, btnDivision;
    private Button btnModular, btnEnter, btnClear, btnLeft, btnRight;
    private ImageButton btnRecode, btnUndo, btnBackSpace;

    private int cursorPosition;
    private String currEntered = ""; //현재 입력값
    private int dotCount = 1;
    private String lastCal = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        ptResult.requestFocus();
        initListener();
        initData();

        setBinValue();
    }

    private void init() {

        btnDot = findViewById(R.id.btn_dot);
        btn0 = findViewById(R.id.num_0);
        btn1 = findViewById(R.id.num_1);
        btn2 = findViewById(R.id.num_2);
        btn3 = findViewById(R.id.num_3);
        btn4 = findViewById(R.id.num_4);
        btn5 = findViewById(R.id.num_5);
        btn6 = findViewById(R.id.num_6);
        btn7 = findViewById(R.id.num_7);
        btn8 = findViewById(R.id.num_8);
        btn9 = findViewById(R.id.num_9);

        btnMultiple = findViewById(R.id.btn_multiple);
        btnDivision = findViewById(R.id.btn_division);
        btnPlus = findViewById(R.id.btn_plus);
        btnMinus = findViewById(R.id.btn_minus);

        btnLeft = findViewById(R.id.btn_parenthesis_left);
        btnRight = findViewById(R.id.btn_parenthesis_right);

        btnEnter = findViewById(R.id.btn_enter);
        btnClear = findViewById(R.id.btn_clear);

        btnBackSpace = findViewById(R.id.btn_backSpace);
        btnRecode = findViewById(R.id.btn_recode); //계산기록
        btnUndo = findViewById(R.id.btn_undo); //실행취소 ㄹㅇ Ctrl+z

        ptResult = findViewById(R.id.pt_result);//결과창
        ptCurrResult = findViewById(R.id.pt_currResult);//현재 계산상태

        btnModular= findViewById(R.id.btn_modular);

        printHexValue = findViewById(R.id.print_hex_value);
        printDecValue = findViewById(R.id.print_dec_value);
        printOctValue = findViewById(R.id.print_oct_value);
        printBinValue = findViewById(R.id.print_bin_value);


    }

    private View.OnClickListener myOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.num_0:
                    ptResult.setText("0");
                    ptCurrResult.append("0");
                    break;
                case R.id.num_1:
                    ptResult.setText("1");
                    ptCurrResult.append("1");
                    break;
                case R.id.num_2:
                    ptResult.setText("2");
                    ptCurrResult.append("2");
                    break;
                case R.id.num_3:
                    ptResult.setText("3");
                    ptCurrResult.append("3");
                    break;
                case R.id.num_4:
                    ptResult.setText("4");
                    ptCurrResult.append("4");
                    break;
                case R.id.num_5:
                    ptResult.setText("5");
                    ptCurrResult.append("5");
                    break;
                case R.id.num_6:
                    ptResult.setText("6");
                    ptCurrResult.append("6");
                    break;
                case R.id.num_7:
                    ptResult.setText("7");
                    ptCurrResult.append("7");
                    break;
                case R.id.num_8:
                    ptResult.setText("8");
                    ptCurrResult.append("8");
                    break;
                case R.id.num_9:
                    ptResult.setText("9");
                    ptCurrResult.append("9");
                    break;
                case R.id.btn_dot:
                    ptResult.append(".");
                    ptCurrResult.append(".");
                    break;
                case R.id.btn_plus:
                    ptCurrResult.append("+");
                    break;
                case R.id.btn_minus:
                    ptCurrResult.append("-");
                    break;
                case R.id.btn_multiple:
                    ptCurrResult.append("*");
                    break;
                case R.id.btn_division:
                    ptCurrResult.append("/");
                    break;
                case R.id.btn_parenthesis_left:
                    ptCurrResult.append("(");
                    break;
                case R.id.btn_parenthesis_right:
                    ptCurrResult.append(")");
                    break;
                case R.id.btn_clear:
                    ptResult.setText("");
                    ptCurrResult.setText("");
                    break;
                case R.id.btn_modular:
                    ptCurrResult.append("%");
                default:
                    break;
            }

        }
    };

    private void initListener() {

        //숫자
        btn0.setOnClickListener(myOnClickListener);
        btn1.setOnClickListener(myOnClickListener);
        btn2.setOnClickListener(myOnClickListener);
        btn3.setOnClickListener(myOnClickListener);
        btn4.setOnClickListener(myOnClickListener);
        btn5.setOnClickListener(myOnClickListener);
        btn6.setOnClickListener(myOnClickListener);
        btn7.setOnClickListener(myOnClickListener);
        btn8.setOnClickListener(myOnClickListener);
        btn9.setOnClickListener(myOnClickListener);
        btnDot.setOnClickListener(myOnClickListener);

        //사칙연산
        btnPlus.setOnClickListener(myOnClickListener);
        btnMinus.setOnClickListener(myOnClickListener);
        btnMultiple.setOnClickListener(myOnClickListener);
        btnDivision.setOnClickListener(myOnClickListener);

        btnClear.setOnClickListener(myOnClickListener);
        btnLeft.setOnClickListener(myOnClickListener);
        btnRight.setOnClickListener(myOnClickListener);
        btnModular.setOnClickListener(myOnClickListener);


        //한 개 지우기
        btnBackSpace.setOnClickListener(view -> {

            if (ptResult.length() == 0) return;

            String a = ptResult.getText().toString();

            cursorPosition = ptResult.getSelectionStart();
            StringBuffer sb = new StringBuffer(a);
            sb.replace(cursorPosition - 1, cursorPosition, "");

            ptResult.setText(sb.toString());
            ptResult.setSelection(cursorPosition - 1);

            ptCurrResult.setText("");

        });


        //enter
        btnEnter.setOnClickListener(view -> {

            //ptCurrResult.append("=");

            if (ptResult.getText().length() == 0) return;

            //Button btn = (Button) view;
            //ptResult.setSelection(ptResult.length());

            String result = ptCurrResult.getText().toString();
            String result2 = MyEval.calculation(result);

            String str = btnEnter.getText().toString();

            if (result2 == null) return;

            double a = Double.valueOf(result2);
            int b = (int) a;

            if (a == b) {
                ptResult.setText(Integer.toString(b));
                ptCurrResult.append("=");
                //ptCurrResult.setText(Integer.toString(b));
            } else {
                //ptCurrResult.setText(result2);
                ptCurrResult.append("=");
                ptResult.setText(result2);
            }
            ptResult.setSelection(ptResult.length());

            //계산 기록 저장
            String recode1 = Double.toString(a);//5
            String recode2 = result + str; //2+3=

            if (recode1.equals(recode2)) return;
            saveRecode(recode2 + recode1 + "\n"); //2+3=5

//            Log.d(TAG, "recode1: "+ recode1); //결과
//            Log.d(TAG, "recode2: "+ recode2);
//            Log.d(TAG, "reseult: "+ result); //= 직전
//            Log.d(TAG, "reseult2: "+ result2);

            lastCal = result;

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
        btnUndo.setOnClickListener(v -> {
            //Log.d(TAG, "initListener: 실행취소 "+s);

            ptResult.setText(lastCal);
            ptCurrResult.setText("");

            ptResult.setSelection(ptResult.length());

        });

        //숫자입력
        /*for (int i = 0; i < 11; i++) {
            int I = i;
            btn[i].setOnClickListener(view -> {

                s+="";

                Button btn = (Button) view;
                cursorPosition = ptResult.getSelectionStart();
                String sentence = ptResult.getText().toString();

                //char lastChar = 'f';
//                if(s != null) {
//                    lastChar = sentence.charAt(sentence.length()-1);
//                }
                //if (lastChar == 'f') {


                    if (I == 0) { //최초 입력
                        if (s == null || dotCount == 1) {

                            ptResult.append("0");
                            ptResult.append(".");
                            dotCount = 0;

                            s += "0.";

                        } else {

                            dotCount = 0;

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

                        dotCount = 0;

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
               // }

            });
        }

        //사칙연산
        for (int i = 12; i < 16; i++) {

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

        }*/

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
                    new BufferedOutputStream(new FileOutputStream(new File(getFilesDir() + "/recode.txt"), true));
            bos.write(msg.getBytes());
            bos.close();
            Log.d(TAG, "saveRecode: 저장완료");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void setHexValue(){ //16진수


    }

    private void setDecValue(){//10진수

    }

    private void setOctValue(){//8진수

    }

    private void setBinValue(){//2진수
        int val = 15; /*Integer.parseInt(ptResult.getText().toString());*/
        //String binaryString = integer


        /*hile(val>0){
            str = (val%2) + str;
            val /= 2;
        }*/

        Log.d(TAG, "setBinValue: 2진수 "+ binaryString); //10만 나옴 0010이 나와야하지않나....
    }


}//mainActivity