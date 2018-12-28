public class dayManager {
	/*Attributes*/
	private int numplayers;	//Game manger has initiated constants for numplayers and daycount.
	private int daycount = 3;
    private int maxDays = 3;
 
	
	/*Constructor*/
	public dayManager(int numplayers) {
      this.numplayers = numplayers;
      setDays();
	}
   // Getters and setters
   public void setDays() {
      if (this.numplayers > 3) {
         this.daycount = 4;
         this.maxDays=4;
      }
   }
   
   public int getDayCount() { /// get dayCount
      return this.daycount;
   }
   public int getMaxDays(){ //get maxDays
      return this.maxDays;
   }
  
	// this method sets the daycount to the given parameter's value
	public void setDayCount(int dayCount) {
      this.daycount=dayCount;
	}
  
   
   public boolean dayComplete(Location[] locations) {  //if day complete returns true, reset all players to trailer
      int j = 0;
      Scene checker;
      for (int i = 0; i < 12; i++) {
         checker = locations[i].getScene();
         if (checker == null) {
         ;
         }
        
         else if (checker.isActive() == false) {
            j++;
         }
      }
      
      if (j >= 9) { // all but one scene is done. day is complete.
         return true;
      }
      else {
         return false;
      }
   }
	
}
