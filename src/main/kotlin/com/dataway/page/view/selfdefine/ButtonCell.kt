package com.dataway.page.view.selfdefine

import com.dataway.page.model.FileData
import javafx.beans.value.ObservableValue
import javafx.scene.control.Button
import javafx.scene.control.TableCell

/**
 * @author sunjian.
 */
class ButtonCell<S, T>() : TableCell<S, T>() {
    private var deleteButton:Button = Button()

    private lateinit var observableValue: ObservableValue<T>

    init {
        graphic = deleteButton
    }

    override fun updateItem(item: T, empty: Boolean) {
        super.updateItem(item, empty)
        deleteButton.setOnAction {

        }
    }
}