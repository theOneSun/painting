package com.dataway.page.util

import com.dataway.page.view.selfdefine.OrderedProperties
import jodd.props.Props
import java.util.Collections
import java.util.Properties

/**
 * @author sunjian.
 */
object PropsUtils {
    fun getMapByPrefix(props: Props, prefix: String): LinkedHashMap<String, String> {
        val linkedHashMap = LinkedHashMap<String, String>()
        //直接遍历props
        for (p in props) {
            if (p.key.startsWith(prefix)) {
                linkedHashMap[p.key] = p.value
            }
        }
        return linkedHashMap
    }

    /**
     * 将props对象转成Properties对象
     */
    fun convertProperties(props: Props): Properties {
        val properties = Properties()
//        val properties = OrderedProperties()
        //对key进行排序
        val keyList = mutableListOf<String>()

        for (entry in props.entries()){
            keyList.add(entry.key)
        }

        keyList.sort()
        for (key in keyList) {
            properties.setProperty(key, props.getValue(key))
        }
        return properties
    }

    /**
     * 将props对象转成OrderedProperties对象
     */
    fun convertOrderProperties(props: Props): OrderedProperties {
        val properties = OrderedProperties()
        //对key进行排序
        val keyList = mutableListOf<String>()

        for (entry in props.entries()){
            keyList.add(entry.key)
        }

        keyList.sort()
        for (key in keyList) {
            properties.setProperty(key, props.getValue(key))
        }
        return properties
    }
}