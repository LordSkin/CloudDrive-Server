package DataTier.FileAcces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FileManagerImpl implements FileManager {

    String basePath;


    public FileManagerImpl(String basePath) {
        this.basePath = basePath;
        File baseFolder = new File(basePath);
        baseFolder.mkdirs();
        this.basePath = baseFolder.getAbsolutePath();
        System.out.println("Folder path: " + baseFolder.getAbsolutePath());
    }

    @Override
    public File getFile(String path) throws FileNotFoundException {
        if (path == null) throw new NullPointerException();
        File result = new File(basePath + path);
        if (!result.exists()) throw new FileNotFoundException();
        return new File(basePath + path);
    }

    @Override
    public List<File> getFilesList(String path) throws FileNotFoundException {
        if (path == null) throw new NullPointerException();
        File baseFile = new File(basePath+File.separator+path);
        String[] files = baseFile.list();
        if (files == null) throw new FileNotFoundException();
        List<File> result = new ArrayList<File>();
        for (String file : files) {
            result.add(new File(baseFile.getAbsolutePath()+File.separator+file));
        }
        return result;
    }

    @Override
    public void addFile(MultipartFile file, String path) throws IOException {
        if (path == null && file == null) throw new NullPointerException();
        byte[] bytes = file.getBytes();
        FileOutputStream out = new FileOutputStream(new File(basePath + path));
        out.write(bytes);
        out.close();
    }

    @Override
    public void addDirectory(String path) throws IOException {
        if (path == null) throw new NullPointerException();
        if(!(new File(basePath +File.separator+ path)).mkdirs()){
            throw new IOException();
        }
    }

    @Override
    public void delete(String path) throws FileNotFoundException {

        if (path == null) throw new NullPointerException();
        File file = new File(basePath + File.separator + path);
        if (file.isFile()) {
            file.delete();
        }
        else if (file.isDirectory()) {
            delete(file);
        }
        else {
            throw new FileNotFoundException();
        }
    }

    @Override
    public void rename(String path, String newName) throws IOException {
        if (path == null) throw new NullPointerException();
        File file = new File(basePath + path);
        boolean success = file.renameTo(new File(basePath+newName));
        if (!success) throw new IOException();
    }

    private void delete(File directory) {
        if (directory.list() == null || directory.list().length == 0) {
            directory.delete();
        }
        else {
            String files[] = directory.list();

            for (String s : files) {
                File fileDelete = new File(directory, s);
                delete(fileDelete);
                if (directory.list().length == 0) {
                    directory.delete();
                }
            }
        }
    }
}
