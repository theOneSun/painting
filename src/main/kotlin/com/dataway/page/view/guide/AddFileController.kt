package com.dataway.page.view.guide

import com.dataway.page.model.FileData
import com.dataway.page.primaryStageName
import com.dataway.page.view.selfdefine.StageManager
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.layout.AnchorPane
import javafx.stage.FileChooser
import javafx.stage.Stage
import java.io.File
import java.net.URL
import java.util.ResourceBundle


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

    @FXML
    private lateinit var fileTableView: TableView<FileData>

    /**
     * 选中的文件
     */
    private var fileList: MutableList<File> = arrayListOf()
    /**
     * 转换的可以显示的文件对象
     */
    private var fileDataList:MutableList<FileData> = arrayListOf()

    override fun initialize(location: URL?, resources: ResourceBundle?) {

        //初始化表头
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
                if (!fileList.isEmpty()) {
                   addTableViewData()
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
     * 初始化tableView
     */
    private fun initTableView() {
        val nameColumn = TableColumn<FileData, String>("name")
        nameColumn.prefWidth = 600.0
        nameColumn.cellValueFactory = PropertyValueFactory("name")
        nameColumn.cellValueFactory = PropertyValueFactory("name")

        val pathColumn = TableColumn<FileData, String>("where")
        pathColumn.prefWidth = 750.0
        pathColumn.cellValueFactory = PropertyValueFactory("path")

        val buttonColumn = TableColumn<FileData, String>("删除")
        buttonColumn.prefWidth = 150.0
        buttonColumn.isSortable = false
        buttonColumn.cellValueFactory = PropertyValueFactory("deleteButton")

        fileTableView.columns.addAll(nameColumn, pathColumn, buttonColumn)

    }

    /**
     * 给tableView添加数据
     */
    private fun addTableViewData(): MutableList<File> {
        fileDataList.clear()
        for (file in fileList) {
            fileDataList.add(FileData(file.name, file.path, deleteButton(fileList.indexOf(file))))
        }
        fileTableView.items.also { it.clear();it.addAll(fileDataList) }
        return fileList
    }

    /**
     * 删除按钮
     * @param index 当前应该是第几行
     */
    private fun deleteButton(index: Int): Button {

        return Button("删除").also {
            it.prefWidth = 150.0
            it.prefHeight = 30.0
            it.setOnAction {
                //设置被选中的行
                fileTableView.selectionModel.select(index)
                //删除对应的数据
                //重新渲染tableView
                val tempFileList: MutableList<File> = mutableListOf()
                tempFileList.addAll(fileList)
                tempFileList.removeAt(index)
                fileList = tempFileList
                addTableViewData()
            }
        }
    }
}