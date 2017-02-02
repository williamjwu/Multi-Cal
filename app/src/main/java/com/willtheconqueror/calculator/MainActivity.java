package com.willtheconqueror.calculator;

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


public class MainActivity extends AppCompatActivity {

    String displayValue = "0";
    private TextView display;
    String getOperator = "";
    double firstInput;
    double secondInput;
    double pendingValue;
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //display
        display = (TextView)findViewById(R.id.textView);
        display.setText(displayValue);
        btnDelete = (Button)findViewById(R.id.btnDelete);

        Toolbar actionToolbar = (Toolbar)findViewById(R.id.toolbar_main);
        setSupportActionBar(actionToolbar);
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
        }

        //switch to third activity
        else if (item.getItemId() == R.id.action_third_activity) {
            Intent getNameScreenIntent = new Intent(this, ThirdActivity.class);
            startActivity(getNameScreenIntent);
        }

        //switch to fourth activity
        else if (item.getItemId() == R.id.action_fourth_activity) {
            Intent getNameScreenIntent = new Intent(this, FourthActivity.class);
            startActivity(getNameScreenIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateScreen() {
        display.setText(displayValue);
    }

    private void clearInfo() {
        firstInput = 0.;
        secondInput = 0.;
        displayValue = "";
        getOperator = "";
    }

    private void storePreviousResult() {
        pendingValue = Double.parseDouble(displayValue);
    }

    private void restorePreviousResult() {
        firstInput = pendingValue;
    }

    private void checkIfInput() {
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
        }

        if (displayValue.length() > 11) {
            displayValue = displayValue.substring(0, 11);
            Toast.makeText(MainActivity.this, "You have reached the input limit", Toast.LENGTH_SHORT).show();
        }
        updateScreen();
        checkIfInput();
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
                if (secondInput == 0.) {
                    displayValue = "Error";
                    updateScreen();
                    clearInfo();
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
        if (!getOperator.equals("") && secondInput != 0.) {
            chooseOperation();
        }
        getOperator = b.getText().toString();
        displayValue = "";
    }

    protected void onClickEqual(View v) {
        if (!getOperator.equals("")) {
            chooseOperation();
        }
    }

    protected void onClickClear(View v) {
        firstInput = 0.;
        secondInput = 0.;
        displayValue = "0";
        getOperator = "";
        updateScreen();
    }

    protected void onClickDelete(View v) {
        if (displayValue.length() > 1) {
            displayValue = displayValue.substring(0, displayValue.length() - 1);
            updateScreen();
            checkIfInput();
        }
        else if (displayValue.length() == 1) {
            displayValue = "0";
            updateScreen();
            checkIfInput();
        }

    }

    protected void onClickSqrt(View v) {

            displayValue = Double.toString(Math.sqrt(firstInput));
            storePreviousResult();
            updateScreen();
            clearInfo();
            restorePreviousResult();

    }

}
