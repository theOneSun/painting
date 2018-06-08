package com.sun.demo.page

import javafx.application.Application
import javafx.stage.Stage
import javafx.scene.layout.BorderPane
import java.io.IOException
import javafx.scene.Scene
import javafx.fxml.FXMLLoader
import javafx.scene.layout.AnchorPane







/**
 * @author sunjian.
 */
class MainApp : Application() {

    private var primaryStage: Stage? = null
    private var rootLayout: BorderPane? = null

    override fun start(primaryStage: Stage) {
        this.primaryStage = primaryStage
        this.primaryStage!!.title = "AddressApp"

        initRootLayout()

        showPersonOverview()

//        println("wake up ...")
//        showAaView()
//        Thread.sleep(10*1000)
    }

    /**
     * Initializes the root layout.
     */
    fun initRootLayout() {
        try {
            // Load root layout from fxml file.
            val loader = FXMLLoader()
            loader.location = MainApp::class.java.getResource("view/RootView.fxml")
            rootLayout = loader.load<Any>() as BorderPane

            // Show the scene containing the root layout.
            val scene = Scene(rootLayout)
            primaryStage?.scene = scene
            primaryStage?.show()


        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    /**
     * Shows the person overview inside the root layout.
     */
    fun showPersonOverview() {
        try {
            // Load person overview.
            val loader = FXMLLoader()
            loader.location = MainApp::class.java.getResource("view/MainView.fxml")
            val personOverview = loader.load<Any>() as AnchorPane

            // Set person overview into the center of root layout.
            rootLayout?.center = personOverview

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun showAaView() {
        try {
            // Load person overview.
            val loader = FXMLLoader()
            loader.location = MainApp::class.java.getResource("view/aa.fxml")
            val aaView = loader.load<Any>() as AnchorPane

            // Set person overview into the center of root layout.
            rootLayout?.center = aaView

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    fun getPrimaryStage(): Stage? {
        return primaryStage
    }
}


fun main(args: Array<String>) {
//    println("hello world!")
    Application.launch(MainApp::class.java, *args)
//    val mainApp = MainApp()
//    mainApp.
}

