package DataTier;

import DataTier.Logs.Logger;
import DataTier.Logs.LoggerImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LoggerTests {

    private Logger testObject;

    @Before
    public void prepare(){
        PrintWriter printWriter = Mockito.mock(PrintWriter.class);

        testObject = new LoggerImpl(printWriter,  new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"));
    }


    @Test(expected = NullPointerException.class)
    public void addNullTest(){
        testObject.logAddedFile(null);
    }

    @Test(expected = NullPointerException.class)
    public void addFolderNullTest(){
        testObject.logAddedFolder(null);
    }

    @Test(expected = NullPointerException.class)
    public void deleteNullTest(){
        testObject.logDeletedFile(null);
    }

    @Test(expected = NullPointerException.class)
    public void deleteFodlerNullTest(){
        testObject.logDeletedFolder(null);
    }

    @Test(expected = NullPointerException.class)
    public void renameNullTest(){
        testObject.logRenamed(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void downloadedNullTest(){
        testObject.logDownloadedFile(null);
    }

    @Test
    public void wrongPathtest(){
        try{
            String testpath ="wrongtestpath";
            testObject.logDownloadedFile(testpath);
            testObject.logRenamed(testpath, "newName");
            testObject.logDeletedFolder(testpath);
            testObject.logDeletedFile(testpath);
            testObject.logAddedFolder(testpath);
            testObject.logAddedFile(testpath);
        }
        catch (Exception e){
            Assert.fail();
        }
    }

    @Test
    public void correctTest(){
        try{
            String testpath ="path"+ File.separator+"name";
            testObject.logDownloadedFile(testpath);
            testObject.logRenamed(testpath, "newName");
            testObject.logDeletedFolder(testpath);
            testObject.logDeletedFile(testpath);
            testObject.logAddedFolder(testpath);
            testObject.logAddedFile(testpath);
        }
        catch (Exception e){
            Assert.fail();
        }
    }

}
