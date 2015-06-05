package com.qunar.temple;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.StringStack;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.io.StringWriter;
import java.util.Stack;

/**
 * @ClassName: TepleDataRenderer
 * @Description: TepleDataRenderer
 * @author: yuhuan.wang
 * @date: 6/5/15 2:37 PM
 */
public class TempleDataRenderer {
    Stack dataName = new Stack<String>(),//模板数据中的名字（变量名）
            objectName = new Stack<String>();//输出模板对象的名字（java中对应缓存变量名）
    int objectCount = 0,
            currentLevel = 2;

    String currentDataName, currentObjectName;

    public String getData(String name) {
        StringWriter sw = new StringWriter();
        sw.write("sw.write("+name+");\n");
        sw.write(StringUtils.join(dataName, "."));
        return sw.toString();
    }

    public void enterScope(String name) {
        objectCount++;
        dataName.push(name);
        currentDataName = name;
        currentObjectName = "o" + objectCount;
        objectName.push(currentObjectName);
    }

    public void quitScope(String name) {
        currentDataName = dataName.pop().toString();
        currentObjectName = objectName.pop().toString();
    }

    public void newLine(StringWriter sw){
        sw.append("\n");
        int i = currentLevel;
        while(i-->0){
            sw.append("\t");
        }
    }

}