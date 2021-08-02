package portal.moichor.com.commons;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.format.DateTimeFormatter;
import lombok.var;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CSVWriter {

    private CSVPrinter csvPrinter;
    private static final Logger LOGGER = LogManager.getLogger();

    public CSVWriter(String fileName, String... headers) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName),
                StandardOpenOption.CREATE)) {
            writer.write('\ufeff');
            csvPrinter = new CSVPrinter(writer, CSVFormat.RFC4180.withHeader(headers));
        } catch (IOException e) {
            LOGGER.debug("Exception occurred while creating file");
        }
    }

    public void write(Object... values) {
        try {
            csvPrinter.printRecord(values);
        } catch (IOException e) {
            LOGGER.debug("Exception occurred while writing to file: " + e.getMessage());
        }
    }

    public void close() {
        try {
            csvPrinter.close();
        } catch (IOException e) {
            LOGGER.debug(e.getMessage());
        }
    }

    public void flush() {
        try {
            csvPrinter.flush();
        } catch (IOException e) {
            LOGGER.debug(e.getMessage());
        }
    }

    private boolean archiveExistingFile(String fileName) {
        boolean isArchived = false;
        if (Files.exists(Paths.get(fileName))) {
            var oldFileName = new File(fileName);
            var newFileName = new File(getDateTime() + fileName);
            isArchived  = oldFileName.renameTo(newFileName);
        }
        return isArchived;
    }

    private String getDateTime() {
        var timeStampPattern = DateTimeFormatter.ofPattern("yyyy_MM_dd_HHmmss_");
        return timeStampPattern.format(java.time.LocalDateTime.now());
    }
}
