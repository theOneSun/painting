package com.dataway.page.model

/**
 * @author sunjian.
 */
class Rule {
    private lateinit var name: String
    private lateinit var keyWorld: String
    private lateinit var columnList: ArrayList<String>
    private lateinit var includeColumns: ArrayList<String> // 统计列
    private lateinit var normalRule: NormalRuleRow
    private lateinit var crossRule: CrossRule

}

class NormalRule {
    private lateinit var columnName: String // 列名
    private var include: Boolean = false
    private var maxValue: Int? = null
    private var minValue: Int? = null
    private var columnValues: String? = null
    private var columnTopValue: String? = null
}

class CrossRule {
    private lateinit var crossColumns: String
    private var crossCheckMaxScale: Int? = null
}