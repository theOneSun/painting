package com.dataway.page.view.configuration

import com.dataway.page.view.selfdefine.LeoContext
import com.dataway.page.view.selfdefine.SELECTED_RULE
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.TextField
import java.net.URL
import java.util.ResourceBundle

/**
 * @author sunjian.
 */
class RuleSetChildNodeController : Initializable{

    @FXML
    private lateinit var ruleNameTextField: TextField

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        //名称
        ruleNameTextField.text = LeoContext.getValue(SELECTED_RULE) as String?

        //todo 根据规则名称查询生效关键字
    }
}