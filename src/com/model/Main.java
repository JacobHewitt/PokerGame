package com.model;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class Main {
	
	@PostConstruct
	private void setup(){
		Game game = new Game("setup");
	}

}
