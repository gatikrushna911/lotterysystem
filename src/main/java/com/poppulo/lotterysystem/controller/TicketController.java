package com.poppulo.lotterysystem.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.poppulo.lotterysystem.utils.Constants.*;
import com.poppulo.lotterysystem.dto.ResponseDTO;
import com.poppulo.lotterysystem.service.TicketService;
import com.poppulo.lotterysystem.utils.Constants;

@RestController
@RequestMapping("/ticket")
public class TicketController {
	@Autowired
	private TicketService ticketService;


	@PostMapping
	public ResponseEntity<ResponseDTO> createTicket(@RequestBody Map<String, Integer> request) {
		// Input validation
		if (request == null || !request.containsKey(LINES) || request.get(LINES) <= 0) {
			throw new IllegalArgumentException(ILLEGAL_ARGUMENTS);
		}
		int lines = request.get(Constants.LINES);
		ResponseDTO response = ticketService.createTicket(lines);

		if (response.hasError()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<ResponseDTO> getAllTickets() {
		ResponseDTO response = ticketService.getAllTickets();
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseDTO> getTicket(@PathVariable Long id) {
		ResponseDTO response = ticketService.getTicket(id);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ResponseDTO> amendTicket(@PathVariable Long id, @RequestBody Map<String, Integer> request) {

		if (request == null || !request.containsKey(LINES) || request.get(LINES) <= 0) {
			throw new IllegalArgumentException(Constants.ILLEGAL_ARGUMENTS);
		}

		int lines = request.get(Constants.LINES);
		ResponseDTO response = ticketService.amendTicket(id, lines);

		if (response.hasError()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		return ResponseEntity.ok(response);
	}
}
