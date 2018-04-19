package DataTier.Logs;

import java.io.File;

/**
 * Interface for login events of file operations
 */
public interface Logger {

    void logAddedFile(String path);

    void logAddedFolder(String path);

    void logDeletedFile(String path);

    void logDeletedFolder(String path);

    void logDownloadedFile(String path);

    void logRenamed(String path, String newName);

}
