package BuisnessTier;

import DataTier.FileAcces.FileManager;
import DataTier.FolderSerializer;
import DataTier.Logs.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class AppControllerImpl implements AppController {


    FileManager fileManager;

    FolderSerializer serializer;

    Logger logger;

    @Autowired
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    @Autowired
    public void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @Autowired
    public void setSerializer(FolderSerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public boolean addFile(MultipartFile file, String path) {
        try {
            fileManager.addFile(file, pathDeserialize(path));
            logger.logAddedFile(pathDeserialize(path));
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
            logger.logAddedFolder(pathDeserialize(path));
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
        return serializer.serialize(filesList);
    }

    @Override
    public File getFile(String path) throws FileNotFoundException {
            File result =  fileManager.getFile(pathDeserialize(path));
            logger.logDownloadedFile(path);
            return result;
    }

    @Override
    public boolean delete(String path) {
        try {
            fileManager.delete(pathDeserialize(path));
            logger.logDeletedFile(pathDeserialize(path));
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
            logger.logRenamed(pathDeserialize(path),newName);
            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String pathDeserialize(String path) {
        return path.replace("&", File.separator);
    }
}
