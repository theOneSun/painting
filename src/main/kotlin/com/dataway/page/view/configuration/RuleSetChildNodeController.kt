package com.dataway.page.view.configuration

import com.dataway.page.model.CrossRuleRow
import com.dataway.page.model.NormalRuleRow
import com.dataway.page.util.PropsUtils
import com.dataway.page.view.selfdefine.BottomAction
import com.dataway.page.view.selfdefine.CURRENT_BOTTOM_ACTION
import com.dataway.page.view.selfdefine.LeoContext
import com.dataway.page.view.selfdefine.RULE_CHECK_MAX_SCALE
import com.dataway.page.view.selfdefine.RULE_CHECK_MIN_SCALE
import com.dataway.page.view.selfdefine.RULE_COLUMN_NAMES
import com.dataway.page.view.selfdefine.RULE_COLUMN_VALUES
import com.dataway.page.view.selfdefine.RULE_CROSS_COLUMNS
import com.dataway.page.view.selfdefine.RULE_CROSS_MAX_SCALE
import com.dataway.page.view.selfdefine.RULE_INCLUDE_COLUMNS
import com.dataway.page.view.selfdefine.RULE_PREFIX
import com.dataway.page.view.selfdefine.RULE_SET_CONTROLLER
import com.dataway.page.view.selfdefine.RULE_TOP_COLUMNS
import com.dataway.page.view.selfdefine.SELECTED_RULE
import com.dataway.page.view.selfdefine.SELECTED_RULE_PARENT_RULE_SET
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.control.ChoiceBox
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.control.cell.TextFieldTableCell
import javafx.util.converter.DoubleStringConverter
import jodd.props.Props
import jodd.props.PropsConverter
import org.apache.commons.lang3.StringUtils
import org.springframework.util.ObjectUtils
import java.io.File
import java.io.FileWriter
import java.net.URL
import java.util.Collections
import java.util.ResourceBundle

/**
 * @author sunjian.
 */
class RuleSetChildNodeController : Initializable, BottomAction {

    @FXML
    private lateinit var ruleNameTextField: TextField
    @FXML
    private lateinit var normalTableView: TableView<NormalRuleRow>
    @FXML
    private lateinit var crossTableView: TableView<CrossRuleRow>
    @FXML
    private lateinit var ruleKeyWordTextField: TextField//暂时不做处理
    //规则集控制器
    private lateinit var ruleSetController: RuleSetController

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
    private val configPath = "${System.getProperty("user.dir")}/conf"
    //是否是编辑
    private var editPage: Boolean = true

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        //底部
        LeoContext.save(CURRENT_BOTTOM_ACTION, this)
        //规则集控制器
        ruleSetController = LeoContext.getValue(RULE_SET_CONTROLLER) as RuleSetController
        //判断是编辑页还是添加规则
        ruleName = LeoContext.getValue(SELECTED_RULE) as String?
        ruleSetName = LeoContext.getValue(SELECTED_RULE_PARENT_RULE_SET) as String

        if (StringUtils.isBlank(ruleName)) {
            editPage = false
            //初始化表格数据
            initNormalTableView()
            //初始化交叉项表格
            initCrossTableView()
        } else {
            showData()
        }


