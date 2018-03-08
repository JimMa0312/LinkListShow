package com.xt.linklistshow.view;

import com.xt.linklistshow.mode.GraphMaterix;
import com.xt.linklistshow.mode.ListNode;
import com.xt.linklistshow.util.ReadFromFile;
import com.xt.linklistshow.view.custom.ListNodeView;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
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
import javafx.util.Duration;

import java.net.URL;
import java.util.*;


public class DeleteListView extends BaseCanve implements Initializable{

    @FXML
    private AnchorPane ListCanve;

    @FXML
    private ListView<String> CodeList;

    ObservableList<String> codes;

    Integer input;

    ObservableList<ListNodeView> nodeArray;

    private  Label s;
    private  Label p;

    private IntegerProperty step=new SimpleIntegerProperty(1);

    private ListNodeView tempListNodeView;
    private ListNodeView nextListNodeView;

    private GraphMaterix<ListNodeView> graphMaterix;    //链接矩阵

    private static final int HeadX=20;
    private static final int HeadY=20;
    private static final int DestenceX=30;
    private static final int DestenceY=40;

    public DeleteListView() {
        super();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        codes= ReadFromFile.readFileByLine("resources/DeleteListCode.txt");
        CodeList.setItems(codes);

        nodeArray=FXCollections.observableArrayList();
        graphMaterix=new GraphMaterix<>(1);

        String str=createLinkListString();
        super.createList(nodeArray,graphMaterix,str);
        createInputIndex();

        super.drawList(nodeArray,graphMaterix,ListCanve);
    }



    //数据结构插入算法理解
    @FXML
    public void handleNext(){
        CodeList.getSelectionModel().select(step.get());
        switch (step.get()){
            case 1:
                step.set(step.get()+1);
                break;
            case 2: {
                p = new Label("p");
                tempListNodeView = nodeArray.get(input);
                ListCanve.getChildren().add(p);
                p.setLayoutX(tempListNodeView.getLayoutX() + 20);
                p.setLayoutY(tempListNodeView.getLayoutY() + 40);
                step.set(step.get()+1);
            }
            break;
            case 3:
            {
                int drop=1;
                if (tempListNodeView==null){
                    drop=5;
                }else{
                    drop=7;
                }
                step.set(drop);
            }
            break;
            case 7:
            {
                int drop=1;
                if (graphMaterix.findVertex(tempListNodeView).size()<=1){
                    drop=9;
                }else{
                    drop=11;
                }
                step.set(drop);
            }
            break;
            case 11:
                step.set(13);
                break;
            case 13:
            {
                s=new Label("s");
                nextListNodeView=nodeArray.get(input+1);
                ListCanve.getChildren().add(s);
                s.setLayoutX(nextListNodeView.getLayoutX()+30);
                s.setLayoutY(nextListNodeView.getLayoutY()+30);
                graphMaterix.deleteEdge(tempListNodeView,nextListNodeView);
                super.DrawLine(graphMaterix,super.getLineList(),ListCanve);
                step.set(step.get()+1);
            }
            break;
            case 14:
            {
                Timeline timeline=new Timeline();
                timeline.setAutoReverse(false);
                timeline.setCycleCount(0);
                KeyValue kv=new KeyValue(nextListNodeView.layoutYProperty(),70);
                KeyFrame kf=new KeyFrame(Duration.millis(1500),kv);

                timeline.getKeyFrames().add(kf);
                timeline.play();
                nextListNodeView.layoutYProperty().addListener((observable, oldValue, newValue) -> {
                    super.DrawLine(graphMaterix,super.getLineList(),ListCanve);
                });
                graphMaterix.addEdge(tempListNodeView,graphMaterix.findVertex(nextListNodeView).get(1));
                super.DrawLine(graphMaterix,super.getLineList(),ListCanve);
                step.set(step.get()+1);
            }
            break;
            case 15:
            {
                ListCanve.getChildren().remove(nextListNodeView);
                graphMaterix.deleteVertex(nextListNodeView);
                super.DrawLine(graphMaterix,super.getLineList(),ListCanve);
                nodeArray.remove(nextListNodeView);
                ListCanve.getChildren().remove(s);

                step.set(step.get()+1);
            }
            break;
        }
    }

    private String createLinkListString(){
        TextInputDialog dialog=new TextInputDialog("abcdef");
        dialog.setTitle("创建新链表");
        dialog.setHeaderText("输入一个字符串（长度不能大于6个）");
        dialog.setContentText("输入字符串：");
        Optional<String> result=dialog.showAndWait();
        if (result.isPresent()){
            System.out.println(result.get());
            return result.get();
        }else{
            return null;
        }
    }

    /**
     * 设置index的值
     */
    private void createInputIndex(){
        TextInputDialog dialog=new TextInputDialog("0");
        dialog.setTitle("输入要删除的结点位置");
        dialog.setHeaderText("结点位置可输入0-"+(nodeArray.size()-2));

        dialog.setContentText("输入位置");
        Optional<String> result=dialog.showAndWait();

        input=Integer.valueOf(result.get());
    }


}