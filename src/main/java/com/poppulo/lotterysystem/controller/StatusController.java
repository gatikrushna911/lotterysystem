package com.poppulo.lotterysystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poppulo.lotterysystem.dto.ResponseDTO;
import com.poppulo.lotterysystem.entity.Ticket;
import com.poppulo.lotterysystem.service.TicketService;

@RestController
@RequestMapping("/status")
public class StatusController {
    @Autowired
    private TicketService ticketService;

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> checkTicketStatus(@PathVariable Long id) {
    	ResponseDTO response = ticketService.checkTicketStatus(id);
        return ResponseEntity.ok(response);
    }
}

