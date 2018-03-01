package DataTier;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;

public class ExtensionReaderTests {

    private ExtensionReader testObject;

    @Test
    public void nullFileTest(){
        try{
            testObject = new ExtensionReader(null);
            Assert.fail();
        }
        catch (NullPointerException e){
            //OK
        }
        catch (Exception e){
            Assert.fail();
        }
    }

    @Test
    public void wrongFileTest(){
        try{
            testObject = new ExtensionReader("wrongFile");
            Assert.fail();
        }
        catch (FileNotFoundException e){
            //OK
        }
        catch (Exception e){
            Assert.fail();
        }
    }

    @Test
    public void readingTest(){
        try{
            testObject = new ExtensionReader("extensions.txt");
            Assert.assertEquals(4, testObject.getImageExtensions().size());
            Assert.assertEquals(4, testObject.getDocumentsExtensions().size());
            Assert.assertEquals(3, testObject.getAudioExtensions().size());
            Assert.assertEquals(3, testObject.getProgramExtensions().size());
            Assert.assertEquals("jpg", testObject.getImageExtensions().get(0));
        }
        catch (Exception e){
            Assert.fail();
        }
    }
}
