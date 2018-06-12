package com.dataway.page.view

import com.dataway.page.view.selfdefine.StageManager
import com.dataway.page.view.selfdefine.primaryStageName
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.AnchorPane
import java.net.URL
import java.util.ResourceBundle

/**
 * @author sunjian.
 */
class IndexController : Initializable {
    @FXML
    private lateinit var guideButton: Button
    @FXML
    private lateinit var configurationButton: Button

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        guideButton.setOnAction {
            //todo goto guidePage
            val loader = FXMLLoader()
            loader.location = javaClass.getResource("/com/dataway/page/view/AddFile.fxml")
            val addFilePage = loader.load<AnchorPane>()

            val scene = Scene(addFilePage)
            val stageByName = StageManager.getStageByName(primaryStageName)
            stageByName?.scene = scene
        }
        configurationButton.setOnAction {
            //todo goto configurationPage
            val loader = FXMLLoader()
            loader.location = javaClass.getResource("/com/dataway/page/view/ConfigurationBase.fxml")
            val addFilePage = loader.load<AnchorPane>()

            val scene = Scene(addFilePage)
            val stageByName = StageManager.getStageByName(primaryStageName)
            stageByName?.scene = scene
        }
    }
}