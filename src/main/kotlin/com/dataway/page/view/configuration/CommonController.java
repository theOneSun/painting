//package com.dataway.page.view.configuration;
//
//import com.dataway.page.view.selfdefine.LeoContext;
//import javafx.collections.FXCollections;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.Button;
//import javafx.scene.control.ChoiceBox;
//import javafx.scene.control.TextField;
//
//import java.net.URL;
//import java.util.ResourceBundle;
//
//import static com.dataway.page.view.selfdefine.LeoConstantKt.TARGET_DIR;
//
///**
// * 通用控制器
// *
// * @author sunjian.
// */
//public class CommonController implements Initializable {
//
//    @FXML
//    private ChoiceBox<String> corpusChoiceBox;
//    @FXML
//    private Button corpusButton;
//    @FXML
//    private TextField targetDirTextField;
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
////        corpusChoiceBox.getItems().setAll("商业地产语料库","XX行业语料库");
//        corpusChoiceBox.setItems(FXCollections.observableArrayList(
//                "商业地产语料库", "XX行业语料库"));
//
//        //初始化统计文件存放目录
//        // TODO: 2018/6/14 读取配置文件,将存放目录,从全局对象中读取,没有的话设置为当前根目录路径,敲回车后更改
//        LeoContext.getValue(TARGET_DIR);
//
//    }
//}
