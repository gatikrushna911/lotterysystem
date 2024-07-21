package com.poppulo.lotterysystem.exceptions;

import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.poppulo.lotterysystem.dto.ErrorDTO;
import com.poppulo.lotterysystem.dto.ResponseDTO;
import com.poppulo.lotterysystem.dto.TicketDTO;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<ResponseDTO> handleTicketNotFound(TicketNotFoundException ex) {
        ErrorDTO error = new ErrorDTO("NOT_FOUND", ex.getMessage());
        ResponseDTO response = new ResponseDTO();
        response.setError(error);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    @ExceptionHandler(TicketCheckedException.class)
    public ResponseEntity<ResponseDTO> handleTicketChecked(TicketCheckedException ex) {
        ErrorDTO error = new ErrorDTO("BAD_REQUEST", ex.getMessage());
        ResponseDTO response = new ResponseDTO();
        response.setError(error);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> handleGenericException(Exception ex) {
        ErrorDTO error = new ErrorDTO("INTERNAL_SERVER_ERROR", "An unexpected error occurred.");
        ResponseDTO response = new ResponseDTO();
        response.setError(error);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDTO> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorDTO error = new ErrorDTO("INVALID_INPUT", ex.getMessage());
        ResponseDTO response = new ResponseDTO();
        response.setError(error);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    @ExceptionHandler(TicketCreationException.class)
    public ResponseEntity<ResponseDTO> handleTicketCreationException(TicketCreationException ex) {
        ErrorDTO error = new ErrorDTO("CREATION_ERROR", ex.getMessage());
        ResponseDTO response = new ResponseDTO();
        response.setError(error);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
