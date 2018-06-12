package com.dataway.page.view.guide

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
import javax.lang.model.util.AbstractElementVisitor6

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
    private fun initGridPane(){
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

        preHandleGridPane.columnConstraints.addAll(nameColumn,statusColumn,pathColumn,progressColumn,undeterminedColumn)
        //添加标题行
        val rowElement = RowConstraints(30.0)
        rowElement.maxHeight = 50.0
        preHandleGridPane.rowConstraints.add(rowElement)

        preHandleGridPane.add(nameLabel("name"),0,0)
        preHandleGridPane.add(statusLabel("status","statusHeader"),1,0)
        preHandleGridPane.add(pathLabel("path"),2,0)
        preHandleGridPane.add(Label("progress").also {
            it.prefHeight = 30.0
            it.maxHeight = 50.0
            it.prefWidth = 354.0
        },3,0)

        if (!ObjectUtils.isEmpty(fileList)){
            for (i in fileList.indices){
                val nextRow = RowConstraints(30.0)
                nextRow.maxHeight = 50.0
                preHandleGridPane.rowConstraints.add(rowElement)
                preHandleGridPane.add(nameLabel(fileList[i].name),0,i+1)
                preHandleGridPane.add(statusLabel("未处理","handleStatus$i"),1,i+1)
                preHandleGridPane.add(pathLabel(fileList[i].path),2,i+1)
                preHandleGridPane.add(getProgressBar(),3,i+1)

            }
        }


    }

    private fun nameLabel(text:String):Label{
        val label = Label(text)
        label.prefWidth = 531.0
        label.prefHeight = 30.0
        label.maxHeight = 50.0
        return label
    }

    private fun statusLabel(text:String,id:String):Label{
        val label = Label(text)
        label.id = id
        label.prefWidth = 177.0
        label.prefHeight = 30.0
        label.maxHeight = 50.0
        return label

    }

    private fun pathLabel(text:String):Label{
        val label = Label(text)
        label.prefWidth = 531.0
        label.prefHeight = 30.0
        label.maxHeight = 50.0
        return label
    }

    private fun getProgressBar():ProgressBar{
        val progressBar = ProgressBar()
        progressBar.prefWidth = 354.0
        return progressBar
    }

}