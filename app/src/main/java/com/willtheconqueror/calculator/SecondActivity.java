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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    Button btnEnter;
    String displayValue2 = "";
    private TextView display2;
    EditText textInputA;
    EditText textInputB;
    EditText textInputC;
    String buttonLabel = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        display2 = (TextView)findViewById(R.id.textView2);
        btnEnter = (Button)findViewById(R.id.btnEnter);
        display2.setText(displayValue2);

        textInputA = (EditText)findViewById(R.id.user_input_a);
        textInputB = (EditText)findViewById(R.id.user_input_b);
        textInputC = (EditText)findViewById(R.id.user_input_c);

        Toolbar actionToolbar = (Toolbar)findViewById(R.id.toolbar_second);
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

        //notifies users that they are currently in second activity
        else if (item.getItemId() == R.id.action_second_activity) {
            Toast.makeText(SecondActivity.this, "You are already in Quadratic Formula mode", Toast.LENGTH_SHORT).show();
        }

        //switch to third activity
        else if (item.getItemId() == R.id.action_third_activity) {
            Intent getNameScreenIntent = new Intent(this, ThirdActivity.class);
            startActivity(getNameScreenIntent);
        }

        return super.onOptionsItemSelected(item);
    }
    private void updateScreen() {
        display2.setText(displayValue2);
    }
    private void clearInfo() {
        textInputA.setText("");
        textInputB.setText("");
        textInputC.setText("");
        buttonLabel = "";
        displayValue2 = "";
    }

    protected void onClickEnter(View v) {
        Button b = (Button) v;
        buttonLabel = b.getText().toString();
        if (buttonLabel.equals("enter")) {

            try {
                double userInputA = Double.parseDouble(textInputA.getText().toString());
                double userInputB = Double.parseDouble(textInputB.getText().toString());
                double userInputC = Double.parseDouble(textInputC.getText().toString());
                quadraticBrain quadraticSum = new quadraticBrain(userInputA, userInputB, userInputC);
                displayValue2 = ("Ans1:   " + Double.toString(quadraticSum.positiveSum()) +
                        "\nAns2:   " + Double.toString(quadraticSum.negativeSum()));
                updateScreen();

            }
            catch (NumberFormatException ex) {
                displayValue2 = "Invalid Input";
                updateScreen();
            }

            btnEnter.setText("clear");

        }
        else if (buttonLabel.equals("clear")){
            clearInfo();
            updateScreen();
            btnEnter.setText("enter");
        }

    }
}
