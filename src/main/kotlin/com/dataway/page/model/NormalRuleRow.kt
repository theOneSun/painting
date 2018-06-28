package com.dataway.page.model

import javafx.scene.control.Button
import javafx.scene.control.CheckBox

/**
 * @author sunjian.
 */
class NormalRuleRow() {
    /*lateinit var columnName: StringProperty
    var include: BooleanProperty = SimpleBooleanProperty(false) //是否统计
    var maxValue: DoubleProperty? = null
    var minValue: DoubleProperty? = null
    var columnValues: StringProperty? = null//数据校验值
    var columnTopValue: DoubleProperty? = null
    lateinit var upButton: Button
    lateinit var downButton: Button
    lateinit var deleteButton: Button

    constructor(columnName: String, include: Boolean, maxValue: Double, minValue: Double,
                columnValues: String, columnTopValue: Double, upButton: Button,
                downButton: Button, deleteButton: Button) : this() {
        this.columnName.value = columnName
        this.include.value = include
        this.maxValue?.value = maxValue
        this.minValue?.value = minValue
        this.columnValues?.value = columnValues
        this.columnTopValue?.value = columnTopValue
        this.upButton = upButton
        this.downButton = downButton
        this.deleteButton = deleteButton
    }*/
    lateinit var columnName: String
    //    var include:Boolean = false
    lateinit var include: CheckBox
    //    var maxValue: Double? = null
    var minValue: Double? = null
    var columnValues: String? = null//数据校验值
    var columnTopValue: Double? = null
    var maxValue: Double? = null
//    lateinit var maxValueProperty: DoubleProperty /*= SimpleDoubleProperty()*/
//    lateinit var minValueProperty: DoubleProperty /*= SimpleDoubleProperty()*/
//    lateinit var columnValuesProperty: StringProperty /*= SimpleStringProperty()*///数据校验值
//    lateinit var columnTopValueProperty: DoubleProperty /*= SimpleDoubleProperty()*/
    lateinit var upButton: Button
    lateinit var downButton: Button
    lateinit var deleteButton: Button
}