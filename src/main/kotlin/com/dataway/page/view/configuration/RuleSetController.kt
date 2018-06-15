import com.dataway.page.ConfigurationMainApp
import com.dataway.page.util.PropsUtils
import com.dataway.page.view.selfdefine.LeoContext
import com.dataway.page.view.selfdefine.SELECTED_RULESET
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.VBox
import jodd.props.Props
import org.apache.coyote.http11.Constants.a
import java.io.File
import java.io.FilenameFilter
import java.net.URL
import java.text.DecimalFormat
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

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        //设置根节点
        val abstractRoot = TreeItem("")
        // --------------------------------------------------------------
        /*val root1 = TreeItem("商业地产规则集")
        root1.isExpanded = true

        val root2 = TreeItem<String>("待添加")

        val leafNode11 = TreeItem<String>("App和wap使用习惯")
        val leafNode12 = TreeItem<String>("电视剧规则")
        val leafNode13 = TreeItem<String>("XX规则")

        root1.children.addAll(leafNode11, leafNode12, leafNode13)

        val leafNode21 = TreeItem<String>("电视剧规则")
        val leafNode22 = TreeItem<String>("XX规则")
        root2.children.addAll(leafNode21, leafNode22)

        //设置root为根节点
        abstractRoot.children.addAll(root1, root2)*/
        ruleSetTreeView.root = abstractRoot
        // 不显示根节点
        ruleSetTreeView.isShowRoot = false
        // --------------------------------------------------------------
        // todo 检测所有规则集配置文件
        val projectPath = System.getProperty("user.dir")
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
            for (ruleName in ruleNameList)
            {
                val ruleRoot = TreeItem(ruleName)
                ruleSetRoot.children.add(ruleRoot)
            }

            //规则集节点添加到抽象根节点中
            abstractRoot.children.add(ruleSetRoot)
        }

        // 选中节点改变事件
        /*ruleSetTreeView.selectionModel.selectedItemProperty().addListener({ observer, _, _ ->
            run {
                println("选中的节点是:$observer")
                val loader = FXMLLoader()

                val fxmlUrl:String
                val selectedKey:String
                if (ruleSetNodeList.contains(observer.value)) {
                    fxmlUrl = "/com/dataway/page/view/RuleSetParentNode.fxml"
                    selectedKey = SELECTED_RULESET
                } else {
                    fxmlUrl = "/com/dataway/page/view/RuleSetChildNode.fxml"
                    selectedKey = SELECTED_RULESET
                }
                LeoContext.save(selectedKey,observer.value.value)

                loader.location = javaClass.getResource(fxmlUrl)
                val wantedPane: AnchorPane = loader.load()
                rightPane.children.also { it.clear();it.add(wantedPane) }
            }
        })*/

        // 树操作
        ruleSetTreeView.setOnMouseClicked { event ->
            // todo 添加右键操作
        }



        //todo 初始化按钮事件
        addButton.setOnMouseClicked {
            // 添加规则集
            val fxmlUrl = "/com/dataway/page/view/RuleSetParentNode.fxml"
            val fxmlLoader = FXMLLoader()
            fxmlLoader.location = javaClass.getResource(fxmlUrl)
            val addRuleSetPane: AnchorPane = fxmlLoader.load()
            rightPane.children.also {
                it.clear()
                it.add(addRuleSetPane)
            }
        }
        deleteButton.setOnMouseClicked {
            //todo 删除选中的规则集

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
    private fun getRuleName(props: Props):LinkedHashSet<String> {
        val resultSet = LinkedHashSet<String>()
        val allRule = PropsUtils.getMapByPrefix(props, "file")
        for (entry in allRule.entries)
        {
            val split = entry.key.split(".")
            resultSet.add(split[1])
        }
        return resultSet
    }
}