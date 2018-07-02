package com.dataway.page.model

import javafx.scene.control.Button
import javafx.scene.control.CheckBox

/**
 * @author sunjian.
 */
class NormalRuleRow {
    //列名
    lateinit var columnName: String
    //统计列
    lateinit var include: CheckBox
    //最小值
    var minValue: Double? = null
    var columnValues: String? = null//数据校验值
    var columnTopValue: Double? = null
    var maxValue: Double? = null
    lateinit var upButton: Button
    lateinit var downButton: Button
    lateinit var deleteButton: Button
}