package lost.temple.a.b;

import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class escape implements com.qunar.temple.TemplateInterface{
    public String render(Map<String, Object> data) {
        StringWriter sw = new StringWriter();
        sw.write("<h1>escape test</h1>\n<p>{{escape}}</p>");
        return sw.toString();
    }
}