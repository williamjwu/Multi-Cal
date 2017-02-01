package com.willtheconqueror.calculator;

/**
 * Created by William on 12/21/16.
 */
public class calculatorBrain {
    private double firstInput;
    private double secondInput;

    //constructor

    public calculatorBrain(double x1, double x2) {
        firstInput = x1;
        secondInput = x2;
    }

    //mutator
    public double add() {
        return Math.round(firstInput + secondInput);
    }
    public double minus() {
        return firstInput - secondInput;
    }
    public double multiply() {
        return firstInput * secondInput;
    }
    public double divide() {
        return firstInput / secondInput;
    }
    public double remain() {
        return firstInput % secondInput;
    }
}
