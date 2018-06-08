package com.dataway.page.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import lombok.Data;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * 定制界面控制器
 * @author sunjian.
 */
@Data
public class CustomizationController implements Initializable {

    /**
     * 选择框
     */
    @FXML
    private ChoiceBox<String> customizationChoiceBox;
    @FXML
    private Button addButton;
    @FXML
    private Button finishButton;
    @FXML
    private VBox addVBox;
//    @FXML
//    private ListView<CustomizationController> customizationListView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //初始化choiceBox
        customizationChoiceBox.getItems().setAll("asdad","sddddd","kasdkj");
//        customizationChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(
//                "First", "Second", "Third")
//        );
        customizationChoiceBox.getSelectionModel().selectFirst();
        customizationChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> System.out.println("值从"+oldValue+"变成了"+newValue));
        // 初始化按钮点击事件
        addButton.setOnAction(event -> System.out.println("click add..."));
        finishButton.setOnAction(event -> System.out.println("click finish..."));
        //初始化listView 不能用listView
//        ObservableList<CustomizationController> customizationControllers = FXCollections.observableArrayList(this);
//        customizationListView.setItems(customizationControllers);
        addVBox.getChildren().add(new Button("123"));
    }

}
