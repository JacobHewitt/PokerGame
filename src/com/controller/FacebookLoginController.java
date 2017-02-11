package com.controller;

import java.io.IOException;
import java.util.Map;

import com.model.FBConnection;
import com.model.FBGraph;
import com.model.Database;
import com.model.Player;
import com.model.Game;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class FacebookLoginController extends HttpServlet{
	
	private String code="";
	private FacebookClient facebookClient;
        private static Database db = new Database();
        private Game game = new Game();
	
	protected void processRequest(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
                if(req.getSession().getAttribute("player") != null){
                    System.out.println("user already logged in");
                    req.getRequestDispatcher("/WEB-INF/jsp/tables.jsp").forward(req, res);
                    return;
                }
		code = req.getParameter("code");
		if (code == null || code.equals("")) {
			throw new RuntimeException(
					"ERROR: Didn't get code parameter in callback.");
		}
		FBConnection fbConnection = new FBConnection();
		facebookClient = new DefaultFacebookClient(fbConnection.getAccessToken(code), Version.VERSION_2_6);
		User me = facebookClient.fetchObject("me", User.class, Parameter.with("fields", "first_name,last_name,email,id"));
                Player p = db.playerLogin(me.getId());
                if(p != null){
                    if(game.userLoggedIn(Long.parseLong(me.getId()))){
                        System.out.println("user already logged in");
                    }else{
                        System.out.println("logging in user");
                        game.addPlayer(p);
                        req.getSession().setAttribute("loggedIn", true);
                        req.getSession().setAttribute("player", p);
                    }
                    
                    System.out.println("game " + p.getPlayer());
                    req.setAttribute("tables", game.getTables());
                    req.setAttribute("username", p.getPlayer());

                    
                }else{
                    System.out.println("creating new player and saving to db");
                    Player newPlayer = db.createNewPlayer(me.getFirstName(), Long.parseLong(me.getId()), me.getEmail());
                    req.getSession().setAttribute("player", newPlayer);
                    
                }
                
                
                
                req.getRequestDispatcher("/WEB-INF/jsp/tables.jsp").forward(req, res);
		
	}
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
	
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
	
	
}
