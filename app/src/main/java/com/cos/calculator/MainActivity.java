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
import android.widget.Toast;

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

    private Button btnA, btnB, btnC, btnD, btnE, btnF, btnHex, btnDec, btnOct, btnBin;

    private ImageButton btnRecode, btnUndo, btnBackSpace;

    //private int cursorPosition;
    //private String lastCal = "";

    private boolean isOperator = false; //연산자
    private boolean hasEntered = false;
    private boolean hasDotted = false;
    private boolean isModeChanged = false;
    //private boolean hasOperator = false;

    private int calculatorMode = CALCULATOR_MODE_DECIMAL;

    public static final int CALCULATOR_MODE_BINARY = 0;
    public static final int CALCULATOR_MODE_DECIMAL = 1;
    public static final int CALCULATOR_MODE_HEXADECIMAL= 2;
    public static final int CALCULATOR_MODE_OCTAL = 3;


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
        btnModular = findViewById(R.id.btn_modular);
        btnLeft = findViewById(R.id.btn_left);
        btnRight = findViewById(R.id.btn_right);

        btnEnter = findViewById(R.id.btn_enter);
        btnClear = findViewById(R.id.btn_clear);

        btnBackSpace = findViewById(R.id.btn_backSpace);
        btnRecode = findViewById(R.id.btn_recode); //계산기록
        btnUndo = findViewById(R.id.btn_undo); //실행취소 ㄹㅇ Ctrl+z

        tvResult = findViewById(R.id.tv_result);//결과창
        tvExpression = findViewById(R.id.tv_expression);//현재 계산상태


        printHexValue = findViewById(R.id.print_hex_value);
        printDecValue = findViewById(R.id.print_dec_value);
        printOctValue = findViewById(R.id.print_oct_value);
        printBinValue = findViewById(R.id.print_bin_value);

        btnA = findViewById(R.id.btn_a);
        btnB = findViewById(R.id.btn_b);
        btnC = findViewById(R.id.btn_c);
        btnD = findViewById(R.id.btn_d);
        btnE = findViewById(R.id.btn_e);
        btnF = findViewById(R.id.btn_f);

        btnBin = findViewById(R.id.btn_bin);
        btnHex = findViewById(R.id.btn_hex);
        btnOct = findViewById(R.id.btn_oct);
        btnDec = findViewById(R.id.btn_dec);

    }

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

        btnA.setOnClickListener(myOnClickListener);
        btnB.setOnClickListener(myOnClickListener);
        btnC.setOnClickListener(myOnClickListener);
        btnD.setOnClickListener(myOnClickListener);
        btnE.setOnClickListener(myOnClickListener);
        btnF.setOnClickListener(myOnClickListener);

        //operator
        btnPlus.setOnClickListener(myOnClickListener);
        btnMinus.setOnClickListener(myOnClickListener);
        btnMultiple.setOnClickListener(myOnClickListener);
        btnDivision.setOnClickListener(myOnClickListener);
        btnModular.setOnClickListener(myOnClickListener);
        btnLeft.setOnClickListener(myOnClickListener);
        btnRight.setOnClickListener(myOnClickListener);

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


        btnBin.setOnClickListener(v -> {

            if(calculatorMode == CALCULATOR_MODE_BINARY ){
                return;

            } else {
                setBinMode();
            }

        });

        btnHex.setOnClickListener(v -> {
            if(calculatorMode == CALCULATOR_MODE_HEXADECIMAL ){
                return;

            }else{
                setHexMode();
            }

        });

        btnOct.setOnClickListener(v -> {
            if(calculatorMode == CALCULATOR_MODE_OCTAL ){
                return;

            }else{
                setOctMode();
            }

        });

        btnDec.setOnClickListener(v -> {
            if(calculatorMode == CALCULATOR_MODE_DECIMAL ){
                return;
            }else{
                setDecMode();
            }
        });


    }//initListener

    private void initData() {

//        AppDataBase db = Room.databaseBuilder(getApplicationContext(),
//                AppDataBase.class, "history_table").build();
//
//        HistoryDAO historyDAO = db.historyDAO();
//        List<History> histories = historyDAO.getAll();

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

                case R.id.btn_a:
                    numberButtonClicked("A");
                    break;
                case R.id.btn_b:
                    numberButtonClicked("B");
                    break;
                case R.id.btn_c:
                    numberButtonClicked("C");
                    break;
                case R.id.btn_d:
                    numberButtonClicked("D");
                    break;
                case R.id.btn_e:
                    numberButtonClicked("E");
                    break;
                case R.id.btn_f:
                    numberButtonClicked("F");
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
                case R.id.btn_left:
                    break;
                case R.id.btn_right:
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

    private String removeLastChar(String sentence){

        if(sentence.isEmpty())
            return null;

        return sentence.substring(0, sentence.length()-1);

    }

    private void numberButtonClicked(String number){

        /*if(isModeChanged){
            tvExpression.setText("");
            tvResult.setText(number);
        }*/

        /*if(hasEntered){
                tvExpression.setText("");

            } else{
                tvExpression.append(" ");
            }*/

        if(isOperator){

            tvExpression.append(" ");
            tvResult.setText(number);

        } else{

            if(hasEntered){
                tvExpression.setText("");
                tvResult.setText(number);

            } else{
                tvResult.append(number);//
            }

        }

        isOperator = false;
        hasEntered = false;
        hasDotted = false;
        isModeChanged = false;
        //tvResult.setSelection(tvResult.length());

    }

    private void operatorButtonClicked(String operator){

        String enteredNumber = tvResult.getText().toString();

        //Log.d(TAG, "operatorButtonClicked: "+ enteredNumber);

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

    //radix 숫자 값 변경
    private void setNumbers() {

        //if(isModeChanged) return;
        if(calculatorMode == CALCULATOR_MODE_DECIMAL){

            String decNumber = tvResult.getText().toString();

            if(decNumber.isEmpty())
                return;

            if(decNumber.contains("."))
                return;

            int changeNumber = Integer.parseInt(decNumber);//string->integer

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

        } else if(calculatorMode == CALCULATOR_MODE_BINARY){
            String binNumber = tvResult.getText().toString();//100

            if(binNumber.isEmpty()) return;

            int intDecimal = Integer.parseInt(binNumber, 2);

            String strDecimal = Integer.toString(intDecimal);
            String octalNumber = Integer.toOctalString(intDecimal);
            String hexNumber = (Integer.toHexString(intDecimal)).toUpperCase();

            //Log.d(TAG, "setNumbers: decimal "+ decimal);  //voila
            //Log.d(TAG, "setNumbers: octalNumber "+ octalNumber);
            //Log.d(TAG, "setNumbers: hexaNumber "+ hexNumber);

            printDecValue.setText(strDecimal);
            printOctValue.setText(octalNumber);
            printHexValue.setText(hexNumber);
            printBinValue.setText(binNumber);

        } else if(calculatorMode == CALCULATOR_MODE_HEXADECIMAL){
            String hexNumber = (tvResult.getText().toString()).toUpperCase();

            if(hexNumber.isEmpty()) return;

            int intDecimal = Integer.parseInt(hexNumber, 16);

            //Log.d(TAG, "setNumbers: intDecimal "+ intDecimal);//37

            String strDecimal = Integer.toString(intDecimal);
            String octalNumber = Integer.toOctalString(intDecimal);
            String binNumber = Integer.toBinaryString(intDecimal);

            printDecValue.setText(strDecimal);
            printOctValue.setText(octalNumber);
            printHexValue.setText(hexNumber);
            printBinValue.setText(binNumber);

        } else if(calculatorMode == CALCULATOR_MODE_OCTAL){

            String octalNumber = tvResult.getText().toString();

            if(octalNumber.isEmpty()) return;

            int intDecimal = Integer.parseInt(octalNumber, 8);

            String strDecimal = Integer.toString(intDecimal);
            String hexNumber = (Integer.toHexString(intDecimal)).toUpperCase();
            String binNumber = Integer.toBinaryString(intDecimal);

            printDecValue.setText(strDecimal);
            printOctValue.setText(octalNumber);
            printHexValue.setText(hexNumber);
            printBinValue.setText(binNumber);

        }

    }


    private void setBinNumber(){

        //Toast.makeText(getApplicationContext(), "2진수 계산기 모드",Toast.LENGTH_SHORT).show();

        String binNumber = printBinValue.getText().toString();
        tvResult.setText(binNumber);

        /*String changedNumber = tvResult.getText().toString();//바뀐 값을 가지고

        if(changedNumber.isEmpty())
            return;
        if(changedNumber.contains("."))
            return;

        int goChange = Integer.parseInt(changedNumber);

        String ptBinNumber = Integer.toBinaryString(goChange);
        String ptOctNumber = Integer.toOctalString(goChange);
        String ptHexNumber = Integer.toHexString(goChange);

        printBinValue.setText(ptBinNumber);
        printOctValue.setText(ptOctNumber);
        printHexValue.setText(ptHexNumber);*/

    }

    private void setOctNumber(){

        String octNumber = printOctValue.getText().toString();
        tvResult.setText(octNumber);

    }

    private void setDecNumber(){

        String decNumber = printDecValue.getText().toString();
        tvResult.setText(decNumber);

    }

    private void setHexNumber(){

        String hexNumber = printHexValue.getText().toString();
        tvResult.setText(hexNumber);

    }




    //mode 변경 -> 키패드 등등
    private void setBinMode(){

        //Log.d(TAG, "setBinMode: 2진수 모드");
        calculatorMode = CALCULATOR_MODE_BINARY;

        btnHex.setTextColor(getResources().getColorStateList(R.color.black));
        btnDec.setTextColor(getResources().getColorStateList(R.color.black));
        btnOct.setTextColor(getResources().getColorStateList(R.color.black));
        btnBin.setTextColor(getResources().getColorStateList(R.color.teal_700));

        //binary 값으로 나오게하기
        setBinNumber();

        //클릭 이벤트 막기
        btn2.setEnabled(false);
        btn3.setEnabled(false);
        btn4.setEnabled(false);
        btn5.setEnabled(false);
        btn6.setEnabled(false);
        btn7.setEnabled(false);
        btn8.setEnabled(false);
        btn9.setEnabled(false);

        //색상 변경
        btn2.setTextColor(getResources().getColorStateList(R.color.btn_pressed_grey));
        btn3.setTextColor(getResources().getColorStateList(R.color.btn_pressed_grey));
        btn4.setTextColor(getResources().getColorStateList(R.color.btn_pressed_grey));
        btn5.setTextColor(getResources().getColorStateList(R.color.btn_pressed_grey));
        btn6.setTextColor(getResources().getColorStateList(R.color.btn_pressed_grey));
        btn7.setTextColor(getResources().getColorStateList(R.color.btn_pressed_grey));
        btn8.setTextColor(getResources().getColorStateList(R.color.btn_pressed_grey));
        btn9.setTextColor(getResources().getColorStateList(R.color.btn_pressed_grey));

        btnA.setVisibility(View.GONE);
        btnB.setVisibility(View.GONE);
        btnC.setVisibility(View.GONE);
        btnD.setVisibility(View.GONE);
        btnE.setVisibility(View.GONE);
        btnF.setVisibility(View.GONE);

        isModeChanged = true;

    }

    private void setDecMode(){
       //Log.d(TAG, "setDecMode: 10진수 모드");
        calculatorMode = CALCULATOR_MODE_DECIMAL;

        setDecNumber();

        btnHex.setTextColor(getResources().getColorStateList(R.color.black));
        btnDec.setTextColor(getResources().getColorStateList(R.color.teal_700));
        btnOct.setTextColor(getResources().getColorStateList(R.color.black));
        btnBin.setTextColor(getResources().getColorStateList(R.color.black));



        btnA.setVisibility(View.GONE);
        btnB.setVisibility(View.GONE);
        btnC.setVisibility(View.GONE);
        btnD.setVisibility(View.GONE);
        btnE.setVisibility(View.GONE);
        btnF.setVisibility(View.GONE);

        btn2.setEnabled(true);
        btn3.setEnabled(true);
        btn4.setEnabled(true);
        btn5.setEnabled(true);
        btn6.setEnabled(true);
        btn7.setEnabled(true);
        btn8.setEnabled(true);
        btn9.setEnabled(true);

        btn2.setTextColor(getResources().getColorStateList(R.color.black));
        btn3.setTextColor(getResources().getColorStateList(R.color.black));
        btn4.setTextColor(getResources().getColorStateList(R.color.black));
        btn5.setTextColor(getResources().getColorStateList(R.color.black));
        btn6.setTextColor(getResources().getColorStateList(R.color.black));
        btn7.setTextColor(getResources().getColorStateList(R.color.black));
        btn8.setTextColor(getResources().getColorStateList(R.color.black));
        btn9.setTextColor(getResources().getColorStateList(R.color.black));

        isModeChanged = true;

    }

    private void setOctMode(){
        //Log.d(TAG, "setOctMode: 8진수 모드");
        calculatorMode = CALCULATOR_MODE_OCTAL;

        setOctNumber();

        btnHex.setTextColor(getResources().getColorStateList(R.color.black));
        btnDec.setTextColor(getResources().getColorStateList(R.color.black));
        btnOct.setTextColor(getResources().getColorStateList(R.color.teal_700));
        btnBin.setTextColor(getResources().getColorStateList(R.color.black));

        btnA.setVisibility(View.GONE);
        btnB.setVisibility(View.GONE);
        btnC.setVisibility(View.GONE);
        btnD.setVisibility(View.GONE);
        btnE.setVisibility(View.GONE);
        btnF.setVisibility(View.GONE);

        btn2.setEnabled(true);
        btn3.setEnabled(true);
        btn4.setEnabled(true);
        btn5.setEnabled(true);
        btn6.setEnabled(true);
        btn7.setEnabled(true);
        btn8.setEnabled(false);
        btn9.setEnabled(false);

        btn2.setTextColor(getResources().getColorStateList(R.color.black));
        btn3.setTextColor(getResources().getColorStateList(R.color.black));
        btn4.setTextColor(getResources().getColorStateList(R.color.black));
        btn5.setTextColor(getResources().getColorStateList(R.color.black));
        btn6.setTextColor(getResources().getColorStateList(R.color.black));
        btn7.setTextColor(getResources().getColorStateList(R.color.black));
        btn8.setTextColor(getResources().getColorStateList(R.color.btn_pressed_grey));
        btn9.setTextColor(getResources().getColorStateList(R.color.btn_pressed_grey));

        isModeChanged = true;

    }

    private void setHexMode(){

        //Log.d(TAG, "setHexMode: 16진수 모드");
        calculatorMode = CALCULATOR_MODE_HEXADECIMAL;

        setHexNumber();

        btnHex.setTextColor(getResources().getColorStateList(R.color.teal_700));
        btnDec.setTextColor(getResources().getColorStateList(R.color.black));
        btnOct.setTextColor(getResources().getColorStateList(R.color.black));
        btnBin.setTextColor(getResources().getColorStateList(R.color.black));

        btnA.setVisibility(View.VISIBLE);
        btnB.setVisibility(View.VISIBLE);
        btnC.setVisibility(View.VISIBLE);
        btnD.setVisibility(View.VISIBLE);
        btnE.setVisibility(View.VISIBLE);
        btnF.setVisibility(View.VISIBLE);

        btn2.setEnabled(true);
        btn3.setEnabled(true);
        btn4.setEnabled(true);
        btn5.setEnabled(true);
        btn6.setEnabled(true);
        btn7.setEnabled(true);
        btn8.setEnabled(true);
        btn9.setEnabled(true);

        btn2.setTextColor(getResources().getColorStateList(R.color.black));
        btn3.setTextColor(getResources().getColorStateList(R.color.black));
        btn4.setTextColor(getResources().getColorStateList(R.color.black));
        btn5.setTextColor(getResources().getColorStateList(R.color.black));
        btn6.setTextColor(getResources().getColorStateList(R.color.black));
        btn7.setTextColor(getResources().getColorStateList(R.color.black));
        btn8.setTextColor(getResources().getColorStateList(R.color.black));
        btn9.setTextColor(getResources().getColorStateList(R.color.black));

        isModeChanged = true;

    }



}//mainActivity