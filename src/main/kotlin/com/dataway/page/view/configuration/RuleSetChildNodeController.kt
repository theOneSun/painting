package com.dataway.page.view.configuration

import com.dataway.page.view.selfdefine.LeoContext
import com.dataway.page.view.selfdefine.SELECTED_RULE
import com.dataway.page.view.selfdefine.SELECTED_RULE_PARENT_RULE_SET
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.TextField
import jodd.props.Props
import java.io.File
import java.net.URL
import java.util.ResourceBundle

/**
 * @author sunjian.
 */
class RuleSetChildNodeController : Initializable{

    @FXML
    private lateinit var ruleNameTextField: TextField

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        val ruleName = LeoContext.getValue(SELECTED_RULE) as String?
        val ruleSetName = LeoContext.getValue(SELECTED_RULE_PARENT_RULE_SET)
        //todo 根据规则集名称查询配置文件,返回props对象
        val configPath = System.getProperty("user.dir") + "/conf"
        val ruleSetProps = Props()
        ruleSetProps.load(File("$configPath/$ruleSetName.props"))
        val columns = ruleSetProps.getValue("file.$ruleName.columnNames")
        println(columns)
        //名称
        ruleNameTextField.text = ruleName

        println()
        //todo 根据规则名称查询生效关键字
    }
}