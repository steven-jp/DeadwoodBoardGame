public class Role {

	/* attributes */
	private Actor actor;
	private String role; // changed to string
	private int requiredrank;
	private int xcoord;
	private int ycoord;



	/* constructors */
	public Role(String role) { // added to take in a string role
		this.actor = null;
		this.role = role;
		this.requiredrank = 1;
	}

	public Role(String role,int requiredRank, int xcoord, int ycoord) { // added to take in a string role
		this.actor = null;
		this.role = role;
		this.requiredrank = requiredRank;
		this.xcoord = xcoord;
		this.ycoord = ycoord;
	}
	//check if a role is taken or not returns true if the role isn't taken
	public boolean slotAvailability(){
		if(this.actor == null){//check if actor in role
			return true;
		}
		return false;
	}
	//getters and setters
	public int getXcoord(){
		return this.xcoord;
	}
	public int getYcoord(){
		return this.ycoord;
	}

	public Role(Actor actor) {
		this.actor = actor;
		this.role = role;
	}

	public Actor getActor() {  // used to check offcard roles if there is actor on it
		return this.actor;
	}

	public void setActor(Actor actor) {  // ------added
		this.actor = actor;
	}

	/* Role methods */
	public int getRank(){
		return this.requiredrank;
	}

	public String getRole(){
		return this.role;
	}
	

}