/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import com.model.Database;
import com.model.Game;
import com.model.Player;

/**
 *
 * @author jake
 */
public class SessionListener implements HttpSessionListener{
    
    Game game = new Game();
    
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("SESSION CREATED");
        System.out.println("SESSION CREATED");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("session being destroyed - saving data");
        HttpSession sesh = (HttpSession) se.getSession();
        if(sesh.getAttribute("player") != null){
            Player toSave = (Player) sesh.getAttribute("player");
            Database db = new Database();
            db.savePlayer(toSave);
            game.removePlayer(toSave);
        }
        
        
    }
    
}
