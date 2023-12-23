package diplom.auth.business.exceptions;

/**
 * Исключение выбрасывается при вызове метода сервиса с некорректными параметрами
 */
public class EntityIllegalArgumentException extends BaseException{


    public EntityIllegalArgumentException(String message) {
        super(message);
    }
}
