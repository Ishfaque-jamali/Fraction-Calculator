package com.isho.frictioncalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class MoreActivity extends AppCompatActivity {

    TextView Step0_num1,Step0_num2,Step0_deno1,Step0_deno2,Step0_operation_sign,
            Step1_num1,Step1_Multiply1_sign ,Step1_multiply1, Step1_operation_sign,
            Step1_num2,Step1_Multiply2_sign, Step1_multiply2, Step1_deno1, Step1_deno2,Step1_deno2_operation,
            Step2_num,Step2_deno,finalAnswer,final_ans_equal_sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_more);

        // Step # 00
        Step0_num1 = findViewById(R.id.more_num1);
        Step0_num2 = findViewById(R.id.more_num2);
        Step0_deno1 = findViewById(R.id.more_deno1);
        Step0_deno2 = findViewById(R.id.more_deno2);
        Step0_operation_sign = findViewById(R.id.operation_sign_step0);

        // Step # 01
        Step1_num1 = findViewById(R.id.step1_num1);
        Step1_multiply1 = findViewById(R.id.step1_multiply1);
        Step1_operation_sign = findViewById(R.id.operation_sign_step1);
        Step1_num2 = findViewById(R.id.step1_num2);
        Step1_multiply2 = findViewById(R.id.step1_multiply2);
        Step1_Multiply1_sign = findViewById(R.id.step1_Multiply1_sign);
        Step1_Multiply2_sign = findViewById(R.id.step1_Multiply2_sign);
        Step1_deno1 = findViewById(R.id.step1_deno);
        Step1_deno2 = findViewById(R.id.step1_deno2);
        Step1_deno2_operation = findViewById(R.id.step1_deno_operation);

        // Step # 02
        Step2_num = findViewById(R.id.step2_num);
        Step2_deno = findViewById(R.id.step2_deno);

        //Step # 03
        final_ans_equal_sign=findViewById(R.id.final_Answer_Equal_sign);
        finalAnswer=findViewById(R.id.final_Answer);

        // Get intent data
        Intent getData = getIntent();
        int numirator1 = getData.getIntExtra("Numirator1", 0);
        int numirator2 = getData.getIntExtra("Numirator2", 0);
        int denominator1 = getData.getIntExtra("Denominator1", 1); // Avoid divide by zero
        int denominator2 = getData.getIntExtra("Denominator2", 1);
        String operator = getData.getStringExtra("Operation");

        // Step # 00 - Show original input
        Step0_num1.setText(String.valueOf(numirator1));
        Step0_deno1.setText(String.valueOf(denominator1));
        Step0_num2.setText(String.valueOf(numirator2));
        Step0_deno2.setText(String.valueOf(denominator2));
        Step0_operation_sign.setText(operator);

        // Step # 01 - Perform calculation
        int denominator = computeLCM(denominator1,denominator2);
        int multiply_1 = denominator / denominator1;
        int multiply_2 = denominator / denominator2;

        double resultNum = 0;
        int resultDeno = denominator;

        switch (operator) {
            case "+":
                Step1_num1.setText(String.valueOf(numirator1));
                Step1_multiply1.setText(String.valueOf(multiply_1));
                Step1_num2.setText(String.valueOf(numirator2));
                Step1_multiply2.setText(String.valueOf(multiply_2));
                Step1_deno1.setText(String.valueOf(denominator));
                Step0_operation_sign.setText(operator);
                Step1_operation_sign.setText(operator);
                Step1_multiply1.setVisibility(View.VISIBLE);
                Step1_multiply2.setVisibility(View.VISIBLE);
                Step1_Multiply1_sign.setVisibility(View.VISIBLE);
                Step1_Multiply2_sign.setVisibility(View.VISIBLE);

                resultNum = (numirator1 * multiply_1) + (numirator2 * multiply_2);
                break;

            case "-":
                Step1_num1.setText(String.valueOf(numirator1));
                Step1_multiply1.setText(String.valueOf(multiply_1));
                Step1_num2.setText(String.valueOf(numirator2));
                Step1_multiply2.setText(String.valueOf(multiply_2));
                Step1_deno1.setText(String.valueOf(denominator));
                Step0_operation_sign.setText(operator);
                Step1_operation_sign.setText(operator);

                Step1_multiply1.setVisibility(View.VISIBLE);
                Step1_multiply2.setVisibility(View.VISIBLE);
                Step1_Multiply1_sign.setVisibility(View.VISIBLE);
                Step1_Multiply2_sign.setVisibility(View.VISIBLE);

                resultNum = (numirator1 * multiply_1) - (numirator2 * multiply_2);
                break;

            case "*":
                Step1_num1.setText(String.valueOf(numirator1));
                Step1_num2.setText(String.valueOf(numirator2));
                Step1_deno1.setText(String.valueOf(denominator1));
                Step1_deno2.setText(String.valueOf(denominator2));
                Step0_operation_sign.setText(operator);
                Step1_operation_sign.setText(operator);

                Step1_deno2_operation.setVisibility(View.VISIBLE);
                Step1_deno2.setVisibility(View.VISIBLE);

                resultNum = numirator1 * numirator2;
                resultDeno = denominator1 * denominator2;
                break;

            case "/":
                Step1_num1.setText(String.valueOf(numirator1));
                Step1_num2.setText(String.valueOf(denominator2));
                Step1_deno1.setText(String.valueOf(denominator1));
                Step1_deno2.setText(String.valueOf(numirator2));
                Step0_operation_sign.setText(operator);
                Step1_operation_sign.setText(operator);

                Step1_deno2_operation.setVisibility(View.VISIBLE);
                Step1_deno2.setVisibility(View.VISIBLE);

                resultNum = numirator1 * denominator2;
                resultDeno = denominator1 * numirator2;
                break;
        }

        // Step # 02 - Final result
        int gcd = getGCD((int) resultNum, resultDeno);
        int simplifiedNum = (int) resultNum / gcd;
        int simplifiedDeno = resultDeno / gcd;

        Step2_num.setText(String.valueOf(simplifiedNum));
        Step2_deno.setText(String.valueOf(simplifiedDeno));

// If the simplified denominator is 1 (i.e., whole number), show only final answer
        if (simplifiedDeno == 1) {
            final_ans_equal_sign.setVisibility(View.VISIBLE);
            finalAnswer.setVisibility(View.VISIBLE);
            finalAnswer.setText(String.valueOf(simplifiedNum));
        }

    }

    // Custom method to find GCD without using Math library
    private int getGCD(int numeratorValue, int denominatorValue) {
        int absoluteNumerator = Math.abs(numeratorValue);
        int absoluteDenominator = Math.abs(denominatorValue);

        while (absoluteDenominator != 0) {
            int tempDenominator = absoluteDenominator;
            absoluteDenominator = absoluteNumerator % absoluteDenominator;
            absoluteNumerator = tempDenominator;
        }

        return (absoluteNumerator == 0) ? 1 : absoluteNumerator;
    }

    // Static method to compute GCD
    public static int computeGCD(int firstValue, int secondValue) {
        int absFirst = Math.abs(firstValue);
        int absSecond = Math.abs(secondValue);

        while (absSecond != 0) {
            int temp = absSecond;
            absSecond = absFirst % absSecond;
            absFirst = temp;
        }

        return absFirst;
    }

    // Static method to compute LCM
    public static int computeLCM(int firstDenominator, int secondDenominator) {
        return Math.abs(firstDenominator * secondDenominator) / computeGCD(firstDenominator, secondDenominator);
    }

}

