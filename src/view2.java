import java.awt.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.util.ArrayList;



public class view2 extends JFrame {

   // JLabels
  JLabel boardlabel;
  JLabel[] cardlabel = new JLabel[10];
  JLabel playerlabel;
  JLabel mLabel;
  JLabel trailerlabel;
  JLabel officelabel;
  JLabel currentPlayerLabel;
  JLabel rankLabel;
  JLabel scoreLabel;
  JLabel fameLabel;
  JLabel dollarLabel;
  JLabel locationLabel;
  JLabel roleLabel;
  JLabel daysLeftLabel;
  JLabel rehearsalCounterLabel;
  JLabel scoreBoardLabel;
  JLabel playerDieColorLabel;
  JLabel curPlayerDieDisplay;
  JLabel[] rolelabel;
  JLabel[] upgradelabel;
  JLabel[] offcardrolelabel;
  JLabel[] takeslabel;
  JLabel[] scores;
  ArrayList<JLabel[]> rolelabels = new ArrayList<JLabel[]>();
  ArrayList<JLabel[]> offcardrolelabels = new ArrayList<JLabel[]>();
  ArrayList<JLabel[]> takeslabels = new ArrayList<JLabel[]>();
  
  //Miscelaneous 
  controller c;
  deadwoodMouseController dMC;
  view2MouseListener v2ML;
  int revealedCount;
  
  //Cards
  ImageIcon cIcon[] = new ImageIcon[10];
  
  //JButtons
  JButton bAct;
  JButton bRehearse;
  JButton bMove;
  JButton bEnd;
  
  // JLayered Pane
  JLayeredPane bPane;
  
  // Playercount for dice
  JLabel[] players;
  public deadwoodMouseController getDeadWoodMouseController() {
     return this.dMC;
  }

public view2(controller c) {     // take in an array for first cards to setup
   super("Deadwood");
   bPane = getLayeredPane();
  
   
   /* deadwood board */
   boardlabel = new JLabel();
   ImageIcon icon =  new ImageIcon("board.jpg");
   boardlabel.setIcon(icon); 
   boardlabel.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());
   dMC=new deadwoodMouseController(c);
   v2ML=new view2MouseListener(dMC);
   boardlabel.addMouseListener(v2ML);
   
   officelabel = new JLabel();
   officelabel.setBounds(9,459,208,209);   
   officelabel.setName("office");
   v2ML=new view2MouseListener(dMC);
   officelabel.addMouseListener(v2ML);
   
   trailerlabel = new JLabel();
   trailerlabel.setBounds(991,248,194,201);
   trailerlabel.setName("trailer");
   v2ML=new view2MouseListener(dMC);
   trailerlabel.addMouseListener(v2ML);
   
 
   
   /* Add the board to the lowest layer */
   bPane.add(boardlabel, new Integer(0));
   bPane.add(trailerlabel, new Integer(1));
   bPane.add(officelabel, new Integer(1));
      
   /* Set the size of the GUI */
   setSize(icon.getIconWidth()+200,icon.getIconHeight()+100);
   

   setDefaultCloseOperation(EXIT_ON_CLOSE);
}

