package com.xt.linklistshow.view;

import com.xt.linklistshow.mode.ArrowLine;
import com.xt.linklistshow.mode.GraphMaterix;
import com.xt.linklistshow.util.DrawLine;
import com.xt.linklistshow.view.custom.ListNodeView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;

public class BaseCanve implements DrawLine{
    private ObservableList<ArrowLine> lineList;

    public BaseCanve(){
        lineList= FXCollections.observableArrayList();
    }

    @Override
    public void DrawLine(GraphMaterix<ListNodeView> graphMaterix, ObservableList<ArrowLine> list, AnchorPane pane) {
        clearLineList(pane);
        for (ArrayList<ListNodeView> edgeList
                : graphMaterix.getVertex()){
            for (int i=1; i<edgeList.size();i++){
                addLine(edgeList.get(0),edgeList.get(i),pane);
            }
        }
    }

    @Override
    public void addLine(ListNodeView fromNode, ListNodeView toNode, AnchorPane pane) {
        double fromNodeX=fromNode.getLayoutX();
        double fromNodeY=fromNode.getLayoutY();

        double toNodeX=toNode.getLayoutX();
        double toNodeY=toNode.getLayoutY();

        double fromNodeWidth=fromNode.getWidth();
        double fromNodeHeigh=fromNode.getHeight();

        Line line=new Line();
        line.setStartX(0);
        line.setStartY(0);
        line.setEndX(toNodeX-fromNodeX-fromNodeWidth);
        line.setEndY((toNodeY-fromNodeY));
        pane.getChildren().add(line);
        line.setLayoutX(fromNodeX+fromNodeWidth);
        line.setLayoutY(fromNodeY+ fromNodeHeigh/2);

        ArrowLine arrowLine=new ArrowLine(line);

        Polygon polygon=arrowLine.getArrow();
        pane.getChildren().add(polygon);
        polygon.setLayoutX(toNodeX);
        polygon.setLayoutY(toNodeY+fromNodeHeigh/2);

        lineList.add(arrowLine);
    }

    @Override
    public void clearLineList(AnchorPane pane) {
        for (ArrowLine arrowLine :
                lineList) {
            arrowLine.remove(pane);
        }

        lineList.clear();
    }

    public ObservableList<ArrowLine> getLineList() {
        return lineList;
    }

    public void setLineList(ObservableList<ArrowLine> lineList) {
        this.lineList = lineList;
    }
}
