/*This class is a holdover from the text based version
 * we still use some of its functions to display to the terminal when the turn,day, and game end
 * */ 
public class View
{
   public void View(){
	   
   }

    public void calcPlayers(){
    	 System.out.println("Hello and welcome to Deadwood Studios! How many players are there?");

    }
    public void turnStart(Actor a){
      System.out.println(a.getName()+", Enter your action.");
    }
    
    
    public void actions(){
      System.out.println(" Acceptable commands are:");
      System.out.println("       who");
      System.out.println("       where");
      System.out.println("       move  room");
      System.out.println("       work part");
      System.out.println("       upgrade $ level");
      System.out.println("       upgrade cr level");
      System.out.println("       rehearse");
      System.out.println("       act");
      System.out.println("       end");
    }
    
    public void who(String name,int money, int fame)
    
    {
      System.out.println(name + " ($" +money+","+fame+"cr)"); 
    }
    public void who(String name,int rank,int money, int fame,String loc)
    
    {
      System.out.println(name + " ($" +money+","+fame+"cr)"); 
    
  }
    public void endTurn(){
      System.out.println("Turn Ended.");
    }
    public void where(String locName, String scene){
    System.out.println(locName + " shooting " + scene);
    
    }    
    public void parts(Location loc){
    
    
    }    
    public void endDay(){
      System.out.println("Day ended. All actors moved back to trailers.");
    }
    public void endGame(){
      System.out.println("Final Day Ended! Calculating Final Scores..."); 
    }
    public void displayScores(Actor actor){
      System.out.print(actor.getName()+ " got ");
      System.out.println(actor.getScore()+ " score.");
    }
    public void displayScores(Actor[] actors){
      for(int i=0 ; i<actors.length;i++){
         displayScores(actors[i]);
      }    
    }
}