// Модель для представления дисконтной карты
package main.java.ru.clevertec.check.model;

public class DiscountCard {
    private int id;
    private String number;
    private int discountAmount;

    /**
     * Конструктор для создания дисконтной карты.
     *
     * @param id            идентификатор карты
     * @param number        номер карты
     * @param discountAmount размер скидки на карте
     */

    public DiscountCard(int id, String number, int discountAmount) {
        this.id = id;
        this.number = number;
        this.discountAmount = discountAmount;
    }

    /**
     * Получить идентификатор карты.
     *
     * @return идентификатор карты
     */

    public int getId() {
        return id;
    }

    /**
     * Получить номер карты.
     *
     * @return номер карты
     */

    public String getNumber() {
        return number;
    }

    /**
     * Получить размер скидки на карте.
     *
     * @return размер скидки на карте
     */

    public int getDiscountAmount() {
        return discountAmount;
    }
}