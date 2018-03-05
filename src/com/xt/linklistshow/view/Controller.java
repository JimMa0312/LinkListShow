package com.xt.linklistshow.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void OpenCreateListView() throws Exception{
        Parent createListRoot= FXMLLoader.load(getClass().getResource("CreateListView.fxml"));
        Stage stage=new Stage();
        stage.setTitle("创建链表");
        stage.setScene(new Scene(createListRoot));
        stage.show();
    }
}