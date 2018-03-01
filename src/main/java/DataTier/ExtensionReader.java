package DataTier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ExtensionReader {
    private List<String> imageExtensions, documentsExtensions, audioExtensions, programExtensions;

    public ExtensionReader(String fileName) throws FileNotFoundException {
        imageExtensions = new ArrayList<String>();
        documentsExtensions = new ArrayList<String>();
        audioExtensions = new ArrayList<String>();
        programExtensions = new ArrayList<String>();

        Scanner scanner = new Scanner(new File(fileName));
        Scanner sc = new Scanner(scanner.nextLine());
        while(sc.hasNext()){
            imageExtensions.add(sc.next());
        }

        sc = new Scanner(scanner.nextLine());
        while(sc.hasNext()){
            documentsExtensions.add(sc.next());
        }

        sc = new Scanner(scanner.nextLine());
        while(sc.hasNext()){
            audioExtensions.add(sc.next());
        }
        sc = new Scanner(scanner.nextLine());
        while(sc.hasNext()){
            programExtensions.add(sc.next());
        }
    }

    public List<String> getImageExtensions() {
        return imageExtensions;
    }

    public List<String> getDocumentsExtensions() {
        return documentsExtensions;
    }

    public List<String> getAudioExtensions() {
        return audioExtensions;
    }

    public List<String> getProgramExtensions() {
        return programExtensions;
    }
}
