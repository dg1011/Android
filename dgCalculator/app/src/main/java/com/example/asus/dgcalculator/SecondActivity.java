package com.example.asus.dgcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by asus on 2018/4/10.
 */

public class SecondActivity extends AppCompatActivity {
    private EditText one;
    private EditText two;
    private EditText third;
    private TextView result;
    private Button buttont_tanrs;
    private Button button_back;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        one = (EditText) findViewById(R.id.text_1);
        two = (EditText) findViewById(R.id.text_2);
        third = (EditText) findViewById(R.id.text_3);
        result = (TextView)findViewById(R.id.txt_result);

        buttont_tanrs = (Button)findViewById(R.id.btnconversion);
        buttont_tanrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.setText(Integer.toString(Integer.valueOf(String.valueOf(two.getText()), Integer.parseInt(String.valueOf(one.getText()))), Integer.parseInt(String.valueOf(third.getText()))));
            }
        });

        button_back =(Button)findViewById(R.id.btnback);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
