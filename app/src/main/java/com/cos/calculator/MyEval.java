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

        Log.d(TAG, "calculation : result "+result);

        StringTokenizer token = new StringTokenizer(result, "*/%+-()", true);
        //char[] operation = {'*', '/', '%', '+', '-', '(', ')'};


        List<String> arrAllToken = new ArrayList<>();// 전체 토큰 싹다 (원본)

        ArrayList<String> arrSplit = new ArrayList<>();
        Stack<String> stackOp = new Stack<>();
        Stack<String> stackCal = new Stack<>();


        //전체 나누기
        while(token.hasMoreTokens()){
            arrAllToken.add(token.nextToken());
        }
        Log.d(TAG, "calculation: arrAllToken "+arrAllToken.toString());


        //후위식으로 정리
        {
            for (int i = 0; i < arrAllToken.size(); i++) {
                setClassification(arrAllToken, arrSplit, i, stackOp);
            }

            //계산식이 끝나면 스택에 남은 연산자를 다 arrSplit으로
            while (stackOp.size() > 0) {
                arrSplit.add(stackOp.pop());
            }

            //Log.d(TAG, "calculation arrSplit222 : "+arrSplit.toString()); //후위식 정리 끝
            //Log.d(TAG, "calculation stackOp222 : "+stackOp.toString()); //stack에 남은 연산자 확인

        }

        //후위식 사칙연산
        for(int i=0; i<arrSplit.size(); i++){

            moveCal(arrSplit, i, stackCal);

        }

        //Log.d(TAG, "calculation stackCal : " + stackCal.toString());
        //Log.d(TAG, "calculation: "+stackCal.get(0));


        return stackCal.get(0);

    }//calculation


    static int opOrder(char op){//연산자 우선순위 처리

        switch (op){
            case '(':
                return 0;
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '%':
                return 3;
            default:
                return -1;
        }

    }


    //토큰 분류
    public static void setClassification(List<String> arr, ArrayList<String> arrSplit, int i, Stack<String> stackOp){
        Log.d(TAG, "setClassification: 실행");

        //사칙연산
        switch(arr.get(i)){
            case "+":
            case "-":
            case "*":
            case "/":
            case "%":
                if(stackOp.isEmpty()){
                    stackOp.push(arr.get(i));

                } else {
                    char prevOp = (stackOp.get(stackOp.size()-1)).charAt(0);
                    char currOp = (arr.get(i)).charAt(0);

                    if (opOrder(prevOp) < opOrder(currOp)) {
                        stackOp.push(arr.get(i));
                    } else if (opOrder(prevOp) >= opOrder(currOp)) {
                        arrSplit.add(stackOp.pop());
                        stackOp.push(arr.get(i));
                    }
                }
                break;
            case "(":
                stackOp.push(arr.get(i));
                break;
            case ")":
                while(!stackOp.get(stackOp.size()-1).equals("("))
                {
                    arrSplit.add(stackOp.pop());
                }

                //Log.d(TAG, "setClassification : stackOp "+ stackOp.toString());
                if(stackOp.get(stackOp.size()-1).equals("(")) // (까지 제거
                    stackOp.pop();

                break;
            default:
                arrSplit.add(arr.get(i));
                break;

        }

    }

    //계산할 식을 옮김
    public static void moveCal(ArrayList<String> arrSplit, int i,  Stack<String> stackCal){

        switch (arrSplit.get(i)) {

            case "+":
            case "-":
            case "*":
            case "/":
            case "%":
                letCalculation(arrSplit, i, stackCal);
                break;
            default:
                stackCal.push(arrSplit.get(i));
                break;

        }

    }

    public static String fmt(double d){

        if(d == (long) d)
            return String.format("%d", (long)d); //10진수
        else
            return String.format("%g", d); //문자열 형식

    }

    public static void letCalculation(ArrayList<String> arrSplit, int i, Stack<String> stackCal){

        double ans =0.0;
        String result = "";

        Double exp2 = Double.parseDouble(stackCal.pop());
        Double exp1 = Double.parseDouble(stackCal.pop());

        switch (arrSplit.get(i)){
            case "+":
                ans = exp1 + exp2;
                result = fmt(ans);
                stackCal.push(result);
                break;
            case "-":
                ans = exp1 - exp2;
                result = fmt(ans);
                stackCal.push(result);
                break;
            case "*":
                ans = exp1 * exp2;
                result = fmt(ans);
                //result = Double.toString(ans); //30.0
                stackCal.push(result);
                break;
            case "/":
                ans = exp1 / exp2;
                result = fmt(ans);
                stackCal.push(result);
                break;
            case "%":
                ans = exp1 % exp2;
                result = fmt(ans);
                stackCal.push(result);
                break;
            default:
                break;
        }

    }


} //main
