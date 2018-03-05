package com.xt.linklistshow.util;

import com.xt.linklistshow.mode.ArrowLine;
import com.xt.linklistshow.mode.GraphMaterix;
import com.xt.linklistshow.view.custom.ListNodeView;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

public interface DrawLine {
    void DrawLine(GraphMaterix<ListNodeView> graphMaterix, ObservableList<ArrowLine> list, AnchorPane pane);
    void addLine(ListNodeView fromNode, ListNodeView toNode, AnchorPane pane);
    void clearLineList(AnchorPane pane);
}
