package com.dataway.page.view.configuration;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * 通用控制器
 *
 * @author sunjian.
 */
public class CommonController implements Initializable {

    @FXML
    private ChoiceBox<String> corpusChoiceBox;
    @FXML
    private Button corpusButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        corpusChoiceBox.getItems().setAll("商业地产语料库","XX行业语料库");
        corpusChoiceBox.setItems(FXCollections.observableArrayList(
                "商业地产语料库", "XX行业语料库"));
    }
}
