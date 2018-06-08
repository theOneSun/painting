package com.dataway.page.view.configuration

import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.ChoiceBox
import javafx.scene.control.Label
import javafx.scene.control.ListView
import javafx.scene.layout.ColumnConstraints
import javafx.scene.layout.GridPane
import javafx.scene.layout.RowConstraints
import javafx.scene.layout.VBox
import java.net.URL
import java.util.ResourceBundle


/**
 * @author sunjian.
 */
class RuleSetParentNodeController : Initializable {
    @FXML
    private lateinit var parentNodeVBox: VBox
    @FXML
    private lateinit var verifyGridPane: GridPane
    @FXML
    private lateinit var verifyRuleChoiceBox: ChoiceBox<String>
    @FXML
    private lateinit var supportListView: ListView<String>

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        //todo gridPane初始化
        showVerifyGridPane()

        //todo 支持库listView初始化

        //todo 验证规则choiceBox初始化


//        loopAdd()

    }

    private fun loopAdd() {
        for (i in 3 until 20) {
            val rowConstraints = RowConstraints(40.0)
            verifyGridPane.rowConstraints.add(rowConstraints)
            val label = Label("电视剧使用习惯$i")
            label.prefHeight = 36.0
            label.prefWidth = 198.0

            verifyGridPane.add(label, 1, i)
        }
    }

    //todo 初始化gridPane,参数待定
    private fun showVerifyGridPane() {

        // 初始化列,3列
        verifyGridPane.columnConstraints.addAll(ColumnConstraints(100.0), ColumnConstraints(250.0), ColumnConstraints(50.0))

        // 初始化行,2行
        verifyGridPane.rowConstraints.addAll(RowConstraints(40.0), RowConstraints(40.0))

        Label("验证列名")
                .also {
                    it.prefHeight = 40.0
                    it.prefWidth = 100.0
                }
                .let { verifyGridPane.add(it, 0, 0) }

        Label("验证规则")
                .also {
                    it.prefHeight = 40.0
                    it.prefWidth = 100.0
                }
                .let { verifyGridPane.add(it, 0, 1) }

        ChoiceBox<String>()
                .also {
                    it.id = "verifyRuleChoiceBox"
                    it.prefHeight = 30.0
                    it.prefWidth = 200.0
                    it.insets

                }
                .let { verifyGridPane.add(it,1,1) }

    }
}