package others;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;

import java.io.StringWriter;
import java.util.Map;

/**
 * Created by yuhuan.wang on 2015/6/4.
 */
public class VelocityTest implements TestInterface{
    VelocityEngine ve;
    Template t;
    public VelocityTest(String template){
        ve = new VelocityEngine();
        ExtendedProperties properties = new ExtendedProperties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream( "velocity.properties" ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        ve.setExtendedProperties(properties);
        ve.init();
        t = ve.getTemplate(template+".vm","utf-8");
    }

    public String render(Map<String,Object> contextMap){
        Context c = new VelocityContext(contextMap);
        StringWriter s = new StringWriter();
        t.merge(c,s);
        return s.toString();
    }

    public static void main(String[] args) {
        VelocityTest test= new VelocityTest("test");
//        System.out.println(test.render(DataProvider.provide()));
        Map<String,Object> data = DataProvider.provide();
        int  testAmount = 10000;
        long startTime = System.currentTimeMillis();
        while(testAmount-->0){
            test.render(data);
        }
        System.out.println(System.currentTimeMillis()-startTime);
    }
}
