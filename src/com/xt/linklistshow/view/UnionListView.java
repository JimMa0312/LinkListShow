package com.xt.linklistshow.view;

import com.sun.istack.internal.localization.NullLocalizable;
import com.xt.linklistshow.mode.ArrowLine;
import com.xt.linklistshow.mode.GraphMaterix;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class UnionListView extends BaseCanve implements Initializable{
    @FXML
    private AnchorPane ListCanveA;

    @FXML
    private AnchorPane ListCanveB;

    @FXML
    private AnchorPane ListCanveC;

    @FXML
    private ListView<String> CodeList;

    private ObservableList<String> codes;

    private IntegerProperty step=new SimpleIntegerProperty(1);

    private Label p,q,s;


    private GraphMaterix<ListNodeView> graphMaterixA;
    private GraphMaterix<ListNodeView> graphMaterixB;
    private GraphMaterix<ListNodeView> graphMaterixC;

    private ObservableList<ArrowLine> arrowLinesA;
    private ObservableList<ArrowLine> arrowLinesB;
    private ObservableList<ArrowLine> arrowLinesC;

    private ObservableList<ListNodeView> nodeArrayA;
    private ObservableList<ListNodeView> nodeArrayB;
    private ObservableList<ListNodeView> nodeArrayC;

    private ListNodeView tempNodeViewA;
    private ListNodeView tempNodeViewB;
    private ListNodeView tempNodeViewC;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        codes= ReadFromFile.readFileByLine("resources/UnionListCode.txt");
        CodeList.setItems(codes);

        CreateThreeLists();
    }

    /**
     * 根据算法将两个链表合并；并且降序排序
     */
    @FXML
    public void handleNext(){
        CodeList.getSelectionModel().select(step.get());

        switch (step.get()){
            case 1:{
                step.set(step.get()+1);
            }
            break;
            case 2:{
                p=new Label("p");
                q=new Label("q");
                s=new Label("s");
                step.set(step.get()+1);
                ListCanveC.getChildren().add(s);
            }
            break;
            case 3:{
                ListCanveA.getChildren().add(p);
                tempNodeViewA=nodeArrayA.get(1);
                p.setLayoutX(tempNodeViewA.getLayoutX()+10);
                p.setLayoutY(tempNodeViewA.getLayoutY()+40);

                tempNodeViewA.getNode().setIsOnFuces(true);

                ListCanveB.getChildren().add(q);
                tempNodeViewB=nodeArrayB.get(1);
                q.setLayoutX(tempNodeViewB.getLayoutX()+10);
                q.setLayoutY(tempNodeViewB.getLayoutY()+40);

                tempNodeViewB.getNode().setIsOnFuces(true);
                step.set(step.get()+1);
            }
            break;
            case 4:{
                step.set(step.get()+1);
            }
            break;
            case 5:{
                ListNodeView headNodeView=new ListNodeView();
                nodeArrayC.add(headNodeView);
                graphMaterixC.addVertex(headNodeView);
                ListCanveC.getChildren().add(headNodeView);

                headNodeView.setLayoutX(HeadX);
                headNodeView.setLayoutY(HeadY);
                step.set(step.get()+1);
            }
            break;
            case 6:{
                int drop=1;
                if (tempNodeViewA== null || tempNodeViewB==null){
                    drop=14;
                }else {
                    drop=7;
                }

                step.set(drop);
            }
            break;
            case 7:{
                int drop=1;
                if (tempNodeViewA.getNode().getData().compareTo(tempNodeViewB.getNode().getData())<0){
                    drop=8;
                }else {
                    drop=10;
                }

                step.set(drop);
            }
            break;

            case 8:{
                tempNodeViewA=ChangePointer(p,tempNodeViewA, nodeArrayA);
                step.set(11);
            }
            break;
            case 10:{
                tempNodeViewB=ChangePointer(q,tempNodeViewB, nodeArrayB);
                step.set(11);
            }
            break;
            case 11:{
                if (nodeArrayC.size()<=1){
                    nodeArrayC.add(tempNodeViewC);
                    graphMaterixC.addVertex(tempNodeViewC);
                }else {
                    Timeline timeline=new Timeline();
                    timeline.setCycleCount(0);
                    timeline.setAutoReverse(false);
                    for (int i=nodeArrayC.size()-1; i>=1;i--){
                    ListNodeView tempListNodeView=nodeArrayC.get(i);
                    KeyValue kvX=new KeyValue(tempListNodeView.layoutXProperty(),tempListNodeView.getLayoutX()+130);
                    KeyFrame kfX=new KeyFrame(Duration.millis(1000),kvX);
                    timeline.getKeyFrames().add(kfX);
                    tempListNodeView.layoutXProperty().addListener((observable, oldValue, newValue) -> {
                        super.DrawLine(graphMaterixC,super.getLineList(),ListCanveC);
                    });
                }
                timeline.play();
                nodeArrayC.add(1,tempNodeViewC);
                    graphMaterixC.addVertex(tempNodeViewC);
                    graphMaterixC.addEdge(tempNodeViewC,nodeArrayC.get(2));
                    tempNodeViewC.getNode().setIsConntectNull(1);
                }
                if (graphMaterixC.getEdgeNum()>0) {
                    super.DrawLine(graphMaterixC, arrowLinesC, ListCanveC);
            }
                step.set(step.get()+1);
            }
            break;
            case 12:{
                if (nodeArrayC.size()>2){
                    graphMaterixC.deleteEdge(nodeArrayC.get(0),nodeArrayC.get(2));
                }
                graphMaterixC.addEdge(nodeArrayC.get(0),tempNodeViewC);
                nodeArrayC.get(0).getNode().setIsConntectNull(1);
                super.DrawLine(graphMaterixC,super.getLineList(),ListCanveC);
                tempNodeViewC.layoutYProperty().addListener((observable, oldValue, newValue) -> {
                    s.setLayoutX(tempNodeViewC.getLayoutX()+10);
                    s.setLayoutY(tempNodeViewC.getLayoutY()+40);
                    super.DrawLine(graphMaterixC,super.getLineList(),ListCanveC);
                });
                movePointer(tempNodeViewC, nodeArrayC.get(0).getLayoutX()+130, nodeArrayC.get(0).getLayoutY());

                step.set(6);
            }
            break;
            case 14:{
                if (tempNodeViewA==null){
                    ListCanveA.getChildren().remove(p);
                    ListCanveB.getChildren().add(p);
                    p.setLayoutX(q.getLayoutX());
                    p.setLayoutY(q.getLayoutY());
                    ListCanveB.getChildren().remove(q);
                    tempNodeViewA=tempNodeViewB;
                }
                step.set(step.get()+1);
            }
            break;
            case 15:{
                int drop=1;
                if (tempNodeViewA==null){
                    drop=20;
                }else{
                    drop=16;
                }

                step.set(drop);
            }
            break;

            case 16:
            {
                tempNodeViewA=ChangePointer(p,tempNodeViewA, nodeArrayA);
                step.set(17);
            }
            break;

            case 17:
            {
                if (nodeArrayC.size()<=1){
                    nodeArrayC.add(tempNodeViewC);
                    graphMaterixC.addVertex(tempNodeViewC);
                }else {
                    Timeline timeline=new Timeline();
                    timeline.setCycleCount(0);
                    timeline.setAutoReverse(false);
                    for (int i=nodeArrayC.size()-1; i>=1;i--){
                        ListNodeView tempListNodeView=nodeArrayC.get(i);
                        KeyValue kvX=new KeyValue(tempListNodeView.layoutXProperty(),tempListNodeView.getLayoutX()+130);
                        KeyFrame kfX=new KeyFrame(Duration.millis(1000),kvX);
                        timeline.getKeyFrames().add(kfX);
                        tempListNodeView.layoutXProperty().addListener((observable, oldValue, newValue) -> {
                            super.DrawLine(graphMaterixC,super.getLineList(),ListCanveC);
                        });
                    }
                    timeline.play();
                    nodeArrayC.add(1,tempNodeViewC);
                    graphMaterixC.addVertex(tempNodeViewC);
                    graphMaterixC.addEdge(tempNodeViewC,nodeArrayC.get(2));
                    tempNodeViewC.getNode().setIsConntectNull(1);
                }
                if (graphMaterixC.getEdgeNum()>0) {
                    super.DrawLine(graphMaterixC, arrowLinesC, ListCanveC);
                }
                step.set(step.get()+1);
            }
            break;
            case 18:{
                if (nodeArrayC.size()>2){
                    graphMaterixC.deleteEdge(nodeArrayC.get(0),nodeArrayC.get(2));
                }
                graphMaterixC.addEdge(nodeArrayC.get(0),tempNodeViewC);
                nodeArrayC.get(0).getNode().setIsConntectNull(1);
                super.DrawLine(graphMaterixC,super.getLineList(),ListCanveC);
                tempNodeViewC.layoutYProperty().addListener((observable, oldValue, newValue) -> {
                    s.setLayoutX(tempNodeViewC.getLayoutX()+10);
                    s.setLayoutY(tempNodeViewC.getLayoutY()+40);
                    super.DrawLine(graphMaterixC,super.getLineList(),ListCanveC);
                });
                movePointer(tempNodeViewC, nodeArrayC.get(0).getLayoutX()+130, nodeArrayC.get(0).getLayoutY());

                step.set(15);
            }
            break;
            case 20:{
                super.ShowEndAlert();
            }
        }
    }

    private ListNodeView ChangePointer(Label p, ListNodeView listNodeView, ObservableList<ListNodeView> nodeArray){
        tempNodeViewC=new ListNodeView(listNodeView.getNode().getData());
        if (nodeArray.indexOf(listNodeView)==nodeArray.size()-1) {
            listNodeView=null;
        }else {
            listNodeView.getNode().setIsOnFuces(false);
            listNodeView = nodeArray.get(nodeArray.indexOf(listNodeView) + 1);
            listNodeView.getNode().setIsOnFuces(true);
            movePointer(p, listNodeView.getLayoutX()+10, listNodeView.getLayoutY()+40);

        }
        MoveNodeToC();
        return listNodeView;
    }

    private void movePointer(Node node, double x, double y){
        Timeline timeline=new Timeline();
        timeline.setCycleCount(0);
        timeline.setAutoReverse(false);
        KeyValue kvX=new KeyValue(node.layoutXProperty(),x);
        KeyValue kvY=new KeyValue(node.layoutYProperty(),y);
        KeyFrame kfX=new KeyFrame(Duration.millis(1000), kvX);
        KeyFrame kfY=new KeyFrame(Duration.millis(1000),kvY);
        timeline.getKeyFrames().add(kfX);
        timeline.getKeyFrames().add(kfY);

        timeline.play();
    }

    private void MoveNodeToC(){
        ListCanveC.getChildren().add(tempNodeViewC);
        tempNodeViewC.setLayoutX(nodeArrayC.get(nodeArrayC.size()-1).getLayoutX()+100+30);
        tempNodeViewC.setLayoutY(100);
        s.setLayoutX(tempNodeViewC.getLayoutX()+10);
        s.setLayoutY(tempNodeViewC.getLayoutY()+40);
        step.set(11);
    }


    /**
     * 初始化（创建）三个链表存储结构
     * 前两个链表具有数据
     * 最后一个链表只进行初始化
     */
    public void CreateThreeLists(){
        nodeArrayA= FXCollections.observableArrayList();
        graphMaterixA=new GraphMaterix<>(1);
        String str=createLinkListString("acf");
        super.createList(nodeArrayA, graphMaterixA, str);

        super.drawList(nodeArrayA,graphMaterixA,ListCanveA);

        nodeArrayB=FXCollections.observableArrayList();
        graphMaterixB=new GraphMaterix<>(1);
        str=createLinkListString("bde");
        super.createList(nodeArrayB,graphMaterixB,str);

        super.drawList(nodeArrayB,graphMaterixB,ListCanveB);

        nodeArrayC=FXCollections.observableArrayList();
        graphMaterixC=new GraphMaterix<>(1);
        arrowLinesC= FXCollections.observableArrayList();
    }

    private String createLinkListString(String str){
        TextInputDialog dialog=new TextInputDialog(str);
        dialog.setTitle("创建新链表");
        dialog.setHeaderText("输入一个有序字符串（长度不能大于4个）");
        dialog.setContentText("输入字符串：");
        Optional<String> result=dialog.showAndWait();
        if (result.isPresent()){
            System.out.println(result.get());
            return result.get();
        }else{
            return null;
        }
    }
}
