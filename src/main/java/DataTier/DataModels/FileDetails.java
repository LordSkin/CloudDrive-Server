package DataTier.DataModels;

/**
 * data model representing most important informations about file
 */
public class FileDetails {
    FileType fileType;
    String name;
    String path;

    public FileDetails(FileType fileType, String name, String path) {
        this.fileType = fileType;
        this.name = name;
        this.path = path;
    }
}
