import PresentationTier.RestController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        System.out.println("It's alive!");
        SpringApplication.run(RestController.class, args);
    }
}