/*ScoreBoard Panel*/
public void showScoreBoard(ArrayList<Actor> players) {
	scoreBoardLabel = new JLabel("ScoreBoard");
	scoreBoardLabel.setBounds(1210,200,200,20);
	scoreBoardLabel.setFont(new Font("TimesRoman ", Font.PLAIN, 20));
	bPane.add(scoreBoardLabel);
	Actor[] playerScores = new Actor[players.size()];
	int temp;
	for (int x = 0; x<playerScores.length; x++) {
		playerScores[x] = players.get(x);
		temp = playerScores[x].getScore();
		int y = x-1;
		while(y>=0 && playerScores[x].getScore() < temp) {
			playerScores[y+1] = playerScores[y];
			y = y-1;
		}
		playerScores[y+1].setScore(temp);
		//System.out.print(playerScores[x].getScore()); prints intial scores
	}
	scores = new JLabel[players.size()];
	int offset =20;
	for (int i = 0; i<players.size(); i++) {
		scores[i] = new JLabel();
		scores[i].setBounds(1210,220+i*offset,200,20);
		scores[i].setText(playerScores[i].getName() + ":  Score: " + String.valueOf((playerScores[i].getScore())));
		bPane.add(scores[i], new Integer(7));
		
	}
	
}
//removeScoreBoard for updating purposes
public void removeScoreBoard(){
      for (int i = 0; i<scores.length; i++) {
		   bPane.remove(scores[i]);
		}
   
}
	//create a message box with the text of string s
	public void deadwoodMessageBox(String s) {
		JOptionPane.showMessageDialog(bPane, s);
	
	}
	//create a JOptionPane to ask the player how many people will be playing
	public int calcPlayers2() {
		   int players ;
		   String[] possibilities = {"2", "3", "4","5","6","7","8"};
		   //create the option pane and assign its return value to s
		   String s = (String)JOptionPane.showInputDialog(bPane,"Welcome to Deadwood Studios\n"
		           + "Pease select the number of players","Customized Dialog",JOptionPane.PLAIN_MESSAGE,
		           null, possibilities, "2");
		   players = Integer.parseInt(s);//s should be an int 
   return players;
}
/* current player's Information panel */
public void showCurrentPlayer(Actor currentPlayer) {
	
	currentPlayerLabel = new JLabel("Current Player: " + currentPlayer.getName());
	currentPlayerLabel.setBounds(1210,0,200,20);   	
	bPane.add(currentPlayerLabel, new Integer(7));
	rankLabel = new JLabel("Rank: "+ String.valueOf(currentPlayer.getRank()));
	rankLabel.setBounds(1220,20,200,20);
	bPane.add(rankLabel, new Integer(7));
	displayPlayerDice(currentPlayer.getRank(),currentPlayer.getDiceColor());
	scoreLabel = new JLabel("Score: "+ String.valueOf(currentPlayer.getScore()));
	scoreLabel.setBounds(1220,40,200,20);
	bPane.add(scoreLabel, new Integer(7)); 
	
    fameLabel = new JLabel("(Fame: "+ String.valueOf(currentPlayer.getFame()) );
    fameLabel.setBounds(1240,60,200,20);
    bPane.add(fameLabel, new Integer(7));   
    dollarLabel = new JLabel("   Dollars: "+ String.valueOf(currentPlayer.getMoney())+ ")");
    dollarLabel.setBounds(1300,60,200,20);
    bPane.add(dollarLabel, new Integer(7));
    locationLabel = new JLabel("Location: " + currentPlayer.getLocation().getName());
    locationLabel.setBounds(1220,100,200,20);
    bPane.add(locationLabel, new Integer(7));
    String roleName = "No Role";  
    rehearsalCounterLabel = new JLabel("Rehearsal Counter: " + currentPlayer.getRehearsalCounter());
    rehearsalCounterLabel.setBounds(1220,140,200,20);
    bPane.add(rehearsalCounterLabel, new Integer(7));
     
    for (Role r	: currentPlayer.getLocation().getOffCardRoles()) {
			if	(r.getActor() == currentPlayer)	{
				roleName= r.getRole();
			}
		} 
    if(currentPlayer.getLocation().hasScene()) {
		for (Role r	: currentPlayer.getLocation().getScene().getoncardroles())	{
			if	(r.getActor() == currentPlayer)	{
				roleName= r.getRole();
			}
		} 
}		
    roleLabel = new JLabel("Role: " + roleName);
    
    
    roleLabel.setBounds(1220,120,200,20);
    bPane.add(roleLabel, new Integer(7));
    
  
	
}
public void showDaysLeft(int dm) {

    daysLeftLabel = new JLabel("Days Left: " + String.valueOf(dm));
	daysLeftLabel.setBounds(10,875,300,100);
	daysLeftLabel.setFont(new Font("TimesRoman ", Font.PLAIN, 25));
	bPane.add(daysLeftLabel, new Integer(2));
}

public void removeDaysLeft() {
	  bPane.remove(daysLeftLabel);
}
public void removeCurrentPlayer(){
   //bPane.remove(playerDieColorLabel);
  bPane.remove(currentPlayerLabel);
  bPane.remove(rankLabel);
  bPane.remove(scoreLabel);
  bPane.remove(fameLabel);
  bPane.remove(dollarLabel);
  bPane.remove(locationLabel);
  bPane.remove(roleLabel);
  bPane.remove(rehearsalCounterLabel);
}

/* sets the dice but also removes any dice if there */
public void addPlayerDice(int playernum,int rank, String color, Location l) {
   // delete if already has a jlabel
   if (players[playernum] != null) {
      bPane.remove(players[playernum]);
   }
   ImageIcon icon =  new ImageIcon(color+rank+".png");
   players[playernum] = new JLabel();
   players[playernum].setIcon(icon);
   players[playernum].setBounds(l.getXcoord(),l.getYcoord(),46,46);
   players[playernum].setOpaque(false);
   players[playernum].setName("Player " + Integer.toString(playernum+1));
   bPane.add(players[playernum], new Integer(6));
}
/* sets the dice but also removes any dice if there
 *  Used in showCurrentPlayer to display current player's die*/
