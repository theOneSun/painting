package com.dataway.page.view.configuration

import com.dataway.page.view.selfdefine.BottomAction
import com.dataway.page.view.selfdefine.CURRENT_BOTTOM_ACTION
import com.dataway.page.view.selfdefine.LeoContext
import com.dataway.page.view.selfdefine.StageManager
import com.dataway.page.view.selfdefine.primaryStageName
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.ListView
import javafx.scene.control.TextField
import javafx.scene.layout.AnchorPane
import javafx.stage.Stage
import java.net.URL
import java.util.ResourceBundle

/**
 * @author sunjian.
 */
class ConfigurationBaseController : Initializable {
    @FXML
    private lateinit var detailPane: AnchorPane
    @FXML
    private lateinit var closeButton: Button
    @FXML
    private lateinit var fullScreenButton: Button
    @FXML
    private lateinit var setOptionListView: ListView<String>
    @FXML
    private lateinit var searchTextField: TextField
    @FXML
    private lateinit var searchButton: Button

    //取消按钮
    @FXML
    private lateinit var cancelButton: Button
    //应用按钮
    @FXML
    private lateinit var saveButton: Button
    //确认按钮
    @FXML
    private lateinit var confirmButton: Button


    private val listViewOptions: Array<String> = arrayOf("通用", "规则集", "支持库")

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        // 关闭
        closeButton.setOnAction { currentStage()?.close() }
        // 全屏 esc退出全屏
        fullScreenButton.setOnAction { currentStage()?.isFullScreen = true }
        // 搜索设置项
        searchTextField.text = "搜索设置项"
        searchButton.setOnAction {
            //todo 搜索功能
            val searchTarget = searchTextField.text
            println("搜索$searchTarget")
        }
        //设置listView
        val missions = FXCollections.observableArrayList("通用", "规则集", "支持库")
        setOptionListView.items = missions

        //取消
        cancelButton.setOnAction {
            doCancel()
        }
        //应用
        saveButton.setOnAction {
            doSave()
        }
        //确认
        confirmButton.setOnAction {
            doConfirm()
        }
    }

    @FXML
    fun onMouseClicked() {
        val selectedItem = setOptionListView.selectionModel.selectedItem ?: return
//        println("选中的设置项是$selectedItem")
        when (selectedItem) {
            "通用" -> {
                showDetailPane("/com/dataway/page/view/Common.fxml")
//                "/com/dataway/page/view/Common.fxml".let { showDetailPane(it) }
            }
            "规则集" -> {
                showDetailPane("/com/dataway/page/view/RuleSet.fxml")
            }
            "支持库" -> {
                showDetailPane("/com/dataway/page/view/SupportLibraries.fxml")
            }
            else -> {
                println("选的是啥不知道")
            }
        }
    }

    // 设置centerPane的显示
    private fun showDetailPane(fxmlPath: String) {
        detailPane.children.also { it.clear();it.add(getFxmlLoaderByPath(fxmlPath).load<AnchorPane>()) }
        // val arr = arrayOf("汽车行业", "金融行业", "餐饮行业", "烟草行业")
    }

    /**
     * 获取当前主舞台对象
     */
    private fun currentStage(): Stage? {
        return StageManager.getStageByName(primaryStageName)
    }

    /**
     * 根据fxml路径返回一个FxmlLoader对象
     */
    private fun getFxmlLoaderByPath(path: String): FXMLLoader {
        return FXMLLoader(javaClass.getResource(path))
    }

    //todo 后期实现文本框第一次点击时清空内容

    //todo 取消应用确认接口
    private fun doCancel(){
        println("click cancel")
        val bottomAction = LeoContext.getValue(CURRENT_BOTTOM_ACTION) as BottomAction?
        bottomAction?.doCancel()
    }
    //应用
    private fun doSave(){
        val bottomAction = LeoContext.getValue(CURRENT_BOTTOM_ACTION) as BottomAction?
        bottomAction?.doSave()
    }
    //确认
    private fun doConfirm(){
        println("click confirm")
        val bottomAction = LeoContext.getValue(CURRENT_BOTTOM_ACTION) as BottomAction?
        bottomAction?.doConfirm()
    }
}