package com.cos.calculator;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.TabStopSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import com.cos.calculator.dao.HistoryDAO;
import com.cos.calculator.model.History;
import com.cos.calculator.view.NormalFragment;
import com.cos.calculator.view.ProgrammerFragment;
import com.cos.calculator.view.ScienceFragment;
import com.google.android.material.navigation.NavigationView;

import org.mozilla.javascript.tools.jsc.Main;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.concurrent.BlockingDeque;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity222";
    private MainActivity mContext = this;

    //undo, redo
    private Stack<String> undoStack = new Stack<>(); //실행취소
    private Stack<String> redoStack = new Stack<>(); //다시 실행

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private TableLayout tlMiddleLayout;
    private NavigationView navigationView;
    private ImageView menuIcon;

    //private EditText tvResult;
    private TextView tvExpression, printHexValue, printDecValue, printOctValue, printBinValue, tvResult;
    private TextView toolbarTitle;
    //private Button btn[] = new Button[17];
    private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnDot, btnPlus, btnMinus, btnMultiple, btnDivision;
    private Button btnModular, btnEnter, btnClear, btnLeft, btnRight;
    private Button btnSin, btnTan, btnCos, btnRad, btnSqrt, btnIntegral, btnLog, btnDivisionX, btnExp, btnSquare, btnPow, btnAbs, btnPi, btnExpo;

    private Button btnA, btnB, btnC, btnD, btnE, btnF, btnHex, btnDec, btnOct, btnBin;

    private ImageButton btnRecode, btnUndo, btnBackSpace, btnNegative;

    //private int cursorPosition;
    //private String lastCal = "";

    private boolean isOperator = false; //연산자
    private boolean hasEntered = false;

    private boolean hasDotted = false;
    private boolean hasNumbered = false;

    private boolean endedWithBracket = false;
    private boolean hasReturned = false;

    //private boolean isModeChanged = false;
    //private boolean hasOperator = false;

    private int calculatorMode = CALCULATOR_MODE_DECIMAL;
    private static final int CALCULATOR_MODE_BINARY = 0;
    private static final int CALCULATOR_MODE_DECIMAL = 1;
    private static final int CALCULATOR_MODE_HEXADECIMAL = 2;
    private static final int CALCULATOR_MODE_OCTAL = 3;

    private int nMode;
    private static final int NORMAL_MODE = 0;
    private static final int SCIENCE_MODE = 1;
    private static final int PROGRAMMER_MODE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


        init();
        initListener();
        initData();


        setSupportActionBar(toolbar);

