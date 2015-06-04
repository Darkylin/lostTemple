import org.junit.Test;
import others.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: TestVelocity
 * @Description: TestVelocity
 * @author: yuhuan.wang
 * @date: 6/3/15 8:46 PM
 */
public class TestAll {
    long start;
    Map<String, Object> contextMap = DataProvider.provide();

    public void start() {
        start = System.currentTimeMillis();
    }

    @Test
    public void all() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class[] testList = new Class[]{VelocityTest.class, HandlebarsTest.class, PureJava.class};
        Map data = DataProvider.provide();

        for (Class c : testList) {
            TestInterface t = (TestInterface) c.getConstructor(String.class).newInstance("test");
            int testTimes = 1000;
//            start();
//            while (testTimes-- > 0) {
//                t.render(data);
//            }
//            end(c.getSimpleName());
            System.out.println(t.render(data));
        }

    }
    @Test
    public void test(){
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        System.out.println(list.get(0).toString());

    }

    public void end(String des) {
        System.out.println(des + " cost: " + (System.currentTimeMillis() - start) + "ms");
    }
}
