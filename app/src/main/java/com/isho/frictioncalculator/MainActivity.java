package com.isho.frictioncalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Declare all UI elements
    EditText numinator1, denominator1, numinator2, denominator2;
    TextView showText, ansNumi, ansDeno, opreation, ansLine, equalSign, finalAnswer;
    Button plus, minus, multiply, divide,More;
    String Selected_Oprator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize EditTexts
        numinator1 = findViewById(R.id.numinator1);
        denominator1 = findViewById(R.id.Denominator1);
        numinator2 = findViewById(R.id.numinator2);
        denominator2 = findViewById(R.id.Denominator2);

        // Initialize TextViews
        showText = findViewById(R.id.showText);
        ansNumi = findViewById(R.id.ansNuminator);
        ansDeno = findViewById(R.id.ansDenominator);
        opreation = findViewById(R.id.opreatin);
        ansLine = findViewById(R.id.ansLine);
        equalSign = findViewById(R.id.equal);
        finalAnswer = findViewById(R.id.finalAns);

        // Initialize Buttons
        plus = findViewById(R.id.btnPlus);
        minus = findViewById(R.id.btnMinus);
        multiply = findViewById(R.id.btnMultiply);
        divide = findViewById(R.id.btnDevide);
        More=findViewById(R.id.moreButton);

        // Set listeners
        plus.setOnClickListener(v -> performOperation("+"));
        minus.setOnClickListener(v -> performOperation("-"));
        multiply.setOnClickListener(v -> performOperation("*"));
        divide.setOnClickListener(v -> performOperation("/"));
        More.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Step 1: Get text from all 4 EditText fields
                String num1 = numinator1.getText().toString().trim();
                String den1 = denominator1.getText().toString().trim();
                String num2 = numinator2.getText().toString().trim();
                String den2 = denominator2.getText().toString().trim();

                // Step 2: Check if any field is empty before proceeding
                if (num1.isEmpty() || den1.isEmpty() || num2.isEmpty() || den2.isEmpty()) {
                    showText.setText("Please fill all fields before proceeding.");
                    return; // stop execution
                }

                try {
                    // Step 3: Convert strings to integers
                    int numinatorInput1 = Integer.parseInt(num1);
                    int denominatorInput1 = Integer.parseInt(den1);
                    int numinatorInput2 = Integer.parseInt(num2);
                    int denominatorInput2 = Integer.parseInt(den2);

                    // Step 4: Create intent to start MoreActivity
                    Intent moresol = new Intent(MainActivity.this, MoreActivity.class);

                    // Step 5: Pass all 4 values and selected operation to next activity
                    moresol.putExtra("Numirator1", numinatorInput1);
                    moresol.putExtra("Numirator2", numinatorInput2);
                    moresol.putExtra("Denominator1", denominatorInput1);
                    moresol.putExtra("Denominator2", denominatorInput2);
                    moresol.putExtra("Operation", Selected_Oprator);

                    // Step 6: Start the MoreActivity
                    startActivity(moresol);

                } catch (NumberFormatException e) {
                    // Step 7: Handle non-numeric input
                    showText.setText("Invalid input. Please enter valid numbers.");
                }
            }
        });

    }

    private void performOperation(String operator) {
        String num1 = numinator1.getText().toString().trim();
        String den1 = denominator1.getText().toString().trim();
        String num2 = numinator2.getText().toString().trim();
        String den2 = denominator2.getText().toString().trim();

        int numinatorInput1 = Integer.parseInt(num1);
        int denominatorInput1 = Integer.parseInt(den1);
        int numinatorInput2 = Integer.parseInt(num2);
        int denominatorInput2 = Integer.parseInt(den2);

        if (num1.isEmpty() || den1.isEmpty() || num2.isEmpty() || den2.isEmpty()) {
            showText.setText("Please enter all required values!");
            return;
        }


        int resultNum = 0;
        int resultDeno = 0;
        Selected_Oprator=operator;
        switch (operator) {
            case "+":
                resultNum = (numinatorInput1 * denominatorInput2) + (numinatorInput2 * denominatorInput1);
                resultDeno = denominatorInput1 * denominatorInput2;
                break;
            case "-":
                resultNum = (numinatorInput1 * denominatorInput2) - (numinatorInput2 * denominatorInput1);
                resultDeno = denominatorInput1 * denominatorInput2;
                break;
            case "*":
                resultNum = numinatorInput1 * numinatorInput2;
                resultDeno = denominatorInput1 * denominatorInput2;
                break;
            case "/":
                resultNum = numinatorInput1 * denominatorInput2;
                resultDeno = denominatorInput1 * numinatorInput2;
                break;
        }

        // Simplify the result
        int gcd = getGCD(resultNum, resultDeno);
        int finalNumi = resultNum / gcd;
        int finalDeno = resultDeno / gcd;

        opreation.setText(operator);
        ansNumi.setText(String.valueOf(finalNumi));
        ansDeno.setText(String.valueOf(finalDeno));
        ansLine.setVisibility(View.VISIBLE);
        equalSign.setVisibility(View.GONE);
        finalAnswer.setVisibility(View.GONE);
        showText.setText("");

        // If the fraction simplifies to a whole number, show it
        if (finalDeno != 0 && finalNumi % finalDeno == 0) {
            int wholeResult = finalNumi / finalDeno;
            equalSign.setVisibility(View.VISIBLE);
            finalAnswer.setVisibility(View.VISIBLE);
            finalAnswer.setText(String.valueOf(wholeResult));
        }
    }

    // Custom method to find GCD without using Math library
    private int getGCD(int a, int b) {
        a = (a < 0) ? -a : a;
        b = (b < 0) ? -b : b;
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return (a == 0) ? 1 : a;
    }
}



