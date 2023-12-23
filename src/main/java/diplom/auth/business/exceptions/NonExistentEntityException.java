package diplom.auth.business.exceptions;

import org.springframework.util.Assert;

/**
 * Исключение вызывается при попытке найти сущность с несуществующим именем
 */
public class NonExistentEntityException extends BaseException {
    public NonExistentEntityException(String message) {
        super(message);
    }
    public NonExistentEntityException(String type, Object id){
        this(formatMassage(type, id));
    }

    private static String formatMassage(String type, Object userName){
        Assert.hasText(type, "Тип неможет быть пустым");
        Assert.notNull(userName, "Имя не может быть null");
        Assert.hasText(userName.toString(), "Имя не может быть пустым");
        return String.format("%s с именем %s не найден(а)", type , userName);
    }
}
