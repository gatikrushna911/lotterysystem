package com.poppulo.lotterysystem.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private boolean checked;

	@OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Line> lines = new ArrayList<>();

	// Getters and setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public void addLine(Line line) {
		lines.add(line);
		line.setTicket(this);
	}

	public void addLines(List<Line> newLines) {
		newLines.forEach(this::addLine);
	}

	public List<Line> getLines() {
		return lines;
	}

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", checked=" + checked + ", lines=" + lines + "]";
	}
}
