package com.sun.demo.gui

import javafx.concurrent.Task
import javafx.scene.control.Label

/**
 * @author sunjian.
 */
class ProgressTask : Task<String>() {
    private val fileLength = 1345256
    var perRead = 1
    override fun call(): String {
        var i = 0
        while (i <= fileLength) {
            if (i == 0) {
                Thread.sleep(2000)
            }
            if (i == fileLength) {
                updateProgress(i.toDouble(), fileLength.toDouble())
                println("i.double是${i.toDouble()}")
                break
            }
//            Thread.sleep(200)
            updateProgress(i.toDouble(), fileLength.toDouble())
            println("i.double是${i.toDouble()}")
            if (i + perRead > fileLength) {
                i = fileLength
            } else {
                i += perRead
            }


        }
        /*for (i in 0..fileLength){
            if (i==0){
                Thread.sleep(2000)
            }
            updateProgress(i.toDouble(),fileLength.toDouble())
            println("i.double是${i.toDouble()}")
//            Thread.sleep(200)
        }*/
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