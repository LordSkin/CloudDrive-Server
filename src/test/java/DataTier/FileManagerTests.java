package DataTier;

import DataTier.FileAcces.FileManager;
import DataTier.FileAcces.FileManagerImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;

public class FileManagerTests {

    private File testDirectory;
    private MultipartFile testFile;

    private FileManager testObject;

    @Before
    public void prepare() {
        testDirectory = new File("testDirectory");
        System.out.println(testDirectory.getAbsolutePath());
        testDirectory.mkdir();
        testObject = new FileManagerImpl(testDirectory.getName());
        testFile = new MockMultipartFile("testFile", new byte[1]);
    }

    @After
    public void cleanUp() {
        delete(testDirectory);
        //delete(testFile);
    }

    private void delete(File directory) {

        if (directory.isFile()||directory.list().length == 0) {
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
    public void addFileNullTest() {
        try {
            testObject.addFile(null, null);
            Assert.fail();
        }
        catch (NullPointerException e) {
            //OK
        }
        catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void addTest1() {
        try {
            testObject.addFile(testFile, "filetest");
            if (testObject.getFilesList("").size() <= 0) Assert.fail();
        }
        catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void getFileTest() {
        try {
            testObject.addFile(testFile,"testFile1");
            testObject.addFile(testFile,"testFile2");
            Assert.assertEquals(2, testDirectory.list().length);
        }
        catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void deleteNull() {
        try {
            testObject.delete(null);
            Assert.fail();
        }
        catch (NullPointerException e) {
            //OK
        }
        catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void deleteTest() {
        try {
            testObject.addFile(testFile, testFile.getName());
            testObject.delete(testFile.getName());
            Assert.assertEquals(testDirectory.list().length, 0);
        }
        catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void deleteWrongTest() {
        try {
            testObject.delete(testFile.getName());
            Assert.fail();
        }
        catch (FileNotFoundException e){
            //OK
        }
        catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void listTest(){
        try{
            testObject.addFile(testFile, "testFile1");
            testObject.addFile(testFile, "testFile2");
            testObject.addFile(testFile, "testFile3");
            Assert.assertEquals(3, testDirectory.list().length);
        }
        catch (Exception e){
            Assert.fail();
        }
    }

    @Test
    public void deleDirectory(){
        try{
            testObject.addDirectory("test1");
            testObject.delete("test1");
            Assert.assertEquals(0, testObject.getFilesList("").size());
        }
        catch (Exception e){
            Assert.fail();
        }
    }

    @Test
    public void testGetFileNull(){
        try{
            testObject.getFile(null);
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
    public void testGetFile(){
        try{
            testObject.addFile(testFile, "testFile1");
            File file = testObject.getFile("testFile1");
            Assert.assertEquals("testFile1", file.getName());
        }
        catch (Exception e){
            Assert.fail();
        }
    }
}
