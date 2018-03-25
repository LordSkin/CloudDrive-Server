package DataTier.Logs;

import java.io.File;

public interface Logger {

    void logAddedFile(String path);

    void logAddedFolder(String path);

    void logDeletedFile(String path);

    void logDeletedFolder(String path);

    void logDownloadedFile(String path);

    void logRenamed(String path, String newName);

}
