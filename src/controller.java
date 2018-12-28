import java.util.Scanner;
import java.lang.StringBuilder;

public class controller {

	/* Attributes */
	private Actor actor;
	private board board;
	private int endturn = 0;
	private view2 v;
	static String end = "notend";

	/* Constructor */
	public controller() {
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String s) {
		end = s;
	}

	public void playerTurn(Actor actor, board b) {
		this.actor = actor;
		this.board = b;
	}

	public void actions() {
		StringBuilder sb = new StringBuilder();
		sb.append(" Acceptable commands are:");
		sb.append("       who");
		sb.append("       where");
		sb.append("       move  room");
		sb.append("       work part");
		sb.append("       upgrade $ level");
		sb.append("       upgrade cr level");
		sb.append("       rehearse");
		sb.append("       act");
		sb.append("       help");
		sb.append("       neighbors");
		sb.append("       end");
		System.out.println(sb.toString());
	}

	public String who(Actor name) {
		int onacard = 0;
		String s = "";
		java.lang.StringBuilder who = new java.lang.StringBuilder();
		who.append(name.getName());
		who.append("($");
		who.append(name.getMoney());
		who.append(",");
		who.append(name.getFame());
		who.append("cr) Rank: ");
		who.append(name.getRank());
		Location L = name.getLocation();
		if (L.getName().equals("office") || L.getName().equals("trailer")) {
			;
		} else {
			for (Role r : L.getOffCardRoles()) {
				if (r.getActor() == actor) {
					s = r.getRole();
					onacard++;
				}
			}
			for (Role r : L.getScene().getoncardroles()) {
				if (r.getActor() == actor) {
					s = r.getRole();
					onacard++;
				}
			}
		}

		if (onacard > 0) {
			who.append(". Working as " + s);
		}
		who.append(".");
		return who.toString();

	}

	public String where(Actor name) {
		java.lang.StringBuilder where = new java.lang.StringBuilder();
		where.append(name.getLocation().getName());
		return where.toString();
	}

	public int move(String loc) {
		// we call rehearse if the actor tries to move to the location they are on
		if (loc.equals(actor.getLocation().getName())) {
			return rehearse();
		} else {
			for (String s : actor.getLocation().getNeighbors()) { // check if the argument is in the list of neighbors
				if (loc.toLowerCase().equals(s.toLowerCase())) {
					Location L = actor.getLocation();
					// check if the player is in a role already
					int onacard = 0;
					// trailer and office don't have roles so we avoid nullpointers by checking for
					// them
					if (L.getName().equals("office") || L.getName().equals("trailer")) {// do nothing
					} else {// iterate through the list of off card then on card roles to see if the actor
							// is in a role
						onacard = 0;
						for (Role r : L.getOffCardRoles()) {
							if (r.getActor() == actor) {
								onacard++;// actor found
							}
						}
						for (Role r : L.getScene().getoncardroles()) {
							if (r.getActor() == actor) {
								onacard++;// actor found
							}
						}
					}
					if (onacard == 0) { // if not in a role move update model and view
						actor.setLocation(board.getLocation(s.toLowerCase()));
						v.addPlayerDice(actor.getPlayerNum(),
								actor.getLocation().getXcoord() + (10 * actor.getLocation().getAllPlayers().size()),
								actor.getLocation().getYcoord());
						L.removePlayers(actor);
						board.getLocation(loc).addPlayers(actor);
						actor.setRehearsalCounter(0);
						if (board.getLocation(s.toLowerCase()).hasScene() && !board.getLocation(s.toLowerCase()).getScene().isRevealed()) {
							board.getLocation(s.toLowerCase()).getScene().revealScene();
							v.revealCard(board.getLocation(s.toLowerCase()),board.getLocation(s.toLowerCase()).getScene().getImgName());
						}
						return 1;//successful move action so return 1
					} else {// actor is in a role
						System.out.println("You cannot move from a role");

					}
				}
			}
		}
		return 0;//unsuccessful move so return 0
	}

