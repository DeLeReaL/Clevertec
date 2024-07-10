// ИИнтерфейс для работы с дисконтными картами
package main.java.ru.clevertec.check.service;

import main.java.ru.clevertec.check.model.DiscountCard;

public interface DiscountCardService {

    /**
     * Получить дисконтную карту по номеру.
     *
     * @param number номер дисконтной карты
     * @return объект DiscountCard или null, если карта не найдена
     */

    DiscountCard getDiscountCardByNumber(String number);
}