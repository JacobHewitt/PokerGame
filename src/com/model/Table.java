package com.model;

import com.card.Board;
import com.card.Deck;
import com.card.Card;
import com.handEval.TwoPlusTwoHandEvaluator;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.websocket.EncodeException;
import javax.websocket.Session;

import com.websocket.WSGameMessage;
import com.websocket.WSWinnerMessage;
import com.websocket.WebSocket;

public class Table implements Serializable{
	
    private static ArrayList<WebSocket> viewers = new ArrayList<WebSocket>();


    private String tableName;
    private PlayerInSeat[] players;
    private List<PlayerInSeat> playersInHand; // circular linked list
    private int maxPlayers = 9;
    private Deck deck;
    private PlayerInSeat currentPlayer;

    private String currentMove;
    private int currentPot;
    private int currentBet;
    private int timer;
    private boolean inHand;
    private int roundOfBetting;
    
    private List<Card> cards = new ArrayList<Card>();
	
    public Table(){
		
		
	}
	
    public void addViewer(WebSocket viewer){
    	System.out.println("adding viewer");
    	viewers.add(viewer);
    }
    
    public void removeViewer(WebSocket viewer){
    	this.viewers.remove(viewer);
    }
	
    public Table(String tableName){

            this.tableName = tableName;
            players = new PlayerInSeat[maxPlayers];
            inHand = false;
            deck = new Deck();
            sendTableState();
            start();

    }

    private int getPlayersInSeat(){
            int toReturn = 0;
            for(PlayerInSeat player : players){
                    if(player!=null) toReturn++;
            }
            return toReturn;
    }

    public void start(){
            currentPot = 0;
            Thread thread = new Thread(new Runnable(){

                    @Override
                    public void run() {
                            // TODO Auto-generated method stub
                            while(true){
                                    if(!inHand){
                                            if(getPlayersInSeat() >= 2){
                                                    resetTableNewHand();
                                                    playersInHand = new LinkedList<>();
                                                    for(PlayerInSeat p : players){
                                                            if(p != null){
                                                                    p.newHand();
                                                                    playersInHand.add(p);
                                                                    currentPot += p.getChips(5);
                                                            }

                                                    }
                                                    System.out.println("starting hand");
                                                    inHand = true;
                                                    startHand();

                                            }
                                    }


                                    sleep();

                            }
                    }

            });
    thread.start();

    }

    private void resetTableNewHand() {
            // TODO Auto-generated method stub
            deck = new Deck();
            cards.clear();
            currentMove="check";
            currentPot = 0;
            currentBet = 0;
            inHand = false;
            roundOfBetting = 0;

    }


    private void sleep(){
            try
    { 

       Thread.sleep(3000); 
    } 
    catch (InterruptedException  interruptedException) 
    { 
      System.out.println(  "First Thread is interrupted when it is  sleeping" +interruptedException); 
    } 

    }

    private void startHand() {
            // TODO Auto-generated method stub
            sleep();
            sleep();
            for(PlayerInSeat p : playersInHand){
                    System.out.println("setting cards for users : table");
                    Card card1 = deck.dealCard();
                    Card card2 = deck.dealCard();
                    p.setHand(card1, card2);
                    

            }

            this.inHand = true;
            if(checkNumPlayersInHand()){
                    findWinner();
            }else{
                    roundOfBetting();
                    flop();
            }


    }

    private void flop(){
            for(int x = 0; x < 3; x++){
                    cards.add(deck.dealCard());
            }

            if(checkNumPlayersInHand()){
                    findWinner();
            }else{
                    roundOfBetting();
                    turn();
            }


    }

    private void turn() {
            // TODO Auto-generated method stub
            cards.add(deck.dealCard());
            if(checkNumPlayersInHand()){
                    findWinner();
            }else{
                    roundOfBetting();
                    river();
            }

    }

    private void river(){
            cards.add(deck.dealCard());
            if(checkNumPlayersInHand()){
                    findWinner();
            }else{
                    roundOfBetting();
                    findWinner();
                    sleep();
            }

    }

    private void findWinner() {
        // TODO Auto-generated method stub
        PlayerInSeat winner = null;
        Board board = new Board(cards.get(0), cards.get(1), cards.get(2), cards.get(3), cards.get(4));
        TwoPlusTwoHandEvaluator eval = TwoPlusTwoHandEvaluator.getInstance();
        for(PlayerInSeat p : playersInHand){
            
            
            
            
            
            
            
//                RankingUtil.checkRanking(p, cards);
//                boolean sameHand = false;
//                if(winner == null){
//                        winner = p;
//                }else{
//                        int winnerInt = RankingUtil.getRankingToInt(winner);
//                        int compareAgainst = RankingUtil.getRankingToInt(p);
//                        if(winnerInt>compareAgainst){
//                                winner = p;
//                        }
//
//                        if(winnerInt == compareAgainst){
//                                sameHand = true;
//                        }
//
//                }

        }

        if(winner != null){
                winner.addChips(currentPot);
        }

        sendWinner(winner.getUsername(), String.valueOf(winner.getRankingEnum()), currentPot);

        inHand = false;


    }

