package itavenues.exception;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public List<String> handleFieldValidation(MethodArgumentNotValidException exception) {
    var errors = exception.getAllErrors()
        .stream()
        .map(ObjectError::getDefaultMessage)
        .collect(Collectors.toList());

    errors.forEach(log::error);
    return errors;
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(Exception.class)
  public String handleCustomException(Exception exception) {
    log.error(exception.getMessage());
    return exception.getMessage();
  }
}
