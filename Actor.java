public class Actor
{
   /*  Attributes  */
   private int rank;   
   private int money;   
   private int fame;   
   private int rehearsalCounter;   
   private int score;  
   private String name; 
   private int player; 
   private Location loc; 
   String diceColor;
   public boolean working = false;
   
   /* Constructors */
   public Actor(int player,String name, String diceColor){ 
      this.rank=1;
      this.money=0;
      this.fame=0;
      this.rehearsalCounter=0;
      this.score=0;
      this.name=name;
      this.player = player;
      this.diceColor = diceColor;
   }
   public Actor(){}
   //creates actor with money= m and fame=f
   public Actor(int player, int m, int f){
      this.player = player;
      this.rank=1;
      this.money=m;
      this.fame=f;
      this.rehearsalCounter=0;
      this.score = 0;
   }
   //creates actor with palyer number = player, name = String name, Location = loc, money= m and fame=f
   public Actor(int player, int m, int f,String name,Location loc){
      this.player = player;
      this.rank=1;
      this.money=m;
      this.fame=f;
      this.rehearsalCounter=0;
      this.score = 0;      
      this.name=name;
      this.loc = loc;
   }
      
   
   //get rank of actor
   public int getRank(){           
      return this.rank;
   }
   //get player number of actor
   public int getPlayerNum(){
      return this.player;
   }
   //get dice color of actor
   public String getDiceColor() {
      return this.diceColor;
   }
   //get location of actor
   public Location getLocation(){
      return this.loc;
   }
   //set location of actor
   public void setLocation(Location loc){
      this.loc = loc;
   }
   
   //get amount of money an actor has
   public int getMoney(){
      return this.money;
   }
   
   //get actor fame value
   public int getFame(){
      return this.fame;
   }
   
   //get actor number of rehearsal Counters 
   public int getRehearsalCounter(){
      return this.rehearsalCounter;
   }
   
   //get actor scores
   public int getScore(){
      setScore();
      return this.score;
   }
   //get name of actor
   public String getName(){
      return this.name;
   }
   // set next player
   public void setRank(int r){  
      this.rank = r;
   }
   
   //set actor value
   public void setMoney(int m){
      this.money = m;
   }
   
   //set an actor's fame value
   public void setFame(int f){
      this.fame = f;
   }
   
   //set an actor's rehearsal counter value
   public void setRehearsalCounter(int rc){
      this.rehearsalCounter = rc;
   }
   //calculate an actor's score value
   public void setScore(){   // remove int s
      this.score = this.money + this.fame + (5*this.rank);
   }
   //set the score of an actor used in showScoreBoard
   public void setScore(int temp) {
	  this.score = temp;
   }

}