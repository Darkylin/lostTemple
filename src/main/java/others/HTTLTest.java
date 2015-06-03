package others;

import httl.Engine;
import httl.Template;

import java.io.IOException;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.Map;

/**
 * Created by yuhuan.wang on 2015/6/4.
 */
public class HTTLTest {
    private Engine engine = Engine.getEngine();
    Template t;
    public HTTLTest(String name){
        try {
            t = engine.getTemplate(name);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
    public String render(  Map<String, Object> context) {
        StringWriter writer = new StringWriter();
        try {
            t.render(context, writer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }

    public static void main(String[] args) {
        System.out.println(new HTTLTest("test.httl").render(DataProvider.provide()));
    }

}
