import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

public class JSONHandler implements LogHandler {
    Gson gson;
    BufferedWriter bufferedWriter;

    public JSONHandler(String fileName) {
        gson = new Gson();
        File file = new File("/home/noah/RemoteRepos/Logging/example-logs/" + fileName);
        try {
            file.createNewFile();
            bufferedWriter = new BufferedWriter(new FileWriter(file));
        } catch(IOException e) {
            System.out.println("Failed to initialize the buffered writer" + e.getMessage());
        }
    }

    public void handleLogMessage(LogMessage logMessage) {
        String line = gson.toJson(logMessage);
        try {
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch(IOException e) {
            System.out.println("Failed to write JSON to log file");
        }
    }
}