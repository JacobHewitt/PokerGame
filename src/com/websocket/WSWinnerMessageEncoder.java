package com.websocket;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import com.card.Card;
import com.model.PlayerInSeat;

public class WSWinnerMessageEncoder implements Encoder.Text<WSWinnerMessage>{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(EndpointConfig arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String encode(WSWinnerMessage message) throws EncodeException {
		System.out.println("sending winner message - encoder ");
		// TODO Auto-generated method stub
		JsonObject json = new JsonObject();
		json.addProperty("messageType", message.getMessageType());
		json.addProperty("winner", message.getWinner());
		json.addProperty("hand", message.getHand());
		json.addProperty("amount", message.getAmount());
		Gson gson = new Gson();
		String toReturn = gson.toJson(json);
		System.out.println("sending message " + toReturn);
		return toReturn;
	}

}