	public int move(String loc, String loc2) {

		// put the two arguments into one string
		String[] arr;
		StringBuilder sb = new StringBuilder();
		sb.append(loc + " " + loc2);
		// we call rehearse if the actor tries to move to the location they are on
		if (actor.getLocation().getName().toLowerCase().equals(sb.toString().toLowerCase())) {
			return rehearse();
		} else {// check if the player is in a role already
				// the argument is two words we need to compare those two words to our neighbor locations
			for (String s : this.actor.getLocation().getNeighbors()) {
				arr = s.toLowerCase().split(" ");
				if (loc.toLowerCase().equals(arr[0])) {//check word one for equality
					if (loc2.toLowerCase().equals(arr[1])) {//check word two for equality
						Location L = actor.getLocation();

						int onacard = 0;
						// trailer and office don't have roles so we avoid nullpointers by checking for them
						if (L.getName().equals("office") || L.getName().equals("trailer")) {
							//do nothing							
						} else {//check if the player is in a role
							onacard = 0;
							for (Role r : L.getOffCardRoles()) {
								if (r.getActor() == actor) {
									onacard++;// actor found in role
								}
							}
							for (Role r : L.getScene().getoncardroles()) {
								if (r.getActor() == actor) {
									onacard++;// actor found in role
								}
							}
						}
						if (onacard == 0) { // if not in a role move and update the model and view
							this.actor.setLocation(board.getLocation(s.toLowerCase()));
							v.addPlayerDice(this.actor.getPlayerNum(),
									actor.getLocation().getXcoord() + (10 * actor.getLocation().getAllPlayers().size()),
									actor.getLocation().getYcoord());
							L.removePlayers(this.actor);
							board.getLocation(s.toLowerCase()).addPlayers(this.actor);
							this.actor.setRehearsalCounter(0);
							// if the location hasn't been visited flip the scene card
							if (board.getLocation(s.toLowerCase()).hasScene()&& !board.getLocation(s.toLowerCase()).getScene().isRevealed()) {
								board.getLocation(s.toLowerCase()).getScene().revealScene();
								v.revealCard(board.getLocation(s.toLowerCase()),
								board.getLocation(s.toLowerCase()).getScene().getImgName());
							}
							return 1;//return 1 because successful move
						}
						
					} 
					else {//player found in role so the player can't move
						System.out.println("You cannot move from a role");
					}
				}
			}
		}
		return 0;//return 0 because unsuccessful
	}
	//command line hold over that displayed possible moves
	public void move() {
		StringBuilder sb = new StringBuilder();
		sb.append("Possible Areas To Move To.");
		for (String s : actor.getLocation().getNeighbors()) {
			sb.append("      " + s);
		}

	}

