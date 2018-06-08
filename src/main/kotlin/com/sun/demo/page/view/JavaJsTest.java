package com.sun.demo.page.view;

import javafx.application.Platform;

/**
 * @author sunjian.
 */
public class JavaJsTest {
    public void exit(){
        System.out.println("aaa");
//        Platform.exit();
    }

    /*public static void main(String[] args) {
        System.out.println("马上退出");
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }*/
}
