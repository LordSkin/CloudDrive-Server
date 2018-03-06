package DataTier;

import DataTier.DataModels.FileType;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConfReader {

    private Map<String, FileType> extensionMap;

    private String basePath;

    public ConfReader(String fileName) throws FileNotFoundException {

        extensionMap = new HashMap<String, FileType>();

        Scanner sc = new Scanner(new File(fileName));

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            Scanner sc2 = new Scanner(line);
            if ( !sc2.hasNext()|| line.charAt(0) == '#') {
                continue;
            }
            else {

                String index = sc2.next();
                switch (index) {
                    case "BasePath:":
                        String path = sc2.next();
                        File file = new File(path);
                        if (!file.exists()){
                            file.mkdirs();
                        }
                        this.basePath = file.getAbsolutePath()+File.separator;
                        break;

                    case "Extensions:":
                        for (int i = 0; i < 4; i++) {
                            sc2 = new Scanner(sc.nextLine());
                            String index2 = sc2.next();
                            switch (index2) {
                                case "Image:":
                                    while (sc2.hasNext()) {
                                        extensionMap.put(sc2.next(), FileType.Image);
                                    }
                                    break;
                                case "Audio:":
                                    while (sc2.hasNext()) {
                                        extensionMap.put(sc2.next(), FileType.Audio);
                                    }
                                    break;
                                case "Docs:":
                                    while (sc2.hasNext()) {
                                        extensionMap.put(sc2.next(), FileType.TextFile);
                                    }
                                    break;
                                case "Executable:":
                                    while (sc2.hasNext()) {
                                        extensionMap.put(sc2.next(), FileType.Program);
                                    }
                                    break;
                            }
                        }
                        break;
                }
            }
        }
    }

    @Bean
    public Map<String, FileType> getExtensionMap() {
        return extensionMap;
    }

    @Bean
    public String getBasePath() {
        return basePath;
    }
}
