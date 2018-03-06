package com.xt.linklistshow.view;

import com.xt.linklistshow.mode.GraphMaterix;
import com.xt.linklistshow.util.ReadFromFile;
import com.xt.linklistshow.view.custom.ListNodeView;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.ResourceBundle;


public class DeleteListView extends BaseCanve implements Initializable{

    @FXML
    private AnchorPane ListCanve;

    @FXML
    private ListView<String> CodeList;

    ObservableList<String> codes;

    Queue<String> inputbuffer;

    ObservableList<ListNodeView> nodeArray;

    private  Label s;
    private  Label p;

    private IntegerProperty step=new SimpleIntegerProperty(1);

    private ListNodeView tempListNodeView;

    private GraphMaterix<ListNodeView> graphMaterix;    //链接矩阵

    private static final int HeadX=20;
    private static final int HeadY=20;
    private static final int DestenceX=30;
    private static final int DestenceY=40;

    public DeleteListView() {
        super();
        inputbuffer=new LinkedList<>();
        nodeArray= FXCollections.observableArrayList();
        graphMaterix=new GraphMaterix<>(1);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        codes= ReadFromFile.readFileByLine("resources/DeleteListCode.txt");
        CodeList.setItems(codes);
        CheckInputBuffer();
    }



    //数据结构插入算法理解
    public  void handleNext(){
        switch(step.get()){
            case 0:{
                if (ListCanve.getChildren().size()<=0){
                     clearAllInCanve();
                }
                step.set(step.get()+1);
            }
            break;
            case 1:{

                CodeList.getSelectionModel().select(step.get());
                ListNodeView nodeView = new ListNodeView();
                ListCanve.getChildren().add(nodeView);

            }
        }
    }

    private void CheckInputBuffer(){
        if (inputbuffer.isEmpty()){
            createInputBuffer();
        }
    }

    private void createInputBuffer(){
        TextInputDialog dialog=new TextInputDialog("abcdef");
        dialog.setTitle("创建新链表");
        dialog.setHeaderText("输入一个字符串（长度不能大于6个）");
        dialog.setContentText("输入字符串：");
        Optional<String> result=dialog.showAndWait();
        if (result.isPresent()){
            System.out.println(result.get());
            CreateInputString(result.get());
        }
       /* dialog.setTitle(" 删除结点");
        dialog.setHeaderText("输入一位整数代表删除结点的位置");
        dialog.setContentText("输入整数：");     */

    }

    private void CreateInputString(String inputStr){
        for (int i=0;i<inputStr.length();i++){
            inputbuffer.add(String.valueOf(inputStr.charAt(i)));
        }
    }

    private void clearAllInCanve(){
        ListCanve.getChildren().removeAll();
        nodeArray= FXCollections.observableArrayList();
        graphMaterix=new GraphMaterix<>(1);
        step.set(1);
    }


}
