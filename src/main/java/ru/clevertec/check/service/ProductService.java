//  Интерфейс для работы с продуктами
package main.java.ru.clevertec.check.service;

import main.java.ru.clevertec.check.model.Product;

public interface ProductService {

    /**
     * Получить продукт по идентификатору.
     *
     * @param id идентификатор продукта
     * @return объект Product или null, если продукт не найден
     */

    Product getProductById(int id);
}
