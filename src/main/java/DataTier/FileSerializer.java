package DataTier;

import DataTier.DataModels.FileType;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileSerializer {

    private Gson gson;

    ExtensionReader extensionReader;

    public FileSerializer(Gson gson) {
        this.gson = gson;
    }

    public void setExtensionReader(ExtensionReader extensionReader) {
        this.extensionReader = extensionReader;
    }

    public  String serialize(List<File> files){


        List<FileDetails> detailsList = new ArrayList<FileDetails>();
        for (File f : files){
            if(f.isDirectory())detailsList.add(new FileDetails(FileType.Folder, f.getName()));
            else detailsList.add(new FileDetails(extensionReader.getExtensionsMap().getOrDefault(getExtension(f.getName()), FileType.Other), f.getName()));
//            else if(extensionReader.getImageExtensions().contains(getExtension(f.getName()))) detailsList.add(new FileDetails(FileType.Image, f.getName()));
//            else if(extensionReader.getDocumentsExtensions().contains(getExtension(f.getName()))) detailsList.add(new FileDetails(FileType.TextFile, f.getName()));
//            else if(extensionReader.getProgramExtensions().contains(getExtension(f.getName()))) detailsList.add(new FileDetails(FileType.Program, f.getName()));
//            else if(extensionReader.getAudioExtensions().contains(getExtension(f.getName()))) detailsList.add(new FileDetails(FileType.Audio, f.getName()));
//            else detailsList.add(new FileDetails(FileType.Other, f.getName()));
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

        public FileDetails(FileType fileType, String name) {
            this.fileType = fileType;
            this.name = name;
        }
    }


}
