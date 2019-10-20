public class StdoutHandler implements LogHandler {
    public void handleLogMessage(LogMessage message) {
        System.out.println("Level: " + Logger.fromLevelToString(message.level));
        System.out.println("    Message: " + message.message);
        System.out.println("    Fields: ");
        for(LogField field : message.fields) {
            System.out.println("        " + field.name + ": " + field.value);
        }
    }
}