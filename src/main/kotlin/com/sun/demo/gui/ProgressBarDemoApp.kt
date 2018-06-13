package com.sun.demo.gui

import com.dataway.page.view.selfdefine.StageManager
import com.dataway.page.view.selfdefine.primaryStageName
import com.sun.org.apache.bcel.internal.generic.NEW
import javafx.animation.Timeline
import javafx.application.Application
import javafx.beans.binding.Bindings
import javafx.beans.property.StringProperty
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.control.ProgressBar
import javafx.scene.layout.AnchorPane
import javafx.scene.text.Text
import javafx.scene.text.TextFlow
import javafx.stage.Stage
import java.io.IOException

/**
 * @author sunjian.
 */
class ProgressBarDemoApp : Application() {
    override fun start(primaryStage: Stage) {
        try {
            val anchorPane = AnchorPane()
            anchorPane.prefWidth = 500.0
            anchorPane.prefHeight = 500.0

            //进度条
            val progressBar = ProgressBar(0.0)
            progressBar.prefWidth = 354.0

            //处理状态
            val label = Label("未处理")
            label.id = "statusLabel"
            label.prefHeight = 100.0
            label.prefWidth = 200.0

            //进度数值
            val textFlow = TextFlow()
            textFlow.prefWidth = 200.0
            val timeText = Text("0")
            val percentText = Text("%")
            textFlow.children.addAll(timeText, percentText)

            anchorPane.children.addAll(progressBar, label, textFlow)

            // 进度控制
            val progressTask = ProgressTask(1345256,1)
            progressBar.progressProperty().bind(progressTask.progressProperty())
            progressBar.progressProperty().addListener { observable, oldValue, newValue ->
                println("newValue是:$newValue")
                timeText.text = ((newValue.toDouble() * 100).toString())
            }
//            label.textProperty().bind(Bindings.createStringBinding())
            //todo 改变数值,监听数值,100%后在改变label的值
            timeText.textProperty().addListener { observable, oldValue, newValue ->
                when {
                    newValue.toDouble() == 0.0 -> {
                    }
                    newValue.toDouble() == 100.0 -> {
                        label.text = "已完成"
                    }
                    else -> label.text = "处理中"
                }
            }
            //测试运行结束隐藏进度条
            progressBar.visibleProperty().bind(progressTask.runningProperty())

            Thread(progressTask).start()

            // Show the scene containing the root layout.
            val scene = Scene(anchorPane, 500.0, 500.0)
            primaryStage.x = 1800.0
            primaryStage.y = 500.0
            primaryStage.scene = scene
            primaryStage.show()
            StageManager.saveStage(primaryStageName, primaryStage)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}

fun main(args: Array<String>) {
    Application.launch(ProgressBarDemoApp::class.java, *args)
}