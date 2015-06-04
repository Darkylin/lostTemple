package com.qunar.temple;

import java.util.LinkedList;
import java.util.Map;

/**
 * Created by yuhuan.wang on 2015/6/5.
 * 作用域链，能找到this.foo this/foo ./foo ../bar ../../baz对应的变量名
 */
public class ScopeChain {
    public static String root = "data";
    public String current = root;
    LinkedList<String> chain = new LinkedList<String>();
    private Map data;

    public ScopeChain(Map data){
        this.data = data;
    }

    public Object get(String key){

    }

    public String nextScope(String scopeName){
        return scopeName;
    }

    public String lastScope(){

    }
}
