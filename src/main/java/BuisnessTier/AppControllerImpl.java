package BuisnessTier;

import DataTier.FileAcces.FileManager;
import DataTier.FolderSerializer;
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
            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean addDirectory(String path) {
            fileManager.addDirectory(pathDeserialize(path));
            return true;
    }

    @Override
    public String getFolder(String path) throws FileNotFoundException {
        List<File> filesList = fileManager.getFilesList(pathDeserialize(path));
        return serializer.serialize(filesList);
    }

    @Override
    public File getFile(String path) throws FileNotFoundException {
            return fileManager.getFile(pathDeserialize(path));
    }

    @Override
    public boolean delete(String path) {
        try {
            fileManager.delete(pathDeserialize(path));
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
