package com.poppulo.lotterysystem.dto;

import java.util.ArrayList;
import java.util.List;

public class TicketDTO {
	
	private Long ticketId;
	private boolean checked;
	
	List<LinesDTO> lines = new ArrayList<LinesDTO>();

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public List<LinesDTO> getLines() {
		return lines;
	}

	public void addLines(LinesDTO line) {
		this.lines.add(line);
	}

	@Override
	public String toString() {
		return "TicketDTO [ticketId=" + ticketId + ", checked=" + checked + "]";
	}
	
	
}
