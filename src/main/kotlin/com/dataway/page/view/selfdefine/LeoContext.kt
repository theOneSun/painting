package com.dataway.page.view.selfdefine

/**
 * @author sunjian.
 */
object LeoContext {
    private val objMap:HashMap<String, Any?> = HashMap()

    fun save(key:String,value: Any?){
        objMap[key] = value
    }
    fun getValue(key:String): Any? {
        return objMap[key]
    }
}

