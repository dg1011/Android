package com.example.asus.wordapp;

/**
 * Created by asus on 2018/4/21.
 */

public class WordValue {
    public String key=null, //单词
            psE=null,  //英音发音
            psA=null,  //美音发音
            acceptation=null, // 意思
            sentOrig=null, // 例句
            sentTrans=null;  // 例句翻译



    public WordValue(String word, String psE, String psA,
                     String acceptation, String sentOrig, String sentTrans) {
        super();
        this.key = ""+word;
        this.psE = ""+psE;
        this.psA = ""+psA;
        this.acceptation = ""+acceptation;
        this.sentOrig = ""+sentOrig;
        this.sentTrans = ""+sentTrans;
    }

    public WordValue() {
        super();
        this.key = "";      //防止空指针异常
        this.psE = "";
        this.psA = "";
        this.acceptation = "";
        this.sentOrig = "";
        this.sentTrans = "";


    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPsE() {
        return psE;
    }

    public void setPsE(String psE) {
        this.psE = psE;
    }

    public String getPsA() {
        return psA;
    }

    public void setPsA(String psA) {
        this.psA = psA;
    }

    public String getAcceptation() {
        return acceptation;
    }

    public void setAcceptation(String acceptation) {
        this.acceptation = acceptation;
    }

    public String getSentOrig() {
        return sentOrig;
    }

    public void setSentOrig(String sentOrig) {
        this.sentOrig = sentOrig;
    }

    public String getSentTrans() {
        return sentTrans;
    }

    public void setSentTrans(String sentTrans) {
        this.sentTrans = sentTrans;
    }

    public void printInfo(){
        System.out.println(this.getKey());
        System.out.println("英[" + this.getPsE() + "]");
        System.out.println("美[" + this.getPsA() + "]");
        System.out.println(this.getAcceptation());
        System.out.println(this.getSentOrig());
        System.out.println(this.getSentTrans());

    }

}
