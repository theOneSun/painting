package com.dataway.page.view.selfdefine

import javafx.stage.Stage

/**
 * @author sunjian.
 */
object StageManager {
    private val stageMap:HashMap<String,Stage> = HashMap()

    fun saveStage(name:String,stage: Stage){
        stageMap[name] = stage
    }
    fun getStageByName(name:String): Stage? {
        return stageMap[name]
    }
}