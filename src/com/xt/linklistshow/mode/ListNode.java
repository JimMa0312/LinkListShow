package com.xt.linklistshow.mode;

import javafx.beans.property.*;
import javafx.scene.Node;
import javafx.scene.control.Label;

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

    public void addDataLisener(Label node){
        data.addListener((observable, oldValue, newValue) -> {
            node.setText(data.get());
        });
    }

    public void addIsConnectionListener(Label node){
        isConntectNull.addListener((observable, oldValue, newValue) -> {
            if (isConntectNull.get()==0){
                node.setText("V");
            }else {
                node.setText("");
            }
        });
    }
}