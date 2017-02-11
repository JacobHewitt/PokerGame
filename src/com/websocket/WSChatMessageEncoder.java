/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.websocket;

import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

import com.model.PlayerInSeat;
 
public class WSChatMessageEncoder implements Encoder.Text<WSChatMessage> {
	@Override
	public void init(final EndpointConfig config) {
	}
 
	@Override
	public void destroy() {
	}
 
	@Override
	public String encode(WSChatMessage message) throws EncodeException {
		Gson gson = new Gson();
		return gson.toJson(message);
	}
	
	
}