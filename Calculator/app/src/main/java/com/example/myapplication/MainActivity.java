package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView resultTv, solutionTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        solutionTv = findViewById(R.id.solution_tv);
        resultTv = findViewById(R.id.result_tv);

        assignId(R.id.button_c).setOnClickListener(this);
        assignId(R.id.button_open_bracket).setOnClickListener(this);
        assignId(R.id.button_close_bracket).setOnClickListener(this);
        assignId(R.id.button_divide).setOnClickListener(this);
        assignId(R.id.button_multiplay).setOnClickListener(this);
        assignId(R.id.button_plus).setOnClickListener(this);
        assignId(R.id.button_minus).setOnClickListener(this);
        assignId(R.id.button_equals).setOnClickListener(this);
        assignId(R.id.button_0).setOnClickListener(this);
        assignId(R.id.button_1).setOnClickListener(this);
        assignId(R.id.button_2).setOnClickListener(this);
        assignId(R.id.button_3).setOnClickListener(this);
        assignId(R.id.button_4).setOnClickListener(this);
        assignId(R.id.button_5).setOnClickListener(this);
        assignId(R.id.button_6).setOnClickListener(this);
        assignId(R.id.button_7).setOnClickListener(this);
        assignId(R.id.button_8).setOnClickListener(this);
        assignId(R.id.button_9).setOnClickListener(this);
        assignId(R.id.button_ac).setOnClickListener(this);
        assignId(R.id.button_dot).setOnClickListener(this);
    }

    private MaterialButton assignId(int id) {
        MaterialButton button = findViewById(id);
        button.setOnClickListener(this);
        return button;
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutionTv.getText().toString();

        if (buttonText.equals("AC")) {
            solutionTv.setText("");
            resultTv.setText("0");
        } else if (buttonText.equals("=")) {
            try {
                String finalResult = getResult(dataToCalculate);
                solutionTv.setText(finalResult);
                resultTv.setText(finalResult);
            } catch (Exception e) {
                // Handle error case
                Toast.makeText(this, "Invalid expression", Toast.LENGTH_SHORT).show();
                solutionTv.setText(""); // Clear the input field to let the user enter a new expression
            }
        } else if (buttonText.equals("c")) {
            if (dataToCalculate.length() > 0) {
                dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
            }
            solutionTv.setText(dataToCalculate);
        } else {
            dataToCalculate += buttonText;
            solutionTv.setText(dataToCalculate);
        }
    }

    private String getResult(String data) {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            Object evaluated = context.evaluateString(scriptable, data, "Javascript", 1, null);
            String finalResult = Context.toString(evaluated);
            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;
        } finally {
            Context.exit();
        }
    }
}
