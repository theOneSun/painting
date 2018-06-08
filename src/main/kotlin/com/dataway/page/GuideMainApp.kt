package com.dataway.page

import com.dataway.page.view.selfdefine.StageManager
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.layout.AnchorPane
import javafx.stage.Stage
import java.io.IOException

/**
 * @author sunjian.
 */
class GuideMainApp: Application() {
    override fun start(primaryStage: Stage?) {
        try {
            // Load root layout from fxml file.
            val loader = FXMLLoader()
            loader.location = ConfigurationMainApp::class.java.getResource("/com/dataway/page/view/AddFile.fxml")
            val rootLayout = loader.load<AnchorPane>()

            // Show the scene containing the root layout.
            val scene = Scene(rootLayout)
            primaryStage?.scene= scene
            primaryStage?.show()
            primaryStage.let { StageManager.saveStage("primaryStage",it!!) }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
const val primaryStageName = "primaryStage"

fun main(args: Array<String>) {
    Application.launch(GuideMainApp::class.java,*args)
}