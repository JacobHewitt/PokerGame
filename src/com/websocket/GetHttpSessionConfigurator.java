package com.websocket;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

import com.model.Game;
import com.model.Player;

public class GetHttpSessionConfigurator extends ServerEndpointConfig.Configurator
{
	
	
	
    @Override
    public void modifyHandshake(ServerEndpointConfig config, 
                                HandshakeRequest request, 
                                HandshakeResponse response)
    {
    	Game game = new Game();
        HttpSession httpSession = (HttpSession)request.getHttpSession();
        Player player = (Player) httpSession.getAttribute("player");
        
        config.getUserProperties().put("username", player.getPlayer());
        
        if(player == null){
        	System.out.println("no player in config");
        	
        }else{
        	config.getUserProperties().put("player", player);
        }
       
        config.getUserProperties().put(HttpSession.class.getName(),httpSession);
    }
}