        /*//todo 根据规则集名称查询配置文件,返回props对象

        ruleSetProps.load(File("$configPath/$ruleSetName.props"))
        val columns = ruleSetProps.getValue("$RULE_PREFIX.$ruleName.$RULE_COLUMN_NAMES")
        val includeColumns = ruleSetProps.getValue("$RULE_PREFIX.$ruleName.$RULE_INCLUDE_COLUMNS")

        //规则名称
        ruleNameTextField.text = ruleName

        //循环显示列数据tableView
        convertColumnsToList(columns, includeColumns)

        //初始化表格数据
        initNormalTableView()
        //todo 初始化交叉项表格
        initCrossTableView()
        //todo 根据规则名称查询生效关键字*/

    }

    /**
     * 展示数据
     */
    private fun showData() {
        normalTableView.columns.clear()
        normalTableView.items.clear()

        ruleSetProps.load(File("$configPath/$ruleSetName.props"))
        val columns = ruleSetProps.getValue("$RULE_PREFIX.$ruleName.$RULE_COLUMN_NAMES")
        val includeColumns = ruleSetProps.getValue("$RULE_PREFIX.$ruleName.$RULE_INCLUDE_COLUMNS")

        //规则名称
        ruleNameTextField.text = ruleName
        //生效关键字
        //ruleKeyWordTextField.text =

        //循环显示列数据tableView
        convertColumnsToList(columns, includeColumns)

        //初始化表格数据
        initNormalTableView()
        //todo 初始化交叉项表格
        initCrossTableView()
        showCrossTableView()
        //todo 根据规则名称查询生效关键字
    }

    /**
     * 初始化交叉规则的表格的表头
     */
    private fun initCrossTableView() {
        val column1 = this.createChoiceBoxTableColumn(230.0, "交叉项1", "crossItemA")
        val column2 = this.createChoiceBoxTableColumn(230.0, "交叉项2", "crossItemB")
        val column3 = this.createChoiceBoxTableColumn(230.0, "交叉项3", "crossItemC")

        val maxScaleColumn = TableColumn<CrossRuleRow, Double>("交叉最大比例")
        maxScaleColumn.prefWidth = 180.0
        maxScaleColumn.isSortable = false
        maxScaleColumn.isEditable = true
        maxScaleColumn.cellFactory = TextFieldTableCell.forTableColumn(DoubleStringConverter())
        maxScaleColumn.cellValueFactory = PropertyValueFactory("maxScale")

        val deleteColumn = TableColumn<CrossRuleRow, String>("删除")
        deleteColumn.prefWidth = 100.0
        deleteColumn.isSortable = false
        deleteColumn.cellValueFactory = PropertyValueFactory("deleteButton")

        val addButtonColumn = TableColumn<CrossRuleRow, String>("添加")
        addButtonColumn.prefWidth = 48.0
        addButtonColumn.isSortable = false
        addButtonColumn.cellValueFactory = PropertyValueFactory("addButton")
        crossTableView.columns.addAll(column1, column2, column3, maxScaleColumn, deleteColumn, addButtonColumn)
    }

    /**
     * 展示交叉的数据
     */
    private fun showCrossTableView() {
        //读取常规配置的列名
        val names = ruleSetProps.getValue("$RULE_PREFIX.$ruleName.$RULE_COLUMN_NAMES")

        //读取已有的配置
        val crossColumnsMap = PropsUtils.getMapByPrefix(ruleSetProps, "$RULE_PREFIX.$ruleName.$RULE_CROSS_COLUMNS")
        println(crossColumnsMap)
        val optionList = names.split(",") as ArrayList
        var groupIndex = 0
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
                /*val removeList = arrayListOf<String>().also { it.addAll(rowSelectedList);it.remove(rowSelectedList[i]);it.remove("") }
                val realOptionList = arrayListOf<String>().also { it.addAll(optionList) }
                realOptionList.removeAll(removeList)*/
                when (j) {
                    1 -> {
                        /*crossRuleRow.crossItemA = this.createChoiceBox(255.0, realOptionList, rowSelectedList[i])
                        //选择的值改变的监听器
                        crossRuleRow.crossItemA.selectionModel.selectedItemProperty().addListener { observable, oldValue, newValue ->
                            //B列,C列增加oldValue,不可选newValue.B列需要增加本身的项,C列增加本身的项
                            //B列
                            val selectedItemB = crossRuleRow.crossItemB.selectionModel.selectedItem
                            println("selectedItemB$selectedItemB")

                            val changeRemoveListB = arrayListOf<String>().also {
                                //在这个集合里的是不可选的
                                it.addAll(rowSelectedList)
                                //原来的值可以展示了
                                it.remove(oldValue)
                                //空串一直可选
                                it.remove("")
                                //B列本身的数据可选
                                it.remove(selectedItemB)
                                //新选的项也不可选
                                it.add(newValue)
                            }
                            val changeOptionListB = arrayListOf<String>().also { it.addAll(optionList) }
                            changeOptionListB.removeAll(changeRemoveListB)
                            println("changeOptionListB$changeOptionListB")
                            crossRuleRow.crossItemB.also {
                                it.items.clear()
                                it.items.addAll(changeOptionListB)
                                it.selectionModel.select(selectedItemB)
                            }

                            //C列
                            val selectedItemC = crossRuleRow.crossItemC.selectionModel.selectedItem
                            println("selectedItemC$selectedItemC")
                            val changeRemoveListC = arrayListOf<String>().also {
                                //在这个集合里的是不可选的
                                it.addAll(rowSelectedList)
                                //原来的值可以展示了
                                it.remove(oldValue)
                                //空串一直可选
                                it.remove("")
                                //B列本身的数据可选
                                it.remove(selectedItemC)
                                //新选的项也不可选
                                it.add(newValue)
                            }
                            val changeOptionListC = arrayListOf<String>().also { it.addAll(optionList) }
                            changeOptionListC.removeAll(changeRemoveListC)
                            println("changeOptionListC$changeOptionListC")

                            crossRuleRow.crossItemC.also {
                                it.items.clear()
                                it.items.addAll(changeOptionListC)
                                it.selectionModel.select(selectedItemC)
                            }
                        }*/
                        crossRuleRow.crossItemA = this.createChoiceBox(230.0, optionList, rowSelectedList[i])
                    }
                    2 -> {
                        //设置配置项2
//                        crossRuleRow.crossItemB = this.createChoiceBox(255.0, realOptionList, rowSelectedList[i])
                        crossRuleRow.crossItemB = this.createChoiceBox(230.0, optionList, rowSelectedList[i])

                        //值改变监听
                        /*crossRuleRow.crossItemB.selectionModel.selectedItemProperty().addListener { observable, oldValue, newValue ->
                            //A列,C列增加oldValue,不可选newValue.A列需要增加本身的项,C列增加本身的项
                            //A列
                            val selectedItemA = crossRuleRow.crossItemA.selectionModel.selectedItem
                            println("selectedItemA$selectedItemA")

                            val changeRemoveListA = arrayListOf<String>().also {
                                //在这个集合里的是不可选的
                                it.addAll(rowSelectedList)
                                //原来的值可以展示了
                                it.remove(oldValue)
                                //空串一直可选
                                it.remove("")
                                //B列本身的数据可选
                                it.remove(selectedItemA)
                                //新选的项也不可选
                                it.add(newValue)
                            }
                            val changeOptionListA = arrayListOf<String>().also { it.addAll(optionList) }
                            changeOptionListA.removeAll(changeRemoveListA)
                            println("changeOptionListA$changeOptionListA")
                            crossRuleRow.crossItemA.also {
                                it.items.clear()
                                it.items.addAll(changeOptionListA)
                                it.selectionModel.select(selectedItemA)
                            }

                            //B列
                            val selectedItemB = crossRuleRow.crossItemB.selectionModel.selectedItem
                            println("selectedItemB$selectedItemB")

                            val changeRemoveListB = arrayListOf<String>().also {
                                //在这个集合里的是不可选的
                                it.addAll(rowSelectedList)
                                //原来的值可以展示了
                                it.remove(oldValue)
                                //空串一直可选
                                it.remove("")
                                //B列本身的数据可选
                                it.remove(selectedItemB)
                                //新选的项也不可选
                                it.add(newValue)
                            }
                            val changeOptionListB = arrayListOf<String>().also { it.addAll(optionList) }
                            changeOptionListB.removeAll(changeRemoveListB)
                            println("changeOptionListB$changeOptionListB")
                            crossRuleRow.crossItemB.also {
                                it.items.clear()
                                it.items.addAll(changeOptionListB)
                                it.selectionModel.select(selectedItemB)
                            }
                        }*/
                    }
                    3 -> {
                        //设置配置项3
//                        crossRuleRow.crossItemC = this.createChoiceBox(255.0, realOptionList, rowSelectedList[i])
                        crossRuleRow.crossItemC = this.createChoiceBox(230.0, optionList, rowSelectedList[i])

                        //值改变监听
                        /*crossRuleRow.crossItemC.selectionModel.selectedItemProperty().addListener { observable, oldValue, newValue ->
                            //A列,C列增加oldValue,不可选newValue.A列需要增加本身的项,C列增加本身的项
                            //A列
                            val selectedItemA = crossRuleRow.crossItemA.selectionModel.selectedItem
                            println("selectedItemA$selectedItemA")

                            val changeRemoveListA = arrayListOf<String>().also {
                                //在这个集合里的是不可选的
                                it.addAll(rowSelectedList)
                                //原来的值可以展示了
                                it.remove(oldValue)
                                //空串一直可选
                                it.remove("")
                                //B列本身的数据可选
                                it.remove(selectedItemA)
                                //新选的项也不可选
                                it.add(newValue)
                            }
                            val changeOptionListA = arrayListOf<String>().also { it.addAll(optionList) }
                            changeOptionListA.removeAll(changeRemoveListA)
                            println("changeOptionListA$changeOptionListA")
                            crossRuleRow.crossItemA.also {
                                it.items.clear()
                                it.items.addAll(changeOptionListA)
                                it.selectionModel.select(selectedItemA)
                            }

                            //C列
                            val selectedItemC = crossRuleRow.crossItemC.selectionModel.selectedItem
                            println("selectedItemC$selectedItemC")
                            val changeRemoveListC = arrayListOf<String>().also {
                                //在这个集合里的是不可选的
                                it.addAll(rowSelectedList)
                                //原来的值可以展示了
                                it.remove(oldValue)
                                //空串一直可选
                                it.remove("")
                                //B列本身的数据可选
                                it.remove(selectedItemC)
                                //新选的项也不可选
                                it.add(newValue)
                            }
                            val changeOptionListC = arrayListOf<String>().also { it.addAll(optionList) }
                            changeOptionListC.removeAll(changeRemoveListC)
                            println("changeOptionListC$changeOptionListC")

                            crossRuleRow.crossItemC.also {
                                it.items.clear()
                                it.items.addAll(changeOptionListC)
                                it.selectionModel.select(selectedItemC)
                            }
                        }*/
                    }
                    else -> {
                    }
                }
                j++
            }

            //交叉最大比例的输入框
            val keySplitList = entry.key.split(".")
            val lastKeyWord = keySplitList.last()
            crossRuleRow.maxScale = ruleSetProps.getValue("$RULE_PREFIX.$ruleName.$RULE_CROSS_MAX_SCALE.$lastKeyWord")?.toDouble()

            crossRuleRow.deleteButton = this.createCrossDeleteButton(100.0, 30.0, groupIndex++)
