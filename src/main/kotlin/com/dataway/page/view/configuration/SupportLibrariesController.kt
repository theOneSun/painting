package com.dataway.page.view.configuration

import javafx.beans.value.ChangeListener
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.ChoiceBox
import javafx.scene.control.TextField
import java.net.URL
import java.util.ResourceBundle

/**
 * 支持库控制器
 * @author sunjian.
 */
class SupportLibrariesController : Initializable {

    @FXML
    private lateinit var searchText: TextField
    @FXML
    private lateinit var searchButton: Button
    @FXML
    private lateinit var libraryChoiceBox: ChoiceBox<String>
    @FXML
    private lateinit var industryChoiceBox: ChoiceBox<String>

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        val libraryArray = arrayOf("语料库","XX库")
        val industryArray = arrayOf("汽车行业", "金融行业", "餐饮行业", "烟草行业")
        //  初始化语料库选择框
        libraryChoiceBox.items.addAll(libraryArray)
        libraryChoiceBox.selectionModel.selectFirst()
        //  初始化行业选择框

        industryChoiceBox.items.addAll(industryArray)
        industryChoiceBox.selectionModel.selectFirst()
        //  选择框都要加改变的事件
        libraryChoiceBox.selectionModel.selectedIndexProperty().addListener({ observer, oldValue, newValue -> println("值从" + oldValue + "变成了" + newValue+"测试值"+libraryArray[observer.value as Int]) })
        industryChoiceBox.selectionModel.selectedItemProperty().addListener({ observer, _, _ -> println("值" + observer.value) })
        //  查找按钮添加事件
        searchButton.setOnAction { println(searchText.text) }
    }
}