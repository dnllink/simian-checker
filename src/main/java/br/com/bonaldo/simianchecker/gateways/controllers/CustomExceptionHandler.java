package br.com.bonaldo.simianchecker.gateways.controllers;

import br.com.bonaldo.simianchecker.gateways.controllers.jsons.ErrorResource;
import br.com.bonaldo.simianchecker.gateways.exceptions.InvalidConversionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Locale;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandler {

    private final MessageSource messageSource;

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidConversionException.class)
    public ErrorResource handleInvalidConversionException(final HttpServletRequest request, final InvalidConversionException exception) {
        log.error("Convertion failed: {}", exception.getMessage());
        return new ErrorResource(Collections.singletonList(getErrorMessage(exception.getMessage())));
    }

    private String getErrorMessage(final String error) {
        return messageSource.getMessage(error, null, Locale.getDefault());
    }
}