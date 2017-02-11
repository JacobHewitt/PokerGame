var ws;
var maxseats = 6;
var thisPlayersName;
var inHand = false;
var inputBoxActive = false;
var currentPot = 0;
var tableCards = new Array(5);
var playerCards = false;
var currentMove;
var thisPlayersSeat;
var thisPlayerInSeat = false;


function init(tableName)
{
    if ("WebSocket" in window)
    {
        
       // Let us open a web socket
       ws = new WebSocket("ws://localhost:8080/jakehewitt/secured/websocket/"+tableName);
       
       ws.onopen = function()
        {
          // Web Socket is connected, send data using send()
          alert("connected");
        };
        
        ws.onmessage = function (evt) 
        { 
        	document.getElementById("gametick").innerHTML=evt.data;
          var msg = JSON.parse(evt.data); // native API
          if(msg.messageType === "chat"){
              displayMessage(evt);
          }else if(msg.messageType == "gamemessage"){
        	  gametick(evt);
          }else if(msg.messageType == "winnermessage"){
        	  displayWinnerMessage(evt);
          }
          
          
        };

        ws.onclose = function()
        { 
          // websocket is closed.
        	alert("closed");
          
        };

    }else{
        
       // The browser doesn't support WebSocket
       alert("WebSocket NOT supported by your Browser!");
    }
};

function displayWinnerMessage(evt){
	tableCards = new Array(5);
	playerCards = false;
	resetTableCards();
	resetPlayerCards();
	var msg = JSON.parse(evt.data);
	
	var winner = msg.winner;
	var hand = msg.hand;
	var amount = msg.amount;
	document.getElementById("winner").innerHTML = "last winner: " + winner + " hand "+hand+" amount: "+amount;
	
};

function displayPictureCardTable(src, whereToAdd){
	var img = document.createElement("img");
    img.src = "http://localhost:8080/jakehewitt/cardImages/"+ src +".png";
    img.width = 100;
    img.height = 100;
    whereToAdd.innerHTML = "";
    whereToAdd.appendChild(img);
};

function displayPlayerCards(card1, card2){
	
	var img1 = document.createElement("img");
	img1.src = "http://localhost:8080/jakehewitt/cardImages/"+ card1 +".png";
	img1.className = "userCards";
	var toAddto = document.getElementById("playerCard0");
	toAddto.innerHTML = "";
	toAddto.appendChild(img1);
	var toAddto2 = document.getElementById("playerCard1");
	toAddto2.innerHTML = "";
	var img2 = document.createElement("img");
	img2.src = "http://localhost:8080/jakehewitt/cardImages/"+ card2 +".png";
	img2.className = "userCards";
	toAddto2.appendChild(img2);
};

function displayMessage(evt){
	
	var msg = JSON.parse(evt.data); // native API
	
    var comment = msg.message;
    var author = msg.author;
    var messageType = msg.messageType;
    
        var chat = document.getElementById("output");
        var div = "<div>author: " + author + "</br>"
              + comment + "</br></div>";
      
        chat.innerHTML += div;
        

};

function gametick(evt){
	var msg = JSON.parse(evt.data); // native API
	this.inHand = msg.inHand;
	document.getElementById("gametick").innerHTML = evt.data;
	this.thisPlayersName = msg.player;
	var currentPlayer = null;
	if(msg.pot != null){
		this.currentPot = msg.pot;
		
	}else{
		this.currentPot = 0;
	}
	if(msg.card1 != null && msg.card2 != null){
		displayPlayerCards(msg.card1, msg.card2);
	}

	displayPot();
	var currentBet = null;
	var currentMove = null;
	var cards = null;
	
	if(msg.cards!=null){
		cards = msg.cards;
		for(var i = 0; i < 5; i++){
			if(cards[i] != null){
				var card = cards[i].card;
				var cardDiv = document.getElementById("tableCard"+i)
				displayPictureCardTable(card, cardDiv);
			}else{
				resetTableCard(i);
			}
			
			
			
		}
	}else{
		for(var i = 0; i < 5; i++){
			resetTableCard(i);
		}
	}
	
	
	if(this.inHand == true){
		
		currentPlayer = msg.currentPlayer;
	}else{
		
	}
	
	var players = msg.players;
	var seats = new Array(maxseats);
	
	for(var i in players){
		
		var playerName = players[i].name;
		var playerChips = players[i].chips;
		var seat = players[i].seat;
		var folded = players[i].folded;
		
		seats[seat] = true;
		
		
		if(playerName===currentPlayer){
			displaySeat(playerName, playerChips, seat, true, folded);
			
			
		}else{
			displaySeat(playerName, playerChips, seat, false, folded);
			
		}
		
	}
	
	var arrayLength = seats.length;
	for(var o = 0; o < arrayLength; o++){
		if(seats[o]==null){
			
			displayEmptySeat(o);
		}
	}
	
	if(msg.currentPlayer === this.thisPlayersName){	
		displayInputBox(msg.currentMove, msg.currentBet);
		
	}else{
		removeInputBox();
		
	}
	
	if(msg.currentBet!=null){
		document.getElementById("currentBet").innerHTML = msg.currentBet;
	}
	
	
};