	public int work(String part) {
		// trailer and office don't have roles so we avoid nullpointers by checking for them
		if (actor.getLocation().getName().equals("trailer") || actor.getLocation().getName().equals("office")) {
			System.out.println("You can't work here."); return 0;
		}

		else {//check if scene is active
			if (actor.getLocation().getScene().isActive()) {
				//if scene is active it has on and offcard roles we can check to see if the actor is in a role
				int onacard = 0;
				for (Role r : actor.getLocation().getOffCardRoles()) {
					if (r.getActor() == actor) {
						onacard++;
					}
				}
				for (Role r : actor.getLocation().getScene().getoncardroles()) {
					if (r.getActor() == actor) {
						onacard++;
					}
				}//if actor isn't in role they can take an on card role 
				if (onacard == 0) {
					boolean roleFound = false;
					for (Role role : actor.getLocation().getScene().getoncardroles()) {
						if (part.toLowerCase().equals(role.getRole().toLowerCase())) {
							//role is found, try to add the actor to the role
							roleFound = true;
							if (actor.getRank() >= role.getRank() && role.slotAvailability() == true) {
								//the actor can take the role so update the model and view
								role.setActor(actor);
								v.addPlayerDice(actor.getPlayerNum(),
										actor.getLocation().getXcoord() + role.getXcoord(),
										actor.getLocation().getYcoord() + role.getYcoord());
								System.out.println(role.getActor().getName() + " is now acting as " + role.getRole());
								endturn = 1;
								return 1;
							} else if (role.slotAvailability() == false) {//role is taken
								System.out.println(role.getActor().getName() + " has already taken this role.");
								roleFound = false;
							} else {//player doesn't have high enough rank to take role
								roleFound = false;
								System.out.println(
										actor.getName() + " isn't high enough rank to take " + role.getRole() + ".");
							}

							break;
						}
					}//if actor isn't in role yet they can take an off card role 
					for (Role role : actor.getLocation().getOffCardRoles()) {
						if (part.toLowerCase().equals(role.getRole().toLowerCase())) {
							//role is found, try to add the actor to the role
							roleFound = true;
							if (actor.getRank() >= role.getRank() && role.slotAvailability() == true) {
								//the actor can take the role so update the model and view
								role.setActor(actor);
								v.addPlayerDice(actor.getPlayerNum(), role.getXcoord(), role.getYcoord());
								System.out.println(role.getActor().getName() + " is now acting as " + role.getRole());
								endturn = 1;
								return 1;
							} else if (role.slotAvailability() == false) {
								System.out.println(role.getActor().getName() + " has already taken this role.");
								roleFound = false;
							} else {
								roleFound = false;
								System.out.println(
										actor.getName() + " isn't high enough rank to take " + role.getRole() + ".");
							}

							break;
						}
					}
					if (!roleFound) {//if the role isn't found the player has tried to work at a different location
						System.out.println(part + " Role Not Found at " + actor.getLocation().getName() + ".");
					}
				} else {//the player already has a role and cannot take another
					System.out.println("You cannot take another role");
				}
			}

			else {//the scene isn't active and cannot have a part taken
				System.out.println("The Scene at " + actor.getLocation().getName() + " is longer active.");
			}
		}
		return 0;

	}
	//command line hold over that printed the available roles to take
	public void work() {
		if (actor.getLocation().getName().equals("trailer") || actor.getLocation().getName().equals("office")) {
			System.out.println("You can't work here.");
		}

		else {
			if (actor.getLocation().getScene().isActive()) {
				// System.out.println(actor.getLocation().getScene().getName() + " budget $"+
				// actor.getLocation().getScene().getBudget());
				// System.out.println("On Card (Starring) Roles:");
				for (Role role : actor.getLocation().getScene().getoncardroles()) {

					if (role.slotAvailability() == false) {
						;
					} else {
						System.out.print(role.getRole());
						System.out.print(" Requires rank ");
						System.out.print(role.getRank());
						System.out.print(".");
					}
					// System.out.println("");
				}
				// System.out.println("Off Card (Extra) Roles:");
				for (Role role : actor.getLocation().getOffCardRoles()) {
					if (role.slotAvailability() == false) {
						;
					} else {
						System.out.print(role.getRole());
						System.out.print(" Requires rank ");
						System.out.print(role.getRank());
						System.out.print(".");
					}
					// System.out.println("");
				}
			} else {
				// System.out.println("The Scene is no longer active.");
			}
		}
	}
	//command line hold over that handled acting
	public void upgrade(int credit, int rank) {
		System.out.println("Upgrade with fame or money?");
		Scanner scan = new Scanner(System.in);
		String arg = scan.next();
		System.out.println(arg);
		if (arg.toLowerCase().equals("fame")) {
			fameUpgrade(rank);
		} else if (arg.equals("money")) {
			dollarUpgrade(rank);
		} else {
			System.out.println("unrecognized argument in upgrade. enter fame or money");
			upgrade(credit, rank);
		}

	}
	//upgrade the player's rank to the given int lvl using money
	public int dollarUpgrade(int lvl) {
		int money = this.actor.getMoney();//get the actor's money to use in the method
		if (this.actor.getLocation().getName().equals("office")) {//check if the actor is in the office
			//see if the actor has enough money to upgrade to the given lvl 
			//if true update the model and view, then end turn and return success
			if (lvl == 6 && money >= 40) {
				this.actor.setRank(lvl);
				this.actor.setMoney(this.actor.getMoney() - 40);
				v.addPlayerDice(actor.getPlayerNum(), lvl, actor.getDiceColor(), this.actor.getLocation());
				endturn = 1;
				return 1;
			} else if (lvl == 5 && money >= 28 && actor.getRank() < lvl) {
				this.actor.setRank(lvl);
				this.actor.setMoney(this.actor.getMoney() - 28);
				v.addPlayerDice(actor.getPlayerNum(), lvl, actor.getDiceColor(), this.actor.getLocation());
				endturn = 1;
				return 1;
			} else if (lvl == 4 && money >= 18 && actor.getRank() < lvl) {
				this.actor.setRank(lvl);
				this.actor.setMoney(this.actor.getMoney() - 18);
				v.addPlayerDice(actor.getPlayerNum(), lvl, actor.getDiceColor(), this.actor.getLocation());
				endturn = 1;
				return 1;
			} else if (lvl == 3 && money >= 10 && actor.getRank() < lvl) {
				this.actor.setRank(lvl);
				this.actor.setMoney(this.actor.getMoney() - 10);
				v.addPlayerDice(actor.getPlayerNum(), lvl, actor.getDiceColor(), this.actor.getLocation());
				endturn = 1;
				return 1;
			} else if (lvl == 2 && money >= 3 && actor.getRank() < lvl) {
				this.actor.setRank(lvl);
				this.actor.setMoney(this.actor.getMoney() - 3);
				v.addPlayerDice(actor.getPlayerNum(), lvl, actor.getDiceColor(), this.actor.getLocation());
				endturn = 1;
				return 1;
			} else if (actor.getRank() >= lvl) {
				System.out.println("Can't upgrade to equal or lesser rank");
			} 
			else {//the actor doesn't have enough money
				System.out.println("Insufficient funds for upgrade.");
			}
		} else {//the actor isn't in the casting office
			System.out.println("Must be in casting office to upgrade rank.");
		}
		return 0;//we didn't return a success so we must have failed
	}
	//upgrade the player's rank to the given int lvl using money
	public int fameUpgrade(int lvl) {
		int fame = this.actor.getFame();//get the actor's fame to use in the method
		if (this.actor.getLocation().getName().equals("office")) {//make sure player is in office
			//try to upgrade to the given level using the actor's fame
			//if true update the view and model then set endturn and return success
			if (lvl == 6 && fame >= 25 && actor.getRank() < lvl) {
				this.actor.setRank(lvl);
				this.actor.setFame(this.actor.getFame() - 25);
				v.addPlayerDice(actor.getPlayerNum(), lvl, actor.getDiceColor(), this.actor.getLocation());
				endturn = 1;
				return 1;
			} else if (lvl == 5 && fame >= 20 && actor.getRank() < lvl) {
				this.actor.setRank(lvl);
				this.actor.setFame(this.actor.getFame() - 20);
				v.addPlayerDice(actor.getPlayerNum(), lvl, actor.getDiceColor(), this.actor.getLocation());
				endturn = 1;
				return 1;
			} else if (lvl == 3 && fame >= 15 && actor.getRank() < lvl) {
				this.actor.setRank(lvl);
				this.actor.setFame(this.actor.getFame() - 15);
				v.addPlayerDice(actor.getPlayerNum(), lvl, actor.getDiceColor(), this.actor.getLocation());
				endturn = 1;
				return 1;
			} else if (lvl == 3 && fame >= 10 && actor.getRank() < lvl) {
				this.actor.setRank(lvl);
				this.actor.setFame(this.actor.getFame() - 10);
				v.addPlayerDice(actor.getPlayerNum(), lvl, actor.getDiceColor(), this.actor.getLocation());
				endturn = 1;
				return 1;
			} else if (lvl == 2 && fame >= 5 && actor.getRank() < lvl) {
				this.actor.setRank(lvl);
				this.actor.setFame(this.actor.getFame() - 5);
				v.addPlayerDice(actor.getPlayerNum(), lvl, actor.getDiceColor(), this.actor.getLocation());
				endturn = 1;
				return 1;
			} else if (actor.getRank() >= lvl) {
				System.out.println("Can't upgrade to equal or lesser rank");
			} 
			else {
				System.out.println("Insufficient fame for upgrade.");
			}
		} 
		else {
			System.out.println("Must be in casting office to upgrade rank.");
		}
		return 0;//didn't return success so we must have failed
	}

