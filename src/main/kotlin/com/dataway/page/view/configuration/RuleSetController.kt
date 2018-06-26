import com.dataway.page.util.PropsUtils
import com.dataway.page.view.selfdefine.LeoContext
import com.dataway.page.view.selfdefine.SELECTED_RULE
import com.dataway.page.view.selfdefine.SELECTED_RULE_PARENT_RULE_SET
import com.dataway.page.view.selfdefine.SELECTED_RULE_SET
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.ButtonBar
import javafx.scene.control.ButtonType
import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import javafx.scene.input.MouseButton
import javafx.scene.layout.AnchorPane
import jodd.props.Props
import java.io.File
import java.net.URL
import java.util.ResourceBundle


/**
 * @author sunjian.
 */
class RuleSetController : Initializable {

    @FXML
    private lateinit var ruleSetTreeView: TreeView<String>
    @FXML
    private lateinit var normalPane: AnchorPane
    @FXML
    private lateinit var crossPane: AnchorPane
    @FXML
    private lateinit var addColumnPane: AnchorPane
    @FXML
    private lateinit var rightPane: AnchorPane

    @FXML
    private lateinit var addButton: Button
    @FXML
    private lateinit var deleteButton: Button
    @FXML
    private lateinit var copyButton: Button
    @FXML
    private lateinit var moveButton: Button
    @FXML
    private lateinit var syncButton: Button
    @FXML
    private lateinit var downloadButton: Button

    private var ruleSetNodeList: ArrayList<TreeItem<String>> = arrayListOf()

    private val parentFxmlUrl = "/com/dataway/page/view/RuleSetParentNode.fxml"
    private val childFxmlUrl = "/com/dataway/page/view/RuleSetChildNode.fxml"
    private val ruleSetPath = "${System.getProperty("user.dir")}/conf/"

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        //设置根节点
        val abstractRoot = TreeItem("")
        // --------------------------------------------------------------
        ruleSetTreeView.root = abstractRoot
        // 不显示根节点
        ruleSetTreeView.isShowRoot = false

        //treeView中选中的节点
        val selectedItemProperty = ruleSetTreeView.selectionModel.selectedItemProperty()
        // --------------------------------------------------------------
        // todo 检测所有规则集配置文件
        /*val projectPath = System.getProperty("user.dir")
        println("项目路径$projectPath")
        val ruleSetFileList = File("$projectPath/conf").listFiles({ _, name ->
            name.endsWith(".props")
        })
        println("规则集配置文件${ruleSetFileList.size}")
        //遍历生成树结构
        for (ruleSetFile in ruleSetFileList) {
            println("ruleSetFile$ruleSetFile")
            val name = ruleSetFile.name
            val ruleSetRoot = TreeItem(name.substring(0, name.lastIndexOf(".")))
            ruleSetRoot.isExpanded = true
            ruleSetNodeList.add(ruleSetRoot)
            //todo 读取详细数据拿到子节点(规则)并设置
            val ruleSetProps = Props()
//            ruleSetProps.setValue("user.dir.conf",System.getProperty("user.dir")+"/conf")
            ruleSetProps.load(ruleSetFile)
            val ruleNameList = getRuleName(ruleSetProps)
            for (ruleName in ruleNameList) {
                val ruleRoot = TreeItem(ruleName)
                ruleSetRoot.children.add(ruleRoot)
            }

            //规则集节点添加到抽象根节点中
            abstractRoot.children.add(ruleSetRoot)
        }*/

        //加载树数据
        loadTreeData(abstractRoot)

        // 选中节点改变事件
        selectedItemProperty.addListener({ observer, _, _ ->
            run {
                if (observer.value != null) {
                    println("选中的节点是:$observer")
                    val loader = FXMLLoader()

                    val fxmlUrl: String
                    val selectedKey: String
                    if (ruleSetNodeList.contains(observer.value)) {
                        fxmlUrl = parentFxmlUrl
                        selectedKey = SELECTED_RULE_SET
                    } else {
                        fxmlUrl = childFxmlUrl
                        selectedKey = SELECTED_RULE
                        LeoContext.save(SELECTED_RULE_PARENT_RULE_SET, observer.value.parent.value)
                    }
                    LeoContext.save(selectedKey, observer.value.value)

                    loader.location = javaClass.getResource(fxmlUrl)
                    val wantedPane: AnchorPane = loader.load()
                    rightPane.children.also {
                        it.clear()
                        it.add(wantedPane)
                    }
                }
            }
        })

        // 定义contextMenu
        val contextMenu = ContextMenu()
        val menuItem = MenuItem("添加规则")
        menuItem.setOnAction {
            /*
            1.获取当前选中的节点
            2.初始化右侧面板
             */
            println("要在规则集${selectedItemProperty}下添加规则")

            val loader = FXMLLoader()

            loader.location = javaClass.getResource(childFxmlUrl)
            LeoContext.save(SELECTED_RULE, null)

            val wantedPane: AnchorPane = loader.load()
            rightPane.children.also {
                it.clear()
                it.add(wantedPane)
            }
        }
        contextMenu.items.addAll(menuItem)
        //------------------------------------------------------

