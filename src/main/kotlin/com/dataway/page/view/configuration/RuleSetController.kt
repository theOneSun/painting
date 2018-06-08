import com.dataway.page.ConfigurationMainApp
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.VBox
import org.apache.coyote.http11.Constants.a
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

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        //设置根节点
        val abstractRoot = TreeItem("")

        val root1 = TreeItem("商业地产规则集")
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
        abstractRoot.children.addAll(root1, root2)
        ruleSetTreeView.root = abstractRoot
        // 不显示根节点
        ruleSetTreeView.isShowRoot = false


        ruleSetTreeView.selectionModel.selectedItemProperty().addListener({ observer, _, _ ->
            run {
                println("选中的节点是:$observer")
                val loader = FXMLLoader()
                val fxmlUrl = if (observer.value.isLeaf) {
                    //todo
                    println("go leaf Pane")
                    "/com/dataway/page/view/RuleSetChildNode.fxml"
                } else {
                    println("go parent Pane")
                    "/com/dataway/page/view/RuleSetParentNode.fxml"
                }
                loader.location = javaClass.getResource(fxmlUrl)
                val wantedPane: AnchorPane = loader.load()
                rightPane.children.also { it.clear();it.add(wantedPane) }
            }
        })

        //--------------------------
//        var normalGridPane = GridPane()
//        var crossGridPane = GridPane()
//        var addColumnGridPane = GridPane()
//
//        normalPane.children.add(normalGridPane)
//
//        var testLabel = Label("测试一下")
//        GridPane.setConstraints(testLabel,0,0)
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
}