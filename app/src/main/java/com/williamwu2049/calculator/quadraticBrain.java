package com.williamwu2049.calculator;

import java.text.DecimalFormat;

/**
 * Created by William on 1/1/17.
 */

public class quadraticBrain {
    private double a;
    private double b;
    private double c;

    public quadraticBrain(double a1, double b1, double c1) {
        a = a1;
        b = b1;
        c = c1;
    }

    public double positiveSum() {
        DecimalFormat formatDouble = new DecimalFormat("#.######");
        double outputPos = ((-b)+(Math.sqrt(b*b-4*a*c)))/(2*a);
        return Double.valueOf(formatDouble.format(outputPos));
    }
    public double negativeSum() {
        DecimalFormat formatDouble = new DecimalFormat("#.######");
        double outputNeg = ((-b)-(Math.sqrt(b*b-4*a*c)))/(2*a);
        return Double.valueOf(formatDouble.format(outputNeg));
    }
}
