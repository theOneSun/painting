package com.dataway.page.view.selfdefine

import java.util.Properties

/**
 * @author sunjian.
 */
class OrderedProperties : Properties() {

    private val keySet = linkedSetOf<Any>()


    override val keys: MutableSet<Any>
        get(){
            val unorderedKeys = super.keys()
            val list = mutableListOf<String>()
            for (unorderedKey in unorderedKeys) {
                list.add(unorderedKey.toString())
            }
            list.sort()
            list.forEach { it -> keySet.add(it) }
            return keySet
        }
}