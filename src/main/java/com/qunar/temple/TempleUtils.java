package com.qunar.temple;

import java.io.StringWriter;

/**
 * @ClassName: TempleUtils
 * @Description: TempleUtils
 * @author: yuhuan.wang
 * @date: 6/5/15 2:56 PM
 */
public class TempleUtils {
    public static void tab(StringWriter sw,int currentLevel){
        int i = currentLevel;
        while (i-- > 0) {
            sw.append('\t');
        }
    }
}
