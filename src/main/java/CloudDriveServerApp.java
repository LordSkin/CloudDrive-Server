import BuisnessTier.AppController;
import BuisnessTier.AppControllerImpl;
import DataTier.ConfReader;
import DataTier.DataModels.FileType;
import DataTier.FileAcces.FileManager;
import DataTier.FileAcces.FileManagerImpl;
import DataTier.FolderSerializer;
import DataTier.Logs.Logger;
import DataTier.Logs.LoggerImpl;
import PresentationTier.RestController;
import com.google.gson.Gson;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.MultipartConfigElement;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@SpringBootApplication(scanBasePackages = {"BuisnessTier", "PresentationTier"}, scanBasePackageClasses = {AppController.class, RestController.class})
public class CloudDriveServerApp {


    private String path;
    private Map<String, FileType> extensionMap;
    private String logFile;

    public CloudDriveServerApp() throws IOException {
        ConfReader confReader = new ConfReader("conf.ini");
        this.logFile = confReader.getLogFile();
        this.path = confReader.getBasePath();
        this.extensionMap = confReader.getExtensionMap();

    }

    @Bean
    public AppController getAppController(){
        return new AppControllerImpl();
    }

    @Bean
    public Logger getLogger() throws FileNotFoundException, UnsupportedEncodingException {
        return new LoggerImpl(logFile);
    }

    @Bean
    public FileManager getFileManager(){
        return new FileManagerImpl(path);
    }

    @Bean
    public FolderSerializer getFolderSerializer() throws FileNotFoundException {
            return new FolderSerializer(new Gson(), path, extensionMap);
    }

    @Bean
    public MimetypesFileTypeMap getMimeTyPMap(){
        MimetypesFileTypeMap result = new MimetypesFileTypeMap();
        result.addMimeTypes("application/pdf pdf");
        return result;
    }

    //@Bean
    public MultipartResolver getMultipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(10000000);
        return multipartResolver;
    }


    @Bean
    public MultipartConfigElement multipartConfigElement() {
        return new MultipartConfigElement("");
    }


}
