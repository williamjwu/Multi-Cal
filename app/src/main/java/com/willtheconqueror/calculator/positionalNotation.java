package com.willtheconqueror.calculator;

/**
 * Created by William on 1/27/17.
 */

public class positionalNotation {
    private int dec;

    positionalNotation(int dec1) {
        dec = dec1;
    }

    public String toHexadecmal() {
        return Integer.toHexString(dec);
    }

    public String toBinary() {
        return Integer.toBinaryString(dec);
    }
}
