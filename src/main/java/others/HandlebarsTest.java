package others;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class HandlebarsTest implements TestInterface{
    private Template template;
    public HandlebarsTest(String tpl){
        Handlebars handlebars = new Handlebars();
        try {
            template = handlebars.compile(tpl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String render(Map<String,Object> data){
        String result = null;
        try {
            StringWriter writer = new StringWriter();
            template.apply(data,writer);
            result = writer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(new HandlebarsTest("test").render(DataProvider.provide()));
    }
}
