import java.util.Scanner;
import java.util.Random;

public class gameManager{
	/* Attributes */
	private int numPlayers;
	private int dayCount;
	private int firstPlayer;
	private boolean game;

	/*Constructor */
	public gameManager(int numPlayers){
		this.game = true;
		this.numPlayers = numPlayers;
		firstPlayer();
	}

	/* Sets the firstplayer to deadwood */
	private int firstPlayer() {
		int randnum = 0 + (int)(Math.random() * (((this.numPlayers-1) - 0) + 1));
		this.firstPlayer = randnum;
		return this.firstPlayer;
	}
	/* Gets the first player */
	public int getfirstPlayer() {
		return this.firstPlayer;
	}

	//this method ends the game after dayManager tells the class the fianl day is over
	public void endGame(){
		this.game = false;
	}
	// To check whether game is over
	public boolean getGameOver(){
		return this.game;
	} 
	// Returns the number of players
	public int getPlayers(){
		return this.numPlayers;
	}

}