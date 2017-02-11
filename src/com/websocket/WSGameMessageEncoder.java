package com.websocket;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import com.card.Card;
import com.model.PlayerInSeat;

 
public class WSGameMessageEncoder implements Encoder.Text<WSGameMessage> {
	@Override
	public void init(final EndpointConfig config) {
	}
 
	@Override
	public void destroy() {
	}
 
	
	@Override
	public String encode(WSGameMessage message) throws EncodeException{
		JsonObject json = new JsonObject();
		json.addProperty("messageType", message.getMessageType());
		json.addProperty("player", message.getPlayer());
		json.addProperty("currentPlayer", message.getCurrentPlayer());
		json.addProperty("pot", message.getPot());
		json.addProperty("currentBet", message.getCurrentBet());
		json.addProperty("inHand", message.isInHand());
		json.addProperty("currentMove", message.getCurrentMove());
		if(message.getPlayerCards() != null){
			Card[] playerCards = message.getPlayerCards();
			
			if(playerCards[0] != null && playerCards[1] != null){
				String card1 = playerCards[0].toString();
				String card2 = playerCards[1].toString();
				json.addProperty("card1", card1);
				json.addProperty("card2", card2);
			}
			
		}
		JsonArray players = new JsonArray();
		for(PlayerInSeat player : message.getPlayers()){
			if(player!=null){
				JsonObject p = new JsonObject();
				p.addProperty("name", player.getUsername());
				p.addProperty("chips", player.getNumberOfChips());
				p.addProperty("seat", player.getSeat());
				p.addProperty("folded", player.getFolded());
				players.add(p);
			}
		}
		json.add("players", players);
		JsonArray cards = new JsonArray();
		for(Card card : message.getCards()){
			if(card!=null){
				JsonObject c = new JsonObject();
				c.addProperty("card", card.toString());
				cards.add(c);
			}
			
		}
		json.add("cards", cards);
		Gson gson = new Gson();
		String toReturn = gson.toJson(json);
		return toReturn;
	}
}