package com.cos.calculator;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class MyEval {

    private static final String TAG = "MyEval";



    public static String calculation(String result){

        Log.d(TAG, "calculation : result "+result); //1*(6-2)+9

        StringTokenizer token = new StringTokenizer(result, "*/%+-()", true);

        //확인
//        while(token.hasMoreTokens())
//            Log.d(TAG, "calculation: "+token.nextToken());

        List<String> arrAllToken = new ArrayList<>();// 전체 토큰 싹다 (원본)

        // 저장 배열
        ArrayList<String> arrNumber = new ArrayList<>();
        // 연산자 stack
        Stack<String> stackOp = new Stack<>();
        // 계산 stack
        Stack<String> stackCal = new Stack<>();

        Stack<String> stackBraCal = new Stack<>();


        while(token.hasMoreTokens()){
            arrAllToken.add(token.nextToken());
        }
        Log.d(TAG, "calculation: arrAllToken "+arrAllToken.toString());


        for(int i=0; i<arrAllToken.size();i++) {

            setClassification(arrAllToken, arrNumber, i, stackOp);


            if(arrAllToken.get(i).equals("(")){

                int j = 0;
                calBracket(arrAllToken, arrNumber, j, stackOp);

            }

        }

        Log.d(TAG, "stackOp : "+stackOp.toString()); // [*, *, -, +]
        Log.d(TAG, "arrAllToken(arr) : "+arrAllToken.toString()); // [1, *, 6, *, (, 5, -, 2, ), +, 3.2] //처음 그대로
        Log.d(TAG, "arrNumber(arrSplit)111 : "+arrNumber.toString()); // [1, 6, (, 5, 2, ), 3.2]


        while(true){

            if(stackOp.isEmpty())
                break;

            for(int i=0; i<stackOp.size();i++){
                arrNumber.add(stackOp.get(stackOp.size()-1));
                //Log.d(TAG, "calculation 여기: "+stackOp.get(stackOp.size()-1));
                stackOp.pop();

            }

        }

        Log.d(TAG, "stackOp2222 : "+stackOp.toString());
        Log.d(TAG, "arrNumber(arrSplit)222 : "+arrNumber.toString()); //[1, 6, (, 5, 2, ), 3.2, +, -, *, *]

//        double ans111 = 0;
//        while(!arrNumber.isEmpty()){
//
//            if(arrNumber.size() == 1)
//                return Double.toString(ans111);
//
//            double exp1 = 0.0;
//            double exp2 = 0.0;
//
//            if(arrNumber.contains("(")){
//                exp1 = Double.parseDouble(arrNumber.get(0));
//                arrNumber.remove(0); //0번 (
//                arrNumber.remove(0);
//
//                exp2 = Double.parseDouble(arrNumber.get(0));
//                arrNumber.remove(0);
//
//
//            } else{
//                exp1 = Double.parseDouble(arrNumber.get(0));
//                arrNumber.remove(0);
//
//                exp2 = Double.parseDouble(arrNumber.get(0));
//                arrNumber.remove(0);
//            }
//
//
//            switch(arrNumber.get(arrNumber.size()-1)){
//
//                case "*":
//                    ans111 = exp1*exp2;
//                    break;
//                case "/":
//                    ans111 = exp1/exp2;
//                    break;
//                case "%":
//                    ans111 = exp1%exp2;
//                    break;
//                case "+":
//                    ans111 = exp1+exp2;
//                    break;
//                case "-":
//                    ans111 = exp1-exp2;
//                    break;
//
//                default:
//                    break;
//
//
//            }
//
//            arrNumber.remove(arrNumber.size()-1);
//
//            stackCal.push(Double.toString(ans111));
//
//            for(int i=0; i<arrNumber.size(); i++){
//                stackCal.push(arrNumber.get(i));
//            }
//
//            arrNumber.clear();
//
//            Log.d(TAG, "calculation 어떻게 됨: "+stackCal.toString());
//            Log.d(TAG, "calculation arrNumber11: "+arrNumber.toString());
//
//            for(int i=0; i<stackCal.size(); i++){
//                arrNumber.add(stackCal.get(i));
//            }
//
//            stackCal.clear();
//
//            Log.d(TAG, "calculation 어떻게 됨: "+stackCal.toString());
//            Log.d(TAG, "calculation arrNumber22: "+arrNumber.toString());
//
//
//        }


        moveCal(arrNumber, stackCal, stackOp);
        Log.d(TAG, "stackCal stackCal 이거111 : "+stackCal.toString());
        Log.d(TAG, "calculation 이거333: "+stackOp.toString());
        Log.d(TAG, "calculation arrSplit 이거222 : "+arrNumber.toString());

        double answer = 0.0;
        String ans = "";

        while(true){

            if(distinction(arrNumber)){//true 연산자

                double exp1 = Double.parseDouble(stackCal.get(0));
                stackCal.remove(0);
                double exp2 = Double.parseDouble(stackCal.get(0));
                stackCal.remove(0);

                Log.d(TAG, "calculation: "+exp1);
                Log.d(TAG, "calculation: "+exp2);

                //제일 뒤 값이
                switch (arrNumber.get(0)){
                    case "*":
                        answer = exp1*exp2;
                        arrNumber.remove(0);
                        break;
                    case "/":
                        answer = exp1/exp2;
                        arrNumber.remove(0);
                        break;
                    case "%":
                        answer = exp1%exp2;
                        arrNumber.remove(0);
                        break;
                    case "+":
                        answer = exp1+exp2;
                        arrNumber.remove(0);
                        break;
                    case "-":
                        answer = exp1-exp2;
                        arrNumber.remove(0);
                        break;
                }

                Log.d(TAG, "calculation 계산결과: "+answer);

                ans = Double.toString(answer);

                stackCal.push(ans);


            } else{//false 숫자 남아있음

//                Log.d(TAG, "calculation 아직 숫자있음 : ");
//
//                Log.d(TAG, "calculation: "+arrNumber.toString());//[]
//                Log.d(TAG, "calculation: "+stackCal.toString()); //30.0
//                Log.d(TAG, "calculation: "+ans);
//
//                if(stackCal.size() == 1)
//                    return ans;
//                else
//                    moveCal(arrNumber,stackCal);

            }



        }//while

        //return null;

    }//calculation


    //토큰 분류
    public static void setClassification(List<String> arr, ArrayList<String> arrSplit, int i, Stack<String> stackOp){
        Log.d(TAG, "setClassification: 실행");

        //사칙연산
        switch(arr.get(i)){
            case "+":
            case "-":
                if(arr.get(i-1).equals(")") ){
                    arrSplit.add(stackOp.get(stackOp.size() - 1));
                    stackOp.pop();
                }
                stackOp.push(arr.get(i));
                break;
            case "*":
            case "/":
            case "%":
            case "(":
            case ")":
                stackOp.push(arr.get(i));
                break;
            default:
                arrSplit.add(arr.get(i)); //기본 숫자
                break;

        }

    }

    //괄호 처리
    public static void calBracket(List<String> arr, ArrayList<String> arrSplit, int j, Stack<String> stackOp){

        if (stackOp.isEmpty())
            return;

        while(true){
            if(stackOp.get(j).equals("(")){
                stackOp.pop();
                break;

            } else {
                if(!stackOp.get(j).equals(")")){
                    arrSplit.add(stackOp.get(stackOp.size()-1));//마지막 값을 arrSplit에 넣기
                    stackOp.pop();

                } else if (stackOp.get(j).equals(")")){
                    stackOp.pop();

                }

            }

            j++;
            break;
        }

    }

    //계산할 숫자를 옮김
    public static void moveCal(ArrayList<String> arrSplit, Stack<String> stackCal, Stack<String> stackOp){
        Log.d(TAG, "moveCal: 나 실행됨");

        while(true) {
            switch (arrSplit.get(0)) {

                case "+":
                case "-":
                case "*":
                case "/":
                case "%":
                    break;
                default:
                    stackCal.push(arrSplit.get(0));
                    arrSplit.remove(0);
                    break;

            }
        }

    }

    public static boolean distinction(ArrayList<String> arrSplit){

        if(arrSplit.isEmpty()){
            return false;

        } else {
            switch (arrSplit.get(0)) {
                case "+":
                case "-":
                case "*":
                case "/":
                case "%":
                    return true;
                default:
                    return false;
            }
        }

    }



} //main
