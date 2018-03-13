package DataTier.DataModels;

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
