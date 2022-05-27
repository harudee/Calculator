package com.cos.calculator.view;

import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cos.calculator.R;

public class NormalFragment extends Fragment  {

    private static final String TAG = "NormalFragment";
    private NormalFragment mContext = this;

    Button num0, num1, num2, num3, num4, num5, num6, num7, num8, num9;
    TextView tvResult, tvExpression;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       
        View view =  inflater.inflate(R.layout.fragment_normal, container, false);

        //이걸 페이지마다... 해야하는거 아냐...? => 맞음...
        num0 = view.findViewById(R.id.num_0);
        num1 = view.findViewById(R.id.num_1);
        num2 = view.findViewById(R.id.num_2);
        num3 = view.findViewById(R.id.num_3);
        num4 = view.findViewById(R.id.num_4);
        num5 = view.findViewById(R.id.num_5);
        num6 = view.findViewById(R.id.num_6);
        num7 = view.findViewById(R.id.num_7);
        num8 = view.findViewById(R.id.num_8);
        num9 = view.findViewById(R.id.num_9);

        tvResult = view.findViewById(R.id.tv_result);
        tvExpression = view.findViewById(R.id.tv_expression);

        num0.setOnClickListener(myOnClickListener);
        num1.setOnClickListener(myOnClickListener);
        num2.setOnClickListener(myOnClickListener);
        num3.setOnClickListener(myOnClickListener);
        num4.setOnClickListener(myOnClickListener);
        num5.setOnClickListener(myOnClickListener);
        num6.setOnClickListener(myOnClickListener);
        num7.setOnClickListener(myOnClickListener);
        num8.setOnClickListener(myOnClickListener);
        num9.setOnClickListener(myOnClickListener);


        return view;


    }

    private View.OnClickListener myOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Button btn = (Button) view;//얘가 데리고 있는 view는 싹다 Button 타입입어야 함

            switch (view.getId()) {

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

                    onNumberButtonClick(btn.getText().toString());
                    break;

                default:
                    break;
            }

        }
    };

    private void onNumberButtonClick(String text){

        Log.d(TAG, "onNumberButtonClick: 나야나 실행됨");

        tvResult.setText(text);
        tvExpression.append(text);

    }



}