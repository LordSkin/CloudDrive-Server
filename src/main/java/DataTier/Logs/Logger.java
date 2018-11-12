package DataTier.Logs;

import DataTier.DataModels.Event;

import java.io.File;
import java.util.List;

/**
 * Interface for login events of file operations
 */
public interface Logger {
    void logEvent(Event e);

    List<Event> getEvents(int limit);
}
