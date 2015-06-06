import com.google.common.io.Files;
import com.qunar.temple.TempleDataRenderer;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * @ClassName: TempleTest
 * @Description: TempleTest
 * @author: yuhuan.wang
 * @date: 6/6/15 11:02 AM
 */
public class TempleTest {
    @Test
    public void simpleGetData(){
        TempleDataRenderer renderer = new TempleDataRenderer();
        String rtn = renderer.getData("msg");
        System.out.println(rtn);
//        assertEquals(FileUtils.readFileToString());
        File assertFile = new File("../classes/data.json");
        System.out.println(assertFile.exists());
    }

}
