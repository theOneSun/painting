package com.dataway.page.view.selfdefine

import java.lang.RuntimeException

/**
 * @author sunjian.
 */
class StageManagerFactory {
    private val stageManager: StageManager? = null
    fun getStageManager(): StageManager {
        if (this.stageManager == null){
            throw RuntimeException("stage初始化异常")
        }
        return this.stageManager
    }
}