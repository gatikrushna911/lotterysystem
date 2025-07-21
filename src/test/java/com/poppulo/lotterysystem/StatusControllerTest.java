package com.poppulo.lotterysystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.poppulo.lotterysystem.controller.StatusController;
import com.poppulo.lotterysystem.dto.ResponseDTO;
import com.poppulo.lotterysystem.entity.Ticket;
import com.poppulo.lotterysystem.exceptions.TicketCheckedException;
import com.poppulo.lotterysystem.exceptions.TicketNotFoundException;
import com.poppulo.lotterysystem.service.TicketService;
import com.poppulo.lotterysystem.utils.TicketMapper;

@SpringBootTest
public class StatusControllerTest {

    @InjectMocks
    private StatusController statusController;

    @Mock
    private TicketService ticketService;

    @Test
    public void checkTicketStatus_ValidId_Success() {
        // Arrange
        Long id = 1L;
        Ticket ticket = new Ticket();
        ResponseDTO responseDTO = TicketMapper.toResponseDTO(ticket);

        when(ticketService.checkTicketStatus(id)).thenReturn(responseDTO);

        // Act
        ResponseEntity<ResponseDTO> response = statusController.checkTicketStatus(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
    }

    @Test
    public void checkTicketStatus_InvalidId_NotFound() {
        // Arrange
        Long id = 1L;

        when(ticketService.checkTicketStatus(id)).thenThrow(new TicketNotFoundException(id));

        // Act & Assert
        Exception exception = assertThrows(TicketNotFoundException.class, () -> {
            statusController.checkTicketStatus(id);
        });

        assertEquals("Ticket with id 1 not found.", exception.getMessage());
    }

    @Test
    public void checkTicketStatus_AlreadyChecked_Exception() {
        // Arrange
        Long id = 1L;

        when(ticketService.checkTicketStatus(id)).thenThrow(new TicketCheckedException(id));

        // Act & Assert
        Exception exception = assertThrows(TicketCheckedException.class, () -> {
            statusController.checkTicketStatus(id);
        });

        assertEquals("Ticket with id1 has already been checked.", exception.getMessage());
    }
}