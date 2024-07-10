// Модель для представления позиции в чеке
package main.java.ru.clevertec.check.model;

public class CheckItem {
    private Product product;
    private int quantity;

    /**
     * Конструктор для создания позиции чека.
     *
     * @param product  продукт
     * @param quantity количество продукта
     */

    public CheckItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    /**
     * Получить продукт позиции чека.
     *
     * @return объект Product
     */

    public Product getProduct() {
        return product;
    }

    /**
     * Получить количество продукта позиции чека.
     *
     * @return количество продукта
     */

    public int getQuantity() {
        return quantity;
    }
}
