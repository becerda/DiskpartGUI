package com.csuci.becerda.volume;

public class Volume {

	private int number;
	private char letter;
	private String label;
	private String fs;
	private String type;
	private int size;
	private char gk;
	private String status;
	private String info;

	public Volume(int number, char letter, String label, String fs, String type, int size, char gk, String status,
			String info) {
		this.number = number;
		this.letter = letter;
		this.label = label;
		this.fs = fs;
		this.type = type;
		this.size = size;
		this.gk = gk;
		this.status = status;
		this.info = info;
	}

	public Volume() {

	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public char getLetter() {
		return letter;
	}

	public String getLetterColon() {
		return getLetter() + ":";
	}

	public void setLetter(char letter) {
		this.letter = letter;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getFs() {
		return fs;
	}

	public void setFs(String fs) {
		this.fs = fs;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public char getGK() {
		return gk;
	}

	public void setGk(char gk) {
		this.gk = gk;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	public boolean isMounted(){
		return getLetter() != ' ';
	}

}
