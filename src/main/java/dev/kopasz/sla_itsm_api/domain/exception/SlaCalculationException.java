package dev.kopasz.sla_itsm_api.domain.exception;

public class SlaCalculationException extends RuntimeException {

    public  SlaCalculationException(String message) {
        super(message);
    }

    public  SlaCalculationException(String message,Throwable cause) {
        super(message,cause);
    }

}
