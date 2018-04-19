package BuisnessTier;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Controller interface for app
 */
public interface AppController {


    boolean addFile(MultipartFile file, String path);

    boolean addDirectory(String path);

    String getFolder(String path) throws FileNotFoundException;

    File getFile(String path, String token) throws FileNotFoundException;

    boolean delete(String path);

    boolean rename(String path, String newName);

    String getToken(String path);

}
