package com.dataway.page.view.selfdefine

/**
 * @author sunjian.
 */

/**
 * 主舞台key
 */
const val primaryStageName = "primaryStage"

//------全局对象的key begin--------------
/**
 * 要处理的文件
 */
const val HANDLE_FILE = "HANDLE_FILE"
/**
 * 生成文件的存放目录的key
 */
const val TARGET_DIR = "targetDir"
/**
 * 选中的规则集
 */
const val SELECTED_RULE_SET = "selectedRuleSet"
/**
 * 选中的规则
 */
const val SELECTED_RULE = "selectedRule"
/**
 * 选中的规则的父规则集
 */
const val SELECTED_RULE_PARENT_RULE_SET = "selectedRuleParentRuleSet"
//--------------end------------------

//-------文件处理状态begin-----------
const val FILE_HANDLE_FINISH = "已完成"
const val FILE_HANDLE_READY = "未处理"
const val FILE_HANDLE_ING = "处理中"
//-------文件处理状态end-----------

//--------配置文件的key--------------
//规则名称前缀
const val RULE_PREFIX = "file"
//列名称
const val RULE_COLUMN_NAMES = "columnNames"
//数据校验值
const val RULE_COLUMN_VALUES = "columnValues"
//包含列
const val RULE_INCLUDE_COLUMNS = "includeColumns"
//排除列
const val RULE_EXCLUDE_COLUMNS = "excludeColumns"
//最大值
const val RULE_CHECK_MAX_SCALE = "checkMaxScale"
//最小值
const val RULE_CHECK_MIN_SCALE = "checkMinScale"
//top值
const val RULE_TOP_COLUMNS = "topColumns"

//交叉规则的配置
const val RULE_CROSS_COLUMNS = "crossColumns"
//交叉最大比例
const val RULE_CROSS_MAX_SCALE = "crossCheckMaxScale"

const val RULE_CROSS_MIN_SCALE = "crossCheckMinScale"

//------------------------------------