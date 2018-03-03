package DataTier;

import DataTier.DataModels.FileType;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FolderSerializer {

    private Gson gson;

    ExtensionReader extensionReader;

    private String basePath;

    public FolderSerializer(Gson gson, String basePath) {
        this.gson = gson;
        this.basePath = basePath;
    }

    public void setExtensionReader(ExtensionReader extensionReader) {
        this.extensionReader = extensionReader;
    }

    public  String serialize(List<File> files){


        List<FileDetails> detailsList = new ArrayList<FileDetails>();
        for (File f : files){
            String path = f.getAbsolutePath().replace(basePath, "").replace(File.separator, "%");
            if(f.isDirectory())detailsList.add(new FileDetails(FileType.Folder, f.getName(), path));
            else detailsList.add(new FileDetails(extensionReader.getExtensionsMap().getOrDefault(getExtension(f.getName()), FileType.Other), f.getName(), path));
        }
        return gson.toJson(detailsList);
    }

    private  String getExtension(String fileName){
        String extension = "";

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i+1);
        }
        return extension;
    }

    private class FileDetails{
        FileType fileType;
        String name;
        String path;

        public FileDetails(FileType fileType, String name, String path) {
            this.fileType = fileType;
            this.name = name;
            this.path = path;
        }
    }


}
