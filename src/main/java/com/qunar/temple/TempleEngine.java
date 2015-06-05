package com.qunar.temple;

import others.DataProvider;
import others.TestInterface;

/**
 * @ClassName: Plate
 * @Description: Plate
 * @author: yuhuan.wang
 * @date: 6/4/15 11:51 AM
 */
public class TempleEngine {
    public TestInterface compile(String hbsFileName){
        TestInterface t = null;

        return t;
    }

    public static void main(String[] args) {
        System.out.println(new TempleEngine().compile("test").render(DataProvider.provide()));
    }
}
