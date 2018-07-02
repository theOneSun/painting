package com.dataway.page.model

import javafx.scene.control.Button
import javafx.scene.control.ChoiceBox

/**
 * @author sunjian.
 */
class CrossRuleRow {
    lateinit var crossItemA: ChoiceBox<String>
    lateinit var crossItemB: ChoiceBox<String>
    lateinit var crossItemC: ChoiceBox<String>
    var maxScale: Double? = null
    lateinit var deleteButton: Button
}