function displayUsersCards(card1, card2){
	var userCards = "<p>"+card1+" "+card2+"</p>";
	document.getElementById("usersCards").innerHTML = userCards;
};

function displayTableCard(i, card){
	var cardDiv = document.getElementById("tableCard"+i);
	cardDiv.innerHTML = card;
};

function resetTableCards(){
	for(var x = 0; x < 5; x++){
		document.getElementById("tableCard"+x).innerHTML = "";
	}
};

function resetTableCard(i){
	document.getElementById("tableCard"+i).innerHTML = "";
};

function resetPlayerCards(){
	document.getElementById("playerCard0").innerHTML ="";
	document.getElementById("playerCard1").innerHTML ="";
};

function userGameMove(move){
	var chips = document.getElementById("betInputBox").value;
	var msg = '{"messageType":"playerMove", "chips":"' + chips + '", "move":"'+move+'"}';
	ws.send(msg);
};

function displayPot(){
	
	if(this.inHand == true){
		
		document.getElementById("pot").innerHTML = "<p>"+this.currentPot+"</p>";
	}else{
		document.getElementById("pot").innerHTML = "NOT IN HAND";
	}
	
};

function displaySeat(playerName, playerChips, seat, playersMove, folded){
	
	var seat = $("#seat"+seat);
	var player = $('<div/>');
	player.append("<p>"+playerName+"</br>"+playerChips+"</p>");
	if(thisPlayersName === playerName){
		var leaveButton = $('<button/>', {
	        text: "Leave Seat",
	        id: 'leaveSeatButton'+seat,
	        click: function () { leaveTable(); }
	    });
		player.className = 'playersGo';
	}
	
	if(playersMove == true){
		player.css("background-color", "red");
		
	}
	if(inHand == true){
		if(folded == true){
			player.append("</br><p>FOLDED</p>");
		}else if(folded == false){
			player.append("</br><p>IN HAND</p>");
		}
	}
	seat.html(player);
};

function displayEmptySeat(seat){
	var button = $('<button/>', {
        text: "Sit Here",
        id: 'seatButton'+seat,
        click: function () { joinTable(seat); }
    });
	$("#seat" + seat).html(button);
};

function send(messageType, data1, data2){
    var msg = '{"messageType":"'+ messageType +'", "message":"' + data1 + '", "author":"'+ data2+'"}';
    ws.send(msg);
    
};

function sendChatMessage(){
    var toSend = document.getElementById("text").value;
    send("chat", toSend, "");
    
    document.getElementById("text").value = "";
    
    
};


function joinTable(seat){
  
  var buyin = prompt("enter in buyin amount: ");
  var msg = '{"messageType":"'+ "join" +'", "buyin":"' + buyin + '", "seat":"'+seat+'"}';
  alert(msg);
  ws.send(msg);
  document.getElementById("buyin").style.visibility='hidden';
  
};

function leaveTable(){
	this.thisPlayerInSeat = false;
	var msg = '{"messageType":"leave"}';
	ws.send(msg);
};

function close(){
    if(ws !== null){
        ws.close();
        
    }
    
    
};

function displayInputBox(currentMove, bet){
	if(this.inputBoxActive == true && currentMove == this.currentMove){
		return;
	}
	this.inputBoxActive = true;
	this.currentMove = currentMove;
	var inputBox = $("#gameInput").empty();
	$('<input/>', {
		type: "text",
		id: "betInputBox",
	}).appendTo(inputBox);
	
	var foldButton = $('<button/>', {
			text: "Fold",
	        id: 'foldButton',
	        click: function () { userGameMove("fold"); }
	    }).appendTo(inputBox);
	if(currentMove==="check"){
		var checkButton = $('<button/>', {
			text: "Check",
	        id: 'checkButton',
	        click: function () { userGameMove("check"); }
	    }).appendTo(inputBox);
	}else if(currentMove==="raise" || currentMove==="call"){
		var callButton = $('<button/>', {
			text: "Call",
	        id: 'callButton',
	        click: function () { userGameMove("call"); }
	    }).appendTo(inputBox);
		
	}
	var raiseButton = $('<button/>', {
		text: "Raise",
        id: 'raiseButton',
        click: function () { userGameMove("raise"); }
    }).appendTo(inputBox);
	
	
	
	this.currentMove = currentMove;
};

function removeInputBox(){
	if(this.inputBoxActive == true){
		this.inputBoxActive = false;
		$("#gameInput").empty();
	}
	
};
