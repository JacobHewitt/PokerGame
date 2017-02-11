package com.model;

import com.card.RankingEnum;
import com.card.Card;
import com.card.Hand;
import java.io.Serializable;
import com.websocket.WebSocket;
import java.util.List;

public class PlayerInSeat{
	
	private WebSocket socket;
	int seat;
	int numberOfChips;
	Player player;
	boolean playersGo = false;
	String currentMove;
	int bet;
	boolean folded;
	private Hand hand;
	private RankingEnum rankingEnum = null;

	private List<Card> rankingList = null;

	private Card highCard = null;
	
	private boolean allIn;

	public Card getHighCard() {
		return highCard;
	}

	public void setHighCard(Card highCard) {
		this.highCard = highCard;
	}

	public RankingEnum getRankingEnum() {
		return rankingEnum;
	}

	public void setRankingEnum(RankingEnum rankingEnum) {
		this.rankingEnum = rankingEnum;
	}

	public List<Card> getRankingList() {
		return rankingList;
	}

	public void setRankingList(List<Card> rankingList) {
		this.rankingList = rankingList;
	}
	
	public PlayerInSeat(){
		
	}
	
	
	public PlayerInSeat(Player player, int chips, int seat, WebSocket socket){
		this.numberOfChips = chips;
		this.player = player;
		this.seat = seat;
		this.socket = socket;
	}
	
	public void setFolded(boolean input){
		folded = input;
		
	}
	
	public int getNumberOfChips(){
		return numberOfChips;
		
	}
	
	public String getUsername(){
		return player.getPlayer();
		
	}
	
	
	public void resetPlayersGo(){
		playersGo = false;
		
	}
	
	public void setCurrentMove(String move){
		currentMove = move;
		
	}
	
	public int getChips(int chips){
		numberOfChips -= chips;
		return chips;
		
	}
	
	public String move(){
		
		return currentMove;
		
	}

	public int getSeat() {
		// TODO Auto-generated method stub
		return seat;
	}
	
	public void setPlayersGo(boolean bool){
		
		playersGo = bool;
	}

	public int getMove(int bet) {
		// TODO Auto-generated method stub
		currentMove = null;
		if(currentMove.equals("check")){
			return 0;
		}else if(currentMove.equals("raise")){
			return bet;
		}else if(currentMove.equals("call")){
			return bet;
		}else if(currentMove.equals("fold")){
			folded = true;
		}
		return bet;
	}
	
	public String getMove(){
		return currentMove;
	}
	
	public void setMove(int chips, String move){
		System.out.println("PlayerInSeat chips: "+chips+" move: "+move);
		if(move.equals("fold")){
			folded = true;
			return;
		}else if(move.equals("check")){
			System.out.println(player.getPlayer() +" checked : PlayerInSeat"); 
			currentMove = "check";
		}else if(move.equals("call")){
			System.out.println(player.getPlayer() +" called : PlayerInSeat"); 
			if(numberOfChips >= chips){
				System.out.println(player.getPlayer() +" has enough chips : PlayerInSeat"); 
				currentMove = "call";
				if(bet!=0){
					numberOfChips += bet;
				}
				bet = chips;
			}
		}else if(move.equals("raise")){
			System.out.println(player.getPlayer() +" raised : PlayerInSeat: numberOfChips: "+numberOfChips+" raise: "+chips); 
			if(this.numberOfChips > chips || this.numberOfChips == chips){
				System.out.println(player.getPlayer() +" has enough chips : PlayerInSeat"); 
				currentMove = "raise";
				bet = chips;
			}
		}
		
	}
	
	public void newHand(){
		this.hand = null;
		currentMove = null;
		bet = 0;
		folded = false;
		RankingEnum rankingEnum = null;

		List<Card> rankingList = null;

		Card highCard = null;
		playersGo = false;
		allIn = false;
	}
	
	public void newRound(){
		currentMove = null;
		bet = 0;
		folded = false;
	}

	public boolean isPlayersGo() {
		// TODO Auto-generated method stub
		return playersGo;
	}

	public boolean getFolded() {
		// TODO Auto-generated method stub
		return folded;
	}

	public int getBet() {
		// TODO Auto-generated method stub
		return bet;
	}

	public void addChips(int currentPot) {
		// TODO Auto-generated method stub
		System.out.println("ADDING CHIPS - PLAYER IN SEAT : " + currentPot);
		numberOfChips+=currentPot;
	}

	public void setHand(Card card1, Card card2){
		this.hand = new Hand(card1, card2);
	}
	
	public Hand getHand(){
		return hand;
	}
	
	public void resetMove(){
		currentMove = null;
		bet = 0;
		
	}
	
	public WebSocket getSocket(){
		return socket;
	}
	
	public void resetCurrentMove(){
		currentMove = null;
	}
	
	public void setAllIn(boolean allIn){
		this.allIn = allIn;
	}
	
	public boolean getAllIn(){
		return allIn;
	}

}
