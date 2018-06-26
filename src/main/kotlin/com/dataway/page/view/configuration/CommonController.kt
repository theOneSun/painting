package com.dataway.page.view.configuration

import com.dataway.page.view.selfdefine.BottomAction
import com.dataway.page.view.selfdefine.CURRENT_BOTTOM_ACTION
import com.dataway.page.view.selfdefine.LeoContext
import com.dataway.page.view.selfdefine.TARGET_DIR
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.ChoiceBox
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import javafx.scene.input.MouseButton
import java.net.URL
import java.util.ResourceBundle

/**
 * @author sunjian.
 */
class CommonController : Initializable,BottomAction {

    @FXML
    private lateinit var corpusChoiceBox: ChoiceBox<String>
    @FXML
    private lateinit var corpusButton: Button
    @FXML
    private lateinit var targetDirTextField: TextField


    override fun initialize(location: URL?, resources: ResourceBundle?) {

        //更改底部操作对象
        LeoContext.save(CURRENT_BOTTOM_ACTION,this)

        //todo 从全局中读取输出目录,如过没有就设置为项目根目录,回车后修改
        val value = LeoContext.getValue(TARGET_DIR)
        if (value == null) {
            LeoContext.save(TARGET_DIR, "/")
        }
        targetDirTextField.text = LeoContext.getValue(TARGET_DIR).toString()
        targetDirTextField.setOnKeyPressed { event ->
            if (event.code == KeyCode.ENTER) {
                println("按了回车")
                println(targetDirTextField.text)
            }
        }

        //todo 读取语料库列表
        corpusChoiceBox.items = FXCollections.observableArrayList("商业地产语料库", "XXX语料库", "AAA语料库")

        //todo 合并本地语料库
        corpusButton.setOnMouseClicked { event ->
            if (event.button == MouseButton.PRIMARY)
                println("合并本地语料库")
        }
    }

    override fun doCancel() {
        println("取消更改")
        /*val alert = Alert(Alert.AlertType.CONFIRMATION)
        alert.height = 500.0
        alert.width = 400.0
        alert.title = "取消"
        alert.contentText = "hahahaha"
        alert.show()*/
    }

    override fun doSave() {
        this.doConfirm()
    }

    //确认按钮,获取统计文件存放目录保存
    override fun doConfirm() {
        println("保存${targetDirTextField.text}")
        LeoContext.save(TARGET_DIR,targetDirTextField.text)
    }
}