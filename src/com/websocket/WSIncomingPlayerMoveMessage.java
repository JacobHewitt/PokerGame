package com.websocket;

public class WSIncomingPlayerMoveMessage extends WSIncomingMessage{
	
	private int chips;
	private String move;

	public int getChips() {
		return chips;
	}

	public void setChips(int chips) {
		this.chips = chips;
	}

	public String getMove() {
		return move;
	}

	public void setMove(String move) {
		this.move = move;
	}

}