//        AppDatabase database = Room.databaseBuilder(getApplicationContext(),
//                AppDatabase.class,
//                "historyDB").build()

        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        menuIcon = findViewById(R.id.menu_icon);
        toolbarTitle = findViewById(R.id.toolbar_title);
        tlMiddleLayout = findViewById(R.id.tl_middle_layout);

        menuIcon.setOnClickListener(v -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });

        navigationView.setNavigationItemSelectedListener(new NavigationViewHelper());
        //getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new NormalFragment()).commit();

    }

    private void init() {

        //Science
        btnSin = findViewById(R.id.btn_sin);
        btnTan = findViewById(R.id.btn_tan);
        btnCos = findViewById(R.id.btn_cos);
        btnRad = findViewById(R.id.btn_rad);
        btnIntegral = findViewById(R.id.btn_integral);
        btnLog = findViewById(R.id.btn_log);
        btnDivisionX = findViewById(R.id.btn_divisionX);
        btnExp = findViewById(R.id.btn_exp);
        btnSquare = findViewById(R.id.btn_square);
        btnPow = findViewById(R.id.btn_pow);
        btnAbs = findViewById(R.id.btn_abs);
        btnPi = findViewById(R.id.btn_pi);
        btnExpo = findViewById(R.id.btn_expo);
        btnSqrt = findViewById(R.id.btn_sqrt);

        btnNegative = findViewById(R.id.btn_negative);

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

        //brackets
        btnLeft = findViewById(R.id.btn_left);
        btnRight = findViewById(R.id.btn_right);


        btnEnter = findViewById(R.id.btn_enter);
        btnClear = findViewById(R.id.btn_clear);
        btnBackSpace = findViewById(R.id.btn_backSpace);
        btnRecode = findViewById(R.id.btn_recode);
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
        
        //Science
        btnSin.setOnClickListener(myOnClickListener);
        btnCos.setOnClickListener(myOnClickListener);
        btnTan.setOnClickListener(myOnClickListener);
        btnRad.setOnClickListener(myOnClickListener);
        btnIntegral.setOnClickListener(myOnClickListener);
        btnLog.setOnClickListener(myOnClickListener);
        btnDivisionX.setOnClickListener(myOnClickListener);
        btnExp.setOnClickListener(myOnClickListener);
        btnSquare.setOnClickListener(myOnClickListener);
        btnPow.setOnClickListener(myOnClickListener);
        btnAbs.setOnClickListener(myOnClickListener);
        btnPi.setOnClickListener(myOnClickListener);
        btnExpo.setOnClickListener(myOnClickListener);
        btnSqrt.setOnClickListener(myOnClickListener);

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

        btnLeft.setOnClickListener(myOnClickListener);
        btnRight.setOnClickListener(myOnClickListener);

        btnClear.setOnClickListener(v -> {
            deleteAll();
        });
        btnEnter.setOnClickListener(v -> {
            startCalculation();
        });
        btnBackSpace.setOnClickListener(v -> {
            deleteOne();
        });

        btnBin.setOnClickListener(myOnClickListener);
        btnDec.setOnClickListener(myOnClickListener);
        btnOct.setOnClickListener(myOnClickListener);
        btnHex.setOnClickListener(myOnClickListener);

        btnUndo.setOnClickListener(v -> {
            setUndo();
        });

        btnNegative.setOnClickListener(v -> {
            onBracketClicked("(");
            tvExpression.append("-");
        });


        //바뀔때마다 변경
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

    private void initData() {

//        AppDataBase db = Room.databaseBuilder(getApplicationContext(),
//                AppDataBase.class, "history_table").build();
//
//        HistoryDAO historyDAO = db.historyDAO();
//        List<History> histories = historyDAO.getAll();

    }


    // Manifest에서 attribute 수정해서 화면 띄울때 사용함
    /*@Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(mContext, "landscape", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_land_main_content);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(mContext, "portrait", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_main);
        }

    }*/

    private String saveExpression;
    private String saveResult;
    private int saveMode;
    private int saveCalculatorMode;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        //저장할 데이터
        saveExpression = tvExpression.getText().toString();
        saveResult = tvResult.getText().toString();
        saveMode = nMode;
        saveCalculatorMode = calculatorMode;

        outState.putString("Expression", saveExpression);
        outState.putString("Result", saveResult);
        outState.putInt("MODE", saveMode);
        outState.putInt("CAL_MODE", saveCalculatorMode);

        //Log.d(TAG, "onSaveInstanceState: 저장해! "+saveMode);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //저장한 데이터 불러오기

        saveExpression = savedInstanceState.getString("Expression");
        saveResult = savedInstanceState.getString("Result");
        saveMode = savedInstanceState.getInt("MODE");
        saveCalculatorMode = savedInstanceState.getInt("CAL_MODE");

        setMode(saveMode);
        setCalculatorMode(saveCalculatorMode);
        tvExpression.setText(saveExpression);
        tvResult.setText(saveResult);

        //Log.d(TAG, "onRestoreInstanceState: 지금 바뀜! "+saveMode);

    }

    private void setMode(int nMode){
        switch(nMode){
            case 0:
                setNormalMode();
                break;
            case 1:
                setScienceMode();
                break;
            case 2:
                setProgrammerMode();
                break;
            default:
                break;
        }
    }

    private void setCalculatorMode(int calculatorMode){
        switch (calculatorMode){
            case 0:
                setBinMode();
                break;
            case 1:
                setDecMode();
                break;
            case 2:
                setHexMode();
                break;
            case 3:
                setOctMode();
                break;
            default:
                break;
        }

    }


    class NavigationViewHelper implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {
            Fragment selectedFragment = null; //이거 하면 버튼 하나하나 다시 입력해야함

            if(getWindowManager().getDefaultDisplay().getRotation() == Surface.ROTATION_0){//세로모드일때
                toolbarTitle.setText(menuitem.getTitle() + " 계산기");
            }

            switch (menuitem.getItemId()) {
                case R.id.nav_normal:
                    setNormalMode();
                    break;

                case R.id.nav_science:
                    setScienceMode();
                    break;

                case R.id.nav_programmer:
                    setProgrammerMode();
                    break;
            }

            //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
    }

    private void setNormalMode(){ //표준 계산기
        nMode = NORMAL_MODE;
        deleteAll();
        tlMiddleLayout.setVisibility(View.GONE);
        setDecMode();

    }

    private void setScienceMode(){//공학용 계산기
        //누르면 가로모드로...?!
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //Toast.makeText(mContext, "화면을 회전시키세요", Toast.LENGTH_SHORT).show();

        nMode = SCIENCE_MODE;

        deleteAll();
        tlMiddleLayout.setVisibility(View.GONE);
        setDecMode();

        //selectedFragment = new NormalFragment();
    }

    private void setProgrammerMode(){ //프로그래머 계산기
        nMode = PROGRAMMER_MODE;

        deleteAll();
        tlMiddleLayout.setVisibility(View.VISIBLE);
        btnDot.setEnabled(false);
        btnDot.setTextColor(getResources().getColorStateList(R.color.btn_pressed_grey));


        //btnDot.setTextColor();
        //selectedFragment = new ProgrammerFragment();
    }


    private View.OnClickListener myOnClickListener = new View.OnClickListener() { //클릭했을때 뭐가 처리되는 이벤트들은 다 여기서 handle
        @Override
        public void onClick(View view) {

            Button btn = (Button) view;

            switch (view.getId()) {

                //Science
                case R.id.btn_sin:
                case R.id.btn_cos:
                case R.id.btn_tan:
                case R.id.btn_log:
                case R.id.btn_exp: // e의 n승
                case R.id.btn_sqrt: // √ 제곱근
                    if(hasNumbered){
                        operatorButtonClicked("*");
                    }
                    tvExpression.append(btn.getText().toString());
                    onBracketClicked("(");
                    break;

                case R.id.btn_divisionX: // 1/n
                    if(hasNumbered){
                        operatorButtonClicked("*");
                    }
                    numberButtonClicked("1");
                    operatorButtonClicked("/");
                    break;

                case R.id.btn_square: //2승
                    if(hasNumbered){
                        operatorButtonClicked("^");
                        onBracketClicked("(");
                        numberButtonClicked("2");
                    }else{
                        Toast.makeText(mContext, " 완성되지 않은 수식입니다 ", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.btn_pow:  //n승
                    if(hasNumbered){
                        operatorButtonClicked("^");
                        onBracketClicked("(");
                    } else{
                        Toast.makeText(mContext, " 완성되지 않은 수식입니다 ", Toast.LENGTH_SHORT).show();
                    }

                    break;

                case R.id.btn_pi:
                case R.id.btn_expo:
                    if(hasNumbered)
                        operatorButtonClicked("*");

                    numberButtonClicked(btn.getText().toString());
                    break;

                case R.id.btn_abs: // 절대값
                    if(hasNumbered){
                        operatorButtonClicked("*");
                    }
                    tvExpression.append("abs");
                    onBracketClicked("(");
                    break;

                case R.id.btn_rad:
                case R.id.btn_integral:
                    break;

                    //normal
                case R.id.btn_dot:
                    if (hasDotted && hasNumbered)
                        return;

                    if (isOperator || tvResult.getText().toString().isEmpty()) {
                        //tvExpression.append(" ");
                        tvResult.setText("0.");
                    } else
                        tvResult.append(".");

                    hasDotted = true;
                    break;

                case R.id.menu_icon:
                    Log.d(TAG, "onClick: 메뉴 클릭됨");
                    drawerLayout.openDrawer(GravityCompat.START);
                    break;

                case R.id.num_0:
                case R.id.num_1:
                case R.id.num_2:
                case R.id.num_3:
                case R.id.num_4:
                case R.id.num_5:
                case R.id.num_6:
                case R.id.num_7:
                case R.id.num_8:
                case R.id.num_9:
                case R.id.btn_a:
                case R.id.btn_b:
                case R.id.btn_c:
                case R.id.btn_d:
                case R.id.btn_e:
                case R.id.btn_f:
                    numberButtonClicked(btn.getText().toString());
                    break;

                case R.id.btn_plus:
                case R.id.btn_minus:
                case R.id.btn_multiple:
                case R.id.btn_division:
                case R.id.btn_modular:
                    operatorButtonClicked(btn.getText().toString());
                    break;

                case R.id.btn_left:
                case R.id.btn_right:
                    onBracketClicked(btn.getText().toString());
                    break;


                case R.id.btn_bin:
                    if (calculatorMode == CALCULATOR_MODE_BINARY) {
                        return;
                    } else {
                        setBinMode();
                    }
                    break;
                case R.id.btn_oct:
                    if (calculatorMode == CALCULATOR_MODE_OCTAL) {
                        return;
                    } else {
                        setOctMode();
                    }
                    break;
                case R.id.btn_dec:
                    if (calculatorMode == CALCULATOR_MODE_DECIMAL) {
                        return;
                    } else {
                        setDecMode();
                    }
                    break;
                case R.id.btn_hex:
                    if (calculatorMode == CALCULATOR_MODE_HEXADECIMAL) {
                        return;
                    } else {
                        setHexMode();
                    }
                    break;

                default:
                    break;
            }

        }
    };



    private void setRedo() {//안만들었네..?
        if (redoStack.isEmpty())
            return;

        String redoSentence = redoStack.pop();
//        String lastRedoExp = removeLastChar(redoSentence);
//        char lastRedoChar = redoSentence.charAt(redoSentence.length() - 1);
//
//        tvExpression.setText(lastRedoExp);
//        tvResult.setText(String.valueOf(lastRedoChar));

        tvExpression.setText(redoSentence);
        tvResult.setText("");

        isOperator = false;
        hasReturned = true;

    }

    private void setUndo() {
        Log.d(TAG, "setUndo: undo 실행됨");

        if (undoStack.isEmpty()) {
            Toast.makeText(mContext, " 계산기록이 없습니다 ", Toast.LENGTH_SHORT).show();
            return;
        }


        String redoSentence = tvExpression.getText().toString() + tvResult.getText().toString();
        Log.d(TAG, "setRedo: " + redoSentence);
        redoStack.push(redoSentence);

        String undoSentence = undoStack.pop();
        Log.d(TAG, "setUndo: " + undoSentence);

//        String lastUndoExp = removeLastChar(undoSentence);
//        char lastUndoChar = undoSentence.charAt(undoSentence.length() - 1);
//
//        tvExpression.setText(lastUndoExp);
//        tvResult.setText(String.valueOf(lastUndoChar));

        tvExpression.setText(undoSentence);
        tvResult.setText("");

        isOperator = false;
        hasReturned = true;
        //hasEntered = true; //expression, result 둘다 삭제되고 다시 입력됨

    }

    private void deleteOne() {
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
        hasNumbered = false;
        hasReturned = false;
    }

    private void deleteAll() {
        tvExpression.setText("");
        tvResult.setText("");

        printBinValue.setText("");
        printHexValue.setText("");
        printOctValue.setText("");
        printDecValue.setText("");

        stackBracket.clear();

        isOperator = false;
        hasEntered = false;
        hasDotted = false;
        hasNumbered = false;
        hasReturned = false;
    }

    private void startCalculation() {//enter 눌렀을 때 처리

        //수식 상태 체크
        if (hasEntered || isOperator || (tvResult.getText().toString().isEmpty() && tvExpression.getText().toString().isEmpty()))
            return;

        if(!stackBracket.isEmpty()){ // 괄호 닫혔는지 확인
            Toast.makeText(mContext, "완성되지않은 수식입니다", Toast.LENGTH_SHORT).show();
            return;
        }
        

        String cutSentence = tvExpression.getText().toString();

        //Log.d(TAG, "startCalculation: 마지막 "+ cutSentence); //5 *

        String undoSentence = cutSentence + tvResult.getText().toString();

        Log.d(TAG, "startCalculation: ㄹㅇ 마지막 저장 " + undoSentence); //5 * 6
        undoStack.push(undoSentence);


        String lastExpression = undoSentence +"=";
        tvExpression.setText(lastExpression);

        String calculatingExpression = removeLastChar(lastExpression); // '= 제거'


        //진법 계산
        StringTokenizer token = new StringTokenizer(calculatingExpression, "*/%+-()√^", true);

        List<String> arrTokenSplit = new ArrayList<>();
        while(token.hasMoreTokens()){
            arrTokenSplit.add(token.nextToken());
        }

        //진법 계산
//        String[] arr = calculatingExpression.split(" ");
//        //Log.d(TAG, "initListener: arr " + Arrays.toString(arr)); //arr [C8, *, C8]
//
//        List<String> arrList = new ArrayList<>(Arrays.asList(arr));
//
        if(calculatorMode == CALCULATOR_MODE_BINARY){

            for(int i =0; i<arrTokenSplit.size();i++){
                switch(arrTokenSplit.get(i)){
                    case "+":
                    case "-":
                    case "*":
                    case "/":
                    case "%":
                    case "(":
                    case ")":
                    //공학계산 추가
                    case "√":
                    case "^":
                    case "sin":
                    case "cos":
                    case "tan":
                        break;
                    default:
                        String binNumber = arrTokenSplit.get(i);
                        int intDecimal = Integer.parseInt(binNumber, 2);

                        String strDecimal = Integer.toString(intDecimal);

                        arrTokenSplit.set(i, strDecimal);
                        break;
                }

            }

            //Log.d(TAG, "initListener: bin "+arrList.toString());

            String changedExpression = "";
            for(int i =0; i<arrTokenSplit.size(); i++){ //전송할 식 다시 만들기
                changedExpression += arrTokenSplit.get(i);
            }

            //Log.d(TAG, "initListener: arrList "+ changedExpression);

            //계산하고 반환값도 다시 각 진수에 맞게 바꿔줘야함
            String changedResult = MyEval.calculation(changedExpression); // 얘는 지금 String decimal
            int integerChangedResult = Integer.parseInt(changedResult);
            String binNumber = Integer.toBinaryString(integerChangedResult);

            tvResult.setText(binNumber);


        } else if(calculatorMode == CALCULATOR_MODE_HEXADECIMAL){

            //int intDecimal = Integer.parseInt(hexNumber, 16);

            for(int i =0; i<arrTokenSplit.size();i++){
                switch(arrTokenSplit.get(i)){
                    case "+":
                    case "-":
                    case "*":
                    case "/":
                    case "%":
                    case "(":
                    case ")":
                    //공학계산 추가
                    case "√":
                    case "^":
                    case "sin":
                    case "cos":
                    case "tan":
                        break;
                    default:
                        String hexNumber = arrTokenSplit.get(i);
                        int intDecimal = Integer.parseInt(hexNumber, 16);

                        String strDecimal = Integer.toString(intDecimal);

                        arrTokenSplit.set(i, strDecimal);
                        break;
                }

            }

            //Log.d(TAG, "initListener: hex "+arrList.toString()); //[200, *, 200]

            //String으로 바꾸기
            String changedExpression = "";
            for(int i =0; i<arrTokenSplit.size(); i++){
                changedExpression += arrTokenSplit.get(i);
            }

            //Log.d(TAG, "initListener: arrList "+ changedExpression); //200*8

            String changedResult = MyEval.calculation(changedExpression);
            int integerChangedResult = Integer.parseInt(changedResult);
            String hexNumber = (Integer.toHexString(integerChangedResult)).toUpperCase();

            tvResult.setText(hexNumber);


        } else if(calculatorMode == CALCULATOR_MODE_OCTAL){

            for(int i =0; i<arrTokenSplit.size();i++){
                switch(arrTokenSplit.get(i)){
                    case "+":
                    case "-":
                    case "*":
                    case "/":
                    case "%":
                    case "(":
                    case ")":
                    //공학계산 추가
                    case "√":
                    case "^":
                    case "sin":
                    case "cos":
                    case "tan":
                        break;
                    default:
                        String octNumber = arrTokenSplit.get(i);
                        int intDecimal = Integer.parseInt(octNumber, 8);

                        String strDecimal = Integer.toString(intDecimal);

                        arrTokenSplit.set(i, strDecimal);
                        break;
                }

            }

            String changedExpression = "";
            for(int i =0; i<arrTokenSplit.size(); i++){
                changedExpression += arrTokenSplit.get(i);
            }


            String changedResult = MyEval.calculation(changedExpression);
            int integerChangedResult = Integer.parseInt(changedResult);
            String octNumber = Integer.toOctalString(integerChangedResult);

            tvResult.setText(octNumber);

        } else  { //일반 모드 decimal

            //계산하러 보냄
            String calculatedResult = MyEval.calculation(calculatingExpression); //일단 보냄 MyEval로 보냈슴
            tvResult.setText(calculatedResult);

        }

        isOperator = false;
        hasEntered = true;

    }

    private String removeLastChar(String sentence) {

        if (sentence.isEmpty())
            return null;

        return sentence.substring(0, sentence.length() - 1);

    }

    private Stack<Character> stackBracket = new Stack<>();

    private void onBracketClicked(String bracket) {

        //Error
        char lastChar = bracket.charAt(0);
        String lastStr = tvResult.getText().toString();

        if (bracket.equals("(")) {

            if(hasEntered)
                tvExpression.setText(bracket);
            else
                tvExpression.append(bracket);

            stackBracket.push(lastChar);


        } else if (bracket.equals(")") && !stackBracket.isEmpty()) {

            stackBracket.pop();

            if (hasNumbered)
                tvExpression.append(lastStr + bracket);
            else
                tvExpression.append(bracket);

            //tvExpression.append(" ");
            tvResult.setText("");

        } else if (stackBracket.isEmpty()) {

            Toast.makeText(mContext, "수식을 다시 확인하세요", Toast.LENGTH_SHORT).show();
        }

        endedWithBracket = true;
        hasNumbered = false;
        //Log.d(TAG, "onBracketClicked: "+ stack.size());

    }

    private void numberButtonClicked(String number) {

        if (hasDotted || hasReturned) {
            //tvExpression.append(" ");
            tvResult.append(number);

        } else if (isOperator) {//변수 이름 바꿔

            //tvExpression.append(" ");
            tvResult.setText(number);

        } else {

            if (hasEntered) { //얘도 이름 이상함
                tvExpression.setText("");
                tvResult.setText(number);

            } else {
                tvResult.append(number);
            }

        }

        isOperator = false;
        hasEntered = false;
        hasNumbered = true;
        endedWithBracket = false;
        hasReturned = false;

        //isModeChanged = false;
        //tvResult.setSelection(tvResult.length());

    }

    private void operatorButtonClicked(String operator) {
        //코드가 개에바

        String enteredNumber = tvResult.getText().toString();

        //Log.d(TAG, "operatorButtonClicked: "+ enteredNumber);

        if (enteredNumber.isEmpty() && !endedWithBracket)
            return;

        else if (isOperator) {//사칙연산 연속 클릭 시 다른 연산으로 변경

            String enteredSentence = tvExpression.getText().toString();
            String removedSentence = removeLastChar(enteredSentence);
            String changedSentence = removedSentence + operator;

            tvExpression.setText(changedSentence);

        } else {

            if (hasEntered) {
                tvExpression.setText(enteredNumber + operator);
            } else {
                tvExpression.append(enteredNumber + operator);
            }
        }

        isOperator = true;
        hasNumbered = false;
        hasDotted = false;
        hasEntered = false;
        //endedWithBracket = false;
        hasReturned = false;

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

        if (tvResult.getText().toString().isEmpty())
            return;

        if (tvResult.getText().toString().contains("."))
            return;

        if (tvResult.getText().toString().contains("("))
            return;

        if (tvResult.getText().toString().contains(")"))
            return;

        if(tvResult.getText().toString().contains("π"))
            return;

        if(tvResult.getText().toString().contains("e"))
            return;


        //if(isModeChanged) return;
        if (calculatorMode == CALCULATOR_MODE_DECIMAL) {

            String decNumber = tvResult.getText().toString();

            int changeNumber = Integer.parseInt(decNumber);//string->integer

            String binNumber = Integer.toBinaryString(changeNumber);
            String octNumber = Integer.toOctalString(changeNumber);
            String hexNumber = (Integer.toHexString(changeNumber)).toUpperCase();

//        Log.d(TAG, "binNumber " + binNumber);
//        Log.d(TAG, "octNumber " + octNumber);
//        Log.d(TAG, "hexUpper " + hexNumber);

            //Log.d(TAG, "setNumbers: "+String.format("%0"+4+"d", Integer.parseInt(binNumber)));

            printBinValue.setText(binNumber);
            printHexValue.setText(hexNumber);
            printOctValue.setText(octNumber);
            printDecValue.setText(decNumber);

        } else if (calculatorMode == CALCULATOR_MODE_BINARY) {
            String binNumber = tvResult.getText().toString();//100

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

        } else if (calculatorMode == CALCULATOR_MODE_HEXADECIMAL) {
            String hexNumber = (tvResult.getText().toString()).toUpperCase();

            int intDecimal = Integer.parseInt(hexNumber, 16);

            //Log.d(TAG, "setNumbers: intDecimal "+ intDecimal);//37

            String strDecimal = Integer.toString(intDecimal);
            String octalNumber = Integer.toOctalString(intDecimal);
            String binNumber = Integer.toBinaryString(intDecimal);

            printDecValue.setText(strDecimal);
            printOctValue.setText(octalNumber);
            printHexValue.setText(hexNumber);
            printBinValue.setText(binNumber);

        } else if (calculatorMode == CALCULATOR_MODE_OCTAL) {

            String octalNumber = tvResult.getText().toString();

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


    private void setBinNumber() {

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

    private void setOctNumber() {

        String octNumber = printOctValue.getText().toString();
        tvResult.setText(octNumber);

    }

    private void setDecNumber() {

        String decNumber = printDecValue.getText().toString();
        tvResult.setText(decNumber);

    }

    private void setHexNumber() {

        String hexNumber = printHexValue.getText().toString();
        tvResult.setText(hexNumber);

    }

    //mode 변경 -> 키패드 등 상태변경
    private void setBinMode() {

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

        //isModeChanged = true;

    }

    private void setDecMode() {
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

        btnDot.setEnabled(true);
        btnDot.setTextColor(getResources().getColorStateList(R.color.black));

        //isModeChanged = true;

    }

    private void setOctMode() {
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

        //isModeChanged = true;

    }

    private void setHexMode() {

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

        //isModeChanged = true;

    }


}//mainActivity