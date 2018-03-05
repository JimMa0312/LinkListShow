package com.xt.linklistshow.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class ReadFromFile {
    public static ObservableList<String> readFileByLine(String fileName){
        File file=new File(fileName);//打开文件

        BufferedReader reader=null;
        ObservableList<String> codes=null;
        try {
            reader=new BufferedReader(new FileReader(file));//创建一个读取缓存
            codes= FXCollections.observableArrayList();//创建一个链表集合（JavaFX专用）
            String tempString=null;//创建一个字符串对象
            while((tempString=reader.readLine())!=null){    //读取文件中的一行(读取之后，下次会自动转到下一行)
                codes.add(tempString);//将当前字符串内容写入链表中
            }

            reader.close();//关闭缓存
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                reader.close();
            }catch (IOException e){
                e.printStackTrace();
            }

            return codes;
        }
    }
}
