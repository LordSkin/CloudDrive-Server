package DataTier;

import DataTier.DataModels.FileType;
import org.ini4j.Ini;
import org.ini4j.IniPreferences;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConfReader {

    private Map<String, FileType> extensionMap;

    private String basePath;

    public ConfReader(String fileName) throws IOException {

        extensionMap = new HashMap<String, FileType>();

        Ini ini = new Ini(new File(fileName));
        java.util.prefs.Preferences prefs = new IniPreferences(ini);

        basePath = prefs.node("Settings").get("basePath", "SharedFolder");

        if (!basePath.endsWith(File.separator)){
            basePath+=File.separator;
        }

        String extensions = prefs.node("Extensions").get("Image", "");
        Scanner sc = new Scanner(extensions);
        while (sc.hasNext()){
            extensionMap.put(sc.next(), FileType.Image);
        }

        extensions = prefs.node("Extensions").get("Audio", "");
        sc = new Scanner(extensions);
        while (sc.hasNext()){
            extensionMap.put(sc.next(), FileType.Audio);
        }

        extensions = prefs.node("Extensions").get("Docs", "");
        sc = new Scanner(extensions);
        while (sc.hasNext()){
            extensionMap.put(sc.next(), FileType.TextFile);
        }

        extensions = prefs.node("Extensions").get("Executable", "");
        sc = new Scanner(extensions);
        while (sc.hasNext()){
            extensionMap.put(sc.next(), FileType.Program);
        }

    }

    public Map<String, FileType> getExtensionMap() {
        return extensionMap;
    }

    public String getBasePath() {
        return basePath;
    }
}
