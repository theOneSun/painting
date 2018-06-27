package com.dataway.page.view.configuration

import com.dataway.page.util.DialogFactory
import com.dataway.page.util.PropsUtils
import com.dataway.page.view.selfdefine.BottomAction
import com.dataway.page.view.selfdefine.CURRENT_BOTTOM_ACTION
import com.dataway.page.view.selfdefine.LeoContext
import com.dataway.page.view.selfdefine.RULE_PREFIX
import com.dataway.page.view.selfdefine.RULE_SET_CONTROLLER
import com.dataway.page.view.selfdefine.RULE_SET_VERIFY_COLUMN
import com.dataway.page.view.selfdefine.SELECTED_RULE_SET
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
import jodd.props.Props
import jodd.props.PropsConverter
import org.apache.commons.lang3.StringUtils
import java.io.File
import java.io.FileWriter
import java.net.URL
import java.util.ResourceBundle


/**
 * @author sunjian.
 */
class RuleSetParentNodeController : Initializable, BottomAction {

    @FXML
    private lateinit var parentNodeVBox: VBox
    @FXML
    private lateinit var verifyGridPane: GridPane
    @FXML
    private lateinit var verifyRuleChoiceBox: ChoiceBox<String>
    @FXML
    private lateinit var supportListView: ListView<String>
    @FXML
    private lateinit var ruleSetNameTextField: TextField
    @FXML
    private lateinit var verifyColumnTextField: TextField

    var ruleSetConfigPath = "${System.getProperty("user.dir")}/conf/"

    private lateinit var ruleSetController: RuleSetController

    /**
     * 判断是修改规则集还是新增规则集
     */
    private var editPage: Boolean = true

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        //底部
        LeoContext.save(CURRENT_BOTTOM_ACTION, this)
        //规则集控制器
        ruleSetController = LeoContext.getValue(RULE_SET_CONTROLLER) as RuleSetController

        //名称
        val ruleSetName = LeoContext.getValue(SELECTED_RULE_SET) as String?
        if (ruleSetName == null) {
            editPage = false
        }
        ruleSetNameTextField.text = ruleSetName

        //todo 验证列名(根据规则集名称查询其验证列名)

        //todo 验证规则:根据规则集名称查询其验证规则 --choiceBox初始化


        //todo gridPane初始化
        showVerifyGridPane()

        //todo 支持库listView初始化 根据规则集名称查询其支持库