	//perform the rehearse action for a player
	public int rehearse() {
		// trailer and office don't have roles so we avoid nullpointers by checking for them
		if (actor.getLocation().getName().equals("trailer") || actor.getLocation().getName().equals("office")) {
			System.out.println("You cannot work here");//you can't rehearse at the office or trailer
		} else {
			//check if the player is in a role
			if (actor.getLocation().getScene().isActive()) {
				int onacard = 0;
				for (Role r : actor.getLocation().getOffCardRoles()) {
					if (r.getActor() == actor) {
						onacard++;//actor found in role
					}
				}
				for (Role r : actor.getLocation().getScene().getoncardroles()) {
					if (r.getActor() == actor) {
						onacard++;//actor found in role
					}
				}
				if (onacard > 0) {//if the player is in a role update the endturn,model and view 
					//then return 1 for successful action
					this.actor.setRehearsalCounter(this.actor.getRehearsalCounter() + 1);
					System.out.println(this.actor.getName() + " has " + this.actor.getRehearsalCounter()
							+ " rehearsal counter(s) for current Scene.");
					endturn = 1;
					return 1;
				} else {//player wasn't found in a role
					System.out.println("You cannot rehearse without a role");
				}
			} else {//the scene is wrapped
				System.out.println("The Scene is no longer active.");
			}
		}
		return 0;//we didn't return a success so we must return failure
	}

