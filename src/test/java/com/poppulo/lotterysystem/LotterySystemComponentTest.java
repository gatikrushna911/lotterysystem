package com.poppulo.lotterysystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.poppulo.lotterysystem.dto.LinesDTO;
import com.poppulo.lotterysystem.dto.ResponseDTO;
import com.poppulo.lotterysystem.dto.TicketDTO;
import com.poppulo.lotterysystem.entity.Ticket;
import com.poppulo.lotterysystem.repository.TicketRepository;

import static com.poppulo.lotterysystem.utils.Constants.LINES;

/**
 * Component test for the Lottery System.
 * This test verifies the end-to-end flow of creating a ticket, amending it, and checking its status.
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LotterySystemComponentTest {

    @Autowired
    private TestRestTemplate restTemplate;
    
    @Autowired
    private TicketRepository ticketRepository;
    
    /**
     * Test the complete flow of the lottery system:
     * 1. Create a ticket
     * 2. Retrieve the ticket
     * 3. Amend the ticket
     * 4. Check the ticket status
     */
    @Test
    public void testCompleteTicketLifecycle() {
        // 1. Create a ticket with 3 lines
        Map<String, Integer> createRequest = Map.of(LINES, 3);
        ResponseEntity<ResponseDTO> createResponse = restTemplate.postForEntity(
                "/ticket", createRequest, ResponseDTO.class);
        
        assertEquals(HttpStatus.OK, createResponse.getStatusCode());
        assertNotNull(createResponse.getBody());
        assertTrue(createResponse.getBody().isSuccess());
        
        // Verify ticket was created with 3 lines
        TicketDTO createdTicket = createResponse.getBody().getTickets().get(0);
        assertNotNull(createdTicket);
        assertEquals(3, createdTicket.getLines().size());
        Long ticketId = createdTicket.getTicketId();
        
        // 2. Retrieve the ticket
        ResponseEntity<ResponseDTO> getResponse = restTemplate.getForEntity(
                "/ticket/" + ticketId, ResponseDTO.class);
        
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        assertTrue(getResponse.getBody().isSuccess());
        
        // Verify retrieved ticket matches created ticket
        TicketDTO retrievedTicket = getResponse.getBody().getTickets().get(0);
        assertEquals(ticketId, retrievedTicket.getTicketId());
        assertEquals(3, retrievedTicket.getLines().size());
        
        // 3. Amend the ticket by adding 2 more lines
        Map<String, Integer> amendRequest = Map.of(LINES, 2);
        ResponseEntity<ResponseDTO> amendResponse = restTemplate.exchange(
                "/ticket/" + ticketId, HttpMethod.PUT, new HttpEntity<>(amendRequest), ResponseDTO.class);
        
        assertEquals(HttpStatus.OK, amendResponse.getStatusCode());
        assertNotNull(amendResponse.getBody());
        assertTrue(amendResponse.getBody().isSuccess());
        
        // Verify ticket now has 5 lines (3 original + 2 new)
        TicketDTO amendedTicket = amendResponse.getBody().getTickets().get(0);
        assertEquals(ticketId, amendedTicket.getTicketId());
        assertEquals(5, amendedTicket.getLines().size());
        
        // 4. Check the ticket status
        ResponseEntity<ResponseDTO> statusResponse = restTemplate.exchange(
                "/status/" + ticketId, HttpMethod.PUT, null, ResponseDTO.class);
        
        assertEquals(HttpStatus.OK, statusResponse.getStatusCode());
        assertNotNull(statusResponse.getBody());
        assertTrue(statusResponse.getBody().isSuccess());
        
        // Verify ticket has been checked and results have been calculated
        TicketDTO checkedTicket = statusResponse.getBody().getTickets().get(0);
        assertEquals(ticketId, checkedTicket.getTicketId());
        assertEquals(5, checkedTicket.getLines().size());
        
        // Verify all lines have results calculated
        List<LinesDTO> lines = checkedTicket.getLines();
        for (LinesDTO line : lines) {
            assertTrue(line.getResult() >= 0, "Line result should be calculated");
        }
        
        // Verify ticket is marked as checked in the database
        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
        assertNotNull(ticket);
        assertTrue(ticket.isChecked(), "Ticket should be marked as checked");
        
        // 5. Verify that we cannot amend a checked ticket
        ResponseEntity<ResponseDTO> invalidAmendResponse = restTemplate.exchange(
                "/ticket/" + ticketId, HttpMethod.PUT, new HttpEntity<>(amendRequest), ResponseDTO.class);
        
        assertEquals(HttpStatus.BAD_REQUEST, invalidAmendResponse.getStatusCode());
    }
    
    /**
     * Test retrieving all tickets
     */
    @Test
    public void testGetAllTickets() {
        // Create a few tickets
        restTemplate.postForEntity("/ticket", Map.of(LINES, 1), ResponseDTO.class);
        restTemplate.postForEntity("/ticket", Map.of(LINES, 2), ResponseDTO.class);
        
        // Retrieve all tickets
        ResponseEntity<ResponseDTO> getAllResponse = restTemplate.getForEntity("/ticket", ResponseDTO.class);
        
        assertEquals(HttpStatus.OK, getAllResponse.getStatusCode());
        assertNotNull(getAllResponse.getBody());
        assertTrue(getAllResponse.getBody().isSuccess());
        
        // Verify we have at least the 2 tickets we just created
        List<TicketDTO> allTickets = getAllResponse.getBody().getTickets();
        assertNotNull(allTickets);
        assertTrue(allTickets.size() >= 2, "Should have at least 2 tickets");
    }
    
    /**
     * Test error handling for invalid requests
     */
    @Test
    public void testErrorHandling() {
        // Test creating a ticket with invalid number of lines
        Map<String, Integer> invalidRequest = Map.of(LINES, -1);
        ResponseEntity<ResponseDTO> invalidCreateResponse = restTemplate.postForEntity(
                "/ticket", invalidRequest, ResponseDTO.class);
        
        assertEquals(HttpStatus.BAD_REQUEST, invalidCreateResponse.getStatusCode());
        
        // Test retrieving a non-existent ticket
        ResponseEntity<ResponseDTO> notFoundResponse = restTemplate.getForEntity(
                "/ticket/999999", ResponseDTO.class);
        
        assertEquals(HttpStatus.NOT_FOUND, notFoundResponse.getStatusCode());
    }
}