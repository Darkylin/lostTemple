package com.qunar.plate.classgenerator;

/**
 * @ClassName: JavaSrcCodeCompileErrorException
 * @Description: JavaSrcCodeCompileErrorException
 * @author: yuhuan.wang
 * @date: 6/4/15 6:11 PM
 */
public class JavaSrcCodeCompileErrorException extends Exception {
    String message = "";

    JavaSrcCodeCompileErrorException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
