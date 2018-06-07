package com.baccredomatic;

import java.io.InputStream;

public class ValidationInput {
	private String inputId;
	private InputStream inputStream;
	
	public ValidationInput(InputStream pInputStream, String pInputID){
		this.setInputId(pInputID);
		this.setInputStream(pInputStream);
	}
	
	public String getInputId() {
		return inputId;
	}
	public void setInputId(String inputId) {
		this.inputId = inputId;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
}
