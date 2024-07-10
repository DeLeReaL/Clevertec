// Фабрика для создания продуктов
package main.java.ru.clevertec.check.factory;

import main.java.ru.clevertec.check.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductFactory {

    /**
     * Создает объект Product из массива данных.
     *
     * @param data массив данных о продукте
     * @return объект Product
     */

    public static Product createProduct(String[] data) {
        int id = Integer.parseInt(data[0]);
        String description = data[1];
        double price = Double.parseDouble(data[2]);
        int quantityInStock = Integer.parseInt(data[3]);
        boolean isWholesaleProduct = data[4].equals("+");

        return new Product(id, description, price, quantityInStock, isWholesaleProduct);
    }

    /**
     * Создает список объектов Product из списка массивов данных.
     *
     * @param data список массивов данных о продуктах
     * @return список объектов Product
     */

    public static List<Product> createProducts(List<String[]> data) {
        List<Product> products = new ArrayList<>();
        for (String[] productData : data) {
            products.add(createProduct(productData));
        }
        return products;
    }
}