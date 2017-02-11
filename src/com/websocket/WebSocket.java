/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;

import com.card.Card;
import com.model.Game;
import com.model.Table;
import com.model.Player;
import com.model.PlayerInSeat;

@ServerEndpoint(value = "/secured/websocket/{tableName}", configurator  = GetHttpSessionConfigurator.class, encoders = {WSChatMessageEncoder.class, WSGameMessageEncoder.class, WSWinnerMessageEncoder.class}, decoders = {WSMessageTextDecoder.class})
public class WebSocket {
	
	Game game = new Game();
    
    private final Logger log = Logger.getLogger(getClass().getName());
    private Player player;
    private PlayerInSeat playerInSeat;
    private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());
    Table table = null;
    private Session session;
    HttpSession httpSession;
    String username;
    
    public String getUsername(){
    	return username;
    }
    
    public Session getSession(){
    	return session;
    }
    @OnOpen
    public void onOpen(Session session, @PathParam("tableName") String table, EndpointConfig config) throws IOException  {
        System.out.println("onOpen WEBSOCKET");
    	if(httpSession==null){
    		this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
    		this.username = (String) config.getUserProperties().get("username");
    		this.player = (Player) config.getUserProperties().get("player");
    	}
    	
        this.session = session;
        peers.add(session);
        this.table = game.getTable(table);
        if(this.table == null){
            // table selected does not exist
        }else{
            
            session.getUserProperties().put("table", this.table.getTableName());
            this.table.addViewer(this);
        }
        if(this.player == null){
        	System.out.println("player nnull");
        }
        
        
    }
 
    @OnClose
    public void onClose(Session peer) {
        peers.remove(peer);
        this.table.removeViewer(this);
        leaveTable();
    }
 
    @OnMessage
    public void onMessage(WSIncomingMessage message, Session session){
    	System.out.println("onmessage");
    	if(message instanceof WSIncomingChatMessage){
    		System.out.println("chatmessage");
    		sendChatMessage(message);
    	}else if(message instanceof WSIncomingJoinMessage){
    		joinTable(message);
    	}else if(message instanceof WSIncomingLeaveMessage){
    		leaveTable();
    	}else if(message instanceof WSIncomingPlayerMoveMessage){
    		playerMove(message);
    	}
        
    }
    
    private void playerMove(WSIncomingMessage message) {
		// TODO Auto-generated method stub
		
		String move = ((WSIncomingPlayerMoveMessage)message).getMove();
		if(move!=null){
			System.out.println(player.getPlayer() + " move in websocket: " + move);
			if(table.getCurrentPlayer().equals(playerInSeat.getUsername())){
				System.out.println("It is this players go : websocket");
				if(move.equals("fold")){
					playerInSeat.setFolded(true);
					return;
				}else if(move.equals("check")){
					playerInSeat.setMove(0, "check");
					return;
					
				}else if(move.equals("call")){
					
					playerInSeat.setMove(table.getCurrentBet(), "call");
					return;
				}else if(move.equals("raise")){
					int chips = ((WSIncomingPlayerMoveMessage)message).getChips();
					playerInSeat.setMove(chips, "raise");
					return;
				}
				
			}
		}
		
		
	}

	private void leaveTable() {
		// TODO Auto-generated method stub
    	System.out.println("leaving table websocket");
		this.table.removePlayer(player);
	}

	private void joinTable(WSIncomingMessage message) {
		// TODO Auto-generated method stub
    	System.out.println("joining table");
    	int seat = ((WSIncomingJoinMessage) message).getSeat();
    	int buyin = ((WSIncomingJoinMessage) message).getBuyin();
    	System.out.println(seat + " " + buyin);
    	if(buyin > this.table.getMinBuyin() && player.getNumberOfChips() >= buyin){
    		System.out.println("first if met");
    		if(this.table.checkSeat(seat)){
    			System.out.println("joining table second");
    			player.takeChips(buyin);
    			playerInSeat = new PlayerInSeat(player, buyin, seat, this);
        		if(table.join(playerInSeat, seat)){
        			System.out.println("table joined");
        		
    		    }else{
    		    	System.out.println("error joining table " + player.getPlayer());
    		    	player.addChips(buyin);
    		    	playerInSeat = null;
    		    }
    		}
    	} 
		 
	}

    
    private void sendChatMessage(WSIncomingMessage message){
    	System.out.println("sending message from serv");
    	System.out.println(this.username);
    	WSChatMessage chatMessage = new WSChatMessage();
    	chatMessage.setMessage(((WSIncomingChatMessage) message).getMessage());
    	chatMessage.setAuthor(this.username);
    	
        	 for(Session s : peers){
        		 if(s.isOpen()){
        			 try {
        				 s.getBasicRemote().sendObject(chatMessage);
        			 } catch (IOException e) {
        				 // TODO Auto-generated catch block
        				 e.printStackTrace();
        			 } catch (EncodeException e) { 
        				 // TODO Auto-generated catch block
        				 e.printStackTrace();
        			 }
        			 
        		 }
        		 
        	 }
        	 
         
    }
    
    
    
}
