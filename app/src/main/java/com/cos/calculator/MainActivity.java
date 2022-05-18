package com.cos.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.cos.calculator.dao.HistoryDAO;
import com.cos.calculator.model.History;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity222";
    private MainActivity mContext = this;


    private EditText tvResult;
    private TextView tvExpression, printHexValue, printDecValue, printOctValue, printBinValue;
    //private Button btn[] = new Button[17];
    private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnDot, btnPlus, btnMinus, btnMultiple, btnDivision;
    private Button btnModular, btnEnter, btnClear, btnLeft, btnRight;
    private ImageButton btnRecode, btnUndo, btnBackSpace;

    //private int cursorPosition;
    //private String lastCal = "";

    private boolean isOperator = false; //연산자
    private boolean hasEntered = false;
    private boolean hasDotted = false;
    //private boolean hasOperator = false;

    /*private Runnable historyRunnable = new Runnable() {
        @Override
        public void run() {

        }
    };*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        init();
        //tvResult.requestFocus();
        initListener();
        initData();

        AppDatabase database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class,
                "historyDB").build();



        //setBinValue();
        //setNumbers();
//        tvResult.setText("0"); //초기값 설정
//        tvResult.setSelection(tvResult.length());
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

        tvResult = findViewById(R.id.tv_result);//결과창
        tvExpression = findViewById(R.id.tv_expression);//현재 계산상태

        btnModular = findViewById(R.id.btn_modular);

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
                    numberButtonClicked("0");
                    break;
                case R.id.num_1:
                    numberButtonClicked("1");
                    break;
                case R.id.num_2:
                    numberButtonClicked("2");
                    break;
                case R.id.num_3:
                    numberButtonClicked("3");
                    break;
                case R.id.num_4:
                    numberButtonClicked("4");
                    break;
                case R.id.num_5:
                    numberButtonClicked("5");
                    break;
                case R.id.num_6:
                    numberButtonClicked("6");
                    break;
                case R.id.num_7:
                    numberButtonClicked("7");
                    break;
                case R.id.num_8:
                    numberButtonClicked("8");
                    break;
                case R.id.num_9:
                    numberButtonClicked("9");
                    break;
                case R.id.btn_plus:
                    operatorButtonClicked("+");
                    break;
                case R.id.btn_minus:
                    operatorButtonClicked("-");
                    break;
                case R.id.btn_multiple:
                    operatorButtonClicked("*");
                    break;
                case R.id.btn_division:
                    operatorButtonClicked("/");
                    break;
                case R.id.btn_modular:
                    operatorButtonClicked("%");
                    break;

                /*case R.id.btn_dot:
                    break;
                case R.id.btn_parenthesis_left:
                    break;
                case R.id.btn_parenthesis_right:
                    break;*/

                default:
                    break;
            }

        }
    };

    private void initListener() {

        //number
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

        //operator
        btnPlus.setOnClickListener(myOnClickListener);
        btnMinus.setOnClickListener(myOnClickListener);
        btnMultiple.setOnClickListener(myOnClickListener);
        btnDivision.setOnClickListener(myOnClickListener);
        btnModular.setOnClickListener(myOnClickListener);

        /*btnLeft.setOnClickListener(myOnClickListener);
        btnRight.setOnClickListener(myOnClickListener);
        btnClear.setOnClickListener(myOnClickListener);*/

        //소수점 처리
        /*btnDot.setOnClickListener(v -> {

            String dotStatus = tvResult.getText().toString();

            *//*if(hasDotted){
                tvResult.append(".");
            }else *//*
            if(dotStatus.isEmpty()){
                tvResult.setText("0.");
            } else{
                tvResult.append(".");
            }

            hasDotted = true;
            isOperator = false;


        });*/

        //한 개 지우기
        btnBackSpace.setOnClickListener(view -> {

            //계산식 한 개씩 지움
            String enteredExpression = tvExpression.getText().toString();
            String changedExpression = removeLastChar(enteredExpression);
            tvExpression.setText(changedExpression);

            //결과창 한 개씩 지움
            String enteredResult = tvResult.getText().toString();
            String changedResult = removeLastChar(enteredResult);
            tvResult.setText(changedResult);

            isOperator = true;
            hasEntered = false;
            hasDotted = false;

            /*if (tvResult.getText().length() == 0) return;

            String a = tvResult.getText().toString();

            StringBuffer sb = new StringBuffer(a);
            sb.replace(cursorPosition - 1, cursorPosition, "");

            tvResult.setText(sb.toString());
            tvResult.setSelection(cursorPosition - 1);

            tvExpression.setText("");*/

        });

        //전체 지우기
        btnClear.setOnClickListener(v -> {
            tvExpression.setText("");
            tvResult.setText("");

            printBinValue.setText("");
            printHexValue.setText("");
            printOctValue.setText("");
            printDecValue.setText("");

            isOperator = false;
            hasEntered = false;
            hasDotted = false;
            //hasOperator = false;

        });


        //enter
        btnEnter.setOnClickListener(view -> {

            String cutSentence = tvExpression.getText().toString();

            if(cutSentence.isEmpty() || isOperator)
                return;

            String lastExpression = cutSentence + tvResult.getText().toString()+" =";
            tvExpression.setText(lastExpression);

            String calculatingExpression = removeLastChar(lastExpression);

            //Log.d(TAG, "initListener: " + lastExpression); // 5 * 9 + 8 =
            //Log.d(TAG, "initListener: "+ calculatingExpression);// 5 * 9 + 8

            String calculatedResult = MyEval.calculation(calculatingExpression);
            tvResult.setText(calculatedResult);

            isOperator = false;
            hasEntered = true;

            /*//ptCurrResult.append("=");

            if (tvResult.getText().length() == 0) return;

            //Button btn = (Button) view;
            //ptResult.setSelection(ptResult.length());

            String result = tvExpression.getText().toString();
            String result2 = MyEval.calculation(result);

            String str = btnEnter.getText().toString();

            if (result2 == null) return;

            double a = Double.valueOf(result2);
            int b = (int) a;

            if (a == b) {
                tvResult.setText(Integer.toString(b));
                tvExpression.append("=");
                //ptCurrResult.setText(Integer.toString(b));
            } else {
                //ptCurrResult.setText(result2);
                tvExpression.append("=");
                tvResult.setText(result2);
            }
            tvResult.setSelection(tvResult.length());

            //계산 기록 저장
            String recode1 = Double.toString(a);//5
            String recode2 = result + str; //2+3=

            if (recode1.equals(recode2)) return;
            saveRecode(recode2 + recode1 + "\n"); //2+3=5

//            Log.d(TAG, "recode1: "+ recode1); //결과
//            Log.d(TAG, "recode2: "+ recode2);
//            Log.d(TAG, "reseult: "+ result); //= 직전
//            Log.d(TAG, "reseult2: "+ result2);

            lastCal = result;*/

        });//enter

        //계산기록 확인
        /*btnRecode.setOnClickListener(view -> {
            //readRecode();
            //popupActivity 화면 띄우기
            Intent intent = new Intent(
                    mContext,
                    PopupActivity.class
            );
            startActivity(intent);

        });*/

        //실행취소
        /*btnUndo.setOnClickListener(v -> {
            //Log.d(TAG, "initListener: 실행취소 "+s);

            tvResult.setText(lastCal);
            tvExpression.setText("");

            tvResult.setSelection(tvResult.length());

        });*/

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

        tvResult.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //변경이전 상태를 보여줌
                //Log.d(TAG, "변경 전 : "+charSequence);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //변경될 때 자동으로 실행
                //Log.d(TAG, "변경 중: "+charSequence);
                //setNumbers();

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //변경완료된 뒤 자동실행
                //Log.d(TAG, "변경 후: "+editable);
                setNumbers();

            }
        });

    }//initListener

    private String removeLastChar(String sentence){

        if(sentence.isEmpty())
            return null;

        return sentence.substring(0, sentence.length()-1);

    }

    private void numberButtonClicked(String number){

        if(isOperator){ //연산자 입력 후

            if(hasEntered){
                tvExpression.setText("");
                tvResult.setText(number);
            } else{
                tvExpression.append(" ");
                tvResult.setText(number);
            }

        } else{
            tvResult.append(number);
        }

        isOperator = false;
        hasEntered = false;
        hasDotted = false;
        //tvResult.setSelection(tvResult.length());

    }

    private void operatorButtonClicked(String operator){

        String enteredNumber = tvResult.getText().toString();

        Log.d(TAG, "operatorButtonClicked: "+ enteredNumber);

        if(enteredNumber.isEmpty()) return;

        if(isOperator){//사칙연산 연속 클릭 시 다른 연산으로 변경
            //Log.d(TAG, "operatorButtonClicked: 나만 계속 실행됨");
            String enteredSentence = tvExpression.getText().toString();
            String removedSentence = removeLastChar(enteredSentence);
            String changedSentence = removedSentence + operator;

            tvExpression.setText(changedSentence);

        }  else {

            if(hasEntered){
                //Log.d(TAG, "operatorButtonClicked: 엔터 후 실행 ");
                tvExpression.setText(enteredNumber + " " + operator);
            }else {
                //Log.d(TAG, "operatorButtonClicked: ㄴㄴ 내가 실행");
                tvExpression.append(enteredNumber + " " + operator);
            }
        }

        isOperator = true;
        hasDotted = false;
        hasEntered = false;

    }


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

    private void setNumbers() {
        String decNumber = tvResult.getText().toString();

        if(decNumber.isEmpty())
            return;

        if(decNumber.contains("."))
            return;

        int changeNumber = Integer.parseInt(decNumber);

        //String binNumber = String.format("%0" + 4 + "d", Integer.parseInt(Integer.toBinaryString(changeNumber)));
        String binNumber = Integer.toBinaryString(changeNumber);
        String octNumber = Integer.toOctalString(changeNumber);
        String hexNumber = (Integer.toHexString(changeNumber)).toUpperCase();

//        Log.d(TAG, "binNumber " + binNumber);
//        Log.d(TAG, "octNumber " + octNumber);
//        Log.d(TAG, "hexUpper " + hexNumber);

        printBinValue.setText(binNumber);
        printHexValue.setText(hexNumber);
        printOctValue.setText(octNumber);
        printDecValue.setText(decNumber);

    }

    /*private void setBinValue() {//2진수
        int val = 85;
        String str = "";
        //String str2 = String.format("");

        *//*while(val>0){
            str = (val%2) + str;
            val /= 2;
        }*//*

        Log.d(TAG, "setBinValue: 2진수 " + str); //10만 나옴 0010이 나와야..
    }*/


}//mainActivity