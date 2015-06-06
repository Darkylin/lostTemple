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

    String currentDataName, currentObjectName="data";
    boolean inBrace = false;//进入大括号后，才开始按新的level补tab

    public String getData(String name) {
        StringWriter sw = new StringWriter();
        newObject(sw,name);
//        sw.write("sw.write(" + name + ");\n");
//        sw.write(StringUtils.join(dataName, "."));
        return sw.toString();
    }
    public String blockHelper(String name){
        StringWriter sw = new StringWriter();
        newLineTab(sw,"if("+name+")");
        return sw.toString();
    }

    public void enterScope(String name) {
        objectCount++;
        currentLevel++;
        dataName.push(name);
        currentDataName = name;
        currentObjectName = "o" + objectCount;
        objectName.push(currentObjectName);
    }

    public void quitScope(String name) {
        currentLevel--;
        currentDataName = dataName.pop().toString();
        currentObjectName = objectName.pop().toString();
    }

    public void newLineTab(StringWriter sw) {
        int i = currentLevel;
        if (i != 2 && !inBrace) {
            i--;
        }
        while (i-- > 0) {
            sw.append("\t");
        }
    }
    public void newLineTab(StringWriter sw,CharSequence str) {
        newLineTab(sw);
        sw.append(str);
    }

    //正常输出一个变量值时调用，需要判断值是否存在
    public void newObject(StringWriter sw,String objectName){
        newLineTab(sw);
        sw.append("Object o")
                .append(String.valueOf(objectCount++))
                .append("=")
                .append(currentObjectName)
                .append(".get(")
                .append(objectName)
                .append(")\n");


    }

}