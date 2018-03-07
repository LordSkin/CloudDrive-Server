package DataTier;

import DataTier.DataModels.FileType;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.annotation.ExpectedException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class ConfReaderTests {

    private ConfReader testObject;


    @Test
    public void readingTest(){
        try {
            testObject = new ConfReader("conf.ini");
            Assert.assertNotNull(testObject.getBasePath());
            Assert.assertEquals(testObject.getExtensionMap().get("jpg"), FileType.Image);
            Assert.assertEquals(testObject.getExtensionMap().size(),14);
        }
        catch (Exception e){
            Assert.fail();
        }
    }

    @Test(expected = FileNotFoundException.class)
    public void wrongFileTest() throws IOException {
            testObject = new ConfReader("wrongFile.ini");
    }


}
