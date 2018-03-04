import BuisnessTier.AppControllerImpl;
import Dagger.ControllerModule;
import Dagger.DaggerAppComponent;
import PresentationTier.RestController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileNotFoundException;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        System.out.println("It's alive!");

        AppControllerImpl controller = (AppControllerImpl) AppControllerImpl.getAppController();
        try{
            File basePath = new File("basePath");
            DaggerAppComponent.builder().controllerModule(new ControllerModule(basePath.getAbsolutePath()+File.separator,"extensions.txt")).build().inject(controller);
        }
        catch (FileNotFoundException e){
            System.out.println(e);
        }
        SpringApplication.run(RestController.class, args);
    }
}
