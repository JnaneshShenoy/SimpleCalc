package com.example.myfirstapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText result;
    private double operand1, operand2;
    private String operator;
    private boolean isNewCalculation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.result);

        // Numbers
        setNumber(R.id.b0, "0");
        setNumber(R.id.b1, "1");
        setNumber(R.id.b2, "2");
        setNumber(R.id.b3, "3");
        setNumber(R.id.b4, "4");
        setNumber(R.id.b5, "5");
        setNumber(R.id.b6, "6");
        setNumber(R.id.b7, "7");
        setNumber(R.id.b8, "8");
        setNumber(R.id.b9, "9");

//        // Decimal Button
//        findViewById(R.id.bdot).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                appendToResult(".");
//            }
//        });

        // Operators
        setOperatorButtonClickListener(R.id.bp, "+");
        setOperatorButtonClickListener(R.id.bm, "-");
        setOperatorButtonClickListener(R.id.bmul, "*");
        setOperatorButtonClickListener(R.id.bd, "/");

        // Clear Button
        findViewById(R.id.bde).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearResult();
            }
        });

        // Equal Button
        findViewById(R.id.eq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performCalculation();
                isNewCalculation = true;
            }
        });

        // Initialize with default values
        clearResult();
        isNewCalculation = true;
    }

    private void setNumber(int buttonId, final String number) {
        findViewById(buttonId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNewCalculation) {
                    result.setText(number);
                    isNewCalculation = false;
                } else {
                    appendToResult(number);
                }
            }
        });
    }

    private void clearResult() {
        result.setText("0");
        operand1 = 0;
        operand2 = 0;
        operator = null;
        isNewCalculation = true;
    }

    private void setOperatorButtonClickListener(int buttonId, final String operation) {
        findViewById(buttonId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNewCalculation && !operatorPressedDirectly()) {
                    operand1 = Double.parseDouble(result.getText().toString());
                    appendToResult(" " + operation + " ");
                    operator = operation;
                }
            }
        });
    }

    private void appendToResult(String value) {
        String currentResult = result.getText().toString();
        result.setText(currentResult + value);
    }

    private void performCalculation() {
        if (!result.getText().toString().isEmpty() && operator != null) {
            String[] expression = result.getText().toString().split(" ");

            if (expression.length == 3) {
                try {
                    operand2 = Double.parseDouble(expression[2]);
                } catch (NumberFormatException e) {
                    // Handle invalid operand
                    result.setText("Error");
                    return;
                }

                double resultValue = 0;

                switch (operator) {
                    case "+":
                        resultValue = operand1 + operand2;
                        break;
                    case "-":
                        resultValue = operand1 - operand2;
                        break;
                    case "*":
                        resultValue = operand1 * operand2;
                        break;
                    case "/":
                        if (operand2 != 0) {
                            resultValue = operand1 / operand2;
                        } else {
                            // Handle division by zero
                            result.setText("Error");
                            return;
                        }
                        break;
                }

                // Check if the result is an integer
                if (resultValue % 1 == 0) {
                    result.setText(String.valueOf((int) resultValue));
                } else {
                    result.setText(String.valueOf(resultValue));
                }

                operand1 = resultValue;
                operator = null;
            } else {
                // Handle invalid expression
                result.setText("Error");
            }
        }
    }

    private boolean operatorPressedDirectly() {
        return result.getText().toString().endsWith(" ");
    }
}
