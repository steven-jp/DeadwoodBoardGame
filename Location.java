import java.util.ArrayList;


public class Location {
	/*Attributes*/
	private int shotCounter = 0;
	private ArrayList<Role> offcardroles = new ArrayList<Role>(); // returns players acting but not on card //-- offcard roles available change
	private ArrayList<Actor> allplayers = new ArrayList<Actor>();
	private ArrayList<String> neighbors = new ArrayList<String>();
	private ArrayList<take> takes = new ArrayList<take>();
	private ArrayList<upgrade> upgrades = new ArrayList<upgrade>();

	private int MaxShots;
	private Scene scene;
	private String name;          // tell if its the casting office or trailer and such
	private int locNum;
	private int xcoord;
	private int ycoord;

	/*Constructors*/
	public Location(int MaxShots) {
		this.MaxShots = MaxShots;
		this.name = null;
		this.scene = null;	
	}
	public Location(int MaxShots,String name) {
		this.MaxShots = MaxShots;
		this.name = name;
		this.scene = null;
	}

	public Location(int MaxShots,String name,Scene scene) {
		this.MaxShots = MaxShots;
		this.name = name;
		this.scene = scene;
	}
	public Location(String name, ArrayList<String> neighbors) {  /// important
		this.name = name;
		this.neighbors = neighbors;
	}
	public Location(String name,Scene scene, ArrayList<String> neighbors,ArrayList<Role> offcardroles) {  /// important
		this.MaxShots = MaxShots;
		this.name = name;
		this.scene = scene;
		this.neighbors = neighbors;
		this.offcardroles=offcardroles;
	}

	public Location(int MaxShots, String name,Scene scene, ArrayList<String> neighbors,ArrayList<Role> offcardroles, ArrayList<take> takes) {  /// important
		this.MaxShots = MaxShots;
		this.name = name;
		this.scene = scene;
		this.neighbors = neighbors;
		this.offcardroles = offcardroles;
		this.takes = takes;
	}

	//check if a location has a scene return false if it doesn't and true if it does
	public boolean hasScene() {
		if (getScene() != null) {
			return true;
		}
		return false;
	}
	//resets the location's shot counter
	public void resetShotCounter() {
		this.shotCounter = 0;
	}
	//adds a player to the array containing all players at the location
	public void addPlayers(Actor actor) {
		this.allplayers.add(actor);
	}

	/* getters and setters*/
	//removes the specified actor from the actors at the location
	public void removePlayers(Actor actor) {
		this.allplayers.remove(actor);
	}
	//return the adjacent locations of the current location
	public ArrayList<String> getNeighbors(){   
		return this.neighbors;
	}
	public void setXcoord(int xcoord) { // used for card locations
		this.xcoord = xcoord;
	}
	public void setYcoord(int ycoord) {
		this.ycoord = ycoord;
	}
	public int getXcoord() { // used for card locations
		return this.xcoord;
	}
	public int getYcoord() {
		return this.ycoord;
	}

	public ArrayList<take> getTakes() {
		return this.takes;
	}
	public void setUpgrades(ArrayList<upgrade> upgrades) {
		this.upgrades = upgrades;
	}
	public ArrayList<upgrade> getUpgrades() {
		return this.upgrades;
	}

	public void setTakes(ArrayList<take> takes) {
		this.takes = takes;
	}

	public String getName() {
		return this.name;
	}
	public void setLocNum(int locNum) {//loc num is used as an index in arrays and array lists to refer to a specific location
		this.locNum = locNum;
	}
	public int getLocNum(){
		return this.locNum;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Actor> getAllPlayers() {
		return this.allplayers;
	}   
	public ArrayList<Role> getOffCardRoles() {
		return this.offcardroles;
	}
	public void setOffCardRoles(ArrayList<Role> offcardroles) {
		this.offcardroles = offcardroles;
	}
	public void addOffCardRoles(Role role) {
		this.offcardroles.add(role);
	}
	public Scene getScene() {
		return this.scene;
	}
	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public int getMaxShots() {
		return this.MaxShots;
	}
	public int getShotCounter() {
		return this.shotCounter;
	}
	public void setShotCounter() {
		this.shotCounter++;
	}

}
