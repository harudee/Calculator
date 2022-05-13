package com.cos.calculator;

import android.util.Log;

import java.util.Arrays;

public class MyEval {

    private static final String TAG = "MyEval";

    public static String calculation(String result){

        //Log.d(TAG, "calculation: result 실행됨");
        Log.d(TAG, "calculation: "+result);//1+2-3

        if(result.contains("-")){
            String[] strTestArr2 = result.split("-");
            Log.d(TAG, "calculation: "+Arrays.toString(strTestArr2));
        }

        /* //1+2=3 만들기
        String[] strTestArr1 = result.split("[+]");
        //Log.d(TAG, "calculation: " + Arrays.toString(strTestArr1));

        int answer = 0;

        for(int i=0; i<strTestArr1.length;i++){
            answer += Integer.parseInt(strTestArr1[i]);
        }

        String calResult = String.valueOf(answer);

        //Log.d(TAG, "calculation: "+ calResult);

        return calResult;*/

        return null;

    }


}
