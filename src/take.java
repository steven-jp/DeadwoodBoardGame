public class take {
   /* Attributes */
   private int shot;
   private int xcoord;
   private int ycoord;
   
   public take(int shot, int xcoord, int ycoord) {
      this.shot = shot;
      this.xcoord = xcoord;
      this.ycoord = ycoord;
   }
   
   public int getXcoord(){
      return this.xcoord;
   }
   public int getYcoord(){
      return this.ycoord;
   }
   public int getShot(){
      return this.shot;
   } 
}