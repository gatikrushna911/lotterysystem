package com.poppulo.lotterysystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Line {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int number1;
    private int number2;
    private int number3;
    private int result;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;
    
    public Line() {}

	public Line(int number1, int number2, int number3) {
		this.number1 = number1;
		this.number2 = number2;
		this.number3 = number3;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getNumber1() {
		return number1;
	}

	public void setNumber1(int number1) {
		this.number1 = number1;
	}

	public int getNumber2() {
		return number2;
	}

	public void setNumber2(int number2) {
		this.number2 = number2;
	}

	public int getNumber3() {
		return number3;
	}

	public void setNumber3(int number3) {
		this.number3 = number3;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	@Override
	public String toString() {
		return "Line [id=" + id + ", number1=" + number1 + ", number2=" + number2 + ", number3=" + number3 + ", result="
				+ result + ", ticket=" + ticket + "]";
	}
    
}
