package com.dataway.page.view.guide

import com.dataway.page.view.selfdefine.FILE_HANDLE_FINISH
import com.dataway.page.view.selfdefine.FILE_HANDLE_ING
import com.dataway.page.view.selfdefine.FILE_HANDLE_READY
import com.dataway.page.view.selfdefine.FileHandleProgressTask
import com.dataway.page.view.selfdefine.HANDLE_FILE
import com.dataway.page.view.selfdefine.LeoContext
import com.dataway.page.view.selfdefine.StageManager
import com.dataway.page.view.selfdefine.primaryStageName
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ProgressBar
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.ColumnConstraints
import javafx.scene.layout.GridPane
import javafx.scene.layout.RowConstraints
import org.springframework.util.ObjectUtils
import java.io.File
import java.net.URL
import java.util.ResourceBundle

/**
 * 文件预处理控制器
 * @author sunjian.
 */
class FilePreHandleController : Initializable {
    @FXML
    private lateinit var nextButton: Button
    @FXML
    private lateinit var cancelButton: Button
    @FXML
    private lateinit var preHandleGridPane: GridPane

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        //下一步按钮
        nextButton.setOnAction {
            run {
                println("下一步跳到处理结果")
                val primaryStage = StageManager.getStageByName(primaryStageName)
                val loader = FXMLLoader()

                loader.location = javaClass.getResource("/com/dataway/page/view/HandleResult.fxml")
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

        //初始化gridPane
        initGridPane()
    }


    /**
     * 初始化gridPane
     */
    private fun initGridPane() {
        //获取预处理的文件集合,调用处理方法
        val fileList = LeoContext.getValue(HANDLE_FILE) as MutableList<File>
        //todo 调用处理的方法

        // 初始化gridPane
        val nameColumn = ColumnConstraints()
        nameColumn.percentWidth = 30.0

        val statusColumn = ColumnConstraints()
        nameColumn.percentWidth = 10.0

        val pathColumn = ColumnConstraints()
        nameColumn.percentWidth = 30.0

        val progressColumn = ColumnConstraints()
        nameColumn.percentWidth = 20.0

        val undeterminedColumn = ColumnConstraints()
        nameColumn.percentWidth = 10.0

        preHandleGridPane.columnConstraints.addAll(nameColumn, statusColumn, pathColumn, progressColumn, undeterminedColumn)
        //添加标题行
        val rowElement = RowConstraints(30.0)
        rowElement.maxHeight = 50.0
        preHandleGridPane.rowConstraints.add(rowElement)

        preHandleGridPane.add(this.getLabel(531.0,"name",null), 0, 0)
        preHandleGridPane.add(this.getLabel(177.0,"status", null), 1, 0)
        preHandleGridPane.add(this.getLabel(531.0,"path",null), 2, 0)
        preHandleGridPane.add(this.getLabel(354.0,"progress",null), 3, 0)

        //根据之前选中的文件渲染
        if (!ObjectUtils.isEmpty(fileList)) {
            for (i in fileList.indices) {
                //文件名label
                val nameLabel = this.getLabel(531.0, fileList[i].name, null)
                //处理状态label
                val statusLabel = this.getLabel(177.0, FILE_HANDLE_READY, "status$i")
                //路径label
                val pathLabel = this.getLabel(531.0, fileList[i].path, null)
                //进度条
                val progressBar = getProgressBar()
                //进度百分比显示
                val percentLabel = this.getLabel(177.0, "0%", "percentLabel$i")

                // 将组件添加到gridPane中
                val newRow = RowConstraints(30.0)
                newRow.maxHeight = 50.0
                preHandleGridPane.rowConstraints.add(rowElement)
                preHandleGridPane.add(nameLabel, 0, i + 1)
                preHandleGridPane.add(statusLabel, 1, i + 1)
                preHandleGridPane.add(pathLabel, 2, i + 1)
                preHandleGridPane.add(progressBar, 3, i + 1)
                preHandleGridPane.add(percentLabel, 4, i + 1)

                // 实现进度条和后面百分比label的绑定
//                val fileLength = fileList[i].length().toInt()
                val fileHandleProgressTask = FileHandleProgressTask(fileList[i])
                progressBar.progressProperty().also {
                    it.bind(fileHandleProgressTask.progressProperty())
                    it.addListener { _, _, newValue ->
                        percentLabel.text = "${newValue.toDouble() * 100}%"
                    }
                }
                // 实现百分比和前面状态label的绑定
                statusLabel.textProperty().addListener { _, _, newValue ->
                    when{
                        newValue.toDouble() == 0.0 ->statusLabel.text = FILE_HANDLE_READY
                        newValue.toDouble() == 100.0 ->statusLabel.text = FILE_HANDLE_FINISH
                        else -> statusLabel.text = FILE_HANDLE_ING
                    }
                }
            }
        }
    }

    private fun getProgressBar(): ProgressBar {
        val progressBar = ProgressBar(0.0)
        progressBar.prefWidth = 354.0
        progressBar.prefHeight = 30.0
        progressBar.maxHeight = 50.0
        return progressBar
    }

    private fun getLabel(prefWidth: Double, text: String, id: String?): Label {
        val label = Label(text)
        if (id != null) {
            label.id = id
        }
        label.prefWidth = prefWidth
        label.prefHeight = 30.0
        label.maxHeight = 50.0
        return label
    }
}