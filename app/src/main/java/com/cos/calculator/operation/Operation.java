package com.cos.calculator.operation;

public class Operation  {

    private Strategy strategy;

    public Operation(Strategy strategy){
        this.strategy = strategy;

    }

    public Strategy getStrategy() {
        return strategy;
    }

    public double executeStrategy(double num1, double num2){
        return strategy.doOperation(num1, num2);
    }


    public static class Add implements Strategy{

        @Override
        public double doOperation(double num1, double num2) {
            return num1 + num2;
        }

    }

    public static class Sub implements Strategy{ // Subtrack

        @Override
        public double doOperation(double num1, double num2) {
            return num1 - num2;
        }

    }

    public static class Multiple implements Strategy{

        @Override
        public double doOperation(double num1, double num2) {
            return num1 * num2;
        }

    }

    public static class Division implements Strategy{
        @Override
        public double doOperation(double num1, double num2) {
            return num1 / num2;
        }
    }

    public static class Modular implements Strategy{
        @Override
        public double doOperation(double num1, double num2) {
            return num1 % num2;
        }
    }


    

}
