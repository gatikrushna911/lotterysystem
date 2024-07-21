package com.poppulo.lotterysystem.dto;

import java.util.ArrayList;
import java.util.List;

public class ResponseDTO {

	private List<TicketDTO> tickets = new ArrayList<>();
    private ErrorDTO error;

	public List<TicketDTO> getTickets() {
		return tickets;
	}

	public void addTicket(TicketDTO ticket) {
		this.tickets.add(ticket);
	}

	public void addTickets(List<TicketDTO> tickets) {
		this.tickets.addAll(tickets);
	}

	public ErrorDTO getError() {
		return error;
	}

	public void setError(ErrorDTO error) {
		this.error = error;
	}
	public boolean hasTickets() {
        return !tickets.isEmpty();
    }

    public boolean hasError() {
        return error != null;
    }

    public boolean isSuccess() {
        return hasTickets() && !hasError();
    }
}
