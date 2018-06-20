package com.dataway.page.view.configuration

import com.dataway.page.model.NormalRuleRow
import com.dataway.page.view.selfdefine.LeoContext
import com.dataway.page.view.selfdefine.RULE_CHECK_MAX_SCALE
import com.dataway.page.view.selfdefine.RULE_CHECK_MIN_SCALE
import com.dataway.page.view.selfdefine.RULE_COLUMN_NAMES
import com.dataway.page.view.selfdefine.RULE_COLUMN_VALUES
import com.dataway.page.view.selfdefine.RULE_INCLUDE_COLUMNS
import com.dataway.page.view.selfdefine.RULE_PREFIX
import com.dataway.page.view.selfdefine.RULE_TOP_COLUMNS
import com.dataway.page.view.selfdefine.SELECTED_RULE
import com.dataway.page.view.selfdefine.SELECTED_RULE_PARENT_RULE_SET
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import javafx.scene.control.cell.CheckBoxTableCell
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.control.cell.TextFieldTableCell
import javafx.util.converter.DoubleStringConverter
import jodd.props.Props
import java.io.File
import java.net.URL
import java.util.Collections
import java.util.ResourceBundle

/**
 * @author sunjian.
 */
class RuleSetChildNodeController : Initializable {

    @FXML
    private lateinit var ruleNameTextField: TextField
    @FXML
    private lateinit var normalTableView: TableView<NormalRuleRow>

    //常规的列的集合,每一行是一个对象
    private var normalColumnRowList: ArrayList<NormalRuleRow> = arrayListOf()
    //    private var normalColumnRowList: ArrayList<NormalRuleRow> = arrayListOf()
    //常规列名集合
    private var columnList: ArrayList<String> = arrayListOf()
    //规则集配置文件对象
    private var ruleSetProps: Props = Props()
    private var ruleName: String? = null//默认没有选中规则,因为可能是新建规则
    private lateinit var ruleSetName: String // 到这个页面,选中的规则集一定不为空

    override fun initialize(location: URL?, resources: ResourceBundle?) {
//        val ruleName = LeoContext.getValue(SELECTED_RULE) as String?
        ruleName = LeoContext.getValue(SELECTED_RULE) as String?
//        val ruleSetName = LeoContext.getValue(SELECTED_RULE_PARENT_RULE_SET)
        ruleSetName = LeoContext.getValue(SELECTED_RULE_PARENT_RULE_SET) as String
        //todo 根据规则集名称查询配置文件,返回props对象
        val configPath = System.getProperty("user.dir") + "/conf"

        ruleSetProps.load(File("$configPath/$ruleSetName.props"))
        val columns = ruleSetProps.getValue("$RULE_PREFIX.$ruleName.$RULE_COLUMN_NAMES")
        val includeColumns = ruleSetProps.getValue("$RULE_PREFIX.$ruleName.$RULE_INCLUDE_COLUMNS")
        println(columns)
        //规则名称
        ruleNameTextField.text = ruleName

        //循环显示列数据tableView
        convertColumnsToList(columns, includeColumns)

        //初始化表格数据
        initNormalTableView()
        //todo 根据规则名称查询生效关键字
    }

    /**
     * 初始化表格的表头
     */
    private fun initNormalTableView() {

        val nameColumn = createStringTableColumn(200.0, "文件列名", "columnName")
        val includeColumn = TableColumn<NormalRuleRow, Boolean>("统计")
        includeColumn.prefWidth = 80.0
        includeColumn.isSortable = false
        includeColumn.cellFactory = CheckBoxTableCell.forTableColumn(includeColumn)
        includeColumn.cellValueFactory = PropertyValueFactory("include")

        val maxValueColumn = createDoubleTableColumn(100.0, "最大值", "maxValue")

        val minValueColumn = createDoubleTableColumn(100.0, "最小值", "minValue")

        val verifyColumn = createStringTableColumn(200.0, "数据校验值", "columnValues")

        val topValueColumn = createDoubleTableColumn(100.0, "取Top值", "columnTopValue")

        val upColumn = TableColumn<NormalRuleRow, Button>("上移")
        upColumn.prefWidth = 79.0
        upColumn.isSortable = false
        upColumn.cellValueFactory = PropertyValueFactory("upButton")

        val downColumn = TableColumn<NormalRuleRow, String>("下移")
        downColumn.prefWidth = 79.0
        downColumn.isSortable = false
        downColumn.cellValueFactory = PropertyValueFactory("downButton")

        val deleteColumn = TableColumn<NormalRuleRow, String>("+")
        deleteColumn.prefWidth = 80.0
        deleteColumn.isSortable = false
        deleteColumn.cellValueFactory = PropertyValueFactory("deleteButton")

        normalTableView.columns.addAll(nameColumn, includeColumn, maxValueColumn, minValueColumn,
                verifyColumn, topValueColumn, upColumn, downColumn, deleteColumn)

        normalTableView.isEditable = true
        normalTableView.items.also { it.clear();it.addAll(normalColumnRowList) }
    }