    private boolean checkNumPlayersInHand(){
            if(playersInHand.size() <= 1){
                    return true;
            }else{
                    return false;
            }
    }

    private void roundOfBetting(){
            if(playersInHand.size() <= 1){
                    findWinner();
            }
            roundOfBetting++;
            String toPrint = "";
            for(int x = 0; x < 40; x++){
                    toPrint += roundOfBetting;
            }
            System.out.println(toPrint);
            currentMove = "check";
            currentBet = 0;
            PlayerInSeat raiser = null;
            boolean playersGos = true;
            resetPlayersMoves();
            while(playersGos == true){

                    System.out.println("ROUND OF BETTING new while loop");
                    for(PlayerInSeat p : playersInHand){
                            System.out.println(p.getUsername()+" in hand");

                            if(p == raiser){
                                    System.out.println("CONTINUE - original raiser" + p.getUsername());
                                    continue;
                            }

                            if(p.getBet() == currentBet && currentBet != 0){
                                    System.out.println("CONTINUE - player visited previously " + currentPlayer.getUsername());
                                    continue;
                            }

                            this.currentPlayer = p;
                            timer = 0;
                            p.resetCurrentMove();
                            p.setPlayersGo(true);
                            System.out.println(p.getUsername()+ " players go");
                            while(timer < 50 && p.getFolded() == false){
                                    if(p.getMove() != null) break;
                                    sleep();
                                    timer++;

                            }
                            System.out.println(p.getUsername()+ " players go ended");
                            p.setPlayersGo(false);

                            if(p.getMove() != null){
                                    System.out.println(p.getUsername() + " move not null");
                                    String playersMove = p.getMove();
                                    if(playersMove.equals("check")){
                                            if(currentBet == 0 && currentMove.equals("check")){
                                                    System.out.println(p.getUsername() + " checked on table");
                                            }
                                    }else if(playersMove.equals("call")){
                                            System.out.println(p.getUsername() + " called on table");

                                            if(currentMove.equals("raise")){
                                                    System.out.println(p.getUsername() + " called a raise on table");
                                                    if(p.getNumberOfChips() >= currentBet) currentPot += p.getChips(currentBet);
                                                    currentMove = "call";
                                            }else if(currentMove.equals("call")){
                                                    System.out.println(p.getUsername() + " called a call on table");
                                                    if(p.getNumberOfChips() >= currentBet) currentPot+= p.getChips(currentBet);

                                            }


                                    }else if(playersMove.equals("raise")){
                                            System.out.println(p.getUsername() + " made a raise on table");
                                            if(p.getNumberOfChips() >= p.getBet()){
                                                    System.out.println(p.getUsername() + " has enough chips : table");
                                                    currentBet = p.getChips(p.getBet());
                                                    currentPot += currentBet;
                                                    currentMove = "raise";
                                                    raiser = p;
                                            }
                                    }


                            }else if(p.getFolded() == true){
                                    System.out.println(p.getUsername() + " player folded : table");
                                    playersInHand.remove(p);
                            }else{
                                    System.out.println(p.getUsername() + " player folded automatically : table");
                                    p.setFolded(true);
                                    playersInHand.remove(p);
                            }

                            System.out.println(currentMove);


                    }


                    boolean toCheck = true;
                    if(currentMove.equals("raise") || currentMove.equals("call")){
                            for(PlayerInSeat playerToCheck : playersInHand){
                                    if(playerToCheck.getBet() != currentBet){
                                            toCheck = false;
                                    }
                            }

                            if(toCheck == true){
                                    playersGos = false;
                                    currentMove = "check";
                            }
                    }

                    if(currentMove.equals("check")){
                            playersGos = false;
                            currentMove = "check";
                    }


            }


    }

    private void resetPlayersMoves() {
            // TODO Auto-generated method stub
            for(PlayerInSeat player : playersInHand){
                    player.resetMove();
            }
    }


    private void resetPlayersNewRound() {
            // TODO Auto-generated method stub
            for(PlayerInSeat p : players){
                    if(p!=null){
                            p.newRound();
                    }

            }
    }


    public int getNumberOfPlayers(){
            int toReturn = 0;
            for(PlayerInSeat p : players){
                    if(p!=null){
                            toReturn++;
                    }
            }
            return toReturn;
    }

    public String getTableName(){
            return tableName;

    }

    public PlayerInSeat[] getPlayers(){
            return players;

    }



    public boolean isFull(){

            if(players.length >= maxPlayers){
                    return true;

            }

            return false;
    }



