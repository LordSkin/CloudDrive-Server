package DataTier.FileAcces;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * DAO for files. Provides basic CRUD operations
 */
public interface FileManager {

    File getFile(String path) throws FileNotFoundException;

    List<File> getFilesList(String path) throws FileNotFoundException;

    void addFile(MultipartFile f, String path) throws IOException;

    void addDirectory(String path) throws IOException;

    void delete(String path) throws FileNotFoundException;

    void rename(String path, String newName) throws IOException;

}
