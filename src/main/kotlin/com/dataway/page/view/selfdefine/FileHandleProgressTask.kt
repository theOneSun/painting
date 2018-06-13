package com.dataway.page.view.selfdefine

import javafx.concurrent.Task
import java.io.File

/**
 * 处理文件用的线程,主要目的是改变进度条的progress
 * @author sunjian.
 */
//class FileHandleProgressTask(private val fileLength: Int, private val perRead: Int) : Task<String>() {
class FileHandleProgressTask(private val file: File) : Task<String>() {

    private var doRun = true
    override fun call(): String {
        /*var i = 0
        while (i <= fileLength) {
            //todo 删除刚开始停的这两秒
            if (i == 0) {
                Thread.sleep(2000)
            }
            if (i == fileLength) {
                updateProgress(i.toDouble(), fileLength.toDouble())
                println("i.double是${i.toDouble()}")
                break
            }
//            Thread.sleep(200)
            updateProgress(i.toDouble(), fileLength.toDouble())
            println("i.double是${i.toDouble()}")
            if (i + perRead > fileLength) {
                i = fileLength
            } else {
                i += perRead
            }
        }
        return ""*/
        while (doRun) {
            //todo 调用方法返回已完成大小,参数为文件名

            var name = ""
            var done = 1024.0
            Thread.sleep(500)

            if (file.length().toDouble() == done) {
                //已完成
                this.doRun = false
            }
            updateProgress(done, file.length().toDouble())

        }
        return ""
    }
}