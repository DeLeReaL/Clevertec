// Интерфейс для обработки заказа и создания чека.
package main.java.ru.clevertec.check.service;

import main.java.ru.clevertec.check.model.CheckItem;

import java.util.List;

public interface CheckService {

    /**
     * Обработать заказ на основе аргументов командной строки.
     *
     * @param args аргументы командной строки
     */

    void processOrder(String[] args);
    void processOrder(String[] args, String saveToFile); // Новый метод


    /**
     * Сформировать чек на основе заказов, дисконтной карты и баланса.
     *
     * @param orders            список строк заказов
     * @param discountCardNumber номер дисконтной карты
     * @param balance           баланс
     * @return список позиций чека
     */


    List<CheckItem> generateCheck(List<String> orders, String discountCardNumber, double balance);

    /**
     * Записать чек в файл.
     *
     * @param checkItems       список позиций чека
     * @param discountCardNumber номер дисконтной карты
     * @param balance           баланс
     * @param totalPrice       общая стоимость
     * @param totalDiscount    общая скидка
     */

    void writeCheckToFile(List<CheckItem> checkItems, String discountCardNumber, double balance, double totalPrice, double totalDiscount, String saveToFile);
}