public void displayPlayerDice(int rank, String color) {
   // delete if already has a jlabel
   if (curPlayerDieDisplay != null) {
      bPane.remove(curPlayerDieDisplay);
   }
   ImageIcon icon =  new ImageIcon(color+rank+".png");
   curPlayerDieDisplay = new JLabel();
   curPlayerDieDisplay.setIcon(icon);
   curPlayerDieDisplay.setBounds(1300,15,46,46);
   curPlayerDieDisplay.setOpaque(false);
   bPane.add(curPlayerDieDisplay, new Integer(7));
}
public void addPlayerDice(int playernum, int x, int y) {
   players[playernum].setBounds(x,y,46,46);
}

//adds the upgrade labels and thier mouselisteners to the layered pane
public void addUpgrades(Location location) {
   upgradelabel = new JLabel[location.getUpgrades().size()];
   for (int i = 0; i < location.getUpgrades().size();i++) {
      upgradelabel[i] = new JLabel();
      upgradelabel[i].setBounds(location.getUpgrades().get(i).getXcoord(),location.getUpgrades().get(i).getYcoord(),19,19);
      upgradelabel[i].setOpaque(false);
      upgrade upgrade = location.getUpgrades().get(i);
      if(upgrade.getCurrency().equals("dollar")){//add the dollar labels to layer 4
         upgradelabel[i].setName(Integer.toString(upgrade.getLevel()));
         v2ML=new view2MouseListener(dMC);
         upgradelabel[i].addMouseListener(v2ML);
         bPane.add(upgradelabel[i], new Integer(4));
      }
      else if(upgrade.getCurrency().equals("credit")){//add hte fame labels to layer 5
         upgradelabel[i].setName(Integer.toString(upgrade.getLevel()));
         v2ML=new view2MouseListener(dMC);
         upgradelabel[i].addMouseListener(v2ML);
         bPane.add(upgradelabel[i], new Integer(5));
      }
   }
}



/* init total players */
public void totalPlayers(int totalplayers){
players = new JLabel[totalplayers];
}
// add takesShots labels to the pane
public void addTakesShots(Location location,int shot) {
   ImageIcon icon =  new ImageIcon("shot.png");
   takeslabels.get(location.getLocNum())[shot].setIcon(icon);
}
//remove the takeslabels associated with the label from the layered pane
public void delTakesShots(Location location) {
   for (int i = 0; i < location.getMaxShots(); i++) {
      takeslabels.get(location.getLocNum())[i].setIcon(null);    
   }
}

//add the take counter labels associated with the given location to the layered pane
//add the mouse listeners as well
public void addTakes(Location location) {
   takeslabel = new JLabel[location.getTakes().size()];
   for (int i = 0; i < location.getTakes().size(); i++) {
   takeslabel[i] = new JLabel();
   takeslabel[i].setBounds(location.getTakes().get(i).getXcoord(),location.getTakes().get(i).getYcoord(),47,47);
   takeslabel[i].setOpaque(false);
   takeslabel[i].setName(Integer.toString(location.getTakes().get(i).getShot()));
   v2ML=new view2MouseListener(dMC);
   takeslabel[i].addMouseListener(v2ML);
   bPane.add(takeslabel[i], new Integer(3));
   }
   takeslabels.add(takeslabel);
}

//remove the takes labels associated with the location number from the layered pane
public void delTakes(int location) {
   for (int i = 0; i < takeslabels.get(location).length; i++) {
       bPane.remove(takeslabels.get(location)[i]);
   }
   takeslabel=null;
}

//add the off card role labels  to the layered pane, add the mouselisteners as well
public void addOffCardRoles(Location location) {
   offcardrolelabel = new JLabel[location.getOffCardRoles().size()];
   for (int i = 0; i < location.getOffCardRoles().size(); i++) {
      offcardrolelabel[i] = new JLabel();
      offcardrolelabel[i].setBounds(location.getOffCardRoles().get(i).getXcoord(),location.getOffCardRoles().get(i).getYcoord(),46,46);
      offcardrolelabel[i].setOpaque(false);
      offcardrolelabel[i].setName(location.getOffCardRoles().get(i).getRole());
      v2ML=new view2MouseListener(dMC);
      offcardrolelabel[i].addMouseListener(v2ML);
      bPane.add(offcardrolelabel[i], new Integer(2));
   }
   offcardrolelabels.add(offcardrolelabel);
}

//remove that off card roles labels from the layered pane
public void delOffCardRoles(int location) {
   for (int i = 0; i < offcardrolelabels.get(location).length; i++) {
       bPane.remove(offcardrolelabels.get(location)[i]);
   }
   offcardrolelabel=null;
}

