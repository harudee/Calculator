package com.cos.calculator.operation;

import android.util.Log;

import com.cos.calculator.operation.Operation;
import com.cos.calculator.operation.Strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class MyEval {

    private static final String TAG = "MyEval";

    static Operation add = new Operation(new Operation.Add());
    static Operation sub = new Operation(new Operation.Sub());
    static Operation mul = new Operation(new Operation.Multiple());
    static Operation modular = new Operation(new Operation.Modular());
    static Operation div = new Operation(new Operation.Division());

    public static String calculation(String result){

        Log.d(TAG, "calculation : result "+result);

        StringTokenizer token = new StringTokenizer(result, "*/%+-()^√", true);
        //char[] operation = {'*', '/', '%', '+', '-', '(', ')'};

        List<String> arrAllToken = new ArrayList<>();

        ArrayList<String> arrSplit = new ArrayList<>();
        Stack<String> stackOp = new Stack<>();
        Stack<String> stackCal = new Stack<>();

        //전체 나누기
        while(token.hasMoreTokens()){
            arrAllToken.add(token.nextToken());
        }
        Log.d(TAG, "calculation: arrAllToken111 "+arrAllToken.toString());

        if(arrAllToken.contains("π") || arrAllToken.contains("e")){
            for(int i = 0; i< arrAllToken.size(); i++){
                switch (arrAllToken.get(i)){
                    case "π":
                        arrAllToken.set(i, Double.toString(Math.PI));
                        break;
                    case "e":
                        arrAllToken.set(i, Double.toString(Math.E));
                        break;
                }
            }

            Log.d(TAG, "calculation: arrAllToken222 치환 "+arrAllToken.toString());
        }


        //후위식으로 정리
        {
            for (int i = 0; i < arrAllToken.size(); i++) {

                setClassification(arrAllToken, arrSplit, i, stackOp);
            }

            //계산식이 끝나면 스택에 남은 연산자를 다 arrSplit으로
            while (stackOp.size() > 0) {
                arrSplit.add(stackOp.pop());
            }

        }

        //후위식 사칙연산
        for(int i=0; i<arrSplit.size(); i++){

            moveCal(arrSplit, i, stackCal);

        }

        return stackCal.get(0);

    }//calculation


    //연산자 우선순위가 크면 먼저 계산식으로 이동
    // ')'가 나오면 ( 까지 저장된 모든 연산자가 계산식으로 이동
    static int opOrder(String op){
        switch (op){
            case "(":
                return 0;
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            case "%":
                return 3;
            case "sin":
            case "cos":
            case "tan":
            case "log":
            case "abs":
            case "^":
            case "√":
            case "ln":
                return 4;
            default:
                return -1;
        }

    }


    //postfix 만들기
    public static void setClassification(List<String> arr, ArrayList<String> arrSplit, int i, Stack<String> stackOp){
        Log.d(TAG, "setClassification: 실행");

        //사칙연산
        switch(arr.get(i)){

            case "-":
                if(arr.get(i-1).equals("(") ){ //음수 부호 처리

                    arrSplit.add(arr.get(i)+arr.get(i+1));
                    arr.remove(i+1);

                    break;
                }

            case "+":
            case "*":
            case "/":
            case "%":
            case "sin":
            case "cos":
            case "tan":
            case "log":
            case "abs":
            case "^":
            case "√":
            case "ln":
                if(stackOp.isEmpty()){
                    stackOp.push(arr.get(i));

                } else {
                    String prevOp = (stackOp.get(stackOp.size()-1));
                    String currOp = (arr.get(i));

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

                if(stackOp.get(stackOp.size()-1).equals("(")) // (까지 제거
                    stackOp.pop();

                break;
            default:
                arrSplit.add(arr.get(i));
                break;

        }

    }

    //계산식으로 각각 이동
    public static void moveCal(ArrayList<String> arrSplit, int i,  Stack<String> stackCal){

        Log.d(TAG, "moveCal: arrSplit "+arrSplit.toString());
        Log.d(TAG, "moveCal: stackCal "+stackCal.toString());

        switch (arrSplit.get(i)) {

            case "-":
            case "+":
            case "*":
            case "/":
            case "%":
            case "^":
                goCalculation(arrSplit, i, stackCal);
                break;
            case "sin":
            case "cos":
            case "tan":
            case "abs":
            case "√":
            case "log":
            case "ln":
                sciCalculation(arrSplit,i,stackCal);
                break;
            default:
                stackCal.push(arrSplit.get(i));
                break;

        }

    }

    public static String fmt(double d){// 소수점이하 포맷변경

        if(d == (long) d) {
            return String.format("%d", (long) d); //10진수
        }
        else {
            return String.format("%g", d); //문자열 형식
        }

    }


    public static void goCalculation(ArrayList<String> arrSplit, int i, Stack<String> stackCal){

        double ans =0.0;
        String result = "";

        Log.d(TAG, "goCalculation: 계산직전 arrSplit " + arrSplit.toString());
        Log.d(TAG, "goCalculation: 계산직전 stackCal "+stackCal.toString());

        Double exp2 = Double.parseDouble(stackCal.pop());
        Double exp1 = Double.parseDouble(stackCal.pop());


        switch (arrSplit.get(i)){
            case "+":
                ans = add.executeStrategy(exp1, exp2);
                result = fmt(ans);
                stackCal.push(result);
                break;
            case "-":
                ans = sub.executeStrategy(exp1, exp2);
                result = fmt(ans);
                stackCal.push(result);
                break;
            case "*":
                ans = mul.executeStrategy(exp1, exp2);
                result = fmt(ans);
                stackCal.push(result);
                break;
            case "/":
                ans = div.executeStrategy(exp1, exp2);
                result = fmt(ans);
                stackCal.push(result);
                break;
            case "%":
                ans = modular.executeStrategy(exp1, exp2);
                result = fmt(ans);
                stackCal.push(result);
                break;
            case "^":
                ans = Math.pow(exp1,exp2);
                result = fmt(ans);
                stackCal.push(result);
                break;

            default:
                break;
        }

    }

    public static void sciCalculation(ArrayList<String> arrSplit, int i, Stack<String> stackCal){

        double ans =0.0;
        double ans2 =0.0;
        String result = "";

        Log.d(TAG, "goCalculation: 공학계산 arrSplit " + arrSplit.toString());
        Log.d(TAG, "goCalculation: 공학계산 stackCal "+stackCal.toString());

        Double exp2 = Double.parseDouble(stackCal.pop());

        switch (arrSplit.get(i)){
            case "√":
                ans= Math.sqrt(exp2);
                result = fmt(ans);
                stackCal.push(result);
                break;
            case "sin":
                ans = Math.sin(Math.toRadians(exp2));
                result = fmt(ans);
                //result = Double.toString(ans);
                stackCal.push(result);
                break;
            case "cos":
                ans = Math.cos(Math.toRadians(exp2));
                result = fmt(ans);
                //result = Double.toString(ans);
                stackCal.push(result);
                break;
            case "tan":
                ans = Math.tan(Math.toRadians(exp2));
                result = fmt(ans);
                //result = Double.toString(ans);
                stackCal.push(result);
                break;
            case "log":
                ans = Math.log10(exp2);
                result = fmt(ans);
                //result = Double.toString(ans);
                stackCal.push(result);
                break;
            case "abs":
                ans = Math.abs(exp2);
                result = fmt(ans);
                //result = Double.toString(ans);
                stackCal.push(result);
                break;
            case "ln":
                ans = Math.log(exp2);
                result = fmt(ans);
                //result = Double.toString(ans);
                stackCal.push(result);
                break;
            default:
                break;

        }

    }



} //main
