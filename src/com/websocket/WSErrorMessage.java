package com.websocket;

public class WSErrorMessage {

	public final String messageType = "error";
	public String message;
	
	public void setMessage(String message){
		this.message = message;
	}
	
	public String getMessage(){
		return message;
	}
	
}
