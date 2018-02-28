package DataTier;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class FileManagerTests {

    private File testDirectory;
    private File testFile;

    private FileManager testObject;

    @Before
    public void prepare() {
        testDirectory = new File("testDirectory");
        testDirectory.mkdir();
        try {
            PrintWriter writer = new PrintWriter("file.txt", "UTF-8");
            writer.println("The first line");
            writer.close();
        }
        catch (Exception e){

        }
        testFile = new File("file.txt");
    }

    @After
    public void cleanUp() {
        delete(testDirectory);
        delete(testFile);
    }

    private void delete(File directory) {
        if (directory.list().length == 0) {
            directory.delete();
        }
        else {
            String files[] = directory.list();

            for (String s : files) {
                File fileDelete = new File(directory, s);
                delete(fileDelete);
                if (directory.list().length == 0) {
                    directory.delete();
                }
            }
        }
    }

    @Test
    public void addFileNullTest(){
        try{
            testObject.addFile(null, null);
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
    public void addTest1(){
        try{
            testObject.addFile(testFile, "");
            if(testObject.getFilesList("").size()<=0) Assert.fail();
        }
        catch (Exception e){
            Assert.fail();
        }
    }

    @Test
    public void getFileTest(){

    }
}
