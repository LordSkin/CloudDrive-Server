package Dagger;

import DataTier.ExtensionReader;
import DataTier.FileAcces.FileManager;
import DataTier.FileAcces.FileManagerImpl;
import DataTier.FolderSerializer;
import com.google.gson.Gson;
import dagger.Module;
import dagger.Provides;

import java.io.FileNotFoundException;

@Module
public class ControllerModule {

    private FileManager fileManager;
    private FolderSerializer serializer;

    public ControllerModule(String basePath, String extensionFile) throws FileNotFoundException {
        fileManager = new FileManagerImpl(basePath);
        serializer = new FolderSerializer(new Gson(), basePath);
        serializer.setExtensionReader(new ExtensionReader(extensionFile));
    }

    @Provides
    public FileManager getFileManager() {
        return fileManager;
    }

    @Provides
    public FolderSerializer getSerializer() {
        return serializer;
    }
}
