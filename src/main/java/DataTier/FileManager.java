package DataTier;

import java.io.File;
import java.util.List;

public interface FileManager {

    File getFile(String path);

    List<File> getFilesList(String path);

    void addFile(File f, String path, String name);

    void addDirectory(String name, String path);

    void delete(String path);

}
