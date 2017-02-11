/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.websocket;

/**
 *
 * @author jake
 */
public class WSChatMessage {
    
    public final String messageType = "chat";
    String author;
    String message;
    
    public WSChatMessage(){
    	
    }
    
    public String getMessageType(){
    	return messageType;
    }
    
    public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
    
    
    
    
	
}
