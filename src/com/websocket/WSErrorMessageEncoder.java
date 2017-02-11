package com.websocket;

import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

import com.model.PlayerInSeat;
 
public class WSErrorMessageEncoder implements Encoder.Text<WSErrorMessage> {
	@Override
	public void init(final EndpointConfig config) {
	}
 
	@Override
	public void destroy() {
	}
 
	@Override
	public String encode(WSErrorMessage message) throws EncodeException {
		Gson gson = new Gson();
		return gson.toJson(message);
	}


	
	
}