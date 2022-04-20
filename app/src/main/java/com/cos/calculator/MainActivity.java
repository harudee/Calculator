package com.cos.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity222";

    private EditText ptResult;
    private Button btn[] = new Button[17];
    private ImageButton btnRecode, btnUndo, btnBackSpace;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        initLr();
        initData();
    }

    private void init(){
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
        btn[10] = findViewById(R.id.num_00);
        btn[11] = findViewById(R.id.btn_division);
        btn[12] = findViewById(R.id.btn_plus);
        btn[13] = findViewById(R.id.btn_minus);
        btn[14] = findViewById(R.id.btn_multiple);

        btn[15] = findViewById(R.id.btn_enter);
        btn[16] = findViewById(R.id.btn_clear);

        btnRecode = findViewById(R.id.btn_recode);
        btnBackSpace = findViewById(R.id.btn_backSpace);
        btnUndo = findViewById(R.id.btn_undo);
        ptResult = findViewById(R.id.pt_result);

    }

    private void initLr(){

        for(int i=0; i<15; i++){
            btn[i].setOnClickListener(view -> {
                Button btn = (Button) view;
                ptResult.append(btn.getText().toString());
            });
        }

        btn[15].setOnClickListener(view -> {
            //계산
            String result = ptResult.getText().toString();
            ptResult.setText(Eval.cal(result));

            Log.d(TAG, "initListener: result : "+result);

        });

        btn[16].setOnClickListener(view -> {
            ptResult.setText(""); //clear
        });


    }

    private void initData(){

    }

}