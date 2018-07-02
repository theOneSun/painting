package com.dataway.page.view.configuration

import com.dataway.page.model.VerifyRuleRow
import com.dataway.page.util.DialogFactory
import com.dataway.page.util.PropsUtils
import com.dataway.page.view.selfdefine.BottomAction
import com.dataway.page.view.selfdefine.CURRENT_BOTTOM_ACTION
import com.dataway.page.view.selfdefine.LeoContext
import com.dataway.page.view.selfdefine.RULE_PREFIX
import com.dataway.page.view.selfdefine.RULE_SET_CONTROLLER
import com.dataway.page.view.selfdefine.RULE_SET_VERIFY_COLUMN
import com.dataway.page.view.selfdefine.RULE_SET_VERIFY_RULES
import com.dataway.page.view.selfdefine.SELECTED_RULE_SET
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.ChoiceBox
import javafx.scene.control.Label
import javafx.scene.control.ListView
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.layout.ColumnConstraints
import javafx.scene.layout.GridPane
import javafx.scene.layout.RowConstraints
import javafx.scene.layout.VBox
import jodd.props.Props
import jodd.props.PropsConverter
import org.apache.commons.lang3.StringUtils
import org.springframework.util.ObjectUtils
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
    @FXML
    private lateinit var verifyRuleTableView: TableView<VerifyRuleRow>
    @FXML
    private lateinit var addButton: Button

    private var verifyRuleRowList = arrayListOf<VerifyRuleRow>()

    var ruleSetConfigPath = "${System.getProperty("user.dir")}/conf/"

    //规则集控制器
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

        // gridPane初始化
        showVerifyGridPane()
        addButton.setOnAction {
            /*
            获取choiceBox的选中项
            根据这个选中项查询是否已经在配置过了
            添加先暂时添加到表格中
             */
            val selectedItem = verifyRuleChoiceBox.selectionModel.selectedItem
            var have = false
            verifyRuleRowList.forEach { verifyRuleRow ->
                if (verifyRuleRow.ruleName == selectedItem) {
                    have = true
                }
            }
            if (have) {
                val errorDialog = DialogFactory.createErrorDialog("", "不能重复添加")
                errorDialog.showAndWait()
            } else {
                /*ruleSetProps = Props()
                ruleSetProps.load(File("$ruleSetConfigPath$ruleSetName.props"))
                PropsUtils.containsKey(RULE_SET_VERIFY_RULES)*/
                verifyRuleRowList.add(VerifyRuleRow().also {
                    it.ruleName = selectedItem
                    it.deleteButton = createDeleteButton(verifyRuleRowList.size)
                })
                refreshRowList()
            }

        }
        // 验证规则表格
        initVerifyRulesTableView()


        //todo 支持库listView初始化 根据规则集名称查询其支持库


        //loopAdd()
        this.showData(ruleSetName)
    }

    /**
     * 初始化验证规则表格
     */
    private fun initVerifyRulesTableView() {
        val nameColumn = TableColumn<VerifyRuleRow, String>()
        nameColumn.prefWidth = 350.0
        nameColumn.isEditable = false
        nameColumn.isSortable = false
        nameColumn.cellValueFactory = PropertyValueFactory("ruleName")
        val deleteColumn = TableColumn<VerifyRuleRow, String>()
        deleteColumn.prefWidth = 50.0
        deleteColumn.isSortable = false
        deleteColumn.cellValueFactory = PropertyValueFactory("deleteButton")

        verifyRuleTableView.columns.addAll(nameColumn, deleteColumn)

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
                //设置验证列名
                props.setValue(RULE_SET_VERIFY_COLUMN, verifyColumn)
                //设置验证规则
                this.setVerifyRulesToProps(props)
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
                    //设置验证列名
                    props.setValue(RULE_SET_VERIFY_COLUMN, verifyColumn)
                    //设置验证规则
                    this.setVerifyRulesToProps(props)

                    //todo 其它属性添加

                    val orderedProperties = PropsUtils.convertOrderProperties(props)
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

            // 新增 将所有数据保存到properties中并生成文件
            val ruleSetFile = File(fileName)
            if (ruleSetFile.exists()) {
                //修改的名称已存在不可以修改
                val errorDialog = DialogFactory.createErrorDialog(500.0, 400.0, "", "规则集名称已存在")
                errorDialog.showAndWait()
            } else {
                println("新增可以存")
            }
            //规则集名称

            val props = Props()
            //验证列名
            props.setValue(RULE_SET_VERIFY_COLUMN, verifyColumn)
            //验证规则
            this.setVerifyRulesToProps(props)
            //todo 其他数据

            val orderedProperties = PropsUtils.convertOrderProperties(props)

            //存储的时候再转成props
            PropsConverter.convert(FileWriter(fileName), orderedProperties)
        }

        // 更新树结构
        ruleSetController.loadTreeData()

    }

    /**
     * 设置参与验证的规则
     */
    private fun setVerifyRulesToProps(props: Props) {
        val itemList = verifyRuleTableView.items
        val verifyRulesValue = StringBuilder()
        for (item in itemList) {
            verifyRulesValue.append("${item.ruleName},")
        }
        //最后是逗号删除逗号
        if (verifyRulesValue.lastIndexOf(",") == verifyRulesValue.length - 1) {
            verifyRulesValue.delete(verifyRulesValue.length - 1, verifyRulesValue.length)
        }
        props.setValue(RULE_SET_VERIFY_RULES, verifyRulesValue.toString())
    }

    // 初始化gridPane
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

