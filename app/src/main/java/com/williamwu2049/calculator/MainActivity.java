package com.williamwu2049.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;

<<<<<<< HEAD
=======
import static android.R.attr.label;
>>>>>>> e1ac682c3bbc0bb0ef36b51aef819645d76e89da

public class MainActivity extends AppCompatActivity {

    private TextView display;
    String displayValue = "0";
    String getOperator = "";
    double firstInput = 0;
    double secondInput = 0;
    double pendingValue;
    Button btnClear;
    Boolean ifUserInputting = true;
    Boolean switchInputTarget = true; //true for first input, false for second input

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
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String btnLabel = btnClear.getText().toString();

                switch (btnLabel) {
                    case "CLR":
                        clearInfo();
                        updateScreen();
                        switchInputTarget = true;
                        break;
                    case "DEL":
                        if (displayValue.length() > 2) {
                            displayValue = displayValue.substring(0, displayValue.length() - 1);
                            updateScreen();
                        }
                        else if (displayValue.length() == 2) {
                            if (displayValue.startsWith("-")) {
                                displayValue = "0";
                                btnClear.setText("CLR");
                                updateScreen();
                            }
                            else {
                                displayValue = displayValue.substring(0, displayValue.length() - 1);
                                updateScreen();
                            }
                        }
                        else if (displayValue.length() == 1) {
                            displayValue = "0";
                            btnClear.setText("CLR");
                            updateScreen();
                        }
                        break;
                }
            }
        });
        btnClear.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                clearInfo();
                updateScreen();
                switchInputTarget = true;
                return false;
            }
        });
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
            Toast.makeText(MainActivity.this, "You are already in Main Function", Toast.LENGTH_SHORT).show();
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
        displayValue = "0";
        getOperator = "";
    }

    private void storePreviousResult() {
        pendingValue = Double.parseDouble(displayValue);
    }

    private void restorePreviousResult() {
        firstInput = pendingValue;
    }

    private void handleSwitchInputTarget() {
        if (switchInputTarget) {
            firstInput = Double.parseDouble(displayValue);
        }
        else {
            secondInput = Double.parseDouble(displayValue);
        }
    }
    private String formatTailingZero(String s) {
        s = s.indexOf(".") < 0 ? s : s.replaceAll("0*$", "").replaceAll("\\.$", "");
        return s;
    }

    public void onClickNumber(View v) {
        Button b = (Button) v;
        if (displayValue.length() <= 18) {
            if (displayValue.equals("0")) {
                if (!b.getText().toString().equals(".")) {
                /*prevent crash method
                 *if not written, the first input will be "."
                 *if delete, Calculator program can crash*/
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
        }
        else {
            Toast.makeText(MainActivity.this, "You have reached the input limit", Toast.LENGTH_SHORT).show();
        }
        handleSwitchInputTarget();
        updateScreen();
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
        //only call choose Operation when getOperator has value
        calculatorBrain calculator = new calculatorBrain(firstInput, secondInput);

        switch(getOperator) {
            case "+":
                displayValue = formatTailingZero(Double.toString(calculator.add()));
                updateScreen();
                storePreviousResult();
                clearInfo();
                restorePreviousResult();
                break;
            case "–":
                displayValue = formatTailingZero(Double.toString(calculator.minus()));
                updateScreen();
                storePreviousResult();
                clearInfo();
                restorePreviousResult();
                break;
            case "×":
                displayValue = formatTailingZero(Double.toString(calculator.multiply()));
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
                    displayValue = formatTailingZero(Double.toString(calculator.divide()));
                    updateScreen();
                    storePreviousResult();
                    clearInfo();
                    restorePreviousResult();
                }
                break;
            case "%":
                displayValue = formatTailingZero(Double.toString(calculator.remain()));
                updateScreen();
                storePreviousResult();
                clearInfo();
                restorePreviousResult();
                break;
            default:
                break;

        }

    }


    public void onClickOperator(View v) {
        Button b = (Button)v;

        if (secondInput != 0 && !getOperator.equals("")) {
            chooseOperation();
            firstInput = pendingValue;
            getOperator = b.getText().toString();
            displayValue = "Ans " + b.getText().toString();
            updateScreen();
            displayValue = "";
            switchInputTarget = false;
        }
        else if (secondInput == 0){
            if (getOperator.equals("")) {
                getOperator = b.getText().toString();
                displayValue = b.getText().toString();
                updateScreen();
                displayValue = "";
                switchInputTarget = false;
            }
            else if (!getOperator.equals("")) {
                getOperator = b.getText().toString();
                displayValue = b.getText().toString();
                updateScreen();
                displayValue = "";
                switchInputTarget = false;
            }
        }
    }

    public void onClickEqual(View v) {
        if (!getOperator.equals("")) {
            chooseOperation();
            switchInputTarget = true;
            ifUserInputting = false;
            switchClearOrDelete();
        }
    }

//    public void onClickSquare(View v) {
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

    public void onClickSqrt(View v) {
        //cast the output
        DecimalFormat formatDouble = new DecimalFormat("#.###########");
        displayValue = formatTailingZero(formatDouble.format(Math.sqrt(firstInput)));
        storePreviousResult();
        updateScreen();
        clearInfo();
        restorePreviousResult();
        switchInputTarget = true;
        ifUserInputting = false;
        switchClearOrDelete();
    }

    public void onClickSignSwitch(View v) {
        if (ifUserInputting) {
            if (!displayValue.equals("") && !displayValue.equals("0")) {
                if (displayValue.startsWith("-")) {
                    displayValue = displayValue.substring(1, displayValue.length());
                    updateScreen();
                    handleSwitchInputTarget();
                }
                else {
                    displayValue = "-" + displayValue;
                    updateScreen();
                    handleSwitchInputTarget();
                }
            }
        }
    }
}
