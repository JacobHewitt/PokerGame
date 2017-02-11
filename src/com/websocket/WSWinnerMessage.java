package com.websocket;

public class WSWinnerMessage {
	
	private final String messageType = "winnermessage";
	private String amount;
	private String winner;
	private String hand;
	
	public String getWinner() {
		return winner;
	}
	public void setWinner(String winner) {
		this.winner = winner;
	}
	public String getHand() {
		return hand;
	}
	public void setHand(String hand) {
		this.hand = hand;
	}
	public String getMessageType() {
		return messageType;
	}
	public String getAmount() {
		// TODO Auto-generated method stub
		return amount;
	}
	public void setAmount(String amount) {
		// TODO Auto-generated method stub
		this.amount = amount;
	}
	
	
	
	

}
