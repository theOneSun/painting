package com.dataway.page.view.guide

import com.dataway.page.model.SupportLibrary
import com.dataway.page.primaryStageName
import com.dataway.page.view.selfdefine.StageManager
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.control.ChoiceBox
import javafx.scene.control.TableCell
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.CheckBoxTableCell
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.layout.AnchorPane
import javafx.util.StringConverter
import java.net.URL
import java.util.ResourceBundle

/**
 * 添加规则集控制器
 * @author sunjian.
 */
class AddRuleSetController : Initializable {
    @FXML
    private lateinit var nextButton: Button
    @FXML
    private lateinit var cancelButton: Button
    @FXML
    private lateinit var ruleSetChoiceBox: ChoiceBox<String>
    @FXML
    private lateinit var supportLibraryTable: TableView<SupportLibrary>

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        //下一步按钮
        nextButton.setOnAction {
            run {
                println("下一步跳到文件预处理页面")
                val primaryStage = StageManager.getStageByName(primaryStageName)
                val loader = FXMLLoader()

                loader.location = javaClass.getResource("/com/dataway/page/view/FilePreHandle.fxml")
                val rootLayout = loader.load<AnchorPane>()

                // Show the scene containing the root layout.
                val scene = Scene(rootLayout)
                primaryStage?.scene = scene
                primaryStage?.show()
            }
        }

        cancelButton.setOnAction {
            //点击取消关闭
            val primaryStage = StageManager.getStageByName(primaryStageName)
            val loader = FXMLLoader()

            loader.location = javaClass.getResource("/com/dataway/page/view/Index.fxml")
            val rootLayout = loader.load<AnchorPane>()
            val scene = Scene(rootLayout)
            primaryStage?.scene = scene
//            primaryStage?.close()
        }

        //规则集choiceBox
        ruleSetChoiceBox.items = FXCollections.observableArrayList("商业地产规则集")

        //初始化表格
        initTableView()
    }

    private fun initTableView() {
        //todo
        //获取选择的规则集
        //根据规则集查询需要的支持库 needSupportLibraryList
        val needSupportLibraryList: ObservableList<SupportLibrary> = FXCollections.observableArrayList()
//        needSupportLibraryList.add(SupportLibrary("appWeb使用习惯", true))
        needSupportLibraryList.add(SupportLibrary("appWeb使用习惯", getTrueCheckBox()))
        //根据本地支持库判断是否需要


        val nameColumn = TableColumn<SupportLibrary, String>("支持库")
        nameColumn.prefWidth = 400.0
        nameColumn.cellValueFactory = PropertyValueFactory("name")


        val existColumn = TableColumn<SupportLibrary, Boolean>("是否拥有")
        existColumn.prefWidth = 80.0
        existColumn.cellValueFactory = PropertyValueFactory("exist")
        existColumn.cellFactory = CheckBoxTableCell.forTableColumn(existColumn)

        supportLibraryTable.columns.addAll(nameColumn, existColumn)
        supportLibraryTable.isEditable = true
        supportLibraryTable.items.addAll(needSupportLibraryList)
    }

    fun showTableDate() {

    }

    private fun getTrueCheckBox():CheckBox{
        println("要添加复选框了")
        val checkBox = CheckBox()
        checkBox.prefWidth = 80.0
        checkBox.isIndeterminate = true
        checkBox.isSelected = true
        return checkBox
    }

}