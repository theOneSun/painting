package com.dataway.page.util

import javafx.scene.control.Alert
import javafx.scene.control.ButtonBar
import javafx.scene.control.ButtonType

/**
 * 对话框工厂(生成各种对话框)
 * @author sunjian.
 */
object DialogFactory {
    var height: Double = 500.0
    var width: Double = 500.0
    /**
     * 创建警告对话框
     */
    fun createWarnDialog(height: Double, width: Double, title: String, contentText: String): Alert {
        val warnDialog = Alert(Alert.AlertType.WARNING)
        warnDialog.height = height
        warnDialog.width = width
        warnDialog.contentText = contentText
        return warnDialog
    }

    /**
     * 错误对话框
     */
    fun createErrorDialog(height: Double, width: Double, title: String, contentText: String): Alert {
        val errorDialog = Alert(Alert.AlertType.ERROR)
        errorDialog.height = height
        errorDialog.width = width
        errorDialog.contentText = contentText
        return errorDialog
    }

    /**
     * 错误对话框
     */
    fun createErrorDialog(title: String, contentText: String): Alert {
        val errorDialog = Alert(Alert.AlertType.ERROR)
        errorDialog.height = height
        errorDialog.width = width
        errorDialog.contentText = contentText
        return errorDialog
    }

    /**
     * 确认对话框
     */
    fun createConfirmDialog(height: Double, width: Double, title: String, contentText: String, headerText: String): Alert {
        val confirmDialog = Alert(Alert.AlertType.CONFIRMATION, "", ButtonType("取消", ButtonBar.ButtonData.NO), ButtonType("确定", ButtonBar.ButtonData.YES))
        confirmDialog.height = height
        confirmDialog.width = width
        confirmDialog.headerText = headerText
        return confirmDialog
    }
}