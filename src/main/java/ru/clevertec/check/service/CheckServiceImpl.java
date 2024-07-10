// Реализация интерфейса CheckService для обработки заказа и создания чека
package main.java.ru.clevertec.check.service;

import main.java.ru.clevertec.check.exception.CheckException;
import main.java.ru.clevertec.check.model.CheckItem;
import main.java.ru.clevertec.check.model.DiscountCard;
import main.java.ru.clevertec.check.model.Product;
import main.java.ru.clevertec.check.util.CSVWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// Реализация сервиса обработки заказов и создания чека
public class CheckServiceImpl implements CheckService {

    private ProductService productService;
    private DiscountCardService discountCardService;


    /**
     * Конструктор для создания сервиса обработки заказа.
     *
     * @param productService    сервис продуктов
     * @param discountCardService сервис дисконтных карт
     */

    public CheckServiceImpl(ProductService productService, DiscountCardService discountCardService) {
        this.productService = productService;
        this.discountCardService = discountCardService;
    }

    /**
     * Обработать заказ на основе аргументов командной строки.
     *
     * @param args аргументы командной строки
     */

    @Override
    public void processOrder(String[] args) {
        processOrder(args, "result.csv"); // Вызов с дефолтным значением
    }

    @Override
    public void processOrder(String[] args, String saveToFile) {
        List<String> orders = new ArrayList<>();
        String discountCardNumber = null;
        double balance = 0;

        // Разбор аргументов командной строки
        for (String arg : args) {
            if (arg.startsWith("discountCard=")) {
                discountCardNumber = arg.split("=")[1];
            } else if (arg.startsWith("balanceDebitCard=")) {
                balance = Double.parseDouble(arg.split("=")[1]);
            } else {
                orders.add(arg);
            }
        }

        // Проверка на наличие обязательных параметров
        if (orders.isEmpty() || discountCardNumber == null || balance == 0) {
            throw new CheckException("BAD REQUEST");
        }

        final String finalDiscountCardNumber = discountCardNumber;
        List<CheckItem> checkItems = generateCheck(orders, finalDiscountCardNumber, balance);
        double totalPrice = checkItems.stream().mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity()).sum();
        double totalDiscount = checkItems.stream().mapToDouble(item -> calculateDiscount(item, finalDiscountCardNumber)).sum();
        double totalWithDiscount = totalPrice - totalDiscount;

        if (balance < totalWithDiscount) {
            throw new CheckException("NOT ENOUGH MONEY");
        }

        writeCheckToFile(checkItems, finalDiscountCardNumber, balance, totalPrice, totalDiscount, saveToFile);
        printCheckToConsole(saveToFile);
    }

    /**
     * Сформировать чек на основе заказов, дисконтной карты и баланса.
     *
     * @param orders            список строк заказов
     * @param discountCardNumber номер дисконтной карты
     * @param balance           баланс
     * @return список позиций чека
     */

    @Override
    public List<CheckItem> generateCheck(List<String> orders, String discountCardNumber, double balance) {
        List<CheckItem> checkItems = new ArrayList<>();
        for (String order : orders) {
            // Игнорируем аргументы pathToFile и saveToFile
            if (order.startsWith("pathToFile=") || order.startsWith("saveToFile=")) {
                continue;
            }

            String[] parts = order.split("-");
            if (parts.length != 2) {
                throw new CheckException("Invalid order format: " + order);
            }
            try {
                int productId = Integer.parseInt(parts[0].trim());
                int quantity = Integer.parseInt(parts[1].trim());

                Product product = productService.getProductById(productId);
                if (product == null) {
                    throw new CheckException("Product not found for ID: " + productId);
                }

                checkItems.add(new CheckItem(product, quantity));
            } catch (NumberFormatException e) {
                throw new CheckException("Invalid format for product ID or quantity: " + order);
            }
        }
        return checkItems;
    }

    /**
     * Вычислить скидку для позиции чека на основе дисконтной карты.
     *
     * @param item              позиция чека
     * @param discountCardNumber номер дисконтной карты
     * @return размер скидки
     */

    private double calculateDiscount(CheckItem item, String discountCardNumber) {
        DiscountCard discountCard = discountCardService.getDiscountCardByNumber(discountCardNumber);
        if (discountCard == null) {
            discountCard = new DiscountCard(0, discountCardNumber, 2); // Default discount 2%
        }

        double discount = 0;
        // Проверка на оптовую скидку
        if (item.getProduct().isWholesaleProduct() && item.getQuantity() >= 5) {
            discount = 0.10 * item.getProduct().getPrice() * item.getQuantity();
        } else {
            discount = (discountCard.getDiscountAmount() / 100.0) * item.getProduct().getPrice() * item.getQuantity();
        }

        return discount;
    }

    /**
     * Записать чек в файл CSV.
     *
     * @param checkItems       список позиций чека
     * @param discountCardNumber номер дисконтной карты
     * @param balance           баланс
     * @param totalPrice       общая стоимость
     * @param totalDiscount    общая скидка
     * @param saveToFile       место сохранения
     */


    @Override
    public void writeCheckToFile(List<CheckItem> checkItems, String discountCardNumber, double balance, double totalPrice, double totalDiscount, String saveToFile) {
        List<String[]> data = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        // Запись даты и времени
        data.add(new String[]{"Date", "Time"});
        data.add(new String[]{now.format(dateFormatter), now.format(timeFormatter)});
        // Запись заголовков
        data.add(new String[]{"QTY", "PRODUCT", "PRICE", "DISCOUNT", "TOTAL"});

        // Заполнение данных чека
        for (CheckItem item : checkItems) {
            double discount = calculateDiscount(item, discountCardNumber);
            data.add(new String[]{
                    String.valueOf(item.getQuantity()),
                    item.getProduct().getDescription(),
                    String.format("%.2f$", item.getProduct().getPrice()),
                    String.format("%.2f$", discount),
                    String.format("%.2f$", item.getProduct().getPrice() * item.getQuantity())
            });
        }

        // Добавление информации о скидочной карте в случае её использования из списка
        DiscountCard usedDiscountCard = discountCardService.getDiscountCardByNumber(discountCardNumber);
        if (usedDiscountCard != null) {
            data.add(new String[]{"DISCOUNT CARD", "DISCOUNT PERCENTAGE"});
            data.add(new String[]{
                    usedDiscountCard.getNumber(),
                    usedDiscountCard.getDiscountAmount() + "%"
            });
        }

        // Запись итоговых значений
        data.add(new String[]{"TOTAL PRICE", "TOTAL DISCOUNT", "TOTAL WITH DISCOUNT"});
        data.add(new String[]{
                String.format("%.2f$", totalPrice),
                String.format("%.2f$", totalDiscount),
                String.format("%.2f$", totalPrice - totalDiscount)
        });

        try {
            CSVWriter.write(saveToFile, data);
        } catch (IOException e) {
            throw new CheckException("INTERNAL SERVER ERROR");
        }
    }

    /**
     * Вывести чек на консоль.
     */

    private void printCheckToConsole(String saveToFile) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(saveToFile));
            for (String line : lines) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("INTERNAL SERVER ERROR");
            throw new CheckException("INTERNAL SERVER ERROR");

        }
    }
}