package com.dataway.page.view.configuration

import ch.qos.logback.core.joran.conditional.ElseAction
import com.dataway.page.model.CrossRuleRow
import com.dataway.page.model.NormalRuleRow
import com.dataway.page.util.PropsUtils
import com.dataway.page.view.selfdefine.LeoContext
import com.dataway.page.view.selfdefine.RULE_CHECK_MAX_SCALE
import com.dataway.page.view.selfdefine.RULE_CHECK_MIN_SCALE
import com.dataway.page.view.selfdefine.RULE_COLUMN_NAMES
import com.dataway.page.view.selfdefine.RULE_COLUMN_VALUES
import com.dataway.page.view.selfdefine.RULE_CROSS_COLUMNS
import com.dataway.page.view.selfdefine.RULE_INCLUDE_COLUMNS
import com.dataway.page.view.selfdefine.RULE_PREFIX
import com.dataway.page.view.selfdefine.RULE_TOP_COLUMNS
import com.dataway.page.view.selfdefine.SELECTED_RULE
import com.dataway.page.view.selfdefine.SELECTED_RULE_PARENT_RULE_SET
import javafx.beans.value.ChangeListener
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.ChoiceBox
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import javafx.scene.control.cell.CheckBoxTableCell
import javafx.scene.control.cell.ChoiceBoxTableCell
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.control.cell.TextFieldTableCell
import javafx.util.converter.DoubleStringConverter
import jodd.props.Props
import java.awt.SystemColor.text
import java.io.File
import java.net.URL
import java.util.Collections
import java.util.Observable
import java.util.ResourceBundle

/**
 * @author sunjian.
 */
class RuleSetChildNodeController : Initializable {

    @FXML
    private lateinit var ruleNameTextField: TextField
    @FXML
    private lateinit var normalTableView: TableView<NormalRuleRow>
    @FXML
    private lateinit var crossTableView: TableView<CrossRuleRow>

    //常规的列的集合,每一行是一个对象
    private var normalColumnRowList: ArrayList<NormalRuleRow> = arrayListOf()
    //交叉的数据的集合,一行是一个对象
    private var crossRowList: ArrayList<CrossRuleRow> = arrayListOf()

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
        //todo 初始化交叉项表格
        initCrossTableView()
        //todo 根据规则名称查询生效关键字
    }

    /**
     * 初始化交叉规则的表格的表头
     */
    private fun initCrossTableView() {
        val column1 = this.createChoiceBoxTableColumn(255.0, "交叉项1", "crossItemA")
        val column2 = this.createChoiceBoxTableColumn(255.0, "交叉项2", "crossItemB")
        val column3 = this.createChoiceBoxTableColumn(255.0, "交叉项3", "crossItemC")
        val maxScaleColumn = TableColumn<CrossRuleRow, Double>("交叉最大比例")
        maxScaleColumn.prefWidth = 253.0
        maxScaleColumn.isSortable = false
        maxScaleColumn.isEditable = true
        maxScaleColumn.cellFactory = TextFieldTableCell.forTableColumn(DoubleStringConverter())
        maxScaleColumn.cellValueFactory = PropertyValueFactory("maxScale")
        crossTableView.columns.addAll(column1, column2, column3, maxScaleColumn)

        //读取常规配置的列名
        val names = ruleSetProps.getValue("$RULE_PREFIX.$ruleName.$RULE_COLUMN_NAMES")

        //读取已有的配置
        val crossColumnsMap = PropsUtils.getMapByPrefix(ruleSetProps, "$RULE_PREFIX.$ruleName.$RULE_CROSS_COLUMNS")
        println(crossColumnsMap)
        val optionList = names.split(",") as ArrayList
        for (entry in crossColumnsMap) {
            /*
            遍历map
            1.每一个keyValue对应一行数据
            2.按照,分割取三个数据作为crossRuleRow的交叉项123
            3.choiceBox的可选项需要有一个是空的
             */
            val rowColumns = entry.value
            val rowSelectedList = rowColumns.split(",")
            val crossRuleRow = CrossRuleRow()
            //可选择的选项列表
            optionList.add(0, "")

            var j = 1
            //循环配置文件中的值,展示已经选择的交叉项
            for (i in rowSelectedList.indices) {
                //自身和空串都不删
                val removeList = arrayListOf<String>().also { it.addAll(rowSelectedList);it.remove(rowSelectedList[i]);it.remove("") }
                val realOptionList = arrayListOf<String>().also { it.addAll(optionList) }
                realOptionList.removeAll(removeList)
                when (j) {
                    1 -> {

                        crossRuleRow.crossItemA = this.createChoiceBox(255.0, realOptionList, rowSelectedList[i])


                        crossRuleRow.crossItemA.selectionModel.selectedItemProperty().addListener { observable, oldValue, newValue ->
                            //B列,C列增加oldValue,不可选newValue
                            val changeRemoveList = arrayListOf<String>().also { it.addAll(rowSelectedList);it.remove(oldValue);it.remove("");it.add(newValue) }
                            val changeOptionList = arrayListOf<String>().also { it.addAll(optionList) }
                            changeOptionList.removeAll(changeRemoveList)
                            val selectedItemB = crossRuleRow.crossItemB.selectionModel.selectedItem
                            println("selectedItemB$selectedItemB")
                            val selectedItemC = crossRuleRow.crossItemC.selectionModel.selectedItem
                            println("selectedItemC$selectedItemC")
                            println("changeOptionList$changeOptionList")
                            crossRuleRow.crossItemB.items.also {
                                it.clear()
                                it.addAll(changeOptionList)
                            }
                            crossRuleRow.crossItemB.selectionModel.select(selectedItemB)
                            println(crossRuleRow.crossItemB.selectionModel.selectedItem)
//                            println("b的可选项$selectionModel")
                            crossRuleRow.crossItemC.items.also {
                                it.clear()
                                it.addAll(changeOptionList)
                            }
                            crossRuleRow.crossItemC.selectionModel.select(selectedItemC)
                        }
                    }
                    2 -> {
                        //设置配置项2

                        crossRuleRow.crossItemB = this.createChoiceBox(255.0, realOptionList, rowSelectedList[i])

                    }
                    3 -> {
                        //设置配置项3

                        crossRuleRow.crossItemC = this.createChoiceBox(255.0, realOptionList, rowSelectedList[i])
                    }
                    else -> {
                    }
                }
                j++
            }

            crossRowList.add(crossRuleRow)
        }

        crossTableView.isEditable = true
        crossTableView.items.addAll(crossRowList)
    }

    /**
     * 刷新crossTableView
     */
    private fun refreshCrossTableView(){

    }

    /**
     * 初始化常规规则的表格的表头
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
     * 生成文本的TableColumn
     */
    private fun createChoiceBox(prefWidth: Double, optionList: MutableList<String>, selectedOption: String): ChoiceBox<String> {
        return ChoiceBox(FXCollections.observableArrayList(optionList)).also {
            it.prefWidth = prefWidth
            it.prefHeight(30.0)
            it.selectionModel.select(selectedOption)
            /*it.selectionModel.selectedItemProperty().addListener { observable, oldValue, newValue ->
                crossRowList.forEach {

                }
            }*/
        }
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
     * 生成ChoiceBox的TableColumn
     */
    private fun createChoiceBoxTableColumn(prefWidth: Double, text: String, property: String): TableColumn<CrossRuleRow, ChoiceBox<String>> {
        val tableColumn = TableColumn<CrossRuleRow, ChoiceBox<String>>(text)
        tableColumn.prefWidth = prefWidth
        tableColumn.isSortable = false
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