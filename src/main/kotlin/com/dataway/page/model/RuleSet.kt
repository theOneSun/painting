package com.dataway.page.model

/**
 * 规则集
 * @author sunjian.
 */
class RuleSet() {
    private lateinit var name: String
    private lateinit var verifyColumn: String
    private lateinit var ruleList: ArrayList<Rule>
    private lateinit var supportLibraryList: ArrayList<String>

    constructor(name:String,verifyColumn:String,ruleList: ArrayList<Rule>,supportLibraryList: ArrayList<String>):this(){
        this.name = name
        this.verifyColumn = verifyColumn
        this.ruleList = ruleList
        this.supportLibraryList = supportLibraryList
    }
}