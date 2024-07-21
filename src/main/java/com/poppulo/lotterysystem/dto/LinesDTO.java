package com.poppulo.lotterysystem.dto;

import com.poppulo.lotterysystem.entity.Line;

public class LinesDTO {
	
	private int number1;
    private int number2;
    private int number3;
    private int result;
    
    public LinesDTO(Line line) {
    	this.setNumber1(line.getNumber1());
    	this.setNumber2(line.getNumber2());
    	this.setNumber3(line.getNumber3());
    	this.setResult(line.getResult());
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
	@Override
	public String toString() {
		return "LinesDTO [number1=" + number1 + ", number2=" + number2 + ", number3=" + number3 + ", result=" + result
				+ "]";
	}
}
