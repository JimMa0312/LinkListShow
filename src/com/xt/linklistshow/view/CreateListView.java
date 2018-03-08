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

/**
 * 创建链表演示 试图的控制器类
 */
public class CreateListView extends BaseCanve implements Initializable{

    @FXML
    private AnchorPane ListCanve; //展示链表模拟的布局对象

    @FXML
    private ListView<String> CodeList; //用于存储展示的Java操作代码

    ObservableList<String> codes; //展示代码的控件，绑定CodeList后一起使用

    Queue<String> inputbuffer;//用于存储一个字符串，将按照该存储的字符串创建模拟链表

    ObservableList<ListNodeView> nodeArray;//用来存储节点

    private Label s;//临时指针视图
    private Label r;//末尾指针视图

    private IntegerProperty step=new SimpleIntegerProperty(1); //存储当前运行CodeList中第几行代码

    private ListNodeView tempListNodeView; //临时节点对象

    private GraphMaterix<ListNodeView> graphMaterix;    //链接矩阵

    /**
     * 构造函数
     * 对一些成员变量进行初始化
     */
    public CreateListView() {
        super();
        inputbuffer=new LinkedList<>();
        nodeArray= FXCollections.observableArrayList();
        graphMaterix=new GraphMaterix<>(1);
    }

    /**
     * JavaFx初始化
     * 将先于构造函数执行
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        codes= ReadFromFile.readFileByLine("resources/CreateListCode.txt");//读取文件，将展示代码按行存储到codes链表中
        CodeList.setItems(codes);//对CodeList控件绑定codes
        CheckInputBuffer();//检查输入队列
        //TODO
//        pathTransition();
    }

    /**
     * 触发（Event）方法
     * 核心代码
     *
     */
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

    /**
     * 检查输入队列的方法
     *
     * 如果队列为空，需将输入一个字符串进行存储
     */
    private void CheckInputBuffer(){
        if (inputbuffer.isEmpty()){
            createInputBuffer();
        }
    }

    /**
     * 创建输入字符串
     */
    private void createInputBuffer(){
        //新建一个可输入的对话框（Dialog）
        TextInputDialog dialog=new TextInputDialog("abcdef");
        dialog.setTitle("创建新链表");
        dialog.setHeaderText("输入一个字符串（长度不能大于6个）");
        dialog.setContentText("输入字符串：");

        Optional<String> result=dialog.showAndWait(); //创建一个接收器，接收一个字符串，当对话框关闭时，会返回该字符串
        if (result.isPresent()){//如果字符串有效
            System.out.println(result.get());
            CreateInputString(result.get());//将字符串放入队列中
        }
    }

    /**
     * 创建输入队列
     * @param inputStr 需要存储的字符串
     */
    private void CreateInputString(String inputStr){
        //将字符串进行遍历，提取单个字符，再将字符存储到队列中
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
