package com.dataway.page.model

import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import javafx.scene.control.CheckBox

/**
 * 支撑库
 * @author sunjian.
 */
//data class SupportLibrary(var name: String,var exist: Boolean) {
data class SupportLibrary(var name: String,var existCheckBox: CheckBox) {
    /*private lateinit var name: StringProperty
    private lateinit var exist: BooleanProperty

    constructor(name: String,exist: Boolean): this(){
        this.name = SimpleStringProperty(name)
        this.exist = SimpleBooleanProperty(exist)
    }*/


}