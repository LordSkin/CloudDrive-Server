package DataTier;

import DataTier.DataModels.FileType;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FileSerializerTests {

    private Serializer testObject;

    @Before
    public void prepare() {

        Map<String, FileType> extensionMap = new HashMap<String, FileType>();
        extensionMap.put("jpg", FileType.Image);
        extensionMap.put("mp3", FileType.Audio);
        extensionMap.put("doc", FileType.TextFile);
        extensionMap.put("exe", FileType.Program);

        testObject = new Serializer(new Gson(),File.separator+"home"+File.separator+"test", extensionMap);

    }

    @Test
    public void nullTest() {
        try {
            testObject.serializeDir(null);
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
            String result = testObject.serializeDir(new ArrayList<File>());
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

            String result = testObject.serializeDir(files);

            Assert.assertEquals(result, "[{\"fileType\":\"Folder\",\"name\":\"testName\",\"path\":\"\\u0026test1\\u0026file\\u0026testName\"}]");
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

            String result = testObject.serializeDir(files);

            Assert.assertEquals(result, "[{\"fileType\":\"Folder\",\"name\":\"testName\",\"path\":\"\\u0026test1\\u0026file\\u0026testName\"},{\"fileType\":\"Other\",\"name\":\"testName2\",\"path\":\"\\u0026test1\\u0026file\\u0026testName2\"}]");
        }
        catch (Exception e) {
            Assert.fail();
        }
    }
}
