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
            Assert.assertEquals(14, testObject.getExtensionsMap().size());
            Assert.assertTrue(testObject.getExtensionsMap().containsKey("jpg"));
        }
        catch (Exception e){
            Assert.fail();
        }
    }
}
