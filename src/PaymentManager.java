import java.util.Arrays;
import java.util.ArrayList;

public class PaymentManager {
	private static PaymentManager pm = null;
	/* takes in an actor and updates their values */
	private void PaymentManager() {
	}
	public static PaymentManager getInstance(){
		if (pm == null){
			pm = new PaymentManager();
		}
		return pm;

	}
	public void upgradeRank(Actor actor,int rank){
		// change actor's rank value to new value
		actor.setRank(rank);
		System.out.println(actor.getRank());
	}
	//this method gets called after a player performs an act action to update the player's currency
	public void updateCurrency(Actor actor, int state, int success){  // on/offcard and if it was success/failure
		int playermoney = actor.getMoney();
		int playerfame = actor.getFame();

		if (state == 1) { // oncard
			if (success == 1){ // succeed
				playerfame += 2;
				actor.setFame(playerfame);
			}//on card failure yields no update to currency

		}
		else {            //offcard
			if (success == 1){ // succeed
				playerfame++;
				playermoney++;
				actor.setFame(playerfame);
				actor.setMoney(playermoney);
			}
			else { // failure
				playermoney++;
				actor.setMoney(playermoney);
			}
		}  
	}


	//upon scene completion wrapbonus is called to dole out the scene wrapping bonus at a location
	public void wrapbonus(Location location){			
		int onacard = 0;
		for (Role r : location.getScene().getoncardroles()) {
			if (r.getActor() != null) {
				onacard++;
			}
		}
		if (onacard > 0) {
			/* pay people offcard*/
			ArrayList<Role> roles = location.getOffCardRoles();
			int rolecount = roles.size()-1;
			int playermoney;
			Actor actor;
			while (rolecount >= 0) {
				actor = roles.get(rolecount).getActor();
				if (actor != null) { // there's a player on the role, get their money, add rank value to it, set new value
					playermoney = actor.getMoney();
					/*test */
					System.out.println(playermoney);
					playermoney = playermoney + roles.get(rolecount).getRank();
					actor.setMoney(playermoney);
					/*test */
					System.out.println(playermoney);
					System.out.println("Role : " + roles.get(rolecount).getRole());
					System.out.println("------");
				}
				rolecount--;
			}


			roles = location.getScene().getoncardroles();
			rolecount = roles.size();


			System.out.println("oncard start");

			/* pay people oncard*/
			int budget = location.getScene().getBudget();
			int[] dicevalues = new int[budget]; // keeps track of adding values for dice roles.
			budget--;
			while (budget >= 0) {
				dicevalues[budget] = rollDice();
				budget--;
			}
			Arrays.sort(dicevalues);

			/* test */
			System.out.println(Arrays.toString(dicevalues));


			int[] rolebonuses = new int[rolecount];
			budget = location.getScene().getBudget();// must add the roles to scene in order for on card roles !!!!!!!!
			int rolecount_cpy = rolecount;
			while (budget != 0) {
				if (rolecount_cpy == 0) {    // set values again starting from highest role
					rolecount_cpy = rolecount;
				}

				rolebonuses[rolecount_cpy-1] = rolebonuses[rolecount_cpy-1] + dicevalues[budget-1]; 


				budget--;
				rolecount_cpy--;
			}

			/* test */
			System.out.println(Arrays.toString(rolebonuses));

			rolecount_cpy = rolecount - 1;

			while (rolecount_cpy >= 0) {
				actor = roles.get(rolecount_cpy).getActor();
				if (actor != null) { // there's a player on the role, get their money, add rank value to it, set new value
					playermoney = actor.getMoney();
					/* test */
					System.out.println(playermoney);

					playermoney = playermoney + rolebonuses[rolecount_cpy];
					actor.setMoney(playermoney);
					/* test */
					System.out.println(playermoney);
					System.out.println("Role : " + roles.get(rolecount_cpy).getRole());
					System.out.println("------");

				}
				rolecount_cpy--;
			}
		} 
	} 
	//simulates a dice roll
	private int rollDice() {
		int dice = (int)(Math.random() * 6) + 1;
		return dice;
	}


}
