// Исключение для проверки ошибок
package main.java.ru.clevertec.check.exception;

public class CheckException extends RuntimeException {
    public CheckException(String message) {
        super(message);
    }
}