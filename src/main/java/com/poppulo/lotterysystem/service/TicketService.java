package com.poppulo.lotterysystem.service;

import static com.poppulo.lotterysystem.utils.Constants.*;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poppulo.lotterysystem.dto.ResponseDTO;
import com.poppulo.lotterysystem.entity.Line;
import com.poppulo.lotterysystem.entity.Ticket;
import com.poppulo.lotterysystem.exceptions.TicketCheckedException;
import com.poppulo.lotterysystem.exceptions.TicketCreationException;
import com.poppulo.lotterysystem.exceptions.TicketNotFoundException;
import com.poppulo.lotterysystem.repository.TicketRepository;
import com.poppulo.lotterysystem.utils.TicketMapper;

@Service
public class TicketService {
	@Autowired
	private TicketRepository ticketRepository;

	public ResponseDTO createTicket(int linesCount) {
		try {
			Ticket ticket = new Ticket();
			List<Line> lines = generateRandomLines(linesCount);
			ticket.addLines(lines);

			ticketRepository.save(ticket);

			return TicketMapper.toResponseDTO(ticket);
		} catch (Exception ex) {
			throw new TicketCreationException(TICKET_CREATION_FAILED, ex);
		}
	}

	public ResponseDTO getTicket(Long id) {
		Ticket ticket = findTicketById(id);
		return TicketMapper.toResponseDTO(ticket);
	}

	public ResponseDTO getAllTickets() {
		List<Ticket> tickets = ticketRepository.findAll();
		return TicketMapper.toResponseDTO(tickets);
	}

	public ResponseDTO amendTicket(Long id, int linesCount) {
		Ticket ticket = findTicketById(id);
		if (ticket.isChecked()) {
			throw new TicketCheckedException(id);
		}
		List<Line> lines = generateRandomLines(linesCount);
		ticket.addLines(lines);
		ticketRepository.save(ticket);
		return TicketMapper.toResponseDTO(ticket);
	}

	public ResponseDTO checkTicketStatus(Long id) {
		Ticket ticket = findTicketById(id);
		if (ticket.isChecked()) {
			throw new TicketCheckedException(id);
		}
		ticket.getLines().forEach(line -> line.setResult(calculateResult(line)));
		ticket.setChecked(true);
		ticketRepository.save(ticket);
		return TicketMapper.toResponseDTO(ticket);
	}


	private List<Line> generateRandomLines(int count) {
		return IntStream.range(0, count).mapToObj(i -> generateRandomLine()).collect(Collectors.toList());
	}

	private Line generateRandomLine() {
		Random random = new Random();
		return new Line(random.nextInt(3), random.nextInt(3), random.nextInt(3));
	}

	private int calculateResult(Line line) {
		int sum = line.getNumber1() + line.getNumber2() + line.getNumber3();
		if (sum == 2) {
			return 10;
		} else if (line.getNumber1() == line.getNumber2() && line.getNumber2() == line.getNumber3()) {
			return 5;
		} else if (line.getNumber1() != line.getNumber2() && line.getNumber1() != line.getNumber3()) {
			return 1;
		} else {
			return 0;
		}
	}

	private Ticket findTicketById(Long id) {
		return ticketRepository.findById(id).orElseThrow(() -> new TicketNotFoundException(id));
	}
}
