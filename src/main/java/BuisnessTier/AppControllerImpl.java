package BuisnessTier;

import DataTier.DataModels.Event;
import DataTier.FileAcces.FileManager;
import DataTier.Serializer;
import DataTier.Logs.Logger;
import BuisnessTier.Security.DownloadTokenManager;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Controller implementation, includes all buisness logic
 */
public class AppControllerImpl implements AppController {


    FileManager fileManager;

    Serializer serializer;

    Logger logger;

    DownloadTokenManager tokenManager;

    @Autowired
    public void setTokenManager(DownloadTokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Autowired
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    @Autowired
    public void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @Autowired
    public void setSerializer(Serializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public boolean addFile(MultipartFile file, String path) {
        try {
            fileManager.addFile(file, pathDeserialize(path));
            logger.logEvent(new Event(Event.ADDED_FILE, pathDeserialize(path)));
            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean addDirectory(String path) {
        try {
            fileManager.addDirectory(pathDeserialize(path));
            logger.logEvent(new Event(Event.ADDED_DIR, pathDeserialize(path)));
            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getFolder(String path) throws FileNotFoundException {
        List<File> filesList = fileManager.getFilesList(pathDeserialize(path));
        return serializer.serializeDir(filesList);
    }

    @Override
    public File getFile(String path, String token) throws FileNotFoundException {
        if(tokenManager.isTokenValid(path, token)){
            File result =  fileManager.getFile(pathDeserialize(path));
            logger.logEvent(new Event(Event.DOWNLOADED, pathDeserialize(path)));
            return result;
        }
        else {
            throw new FileNotFoundException();
        }

    }

    @Override
    public boolean delete(String path) {
        try {
            fileManager.delete(pathDeserialize(path));
            logger.logEvent(new Event(Event.DELETED, pathDeserialize(path)));
            return true;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean rename(String path, String newName) {
        try {
            fileManager.rename(pathDeserialize(path), newName);
            logger.logEvent(new Event(Event.RENAMED, pathDeserialize(path), newName));
            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getToken(String path) {
        if (path==null) throw new NullPointerException();
        return tokenManager.getToken(path);
    }

    @Override
    public String getLogs() throws HibernateException {
        return serializer.serializeLogs(logger.getEvents(100));
    }

    private String pathDeserialize(String path) {
        return path.replace("&", File.separator).replace("ucode0020"," ");
    }
}
