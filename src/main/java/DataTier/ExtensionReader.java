package DataTier;

import DataTier.DataModels.FileType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ExtensionReader {
    private Map<String, FileType> extensionsMap;

    public ExtensionReader(String fileName) throws FileNotFoundException {
        extensionsMap = new HashMap<String, FileType>();

        Scanner scanner = new Scanner(new File(fileName));
        Scanner sc = new Scanner(scanner.nextLine());
        while(sc.hasNext()){
            extensionsMap.put(sc.next(), FileType.Image);
        }

        sc = new Scanner(scanner.nextLine());
        while(sc.hasNext()){
            extensionsMap.put(sc.next(), FileType.TextFile);
        }

        sc = new Scanner(scanner.nextLine());
        while(sc.hasNext()){
            extensionsMap.put(sc.next(), FileType.Audio);
        }
        sc = new Scanner(scanner.nextLine());
        while(sc.hasNext()){
            extensionsMap.put(sc.next(), FileType.Program);
        }
    }

    public Map<String, FileType> getExtensionsMap() {
        return extensionsMap;
    }
}
