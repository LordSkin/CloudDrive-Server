import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

public class Main {
    public static void main(String[] args) {
        System.out.println("It's alive!");
        ApplicationContext ctx = SpringApplication.run(CloudDriveServerApp.class, args);
    }
}
