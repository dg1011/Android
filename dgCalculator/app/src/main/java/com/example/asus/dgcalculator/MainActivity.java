package com.example.asus.dgcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.math.BigDecimal;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    private int[] idNum = {R.id.txt0, R.id.txt1, R.id.txt2, R.id.txt3,
            R.id.txt4, R.id.txt5, R.id.txt6, R.id.txt7, R.id.txt8, R.id.txt9};  //数字Number输入
    private int[] idCal = {R.id.txtPlus, R.id.txtMinus, R.id.txtMul, R.id.txtDiv,
            R.id.txtLeft,R.id.txtRight,R.id.txtDot,
            R.id.txtsin,R.id.txtcos,R.id.txttan,R.id.txtExpo};  //运算符
    private Button[] buttonsCal = new Button[idCal.length];
    private Button[] buttonsNum = new Button[idNum.length];
    private Button buttonEqu;   //=
    private Button buttonClear;  // AC
    private Button buttonDel;  //←
    private Button butjzzh; //进制转换
    private Button butdwzh; //单位转换
    private EditText input ;
    private TextView result;
    private static String numStr = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        butjzzh = (Button) findViewById(R.id.txtjzzh);
        butjzzh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        butdwzh = (Button) findViewById(R.id.txtdwhs);
        butdwzh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
                startActivity(intent);
            }
        });

        input = (EditText)findViewById(R.id.input);
        input.setText("");
        input.setEnabled(false);
        result = (TextView) findViewById(R.id.output);
        result.setText("");

        buttonEqu = (Button)findViewById(R.id.txtIs);
        buttonEqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double output =  new Calculate().Calculate(numStr);
                if((int)output == output){
                    result.setText("" + (int)output);
                }
                else{
                    result.setText("" + output);
                }
                numStr = "";//将字符串制空
            }
        });

        buttonClear = (Button) findViewById(R.id.txtClear);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setText("");
                result.setText("");
                numStr="";
            }
        });

        buttonDel = (Button) findViewById(R.id.txtDel);
        buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!input.getText().toString().isEmpty() ) {
                    numStr = numStr.substring(0, numStr.length() - 1);
                    input.setText(numStr);
                }
            }
        });
        /**
         * 注册单击事件
         */
        for (int idcal = 0; idcal < idCal.length; idcal++) {
            buttonsCal[idcal] = (Button) findViewById(idCal[idcal]);
            buttonsCal[idcal].setOnClickListener(new CalOnClick(buttonsCal[idcal].getText().toString()));

        }
        for (int i = 0; i < idNum.length; i++) {
            buttonsNum[i] = (Button) findViewById(idNum[i]);
            buttonsNum[i].setOnClickListener(new NumberOnClick(buttonsNum[i].getText().toString()));
        }
    }
    //继承OnClick接口
    class NumberOnClick implements View.OnClickListener {
        String ch;
        /**
         *
         * @param msg 点击按钮传入字符
         */
        public NumberOnClick(String msg) {
            ch = msg;
        }

        @Override
        public void onClick(View v) {
            if (!result.getText().toString().equals("")) {
                input.setText("");
                result.setText("");
            }

            numStr = numStr + ch;
            input.setText(numStr);
        }
    }
    class CalOnClick implements  View.OnClickListener{
        String ch;
        public CalOnClick(String msg) {
            ch = msg;
        }
        @Override
        public void onClick(View v) {
            if (!result.getText().toString().equals("")) {
                input.setText("");
                result.setText("");
            }
            //cos sin tan 这几个符号只写首字母
            if(ch.equals("cos")){
                ch = "c";
            }
            if(ch.equals("sin")){
                ch = "s";
            }
            if(ch.equals("tan")){
                ch = "t";
            }
            numStr = numStr + ch;
            input.setText(numStr);
        }
    }

    /* 计算器优先级算法 */
    public class Calculate {

        /**
         * 数字栈：用于存储表达式中的各个数字
         */
        private Stack<Double> numberStack = null;
        /**
         * 符号栈：用于存储运算符和括号
         */
        private Stack<Character> symbolStack = null;

        /**
         * 解析并计算四则运算表达式(含括号)，返回计算结果
         *
         * @param numStr 算术表达式(含括号)
         */
        public double Calculate(String numStr) {

            // 检查表达式是否合法
            if (!isStandard(numStr)) {
                System.err.println("错误：算术表达式有误！");
                return 0;
            }
            //在尾部加上=表示运算式结束符
            numStr += "=";
            //防止直接进行三角函数运算时无法出数据栈中取出下文中的a和b
            numStr = "0+" + numStr;
            // 初始化栈
            numberStack = new Stack<Double>();
            symbolStack = new Stack<Character>();
            // 用于缓存数字，因为数字可能是多位的
            StringBuffer temp = new StringBuffer();
            // 从表达式的第一个字符开始处理
            for (int i = 0; i < numStr.length(); i++) {
                char ch = numStr.charAt(i); // 获取一个字符
                if (isNumber(ch)) { // 若当前字符是数字
                    temp.append(ch); // 加入到数字缓存中
                } else { // 非数字的情况
                    String tempStr = temp.toString(); // 将数字缓存转为字符串
                    if (!tempStr.isEmpty()) {
                        double num = Double.valueOf(tempStr); // 将数字字符串转为长整型数
                        numberStack.push(num); // 将数字压栈
                        temp = new StringBuffer(); // 重置数字缓存
                    }
                    // 判断运算符的优先级，若当前优先级低于栈顶的优先级，则先把计算前面计算出来
                    while (!comparePri(ch) && !symbolStack.empty()) {
                        double b = numberStack.pop(); // 出栈，取出数字，后进先出
                        double a = numberStack.pop();

                        //高精度计算函数
                        BigDecimal b2 = new BigDecimal(Double.toString(b));
                        BigDecimal b1 = new BigDecimal(Double.toString(a));
                        // 取出运算符进行相应运算，并把结果压栈进行下一次运算
                        switch ((char) symbolStack.pop()) {
                            case '+':
                                numberStack.push(Double.valueOf(b1.add(b2).doubleValue()));
                                break;
                            case '-':
                                numberStack.push(Double.valueOf(b1.subtract(b2).doubleValue()));
                                break;
                            case '*':
                                numberStack.push(Double.valueOf(b1.multiply(b2).doubleValue()));
                                break;
                            case '/':
                                numberStack.push(Double.valueOf(b1.divide(b2, BigDecimal.ROUND_HALF_UP).doubleValue()));
                                break;
                            case '^':
                                numberStack.push((double) Math.pow(a, b));
                                break;
                            //三角函数的输入为角度，Math.toRadians(b)将角度转化为弧度
                            case 'c':
                                numberStack.push(a);
                                numberStack.push((double) Math.cos(Math.toRadians(b)));
                                break;
                            case 's':
                                numberStack.push(a);
                                numberStack.push((double) Math.sin(Math.toRadians(b)));
                                break;
                            case 't':
                                numberStack.push(a);
                                numberStack.push((double) Math.tan(Math.toRadians(b)));
                                break;
                            default:
                                break;
                        }
                    } // while循环结束
                    if (ch != '=') {
                        symbolStack.push(new Character(ch)); // 符号入栈
                        if (ch == ')') { // 去括号
                            symbolStack.pop();
                            symbolStack.pop();
                        }
                    }
                }
            } // for循环结束

            return numberStack.pop(); // 返回计算结果
        }

        /**
         * 检查算术表达式的基本合法性，符合返回true，否则false
         */
        private boolean isStandard(String numStr) {
            if (numStr == null || numStr.isEmpty()) // 表达式不能为空
                return false;
            Stack<Character> stack = new Stack<Character>(); // 用来保存括号，检查左右括号是否匹配

            for (int i = 0; i < numStr.length(); i++) {
                char n = numStr.charAt(i);
                // 判断字符是否合法
                if (!(isNumber(n) || "(".equals(n + "") || ")".equals(n + "")
                        || "+".equals(n + "") || "-".equals(n + "")
                        || "*".equals(n + "") || "/".equals(n + "")
                        || "^".equals(n + "") || "c".equals(n + "")
                        || "s".equals(n + "") || "t".equals(n + "")
                )) {
                    return false;
                }
                // 将左括号压栈，用来给后面的右括号进行匹配
                if ("(".equals(n + "")) {
                    stack.push(n);
                }
                if (")".equals(n + "")) { // 匹配括号
                    if (stack.isEmpty() || !"(".equals((char) stack.pop() + "")) // 括号是否匹配
                        return false;
                }

            }
            // 可能会有缺少右括号的情况
            if (!stack.isEmpty())
                return false;

            return true;
        }

        /**
         * 判断字符是否是0-9的数字
         */
        private boolean isNumber(char num) {
            if (num >= '0' && num <= '9' || num == '.')
                return true;
            return false;
        }

        /**
         * 比较优先级：如果当前运算符比栈顶元素运算符优先级高则返回true，否则返回false
         */
        private boolean comparePri(char symbol) {
            if (symbolStack.empty()) { // 空栈返回ture
                return true;
            }

            // 符号优先级说明（从高到低）:
            // 第1级: (
            // 第2级: ^ cos(c) sin(s) tan(t)
            // 第3级: * /
            // 第4级: + -
            // 第5级: )

            char top = (char) symbolStack.peek(); // 查看堆栈顶部的对象，注意不是出栈
            if (top == '(') {
                return true;
            }
            // 比较优先级
            switch (symbol) {
                case '(': // 优先级最高
                    return true;
                case '^': {
                    if (top == '+' || top == '-' || top == '*' || top == '/') // 优先级比+和-高
                        return true;
                    else
                        return false;
                }
                case 'c': {
                    if (top == '+' || top == '-' || top == '*' || top == '/') // 优先级比+和-高
                        return true;
                    else
                        return false;
                }
                case 's': {
                    if (top == '+' || top == '-' || top == '*' || top == '/') // 优先级比+和-高
                        return true;
                    else
                        return false;
                }
                case 't': {
                    if (top == '+' || top == '-' || top == '*' || top == '/') // 优先级比+和-高
                        return true;
                    else
                        return false;
                }
                case '*': {
                    if (top == '+' || top == '-') // 优先级比+和-高
                        return true;
                    else
                        return false;
                }
                case '/': {
                    if (top == '+' || top == '-') // 优先级比+和-高
                        return true;
                    else
                        return false;
                }
                case '+':
                    return false;
                case '-':
                    return false;
                case ')': // 优先级最低
                    return false;
                case '=': // 结束符
                    return false;
                default:
                    break;
            }
            return true;
        }
    }
}




