package com.poppulo.lotterysystem.utils;

import java.util.List;
import java.util.stream.Collectors;

import com.poppulo.lotterysystem.dto.LinesDTO;
import com.poppulo.lotterysystem.dto.ResponseDTO;
import com.poppulo.lotterysystem.dto.TicketDTO;
import com.poppulo.lotterysystem.entity.Ticket;

public class TicketMapper {
	
	 public static TicketDTO toTicketDTO(Ticket ticket) {
	        TicketDTO ticketDTO = new TicketDTO();
	        ticketDTO.setTicketId(ticket.getId());
	        ticket.getLines().stream()
	              .map(LinesDTO::new)
	              .forEach(ticketDTO::addLines);
	        return ticketDTO;
	    }

	    public static List<TicketDTO> toTicketDTOs(List<Ticket> tickets) {
	        return tickets.stream()
	                      .map(TicketMapper::toTicketDTO)
	                      .collect(Collectors.toList());
	    }

	    public static ResponseDTO toResponseDTO(Ticket ticket) {
	        ResponseDTO response = new ResponseDTO();
	        response.addTicket(toTicketDTO(ticket));
	        return response;
	    }

	    public static ResponseDTO toResponseDTO(List<Ticket> tickets) {
	        ResponseDTO response = new ResponseDTO();
	        response.addTickets(toTicketDTOs(tickets));
	        return response;
	    }

}
