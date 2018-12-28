/*This class is attatched to every instance of our view2mouselistener and calls it's methods 
 * with values supplied by the mouseclicks from the mouse listener
 */
public class deadwoodMouseController{
	//instantiate the class attributes
	controller c;
	static int move = 0;
	static int difaction = 0;
	//constructor that assigns the given controller reference parameter c, to the class attribute c
	public deadwoodMouseController(controller c){
		this.c = c;
	}
	//calls the controller move with a string array (used for multiple word locations)
	public void move(String[] arg){
		if(move ==0){//if the player hasn't moved yet
			difaction=c.move(arg[0],arg[1]);//call c.move if it is a successful move it will return 1 other wise it returns 0
			if(difaction==1){//upon successful move we iterate the move attribute to indicate the player has moved already
				move++;
			}
		}
		else{
			end();//if the player has moved already end the turn
		}
	}
	//a single word version of the move function
	public void move(String arg){
		if(move ==0){//if the player hasn't moved yet
			difaction=c.move(arg);//call c.move if it is a successful move it will return 1 other wise it returns 0
			if(difaction==1){//upon successful move we iterate the move attribute to indicate the player has moved already
				move++;
			}
		}
		else{
			end();//if the player has moved already end the turn
		}
	}
	//starts the act action in controller
	public void act(){
		difaction=c.act();//assign the success value of c.act to difcation
		if(difaction==1){//if difaction is 1 then the c.act was successful
			end();//the action was performed successfully so end the turn
		}
	}
	//starts the dollarUpgrade function in controller with a string value that represents the desired upgrade rank
	public void dollarUpgrade(String arg){
		difaction=c.dollarUpgrade(Integer.parseInt(arg));
		if(difaction==1){//if difaction is 1 then the c.act was successful
			end();//the action was performed successfully so end the turn
		}
		// end();
	}
	//starts the fameUpgrade function in controller with a string value that represents the desired upgrade rank
	public void fameUpgrade(String arg){
		difaction=c.fameUpgrade(Integer.parseInt(arg));
		if(difaction==1){//if difaction is 1 then the c.act was successful
			end();//the action was performed successfully so end the turn
		}
		// end();
	}
	public void rehearse(){
		end();
	}
	//this ends the turn so we set move back to 0 for the next turn
	public void end(){
		move=0;
		c.setEnd("end");//ends the turn in the controller referenced by c
	}
	public void work(String arg){
		difaction=c.work(arg);//assign the success value of c.work to difaction
		if(difaction==1){//if difaction is 1 then the c.work was successful
			end();//the action was performed successfully so end the turn
		}
	}
}