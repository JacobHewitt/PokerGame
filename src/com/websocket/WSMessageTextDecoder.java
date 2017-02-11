/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.websocket;

import java.io.StringReader;
import java.util.Date;
 
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
 
public class WSMessageTextDecoder implements Decoder.Text<WSIncomingMessage> {
	@Override
	public void init(final EndpointConfig config) {
	}
 
	@Override
	public void destroy() {
	}
 
	@Override
	public WSIncomingMessage decode(final String textMessage) throws DecodeException {
		System.out.println(textMessage);
		JsonObject message = Json.createReader(new StringReader(textMessage)).readObject();
		if(message.getString("messageType").equals("chat")){
			WSIncomingMessage chatMessage = new WSIncomingChatMessage();
			chatMessage.setMessageType("chat");
			((WSIncomingChatMessage) chatMessage).setMessage(message.getString("message"));
			return chatMessage;
		}else if(message.getString("messageType").equals("join")){
			WSIncomingMessage joinMessage = new WSIncomingJoinMessage();
			joinMessage.setMessageType("join");
			int buyin = Integer.parseInt(message.getString("buyin"));
			int seat = Integer.parseInt(message.getString("seat"));
			System.out.println(buyin + " " + seat);
			((WSIncomingJoinMessage) joinMessage).setBuyin(buyin);
			((WSIncomingJoinMessage) joinMessage).setSeat(seat);
			return joinMessage;
		}else if(message.getString("messageType").equals("leave")){
			WSIncomingMessage leaveMessage = new WSIncomingLeaveMessage();
			leaveMessage.setMessageType("leave");
			return leaveMessage;
		}else if(message.getString("messageType").equals("playerMove")){
			WSIncomingMessage moveMessage = new WSIncomingPlayerMoveMessage();
			int chips;
			try{
				chips = Integer.parseInt(message.getString("chips"));
			}catch(Exception ex){
				System.out.println("problem converting chips to int");
				chips = 0;
			}
			
			((WSIncomingPlayerMoveMessage) moveMessage).setChips(chips);
			((WSIncomingPlayerMoveMessage) moveMessage).setMove(message.getString("move"));
			moveMessage.setMessageType("playerMove");
			return moveMessage;
		}
        
		return null;
	}
 
	@Override
	public boolean willDecode(final String s) {
		return true;
	}
}