    /**
     * 生成文本的TableColumn
     */
    private fun createStringTableColumn(prefWidth: Double, text: String, property: String): TableColumn<NormalRuleRow, String> {
        val tableColumn = TableColumn<NormalRuleRow, String>(text)
        tableColumn.prefWidth = prefWidth
        tableColumn.isSortable = false
        tableColumn.isEditable = true
        tableColumn.cellFactory = TextFieldTableCell.forTableColumn()
        tableColumn.cellValueFactory = PropertyValueFactory(property)
        return tableColumn
    }

    /**
     * 生成Double的TableColumn
     */
    private fun createDoubleTableColumn(prefWidth: Double, text: String, property: String): TableColumn<NormalRuleRow, Double> {
        val tableColumn = TableColumn<NormalRuleRow, Double>(text)
        tableColumn.prefWidth = prefWidth
        tableColumn.isSortable = false
        tableColumn.isEditable = true
        tableColumn.cellFactory = TextFieldTableCell.forTableColumn(DoubleStringConverter())
        tableColumn.cellValueFactory = PropertyValueFactory(property)
        return tableColumn
    }

    /**
     * 将配置文件中读取的属性转化成tableView展示的格式
     */
    private fun convertColumnsToList(columnNames: String?, includeColumns: String?) {
        val list = columnNames?.split(",")
        list?.forEach { columnName ->
            columnList.add(columnName)
            val normalRuleRow = NormalRuleRow()
            //列名和统计包含列
            /* normalRuleRow.columnName = SimpleStringProperty(columnName)
             normalRuleRow.include.value = includeColumns?.contains(columnName)*/
            normalRuleRow.columnName = columnName
            includeColumns?.contains(columnName)?.let {
                normalRuleRow.include = it
            }
            //最大最小值
            ruleSetProps.getValue("$RULE_PREFIX.$ruleName.$RULE_CHECK_MAX_SCALE.$columnName")?.toDouble()?.let {
                //                normalRuleRow.maxValue = SimpleDoubleProperty(it)
                normalRuleRow.maxValue = it
            }

            ruleSetProps.getValue("$RULE_PREFIX.$ruleName.$RULE_CHECK_MIN_SCALE.$columnName")?.toDouble()?.let {
                //                normalRuleRow.minValue = SimpleDoubleProperty(it)
                normalRuleRow.minValue = it
            }

            //数据校验值
            ruleSetProps.getValue("$RULE_PREFIX.$ruleName.$RULE_COLUMN_VALUES.$columnName")?.let {
                //                normalRuleRow.columnValues = SimpleStringProperty(it)
                normalRuleRow.columnValues = it
            }
            //top值
            ruleSetProps.getValue("$RULE_PREFIX.$ruleName.$RULE_TOP_COLUMNS.$columnName")?.toDouble()?.let {
                //                normalRuleRow.columnTopValue = SimpleDoubleProperty(it)
                normalRuleRow.columnTopValue = it
            }

            //上移
            normalRuleRow.upButton = createUpButton(list.indexOf(columnName))
            //下移
            normalRuleRow.downButton = createDownButton(list.indexOf(columnName))

            //删除
            normalRuleRow.deleteButton = Button("删除这行")
            //添加到属性的集合中
            normalColumnRowList.add(normalRuleRow)

        }
        println(columnList)
    }

    /**
     * 生成上移按钮
     */
    private fun createUpButton(index: Int): Button {
        val upButton = Button("∧")
        upButton.setOnAction {
            println("上移一位")
            //设置当前行处于选中状态
            normalTableView.selectionModel.select(index)
            if (index > 0) {
                Collections.swap(normalColumnRowList, index - 1, index)
                refreshRowList()
            }
        }
        return upButton
    }

    /**
     * 生成下移按钮
     */
    private fun createDownButton(index: Int): Button {
        val downButton = Button("∨")
        downButton.setOnAction {
            println("下移一位")
            normalTableView.selectionModel.select(index)
            if (index < normalColumnRowList.size - 1) {
                Collections.swap(normalColumnRowList, index + 1, index)
                refreshRowList()
            }
        }
        return downButton
    }

    /**
     * 生成删除按钮
     */
    private fun createDeleteButton(index: Int): Button {
        return Button("删除").also {
            it.prefWidth = 80.0
            it.prefHeight = 30.0
            it.setOnAction {
                //设置被选中的行
                normalTableView.selectionModel.select(index)
                normalColumnRowList.removeAt(index)
                refreshRowList()
            }
        }

    }

    /**
     * 位置操作后要对按钮的index值进行更新
     */
    private fun refreshRowList() {
        normalColumnRowList.forEach { normalRuleRow: NormalRuleRow ->
            normalRuleRow.upButton = createUpButton(normalColumnRowList.indexOf(normalRuleRow))
            normalRuleRow.downButton = createDownButton(normalColumnRowList.indexOf(normalRuleRow))
            normalRuleRow.deleteButton = createDeleteButton(normalColumnRowList.indexOf(normalRuleRow))
        }
        normalTableView.items.also {
            it.clear()
            it.addAll(normalColumnRowList)
        }
    }
}