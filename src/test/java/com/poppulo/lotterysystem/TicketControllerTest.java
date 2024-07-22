package com.poppulo.lotterysystem;
import static com.poppulo.lotterysystem.utils.Constants.ILLEGAL_ARGUMENTS;
import static com.poppulo.lotterysystem.utils.Constants.LINES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        Map<String, Integer> request = Map.of(LINES, 3);
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

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ticketController.createTicket(request);
        });

        assertEquals(ILLEGAL_ARGUMENTS, exception.getMessage());
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

    @Test
    public void getTicket_InvalidId_NotFound() {
        Long id = 1L;

        when(ticketService.getTicket(id)).thenThrow(new TicketNotFoundException(id));

        Exception exception = assertThrows(TicketNotFoundException.class, () -> {
            ticketController.getTicket(id);
        });

        assertEquals("Ticket with id 1 not found.", exception.getMessage());
    }

    @Test
    public void amendTicket_ValidRequest_Success() {
        Long id = 1L;
        Map<String, Integer> request = Map.of(LINES, 3);
        Ticket ticket = new Ticket();
        ResponseDTO responseDTO = TicketMapper.toResponseDTO(ticket);

        when(ticketService.amendTicket(id, 3)).thenReturn(responseDTO);

        ResponseEntity<ResponseDTO> response = ticketController.amendTicket(id, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

}
