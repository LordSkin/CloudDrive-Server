package DataTier;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class FileSerializerTests {

    private FolderSerializer testObject;

    @Before
    public void prepare() {

        testObject = new FolderSerializer(new Gson(),File.separator+"home"+File.separator+"test");

        ExtensionReader extensionReader = Mockito.mock(ExtensionReader.class);

        testObject.setExtensionReader(extensionReader);
    }

    @Test
    public void nullTest() {
        try {
            testObject.serialize(null);
        }
        catch (NullPointerException e) {
            //OK
        }
        catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void emptyTest() {
        try {
            String result = testObject.serialize(new ArrayList<File>());
            Assert.assertEquals(result, "[]");
        }
        catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void serializeTest() {
        try {
            List<File> files = new ArrayList<File>();
            File testFile = Mockito.mock(File.class);
            Mockito.when(testFile.isDirectory()).thenReturn(true);
            Mockito.when(testFile.getName()).thenReturn("testName");
            Mockito.when(testFile.getAbsolutePath()).thenReturn(File.separator+"home"+File.separator+"test"+File.separator+"test1"+File.separator+"file"+File.separator+"testName");
            files.add(testFile);

            String result = testObject.serialize(files);

            Assert.assertEquals(result, "[{\"fileType\":\"Folder\",\"name\":\"testName\",\"path\":\"%test1%file%testName\"}]");
        }
        catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void serializeTest2() {
        try {
            List<File> files = new ArrayList<File>();
            File testFile = Mockito.mock(File.class);
            File testFile2 = Mockito.mock(File.class);
            Mockito.when(testFile.isDirectory()).thenReturn(true);
            Mockito.when(testFile.getName()).thenReturn("testName");
            Mockito.when(testFile2.isDirectory()).thenReturn(false);
            Mockito.when(testFile2.getName()).thenReturn("testName2");
            Mockito.when(testFile.getAbsolutePath()).thenReturn(File.separator+"home"+File.separator+"test"+File.separator+"test1"+File.separator+"file"+File.separator+"testName");
            Mockito.when(testFile2.getAbsolutePath()).thenReturn(File.separator+"home"+File.separator+"test"+File.separator+"test1"+File.separator+"file"+File.separator+"testName2");
            files.add(testFile);
            files.add(testFile2);

            String result = testObject.serialize(files);

            Assert.assertEquals(result, "[{\"fileType\":\"Folder\",\"name\":\"testName\",\"path\":\"%test1%file%testName\"},{\"fileType\":\"Other\",\"name\":\"testName2\",\"path\":\"%test1%file%testName2\"}]");
        }
        catch (Exception e) {
            Assert.fail();
        }
    }
}
