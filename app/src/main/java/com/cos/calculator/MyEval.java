package com.cos.calculator;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyEval {

    private static final String TAG = "MyEval";

    public static String calculation(String result){

        Log.d(TAG, "calculation : result "+result);

        // double 값도 처리해야함 => 일단 .을 비활성화해놓음
        // 괄호는 우짬

        String trimResult = result.trim(); //앞뒤 공백제거
        String[] arrResult = trimResult.split(" "); //
        List<String> arrayList = new ArrayList<>(Arrays.asList(arrResult));

        Log.d(TAG, "calculation: "+ arrayList);

        String calResult = "";

        while(arrayList.size()>1){

            if(arrayList.contains("*"))
            { //곱셈 후 배열 바꾸기
                int indexOfMultiple = arrayList.indexOf("*");

//                Log.d(TAG, "calculation: " + indexOfMultiple); //[1, +, 2, *, 3] 일때 3번 째
//                Log.d(TAG, "calculation: " + arrayList.get(indexOfMultiple - 1)); // 2
//                Log.d(TAG, "calculation: " + arrayList.get(indexOfMultiple + 1)); // 3

                //얘네는 계속 같이 반복됨 위에서 받아 -> 인덱스번호로 찾지마 문제가 생긴다... 너?
                //찾는 함수01
                String exp1 = arrayList.get(indexOfMultiple - 1);
                String exp2 = arrayList.get(indexOfMultiple + 1);

                //파싱
                int integerExp1 = Integer.parseInt(exp1);
                int integerExp2 = Integer.parseInt(exp2);

                //연산함수02
                int answer1 = integerExp1 * integerExp2;
                String strAnswer1 = Integer.toString(answer1);

                //갱신함수03 로 따로 나눠
                arrayList.set(indexOfMultiple - 1, strAnswer1);
                arrayList.remove(indexOfMultiple + 1);
                arrayList.remove(indexOfMultiple);

                //Log.d(TAG, "calculation: " + arrayList.toString()); // [1, +, 6]

                calResult = arrayList.get(0);
            } else if(arrayList.contains("/"))
            { //나눗셈
                int indexOfDivision = arrayList.indexOf("/");

//                Log.d(TAG, "calculation: " + indexOfDivision);
//                Log.d(TAG, "calculation: " + arrayList.get(indexOfDivision - 1));
//                Log.d(TAG, "calculation: " + arrayList.get(indexOfDivision + 1));

                String exp1 = arrayList.get(indexOfDivision - 1);
                String exp2 = arrayList.get(indexOfDivision + 1);

                int integerExp1 = Integer.parseInt(exp1);
                int integerExp2 = Integer.parseInt(exp2);

                int answer1 = integerExp1 / integerExp2;
                String strAnswer1 = Integer.toString(answer1);

                arrayList.set(indexOfDivision - 1, strAnswer1);
                arrayList.remove(indexOfDivision + 1);
                arrayList.remove(indexOfDivision);

                //Log.d(TAG, "calculation: " + arrayList.toString());

                calResult = arrayList.get(0);
            } else if(arrayList.contains("%"))
            { //나머지 연산
                int indexOfModular = arrayList.indexOf("%");

//                Log.d(TAG, "calculation: " + indexOfModular);
//                Log.d(TAG, "calculation: " + arrayList.get(indexOfModular - 1));
//                Log.d(TAG, "calculation: " + arrayList.get(indexOfModular + 1));

                String exp1 = arrayList.get(indexOfModular - 1);
                String exp2 = arrayList.get(indexOfModular + 1);

                int integerExp1 = Integer.parseInt(exp1);
                int integerExp2 = Integer.parseInt(exp2);

                int answer1 = integerExp1 % integerExp2;
                String strAnswer1 = Integer.toString(answer1);

                arrayList.set(indexOfModular - 1, strAnswer1);
                arrayList.remove(indexOfModular + 1);
                arrayList.remove(indexOfModular);

                //Log.d(TAG, "calculation: " + arrayList.toString());

                calResult = arrayList.get(0);
            } else if(arrayList.contains("+"))
            { //덧셈
                int indexOfPlus = arrayList.indexOf("+");

//                Log.d(TAG, "calculation: " + indexOfPlus);
//                Log.d(TAG, "calculation: " + arrayList.get(indexOfPlus - 1));
//                Log.d(TAG, "calculation: " + arrayList.get(indexOfPlus + 1));

                String exp1 = arrayList.get(indexOfPlus - 1);
                String exp2 = arrayList.get(indexOfPlus + 1);

                int integerExp1 = Integer.parseInt(exp1);
                int integerExp2 = Integer.parseInt(exp2);

                int answer1 = integerExp1 + integerExp2;
                String strAnswer1 = Integer.toString(answer1);

                arrayList.set(indexOfPlus - 1, strAnswer1);
                arrayList.remove(indexOfPlus + 1);
                arrayList.remove(indexOfPlus);

                //Log.d(TAG, "calculation: " + arrayList.toString()); //[7]

                calResult = arrayList.get(0);
            } else if(arrayList.contains("-"))
            { //뺄셈
                int indexOfMinus = arrayList.indexOf("-");

//                Log.d(TAG, "calculation: " + indexOfMinus);
//                Log.d(TAG, "calculation: " + arrayList.get(indexOfMinus - 1));
//                Log.d(TAG, "calculation: " + arrayList.get(indexOfMinus + 1));

                String exp1 = arrayList.get(indexOfMinus - 1);
                String exp2 = arrayList.get(indexOfMinus + 1);

                int integerExp1 = Integer.parseInt(exp1);
                int integerExp2 = Integer.parseInt(exp2);

                int answer1 = integerExp1 - integerExp2;
                String strAnswer1 = Integer.toString(answer1);

                arrayList.set(indexOfMinus - 1, strAnswer1);
                arrayList.remove(indexOfMinus + 1);
                arrayList.remove(indexOfMinus);

                //Log.d(TAG, "calculation: " + arrayList.toString());//[40]

                calResult = arrayList.get(0);

            }

            //Log.d(TAG, "calculation: 여기1" + calResult);

        }// while문 끝

        //Log.d(TAG, "calculation: 여기여기 " + calResult);
        return calResult;

        // 연산자 한개만 처리됨
        /*String answer = "";
        String exp1 = arrResult[0];
        int integerExp1 = Integer.parseInt(exp1);

        String op = arrResult[1];

        String exp2 = arrResult[2];
        int integerExp2 = Integer.parseInt(exp2);

        if(op.equals("*")){
            answer = Integer.toString(integerExp1 * integerExp2);
            return answer;

        } else if(op.equals("/")){
            answer = Integer.toString(integerExp1 / integerExp2);
            return answer;

        } else if(op.equals("+")){
            answer = Integer.toString(integerExp1 + integerExp2);
            return answer;

        } else if(op.equals("-")){
            answer = Integer.toString(integerExp1 - integerExp2);
            return answer;

        } else if(op.equals("%")){
            answer = Integer.toString(integerExp1 % integerExp2);
            return answer;
        }*/


        /*//홀수자리에 연산자가 들어온다
        for(int i = 0; i<arrResult.length; i++){
            String answer = "";
            //String exp1 = arrResult[i];
            //int integerExp1 = Integer.parseInt(exp1);

            for(int j = i+2; j<arrResult.length; j++){

                String exp2 = arrResult[j];
                int integerExp2 = Integer.parseInt(exp2);

                if(i%2 == 0){
                    String exp1 = arrResult[i];
                    int integerExp1 = Integer.parseInt(exp1);

                } else if(i%2 ==1){
                    String operator = arrResult[i];

                    if(operator.equals("*")){
                        answer = Integer.toString(integerExp1 * integerExp2);

                        Log.d(TAG, "calculation: answer "+ answer);
                        return answer;
                    }

                }


            }

        }//for*/

        /*//#01.
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

        }*/


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


        //return null;

    }//calculation


    /*private String plus(String exp1, String exp2){
        int integerExp1 = Integer.parseInt(exp1);
        int integerExp2 = Integer.parseInt(exp2);

        int calculation = integerExp1 + integerExp2;
        String result = Integer.toString(calculation);

        return result;
    }

    private String minus(String exp1, String exp2){
        int integerExp1 = Integer.parseInt(exp1);
        int integerExp2 = Integer.parseInt(exp2);

        int calculation = integerExp1 - integerExp2;
        String result = Integer.toString(calculation);

        return result;
    }

    private String multiple(String exp1, String exp2){

        int integerExp1 = Integer.parseInt(exp1);
        int integerExp2 = Integer.parseInt(exp2);

        int calculation = integerExp1 * integerExp2;
        String result = Integer.toString(calculation);

        return result;

    }

    private String division(String exp1, String exp2){
        int integerExp1 = Integer.parseInt(exp1);
        int integerExp2 = Integer.parseInt(exp2);

        int calculation = integerExp1 / integerExp2;
        String result = Integer.toString(calculation);

        return result;
    }

    private String modular(String exp1, String exp2){

        int integerExp1 = Integer.parseInt(exp1);
        int integerExp2 = Integer.parseInt(exp2);

        int calculation = integerExp1 % integerExp2;
        String result = Integer.toString(calculation);

        return result;
    }*/




}
