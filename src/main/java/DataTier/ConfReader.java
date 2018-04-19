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

/**
 * Reading configuratuion from configuration file .ini
 */
public class ConfReader {

    private Map<String, FileType> extensionMap;

    private String basePath;

    private String logFile;

    private String userName;

    private String password;

    public ConfReader(String fileName) throws IOException {

        extensionMap = new HashMap<String, FileType>();

        Ini ini = new Ini(new File(fileName));
        java.util.prefs.Preferences prefs = new IniPreferences(ini);

        basePath = prefs.node("Settings").get("basePath", "SharedFolder");
        logFile = prefs.node("Settings").get("logFile", "logs.txt");

        userName = prefs.node("Seciurity").get("user", "user");
        password = prefs.node("Seciurity").get("password", "pass");

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

    public String getLogFile() {
        return logFile;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
