import org.java.emlparser.helper.EMLHelper;
import org.java.emlparser.parser.EMLContent;
import org.java.emlparser.parser.constants.FieldNameEnum;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;

/**
 * Create by LuoChenXu on 2019/11/4
 */
public class EMLHelperTest {

    @Before
    public void init() {

    }

    @Test
    public void parserTest() throws IOException {
        InputStream in = null;
        try {
            in = new FileInputStream(getFile("emlfile/eml.eml"));
            EMLContent emlContent = EMLHelper.parser(in);

        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    @Test
    public void parserMailBaseInfo() throws IOException {
        InputStream in = null;
        try {
            in = new FileInputStream(getFile("emlfile/eml.eml"));
            EMLContent emlContent = EMLHelper.parser(in);
            assertEquals("1.0", emlContent.getHeader("MIME-Version"));
            assertEquals("1105405146@qq.com", emlContent.getHeader(FieldNameEnum.TO.getName()));
            assertEquals("Alienware · 活动 <Dell_Home@e-chn.dell.com>", emlContent.getHeader(FieldNameEnum.FROM.getName()));
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    @After
    public void after() {

    }

    public File getFile(String resourcePath) {
        try {
            File file = new File(EMLHelperTest.class.getResource(resourcePath).toURI());
            return file;
        } catch (URISyntaxException e) {
            throw new IllegalStateException(e);
        }

    }
}
