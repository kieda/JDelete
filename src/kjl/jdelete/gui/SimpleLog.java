package kjl.jdelete.gui;

/**
 * Represents a basic interface for a log. Supports appending information to the
 * log, and clearing the log. 
 * 
 * @author zkieda
 */
public interface SimpleLog {
    /**
     * append a String to the end of the log. 
     */
    void append(String s);
    /**
     * clears this log.
     */
    void clear();
}
