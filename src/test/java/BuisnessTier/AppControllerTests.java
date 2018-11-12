package BuisnessTier;

import BuisnessTier.Security.DownloadTokenManager;
import DataTier.FileAcces.FileManager;
import DataTier.Serializer;
import DataTier.Logs.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class AppControllerTests {

    private AppController testObject;

    @Before
    public void prepare() {
        testObject = new AppControllerImpl();

        FileManager fileManager = Mockito.mock(FileManager.class);
        try {
            Mockito.when(fileManager.getFilesList("testPath")).thenReturn(new ArrayList<File>());
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Mockito.when(fileManager.getFile("testPath")).thenReturn(new File("testFile"));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Serializer serializer = Mockito.mock(Serializer.class);
        DownloadTokenManager tokenManager = Mockito.mock(DownloadTokenManager.class);
        Mockito.when(tokenManager.isTokenValid(Mockito.any(), Mockito.any())).thenReturn(true);
        ((AppControllerImpl) testObject).setFileManager(fileManager);
        ((AppControllerImpl) testObject).setTokenManager(tokenManager);
        ((AppControllerImpl) testObject).setSerializer(serializer);
        ((AppControllerImpl) testObject).setLogger(Mockito.mock(Logger.class));
    }

    @Test
    public void addtest() {
        try {
            boolean result = testObject.addFile(new MockMultipartFile("testFile", new byte[1]), "");
            Assert.assertTrue(result);
        }
        catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void getTest() {
        try {
            File result = testObject.getFile("testPath", "testToken");
            Assert.assertTrue(result != null);
        }
        catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void getListTest() {
        try {
            String result = testObject.getFolder("testPath");
        }
        catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void deleteTest() {
        try {
            boolean result = testObject.delete("testPath");
            Assert.assertTrue(result);
        }
        catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void renameTest() {
        try {
            boolean result = testObject.rename("testPath", "newTestName");
            Assert.assertTrue(result);
        }
        catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(expected = NullPointerException.class)
    public void nullTestGet() {
        try {
            testObject.getFile(null, null);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test(expected = NullPointerException.class)
    public void nullTestAdd() {
        testObject.addFile(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void nullTestGetList() {
        try {
            testObject.getFolder(null);
        }
        catch (FileNotFoundException e) {
            Assert.fail();
        }
    }

    @Test(expected = NullPointerException.class)
    public void nullTestDelete() {
        testObject.delete(null);
    }

    @Test(expected = NullPointerException.class)
    public void nullTestRename() {
        testObject.rename(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void nullTokenTest() {
        testObject.getToken(null);
    }

}
