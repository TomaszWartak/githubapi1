package pl.dev4lazy.githubapi1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import pl.dev4lazy.githubapi1.dto.ErrorResponse;

import java.util.HashMap;
import java.util.Map;

import static pl.dev4lazy.githubapi1.constants.ErrorMessages.*;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler( HttpRequestMethodNotSupportedException.class )
    public ResponseEntity<ErrorResponse> handleMethodNotAllowed( final HttpRequestMethodNotSupportedException ex) {
        final ErrorResponse error = new ErrorResponse(
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                String.format( HTTP_METHOD_NOT_ALLOWED, ex.getMethod( ) )
        );
        return ResponseEntity
                .status( HttpStatus.METHOD_NOT_ALLOWED )
                .body( error );
    }


    @ExceptionHandler( MethodArgumentNotValidException.class )
    @ResponseStatus( HttpStatus.BAD_REQUEST ) // 400
    public Map<String,String> handleValidationExceptions(
            final MethodArgumentNotValidException argumentNotValidException
    ) {
        final Map<String,String> errors = new HashMap<>();
        argumentNotValidException
                .getBindingResult()
                .getFieldErrors()
                .forEach( err ->
                        errors.put( err.getField(), err.getDefaultMessage() )
                );
        return errors;
    }

    @ExceptionHandler( NoHandlerFoundException.class )
    public ResponseEntity<ErrorResponse> handleNotFound( final NoHandlerFoundException ex ) {
        final ErrorResponse err = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                String.format( ENDPOINT_NOT_FOUND, ex.getRequestURL() )
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler( UserNotFoundException.class )
    public ResponseEntity< ErrorResponse > handleUserNotFoundException( final UserNotFoundException ex) {
        final ErrorResponse body = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(body);
    }
}
