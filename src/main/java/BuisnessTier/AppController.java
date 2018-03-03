package BuisnessTier;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface AppController {
    boolean addFile(MultipartFile file, String path);

    String getFolder(String path);

    File getFile(String path);

    boolean delete(String path);

    boolean rename(String path, String newName);
}
