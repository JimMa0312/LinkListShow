package com.xt.linklistshow.view.custom;

import com.xt.linklistshow.mode.ListNode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ListNodeView extends Pane implements Initializable {

    @FXML
    private Pane onFuces;

    @FXML
    private Label data;

    @FXML
    private Label isNull;

    private ListNode node;

    public ListNodeView(){
        loadFxml();
        node=new ListNode();
        drawNode();
        node.addDataLisener(data);
        node.addIsConnectionListener(isNull);
        node.addIsOnFouce(onFuces);
    }

    public ListNodeView(ListNode node){
        loadFxml();
        this.node=node;
        drawNode();
        node.addDataLisener(data);
        node.addIsConnectionListener(isNull);
        node.addIsOnFouce(onFuces);
    }

    public ListNode getNode() {
        return node;
    }

    public void setNode(ListNode node) {
        this.node = node;
        drawNode();
        node.addDataLisener(data);
        node.addIsConnectionListener(isNull);
        node.addIsOnFouce(onFuces);
    }

    private void loadFxml(){
        FXMLLoader loader=new FXMLLoader(getClass().getResource("ListNode.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * 绘制最原始的节点图象
     */
    private void drawNode(){
        if (node!=null){
            if (node.getIsOnFuces()){
                onFuces.setStyle("-fx-background-color: #ffff00");
            }else{
                onFuces.setStyle("-fx-background-color: #00ff00");
            }
            data.setText(node.getData());
            if (node.getIsConntectNull()==0){
                isNull.setDisable(false);
            }
        }
    }
}
