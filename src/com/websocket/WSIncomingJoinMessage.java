package com.websocket;

public class WSIncomingJoinMessage extends WSIncomingMessage{
	
	private int buyin;
	private int seat;

	public int getBuyin() {
		return buyin;
	}

	public void setBuyin(int buyin) {
		this.buyin = buyin;
	}

	public int getSeat() {
		return seat;
	}

	public void setSeat(int seat) {
		this.seat = seat;
	}

}
