package com.example.asus.wordapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    private Button find,delete,change;
    private EditText input,acc;
    private TextView pse,psa,sentorig,senttrans,come;
    private static final String TAG = "tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = (EditText) findViewById(R.id.input);
        acc = (EditText) findViewById(R.id.acc);

        pse = (TextView) findViewById(R.id.pse);
        psa = (TextView) findViewById(R.id.psa);
        sentorig = (TextView) findViewById(R.id.sentorig);
        senttrans = (TextView) findViewById(R.id.senttrans);
        come = (TextView) findViewById(R.id.come);

        find = (Button)findViewById(R.id.find);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ljwl x=new ljwl();
                x.start();
            }
        });


        delete = (Button)findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String key = input.getText().toString();
                wordDBHelper dbHelper = new wordDBHelper(MainActivity.this,"word_db",null,1);
                SQLiteDatabase db =dbHelper.getWritableDatabase();

                //1
                String a1 = "delete  from " + "word_table" + " where danci = '" + key + "'";
                db.execSQL(a1);

                acc.setText("删除成功！！！");
            }
        });

        change = (Button)findViewById(R.id.change);
        change.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String key = input.getText().toString();
                wordDBHelper dbHelper = new wordDBHelper(MainActivity.this,"word_db",null,1);
                SQLiteDatabase db =dbHelper.getWritableDatabase();

                //1
                String a1 = "delete  from " + "word_table" + " where danci = '" + key + "'";
                db.execSQL(a1);

                WordValue word = new WordValue();
                word.setKey(key);
                word.setPsE(pse.getText().toString());
                word.setPsA(psa.getText().toString());
                word.setAcceptation(acc.getText().toString());
                word.setSentOrig(sentorig.getText().toString());
                word.setSentTrans(senttrans.getText().toString());

                String psE = null;
                try {
                    psE = URLEncoder.encode(word.getPsE(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                String psA = null;
                try {
                    psA = URLEncoder.encode(word.getPsA(), "UTF-8");
                } catch (UnsupportedEncodingException e1) {
                    // TODO 自动生成的 catch 块
                    e1.printStackTrace();
                }
                //1
                String a = "INSERT INTO " + "word_table(danci,psE,psA,acceptation,sentOrig,sentTrans)" + " VALUES('" + word.getKey() + "','" + psE + "','" + psA + "','" + word.getAcceptation() + "','" + word.getSentOrig() + "','" + word.getSentTrans() + "')";
                db.execSQL(a);


            }
        });
    }


    //上网查询超时，添加线程
    class ljwl extends Thread {
        ljwl() {
        }

        public void run() {
            String key = input.getText().toString();
            WordValue word = new WordValue();
            wordDBHelper dbHelper = new wordDBHelper(MainActivity.this, "word_db", null, 1);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            //dbHelper.onCreate(db);
            //参数1：表名
            //参数2：要想显示的列
            //参数3：where子句
            //参数4：where子句对应的条件值
            //参数5：分组方式
            //参数6：having条件
            //参数7：排序方式
            Cursor cursor = db.query("word_table", new String[]{"danci", "psE", "psA", "acceptation", "sentOrig", "sentTrans"}, "danci=?", new String[]{key}, null, null, null);
            while (cursor.moveToNext()) {
                word.setKey(key);
                try {
                    word.setPsE(URLDecoder.decode(cursor.getString(cursor.getColumnIndex("psE")), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                try {
                    word.setPsA(URLDecoder.decode(cursor.getString(cursor.getColumnIndex("psA")), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                word.setAcceptation(cursor.getString(cursor.getColumnIndex("acceptation")));
                word.setSentOrig(cursor.getString(cursor.getColumnIndex("sentOrig")));
                word.setSentTrans(cursor.getString(cursor.getColumnIndex("sentTrans")));
            }

            if (word.getKey() == key) {
                pse.setText("英[" + word.getPsE() + "]");
                psa.setText("美[" + word.getPsA() + "]");
                acc.setText(word.getAcceptation());
                sentorig.setText(word.getSentOrig());
                senttrans.setText(word.getSentTrans());
                come.setText("来至本地");
            } else {
                getWordFromInternet a = new getWordFromInternet();
                word = a.getWordFromInternet(key);
                String psE = null;
                try {
                    psE = URLEncoder.encode(word.getPsE(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                String psA = null;
                try {
                    psA = URLEncoder.encode(word.getPsA(), "UTF-8");
                } catch (UnsupportedEncodingException e1) {
                    // TODO 自动生成的 catch 块
                    e1.printStackTrace();
                }
                //1
                String a1 = "INSERT INTO " + "word_table(danci,psE,psA,acceptation,sentOrig,sentTrans)" + " VALUES('" + word.getKey() + "','" + psE + "','" + psA + "','" + word.getAcceptation() + "','" + word.getSentOrig() + "','" + word.getSentTrans() + "')";
                db.execSQL(a1);

                pse.setText("英[" + word.getPsE() + "]");
                psa.setText("美[" + word.getPsA() + "]");
                acc.setText(word.getAcceptation());
                sentorig.setText(word.getSentOrig());
                senttrans.setText(word.getSentTrans());
                come.setText("来至网络");

            }
        }
    }}
