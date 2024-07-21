package com.poppulo.lotterysystem.exceptions;

public class TicketCheckedException extends RuntimeException {
    public TicketCheckedException(Long id) {
        super("Ticket with id " + id + " has already been checked.");
    }
}