//        ChoiceBox<String>()
//                .also {
//                    it.id = "verifyRuleChoiceBox"
//                    it.prefHeight = 30.0
//                    it.prefWidth = 200.0
////                    it.insets
//                }
//                .let { verifyGridPane.add(it, 1, 1) }

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
            // 验证规则
            val ruleSet = LinkedHashSet<String>()
            val rulePropertyMap = PropsUtils.getMapByPrefix(props, RULE_PREFIX)
            for (entry in rulePropertyMap.entries) {
                ruleSet.add(entry.key.split(".")[1])
            }
            //选择框
            verifyRuleChoiceBox.items.addAll(ruleSet)
            //tableView展示
            if (PropsUtils.containsKey(props, RULE_SET_VERIFY_RULES)) {
                //如果有这个key值
                val verifyRules = props.getValue(RULE_SET_VERIFY_RULES)

                val splitList = verifyRules.split(",")
                if (!ObjectUtils.isEmpty(splitList)) {
                    for (i in splitList.indices) {
                        val verifyRuleRow = VerifyRuleRow()
                        verifyRuleRow.ruleName = splitList[i]
                        verifyRuleRow.deleteButton = createDeleteButton(i)
                        verifyRuleRowList.add(verifyRuleRow)
                    }
                }
                verifyRuleTableView.items.addAll(verifyRuleRowList)
            }


            //todo 支持库
        }
    }

    /**
     * 创建验证规则table的删除按钮
     */
    private fun createDeleteButton(rowIndex: Int): Button {
        val button = Button("D")
//        maxWidth="30.0" mnemonicParsing="false" prefHeight="30.0" text="D" GridPane.columnIndex="2" GridPane.rowIndex="2"
        button.maxWidth = 30.0
        button.prefHeight = 30.0
        button.setOnAction {
            verifyRuleTableView.selectionModel.select(rowIndex)
            verifyRuleRowList.removeAt(rowIndex)
            refreshRowList()
        }
        return button
    }

    /**
     * 删除操作后需要对index值进行更新
     */
    private fun refreshRowList() {
        verifyRuleRowList.forEach { it ->
            it.deleteButton = createDeleteButton(verifyRuleRowList.indexOf(it))
        }
        verifyRuleTableView.items.also {
            it.clear()
            it.addAll(verifyRuleRowList)
        }
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