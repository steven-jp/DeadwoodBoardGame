import java.util.ArrayList;
public class Scene {

	/* attributes */
	private int budget;
	private ArrayList<Role> oncardroles; // roles avail
	private boolean isRevealed = false; 
	private boolean isActive; 
	private String name;
	private String imgName;
	private int revealOrder=-1;



	/* constructors */
	public Scene(int budget, ArrayList<Role> oncardroles){//,String name) {
		this.budget = budget;
		this.oncardroles = oncardroles;
		this.isActive = true;
		this.name = name;
	}

	public Scene(int budget, ArrayList<Role> oncardroles,String name, String imgName) {
		this.budget = budget;
		this.oncardroles = oncardroles;
		this.isActive = true;
		this.name = name;
		this.imgName = imgName;
	}

	/* Scene card methods */
	
	//update the scene to the wrapped state (no actors in on card roles and scene is not active)
	public void wrapscene() {
		for (Role r : oncardroles) {
			r.setActor(null);
		}
		this.isActive = false;
	}
	//check if the scene is revealed
	public boolean isRevealed() {
		return this.isRevealed;
	}
	//check if the scene is active
	public boolean isActive() {
		return this.isActive;
	}
	//reveal the scene
	public void revealScene() {
		this.isRevealed = true;
	}
	//getters and setters
	public void setRevealOrder(int i){
		this.revealOrder=i;
	}
	public int getBudget(){
		return this.budget;
	}
	public String getImgName(){
		return this.imgName;
	}
	public ArrayList<Role> getoncardroles(){
		return this.oncardroles;
	}
	public void setoncardroles(ArrayList<Role> oncardroles){    // added
		this.oncardroles = oncardroles;
	}

	public int getRoleCount(){           //// added
		return this.oncardroles.size();
	}
	//used to keep track of the reveal order in the view
	public int getRevealOrder(){
		if(revealOrder>=0){
			return this.revealOrder;
		}
		else{
			return -1;
		}
	}

	public String getName(){
		return this.name;
	}
}