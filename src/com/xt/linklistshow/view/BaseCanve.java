package com.xt.linklistshow.view;

import com.xt.linklistshow.mode.ArrowLine;
import com.xt.linklistshow.mode.GraphMaterix;
import com.xt.linklistshow.mode.ListNode;
import com.xt.linklistshow.util.DrawLine;
import com.xt.linklistshow.view.custom.ListNodeView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;

/**
 * 控制器基础类
 * 包含关于一次性创建链表（需要模拟的链表）方法,
 * 关于连接线的操作等
 */
public class BaseCanve implements DrawLine{

    public static final int HeadX=20;
    public static final int HeadY=20;
    public static final int DestenceX=30;
    public static final int DestenceY=40;


    private ObservableList<ArrowLine> lineList;

    public BaseCanve(){
        lineList= FXCollections.observableArrayList();
    }

    /**
     * 绘制箭头线
     * 先删除删除所有链接线，
     * 之后再根据链接存储结构（GraphMaterix）进行绘制（生成ArrowLine对象），
     * 将新生成的ArrowLine对象让如存储数组进行存储
     * @param graphMaterix 需要扫描的链接存储结构对象
     * @param list 需要存储箭头线的数组
     * @param pane 需要绘制的布局
     */
    @Override
    public void DrawLine(GraphMaterix<ListNodeView> graphMaterix, ObservableList<ArrowLine> list, AnchorPane pane) {
        clearLineList(pane,list);
        for (ArrayList<ListNodeView> edgeList
                : graphMaterix.getVertex()){
            for (int i=1; i<edgeList.size();i++){
                addLine(edgeList.get(0),edgeList.get(i),pane);
            }
        }
    }
    /**
     * 添加连接线
     *
     * 获取始节点和终节点的x，y坐标，以及宽度和长度，
     * 生成ArrowList对象
     * 将该对象添加到布局中
     * 将对象加入List中进行存储
     *
     * @param fromNode
     * @param toNode
     * @param pane
     */
    @Override
    public void addLine(ListNodeView fromNode, ListNodeView toNode, AnchorPane pane) {
        double fromNodeX=fromNode.getLayoutX();
        double fromNodeY=fromNode.getLayoutY();

        double toNodeX=toNode.getLayoutX();
        double toNodeY=toNode.getLayoutY();

        double fromNodeWidth=100;
        double fromNodeHeigh=30;

        /**
         * 创建连接线
         * 并将线放入Pane中
         */
        Line line=new Line();
        line.setStartX(0);
        line.setStartY(0);
        line.setEndX(toNodeX-fromNodeX-fromNodeWidth);
        line.setEndY((toNodeY-fromNodeY));
        pane.getChildren().add(line);
        line.setLayoutX(fromNodeX+fromNodeWidth);
        line.setLayoutY(fromNodeY+ fromNodeHeigh/2);

        /**
         * 创建ArrowLine
         */
        ArrowLine arrowLine=new ArrowLine(line);

        //在创建ArrowLine对象时，计算出三角形相应的参数，对三角形进行获取
        Polygon polygon=arrowLine.getArrow();
        //将三角形添加到Pane中
        pane.getChildren().add(polygon);
        polygon.setLayoutX(toNodeX);
        polygon.setLayoutY(toNodeY+fromNodeHeigh/2);

        /**
         * 将arrowLine对象加入到数组中，方便以后操作
         */
        lineList.add(arrowLine);
    }

    /**
     * 清除连接线
     * @param pane 需要进行清除的
     * @param list 需要清除的数组
     */
    @Override
    public void clearLineList(AnchorPane pane, ObservableList<ArrowLine> list) {
        //遍历数组，将所有的ArrowLine从Pane中删除
        if (list.size()>=1) {
            for (ArrowLine arrowLine :
                    list) {
                arrowLine.remove(pane);
            }
        }

        //清除链表
        list.clear();
    }

    public ObservableList<ArrowLine> getLineList() {
        return lineList;
    }

    public void setLineList(ObservableList<ArrowLine> lineList) {
        this.lineList = lineList;
    }

    /**
     * 创建链表
     *
     * @param list  一个新的空链表对象
     * @param graphMaterix 一个新的节点关系存储结构对象
     * @param input 建立链表需要的数据
     */
    public void createList(ObservableList<ListNodeView> list, GraphMaterix<ListNodeView> graphMaterix, String input){

        //添加Head
        ListNodeView nodeView=new ListNodeView();
        list.add(nodeView);

        graphMaterix.addVertex(nodeView);

        ListNodeView s=nodeView;

        //将input中的所有字符存储在list
        for (int i = 0; i<input.length();i++){
            ListNode listNode=new ListNode(0, String.valueOf(input.charAt(i)), false);//新建一个ListNode对象 将input[i]位置上的字符存入对象中
            ListNodeView tempNodeView=new ListNodeView(listNode);//将新创建的ListNode对象放入新建的ListNodeView对象中

            list.add(tempNodeView);//将新建的视图对象放入链表中
            graphMaterix.addVertex(tempNodeView);//在关系对象中添加该新建的节点
            graphMaterix.addEdge(s,tempNodeView); //添加一两个节点相互连接的数据记录。 模拟链表中*next
            s.getNode().setIsConntectNull(1);
            s=tempNodeView;
        }
    }

    /**
     * 在指定的布局中绘制一个链表
     * @param list 需要绘制的链表
     * @param graphMaterix 需要绘制链表的节点关系对象
     * @param pane 需要被绘制布局
     */
    public void drawList(ObservableList<ListNodeView> list, GraphMaterix<ListNodeView> graphMaterix, AnchorPane pane){
        if (list==null || list.size()<=0){//当该链表未被创建，或者长度为0时，直接跳出该方法。
            return;
        }

        for (int i=0;i<list.size();i++){
            ListNodeView tempView=list.get(i);
            pane.getChildren().add(tempView);//将list[i]位置上的节点视图 放入到布局中，进行绘制

            if (i==0){
                //设置头节点的位置
                tempView.setLayoutX(HeadX);
                tempView.setLayoutY(HeadY);
            }else{
                //获取上个节点的位置
                double x= list.get(i-1).getLayoutX();
                double y=list.get(i-1).getLayoutY();
                //设置除头节点以外节点的位置
                tempView.setLayoutX(x+ DestenceX + 100);//100是节点视图的长度
                tempView.setLayoutY(y);
            }
        }

        //绘制所有的箭头线

        DrawLine(graphMaterix,lineList,pane);
    }
    
    public ListNodeView findNode(ObservableList<ListNodeView> nodeArray){
        ListNodeView node=null;

        for (ListNodeView nodeView :
                nodeArray) {
            
        }
        
        return null;
    }

    public void ShowEndAlert(){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("演示完成");
        alert.setHeaderText("演示完成");
        alert.showAndWait();
    }
}
