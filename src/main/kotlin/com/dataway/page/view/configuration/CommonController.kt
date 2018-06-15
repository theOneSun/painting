package com.dataway.page.view.configuration

import com.dataway.page.view.selfdefine.LeoContext
import com.dataway.page.view.selfdefine.TARGET_DIR
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.fxml.Initializable
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
class CommonController : Initializable {

    @FXML
    private lateinit var corpusChoiceBox: ChoiceBox<String>
    @FXML
    private lateinit var corpusButton: Button
    @FXML
    private lateinit var targetDirTextField: TextField


    override fun initialize(location: URL?, resources: ResourceBundle?) {

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
}