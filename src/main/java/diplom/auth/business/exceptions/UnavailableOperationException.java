package diplom.auth.business.exceptions;

import org.springframework.util.Assert;

public class UnavailableOperationException extends BaseException {
    public UnavailableOperationException(String message) {
        super(message);
    }

    public UnavailableOperationException(Object authority){
        this(formatMassage( authority));
    }

    private static String formatMassage(Object authority){
        Assert.notNull(authority, "Право не может быть null");
        Assert.hasText(authority.toString(), "Право не может быть пустым");
        return String.format("Нет доступа к праву %", authority);
    }
}
