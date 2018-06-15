package com.dataway.page.view.configuration

import com.dataway.page.view.selfdefine.LeoContext
import com.dataway.page.view.selfdefine.SELECTED_RULESET
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.ChoiceBox
import javafx.scene.control.Label
import javafx.scene.control.ListView
import javafx.scene.control.TextField
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
    @FXML
    private lateinit var ruleSetNameTextField:TextField
    @FXML
    private lateinit var verifyColumnTextField:TextField

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        //名称
        ruleSetNameTextField.text = LeoContext.getValue(SELECTED_RULESET) as String?
        //todo 验证列名(根据规则集名称查询其验证列名)
        //todo 验证规则:根据规则集名称查询其验证规则 --choiceBox初始化


        //todo gridPane初始化
        showVerifyGridPane()

        //todo 支持库listView初始化 根据规则集名称查询其支持库



        //loopAdd()

    }

    private fun loopAdd() {
        var ruleList = ArrayList<String>()
        for (i in 3..20){
            ruleList.add("验证规则$i")
        }

        for (i in ruleList.indices) {
            val rowConstraints = RowConstraints(40.0)

            verifyGridPane.rowConstraints.add(rowConstraints)
            val label = Label("电视剧使用习惯${i+3}")
            label.prefHeight = 36.0
            label.prefWidth = 198.0

            val button =  Button("D")
            button.id = "delete${i+3}"
            button.setOnMouseClicked {
                println("删除这行")
//                verifyGridPane.rowConstraints.removeAt(i+3)
                ruleList.remove(ruleList[i])
                verifyGridPane.rowConstraints.clear()
                loopAdd()
            }
            verifyGridPane.add(label, 1, i+3)
            verifyGridPane.add(button,2,i+3)
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