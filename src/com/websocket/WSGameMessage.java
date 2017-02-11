package com.websocket;


import java.util.List;

import com.card.Card;
import com.model.PlayerInSeat;

public class WSGameMessage {
	
	private String player;
	private final String messageType = "gamemessage";
	private PlayerInSeat[] players;
	private String currentPlayer;
	private int pot;
	private String currentMove;
	private int currentBet;
	private boolean inHand;
	private List<Card> cards;
	private Card[] playerCards = null;
	
	public void setPlayers(PlayerInSeat[] playerInSeats){
		this.players = playerInSeats;
	}
	
	public PlayerInSeat[] getPlayers(){
		return players;
	}
	
	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

	public String getMessageType(){
		return messageType;
	}

	public void setCurrentPlayer(String currentPlayer) {
		// TODO Auto-generated method stub
		this.currentPlayer = currentPlayer;
	}
	
	public String getCurrentPlayer(){
		return currentPlayer;
	}

	public int getPot() {
		return pot;
	}

	public void setPot(int pot) {
		this.pot = pot;
	}

	public void setCurrentMove(String currentMove) {
		// TODO Auto-generated method stub
		
		this.currentMove = currentMove;
	}
	
	public String getCurrentMove(){
		return currentMove;
	}

	public int getCurrentBet() {
		// TODO Auto-generated method stub
		return currentBet;
	}
	
	public void setCurrentBet(int bet){
		currentBet = bet;
	}

	public boolean isInHand() {
		return inHand;
	}

	public void setInHand(boolean inHand) {
		this.inHand = inHand;
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public Card[] getPlayerCards(){
		return playerCards;
	}

	public void setPlayerCards(Card[] playerCards){
		this.playerCards = playerCards;
	}

}
