package com.poppulo.lotterysystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.poppulo.lotterysystem.dto.LinesDTO;
import com.poppulo.lotterysystem.dto.ResponseDTO;
import com.poppulo.lotterysystem.dto.TicketDTO;
import com.poppulo.lotterysystem.entity.Line;
import com.poppulo.lotterysystem.entity.Ticket;
import com.poppulo.lotterysystem.utils.TicketMapper;

@SpringBootTest
public class TicketMapperTest {

    @Test
    public void toTicketDTO_ValidTicket_Success() {
        // Arrange
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        
        Line line1 = new Line(1, 1, 1);
        Line line2 = new Line(0, 1, 2);
        
        ticket.addLines(Arrays.asList(line1, line2));

        // Act
        TicketDTO ticketDTO = TicketMapper.toTicketDTO(ticket);

        // Assert
        assertNotNull(ticketDTO);
        assertEquals(1L, ticketDTO.getTicketId());
        assertEquals(2, ticketDTO.getLines().size());
    }

    @Test
    public void toTicketDTOs_ValidTickets_Success() {
        // Arrange
        Ticket ticket1 = new Ticket();
        ticket1.setId(1L);
        
        Ticket ticket2 = new Ticket();
        ticket2.setId(2L);
        
        List<Ticket> tickets = Arrays.asList(ticket1, ticket2);

        // Act
        List<TicketDTO> ticketDTOs = TicketMapper.toTicketDTOs(tickets);

        // Assert
        assertNotNull(ticketDTOs);
        assertEquals(2, ticketDTOs.size());
        assertEquals(1L, ticketDTOs.get(0).getTicketId());
        assertEquals(2L, ticketDTOs.get(1).getTicketId());
    }

    @Test
    public void toResponseDTO_SingleTicket_Success() {
        // Arrange
        Ticket ticket = new Ticket();
        ticket.setId(1L);

        // Act
        ResponseDTO responseDTO = TicketMapper.toResponseDTO(ticket);

        // Assert
        assertNotNull(responseDTO);
        assertTrue(responseDTO.isSuccess());
        assertEquals(1, responseDTO.getTickets().size());
        assertEquals(1L, responseDTO.getTickets().get(0).getTicketId());
    }

    @Test
    public void toResponseDTO_MultipleTickets_Success() {
        // Arrange
        Ticket ticket1 = new Ticket();
        ticket1.setId(1L);
        
        Ticket ticket2 = new Ticket();
        ticket2.setId(2L);
        
        List<Ticket> tickets = Arrays.asList(ticket1, ticket2);

        // Act
        ResponseDTO responseDTO = TicketMapper.toResponseDTO(tickets);

        // Assert
        assertNotNull(responseDTO);
        assertTrue(responseDTO.isSuccess());
        assertEquals(2, responseDTO.getTickets().size());
        assertEquals(1L, responseDTO.getTickets().get(0).getTicketId());
        assertEquals(2L, responseDTO.getTickets().get(1).getTicketId());
    }

    @Test
    public void linesDTO_Constructor_Success() {
        // Arrange
        Line line = new Line(1, 2, 0);
        line.setId(1L);
        line.setResult(10);

        // Act
        LinesDTO linesDTO = new LinesDTO(line);

        // Assert
        assertNotNull(linesDTO);
        assertEquals(1, linesDTO.getNumber1());
        assertEquals(2, linesDTO.getNumber2());
        assertEquals(0, linesDTO.getNumber3());
        assertEquals(10, linesDTO.getResult());
    }
}