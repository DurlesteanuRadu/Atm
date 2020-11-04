package interview.log;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public final class LogService {
    private static final String path = "src/data/log.csv";
    private static LogService instance;

    private LogService() {

    }

    public static LogService getInstance() {
        if (null == instance) {
            instance = new LogService();
        }
        return instance;
    }

    public void record(String action) {
        FileWriter logWriter = null;
        SimpleDateFormat timestampFormat = new SimpleDateFormat("dd:MMM:yyyy HH:mm:ss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        try {
            logWriter = new FileWriter(path, true);
            logWriter.append(action);
            logWriter.append(",");
            logWriter.append(timestampFormat.format(timestamp));
            logWriter.append("\n");

            logWriter.flush();
        } catch (final IOException exception) {
            exception.printStackTrace();
        }
    }

    // Method called to separate different sessions of Atm usage
    public void start() {
        FileWriter logWriter = null;
        try {
            logWriter = new FileWriter(path, true);
            logWriter.append("--------------------------------,---------------------\n");
            logWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
