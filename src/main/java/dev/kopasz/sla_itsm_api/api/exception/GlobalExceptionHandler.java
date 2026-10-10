package dev.kopasz.sla_itsm_api.api.exception;

import dev.kopasz.sla_itsm_api.domain.exception.SlaCalculationException;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SlaCalculationException.class)
    public ProblemDetail slaCalculationExceptionHandler(SlaCalculationException e){

        ProblemDetail problemDetail = ProblemDetail
                .forStatusAndDetail(HttpStatus.UNPROCESSABLE_CONTENT,e.getMessage());
        problemDetail.setTitle("SLA Calculation Error");

        log.error(problemDetail.toString());

        return problemDetail;

    }

    //  Jakarta @Valid exceptions
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail validationExceptionHandler(MethodArgumentNotValidException e){

        ProblemDetail problemDetail = ProblemDetail
                .forStatusAndDetail(HttpStatus.BAD_REQUEST,e.getMessage()+
                        " / Egy vagy több mező érvénytelen a bejövő kérésben");
        problemDetail.setTitle("Validation Error");

        Map<String, String> validationErrors = e.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(
            FieldError::getField,
            FieldError::getDefaultMessage,
            (firstError, secondError) -> firstError
        ));
        problemDetail.setProperty("invalidFields", validationErrors);

        log.warn(problemDetail.toString());

        return problemDetail;

    }

}
