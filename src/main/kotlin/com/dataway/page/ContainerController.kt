package com.dataway.page

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.BorderPane
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import java.io.IOException

/**
 * @author sunjian.
 */
class ContainerController : Application() {

    override fun start(primaryStage: Stage?) {
        try {
            // Load root layout from fxml file.
            val loader = FXMLLoader()
            loader.location = ContainerController::class.java.getResource("/com/dataway/page/view/Container.fxml")
            val rootLayout = loader.load<StackPane>()

            val loader2 = FXMLLoader()
            loader2.location = ContainerController::class.java.getResource("/com/dataway/page/view/Configuration.fxml")
            val child = loader2.load<AnchorPane>()
            rootLayout.children.addAll(child)
            // Show the scene containing the root layout.
            val scene = Scene(rootLayout)
            primaryStage?.scene = scene
            primaryStage?.show()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}
fun main(args: Array<String>){
    Application.launch(ContainerController::class.java ,*args)
}