//            crossRuleRow.addButton = this.createCrossAddButton(48.0,30.0)
            crossRowList.add(crossRuleRow)
        }

        crossTableView.isEditable = true
        crossTableView.items.addAll(crossRowList)
    }

    /**
     * 刷新crossTableView
     */
    private fun refreshCrossTableView() {

    }

    /**
     * 初始化常规规则的表格的表头
     */
    private fun initNormalTableView() {

        val nameColumn = createStringTableColumn(200.0, "文件列名", "columnName")
        val includeColumn = TableColumn<NormalRuleRow, String>("统计")
        includeColumn.prefWidth = 80.0
        includeColumn.isSortable = false
//        includeColumn.cellFactory = CheckBoxTableCell.forTableColumn(includeColumn)
        includeColumn.cellValueFactory = PropertyValueFactory("include")

        val maxValueColumn = createDoubleTableColumn(100.0, "最大值", "maxValue")
        maxValueColumn.setOnEditCommit {
            val selectedIndex = normalTableView.selectionModel.selectedIndex
            normalColumnRowList[selectedIndex].maxValue =  it.newValue
        }
        val minValueColumn = createDoubleTableColumn(100.0, "最小值", "minValue")
        minValueColumn.setOnEditCommit {
            val selectedIndex = normalTableView.selectionModel.selectedIndex
            normalColumnRowList[selectedIndex].minValue =  it.newValue
        }

        val verifyColumn = createStringTableColumn(200.0, "数据校验值", "columnValues")
        verifyColumn.setOnEditCommit {
            val selectedIndex = normalTableView.selectionModel.selectedIndex
            normalColumnRowList[selectedIndex].columnValues =  it.newValue
        }

        val topValueColumn = createDoubleTableColumn(100.0, "取Top值", "columnTopValue")
        topValueColumn.setOnEditCommit {
            val selectedIndex = normalTableView.selectionModel.selectedIndex
            normalColumnRowList[selectedIndex].columnTopValue =  it.newValue
        }

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

        normalTableView.columns.also {
            it.clear()
            it.addAll(nameColumn, includeColumn, maxValueColumn, minValueColumn,
                    verifyColumn, topValueColumn, upColumn, downColumn, deleteColumn)
        }

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
        columnList.clear()
        normalColumnRowList.clear()
        list?.forEach { columnName ->
            columnList.add(columnName)
            val normalRuleRow = NormalRuleRow()
            //列名和统计包含列
            /* normalRuleRow.columnName = SimpleStringProperty(columnName)
             normalRuleRow.include.value = includeColumns?.contains(columnName)*/
            normalRuleRow.columnName = columnName
            normalRuleRow.include = CheckBox()
            normalRuleRow.include.isSelected = includeColumns != null && includeColumns.contains(columnName)
            /*includeColumns?.contains(columnName)?.let {
                //normalRuleRow.include = it
                normalRuleRow.include = CheckBox().also { it.isSelected = true }
            }*/
            //最大最小值
            ruleSetProps.getValue("$RULE_PREFIX.$ruleName.$RULE_CHECK_MAX_SCALE.$columnName")?.toDouble()?.let {
                //                                normalRuleRow.maxValueProperty = SimpleDoubleProperty(it)
                normalRuleRow.maxValue = it
            }

            ruleSetProps.getValue("$RULE_PREFIX.$ruleName.$RULE_CHECK_MIN_SCALE.$columnName")?.toDouble()?.let {
                //                                normalRuleRow.minValueProperty = SimpleDoubleProperty(it)
                normalRuleRow.minValue = it
            }

            //数据校验值
            ruleSetProps.getValue("$RULE_PREFIX.$ruleName.$RULE_COLUMN_VALUES.$columnName")?.let {
                //                                normalRuleRow.columnValuesProperty = SimpleStringProperty(it)
                normalRuleRow.columnValues = it
            }
            //top值
            ruleSetProps.getValue("$RULE_PREFIX.$ruleName.$RULE_TOP_COLUMNS.$columnName")?.toDouble()?.let {
                //                                normalRuleRow.columnTopValueProperty = SimpleDoubleProperty(it)
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
     * 生成常规页面中的删除按钮
     */
    private fun createNormalDeleteButton(index: Int): Button {
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
     * 生成交叉页面中的删除按钮
     */
    private fun createCrossDeleteButton(prefWidth: Double, prefHeight: Double, index: Int): Button {
        return Button("删除").also {
            it.prefWidth = prefWidth
            it.prefHeight = prefHeight
            it.setOnAction {
                //设置被选中的行
                crossTableView.selectionModel.select(index)
                crossRowList.removeAt(index)
                refreshCrossRowList()
            }
        }
    }

    /**
     * 生成交叉页面中的添加按钮
     */
    private fun createCrossAddButton(prefWidth: Double, prefHeight: Double): Button {
        return Button("+").also {
            it.prefWidth = prefWidth
            it.prefHeight = prefHeight
            it.setOnAction {
                //读取常规配置的列名
                val names = ruleSetProps.getValue("$RULE_PREFIX.$ruleName.$RULE_COLUMN_NAMES")

                //读取已有的配置
                val crossColumnsMap = PropsUtils.getMapByPrefix(ruleSetProps, "$RULE_PREFIX.$ruleName.$RULE_CROSS_COLUMNS")
                println("点急了添加$crossColumnsMap")
                val optionList = names.split(",") as ArrayList
                //设置被选中的行
                val crossRuleRow = CrossRuleRow()
                crossRuleRow.crossItemA = this.createChoiceBox(230.0, optionList, "")
                crossRuleRow.crossItemB = this.createChoiceBox(230.0, optionList, "")
                crossRuleRow.crossItemC = this.createChoiceBox(230.0, optionList, "")
                crossRuleRow.deleteButton = this.createCrossDeleteButton(100.0, 30.0, crossRowList.size)
//                crossRuleRow.addButton = this.createCrossAddButton(prefWidth,prefHeight)
                crossRowList.add(crossRuleRow)
                //跟新数据
                showCrossTableView()
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
            normalRuleRow.deleteButton = createNormalDeleteButton(normalColumnRowList.indexOf(normalRuleRow))
        }
        normalTableView.items.also {
            it.clear()
            it.addAll(normalColumnRowList)
        }
    }

    /**
     * 位置操作后要对按钮的index值进行更新
     */
    private fun refreshCrossRowList() {
        crossRowList.forEach { crossRuleRow: CrossRuleRow ->
            crossRuleRow.deleteButton = createCrossDeleteButton(48.0, 30.0, crossRowList.indexOf(crossRuleRow))
        }
        crossTableView.items.also {
            it.clear()
            it.addAll(crossRowList)
        }
    }

    override fun doCancel() {
        //判断是编辑还是新增
        //新增初始化值,编辑重新加载
        //重新加载此页
        normalColumnRowList.clear()
        crossRowList.clear()
        normalTableView.columns.clear()
        crossTableView.columns.clear()
        crossTableView.items.clear()
        if (editPage) {

            showData()
        } else {
            ruleNameTextField.text = ""
            ruleKeyWordTextField.text = ""
            normalTableView.items.clear()
            crossTableView.items.clear()
        }
    }

    override fun doSave() {
        this.doConfirm()
    }

    override fun doConfirm() {
        /*
        需要保存的:
        1.ruleName file.name的name
        2.所有文件列名 file.name.columns
        3.数据校验值 columnValues.人群
        4.统计(包含列) includeColumns
        5.top值 topColumns.人群
        6.最大值 checkMaxScale.人群
        7.最小值 checkMinScale.人群
         */
        /*
        分为编辑还是新增
         */
        if (editPage) {
            //编辑
            /*
            判断是否改了规则名字
             */
            if (ruleName == ruleNameTextField.text) {
                //没改名字
                ruleName?.let { saveData(it) }
            } else {
                //改了名字

            }
            showData()
        } else {
            ruleNameTextField.text = ""
            ruleKeyWordTextField.text = ""
            normalTableView.items.clear()
            crossTableView.items.clear()
        }

    }

    /**
     * 保存数据
     */
    private fun saveData(ruleName: String) {
        /*
        根据表格数据读取
         */
        val changedList = normalTableView.items

        /*
        需要保存的:
        1.ruleName file.name的name
        2.所有文件列名 file.name.columns
        3.数据校验值 columnValues.人群
        4.统计(包含列) includeColumns
        5.top值 topColumns.人群
        6.最大值 checkMaxScale.人群
        7.最小值 checkMinScale.人群
         */
//        val saveProps = Props()
        val keyPre = "$RULE_PREFIX.$ruleName"
        val columnNames = StringBuilder()
        val includeColumns = StringBuilder()
        changedList.forEach { normalRuleRow ->
            val columnName = normalRuleRow.columnName
            //追加列
            columnNames.append("$columnName,")
            //数据校验值
            normalRuleRow.columnValues?.let {
                //            normalRuleRow.columnValuesProperty?.let {
                ruleSetProps.setValue("$keyPre.$RULE_COLUMN_VALUES.$ruleName", normalRuleRow.columnValues)
//                ruleSetProps.setValue("$keyPre.$RULE_COLUMN_VALUES.$ruleName", normalRuleRow.columnValues.toString())
//                ruleSetProps.setValue("$keyPre.$RULE_COLUMN_VALUES.$ruleName", normalRuleRow.columnValuesProperty.toString())
            }
            //包含列

            if (normalRuleRow.include.isSelected) {
                includeColumns.append("$columnName,")
            }
            //最大值
            if (normalRuleRow.maxValue != null) {
//            if (normalRuleRow.maxValueProperty != null) {
                ruleSetProps.setValue("$keyPre.$RULE_CHECK_MAX_SCALE.$columnName", normalRuleRow.maxValue.toString())
//                ruleSetProps.setValue("$keyPre.$RULE_CHECK_MAX_SCALE.$columnName", normalRuleRow.maxValueProperty.toString())
            }
            //最小值
            if (normalRuleRow.minValue != null) {
//            if (normalRuleRow.minValueProperty != null) {
                ruleSetProps.setValue("$keyPre.$RULE_CHECK_MIN_SCALE.$columnName", normalRuleRow.minValue.toString())
//                ruleSetProps.setValue("$keyPre.$RULE_CHECK_MIN_SCALE.$columnName", normalRuleRow.minValueProperty.toString())
            }

            //top值
            if (normalRuleRow.columnTopValue != null) {
//            if (normalRuleRow.columnTopValueProperty != null) {
                ruleSetProps.setValue("$keyPre.$RULE_TOP_COLUMNS.$columnName", normalRuleRow.columnTopValue.toString())
//                ruleSetProps.setValue("$keyPre.$RULE_TOP_COLUMNS.$columnName", normalRuleRow.columnTopValueProperty.toString())
            }
        }
        //所有列
        if (!ObjectUtils.isEmpty(columnNames)) {
            ruleSetProps.setValue("$keyPre.$RULE_COLUMN_NAMES", columnNames.substring(0, columnNames.length - 1).toString())
        }
        //包含列
        if (!ObjectUtils.isEmpty(includeColumns)) {
            ruleSetProps.setValue("$keyPre.$RULE_INCLUDE_COLUMNS", includeColumns.substring(0, includeColumns.length - 1).toString())
        }

        val orderedProperties = PropsUtils.convertOrderProperties(ruleSetProps)
        PropsConverter.convert(FileWriter("$configPath/$ruleSetName.props"), orderedProperties)
    }
}