package DataTier;

import DataTier.DataModels.Event;
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
public class Serializer {

    private Gson gson;

    private Map<String, FileType> extensionMap;

    private String basePath;

    private String fileSeparator = "&";

    private String space = "ucode0020";

    public Serializer(Gson gson, String basePath, Map<String, FileType> extensionMap) {
        this.gson = gson;
        this.basePath = basePath;
        this.extensionMap = extensionMap;
    }


    public String serializeDir(List<File> files) {


        List<FileDetails> detailsList = new ArrayList<FileDetails>();
        for (File f : files) {
            String path = f.getAbsolutePath().replace(basePath,"").replace(File.separator, fileSeparator).replace(" ",space);
            if (f.isDirectory()) detailsList.add(new FileDetails(FileType.Folder, f.getName(), path));
            else
                detailsList.add(new FileDetails(extensionMap.getOrDefault(getExtension(f.getName()), FileType.Other), f.getName().replace(" ",space), path));
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

    public String serializeLogs(List<Event> events){
        return gson.toJson(events);
    }



}
