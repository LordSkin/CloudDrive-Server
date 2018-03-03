package BuisnessTier;

import DataTier.FileAcces.FileManager;
import DataTier.FolderSerializer;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class AppControllerImpl implements AppController {

    FileManager fileManager;

    FolderSerializer serializer;

    public void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

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
    public String getFolder(String path) {
        List<File> filesList = fileManager.getFilesList(pathDeserialize(path));
        return  serializer.serialize(filesList);
    }

    @Override
    public File getFile(String path) {
        try {
            return fileManager.getFile(pathDeserialize(path));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
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

    private String pathDeserialize(String path){
        return path.replaceAll("%", File.separator);
    }
}
