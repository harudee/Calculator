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
        char operation[] = {'*', '/', '%', '+', '-', '(', ')'};


        List<String> arrAllToken = new ArrayList<>();// 전체 토큰 싹다 (원본)


        ArrayList<String> arrNumber = new ArrayList<>();
        Stack<String> stackOp = new Stack<>();
        Stack<String> stackCal = new Stack<>();


        String op = "";


        //전체 나누기
        while(token.hasMoreTokens()){
            arrAllToken.add(token.nextToken());
        }
        Log.d(TAG, "calculation: arrAllToken "+arrAllToken.toString());


//        for(int i=0; i<arrAllToken.size(); i++){
//
//            switch (arrAllToken.get(i)){
//                case "*":
//                case "/":
//                case "%":
//                case "+":
//                case "-":
//                    op = arrAllToken.get(i);
//                    break;
//                case "(":
//                    while(arrAllToken.get(i).equals(")")){
//                        stackCal.push(arrAllToken.get(i));
//                    }
//                    break;
//                default:
//                    stackCal.push(arrAllToken.get(i));
//                    break;
//
//
//            }
//
//            if(!op.equals("")){
//
//            }
//
//
//        }

        for(int i=0; i<arrAllToken.size();i++) {

            setClassification(arrAllToken, arrNumber, i, stackOp);//여기서 이미 정리 완

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

//        Log.d(TAG, "stackOp2222 : "+stackOp.toString());
        Log.d(TAG, "arrNumber(arrSplit)222 : "+arrNumber.toString()); //[1, 6, (, 5, 2, ), 3.2, +, -, *,* ]


         //여기부터 calculation 다시
        moveCal(arrNumber, stackCal);
        Log.d(TAG, "stackCal stackCal 이거111 : "+stackCal.toString());
        Log.d(TAG, "calculation 이거333: "+stackOp.toString());
        Log.d(TAG, "calculation arrSplit 이거222 : "+arrNumber.toString());

        double answer = 0.0;
        String ans = "";

        while(true){

            if(distinction(arrNumber)){//true 연산자

                double exp1 = Double.parseDouble(stackCal.pop());
                //stackCal.remove(0);
                double exp2 = Double.parseDouble(stackCal.pop());
                //stackCal.remove(0);

                Log.d(TAG, "calculation: "+exp1);
                Log.d(TAG, "calculation: "+exp2);

                //
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


    static int opOrder(char op){//연산자 우선순위 처리
        switch (op){
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '%':
                return 3;
            case '(':
            case ')':
                return 4;
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
                if(arr.get(i-1).equals(")") ){
                    arrSplit.add(stackOp.get(stackOp.size() - 1));
                    stackOp.pop();
                }
                stackOp.push(arr.get(i));
                break;
            case "*":
            case "/":
            case "%":
                stackOp.push(arr.get(i));
                break;
            case ")":
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
    public static void moveCal(ArrayList<String> arrSplit, Stack<String> stackCal){
        Log.d(TAG, "moveCal: 나 실행됨");

        //while(true) {
            switch (arrSplit.get(0)) {

                case "+":
                case "-":
                case "*":
                case "/":
                case "%":
//                case "(":
//                    while(arrSplit.get(0).equals(")")){
//                        stackCal.push(arrSplit.get(0));
//                        arrSplit.remove(0);
//                    }
                    break;
                default:
                    stackCal.push(arrSplit.get(0));
                    arrSplit.remove(0);
                    break;

            }
        //}

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
