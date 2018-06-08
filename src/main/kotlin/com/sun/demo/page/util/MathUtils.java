package com.sun.demo.page.util;

import lombok.Data;

import java.util.UUID;

/**
 * @author sunjian.
 */
@Data
public class MathUtils {
    public String id;
    public static String uuid(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
