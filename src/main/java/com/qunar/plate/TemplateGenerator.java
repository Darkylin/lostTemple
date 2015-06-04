package com.qunar.plate;

import java.io.File;
import java.io.FileReader;
import java.io.StringWriter;

/**
 * @ClassName: TemplateGenerator
 * @Description: TemplateGenerator
 * @author: yuhuan.wang
 * @date: 6/4/15 2:03 PM
 */
public class TemplateGenerator {
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
                .append("public class ").append(className).append("{\n");

        int currChar, lastChar = 0;
        while ((currChar = fr.read()) != -1) {
            sw.write(currChar);
//            System.out.println(currChar + " " + (char) currChar);
        }
        sw.append("}");
        System.out.println(sw.toString());
        TemplateInterface rtn = null;
        return rtn;
    }

    public static void main(String[] args) throws Exception {
        new TemplateGenerator().compile("hbs/test");
    }
}

