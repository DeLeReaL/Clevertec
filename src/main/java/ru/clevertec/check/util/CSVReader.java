// Утилита для чтения CSV файлов
package main.java.ru.clevertec.check.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    /**
     * Чтение данных из CSV файла с пропуском первой строки (заголовка).
     *
     * @param filename путь к CSV файлу
     * @return список строковых массивов, содержащих данные
     * @throws IOException если произошла ошибка чтения файла
     */
    public static List<String[]> read(String filename) throws IOException {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false; // Пропустить первую строку (заголовок)
                    continue;
                }
                String[] values = line.split(";");
                data.add(values);
            }
        }
        return data;
    }
}