package DataTier.FileAcces;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface FileManager {

    File getFile(String path);

    List<File> getFilesList(String path);

    void addFile(MultipartFile f, String path) throws IOException;

    void addDirectory(String path);

    void delete(String path) throws FileNotFoundException;

}
