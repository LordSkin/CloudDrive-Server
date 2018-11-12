package DataTier.DataModels;

import javax.persistence.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "events")
public class Event {

    public static final int ADDED_FILE = 0;
    public static final int ADDED_DIR= 1;
    public static final int DELETED = 2;
    public static final int RENAMED = 3;
    public static final int DOWNLOADED = 3;



    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "operation", nullable = false)
    private int operation;

    @Column(name = "path", nullable = false)
    private String path;

    @Column(name = "date", nullable = false)
    private String date;

    @Column(name = "newName", nullable = true)
    private String newName;

    public Event() {
    }

    public Event(int operation, String path, String date, String newName, int id) {
        this.operation = operation;
        this.path = path;
        this.date = date;
        this.newName = newName;
        this.id = id;
    }

    public Event(int operation, String path, String newName) {
        this.operation = operation;
        this.path = path;
        this.newName = newName;
        this.date = (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).format(new Date());
    }

    public Event(int operation, String path) {
        this.operation = operation;
        this.path = path;
        this.date = (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).format(new Date());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }
}
