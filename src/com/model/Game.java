package com.model;

import java.util.ArrayList;
import java.util.List;




public class Game {
	
	private static List<Table> tables = new ArrayList<Table>();
	private static List<Player> playersLoggedIn = new ArrayList<Player>();
	
	
	public Game(){
		
	}
	
	public Game(String setup){
		Table toAdd = new Table("low");
		tables.add(toAdd);

		
	}
        
    
	
	public void addPlayer(Player player){
		playersLoggedIn.add(player);
		
	}
	
	public void removePlayer(Player player){
		playersLoggedIn.remove(player);
		
	}

	public int getTotalPlayers() {
		// TODO Auto-generated method stub
		return playersLoggedIn.size();
	}

	public List<Table> getTables() {
		// TODO Auto-generated method stub
		return tables;
	}

	public Table getTable(String tableName) {
		// TODO Auto-generated method stub
		Table toReturn = null;
		for(int x = 0; x < tables.size(); x++){
			if(tables.get(x).getTableName().equals(tableName)){
				return tables.get(x);
				
			}
		}
		return toReturn;
	}
	

	public boolean userLoggedIn(long id) {
		// TODO Auto-generated method stub
		for(int x = 0; x < playersLoggedIn.size(); x++){
			if(playersLoggedIn.get(x).getUserId() == id){
				return true;
				
				
			}
			
		}
		return false;
	}

	public Player getPlayer(String name) {
		// TODO Auto-generated method stub
		Player toReturn = null;
		for(Player p : playersLoggedIn){
			if(p.getPlayer().equals(name)){
				toReturn = p;
				break;
			}
			
		}
		return toReturn;
	}
	
	

}
