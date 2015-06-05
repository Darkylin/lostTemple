package com.qunar.temple;

import java.io.FileReader;
import java.io.StringWriter;

/**
 * @ClassName: TemplateGenerator
 * @Description: TemplateGenerator
 * @author: yuhuan.wang
 * @date: 6/4/15 2:03 PM
 */
public class TemplateGenerator {
    private static String INTERFACE_NAME="com.qunar.temple.TemplateInterface";
    public TemplateInterface compile(String tplPath) throws Exception {
        int lastDotIndex = tplPath.lastIndexOf('.'),lastSlashIndex = tplPath.lastIndexOf('/');
        String className,packageName=null;
        if(lastDotIndex==-1){
            lastDotIndex=tplPath.length();
            tplPath+=".hbs";
        }
        if(lastSlashIndex==-1){
            lastSlashIndex = 0;
        }else{
            packageName = tplPath.substring(0,lastSlashIndex);
            lastSlashIndex++;
        }
        className = tplPath.substring(lastSlashIndex,lastDotIndex);

        String absPath = TemplateGenerator.class.getClassLoader().getResource(tplPath).getFile();

        FileReader fr = new FileReader(absPath);
        StringWriter sw = new StringWriter();

        if(packageName!=null){
            sw.append("package ")
                    .append(packageName).append(";\n\n");
        }
        sw.append("import java.io.StringWriter;\nimport java.lang.reflect.Method;\nimport java.util.Iterator;\nimport java.util.List;\nimport java.util.Map;\n\n")
                .append("public class ").append(className).append(" implements ").append(INTERFACE_NAME).append("{\n").append("\tpublic String render(Map<String, Object> data) {\n\t\tStringWriter sw = new StringWriter();\n");

        int currCharCode,
                lastChar = 0,
                lineNumber = 1,
                charNumber = 0,
                currentLevel = 2;//当前级别，确定缩进tab,0是class，1是render方法，所以默认从2开始。
        boolean openBraceOnce=false,//是否出现过一次大括号
            writing=false;//是否正在直接写入文件内容


        while ((currCharCode = fr.read()) != -1) {
            StringWriter shouldWrite = new StringWriter();
            if(!writing){
                int i = currentLevel;
                while(i-->0){
                    shouldWrite.append('\t');
                }
                shouldWrite.append("sw.write(\"");
                writing = true;
            }

            if(currCharCode==10){//换行符
                shouldWrite.append("\");\n");
                writing = false;
            }else if(currCharCode!=13){//回车符
                shouldWrite.write(currCharCode);
            }
            sw.append(shouldWrite.toString());
        }
        sw.append("\t}\n}");
        System.out.println(sw.toString());
        TemplateInterface rtn = null;
        return rtn;
    }

    public static void main(String[] args) throws Exception {
        new TemplateGenerator().compile("hbs/test");
    }
}

