package com.example.asus.dgcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.math.BigDecimal;

import java.math.RoundingMode;

/**
 * Created by asus on 2018/4/10.
 */

public class ThirdActivity extends AppCompatActivity {
    private EditText rmb;
    private EditText dollar;
    private Button buttont_tanrs;
    private Button button_back;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        rmb = (EditText) findViewById(R.id.text_rmb);
        dollar = (EditText) findViewById(R.id.text_american);
        buttont_tanrs = (Button)findViewById(R.id.btnconversion);
        buttont_tanrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rmb.getText().toString().length() !=0){
                    dollar.setText(String .valueOf(Double.valueOf(String.valueOf(rmb.getText()))* 6.2913));
                }
                if (rmb.getText().toString().length() == 0 && dollar.getText().toString().length() !=0){
                    BigDecimal bg = new BigDecimal(Double.valueOf(String.valueOf(dollar.getText()))/ 6.2913).setScale(4, RoundingMode.UP);
                    rmb.setText(String .valueOf(bg));
                }
            }
        });

        button_back =(Button)findViewById(R.id.btnback);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThirdActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}



