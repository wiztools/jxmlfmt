package org.wiztools.jxmlfmt;

import org.junit.*;
import org.wiztools.commons.Charsets;
import org.wiztools.commons.FileUtil;
import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;

public class XMLFmtTest {
    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testFormatXML() throws IOException {
        String inXml = "<root><this><is>a</is><test /><message><org><cn>Some org-or-other</cn><ph>Wouldnt you like to know</ph></org><contact><fn>Pat</fn><ln>Califia</ln></contact></message></this></root>";
        String computed = XMLFmt.fmtXML(inXml, "  ");
        String expected = FileUtil.getContentAsString(
            new File("src/test/resources/exp-result.xml"), Charsets.UTF_8);
        System.out.println(computed);
        assertEquals(expected, computed);
    }

    @Test
    public void testSingleElement() throws IOException {
        String inXml = "<root/>";
        String computed = XMLFmt.fmtXML(inXml, "  ");
        String expected = "<root/>\n";
        System.out.println(computed);
        assertEquals(expected, computed);
    }

    @Test
    public void testDocType() throws IOException {
        String inXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root>sudo</root>";
        String computed = XMLFmt.fmtXML(inXml, "  ");
        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<root>\nsudo  \n</root>\n";
        // TBD: remove space *after* sudo!
        System.out.println(computed);
        assertEquals(expected, computed);
    }

    @Test
    public void testComments() throws IOException {
        String inXml = "<root><!--Permission--></root>";
        String computed = XMLFmt.fmtXML(inXml, "  ");
        String expected = "<root>\n  <!--Permission-->\n  \n</root>\n";
        // TBD: remove newline *after* comment!
        System.out.println(computed);
        assertEquals(expected, computed);
    }
}
