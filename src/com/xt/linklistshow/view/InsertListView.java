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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class InsertListView extends BaseCanve implements Initializable {

    @FXML
    private AnchorPane ListCanve;

    @FXML
    private ListView<String> CodeList;

    private ObservableList<String> codes;

    private String inputValue;

    private int index;

    ObservableList<ListNodeView> nodeArray;

    private IntegerProperty step=new SimpleIntegerProperty(1);

    private GraphMaterix<ListNodeView> graphMaterix;

    private Label p,s;

    private ListNodeView TempListNodeView;

    private ListNodeView NextListNodeView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        codes= ReadFromFile.readFileByLine("resources/InsertListCode.txt");
        CodeList.setItems(codes);

        nodeArray= FXCollections.observableArrayList();
        graphMaterix=new GraphMaterix<>(1);


        String str=createLinkListString();
        super.createList(nodeArray,graphMaterix,str);
        createInputIndex();
        createDataValue();

        super.drawList(nodeArray,graphMaterix,ListCanve);
    }


    @FXML
    public void handleNext(){
        CodeList.getSelectionModel().select(step.get());
        switch (step.get()){
            case 1:{
                step.set(step.get()+1);
                p=new Label("p");
                s=new Label("s");
            }
            break;
            case 2:{
                TempListNodeView=nodeArray.get(index);
                ListCanve.getChildren().add(p);
                p.setLayoutX(TempListNodeView.getLayoutX()+20);
                p.setLayoutY(TempListNodeView.getLayoutY()+40);
                TempListNodeView.getNode().setIsOnFuces(true);
                step.set(step.get()+1);
            }
            break;
            case 3:{
                int drop=1;
                if (TempListNodeView==null){
                    drop=4;
                }else {
                    drop=6;
                }
                step.set(drop);
            }
            break;
            case 6:{
                step.set(8);
            }
            break;
            case 8:{
                NextListNodeView=new ListNodeView();
                ListCanve.getChildren().add(NextListNodeView);
                NextListNodeView.setLayoutX(300);
                NextListNodeView.setLayoutY(100);
                ListCanve.getChildren().add(s);
                s.setLayoutX(NextListNodeView.getLayoutX()+10);
                s.setLayoutY(NextListNodeView.getLayoutY()+50);
                NextListNodeView.getNode().setIsOnFuces(true);

                step.set(step.get()+1);
            }
            break;
            case 9:{
                ListNode listNode=new ListNode();
                NextListNodeView.setNode(listNode);
                listNode.setData(inputValue);
                listNode.setIsOnFuces(true);
                graphMaterix.addVertex(NextListNodeView);
                step.set(step.get()+1);
            }
            break;
            case 10:{
                graphMaterix.addEdge(NextListNodeView, nodeArray.get(index+1));
                super.DrawLine(graphMaterix,super.getLineList(),ListCanve);
                Timeline timeline = new Timeline();
                for (int i=nodeArray.size()-1;i>index;i--){
                    timeline.setCycleCount(0);
                    timeline.setAutoReverse(false);
                    ListNodeView listNodeView=nodeArray.get(i);
                    KeyValue kv=new KeyValue(listNodeView.layoutXProperty(),listNodeView.getLayoutX()+100+30);
                    KeyFrame keyFrame=new KeyFrame(Duration.millis(1000),kv);
                    listNodeView.layoutXProperty().addListener((observable, oldValue, newValue) -> {
                        super.DrawLine(graphMaterix,super.getLineList(),ListCanve);
                    });
                    timeline.getKeyFrames().add(keyFrame);
                }

                timeline.play();
                step.set(step.get()+1);
            }
            break;
            case 11:{
                graphMaterix.deleteEdge(TempListNodeView, nodeArray.get(index+1));
                graphMaterix.addEdge(TempListNodeView,NextListNodeView);
                super.DrawLine(graphMaterix,super.getLineList(),ListCanve);
                Timeline timeline=new Timeline();
                timeline.setCycleCount(0);
                timeline.setAutoReverse(false);
                KeyValue kvX=new KeyValue(NextListNodeView.layoutXProperty(),TempListNodeView.getLayoutX()+100+30);
                KeyValue kvY=new KeyValue(NextListNodeView.layoutYProperty(), TempListNodeView.getLayoutY());
                KeyFrame kfX=new KeyFrame(Duration.millis(1000),kvX);
                KeyFrame kfY=new KeyFrame(Duration.millis(1000),kvY);
                timeline.getKeyFrames().add(kfX);
                timeline.getKeyFrames().add(kfY);
                timeline.play();
                NextListNodeView.layoutXProperty().addListener((observable, oldValue, newValue) -> {
                    super.DrawLine(graphMaterix,super.getLineList(),ListCanve);
                });
                nodeArray.add(NextListNodeView);
                step.set(step.get()+1);
            }
            break;
            case 4:
            case 12:
            {
                //将高亮删除
                if (TempListNodeView.getNode()!=null){
                    TempListNodeView.getNode().setIsOnFuces(false);
                }

                if (NextListNodeView.getNode()!=null){
                    NextListNodeView.getNode().setIsOnFuces(false);
                }

                super.ShowEndAlert();
            }
            break;
        }
    }


    private String createLinkListString(){
        TextInputDialog dialog=new TextInputDialog("abdef");
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
        TextInputDialog dialog=new TextInputDialog("2");
        dialog.setTitle("输入要添加的节点位置");
        dialog.setHeaderText("结点位置可输入0-"+(nodeArray.size()-3));

        dialog.setContentText("输入位置：");
        Optional<String> result=dialog.showAndWait();

        index=Integer.valueOf(result.get());
    }

    private void createDataValue(){
        TextInputDialog dialog=new TextInputDialog("c");
        dialog.setTitle("输入要插入的节点的值");
        dialog.setHeaderText("输入一个字符");
        dialog.setContentText("值：");
        Optional<String> result=dialog.showAndWait();

        inputValue=result.get();
    }
}