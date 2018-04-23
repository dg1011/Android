package com.example.asus.wordapp;

/**
 * Created by asus on 2018/4/21.
 */


 import java.io.BufferedReader;
 import java.io.ByteArrayInputStream;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.InputStreamReader;

 import javax.xml.parsers.DocumentBuilder;
 import javax.xml.parsers.DocumentBuilderFactory;
 import javax.xml.parsers.ParserConfigurationException;

 import org.w3c.dom.Document;
 import org.w3c.dom.Element;
 import org.w3c.dom.NodeList;
 import org.xml.sax.SAXException;

public class getWordFromInternet {

    public WordValue getWordFromInternet(String searchedWord){

        WordValue wordValue=new WordValue();
        String tempWord=searchedWord;

        //连接网络，根据tempUrl得到输入流
        InputStream in=null;
        String text=null;
        try{
            String tempUrl=NetOperator.iCiBaURL1+tempWord+NetOperator.iCiBaURL2;
            //System.out.println(tempUrl);
            //从网络获得输入流
            in=NetOperator.getInputStreamByUrl(tempUrl);
            //把输入流读出来
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null){
                builder.append(line);
            }
            //保存得到的xml结果
            text = builder.toString();
            //System.out.println(text);
        }catch(Exception e){
            e.printStackTrace();
        }

        //对xml字符串进行处理，得到数据
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        Document doc = null;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        //对xml字符串的处理
        InputStream iStream=new ByteArrayInputStream(text.getBytes());
        try {
            doc = db.parse(iStream);
        } catch (SAXException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        Element root=doc.getDocumentElement();

        //得到单词key
        NodeList key=root.getElementsByTagName("key");
        for(int i = 0; i<key.getLength(); i++){
            try {
                String s = key.item(i).getFirstChild().getNodeValue();
                wordValue.setKey(s);
            } catch (Exception e1) {
                // TODO 自动生成的 catch 块
                e1.printStackTrace();
            }
        }

        //得到单词发音ps
        NodeList ps=root.getElementsByTagName("ps");
        try {
            String e = ps.item(0).getFirstChild().getNodeValue();
            wordValue.setPsE(e);
            String a = ps.item(1).getFirstChild().getNodeValue();
            //System.out.println(ps.item(1).getFirstChild());
            wordValue.setPsA(a);
        } catch (Exception e1) {
            // TODO 自动生成的 catch 块
            e1.printStackTrace();
        }

        //得到单词词性及相对应的意思
        NodeList acceptation=root.getElementsByTagName("acceptation");
        NodeList pos=root.getElementsByTagName("pos");
        String ac = "";
        for(int i = 0; i<pos.getLength(); i++){
            try {
                ac = ac + pos.item(i).getFirstChild().getNodeValue() + ":" + acceptation.item(i).getFirstChild().getNodeValue() + "\n";
                wordValue.setAcceptation(ac);
            } catch (Exception e1) {
                // TODO 自动生成的 catch 块
                e1.printStackTrace();
            }
        }

        //得到单词例句及例句翻译
        NodeList sent=root.getElementsByTagName("sent");
        try {
            String orig = sent.item(0).getChildNodes().item(0).getFirstChild().getNodeValue();
            wordValue.setSentOrig(orig);
            String trans = sent.item(0).getChildNodes().item(1).getFirstChild().getNodeValue();
            //System.out.print(trans);
            wordValue.setSentTrans(trans);
        } catch (Exception e1) {
            // TODO 自动生成的 catch 块
            e1.printStackTrace();
        }
        return wordValue;
    }



}



