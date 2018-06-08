package com.dataway.page.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import lombok.Data;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author sunjian.
 */
@Data
public class PreHandleController implements Initializable {
    /**
     * 预处理tab的tree
     */
    @FXML
    private TreeView preHandleTreeView;

    /**
     * 初始化
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TreeItem<String> item = new TreeItem<>("跟节点");
        preHandleTreeView.setRoot(item);
        item.setExpanded(false);
        TreeItem<String> i1 = new TreeItem<>("电影");
        TreeItem<String> i2 = new TreeItem<>("音乐");
        TreeItem<String> i3 = new TreeItem<>("游戏");
        item.getChildren().addAll(i1, i2, i3);
        TreeItem<String> i4 = new TreeItem<>("荡寇风云");
        TreeItem<String> i5 = new TreeItem<>("变形金刚5");
        i1.setExpanded(false);
        i1.getChildren().addAll(i4, i5);
    }
}
