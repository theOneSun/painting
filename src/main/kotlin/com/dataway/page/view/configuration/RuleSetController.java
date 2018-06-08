//package com.dataway.page.view.configuration;
//
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.TreeItem;
//import javafx.scene.control.TreeView;
//
//import java.net.URL;
//import java.util.ResourceBundle;
//
///**
// * @author sunjian.
// */
//public class RuleSetController implements Initializable{
//    @FXML
//    private TreeView<String> ruleSetTreeView;
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        TreeItem<String> abstractRoot = new TreeItem<>("");
//        abstractRoot.setExpanded(true);
//        TreeItem<String> root = new TreeItem<>("商业地产规则集");
//        root.setExpanded(true);
//
//
//        TreeItem<String> root2 = new TreeItem<>("其它规则集");
//        TreeItem leafNode1 = new TreeItem<>("App个wap使用习惯");
//        TreeItem leafNode2 = new TreeItem<>("电视剧规则");
//        root.getChildren().addAll(leafNode1,leafNode2);
//        root2.getChildren().addAll(leafNode1,leafNode2);
//
//        abstractRoot.getChildren().addAll(root,root2);
//        ruleSetTreeView.setRoot(abstractRoot);
//        ruleSetTreeView.setShowRoot(false);
////        ruleSetTreeView.setRoot(root2);
//    }
//}
