package com.xt.linklistshow.mode;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

/**
 * 箭头线
 */
public class ArrowLine {
    private Line line;
    private Polygon arrow;  //箭头

    public ArrowLine(){
        line=new Line();
        arrow=new Polygon();
    }

    public ArrowLine(Line line) {
        this.line = line;
        drawArrow();
    }

    public ArrowLine(Line line, Polygon arrow) {
        this.line = line;
        this.arrow = arrow;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public Polygon getArrow() {
        return arrow;
    }

    public void setArrow(Polygon arrow) {
        this.arrow = arrow;
    }

    /**
     * 绘制箭头
     */
    private void drawArrow(){
        double angle=Math.atan((line.getEndY()-line.getStartY())/(line.getEndX()-line.getStartX()));//获取线段两点之间夹角弧度
        double newAngle=(angle+Math.PI)%(2*Math.PI);

        double hypotenuse=10;//设置斜边长度
        Polygon polygon=new Polygon();
        ObservableList<Double> list =polygon.getPoints();

        list.add(0.0);//设定原点X
        list.add(0.0);//设定原点Y

        //弧度分配
        double aAngle=newAngle-Math.toRadians(20);
        double bAngle=newAngle+Math.toRadians(20);

        //极坐标系转笛卡尔坐标
        double aX=hypotenuse * Math.cos(aAngle);
        double aY=hypotenuse * Math.sin(aAngle);
        double bX=hypotenuse * Math.cos(bAngle);
        double bY=hypotenuse * Math.sin(bAngle);

        list.add(aX);
        list.add(aY);
        list.add(bX);
        list.add(bY);

        arrow=polygon;
    }

    /**
     * 删除箭头线
     * @param pane 当前存放的父亲节点
     * @return
     */
    public boolean remove(Pane pane){
        boolean removed=false;
        removed=pane.getChildren().remove(line);
        removed &= pane.getChildren().remove(arrow);

        return removed;
    }
}
