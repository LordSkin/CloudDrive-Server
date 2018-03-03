package BuisnessTier;

import DataTier.FileAcces.FileManager;
import DataTier.FolderSerializer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class AppControllerTests {

    private AppController testObject;

    @Before
    public void prepare(){
        testObject = new AppControllerImpl();

        FileManager fileManager = Mockito.mock(FileManager.class);
        Mockito.when(fileManager.getFilesList("testPath")).thenReturn(new ArrayList<File>());
        try {
            Mockito.when(fileManager.getFile("testPath")).thenReturn(new File("testFile"));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        FolderSerializer serializer = Mockito.mock(FolderSerializer.class);

        ((AppControllerImpl)testObject).setFileManager(fileManager);
        ((AppControllerImpl)testObject).setSerializer(serializer);
    }

    @Test
    public void addtest(){
        try{
            boolean result =  testObject.addFile(new MockMultipartFile("testFile", new byte[1]), "");
            Assert.assertTrue(result);
        }
        catch (Exception e){
            Assert.fail();
        }
    }

    @Test
    public void getTest(){
        try{
            File result =  testObject.getFile("testPath");
            Assert.assertTrue(result!=null);
        }
        catch (Exception e){
            Assert.fail();
        }
    }

    @Test
    public void getListTest(){
        try{
            String result =  testObject.getFolder("testPath");
        }
        catch (Exception e){
            Assert.fail();
        }
    }

    @Test
    public void deleteTest(){
        try{
            boolean result =  testObject.delete("testPath");
            Assert.assertTrue(result);
        }
        catch (Exception e){
            Assert.fail();
        }
    }

    @Test
    public void renameTest(){
        try{
            boolean result =  testObject.rename("testPath", "newTestName");
            Assert.assertTrue(result);
        }
        catch (Exception e){
            Assert.fail();
        }
    }

    @Test(expected = NullPointerException.class)
    public void nullTestGet(){
        testObject.getFile(null);
    }

    @Test(expected = NullPointerException.class)
    public void nullTestAdd(){
        testObject.addFile(null,null);
    }

    @Test(expected = NullPointerException.class)
    public void nullTestGetList(){
        testObject.getFolder(null);
    }

    @Test(expected = NullPointerException.class)
    public void nullTestDelete(){
        testObject.delete(null);
    }
    @Test(expected = NullPointerException.class)
    public void nullTestRename(){
        testObject.rename(null,null);
    }

}
