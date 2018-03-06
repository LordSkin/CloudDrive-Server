package DataTier;

import DataTier.DataModels.FileType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;

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

}
