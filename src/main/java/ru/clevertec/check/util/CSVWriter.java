// Утилита для записи данных в CSV файл
package main.java.ru.clevertec.check.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVWriter {

    /**
     * Запись данных в CSV файл.
     *
     * @param filename путь к CSV файлу
     * @param data     данные для записи
     * @throws IOException если произошла ошибка записи файла
     */
    public static void write(String filename, List<String[]> data) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            for (String[] row : data) {
                writer.write(String.join(";", row));
                writer.write("\n");
            }
        }
    }

    /**
     * Запись сообщения об ошибке в CSV файл.
     *
     * @param filename путь к CSV файлу
     * @param message  сообщение об ошибке
     */
    public static void writeError(String filename, String message) {
        try {
            List<String[]> errorData = new ArrayList<>();
            errorData.add(new String[]{"ERROR", message});
            write(filename, errorData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}