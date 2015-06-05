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
    private static String INTERFACE_NAME = "com.qunar.temple.TemplateInterface";


    public TemplateInterface compile(String tplPath) throws Exception {
        //---------对传入tplPath处理，加默认后缀，提取文件名为类名，路径转为包名----------
        int lastDotIndex = tplPath.lastIndexOf('.'), lastSlashIndex = tplPath.lastIndexOf('/');
        String className, packageName = null;
        if (lastDotIndex == -1) {
            lastDotIndex = tplPath.length();
            tplPath += ".hbs";
        }
        if (lastSlashIndex == -1) {
            lastSlashIndex = 0;
        } else {
            packageName = tplPath.substring(0, lastSlashIndex);
            lastSlashIndex++;
        }
        className = tplPath.substring(lastSlashIndex, lastDotIndex);

        String absPath = TemplateGenerator.class.getClassLoader().getResource(tplPath).getFile();

        //---------正式读取数据---------
        FileReader fr = new FileReader(absPath);
        StringWriter sw = new StringWriter();
        TempleDataRenderer data = new TempleDataRenderer();

        if (packageName != null) {
            sw.append("package ")
                    .append(packageName).append(";\n\n");
        }
        sw.append("import java.io.StringWriter;\nimport java.lang.reflect.Method;\nimport java.util.Iterator;\nimport java.util.List;\nimport java.util.Map;\n\n")
                .append("public class ").append(className).append(" implements ").append(INTERFACE_NAME).append("{\n").append("\tpublic String render(Map<String, Object> data) {\n\t\tStringWriter sw = new StringWriter();\n");

        int i,//当前遍历charCode
                lastCharCode = 0,
                lineNumber = 1,
                charNumber = 0,
                currentLevel = 2;//当前级别，确定缩进tab,0是class，1是render方法，所以默认从2开始。
        boolean writingMode = false,//是否正在直接写入文件内容
                escapeMode = false,//给\{{...}}中第二个{标识当前序列是否被转义。
                maybeDirecitiveMode = false,//可能进入指令状态
                directiveMode = false,
                firstEnterDirective = false,//刚刚进入指令状态，用于确定指令类型
                endDirectiveMode = false,//是否正在准备结束指令读取
                directiveLine = false;//当前行是否为指令行
        DirectiveType directiveType = DirectiveType.NORMAL;//指令类型
        DirectiveLevel directiveLevel = DirectiveLevel.NORMAL;//指令级别{{}}为normal，{{{}}}为level1

        StringWriter directive = null;
        while ((i = fr.read()) != -1) {
            StringWriter shouldWrite = new StringWriter();
            if (maybeDirecitiveMode) {
                if (i == 123) {
                    directiveMode = true;
                    firstEnterDirective = true;
                    //重置相关参数
                    directive = new StringWriter();
                    directiveType = DirectiveType.NORMAL;
                    directiveLevel = DirectiveLevel.NORMAL;
                    if (writingMode) {
                        writingMode = false;
                        sw.write("\");\n");
                    }
                } else {
                    sw.append("{").write(i);
                }
                maybeDirecitiveMode = false;
                continue;
            } else if (directiveMode) {
                if (firstEnterDirective) {
                    if (i == 123) {
                        directiveLevel = DirectiveLevel.LEVEL1;
                        continue;
                    } else {
                        if (i == 33) {//'!'
                            directiveType = DirectiveType.COMMENT;
                            continue;
                        } else if (i == 35) {//'#'
                            directiveType = DirectiveType.BLOCK;
                            continue;
                        } else if (i == 62) {//'>'
                            directiveType = DirectiveType.PARTIAL;
                            continue;
                        }else{
                            directive.write(i);
                        }
                        firstEnterDirective = false;
                    }
                }

                if (endDirectiveMode) {
                    if (i != 125) {//结束指令读取
                        sw.append(data.getData(directive.toString()));
                        endDirectiveMode = false;
                        directiveMode = false;
                    }
                } else if (i == 125) {
                    endDirectiveMode = true;
                } else if (i != 123) {
                    directive.write(i);
                }
                continue;
            }

            if (i == 92) {//转义符
                escapeMode = true;
            }
            if (escapeMode) {
                if (i != 92 && i != 123) {//转义符后出现非转义符'\',非左大括号'{'，取消转义状态。
                    escapeMode = false;
                }
            } else {
                if (i == 123) {//出现左括号
                    maybeDirecitiveMode = true;
                    continue;
                }
            }

            if (!writingMode) {
                TempleUtils.tab(shouldWrite, currentLevel);
                shouldWrite.append("sw.write(\"");
                writingMode = true;
            }
            if (i == 10) {//换行符
                shouldWrite.write("\\n");
            } else if (i != 13) {//回车符
                shouldWrite.write(i);
            }
            sw.append(shouldWrite.toString());
            lastCharCode = i;
        }
        if (writingMode) {
            sw.write("\");\n");
        }
        sw.append("\t\treturn sw.toString();\n");
        sw.append("\t}\n}");
        System.out.println(sw.toString());
        TemplateInterface rtn = null;
        return rtn;
    }


    public static void main(String[] args) throws Exception {
        new TemplateGenerator().compile("hbs/test");
    }
}

