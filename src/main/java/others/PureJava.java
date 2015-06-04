package others;

import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by yuhuan.wang on 2015/6/4.
 */
public class PureJava implements TestInterface {
    public PureJava(String tpl) {

    }

    public PureJava() {

    }

    public String render(Map<String, Object> data) {
        StringWriter sw = new StringWriter();
        sw
                .append("hi,")
                .append(data.get("msg").toString())
                .append('\n')
                .append("test list:\n");
        Object o1 = data.get("list");
        if (o1 != null) {
            if (o1 instanceof List) {
                List l1 = (List) o1;
                Iterator i1 = l1.iterator();
                while (i1.hasNext()) {
                    sw.append("    list: ");
                    sw.append(i1.next().toString());
                    sw.append('\n');
                }
            } else if (o1 instanceof Map) {

            } else {

            }
        }
        sw.append("test if:\n");

        Object o4 = data.get("isTest");
        if (o4 != null && o4 != Boolean.FALSE) {
            sw.append("test object list:\n");

            Object o2 = data.get("objList");
            if (o2 != null) {
                if (o2 instanceof List) {
                    List l2 = (List) o2;
                    Iterator i2 = l2.iterator();
                    while (i2.hasNext()) {
                        Object o3 = i2.next();
                        if (o3 != null) {
                            if (o3 instanceof List) {

                            } else if (o3 instanceof Map) {

                            } else {
                                sw.append("    objlist: ");
                                try {
                                    Method m = o3.getClass().getMethod("getMsg");
                                    sw.append(m.invoke(o3).toString());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                sw.append('\n');
                            }
                        }

                    }
                } else if (o2 instanceof Map) {

                } else {

                }
            }
        }


        return sw.toString();
    }

    public static void main(String[] args) {
        PureJava test = new PureJava();
        Map<String, Object> data = DataProvider.provide();
        int testAmount = 1;
        long startTime = System.currentTimeMillis();
        while (testAmount-- > 0) {
            test.render(data);
        }
        System.out.println(System.currentTimeMillis() - startTime);
    }
}
