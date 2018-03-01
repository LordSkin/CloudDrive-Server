package DataTier.FileAcces;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileManagerImpl implements FileManager {

    private String basePath;

    public FileManagerImpl(String basePath) {
        this.basePath = basePath+"\\";
    }

    @Override
    public File getFile(String path) {
        if(path==null) throw new NullPointerException();
        return new File(basePath+path);
    }

    @Override
    public List<File> getFilesList(String path) {
        if(path==null) throw new NullPointerException();
        String[] files = (new File(basePath + path)).list();
        List<File> result = new ArrayList<File>();
        for (String file : files) {
            result.add(new File(file));
        }
        return result;
    }

    @Override
    public void addFile(MultipartFile file, String path) throws IOException {
        if(path==null&&file==null) throw new NullPointerException();
        byte[] bytes = file.getBytes();
        FileOutputStream out = new FileOutputStream(new File(basePath + path));
        out.write(bytes);
        out.close();
    }

    @Override
    public void addDirectory(String path) {
        if(path==null) throw new NullPointerException();
        (new File(basePath + path)).mkdirs();
    }

    @Override
    public void delete(String path) throws FileNotFoundException {
        if(path==null) throw new NullPointerException();
        File file = new File(basePath + path);
        if (file.isFile()) {
            file.delete();
        }
        else if (file.isDirectory()){
            delete(file);
        }
        else {
            throw new FileNotFoundException();
        }
    }

    private void delete(File directory) {
        if (directory.list().length == 0) {
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
