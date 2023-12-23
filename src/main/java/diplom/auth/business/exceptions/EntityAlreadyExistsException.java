package diplom.auth.business.exceptions;

import org.springframework.util.Assert;

/**
 * Исключение вызывается при попытке создать сущность с уже существующим именем
 */
public class EntityAlreadyExistsException extends BaseException{

    public EntityAlreadyExistsException(String message) {
        super(message);
    }

    public EntityAlreadyExistsException(String type, Object userName){
        this(formatMassage(type, userName));
    }

    private static String formatMassage(String type, Object userName){
        Assert.hasText(type, "Тип неможет быть пустым");
        Assert.notNull(userName, "Имя не может быть null");
        Assert.hasText(userName.toString(), "Имя не может быть пустым");
        return String.format("%s с именем %s уже существует", type , userName);
    }
}
