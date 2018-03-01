package DataTier;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class FileSerializerTests {

    private FileSerializer testObject;

    @Before
    public void prepare(){

        testObject = new FileSerializer(new Gson());

        ExtensionReader extensionReader = Mockito.mock(ExtensionReader.class);

        List<String> images, docs, audio, programs;

        images = new ArrayList<String>();
        images.add("jpg");
        docs = new ArrayList<String>();
        docs.add("txt");
        audio = new ArrayList<String>();
        audio.add("mp3");
        programs = new ArrayList<String>();
        programs.add("exe");

        Mockito.when(extensionReader.getImageExtensions()).thenReturn(images);
        Mockito.when(extensionReader.getAudioExtensions()).thenReturn(audio);
        Mockito.when(extensionReader.getProgramExtensions()).thenReturn(programs);
        Mockito.when(extensionReader.getDocumentsExtensions()).thenReturn(docs);

        testObject.setExtensionReader(extensionReader);
    }

    @Test
    public void nullTest(){
        try{
            testObject.serialize(null);
        }
        catch (NullPointerException e){
            //OK
        }
        catch (Exception e){
            Assert.fail();
        }
    }

    @Test
    public void emptyTest(){
        try{
            String result = testObject.serialize(new ArrayList<File>());
            Assert.assertEquals(result, "[]");
        }
        catch (Exception e){
            Assert.fail();
        }
    }

    @Test
    public void serializeTest(){
        try{
            List<File> files = new ArrayList<File>();
            File testFile = Mockito.mock(File.class);
            Mockito.when(testFile.isDirectory()).thenReturn(true);
            Mockito.when(testFile.getName()).thenReturn("testName");
            files.add(testFile);

            String result = testObject.serialize(files);

            Assert.assertEquals(result, "[{\"fileType\":\"Folder\",\"name\":\"testName\"}]");
        }
        catch (Exception e){
            Assert.fail();
        }
    }

    @Test
    public void serializeTest2(){
        try{
            List<File> files = new ArrayList<File>();
            File testFile = Mockito.mock(File.class);
            File testFile2 = Mockito.mock(File.class);
            Mockito.when(testFile.isDirectory()).thenReturn(true);
            Mockito.when(testFile.getName()).thenReturn("testName");
            Mockito.when(testFile2.isDirectory()).thenReturn(false);
            Mockito.when(testFile2.getName()).thenReturn("testName2");
            files.add(testFile);
            files.add(testFile2);

            String result = testObject.serialize(files);

            Assert.assertEquals(result, "[{\"fileType\":\"Folder\",\"name\":\"testName\"},{\"fileType\":\"Other\",\"name\":\"testName2\"}]");
        }
        catch (Exception e){
            Assert.fail();
        }
    }
}
