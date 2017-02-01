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

public class ThirdActivity extends AppCompatActivity {

    Button btnEnter;
    private TextView display3;
    String displayValue = "";
    int decimal;
    String buttonLabel = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        display3 = (TextView)findViewById(R.id.textView3);
        display3.setText(displayValue);
        btnEnter = (Button)findViewById(R.id.btnEnter);

        Toolbar actionToolbar = (Toolbar)findViewById(R.id.toolbar_third);
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
        //switch to main activity
        if (item.getItemId() == R.id.action_main_activity) {
            Intent getNameScreenIntent = new Intent(this, MainActivity.class);
            startActivity(getNameScreenIntent);
        }

        //switch to second activity
        else if (item.getItemId() == R.id.action_second_activity) {

            Intent getNameScreenIntent = new Intent(this, SecondActivity.class);
            startActivity(getNameScreenIntent);
        }

        //notifies users that they are currently in third activity
        else if (item.getItemId() == R.id.action_third_activity) {
            Toast.makeText(ThirdActivity.this, "You are already in Binary mode", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void clearInfo() {
        displayValue = "";
        buttonLabel = "";
    }

    protected void onClickDelete(View v) {
        if (displayValue.length() > 1) {
            displayValue = displayValue.substring(0, displayValue.length() - 1);
            updateScreen();
        }
        else if (displayValue.length() == 1) {
            displayValue = "0";
            updateScreen();
        }
    }

    private void updateScreen() {
        display3.setText(displayValue);
    }

    protected void onClickEnter(View v) {
        Button b = (Button)v;
        buttonLabel = b.getText().toString();
        if (buttonLabel.equals("enter")) {
            try {
                decimal = Integer.parseInt(displayValue);
                positionalNotation value = new positionalNotation(decimal);
                displayValue = value.toBinary();
                updateScreen();
            }
            catch(NumberFormatException ex) {
                displayValue = "Invalid Input";
                updateScreen();
            }
            btnEnter.setText("clear");
        }

        else if (buttonLabel.equals("clear")) {
            clearInfo();
            updateScreen();
            btnEnter.setText("enter");
        }
    }

    protected void onClickNumber(View v) {
        Button b = (Button) v;
        displayValue += b.getText();
        if (displayValue.length() > 9) {
            displayValue = displayValue.substring(0, 9);
            Toast.makeText(ThirdActivity.this, "You have reached the input limit", Toast.LENGTH_SHORT).show();
        }
        updateScreen();
    }
}