        // 树操作
        /*ruleSetTreeView.setOnContextMenuRequested {

        event->
            run {
                var contextMenu = ContextMenu()
                contextMenu.show(this, this.gets)
            }



        }*/
        ruleSetTreeView.setOnMouseClicked { event ->
            contextMenu.hide()
            // todo 添加右键操作
            val button = event.button
            if (MouseButton.SECONDARY == button) {
                if (ruleSetNodeList.contains(selectedItemProperty.value)) {
                    //右键的是规则集节点
                    val treeItem = ruleSetTreeView.selectionModel.selectedItem
                    println("右键规则集$treeItem")

                    contextMenu.show(ruleSetTreeView, event.screenX, event.screenY)
                } else {
                    contextMenu.hide()
                }
            }
            println(button)
        }


        //todo 初始化按钮事件
        addButton.setOnMouseClicked {
            //设置已选中的规则集是空的
            LeoContext.save(SELECTED_RULE_SET, null)
            // 添加规则集
            val fxmlLoader = FXMLLoader()
            fxmlLoader.location = javaClass.getResource(parentFxmlUrl)
            val addRuleSetPane: AnchorPane = fxmlLoader.load()
            rightPane.children.also {
                it.clear()
                it.add(addRuleSetPane)
            }
        }
        deleteButton.setOnMouseClicked {
            //todo 删除选中的规则集
            //判断选中的是否是规则集
            if (ruleSetNodeList.contains(selectedItemProperty.value)) {
                //右键的是规则集节点


                val alert = Alert(Alert.AlertType.CONFIRMATION, "", ButtonType("取消", ButtonBar.ButtonData.NO), ButtonType("确定", ButtonBar.ButtonData.YES))
                alert.height = 500.0
                alert.width = 400.0
                alert.title = "111"
                alert.headerText = "确认删除?"

                val result = alert.showAndWait()
                if (result.get().buttonData == ButtonBar.ButtonData.YES) {
                    val ruleSetFile = File("$ruleSetPath${LeoContext.getValue(SELECTED_RULE_SET)}.props")

                    if (ruleSetFile.exists() && ruleSetFile.isFile) {
                        if (ruleSetFile.delete()) {
                            println("删除成功")
                            //刷新树
                            loadTreeData(abstractRoot)
                        } else {
                            val warnDialog = Alert(Alert.AlertType.ERROR)
                            warnDialog.height = 500.0
                            warnDialog.width = 400.0
                            warnDialog.title = "错误"
                            warnDialog.contentText = "删除失败"
                            warnDialog.showAndWait()
                        }
                    } else {
                        val warnDialog = Alert(Alert.AlertType.ERROR)
                        warnDialog.height = 500.0
                        warnDialog.width = 400.0
                        warnDialog.title = "注意"
                        warnDialog.contentText = "文件不存在"
                        warnDialog.showAndWait()
                    }
                } else {
                    println("取消删除")
                }
            } else {
                val chooseWrongDialog = Alert(Alert.AlertType.WARNING)
                chooseWrongDialog.height = 500.0
                chooseWrongDialog.width = 400.0
                chooseWrongDialog.contentText = "请选择规则集!"
                chooseWrongDialog.showAndWait()
            }
        }
        copyButton.setOnMouseClicked {
            //todo 复制选中的规则集,并增加节点
        }
        moveButton.setOnMouseClicked {
            //todo 移动选中的规则集
        }
        syncButton.setOnMouseClicked {
            //todo 同步规则集
        }
        downloadButton.setOnMouseClicked {
            //todo 下载规则集
        }
    }

    // todo 上移
    @FXML
    fun moveUp() {
        println("上移")
    }

    // todo 下移
    @FXML
    fun moveDown() {
        println("下移")
    }

    // todo 添加
    @FXML
    fun addField() {
        println("添加")
    }

    /**
     * 获取规则名称
     */
    private fun getRuleName(props: Props): LinkedHashSet<String> {
        val resultSet = LinkedHashSet<String>()
        val allRule = PropsUtils.getMapByPrefix(props, "file")
        for (entry in allRule.entries) {
            val split = entry.key.split(".")
            resultSet.add(split[1])
        }
        return resultSet
    }

    /**
     * 加载树节点的数据
     */
    private fun loadTreeData(abstractRoot: TreeItem<String>) {
        abstractRoot.children.clear()
        val projectPath = System.getProperty("user.dir")
        println("项目路径$projectPath")
        val ruleSetFileList = File("$projectPath/conf").listFiles({ _, name ->
            name.endsWith(".props")
        })
        for (ruleSetFile in ruleSetFileList) {
            println("ruleSetFile$ruleSetFile")
            val name = ruleSetFile.name
            val ruleSetRoot = TreeItem(name.substring(0, name.lastIndexOf(".")))
            ruleSetRoot.isExpanded = true
            ruleSetNodeList.add(ruleSetRoot)
            //todo 读取详细数据拿到子节点(规则)并设置
            val ruleSetProps = Props()
//            ruleSetProps.setValue("user.dir.conf",System.getProperty("user.dir")+"/conf")
            ruleSetProps.load(ruleSetFile)
            val ruleNameList = getRuleName(ruleSetProps)
            for (ruleName in ruleNameList) {
                val ruleRoot = TreeItem(ruleName)
                ruleSetRoot.children.add(ruleRoot)
            }

            //规则集节点添加到抽象根节点中
            abstractRoot.children.add(ruleSetRoot)
        }
    }
}