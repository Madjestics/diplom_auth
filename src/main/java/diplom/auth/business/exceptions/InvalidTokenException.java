package diplom.auth.business.exceptions;

/**
* Исключение вызывается при получении недействительного токена
*/
public class InvalidTokenException extends BaseException {
    public InvalidTokenException(String message) {
        super(message);
    }
}
