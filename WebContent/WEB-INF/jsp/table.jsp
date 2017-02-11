<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <jsp:include page="includes/head.jsp" />
     <jsp:include page="includes/header.jsp" />
    
    <body onLoad="init('${table.tableName}')" onClose="close()" >
    	<div id="mainWrap" >
	    	<div id="gametick"></div>
	        <h1><c:out value="${table.tableName}" /></h1>
	        <div id="table">
	        
	        	<div id="seat0" class="seat">
		        	<button onClick="joinTable(0)" id="seatButton0" >Sit here original</button>
		        </div>
		        <div id="seat1" class="seat">
		        	<button onClick="joinTable(1)" id="seatButton1" >Sit here</button>
		        </div>
		        <div id="seat2" class="seat">
		        	<button onClick="joinTable(2)" id="seatButton2" >Sit here</button>
		        </div>
		        <div id="seat3" class="seat">
		        	<button onClick="joinTable(3)" id="seatButton3" >Sit here</button>
		        </div>
		        <div id="seat4" class="seat">
		        	<button onClick="joinTable(4)" id="seatButton4" >Sit here</button>
		        </div>
		        <div id="seat5" class="seat">
		        	<button onClick="joinTable(5)" id="seatButton5" >Sit here</button>
		        </div>
	        
	        	<div id="pot" ></div></br><div id="currentBet"></div>
		        <div id="tableCards" >
		        	<div id="tableCard0" class="tableCards"></div>
		        	<div id="tableCard1" class="tableCards"></div>
		        	<div id="tableCard2" class="tableCards"></div>
		        	<div id="tableCard3" class="tableCards"></div>
		        	<div id="tableCard4" class="tableCards"></div>
		        </div>
		        
		        <div id="winner" ></div>
		        <div id="usersCards">
	        		<div id="playerCard0" ></div>
	        		<div id="playerCard1" ></div>	
	        	</div>
		    </div>
		    <div id="underTable">
		    	
	        	<div id="gameInput">
	        
	        
	        
	        
	        
	        	
	       		</div>
	       		<div id="output" >
	            
	            
	            
	            
	            
	        
	        
	      	   </div>
	      	   <div id="chatbox">
	            	<input type="text" id="text" />
	            	<input type="button" id="butt" onClick="sendChatMessage()" value="Send" />
	           </div>
	       </div>
	        
        </div>
    </body>
</html>
