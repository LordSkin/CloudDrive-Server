import BuisnessTier.AppController;
import BuisnessTier.AppControllerImpl;
import DataTier.ConfReader;
import DataTier.DataModels.FileType;
import DataTier.FileAcces.FileManager;
import DataTier.FileAcces.FileManagerImpl;
import DataTier.FolderSerializer;
import PresentationTier.RestController;
import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.FileNotFoundException;
import java.util.Map;

@SpringBootApplication(scanBasePackages = {"BuisnessTier", "PresentationTier"}, scanBasePackageClasses = {AppController.class, RestController.class})
public class CloudDriveServerApp {


    private String path;
    private Map<String, FileType> extensionMap;

    public CloudDriveServerApp() throws FileNotFoundException {
        ConfReader confReader = new ConfReader("conf.ini");
        this.path = confReader.getBasePath();
        this.extensionMap = confReader.getExtensionMap();

    }

    @Bean
    public AppController getAppController(){
        return new AppControllerImpl();
    }

    @Bean
    public FileManager getFileManager(){
        return new FileManagerImpl("");
    }

    @Bean
    public FolderSerializer getFolderSerializer() throws FileNotFoundException {
            return new FolderSerializer(new Gson(), path, extensionMap);
    }


}
