package com.poppulo.lotterysystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.poppulo.lotterysystem.controller.TicketController;
import com.poppulo.lotterysystem.dto.ResponseDTO;
import com.poppulo.lotterysystem.entity.Ticket;
import com.poppulo.lotterysystem.exceptions.TicketCheckedException;
import com.poppulo.lotterysystem.exceptions.TicketNotFoundException;
import com.poppulo.lotterysystem.service.TicketService;
import com.poppulo.lotterysystem.utils.TicketMapper;

@SpringBootTest
public class TicketControllerTest {
	
	  @InjectMocks
	    private TicketController ticketController;

	  @Mock
	   private TicketService ticketService;
	   
	   @Test
	    public void createTicket_ValidRequest_Success() {
	        Map<String, Integer> request = Map.of("lines", 3);
	        Ticket ticket = new Ticket();
	        ResponseDTO responseDTO = TicketMapper.toResponseDTO(ticket);

	        when(ticketService.createTicket(3)).thenReturn(responseDTO);

	        ResponseEntity<ResponseDTO> response = ticketController.createTicket(request);

	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertNotNull(response.getBody());
	        assertTrue(response.getBody().isSuccess());
	    }
	   @Test
	   public void createTicket_InvalidRequest_MissingLines() {
	        Map<String, Integer> request = Map.of();

	        try {
	            ticketController.createTicket(request);
	            fail("Expected IllegalArgumentException");
	        } catch (IllegalArgumentException ex) {
	            assertEquals("The 'lines' parameter must be provided and greater than 0.", ex.getMessage());
	        }
	    }
	    @Test
	    public void getAllTickets_Success() {
	        List<Ticket> tickets = List.of(new Ticket(), new Ticket());
	        ResponseDTO responseDTO = TicketMapper.toResponseDTO(tickets);

	        when(ticketService.getAllTickets()).thenReturn(responseDTO);

	        ResponseEntity<ResponseDTO> response = ticketController.getAllTickets();

	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertNotNull(response.getBody());
	        assertTrue(response.getBody().hasTickets());
	    }

	    @Test
	    public void getTicket_ValidId_Success() {
	        Long id = 1L;
	        Ticket ticket = new Ticket();
	        ResponseDTO responseDTO = TicketMapper.toResponseDTO(ticket);

	        when(ticketService.getTicket(id)).thenReturn(responseDTO);

	        ResponseEntity<ResponseDTO> response = ticketController.getTicket(id);

	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertNotNull(response.getBody());
	    }

	   /* @Test
	    public void getTicket_InvalidId_NotFound() {
	        Long id = 1L;

	        when(ticketService.getTicket(id)).thenThrow(new TicketNotFoundException(id));

	        ResponseEntity<ResponseDTO> response = ticketController.getTicket(id);

	        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	        assertNotNull(response.getBody());
	        assertTrue(response.getBody().hasError());
	    }*/

	   @Test
	    public void amendTicket_ValidRequest_Success() {
	        Long id = 1L;
	        Map<String, Integer> request = Map.of("lines", 3);
	        Ticket ticket = new Ticket();
	        ResponseDTO responseDTO = TicketMapper.toResponseDTO(ticket);

	        when(ticketService.amendTicket(id, 3)).thenReturn(responseDTO);

	        ResponseEntity<ResponseDTO> response = ticketController.amendTicket(id, request);

	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertNotNull(response.getBody());
	    }

	    /*@Test
	    public void amendTicket_TicketChecked_Exception() {
	        Long id = 1L;
	        Map<String, Integer> request = Map.of("lines", 3);

	        when(ticketService.amendTicket(id, 3)).thenThrow(new TicketCheckedException(id));

	        ResponseEntity<ResponseDTO> response = ticketController.amendTicket(id, request);

	        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	        assertNotNull(response.getBody());
	        assertTrue(response.getBody().hasError());
	    }*/
}