	public int act() {
		// call act from turn class
		int onacard = 0;
		// trailer and office don't have roles so we avoid nullpointers by checking for them
		if (actor.getLocation().getName().equals("trailer") || actor.getLocation().getName().equals("office")) {
			System.out.println("You cannot work here");//can't act at the office or trailer
		} else {
			//check if the actor is in a role
			if (actor.getLocation().getScene().isActive()) {
				boolean actorFound = false;
				for (Role r : actor.getLocation().getOffCardRoles()) {
					if (r.getActor() == actor) {
						onacard++;//actor found in role
					}
				}
				for (Role r : actor.getLocation().getScene().getoncardroles()) {
					if (r.getActor() == actor) {
						onacard++;//actor found in role
					}
				}
				if (onacard > 0) {//player found in role so perform act action
					int roll = actRoll(actor);//roll the die
					//get an instance of paymentmanager to handle payment
					PaymentManager pm = PaymentManager.getInstance();
					if (actor.getLocation().getScene().getBudget() > roll) {//check the roll vs the budget of the scene
						System.out.println("Unsuccessful take." + actor.getName() + " rolled a " + roll + ".");
						System.out.println(actor.getLocation().getMaxShots() - actor.getLocation().getShotCounter()
								+ " shots left.");
						//check if actor found on card
						for (Role r : actor.getLocation().getScene().getoncardroles()) {
							if (r.getActor() != null) {
								if (r.getActor().equals(actor)) {
									actorFound = true;//actor found in on card role
								}
							}
						}
						if (actorFound) {// on card roll failure get the currencyUpdate from paymentManager
							pm.updateCurrency(actor, 1, 0);							
						} else {// off card roll failure get the currencyUpdate from paymentManager
							pm.updateCurrency(actor, 0, 0);
						}

					} else {//successful act roll update model and view and check if scene is wrapped
						v.addTakesShots(actor.getLocation(),
						actor.getLocation().getMaxShots() - actor.getLocation().getShotCounter() - 1);
						actor.getLocation().setShotCounter();
						System.out.println("Succesful take!" + actor.getName() + " rolled a " + roll + ".");
						// check if scene is done
						//search for actor in on card role
						for (Role r : actor.getLocation().getScene().getoncardroles()) {
							if (r.getActor() != null) {//avoid nullpointer
								if (r.getActor().equals(actor)) {
									actorFound = true;
								}
							}
						}
						if (actorFound) {	//on card successful roll get the currencyUpdate from paymentManager
							pm.updateCurrency(actor, 1, 1);
						} else {	//off card successful roll get the currencyUpdate from paymentManager
							pm.updateCurrency(actor, 0, 1);
						}
						//check if the successful role wrapped the scene update the view and model accordingly if true
						//also get the wrap bonus from paymentManager
						if (actor.getLocation().getShotCounter() == actor.getLocation().getMaxShots()) {
							pm.wrapbonus(actor.getLocation());
							actor.getLocation().getScene().wrapscene();
							v.delOffCardRoles(actor.getLocation().getLocNum());
							for (Role r : actor.getLocation().getOffCardRoles()) {
								r.setActor(null);
							}
							v.cardWrap(actor.getLocation());
						}
					}
					endturn = 1;//the act action was performed so end the turn and return a completion of the action
					return 1;
				} else {
					System.out.println("You cannot act without a role");
				}
			} else {
				System.out.println("The Scene is no longer active.");
			}
		}
		return 0;//we didn't return a successful completion of the action so we must've failed to complete the action
	}

	//prompt view2 to ask how many players there are
	public int calcPlayers() {
		return v.calcPlayers2();
	}
	//add an instance of view2 to the class attribute v
	public void addView(view2 v) {
		this.v = v;
	}
	//our dice roll simulation
	private int actRoll(Actor a) {
		int dice = (int) (Math.random() * 6) + 1 + a.getRehearsalCounter();
		return dice;
	}
}