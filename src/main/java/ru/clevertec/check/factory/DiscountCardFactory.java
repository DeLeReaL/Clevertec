// Фабрика для создания объектов из данных.
package main.java.ru.clevertec.check.factory;

import main.java.ru.clevertec.check.model.DiscountCard;

import java.util.ArrayList;
import java.util.List;

public class DiscountCardFactory {

    /**
     * Создает объект DiscountCard из массива данных.
     *
     * @param data массив данных о карте
     * @return объект DiscountCard
     */

    public static DiscountCard createDiscountCard(String[] data) {
        int id = Integer.parseInt(data[0]);
        String number = data[1];
        int discountAmount = Integer.parseInt(data[2]);

        return new DiscountCard(id, number, discountAmount);
    }

    /**
     * Создает список объектов DiscountCard из списка массивов данных.
     *
     * @param data список массивов данных о картах
     * @return список объектов DiscountCard
     */

    public static List<DiscountCard> createDiscountCards(List<String[]> data) {
        List<DiscountCard> discountCards = new ArrayList<>();
        for (String[] discountCardData : data) {
            discountCards.add(createDiscountCard(discountCardData));
        }
        return discountCards;
    }
}