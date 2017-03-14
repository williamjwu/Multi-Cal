package com.williamwu2049.calculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {

    String displayValue = "0";
    private TextView display;
    String getOperator = "";
    double firstInput;
    double secondInput;
    double pendingValue;
    Button btnClear;
    Boolean ifUserInputting = true;
    Boolean signSwitchState = true; // false for positive sign, true for no sign

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //if an uncaught exception is thrown, the following line will stop the app from permanent crash
        //will automatically start up after few seconds
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

        //display
        display = (TextView)findViewById(R.id.textView);
        display.setText(displayValue);
        btnClear = (Button)findViewById(R.id.btnClear);

        Toolbar actionToolbar = (Toolbar)findViewById(R.id.toolbar_main);
        setSupportActionBar(actionToolbar);
        //disable title on action bar
        try {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        catch (NullPointerException ex) {
            Toast.makeText(MainActivity.this, "Fails to disable title", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.calculator_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //notifies users that they are currently in main activity
        if (item.getItemId() == R.id.action_main_activity) {
            Toast.makeText(MainActivity.this, "You are already in Main Function mode", Toast.LENGTH_SHORT).show();
        }

        //switch to second activity
        else if (item.getItemId() == R.id.action_second_activity) {
            Intent getNameScreenIntent = new Intent(this, SecondActivity.class);
            startActivity(getNameScreenIntent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }

        //switch to third activity
        else if (item.getItemId() == R.id.action_third_activity) {
            Intent getNameScreenIntent = new Intent(this, ThirdActivity.class);
            startActivity(getNameScreenIntent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }

        //switch to fourth activity
        else if (item.getItemId() == R.id.action_fourth_activity) {
            Intent getNameScreenIntent = new Intent(this, FourthActivity.class);
            startActivity(getNameScreenIntent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateScreen() {
        display.setText(displayValue);
    }

    private void clearInfo() {
        firstInput = 0;
        secondInput = 0;
        displayValue = "";
        getOperator = "";
    }

    private void storePreviousResult() {
        pendingValue = Double.parseDouble(displayValue);
    }

    private void restorePreviousResult() {
        firstInput = pendingValue;
    }

    private void switchInputtingPlace() {
        if (getOperator.equals("")) {
            firstInput = Double.parseDouble(displayValue);
        }
        else {
            secondInput = Double.parseDouble(displayValue);
        }
    }

    protected void onClickNumber(View v) {
        Button b = (Button) v;
        if (displayValue.equals("0")) {
            if (!b.getText().toString().equals(".")) {
                //prevent crash method
                //if not written, the first input will be "."
                //if delete, Calculator program can crash
                displayValue = "";
            }
        }
        if (!(displayValue.contains(".") && b.getText().toString().equals("."))) {
            //prevent crash method
            //prevent adding a second "." in the display
            displayValue += b.getText();
            ifUserInputting = true;
            switchClearOrDelete();
        }
        if (displayValue.length() > 11) {
            displayValue = displayValue.substring(0, 11);
            Toast.makeText(MainActivity.this, "You have reached the input limit", Toast.LENGTH_SHORT).show();
        }
        updateScreen();
        switchInputtingPlace();
    }

    private void switchClearOrDelete() {
        if (ifUserInputting) {
            btnClear.setText("DEL");
        }
        else {
            btnClear.setText("CLR");
        }
    }
    private void chooseOperation() {
        //only call choose Operation when there getOperator has value

        calculatorBrain calculator = new calculatorBrain(firstInput, secondInput);

        switch(getOperator) {
            case "+":
                displayValue = Double.toString(calculator.add());
                updateScreen();
                storePreviousResult();
                clearInfo();
                restorePreviousResult();
                break;
            case "-":
                displayValue = Double.toString(calculator.minus());
                updateScreen();
                storePreviousResult();
                clearInfo();
                restorePreviousResult();
                break;
            case "×":
                displayValue = Double.toString(calculator.multiply());
                updateScreen();
                storePreviousResult();
                clearInfo();
                restorePreviousResult();
                break;
            case "÷":
                if (secondInput == 0) {
                    displayValue = "Error";
                    updateScreen();
                    clearInfo();
                    ifUserInputting = false;
                    switchClearOrDelete();
                }
                else {
                    displayValue = Double.toString(calculator.divide());
                    updateScreen();
                    storePreviousResult();
                    clearInfo();
                    restorePreviousResult();
                }
                break;
            case "%":
                displayValue = Double.toString(calculator.remain());
                updateScreen();
                storePreviousResult();
                clearInfo();
                restorePreviousResult();
                break;
            default:
                displayValue = Double.toString(firstInput);
                updateScreen();
                clearInfo();
                break;
        }

    }


    protected void onClickOperator(View v) {
        Button b = (Button)v;
        if (!getOperator.equals("") && secondInput != 0) {
            chooseOperation();
        }
        getOperator = b.getText().toString();
        displayValue = b.getText().toString();
        updateScreen();
        displayValue = "0";
    }

    protected void onClickEqual(View v) {
        if (!getOperator.equals("")) {
            chooseOperation();
            displayValue = "0";
            ifUserInputting = false;
            switchClearOrDelete();
        }
    }

    protected void onClickClear(View v) {
        Button b = (Button)v;
        String label = b.getText().toString();
        switch (label) {
            case "CLR":
                firstInput = 0;
                secondInput = 0;
                displayValue = "0";
                updateScreen();
                getOperator = "";
                break;
            case "DEL":
                if (displayValue.length() > 2) {
                    displayValue = displayValue.substring(0, displayValue.length() - 1);
                    updateScreen();
                    switchInputtingPlace();
                }
                else if (displayValue.length() == 2) {
                    if (displayValue.startsWith("-")) {
                        displayValue = "0";
                        btnClear.setText("CLR");
                        updateScreen();
                        switchInputtingPlace();
                    }
                    else {
                        displayValue = displayValue.substring(0, displayValue.length() - 1);
                        updateScreen();
                        switchInputtingPlace();
                    }
                }
                else if (displayValue.length() == 1) {
                    displayValue = "0";
                    btnClear.setText("CLR");
                    updateScreen();
                    switchInputtingPlace();
                }
                break;
        }

    }

//    protected void onClickSquare(View v) {
//        //cast the output
//        DecimalFormat formatDouble = new DecimalFormat("#.#######");
//        try {
//            displayValue = Double.toString(Double.valueOf(formatDouble.format(firstInput * firstInput)));
//            storePreviousResult();
//            updateScreen();
//            clearInfo();
//            restorePreviousResult();
//            ifUserInputting = false;
//            switchClearOrDelete();
//        }
//        catch (IllegalArgumentException ex) {
//            displayValue = "Error";
//            updateScreen();
//            clearInfo();
//            ifUserInputting = false;
//            switchClearOrDelete();
//        }
//    }

    protected void onClickSqrt(View v) {
        //cast the output
        DecimalFormat formatDouble = new DecimalFormat("#.#######");
        displayValue = Double.toString(Double.valueOf(formatDouble.format(Math.sqrt(firstInput))));
        storePreviousResult();
        updateScreen();
        clearInfo();
        restorePreviousResult();
    }

    protected void onClickSignSwitch(View v) {
        if (ifUserInputting) {
            if (!displayValue.equals("") && !displayValue.equals("0")) {
                if (signSwitchState) {
                    signSwitchState = false; //set state to negative sign
                    displayValue = "-" + displayValue;
                    updateScreen();
                }
                else {
                    signSwitchState = true;
                    if (displayValue.startsWith("-")) {
                        displayValue = displayValue.substring(1, displayValue.length());
                        updateScreen();
                    }
                }
            }
        }
    }

}