        //loopAdd()
        this.showData(ruleSetName)
    }

    private fun loopAdd() {
        var ruleList = ArrayList<String>()
        for (i in 3..20) {
            ruleList.add("验证规则$i")
        }

        for (i in ruleList.indices) {
            val rowConstraints = RowConstraints(40.0)

            verifyGridPane.rowConstraints.add(rowConstraints)
            val label = Label("电视剧使用习惯${i + 3}")
            label.prefHeight = 36.0
            label.prefWidth = 198.0

            val button = Button("D")
            button.id = "delete${i + 3}"
            button.setOnMouseClicked {
                println("删除这行")
//                verifyGridPane.rowConstraints.removeAt(i+3)
                ruleList.remove(ruleList[i])
                verifyGridPane.rowConstraints.clear()
                loopAdd()
            }
            verifyGridPane.add(label, 1, i + 3)
            verifyGridPane.add(button, 2, i + 3)
        }
    }

    //取消按钮
    override fun doCancel() {
        showData(LeoContext.getValue(SELECTED_RULE_SET).toString())
    }

    override fun doSave() {
        this.doConfirm()
    }

    override fun doConfirm() {
        //判断是编辑还是新增
//        var ruleSetName: String?
//         ruleSetName =
        //判断有没有改名
        /*if (ruleSetNameTextField.text == LeoContext.getValue(SELECTED_RULE_SET)) {
            ruleSetName = LeoContext.getValue(SELECTED_RULE_SET) as String?
        } else {
            ruleSetName = ruleSetNameTextField.text
            editPage = false
        }*/
        val ruleSetName = ruleSetNameTextField.text
        val fileName = "${System.getProperty("user.dir")}/conf/$ruleSetName.props"
        val verifyColumn = verifyColumnTextField.text

        if (editPage) {
            //编辑状态
            //判断是否修改规则集名称

            if (ruleSetName == LeoContext.getValue(SELECTED_RULE_SET)) {
                //没改规则集名称
                val props = Props()

                props.load(File(fileName))

                props.setValue(RULE_SET_VERIFY_COLUMN, verifyColumn)
//            val orderedProperties = PropsUtils.convertProperties(props)
                val orderedProperties = PropsUtils.convertOrderProperties(props)
//                orderedProperties.keys
                //存储的时候再转成props
                PropsConverter.convert(FileWriter(fileName), orderedProperties)
            } else {
                //修改了规则集名称
                val oldRuleSetName = LeoContext.getValue(SELECTED_RULE_SET)
                val ruleSetFile = File(fileName)
                if (ruleSetFile.exists()) {
                    //修改的名称已存在不可以修改
                    val errorDialog = DialogFactory.createErrorDialog(500.0, 400.0, "", "规则集名称已存在")
                    errorDialog.showAndWait()
                } else {
                    //可以修改

                    val oldFileName = "${System.getProperty("user.dir")}/conf/$oldRuleSetName.props"
                    val props = Props()

                    props.load(File(oldFileName))
                    props.setValue(RULE_SET_VERIFY_COLUMN, verifyColumn)
                    val orderedProperties = PropsUtils.convertOrderProperties(props)
                    //todo 其它属性添加

                    //存储的时候再转成props
                    PropsConverter.convert(FileWriter(fileName), orderedProperties)
                    //保存新的规则集名称
                    LeoContext.save(SELECTED_RULE_SET, ruleSetName)
                    File(oldFileName).delete()

                }
            }
//            val fileName = "${System.getProperty("user.dir")}/conf/$ruleSetName.props"

            //验证列名
            println("验证列名$verifyColumn")
            //验证规则

            // 更新页面数据
            this.showData(ruleSetName)
        } else {

            //todo 新增 将所有数据保存到properties中并生成文件
            val ruleSetFile = File(fileName)
            if (ruleSetFile.exists()) {
                //修改的名称已存在不可以修改
                val errorDialog = DialogFactory.createErrorDialog(500.0, 400.0, "", "规则集名称已存在")
                errorDialog.showAndWait()
            } else {
                println("新增可以存")
            }
            //规则集名称

            //验证列名
            println("验证列名$verifyColumn")
            val props = Props()
            props.setValue(RULE_SET_VERIFY_COLUMN, verifyColumn)
            //todo 其他数据

            val orderedProperties = PropsUtils.convertOrderProperties(props)

            //存储的时候再转成props
            PropsConverter.convert(FileWriter(fileName), orderedProperties)
        }

        //TODO 更新树结构

        ruleSetController.loadTreeData()

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
                .let { verifyGridPane.add(it, 1, 1) }

    }

    /**
     * 展示规则集页面的数据(根据名称加载配置文件,展示数据)
     */
    private fun showData(ruleSetName: String?) {
        //val ruleSetName = LeoContext.getValue(SELECTED_RULE_SET) as String?
        if (StringUtils.isNotBlank(ruleSetName)) {
            //不为空,为新建编辑
            val props = Props()
            props.load(File("$ruleSetConfigPath$ruleSetName.props"))
            //验证列名
            verifyColumnTextField.text = props.getValue(RULE_SET_VERIFY_COLUMN)
            //todo 验证规则
            val ruleList = arrayListOf<String>()
            val rulePropertyMap = PropsUtils.getMapByPrefix(props, RULE_PREFIX)
            for (entry in rulePropertyMap.entries) {
                ruleList.add(entry.key.split(".")[1])
            }

            //todo 支持库
        }
    }

    private fun createDeleteButton(rowIndex: Int): Button {
        val button = Button("D")
//        maxWidth="30.0" mnemonicParsing="false" prefHeight="30.0" text="D" GridPane.columnIndex="2" GridPane.rowIndex="2"
        button.maxWidth = 30.0
        button.prefHeight = 30.0
        button.setOnAction {

        }
        return button
    }

    /*private fun loadProps(propsFile: File,verifyColumn:String){
        val props = Props()

        props.load()

        props.setValue(RULE_SET_VERIFY_COLUMN, verifyColumn)
//            val orderedProperties = PropsUtils.convertProperties(props)
        val orderedProperties = PropsUtils.convertOrderProperties(props)
        orderedProperties.keys
        //存储的时候再转成props
        PropsConverter.convert(FileWriter(fileName), orderedProperties)
    }*/

}