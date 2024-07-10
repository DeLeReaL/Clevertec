// Главный класс приложения для обработки заказа и создания чека
package main.java.ru.clevertec.check;

import main.java.ru.clevertec.check.exception.CheckException;
import main.java.ru.clevertec.check.factory.ProductFactory;
import main.java.ru.clevertec.check.factory.DiscountCardFactory;
import main.java.ru.clevertec.check.model.Product;
import main.java.ru.clevertec.check.model.DiscountCard;
import main.java.ru.clevertec.check.service.*;
import main.java.ru.clevertec.check.util.CSVReader;
import main.java.ru.clevertec.check.util.CSVWriter;

import java.util.List;

public class CheckRunner {
    public static void main(String[] args) {
        String pathToFile = null;
        String saveToFile = null;

        // Обработка аргументов командной строки
        for (String arg : args) {
            if (arg.startsWith("pathToFile=")) {
                pathToFile = arg.split("=")[1];
            } else if (arg.startsWith("saveToFile=")) {
                saveToFile = arg.split("=")[1];
            }
        }

        if (pathToFile == null || saveToFile == null) {
            String errorMessage = "BAD REQUEST";
            if (pathToFile == null) {
                errorMessage = "BAD REQUEST";
            } else if (saveToFile == null) {
                errorMessage = "BAD REQUEST";
            }

            String errorFile = saveToFile != null ? saveToFile : "result.csv";
            CSVWriter.writeError(errorFile, errorMessage);
            return;
        }

        try {
            // Чтение данных из CSV файлов с пропуском первой строки (заголовка)
            List<Product> products = ProductFactory.createProducts(CSVReader.read(pathToFile));
            List<DiscountCard> discountCards = DiscountCardFactory.createDiscountCards(CSVReader.read("./src/main/resources/discountCards.csv"));

            // Создание сервисов для работы с продуктами и дисконтными картами
            ProductService productService = new ProductServiceImpl(products);
            DiscountCardService discountCardService = new DiscountCardServiceImpl(discountCards);
            CheckService checkService = new CheckServiceImpl(productService, discountCardService);

            // Обработка заказа на основе аргументов командной строки
            checkService.processOrder(args, saveToFile);
        } catch (CheckException e) {
            // Запись ошибки в указанный CSV файл
            CSVWriter.writeError(saveToFile, e.getMessage());
        } catch (Exception e) {
            // Общая обработка ошибок
            e.printStackTrace(); // Вывод стека исключений

            CSVWriter.writeError(saveToFile, "INTERNAL SERVER ERROR");
        }
    }
}