package DataTier.Logs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

public class LoggerImpl implements Logger {



    private DateFormat dateFormat;

    private Queue<String> pending;

    private boolean isWriting;

    private String fileName;

    private PrintWriter pw;

    public LoggerImpl(PrintWriter printWriter, SimpleDateFormat format) {
        isWriting = false;
        this.pw = printWriter;
        this.fileName = fileName;
        pending = new LinkedList<String>();
        this.dateFormat = format;
    }

    public LoggerImpl(String filename) throws FileNotFoundException, UnsupportedEncodingException {
        this.pw = new PrintWriter(filename, "UTF-8");
        this.fileName = filename;
        isWriting = false;
        pending = new LinkedList<String>();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    }


    @Override
    public void logAddedFile(String path) {
        write("[" + dateFormat.format(new Date()) + "] File: " + getFileName(path) + " added to " + path);
    }

    @Override
    public void logAddedFolder(String path) {
        write("[" + dateFormat.format(new Date()) + "] Folder: " + getFileName(path) + " added to " + path);
    }

    @Override
    public void logDeletedFile(String path) {
        write("[" + dateFormat.format(new Date()) + "] File: " + getFileName(path) + " deleted from " + path);
    }

    @Override
    public void logDeletedFolder(String path) {
        write("[" + dateFormat.format(new Date()) + "] Folder: " + getFileName(path) + " deleted from " + path);
    }

    @Override
    public void logDownloadedFile(String path) {
        write("[" + dateFormat.format(new Date()) + "] File: " + getFileName(path) + " downloaded from " + path);
    }

    @Override
    public void logRenamed(String path, String newName) {
        write("[" + dateFormat.format(new Date()) + "] element " + getFileName(path) + " from " + path + " renamed to " + newName);
    }

    private String getFileName(String path) {
        if (path == null) throw new NullPointerException();
        if (path.endsWith(File.separator)) path.substring(0, path.length() - 1);
        int ind = path.lastIndexOf(File.separator);
        if (ind > 0) return path.substring(ind + 1);
        else return path;
    }

    private void write(String s) {
        new Runnable(){
            @Override
            public void run() {
                if (!isWriting){
                    try{
                        isWriting = true;

                        for (String string : pending){
                            pw.println(string);
                        }
                        pw.println(s);
                        pw.flush();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    finally {
                        isWriting = false;
                    }
                }
                else {
                    pending.add(s);
                }
            }
        }.run();


    }
}
