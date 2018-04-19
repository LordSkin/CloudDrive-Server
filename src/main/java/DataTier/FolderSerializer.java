package DataTier;

import DataTier.DataModels.FileDetails;
import DataTier.DataModels.FileType;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Serializing list of files to FileDetails, including recognision of fiel types
 */
public class FolderSerializer {

    private Gson gson;

    private Map<String, FileType> extensionMap;

    private String basePath;

    private String fileSeparator = "&";

    public FolderSerializer(Gson gson, String basePath, Map<String, FileType> extensionMap) {
        this.gson = gson;
        this.basePath = basePath;
        this.extensionMap = extensionMap;
    }


    public String serialize(List<File> files) {


        List<FileDetails> detailsList = new ArrayList<FileDetails>();
        for (File f : files) {
            String path = f.getAbsolutePath().replace(basePath,"").replace(File.separator, fileSeparator);
            if (f.isDirectory()) detailsList.add(new FileDetails(FileType.Folder, f.getName(), path));
            else
                detailsList.add(new FileDetails(extensionMap.getOrDefault(getExtension(f.getName()), FileType.Other), f.getName(), path));
        }
        return gson.toJson(detailsList);
    }

    private String getExtension(String fileName) {
        String extension = "";

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i + 1);
        }
        return extension;
    }



}
