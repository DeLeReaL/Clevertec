// Утилита для записи данных в CSV файл
package main.java.ru.clevertec.check.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVWriter {

    public static void write(String filename, List<String[]> data) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            for (String[] values : data) {
                for (int i = 0; i < values.length; i++) {
                    writer.append(values[i]);
                    if (i < values.length - 1) {
                        writer.append(";");
                    }
                }
                writer.append("\n");
            }
        }
    }

    public static void writeError(String filename, String errorMessage) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.append("ERROR\n");
            writer.append(errorMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
