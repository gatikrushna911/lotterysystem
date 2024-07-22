package com.poppulo.lotterysystem;


import static com.poppulo.lotterysystem.utils.Constants.TICKET_CREATION_FAILED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.poppulo.lotterysystem.dto.ResponseDTO;
import com.poppulo.lotterysystem.entity.Ticket;
import com.poppulo.lotterysystem.exceptions.TicketCheckedException;
import com.poppulo.lotterysystem.exceptions.TicketCreationException;
import com.poppulo.lotterysystem.exceptions.TicketNotFoundException;
import com.poppulo.lotterysystem.repository.TicketRepository;
import com.poppulo.lotterysystem.service.TicketService;
import com.poppulo.lotterysystem.utils.TicketMapper;

@SpringBootTest
public class TicketServiceTest {

    @InjectMocks
    private TicketService ticketService;

    @Mock
    private TicketRepository ticketRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createTicket_Success() {
        int linesCount = 3;
        Ticket ticket = new Ticket();

        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        ResponseDTO result = ticketService.createTicket(linesCount);

        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertNotNull(result.getTickets());
    }

    @Test
    public void createTicket_Exception() {
        int linesCount = 3;

        when(ticketRepository.save(any(Ticket.class)))
                .thenThrow(new RuntimeException("Database error"));

        try {
            ticketService.createTicket(linesCount);
            fail("Expected TicketCreationException");
        } catch (TicketCreationException e) {
            assertEquals(TICKET_CREATION_FAILED, e.getMessage());
        }
    }

    @Test
    public void getTicket_ValidId_Success() {
        Long id = 1L;
        Ticket ticket = new Ticket();

        when(ticketRepository.findById(id)).thenReturn(java.util.Optional.of(ticket));

        ResponseDTO result = ticketService.getTicket(id);

        assertNotNull(result);
        assertTrue(result.isSuccess());
    }

    @Test
    public void getTicket_InvalidId_NotFound() {
        Long id = 1L;

        when(ticketRepository.findById(id)).thenReturn(java.util.Optional.empty());

        try {
            ticketService.getTicket(id);
            fail("Expected TicketNotFoundException");
        } catch (TicketNotFoundException e) {
            assertEquals("Ticket with id 1 not found.", e.getMessage());
        }
    }

    @Test
    public void getAllTickets_Success() {
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(new Ticket());
        tickets.add(new Ticket());

        when(ticketRepository.findAll()).thenReturn(tickets);

        ResponseDTO result = ticketService.getAllTickets();

        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals(2, result.getTickets().size());
    }

    @Test
    public void amendTicket_ValidRequest_Success() {
        Long id = 1L;
        int linesCount = 3;
        Ticket ticket = new Ticket();

        when(ticketRepository.findById(id)).thenReturn(java.util.Optional.of(ticket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        ResponseDTO result = ticketService.amendTicket(id, linesCount);

        assertNotNull(result);
        assertTrue(result.isSuccess());
    }

    @Test
    public void checkTicketStatus_ValidId_Success() {
        Long id = 1L;
        Ticket ticket = new Ticket();

        when(ticketRepository.findById(id)).thenReturn(java.util.Optional.of(ticket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        ResponseDTO result = ticketService.checkTicketStatus(id);

        assertNotNull(result);
        assertTrue(result.isSuccess());
    }

}
