package com.model;

public class TableState {

	private int currentPot;
	private PlayerInSeat currentPlayer;
	private String moveType;
	private int bet;
	
	public TableState(int currentPot, PlayerInSeat player, String move, int bet){
		this.currentPot = currentPot;
		this.currentPlayer = player;
		this.moveType = move;
		this.bet = bet;
		
	}
	
	public int getPot(){
		return currentPot;
		
	}
	
	public PlayerInSeat getPlayerInSeat(){
		return currentPlayer;
		
	}
	
	public String getMove(){
		
		return moveType;
	}
	
	public int getBet(){
		return bet;
		
	}
}
