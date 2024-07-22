package com.poppulo.lotterysystem.exceptions;

import static com.poppulo.lotterysystem.utils.Constants.*;

public class TicketCheckedException extends RuntimeException {
    public TicketCheckedException(Long id) {
        super(TICKET_ID+ id + CHECKED);
    }
}
