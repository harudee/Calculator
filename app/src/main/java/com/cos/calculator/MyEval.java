package com.cos.calculator;

import android.util.Log;

import java.util.Arrays;

public class MyEval {

    private static final String TAG = "MyEval";

    public static String calculation(String result){

        //Log.d(TAG, "calculation: result 실행됨");
        //Log.d(TAG, "calculation: "+result);//12+23

        String[] strTestArr1 = result.split("[+]");
        Log.d(TAG, "calculation: " + Arrays.toString(strTestArr1));

        int answer = 0;
        //i번째애들끼리 더해야함
        for(int i=0; i<strTestArr1.length;i++){
            answer += Integer.parseInt(strTestArr1[i]);
        }

        String calResult = String.valueOf(answer);

        Log.d(TAG, "calculation: "+ calResult);

        return calResult;

    }


}
