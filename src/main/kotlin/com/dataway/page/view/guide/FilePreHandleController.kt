package com.dataway.page.view.guide

import com.dataway.page.primaryStageName
import com.dataway.page.view.selfdefine.StageManager
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.AnchorPane
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
    }
}