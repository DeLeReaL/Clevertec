// Модель для представления продукта
package main.java.ru.clevertec.check.model;

public class Product {
    private int id;
    private String description;
    private double price;
    private int quantityInStock;
    private boolean isWholesaleProduct;

    /**
     * Конструктор для создания продукта.
     *
     * @param id                 идентификатор продукта
     * @param description        описание продукта
     * @param price              цена продукта
     * @param quantityInStock    количество продукта на складе
     * @param isWholesaleProduct признак оптового продукта
     */

    public Product(int id, String description, double price, int quantityInStock, boolean isWholesaleProduct) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.quantityInStock = quantityInStock;
        this.isWholesaleProduct = isWholesaleProduct;
    }

    /**
     * Получить идентификатор продукта.
     *
     * @return идентификатор продукта
     */

    public int getId() {
        return id;
    }

    /**
     * Получить описание продукта.
     *
     * @return описание продукта
     */

    public String getDescription() {
        return description;
    }

    /**
     * Получить цену продукта.
     *
     * @return цена продукта
     */

    public double getPrice() {
        return price;
    }

    /**
     * Получить количество продукта на складе.
     *
     * @return количество продукта на складе
     */

    public int getQuantityInStock() {
        return quantityInStock;
    }

    /**
     * Получить признак оптового продукта.
     *
     * @return true, если продукт является оптовым, иначе false
     */

    public boolean isWholesaleProduct() {
        return isWholesaleProduct;
    }

    /**
     * Установить количество продукта на складе.
     *
     * @param quantityInStock новое количество продукта на складе
     */

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }
}
