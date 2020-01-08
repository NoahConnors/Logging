import java.util.ArrayList;
import java.util.Arrays;

public class Logger {
    public ArrayList<LogField> baseFields;
    public LogHandler[] handlers;
    public static final int DEBUG = 0;
    public static final int INFO = 1;
    public static final int WARNING = 2;
    public static final int ERROR = 3;

    /**
     * Constructs a new logger object.
     * @param handlers An array of LogHandlers meant to take each call of the log methods
     * and handle the level, fields, and tags associated with the call.
     * @param baseFields a variable number of LogFields that will be appended to the fields
     * input into the log methods.
     */
    public Logger(LogHandler[] handlers, LogField... baseFields) {
        this.handlers = handlers;
        this.baseFields = new ArrayList<>(Arrays.asList(baseFields));
    }

    /**
     * Sends a log message and a group of fields (including the base fields)
     * to the handlers defined in the constructor.
     * @param level the level at which to log. Use static constants DEBUG (0), INFO (1),
     * WARNING (2), and ERROR(3). Using a number outside the range 0-4 will display the level
     * as a "?".
     * @param message a message to display with the log.
     * @param fieldsArray a variable number of fields to display with the log message.
     */
    public void log(int level, String message, LogField... fieldsArray) {
        ArrayList<LogField> fields = new ArrayList<>(Arrays.asList(fieldsArray));
        for(int i = 0; i < baseFields.size(); i++) {
            fields.add(i, baseFields.get(i));
        }
        for(LogHandler handler : handlers) {
            handler.handleLogMessage(new LogMessage(level, message, fields));
        }
    }

    /**
     * Sends a log message and a group of fields at the DEBUG level.
     * @param message a message to display with the log.
     * @param fieldsArray a variable number of fields to display with the log message.
     * @see #log(int, String, LogField...)
     */
    public void debug(String message, LogField... fieldsArray) {
        log(DEBUG, message, fieldsArray);
    }

    /**
     * Sends a log message and a group of fields at the INFO level.
     * @param message a message to display with the log.
     * @param fieldsArray a variable number of fields to display with the log message.
     * @see #log(int, String, LogField...)
     */
    public void info(String message, LogField... fieldsArray) {
        log(INFO, message, fieldsArray);
    }

    /**
     * Sends a log message and a group of fields at the WARNING level.
     * @param message a message to display with the log.
     * @param fieldsArray a variable number of fields to display with the log message.
     * @see #log(int, String, LogField...)
     */
    public void warning(String message, LogField... fieldsArray) {
        log(WARNING, message, fieldsArray);
    }

    /**
     * Sends a log message and a group of fields at the ERROR level.
     * @param message a message to display with the log.
     * @param fieldsArray a variable number of fields to display with the log message.
     * @see #log(int, String, LogField...)
     */
    public void error(String message, LogField... fieldsArray) {
        log(ERROR, message, fieldsArray);
    }

    public Logger newWithExtraFields(LogField... newBaseFieldsArray) {
        ArrayList<LogField> newBaseFields = new ArrayList<>(Arrays.asList(newBaseFieldsArray));
        for(int i = 0; i < baseFields.size(); i++) {
            newBaseFields.add(i, baseFields.get(i));
        }



        return new Logger(handlers, newBaseFields.toArray(new LogField[0]));
    }

    /**
     * Converts from a log level integer to an appropriate string.
     */
    public static String fromLevelToString(int level) {
        switch(level) {
            case DEBUG:
                return "DEBUG";
            case INFO:
                return "INFO";
            case WARNING:
                return "WARNING";
            case ERROR:
                return "ERROR";
            default:
                return "?";
        }
    }

    public static void main(String args[]) {
        // Example with base fields
        Logger logger = new Logger(new LogHandler[] {
            new StdoutHandler(), new JSONHandler("RobotLog1.log")
        }, new LogField("Robot", "Competition"), new LogField("Team", 2175));

        // Example without base fields
        // Logger logger = new Logger(new LogHandler[] {
        //     new StdoutHandler(), new JSONHandler()
        // });
        // logger.info("Test log", new LogField("GryoAngle", 22.2), new LogField("LeftEncoder", 400));

        // SpacetimeEvent parentA = new SpacetimeEvent("Auto1Parent", logger);

        // SpacetimeEvent childOfA1 = parentA.makeChild("Auto1Child1");
        // SpacetimeEvent childOfA2 = parentA.makeChild("Auto1Child2");
        // SpacetimeEvent childOfChildOfA2 = childOfA2.makeChild("Auto1Child2Child1");
        // parentA.start();
        // childOfA1.start();
        // childOfA1.end();
        // childOfA2.start();
        // childOfChildOfA2.start();
        // childOfChildOfA2.end();
        // childOfA2.end();
        // parentA.end();
        
        
        // SpacetimeEvent parentB = new SpacetimeEvent("Auto2Parent", logger);
        // SpacetimeEvent parentC = new SpacetimeEvent("Auto3Parent", logger);
        // parentA.start();
        // parentB.start();
        // parentB.end();
        // parentA.end();
        // parentC.start();
        // parentC.end();
        
        SpacetimeEvent a = new SpacetimeEvent("A", logger);
        SpacetimeEvent f = new SpacetimeEvent("F", logger);
        SpacetimeEvent g = new SpacetimeEvent("G", logger);
        SpacetimeEvent b = a.makeChild("B");
        SpacetimeEvent c = b.makeChild("C");
        SpacetimeEvent d = b.makeChild("D");
        SpacetimeEvent e = c.makeChild("E");
        SpacetimeEvent h = g.makeChild("H");
        a.start();
        b.start();
        c.start();
        d.start();
        e.start();
        e.end();
        c.end();
        f.start();
        d.end();
        b.end();
        a.end();
        g.start();
        h.start();
        f.end();
        h.end();
        g.end();
        
        logger.info("Talon1 Data", new LogField("data/talon1/Output", 0.84), new LogField("data/talon1/CurrentDraw", 5));
        logger.info("Talon1 Data", new LogField("data/talon1/Output", 0.25), new LogField("data/talon1/CurrentDraw", 1.7));
        logger.info("Talon1 Data", new LogField("data/talon1/Output", 0.5), new LogField("data/talon1/CurrentDraw", 3));
        logger.info("Talon1 Data", new LogField("data/talon1/Output", 0.35), new LogField("data/talon1/CurrentDraw", 7));

        logger.info("Talon2 Data", new LogField("data/talon2/Output", 0.94), new LogField("data/talon2/CurrentDraw", 0.5));
        logger.info("Talon2 Data", new LogField("data/talon2/Output", 0.75), new LogField("data/talon2/CurrentDraw", 2.3));
        logger.info("Talon2 Data", new LogField("data/talon2/Output", 0.5), new LogField("data/talon2/CurrentDraw", 6));
        logger.info("Talon2 Data", new LogField("data/talon2/Output", 0.1), new LogField("data/talon2/CurrentDraw", 9));
        
    }
}
