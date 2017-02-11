package com.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable{
	
        private long userId;
	private String user_name;
	private int numberOfChips;
        private String email;
	
	public Player(String name, int numberOfChips, long userId, String email){
		user_name = name;
		this.numberOfChips = numberOfChips;
                this.email = email;
                this.userId = userId;
	}
	
	public String getPlayer(){
		return user_name;
		
	}
	
	public int getNumberOfChips(){
		return numberOfChips;
		
	}
	
	public void takeChips(int number){
		
		numberOfChips = numberOfChips - number;
	}
	
	public void returnChips(int number){
		numberOfChips += number;
		
	}

	public void addChips(int chips) {
		// TODO Auto-generated method stub
		this.numberOfChips += chips;
	}
        
        public long getUserId(){
            return userId;
        }
           
        public String getEmail(){
            return email;
        }
}
