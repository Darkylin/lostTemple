package com.qunar.plate;

import java.io.File;
import java.io.FileReader;

/**
 * @ClassName: TemplateGenerator
 * @Description: TemplateGenerator
 * @author: yuhuan.wang
 * @date: 6/4/15 2:03 PM
 */
public class TemplateGenerator {
    public TemplateInterface compile(String tplPath) throws Exception {
        FileReader fr = new FileReader(tplPath);
        int currChar, lastChar = 0;
        while ((currChar = fr.read()) != -1) {
            System.out.println(currChar + " " + (char) currChar);
        }
        fr.read();

        TemplateInterface rtn = null;
        return rtn;
    }

    public static void main(String[] args) throws Exception {
        new TemplateGenerator().compile(TemplateGenerator.class.getClassLoader().getResource("ttest.hbs").getFile());
    }
}
