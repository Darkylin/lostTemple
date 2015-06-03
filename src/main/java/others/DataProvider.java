package others;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yuhuan.wang on 2015/6/4.
 */
public class DataProvider {
    public static Map<String, Object> provide(int listSize, int objListSize) {
        URL url = Resources.getResource("data.json");
        String text = null;
        try {
            text = Resources.toString(url, Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, Object> map = JSON.parseObject(text);
        List<String> list = new ArrayList<String>();
        while (listSize-- > 0) {
            list.add("test");
        }
        map.put("list", list);
        List<Object> objList = new ArrayList<Object>();
        while (objListSize-- > 0) {
            objList.add(new Bean());
        }
        map.put("objList", objList);
        return map;
    }

    public static Map<String, Object> provide() {
        return provide(10, 10);
    }

}


