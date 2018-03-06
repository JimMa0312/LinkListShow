package com.xt.linklistshow.view;

import com.xt.linklistshow.mode.GraphMaterix;
import com.xt.linklistshow.mode.ListNode;
import com.xt.linklistshow.util.DrawLine;
import com.xt.linklistshow.util.ReadFromFile;
import com.xt.linklistshow.view.custom.ListNodeView;
import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;
import org.w3c.dom.NodeList;

import java.net.URL;
import java.util.*;

public class CreateListView extends BaseCanve implements Initializable{

    @FXML
    private AnchorPane ListCanve;

    @FXML
    private ListView<String> CodeList;

    ObservableList<String> codes;

    Queue<String> inputbuffer;

    ObservableList<ListNodeView> nodeArray;

    private Label s;//临时指针视图
    private Label r;//末尾指针视图

    private IntegerProperty step=new SimpleIntegerProperty(1);

    private ListNodeView tempListNodeView;

    private GraphMaterix<ListNodeView> graphMaterix;    //链接矩阵

    private static final int HeadX=20;
    private static final int HeadY=20;
    private static final int DestenceX=30;
    private static final int DestenceY=40;

    public CreateListView() {
        super();
        inputbuffer=new LinkedList<>();
        nodeArray= FXCollections.observableArrayList();
        graphMaterix=new GraphMaterix<>(1);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        codes= ReadFromFile.readFileByLine("resources/CreateListCode.txt");
        CodeList.setItems(codes);
        CheckInputBuffer();
        //TODO
//        pathTransition();
    }

    @FXML
    public void handleNext(){
        switch (step.get()) {
            case 0:{
                if (ListCanve.getChildren().size()<=0){
                    clearAllInCanve();
                }
                step.set(step.get()+1);
            }
            break;
            case 1: {

                CodeList.getSelectionModel().select(step.get());
                ListNodeView nodeView = new ListNodeView();
                ListCanve.getChildren().add(nodeView);
                nodeView.setLayoutX(HeadX);
                nodeView.setLayoutY(HeadY);
                nodeArray.add(nodeView);
                graphMaterix.addVertex(nodeView);
                step.set(step.get()+1);
            }
            break;
            case 2:
            case 3:
            case 4: {
                CodeList.getSelectionModel().select(step.get());
                step.set(step.get()+1);
            }
            break;
            case 5: {
                CodeList.getSelectionModel().select(step.get());
                r = new Label("L");
                ListCanve.getChildren().add(r);
                r.setLayoutX(HeadY + 80);
                r.setLayoutY(HeadX + 30);
                step.set(step.get()+1);
            }
            break;
            case 6:
            {
                CodeList.getSelectionModel().select(step.get());
                if (inputbuffer.size()>0){
                    step.set(step.get()+1);
                }else{
                    step.set(14);
                }
            }
            break;
            case 7:
            {
                CodeList.getSelectionModel().select(step.get());
                ListNode tempListNode=new ListNode();
                tempListNodeView=new ListNodeView(tempListNode);
                ListCanve.getChildren().add(tempListNodeView);
                double x= nodeArray.get(nodeArray.size()-1).getLayoutX();
                double y=nodeArray.get(nodeArray.size()-1).getLayoutY();
                nodeArray.add(tempListNodeView);
                tempListNodeView.setLayoutX(x+DestenceX+100);
                tempListNodeView.setLayoutY(y);

                graphMaterix.addVertex(tempListNodeView);

                step.set(step.get()+1);
            }
            break;
            case 8:
            {
                CodeList.getSelectionModel().select(step.get());
                tempListNodeView.getNode().setData(inputbuffer.poll());
                step.set(step.get()+1);
            }
            break;
            case 9:
            {
                CodeList.getSelectionModel().select(step.get());
                ListNodeView fromNode=nodeArray.get(nodeArray.indexOf(tempListNodeView)-1);
                graphMaterix.addEdge(fromNode,tempListNodeView);
                DrawLine(graphMaterix,super.getLineList(), ListCanve);
                fromNode.getNode().setIsConntectNull(1);
                step.set(step.get()+1);
            }
            break;
            case 10:
            {
                CodeList.getSelectionModel().select(step.get());
                r.setLayoutX(20+tempListNodeView.getLayoutX());
                r.setLayoutY(30+tempListNodeView.getLayoutY());
                step.set(step.get()+1);
            }
            break;
            case 11:
            {
                CodeList.getSelectionModel().select(step.get());
                step.set(6);
            }
            break;
            case 14:
                {
                    CodeList.getSelectionModel().select(step.get());
                    Alert alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("完成");
                    alert.setHeaderText("创建链表演示完成！");
                    alert.showAndWait();
                }
                break;
        }
    }

    /**
     * demo
     */
    private void pathTransition(){
        Rectangle rectPath=new Rectangle(0,0,40,40);
        rectPath.setArcHeight(10);
        rectPath.setArcWidth(10);
        rectPath.setFill(Color.ORANGE);
        ListCanve.getChildren().add(rectPath);

        Path path=new Path();
        path.getElements().add(new MoveTo(20,20));
        path.getElements().add(new CubicCurveTo(380,0,380,120,200,120));
        path.getElements().add(new CubicCurveTo(0,120,0,240,380,240));
        PathTransition pathTransition=new PathTransition();
        pathTransition.setDuration(Duration.millis(4000));
        pathTransition.setPath(path);
        pathTransition.setNode(rectPath);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(Timeline.INDEFINITE);
        pathTransition.setAutoReverse(true);
        pathTransition.play();
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
