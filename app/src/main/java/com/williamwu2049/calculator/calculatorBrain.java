package com.williamwu2049.calculator;

import java.text.DecimalFormat;

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
        DecimalFormat formatDouble = new DecimalFormat("#.###########");
        return Double.valueOf(formatDouble.format(firstInput + secondInput));
    }
    public double minus() {
        DecimalFormat formatDouble = new DecimalFormat("#.###########");
        return Double.valueOf(formatDouble.format(firstInput - secondInput));
    }
    public double multiply() {
        DecimalFormat formatDouble = new DecimalFormat("#.###########");
        return Double.valueOf(formatDouble.format(firstInput * secondInput));
    }
    public double divide() {
        //cast the output
        DecimalFormat formatDouble = new DecimalFormat("#.###########");
        return Double.valueOf(formatDouble.format(firstInput / secondInput));
    }
    public double remain() {
        //cast the output
        DecimalFormat formatDouble = new DecimalFormat("#.###########");
        return Double.valueOf(formatDouble.format(firstInput % secondInput));
    }
}
