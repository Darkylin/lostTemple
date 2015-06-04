package com.qunar.temple.classgenerator;

public class Test
{
    public static void main(String[] args) {
        String fullName = "hbstpl.DynaClass";
        StringBuilder src = new StringBuilder();
        src.append("package hbstpl;\n");
        src.append("public class DynaClass {\n");
        src.append("    public String toString() {\n");
        src.append("        return \"Hello, I am \" + ");
        src.append("this.getClass().getSimpleName();1\n");
        src.append("    }\n");
        src.append("}\n");
 
        System.out.println(src);
        DynamicEngine de = DynamicEngine.getInstance();
        Object instance = null;
        try {
            instance = de.javaCodeToObject(fullName,src.toString());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (JavaSrcCodeCompileErrorException e) {
            e.printStackTrace();
        }
//        System.out.println(instance);
    }
}
