package estate.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.time.LocalDate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(OwnerNotFoundException.class)
    public ResponseEntity<Object> handleOwnerNotFoundException(
            OwnerNotFoundException ex) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OwnerExistsException.class)
    public ResponseEntity<Object> handleOwnerExistsException(
            OwnerExistsException ex) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TaxRateNotExistsException.class)
    public ResponseEntity<Object> handleTaxRateNotExistsException(
            TaxRateNotExistsException ex) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TaxRateExistsException.class)
    public ResponseEntity<Object> handleTaxRateExistsException(
            TaxRateExistsException ex) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BuildingNotFoundException.class)
    public ResponseEntity<Object> handleBuildingNotFoundException(
            BuildingNotFoundException ex) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BuildingExistsException.class)
    public ResponseEntity<Object> handleBuildingExistsException(
            BuildingExistsException ex) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<Object> handleAddressNotFoundException(
            AddressNotFoundException ex) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AddressExistsException.class)
    public ResponseEntity<Object> handleAddressExistsException(
            AddressExistsException ex) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<Object> handleNodataFoundException(
            NoDataFoundException ex) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDate.now());
        body.put("status", status.value());

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

}
