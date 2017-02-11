/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author jake
 */
public class Database {
    
    private Connection conn;
    private DataSource ds;
    
    private Connection getConnection(){
        try {
            ds = (DataSource) new InitialContext().lookup("jdbc/pokerSite");
            conn = ds.getConnection();
            return conn;
        } catch (NamingException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Player playerLogin(String id){
        Player toReturn = null;
        getConnection();
        
        if(conn != null){
            try {
                PreparedStatement query = conn.prepareStatement("SELECT * FROM users WHERE users.userid = ?");
                query.setString(1, id);
                ResultSet results = query.executeQuery();
                if(results.next()){
                    toReturn  = new Player(results.getString("username"), results.getInt("chips"), results.getLong("userId"), results.getString("email"));
                }
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        
        return toReturn;
    }
    
    public void savePlayer(Player toSave){
        getConnection();
        
        if(conn != null){
            try {
                PreparedStatement query = conn.prepareStatement("UPDATE users SET username = ?, email = ?, chips = ? WHERE users.userId = ?");
                query.setString(1, toSave.getPlayer());
                query.setString(2, toSave.getEmail());
                query.setInt(3, toSave.getNumberOfChips());
                query.setLong(4, toSave.getUserId());
                query.executeUpdate();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
    }
    
    public Player createNewPlayer(String firstName, long userId, String email){
        Player player = null;
        try {
            player = new Player(firstName, 10000, userId, email);
            getConnection();
            
            PreparedStatement query = conn.prepareStatement("INSERT INTO users VALUES (?, ? , ? , ?)");
            query.setString(1, firstName);
            query.setString(2, email);
            query.setInt(3, player.getNumberOfChips());
            query.setLong(4, userId);
            query.executeUpdate();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return player;
    }
}
