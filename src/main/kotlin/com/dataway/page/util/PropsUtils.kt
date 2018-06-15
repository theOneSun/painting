package com.dataway.page.util

import jodd.props.Props

/**
 * @author sunjian.
 */
object PropsUtils {
    fun getMapByPrefix(props: Props,prefix:String): LinkedHashMap<String,String>{
        val linkedHashMap = LinkedHashMap<String, String>()
        //直接遍历props
        for (p in props){
            if (p.key.startsWith(prefix)){
                linkedHashMap[p.key] = p.value
            }
        }
        return linkedHashMap
    }
}