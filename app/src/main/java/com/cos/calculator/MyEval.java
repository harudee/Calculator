package com.cos.calculator;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class MyEval {

    private static final String TAG = "MyEval";

    public static String calculation(String result){

        Log.d(TAG, "calculation : result "+result);
        // 3+4*5 이런건 어짬

        if(result.contains("*")){
            String[] arrMultiple = result.split("\\*");

            int ansMultiple=1;
            ArrayList<Integer> arrayMul = new ArrayList<>();
            for(String mul : arrMultiple){
                arrayMul.add(Integer.parseInt(mul));
            }

            for(int i=0; i<arrayMul.size(); i++){
                for(int j = i+1; j<arrayMul.size(); j++){
                    ansMultiple = arrayMul.get(i) * arrayMul.get(j);
                }
            }

            //Log.d(TAG, "calculation: ansMultiple "+ansMultiple);

            return String.valueOf(ansMultiple);


        } else if(result.contains("/")){
            String[] arrDivision = result.split("\\/");

            int ansDivision=1;
            ArrayList<Integer> arrayDiv = new ArrayList<>();
            for(String division : arrDivision){
                arrayDiv.add(Integer.parseInt(division));
            }

            for(int i=0; i<arrayDiv.size(); i++){
                for(int j = i+1; j<arrayDiv.size(); j++){
                    ansDivision = arrayDiv.get(i) / arrayDiv.get(j);
                }
            }

            //Log.d(TAG, "calculation: ansDivision "+ ansDivision);
            return String.valueOf(ansDivision);


        } else if(result.contains("-")){
            String[] arrMinus1 = result.split("-");

            int ansMinus=0;
            ArrayList<Integer> alMinus = new ArrayList<>();
            for(String minus : arrMinus1){
                alMinus.add(Integer.parseInt(minus));
            }

            for(int i=0; i<alMinus.size(); i++){
                for(int j = i+1; j<alMinus.size(); j++){
                    ansMinus = alMinus.get(i) - alMinus.get(j);
                }
            }

            //Log.d(TAG, "calculation: " + ansMinus);
            return String.valueOf(ansMinus);


        } else if(result.contains("+")){
            String[] arrPlus = result.split("\\+");

            int ansPlus=0;
            ArrayList<Integer> alPlus = new ArrayList<>();
            for(String plus : arrPlus){
                alPlus.add(Integer.parseInt(plus));
            }

            for(int i=0; i<alPlus.size(); i++){
                for(int j = i+1; j<alPlus.size(); j++){
                    ansPlus = alPlus.get(i) + alPlus.get(j);
                }
            }

            //Log.d(TAG, "calculation: " + ansPlus);
            return String.valueOf(ansPlus);


        } else if(result.contains("%")){
            String[] arrModular = result.split("%");

            int ansModular=0;
            ArrayList<Integer> alModular = new ArrayList<>();
            for(String plus : arrModular){
                alModular.add(Integer.parseInt(plus));
            }

            for(int i=0; i<alModular.size(); i++){
                for(int j = i+1; j<alModular.size(); j++){
                    ansModular = alModular.get(i) % alModular.get(j);
                }
            }

            //Log.d(TAG, "calculation: " + ansModular);
            return String.valueOf(ansModular);

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
