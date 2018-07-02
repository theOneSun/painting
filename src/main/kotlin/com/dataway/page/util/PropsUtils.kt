package com.dataway.page.util

import com.dataway.page.view.selfdefine.OrderedProperties
import jodd.props.Props
import org.apache.commons.lang3.StringUtils
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

        for (entry in props.entries()) {
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

        for (entry in props.entries()) {
            keyList.add(entry.key)
        }

        keyList.sort()
        for (key in keyList) {
            properties.setProperty(key, props.getValue(key))
        }
        return properties
    }

    /**
     *  props中的key的指定部分是否是有指定的key值
     *  @return 有返回true;没有返回false
     */
    fun containsSubKey(props: Props, subKeyIndex: Int, targetKey: String?): Boolean {
        if (StringUtils.isNotBlank(targetKey)) {
            for (entry in props.entries()) {
                if (entry.key.split(".")[subKeyIndex] == targetKey) {
                    return true
                }
            }
        }
        return false
    }

    /**
     * props中是否有指定的key
     */
    fun containsKey(props: Props, key:String):Boolean{
        if (StringUtils.isNotBlank(key)) {
            for (entry in props.entries()) {
                if (entry.key == key) {
                    return true
                }
            }
        }
        return false
    }
}