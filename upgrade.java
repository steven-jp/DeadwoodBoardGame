public class upgrade {
   /* Attributes */
   private int xcoord;
   private int ycoord;
   private String currency;
   private int level;
   private int amount;
   
   
   /*Constructor*/
   public upgrade(String currency, int level, int amount, int xcoord, int ycoord) {
      this.currency = currency;
      this.level = level;
      this.amount = amount;
      this.xcoord = xcoord;
      this.ycoord = ycoord;
   }
   
   public int getXcoord(){
      return this.xcoord;
   }
   public int getYcoord(){
      return this.ycoord;
   }
   //gets the type of currency used to upgrade with (money or fame)
   public String getCurrency(){
      return this.currency;
   }
   //gets the level the upgrade is associated with (2 through 6)
   public int getLevel(){
      return this.level;
   }
   //gest tha amount of currency needed to upgrade
    public int getAmount(){
      return this.amount;
   }
}