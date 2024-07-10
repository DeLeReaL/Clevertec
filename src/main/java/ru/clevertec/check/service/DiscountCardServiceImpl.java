// Реализация интерфейса DiscountCardService для работы с дисконтными картами
package main.java.ru.clevertec.check.service;

import main.java.ru.clevertec.check.model.DiscountCard;

import java.util.List;

public class DiscountCardServiceImpl implements DiscountCardService {

    private List<DiscountCard> discountCards;

    /**
     * Конструктор для создания сервиса дисконтных карт.
     *
     * @param discountCards список дисконтных карт
     */

    public DiscountCardServiceImpl(List<DiscountCard> discountCards) {
        this.discountCards = discountCards;
    }

    /**
     * Получить дисконтную карту по номеру.
     *
     * @param number номер дисконтной карты
     * @return объект DiscountCard или null, если карта не найдена
     */

    @Override
    public DiscountCard getDiscountCardByNumber(String number) {
        return discountCards.stream()
                .filter(card -> card.getNumber().equals(number))
                .findFirst()
                .orElse(null);
    }
}