package com.xt.linklistshow.mode;

import javafx.beans.property.*;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class ListNode {
    private IntegerProperty isConntectNull;
    private StringProperty data;
    private BooleanProperty isOnFuces;

    public ListNode(){
        this(0,"NULL",false);
    }

    public ListNode(int conntectNull, String data, boolean isOnFuces){
        this.isConntectNull=new SimpleIntegerProperty(conntectNull);
        this.data=new SimpleStringProperty(data);
        this.isOnFuces=new SimpleBooleanProperty(isOnFuces);
    }

    public void setIsConntectNull(int isConntectNull){
        this.isConntectNull.set(isConntectNull);
    }

    public void setData(String data){
        this.data.set(data);
    }

    public void setIsOnFuces(boolean onFuces){
        this.isOnFuces.set(onFuces);
    }

    public int getIsConntectNull(){
        return isConntectNull.get();
    }

    public String getData(){
        return data.get();
    }

    public boolean getIsOnFuces(){
        return isOnFuces.get();
    }

    /**
     * 添加一个监听Data变量的监听器
     * 当Data的数据被set后，会更改相关的Label中Text的值
     *
     * @param node
     */
    public void addDataLisener(Label node){
        data.addListener((observable, oldValue, newValue) -> {
            node.setText(data.get());
        });
    }

    /**
     * 添加一个监听IsConnectionNull变量的监听器
     *
     * 当值改为1时：label中Text变为空
     * 当值改为0时：label中Text变为“V”
     *
     * @param node
     */
    public void addIsConnectionListener(Label node){
        isConntectNull.addListener((observable, oldValue, newValue) -> {
            if (isConntectNull.get()==0){
                node.setText("V");
            }else {
                node.setText("");
            }
        });
    }

    /**
     * 添加一个监听IsOnFouce变量的监听器
     *
     * 当值为真时：pane的背景变成黄色
     * 当值为假时：pane的背景变成绿色
     *
     * @param node
     */
    public void addIsOnFouce(Pane node){
        isOnFuces.addListener((observable, oldValue, newValue) -> {
            if (isOnFuces.get()){
                node.setStyle("-fx-background-color: #ffff00");
            }else{
                node.setStyle("-fx-background-color: #00ff00");
            }
        });
    }
}