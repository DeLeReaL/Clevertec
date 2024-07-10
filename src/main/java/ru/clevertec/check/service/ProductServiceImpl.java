// Реализация интерфейса ProductService для работы с продуктами
package main.java.ru.clevertec.check.service;

import main.java.ru.clevertec.check.model.Product;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    private List<Product> products;

    /**
     * Конструктор для создания сервиса продуктов.
     *
     * @param products список продуктов
     */

    public ProductServiceImpl(List<Product> products) {
        this.products = products;
    }

    /**
     * Получить продукт по идентификатору.
     *
     * @param id идентификатор продукта
     * @return объект Product или null, если продукт не найден
     */

    @Override
    public Product getProductById(int id) {
        return products.stream()
                .filter(product -> product.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
