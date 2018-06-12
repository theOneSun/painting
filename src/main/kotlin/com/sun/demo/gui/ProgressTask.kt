package com.sun.demo.gui

import com.dataway.page.view.selfdefine.StageManager
import com.dataway.page.view.selfdefine.primaryStageName
import javafx.concurrent.Task
import javafx.scene.control.Label

/**
 * @author sunjian.
 */
class ProgressTask: Task<String>() {
    override fun call(): String {
        for (i in 1..10){
            updateProgress(i.toDouble(),10.0)
            Thread.sleep(200)
        }
        val finishLabel = Label("已完成").also {
            it.prefWidth = 200.0
            it.prefHeight = 100.0
        }
        /*val stage = StageManager.getStageByName(primaryStageName)

        val scene = stage?.scene
        val label = scene?.lookup("#statusLabel")
        scene?.root?.childrenUnmodifiable?.also {
            it.remove(label)
            it.add(finishLabel)
        }

        stage?.scene = scene
        stage?.show()*/
        return "已完成"
//        return finishLabel
    }
}