package com.sun.demo.gui

import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.SwingUtilities

/**
 * @author sunjian.
 */
fun main(args: Array<String>) {
    print("sum of 3 and 5 is ")
    SwingUtilities.invokeLater { createAndShowGUI() }
}

fun createAndShowGUI(){
    print("创建显示一个gui界面")

    JFrame.setDefaultLookAndFeelDecorated(true)

    val jFrame = JFrame("主页")
    jFrame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

    val jLabel = JLabel("Hello Kotlin World")

    jFrame.contentPane.add(jLabel)

    jFrame.pack()
    jFrame.isVisible = true
}