//remove the cardlabel for the specified location and everything on that card
public void delCard(Location location) {
   cardlabel[location.getLocNum()].setIcon(null);
   cardlabel[location.getLocNum()].setOpaque(false);
   
   // remove role labels 
   if(location.hasScene()){
      if(location.getScene().isRevealed()){
         for (int i = 0; i < rolelabels.get(location.getScene().getRevealOrder()-1).length; i++) {
               bPane.remove(rolelabels.get(location.getScene().getRevealOrder()-1)[i]);
         }
      }
   }
   
   rolelabel=null;
   bPane.remove(cardlabel[location.getLocNum()]);
}
//remove the on card role labels and set the icon to null but keep the bounds and coords of the label the same 
public void cardWrap(Location location) {
	//setting the icon to null and setting the bounds again allows the player to move back to a location 
	//that has wrapped
   cardlabel[location.getLocNum()].setOpaque(false);
   cardlabel[location.getLocNum()].setIcon(null);  
   cardlabel[location.getLocNum()].setBounds(location.getXcoord(),location.getYcoord(),205,115);   // remove role labels 
      for (int i = 0; i < rolelabels.get(location.getScene().getRevealOrder()-1).length; i++) {
         bPane.remove(rolelabels.get(location.getScene().getRevealOrder()-1)[i]);
   }
}
//reveal an unrevealed scene and add its on card role labels to the pane, add the mouselisteners for the oncard roles
public void revealCard(Location location,String imgName){
   this.revealedCount++;
   location.getScene().setRevealOrder(this.revealedCount);
   bPane.remove(cardlabel[location.getLocNum()]);
   cardlabel[location.getLocNum()] = null;
   cardlabel[location.getLocNum()] = new JLabel();  
   cIcon[location.getLocNum()] = new ImageIcon(imgName);// set the icon to the corresponding scene card image
   cardlabel[location.getLocNum()].setIcon(cIcon[location.getLocNum()]);
   cardlabel[location.getLocNum()].setBounds(location.getXcoord(),location.getYcoord(),cIcon[location.getLocNum()].getIconWidth(),cIcon[location.getLocNum()].getIconHeight());
   cardlabel[location.getLocNum()].setOpaque(true);
   cardlabel[location.getLocNum()].setName(location.getName());
   v2ML=new view2MouseListener(dMC);					//add the mouselistener to the label
   cardlabel[location.getLocNum()].addMouseListener(v2ML);
   bPane.add(cardlabel[location.getLocNum()], new Integer(1));//add the label to layer 1 of the layered pane
   
   //oncard roles for cards
   int role_xcoord;
   int role_ycoord;
   rolelabel = new JLabel[location.getScene().getoncardroles().size()];
   for (int i = 0; i < location.getScene().getoncardroles().size(); i++) {
      rolelabel[i] = new JLabel(); 
      role_xcoord = location.getXcoord() + location.getScene().getoncardroles().get(i).getXcoord();
      role_ycoord = location.getYcoord() + location.getScene().getoncardroles().get(i).getYcoord();
      rolelabel[i].setBounds(role_xcoord,role_ycoord,40,40);
      rolelabel[i].setOpaque(false);
      rolelabel[i].setName(location.getScene().getoncardroles().get(i).getRole());
      v2ML=new view2MouseListener(dMC);
      rolelabel[i].addMouseListener(v2ML);
      bPane.add(rolelabel[i], new Integer(2));
   }
   rolelabels.add(rolelabel);//add the role label to the rolelabels array that keeps track of all scenes' roles
}
//add a face down scene card to the layered pane add the mouselistener for the scene card
public void addCard(Location location, String imgName) {
   cardlabel[location.getLocNum()] = new JLabel();  
   cIcon[location.getLocNum()] = new ImageIcon("unrevealed.png");
   cardlabel[location.getLocNum()].setIcon(cIcon[location.getLocNum()]);
   cardlabel[location.getLocNum()].setBounds(location.getXcoord(),location.getYcoord(),cIcon[location.getLocNum()].getIconWidth(),cIcon[location.getLocNum()].getIconHeight());
   cardlabel[location.getLocNum()].setOpaque(true);
   cardlabel[location.getLocNum()].setName(location.getName());
   v2ML=new view2MouseListener(dMC);
   cardlabel[location.getLocNum()].addMouseListener(v2ML);
   bPane.add(cardlabel[location.getLocNum()], new Integer(1));
  
  

}
//upon completion of day reset the number of revealed scenes
public void resetRevealedCount(){
      this.revealedCount=0;
  }




}