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
        try {
            // Чтение данных из CSV файлов
            List<Product> products = ProductFactory.createProducts(CSVReader.read("./src/main/resources/products.csv"));
            List<DiscountCard> discountCards = DiscountCardFactory.createDiscountCards(CSVReader.read("./src/main/resources/discountCards.csv"));

            // Создание сервисов
            ProductService productService = new ProductServiceImpl(products);
            DiscountCardService discountCardService = new DiscountCardServiceImpl(discountCards);
            CheckService checkService = new CheckServiceImpl(productService, discountCardService);

            // Обработка заказа
            checkService.processOrder(args);
        } catch (CheckException e) {
            // Запись ошибки в CSV файл result.csv в корне проекта
            CSVWriter.writeError("result.csv", e.getMessage());
        } catch (Exception e) {
            // Общая обработка ошибок
            CSVWriter.writeError("result.csv", "INTERNAL SERVER ERROR");
        }
    }
}