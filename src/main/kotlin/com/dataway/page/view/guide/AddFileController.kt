package com.dataway.page.view.guide

import com.dataway.page.model.FileData
import com.dataway.page.primaryStageName
import com.dataway.page.view.selfdefine.ButtonCell
import com.dataway.page.view.selfdefine.StageManager
import javafx.beans.value.ObservableValue
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.layout.AnchorPane
import javafx.stage.FileChooser
import javafx.stage.Stage
import javafx.util.Callback
import java.io.File
import java.net.URL
import java.util.ResourceBundle
import javafx.beans.property.SimpleBooleanProperty
import com.sun.xml.internal.fastinfoset.alphabet.BuiltInRestrictedAlphabets.table
import javafx.scene.control.TableCell


/**
 * 添加文件控制器
 * @author sunjian.
 */
class AddFileController : Initializable {

    @FXML
    private lateinit var addFileButton: Button
    @FXML
    private lateinit var nextButton: Button
    @FXML
    private lateinit var cancelButton: Button
    //    @FXML
//    private lateinit var fileGridPane: GridPane
    @FXML
    private lateinit var fileTableView: TableView<File>
//    private lateinit var fileTableView: TableView<FileData>

    private var fileList: MutableList<File> = ArrayList()

    override fun initialize(location: URL?, resources: ResourceBundle?) {

        //初始化gridPane
//        initGridPane()
        initTableView()

        //添加文件按钮fileList
        addFileButton.setOnAction {
            run {
                println("点击了添加文件")
                val fileChooser = FileChooser()
                fileChooser.title = "选择文件"
                val chooserStage = Stage()
                // 打开可以多选文件的是视窗
                fileList = fileChooser.showOpenMultipleDialog(chooserStage)
//                var tempFileList = fileList
//                addFileGridPaneData(fileList)
                if (!fileList.isEmpty()) {
                    val fileDataList = addTableViewData()
//                    addDeleteAction(fileDataList)

                }
            }
        }

        //下一步按钮
        nextButton.setOnAction {
            run {
                // 跳到添加规则集页面
                println("下一步跳到添加规则集")
                val primaryStage = StageManager.getStageByName(primaryStageName)
                val loader = FXMLLoader()

                loader.location = javaClass.getResource("/com/dataway/page/view/AddRuleSet.fxml")
                val rootLayout = loader.load<AnchorPane>()

                // Show the scene containing the root layout.
                val scene = Scene(rootLayout)
                primaryStage?.scene = scene
//                primaryStage?.show()
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
    }

    /**
     * 初始化gridPane
     */
//    private fun initGridPane() {
//        //初始化表格的列和表头
//        fileGridPane.columnConstraints.addAll(ColumnConstraints().also { it.percentWidth = 40.0 }, ColumnConstraints().also { it.percentWidth = 50.0 }, ColumnConstraints().also { it.percentWidth = 10.0 })
//        fileGridPane.rowConstraints.add(RowConstraints(30.0))
//        fileGridPane.also {
//            it.add(firstColumnLabel("name"), 0, 0)
//            it.add(secondColumnLabel("where"), 1, 0)
//        }
//    }

    /**
     * 初始化tableView
     */
    private fun initTableView() {
//        val nameColumn = TableColumn<FileData, String>("name")
        val nameColumn = TableColumn<File, String>("name")
        nameColumn.prefWidth = 600.0
        nameColumn.cellValueFactory = PropertyValueFactory("name")
        nameColumn.cellValueFactory = PropertyValueFactory("name")
        val pathColumn = TableColumn<File, String>("where")
//        val pathColumn = TableColumn<FileData, String>("where")
        pathColumn.prefWidth = 750.0
        pathColumn.cellValueFactory = PropertyValueFactory("path")

//        val buttonColumn = TableColumn<File, Button>()
//        buttonColumn.prefWidth = 150.0
//        buttonColumn.cellValueFactory = PropertyValueFactory("deleteButton")
//        buttonColumn.cellFactory =
        //todo 测试新方法行不行
        val buttonColumn = TableColumn<File, Boolean>("删除")
        buttonColumn.isSortable = false

        buttonColumn.setCellValueFactory({ features -> SimpleBooleanProperty(features.value != null) })

        buttonColumn.setCellFactory({ ButtonCell() })

        fileTableView.columns.addAll(nameColumn, pathColumn, buttonColumn)

    }

    /**
     * 给tableView添加数据
     */
    private fun addTableViewData() :MutableList<File>{
//        val fileDataList: MutableList<FileData> = mutableListOf()
        for (file in fileList) {


            /*deleteButton.setOnAction {
                fileList.remove(file)
                addTableViewData()
            }*/

//            fileDataList.add(FileData(file.name, file.path, deleteButton()))
        }
//        val list = FXCollections.observableArrayList(fileDataList)

        fileTableView.items.also { it.clear();it.addAll(fileList) }

        return fileList
    }
    /*private fun addDeleteAction(fileDataList:MutableList<File>){
        for (fileData in fileDataList){
            fileData.deleteButton.setOnAction {
                fileList.removeAt(fileDataList.indexOf(fileData))
                addTableViewData()
            }
        }
    }*/

    /**
     * gridPane添加数据
     */
//    private fun addFileGridPaneData(fileList: MutableList<File>) {
//        if (fileList.isEmpty()) {
//            // 未选中文件
//            println("未选择文件")
//        } else {
//            val size = fileList.size
//            println("选择的文件的长度是$size")
//            // 展示选中的文件
//            var insertRow = 1
//            for (file in fileList) {
//                println(file)
//                val deleteButton = deleteButton()
//                fileGridPane.also {
//                    it.add(firstColumnLabel(file.name), 0, insertRow)
//                    it.add(secondColumnLabel(file.path), 1, insertRow)
//                    it.add(deleteButton.also {
//                        it.setOnAction {
//                            fileList.remove(file)
//
//                        }
//                    }, 2, insertRow)
//                }
//                insertRow++
//            }
//
//        }
//    }

    /**
     * 第一列的label
     */
    private fun firstColumnLabel(text: String): Label {
        return Label(text).also {
            it.prefWidth = 600.0
            it.prefHeight = 30.0
        }
    }

    /**
     * 第二列的label
     */
    private fun secondColumnLabel(text: String): Label {
        return Label(text).also {
            it.prefWidth = 750.0
            it.prefHeight = 30.0
        }
    }

    /**
     * 删除按钮
     */
    private fun deleteButton(): Button {
        return Button("删除").also {
            it.prefWidth = 150.0
            it.prefHeight = 30.0
        }
    }
}