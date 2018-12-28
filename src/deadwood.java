import java.util.ArrayList;
import java.util.Collections;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.Arrays;



class deadwood {

   public static void main(String[] args) {
      // main managers     
      View v = new View();
      controller c = new controller();
      view2 view2 = new view2(c);
	   view2.setVisible(true); 
	   c.addView(view2);
	   int playercount = c.calcPlayers();
	   view2.totalPlayers(playercount); 
	   gameManager gm = new gameManager(playercount);
      dayManager dm = new dayManager(playercount);
      PaymentManager pm  = PaymentManager.getInstance();  
       
       
       int turncounter = 0;
       // setup locations and parse in board
       Location[] locations = locationSetup();
       board b = board.getInstance(locations);
  
       
       // actor setup
      ArrayList<Actor> actors = new ArrayList<Actor>();
      String[] colors = {"b","c","g","o","p","r","v","y"};
      ArrayList<String> dicecolors = new ArrayList<String>();
      dicecolors.addAll(Arrays.asList(colors));
      Collections.shuffle(dicecolors);
      int rank = 1;
      int fame = 0;
      if (playercount >= 7) {
         rank = 2;
      }
      else if (playercount == 5) {
         fame = 2;
      }
      else if (playercount == 6) {
         fame = 4;
      }
      
      
      // save trailer location and set all players to it
      Location trailer = null;
      for (int i = 0; i < 12; i++) {
         if(locations[i].getName().equals("trailer")) {
            trailer = locations[i];
         }
         if(locations[i].getName().equals("office")) {
            view2.addUpgrades(locations[i]);
         }
      }
   
      /* set up all players (rank, fame, die color and place them in the trailer*/
      for (int i = 0; i < playercount; i++) {
         actors.add(new Actor(i, "Player " + Integer.toString(i+1),dicecolors.get(i)));
         actors.get(i).setRank(rank);
         actors.get(i).setFame(fame);
         view2.addPlayerDice(i,actors.get(i).getRank(),dicecolors.get(i), trailer);
      }
      for (int i = 0; i < playercount; i++) {
         actors.get(i).setLocation(trailer);
      }
      view2.showScoreBoard(actors);//display the scoreboard in the lower right corner
      
      
       ArrayList<Scene> sceneDeck = sceneDeck();//sceneDeck parses all the scene values from cards.xml
       int maxDays = dm.getMaxDays();//set maxDays for displaying the correct day number
       while (dm.getDayCount() > 0) {//while dayCount>0 the game should run         
          
         Collections.shuffle(sceneDeck); // shuffle at beginning to get first 10
         if(sceneDeck.size()<10){// parse new scenes if there aren't enough scenes left in sceneDeck
            sceneDeck = sceneDeck();
         }
          for (int i = 0; i < 10; i++) { // sets up everything but trailer and casting
            locations[i].setScene(sceneDeck.get(i));
            locations[i].setLocNum(i); // used for view class to know location number
            // add card to gui (need to parse in image names)
            view2.addCard(locations[i], sceneDeck.get(i).getImgName()); 
            view2.addOffCardRoles(locations[i]);
            view2.addTakes(locations[i]);
            sceneDeck.remove(i);//remove the scene from the deck so it doesn't get added on in a later day
         }
          
          //set all the actors to the trailer and update the model and view at the start of everyday
          for (Actor A : actors ) {
            A.setLocation(trailer);
            trailer.addPlayers(A);
            view2.addPlayerDice(A.getPlayerNum(),A.getLocation().getXcoord()+ (10*A.getLocation().getAllPlayers().size()), A.getLocation().getYcoord());
         }
          //choose the first player and start the first day updating the model and view
          int firstplayer = gm.getfirstPlayer();
          turncounter = firstplayer;
          view2.deadwoodMessageBox("Day "+ (maxDays-dm.getDayCount()+1)+" has Started!");
          view2.showDaysLeft(dm.getDayCount());//display days left in bottom left
          view2.showCurrentPlayer(actors.get(turncounter));//display current player info in right pane
          while (dm.dayComplete(locations) == false) {//check all the locations for unfinished scenes when 1 left end day          
            
            try{//Prevents consecutive mouse clicks from being processed a players turn. 
            
                Thread.sleep(200);
                
            } 
            catch(InterruptedException ex){
            
                Thread.currentThread().interrupt();
                
            }
            if (c.getEnd().equals("notend")) {
                c.playerTurn(actors.get(turncounter),b); //start a players turn
             }   
             else {
             // reset turn counter when has gone through all players
                view2.deadwoodMessageBox(    actors.get(turncounter).getName()+"'s Turn has ended");                
                if (turncounter == playercount-1) {
                  turncounter = 0;
                }     
                else {
                  
                  turncounter++;
                }      
                //update view at end of player turn 
                view2.removeCurrentPlayer();
                view2.showCurrentPlayer(actors.get(turncounter));
                view2.removeScoreBoard();
                view2.showScoreBoard(actors);                
                c.setEnd("notend");  
                dm.dayComplete(locations);         //keeps checking day complete  
            } 
              
          }       
          //update view at end of day
          view2.removeCurrentPlayer();
          dm.setDayCount(dm.getDayCount()-1);  
          view2.removeDaysLeft();
          view2.showDaysLeft(dm.getDayCount());
          view2.deadwoodMessageBox("******The day has been completed*******\n\n\n");
          v.endDay();
         //reset the board at end of day removing all actors from roles and such
         for (Location L : locations ) {//visit each location and remove all reset it
              if	(L.getName().equals("office")	||	L.getName().equals("trailer")) {
              ;
              }
              else {
                 for (Role r :	L.getOffCardRoles())	{
                       r.setActor(null);
                 }
         		  for (Role r : L.getScene().getoncardroles()) {
                      r.setActor(null);        
   		        }	
                 L.resetShotCounter();  
                 view2.delCard(L);//delete the scene card from each location
                 view2.delOffCardRoles(L.getLocNum());//reset the offcard roles
                 view2.delTakesShots(L);//reset the shot markers
                 L.setScene(null);//set the scene at the location to null
              }
          }
       }
       
       
       /* end game routine */ 
       v.endGame();
       turncounter = 0;
       for (int i = 0; i < playercount; i++) {
         v.displayScores(actors.get(i));//print all player's scores
       }
       gm.endGame(); //end the game
       view2.deadwoodMessageBox("Game ended -Deadwood.java");
   }
   
  
//XML methods
 public static String getNameString(Node n){
      String s = n.toString();
            String[] sArr = s.split("=");
            String b =sArr[1];
            b=b.substring(1,b.length()-1);
            return b;
   }
 	// Method that parses information from board.xml
   public static ArrayList<Element> boardParser() {
      NodeList[] boardRoomsArray;
	    try {
	    	File xmlFile = new File ("board.xml");
	    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    	Document doc = dBuilder.parse(xmlFile);
	    	doc.getDocumentElement().normalize();
	    	Element boardElement = doc.getDocumentElement();	//BoardElement a location	(Default)
	    	NodeList boardRooms = boardElement.getElementsByTagName("set");	// Creates a NodeList from each element of set
         ArrayList<Element> elementList = new ArrayList<Element>();
         boardRoomsArray = new NodeList[boardRooms.getLength()]; // creates a NodeList array that holds the connections between each nodeList
         
         NodeList trailer = boardElement.getElementsByTagName("trailer");	// Creates a NodeList from each element of trailer
	     NodeList office = boardElement.getElementsByTagName("office");	// Creates a NodeList from each element of office
         
         Element rooms;
	    	for (int i = 0; i < boardRooms.getLength(); i++){
	    		rooms = (Element) boardRooms.item(i); // rooms is an element of the NodeList boardrooms
            elementList.add((Element) boardRooms.item(i));
	    		NodeList neighborList = rooms.getElementsByTagName("neighbor"); // Creates a NodeList from each element of neighbor
            Element boardArea = (Element) neighborList.item(i);	// boardArea is an element of the NodeList neighborList
	    		NodeList takesList = rooms.getElementsByTagName("take"); // Creates a NodeList from each element of takes
	    		NodeList partList = rooms.getElementsByTagName("part");// Creates a NodeList from each element of parts
	    	   boardRoomsArray[i] = boardRooms;
	    	}
 
         // trailer/office
            elementList.add((Element)trailer.item(0));
            elementList.add((Element)office.item(0));
         
         return elementList; 
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
      return null;  
     }  
     
     
     
   // This method uses the boardParser in order to set up the dimensions the nodeList connections within boardParser, to the corresponding locations
     public static Location[] locationSetup() {
      Location[] locs = new Location[12];
      take take;
      String roleName;
      String neighborName;
      String locName;
      int maxShots;
      int shots;
      int xcoord, ycoord, role_xcoord, role_ycoord, take_xcoord, take_ycoord, upgrade_xcoord, upgrade_ycoord;
      
      
      ArrayList<Element> boardParser = boardParser();
      ArrayList<String> neighbors;
      ArrayList<Role> roles;
      ArrayList<take> takes; 
      ArrayList<String> officeNeighbors = new ArrayList<String>();
      ArrayList<String> trailerNeighbors = new ArrayList<String>();
      ArrayList<upgrade> upgrades = new ArrayList<upgrade>();
      

    // Iterates through the boarParser and gets a specific "NamedItem" 
    for(int j=0; j<boardParser.size()-2;j++){
      neighbors = new ArrayList<String>();
      roles = new ArrayList<Role>();
      takes = new ArrayList<take>();
      locName = getNameString(boardParser.get(j).getAttributes().getNamedItem("name"));
      // neighbors
      for(int i =0; i < boardParser.get(j).getElementsByTagName("neighbor").getLength(); i++){
         neighborName= getNameString(boardParser.get(j).getElementsByTagName("neighbor").item(i).getAttributes().getNamedItem("name"));
         neighbors.add(neighborName.toLowerCase());
  
       }   
       
       // coordinates for takes (need to get shots for each and add to a takesclass)
       for(int i =0; i < boardParser.get(j).getElementsByTagName("take").getLength(); i++){
          Element takeArea = (Element) boardParser.get(j).getElementsByTagName("take").item(i);
	    	 NodeList tArea = takeArea.getElementsByTagName("area");
          take_xcoord = Integer.parseInt(getNameString(tArea.item(0).getAttributes().getNamedItem("x")));
          take_ycoord = Integer.parseInt(getNameString(tArea.item(0).getAttributes().getNamedItem("y")));
          shots = Integer.parseInt(getNameString(boardParser.get(j).getElementsByTagName("take").item(i).getAttributes().getNamedItem("number")));
          take = new take(shots,take_xcoord,take_ycoord); // need to get shot value
          takes.add(take);
       }      
       maxShots = Integer.parseInt(getNameString(boardParser.get(j).getElementsByTagName("take").item(0).getAttributes().getNamedItem("number")));
       
   
       // coordinates of cards
       xcoord = Integer.parseInt(getNameString(boardParser.get(j).getElementsByTagName("area").item(0).getAttributes().getNamedItem("x")));
       ycoord = Integer.parseInt(getNameString(boardParser.get(j).getElementsByTagName("area").item(0).getAttributes().getNamedItem("y")));
       
       // coordinates for roles
       for(int i =0; i < boardParser.get(j).getElementsByTagName("part").getLength(); i++){
           roleName= getNameString(boardParser.get(j).getElementsByTagName("part").item(i).getAttributes().getNamedItem("name"));
           int rank = Integer.parseInt(getNameString(boardParser.get(j).getElementsByTagName("part").item(i).getAttributes().getNamedItem("level")));
           Element partArea = (Element) boardParser.get(j).getElementsByTagName("part").item(i);
	    	  NodeList pArea = partArea.getElementsByTagName("area");
           role_xcoord = Integer.parseInt(getNameString(pArea.item(0).getAttributes().getNamedItem("x")));
           role_ycoord = Integer.parseInt(getNameString(pArea.item(0).getAttributes().getNamedItem("y")));
           roles.add(new Role(roleName.toLowerCase(),rank,role_xcoord,role_ycoord));
        }
         locs[j] = new Location(maxShots,locName.toLowerCase(),null,neighbors,roles,takes);
         locs[j].setXcoord(xcoord);
         locs[j].setYcoord(ycoord);
      }
       
       
       
       /* setup trailer and casting office */  
      String currency;
      int level, amount;
      for(int j=boardParser.size()-2; j<boardParser.size();j++){
        String name = "trailer";
        neighbors = new ArrayList<String>();
        for(int i =0; i < boardParser.get(j).getElementsByTagName("neighbor").getLength(); i++){
         neighborName= getNameString(boardParser.get(j).getElementsByTagName("neighbor").item(i).getAttributes().getNamedItem("name"));
         neighbors.add(neighborName.toLowerCase());
        }
        xcoord = Integer.parseInt(getNameString(boardParser.get(j).getElementsByTagName("area").item(0).getAttributes().getNamedItem("x")));
        ycoord = Integer.parseInt(getNameString(boardParser.get(j).getElementsByTagName("area").item(0).getAttributes().getNamedItem("y")));

        
        if (j == 11) { 
           name = "office";
        }
        locs[j] = new Location(name,neighbors);
        locs[j].setXcoord(xcoord);
        locs[j].setYcoord(ycoord); 
       
        // do upgrades if office
        if (j == 11) {
           NodeList upgradesList = boardParser.get(j).getElementsByTagName("upgrades");
           Element upgrade_elems = (Element) upgradesList.item(0);
			  NodeList upgrade_elem = upgrade_elems.getElementsByTagName("upgrade");
           upgrade upgrade;
           for(int i = 0; i < upgrade_elem.getLength(); i++){
              level = Integer.parseInt(getNameString(upgrade_elem.item(i).getAttributes().getNamedItem("level")));
              currency = getNameString(upgrade_elem.item(i).getAttributes().getNamedItem("currency"));
              amount = Integer.parseInt(getNameString(upgrade_elem.item(i).getAttributes().getNamedItem("amt")));
              
              Element upgradeArea = (Element) upgrade_elem.item(i);
				  NodeList uArea = upgradeArea.getElementsByTagName("area");
              
              upgrade_xcoord = Integer.parseInt(getNameString(uArea.item(0).getAttributes().getNamedItem("x")));
              upgrade_ycoord = Integer.parseInt(getNameString(uArea.item(0).getAttributes().getNamedItem("y")));
              upgrade = new upgrade(currency,level,amount,upgrade_xcoord,upgrade_ycoord);
              upgrades.add(upgrade);
           }
           locs[j].setUpgrades(upgrades);
           
        }

     }
      

   return locs; 
   }
   
     // This method creates takes the cards.xml file and then parses the information that produces our scene cards.
	public static NodeList CardParser() {
			try {
				File xmlFile = new File("cards.xml");
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(xmlFile);
				Element root = doc.getDocumentElement();
				root.normalize();
				ArrayList<Element> elementList = new ArrayList<Element>();
				NodeList cardNames = root.getElementsByTagName("card");
				
				for (int i= 0; i<cardNames.getLength(); i++) {
					Element scene = (Element) cardNames.item(i);
               elementList.add(scene);
					NodeList sceneList = scene.getElementsByTagName("scene");
					NodeList roleList = scene.getElementsByTagName("part");
              // return cardNames;             /// this is supposed to be outside ? 
				}
             return cardNames;
			}catch (Exception e) {
		    	e.printStackTrace();
		    }
          
           return null;
	}
	
	//This method is assigning the information that was parsed from the cards.xml file to a ArrayList of scenes
      public static ArrayList<Scene> sceneDeck() {
         NodeList elementList = CardParser();
         ArrayList<Scene> sceneDeck  = new ArrayList<Scene>();     
         String scenename;
         String roleName;
         int scenenum;
         int budget;
         int rank;
         String imgName;
         ArrayList<Role> oncardroles = new ArrayList<Role>();
         int role_xcoord;
         int role_ycoord;
         
      	for (int i= 0; i<elementList.getLength(); i++) {
               oncardroles = new ArrayList<Role>();
				scenename = getNameString(elementList.item(i).getAttributes().getNamedItem("name"));
               budget = Integer.parseInt(getNameString(elementList.item(i).getAttributes().getNamedItem("budget")));
				Element scene = (Element) elementList.item(i);
               NodeList sceneList = scene.getElementsByTagName("scene");
				NodeList roleList = scene.getElementsByTagName("part");
               imgName = getNameString(elementList.item(i).getAttributes().getNamedItem("img"));
               scenenum = Integer.parseInt(getNameString(sceneList.item(0).getAttributes().getNamedItem("number")));
              
               // card role coords
               for(int j=0; j<roleList.getLength();j++){
                  roleName=getNameString(roleList.item(j).getAttributes().getNamedItem("name") );
                  rank=Integer.parseInt(getNameString(roleList.item(j).getAttributes().getNamedItem("level")));
                  Element roleArea = (Element) roleList.item(j);
						NodeList area = roleArea.getElementsByTagName("area");
                  role_xcoord = Integer.parseInt(getNameString(area.item(0).getAttributes().getNamedItem("x")));
					   role_ycoord = Integer.parseInt(getNameString(area.item(0).getAttributes().getNamedItem("y")));
                  oncardroles.add(new Role(roleName,rank,role_xcoord,role_ycoord));
               }
               sceneDeck.add(new Scene(budget,oncardroles,scenename, imgName));
		}

      return sceneDeck;
   }
     
     
     
}