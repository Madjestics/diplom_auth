package diplom.auth.web.controllers;

import diplom.auth.business.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(EntityIllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponseEntity handleEntityIllegalArgumentException(EntityIllegalArgumentException e){
        return createErrorResponseEntity(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorResponseEntity handleEntityAlreadyExistsException(EntityAlreadyExistsException e){
        return createErrorResponseEntity(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponseEntity handleInvalidTokenException(InvalidTokenException e){
        return createErrorResponseEntity(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NonExistentEntityException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponseEntity handleNonExistentEntityException(NonExistentEntityException e){
        return createErrorResponseEntity(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnavailableOperationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorResponseEntity handleUnavailableOperationException(UnavailableOperationException e){
        return createErrorResponseEntity(e, HttpStatus.FORBIDDEN);
    }

    private static ErrorResponseEntity createErrorResponseEntity(BaseException exception, HttpStatus httpStatus) {
        return new ErrorResponseEntity(exception.getMessage(), httpStatus.getReasonPhrase(), httpStatus.value());
    }
}
