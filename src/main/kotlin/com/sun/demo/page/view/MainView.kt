package com.sun.demo.page.view

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Button

/**
 * @author sunjian.
 */

class MainView{

    @FXML
    val button: Button? = null

    @FXML
    fun eventButton() {
        // val text = text_1.getText()//获取文本框输入的内容
//        println(text)
        println("点击了登录...")
    }

}