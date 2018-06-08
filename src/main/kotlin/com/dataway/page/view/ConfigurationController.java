package com.dataway.page.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author sunjian.
 */
public class ConfigurationController implements Initializable {

    private static final String[] listViewOptions = {"通用", "规则集", "支持库"};

    @FXML
    private Button delButton;
    @FXML
    private Button bigButton;
    @FXML
    private HBox hBox;
    @FXML
    private ListView<String> setOptionList;
    @FXML
    private AnchorPane splitRightPane;
    @FXML
    private TextField searchTextField;
    @FXML
    private Button catalogSearchButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        delButton.setGraphic(new ImageView("/com/dataway/page/image/del.png"));
        delButton.setText(null);
        delButton.setOnAction(event -> System.out.println("delete"));
        //设置放大的图片
        bigButton.setGraphic(new ImageView("/com/dataway/page/image/fullScreen.png"));
        bigButton.setText(null);
        bigButton.setOnAction(event -> System.out.println("fullScreen"));
        //设置listView
        ObservableList<String> missions = FXCollections.observableArrayList(listViewOptions);
        setOptionList.setItems(missions);

        catalogSearchButton.setOnAction(event -> System.out.println(searchTextField.getText()));
    }


    @FXML
    private void onMouseClicked(MouseEvent event) throws IOException {
        /*missionOverviewText.clear();
        final String selectedItem = missionsList.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            return;
        }
        missionOverviewText.positionCaret(0);
        missionOverviewText.appendText(getInfo(selectedItem));*/
        final String selectedItem = setOptionList.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            return;
        }
        System.out.println("选中的是:  " + selectedItem);
        switch(selectedItem){
            case "通用":
                System.out.println(1);
                /*AnchorPane commonAnchorPane = new AnchorPane();
                commonAnchorPane.prefHeight(706.0);
                commonAnchorPane.prefWidth(954.0);
                commonAnchorPane.getChildren().add(new Button("asdasd"));
                common.getChildren().clear();
                common.getChildren().add(commonAnchorPane);*/

//                FXMLLoader loader = new FXMLLoader(ConfigurationController.class.getResource("/com/dataway/page/view/Common.fxml"));
//                loader.setLocation(ConfigurationController.class.getResource("/com/dataway/page/view/Common.fxml"));
//                AnchorPane commonAnchorPane = loader.load();
//                splitRightPane.getChildren().clear();
//                splitRightPane.getChildren().add(commonAnchorPane);
                showSplitRightPane(new FXMLLoader(ConfigurationController.class.getResource("/com/dataway/page/view/Common.fxml")));
                break;
            case "规则集":
                System.out.println(2);
                /*AnchorPane ruleSetAnchorPane = new AnchorPane();
                ruleSetAnchorPane.prefHeight(706.0);
                ruleSetAnchorPane.prefWidth(954.0);
                ruleSetAnchorPane.getChildren().add(new Button("guizji"));*/
//                FXMLLoader loader2 = new FXMLLoader(ConfigurationController.class.getResource("/com/dataway/page/view/RuleSet.fxml"));
//                loader.setLocation(ConfigurationController.class.getResource("/com/dataway/page/view/Common.fxml"));
//                AnchorPane RuleSetAnchorPane = loader2.load();
//                splitRightPane.getChildren().clear();
//                splitRightPane.getChildren().add(RuleSetAnchorPane);
                showSplitRightPane(new FXMLLoader(ConfigurationController.class.getResource("/com/dataway/page/view/RuleSet.fxml")));
                break;
            case "支持库":
                System.out.println(3);
               /* AnchorPane supportLibraryAnchorPane = new AnchorPane();
                supportLibraryAnchorPane.prefHeight(706.0);
                supportLibraryAnchorPane.prefWidth(954.0);
                supportLibraryAnchorPane.getChildren().add(new Button("zhchiku"));
                splitRightPane.getChildren().clear();
                splitRightPane.getChildren().add(supportLibraryAnchorPane);*/
                showSplitRightPane(new FXMLLoader(ConfigurationController.class.getResource("/com/dataway/page/view/SupportLibraries.fxml")));
                break;
            default:
                System.out.println("default");
        }
    }

    // 设置右侧动态窗格展示页
    private void showSplitRightPane(FXMLLoader fxmlLoader) throws IOException {
        AnchorPane anchorPane = fxmlLoader.load();
        splitRightPane.getChildren().clear();
        splitRightPane.getChildren().add(anchorPane);
        String[] arr = {"汽车行业","金融行业","餐饮行业","烟草行业"};
    }
}