/*
package com.isho.frictioncalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Declare all UI elements
    EditText numinator1, denominator1, numinator2, denominator2;
    TextView showText, ansNumi, ansDeno, opreation, ansLine, equalSign, finalAnswer;
    Button plus, minus, multiply, divide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Enable edge-to-edge UI
        setContentView(R.layout.activity_main); // Set layout file

        // Initialize EditTexts
        numinator1 = findViewById(R.id.numinator1);
        denominator1 = findViewById(R.id.Denominator1);
        numinator2 = findViewById(R.id.numinator2);
        denominator2 = findViewById(R.id.Denominator2);

        // Initialize TextViews
        showText = findViewById(R.id.showText);
        ansNumi = findViewById(R.id.ansNuminator);
        ansDeno = findViewById(R.id.ansDenominator);
        opreation = findViewById(R.id.opreatin);
        ansLine = findViewById(R.id.ansLine);
        equalSign = findViewById(R.id.equal);
        finalAnswer = findViewById(R.id.finalAns);

        // Initialize Buttons
        plus = findViewById(R.id.btnPlus);
        minus = findViewById(R.id.btnMinus);
        multiply = findViewById(R.id.btnMultiply);
        divide = findViewById(R.id.btnDevide);

        // Set listeners for each button and perform the appropriate operation
        plus.setOnClickListener(v -> performOperation("+"));
        minus.setOnClickListener(v -> performOperation("-"));
        multiply.setOnClickListener(v -> performOperation("*"));
        divide.setOnClickListener(v -> performOperation("/"));
    }

    */
/**
     * This method performs the arithmetic operation based on the operator.
     * It reads values from EditTexts, validates them, calculates result,
     * and displays the output in TextViews.
     *//*

    private void performOperation(String operator) {
        // Get input strings and trim spaces
        String num1 = numinator1.getText().toString().trim();
        String den1 = denominator1.getText().toString().trim();
        String num2 = numinator2.getText().toString().trim();
        String den2 = denominator2.getText().toString().trim();

        // Validate inputs
        if (num1.isEmpty() || den1.isEmpty() || num2.isEmpty() || den2.isEmpty()) {
            showText.setText("Please enter all required values!");
            return;
        }

        // Parse integers from EditText inputs
        int numinatorInput1 = Integer.parseInt(num1);
        int denominatorInput1 = Integer.parseInt(den1);
        int numinatorInput2 = Integer.parseInt(num2);
        int denominatorInput2 = Integer.parseInt(den2);

        int resultNum = 0;
        int resultDeno = 0;

        // Perform operation based on selected operator
        switch (operator) {
            case "+":
                resultNum = (numinatorInput1 * denominatorInput2) + (numinatorInput2 * denominatorInput1);
                resultDeno = denominatorInput1 * denominatorInput2;
                break;

            case "-":
                resultNum = (numinatorInput1 * denominatorInput2) - (numinatorInput2 * denominatorInput1);
                resultDeno = denominatorInput1 * denominatorInput2;
                break;

            case "*":
                resultNum = numinatorInput1 * numinatorInput2;
                resultDeno = denominatorInput1 * denominatorInput2;
                break;

            case "/":
                resultNum = numinatorInput1 * denominatorInput2;
                resultDeno = denominatorInput1 * numinatorInput2;
                break;
        }

       */
/* if ( resultNum % resultDeno == 0) {
            int finalResult = resultNum / resultDeno;
            opreation.setText(operator);
            ansNumi.setText(String.valueOf(resultNum));
            ansDeno.setText(String.valueOf(resultDeno));
            ansLine.setVisibility(View.VISIBLE);
            equalSign.setVisibility(View.VISIBLE); // ✅ Show equal sign
            finalAnswer.setText(String.valueOf(finalResult)); // ✅ Show final result
            finalAnswer.setVisibility(View.VISIBLE);
            showText.setText(""); // Clear warning
        } else {
            opreation.setText(operator);
            ansNumi.setText(String.valueOf(resultNum));
            ansDeno.setText(String.valueOf(resultDeno));
            ansLine.setVisibility(View.VISIBLE);
            equalSign.setVisibility(View.GONE); // 🔻 Hide equal sign
            finalAnswer.setVisibility(View.GONE); // 🔻 Hide final result
            showText.setText(""); // Clear warning
        }*//*


    }
}*/