    public boolean checkSeat(int seat) {
            // TODO Auto-generated method stub
            if(seat < maxPlayers){
                    if(players[seat] == null){
                            return true;
                    }else{
                            return false;
                    }
            }else{
                    return false;
            }

    }


    public boolean join(PlayerInSeat player, int seat) {
            // TODO Auto-generated method stub
            System.out.println(player.getUsername() + " " + seat);

            if(checkSeat(seat) && tableContainsPlayer(player.getUsername()) == false){
                    System.out.println("player can be added");
                    players[seat] = player;
                    return true;
            }else{
                    System.out.println("cant be added");
                    return false;
            }
    }


    private boolean tableContainsPlayer(String username) {
            // TODO Auto-generated method stub
            for(PlayerInSeat player : players){
                    if(player!=null){
                            if(player.getUsername().equals(username)){
                                    return true;
                            }
                    }

            }
            return false;
    }


    public int getMinBuyin() {
            // TODO Auto-generated method stub
            return 10;
    }


    public void removePlayer(Player player) {
            // TODO Auto-generated method stub

            System.out.println("table remove player "+player.getPlayer());
            for(int i = 0; i < players.length; i++){
                    if(players[i] != null){
                            if(players[i].getUsername().equals(player.getPlayer())){
                                    System.out.println("removing player from table ");
                                    if(playersInHand.contains(players[i])){
                                            players[i].setFolded(true);
                                            playersInHand.remove(players[i]);
                                            player.addChips(players[i].getNumberOfChips());
                                            players[i] = null;
                                    }

                            }

                    }

            }
    }

    public String getCurrentPlayer() {
            // TODO Auto-generated method stub
            if(currentPlayer!=null){
                    return currentPlayer.getUsername();
            }
            return null;
    }

    public int getCurrentPot() {
            // TODO Auto-generated method stub
            return currentPot;
    }

    public String getCurrentMove() {
            // TODO Auto-generated method stub
            return currentMove;
    }

    public int getCurrentBet() {
            // TODO Auto-generated method stub
            return currentBet;
    }


    public boolean isInHand() {
            return inHand;
    }


    public List<Card> getCards() {
            // TODO Auto-generated method stub
            return cards;
    }

    public void sendTableState(){
            Thread thread = new Thread(new Runnable(){

                    @Override
                    public void run() {
                            System.out.println("running thread - send table state");
                            while(true){
                                    // TODO Auto-generated method stub
                                    for(WebSocket viewer : (ArrayList<WebSocket>) viewers.clone()){
                                            Session session = viewer.getSession();
                                            if(viewer.getSession().isOpen()){
                                                    WSGameMessage gameMessage = new WSGameMessage();
                                            gameMessage.setPlayers(players);
                                            if(currentPlayer!=null){
                                                    gameMessage.setCurrentPlayer(currentPlayer.getUsername());
                                            }
                                            gameMessage.setPlayer(viewer.getUsername());
                                            gameMessage.setPot(currentPot);
                                            gameMessage.setCurrentMove(currentMove);
                                            gameMessage.setCurrentBet(currentBet);
                                            gameMessage.setInHand(inHand);
                                            gameMessage.setCards(cards);
                                            if(playersInHand != null){
                                                    for(PlayerInSeat p : playersInHand){
                                                            if(p.getUsername() == viewer.getUsername()){
                                                                    if(p.getCards().length != 0){
                                                                            Card[] playerCards;
                                                                            playerCards = p.getCards();
                                                                            gameMessage.setPlayerCards(playerCards);
                                                                    }

                                                            }
                                                    }
                                            }

                                            try {

                                                            session.getBasicRemote().sendObject(gameMessage);
                                                            Thread.sleep(1000);
                                                    } catch (IOException | EncodeException e1) {
                                                            // TODO Auto-generated catch block
                                                            e1.printStackTrace();
                                                    } catch (InterruptedException e) {
                                                            // TODO Auto-generated catch block
                                                            e.printStackTrace();
                                                    }
                                            }
                                    }

                            }


                    }

                    });

        thread.start();

    }

    private void sendWinner(String winner, String hand, int amount){
            System.out.println("sending winner from table ------------------------------");
            WSWinnerMessage message = new WSWinnerMessage();
    message.setWinner(winner);
    message.setHand(hand);
    message.setAmount(String.valueOf(amount));
            for(WebSocket viewer : (ArrayList<WebSocket>) viewers.clone()){
                    Session session = viewer.getSession();
                     if(session.isOpen()){
                             try {
                                     session.getBasicRemote().sendObject(message);
                             } catch (IOException e) {
                                     // TODO Auto-generated catch block
                                     e.printStackTrace();
                             } catch (EncodeException e) { 
                                     // TODO Auto-generated catch block
                                     e.printStackTrace();
                             }

                     }else{
                             System.out.println("session not open -----------------------");
                     }

        }
            System.out.println("message sent winner ------------- " + winner);
    }


}
