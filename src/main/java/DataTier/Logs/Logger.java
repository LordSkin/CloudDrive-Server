package DataTier.Logs;

import DataTier.DataModels.Event;

import java.io.File;

/**
 * Interface for login events of file operations
 */
public interface Logger {
    void logEvent(Event e);
}
