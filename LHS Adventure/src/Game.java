import java.util.*;
 
public class Game 
{
	Player player;
    private Scanner in;
     
    public Game() {
        in = new Scanner(System.in);
        player = new Player();
        createRooms();
    }
     
    /** Creates Room objects and sets exits to create the game world */
    private void createRooms()
    {
        //create Rooms
        Room rotunda = new Room("A large, echoing rotunda");
        Room principalsOffice = new Room("The principal's office");
        Room entryWay = new Room("School entryway");
        Room outside = new Room("Outside Liberty HS");
        Room classRoom = new Room("Hallways for most classes");
        Room cafeteria = new Room("School cafeteria");
        Room band = new Room("Band Hallway");
        Item book = new Item("book" , 5);
        Item laptop = new Item("laptop", 13);
        Item tray = new Item("tray",15);
        Item binder = new Item("binder", 9);
        Item saxophone = new Item("saxophone", 24);
        Item chair = new Item("chair", 17);
        Item table = new Item("Table" , 20);
        //set exits for Rooms
        outside.setExits("north",entryWay);
        entryWay.setExits("north",rotunda);
        entryWay.setExits("south",outside);
        entryWay.setExits("east",principalsOffice);
        principalsOffice.setExits("west", entryWay);
        rotunda.setExits("south", entryWay);
        rotunda.setExits("north", cafeteria);
        rotunda.setExits("west", classRoom);
        rotunda.setExits("east", band);
        classRoom.setExits("east", rotunda);
        cafeteria.setExits("south", rotunda);
        band.setExits("west", rotunda);
        entryWay.addItem(laptop);
        principalsOffice.addItem(chair);
        rotunda.addItem(table);
        classRoom.addItem(binder);
        classRoom.addItem(book);
        cafeteria.addItem(tray);
        band.addItem(saxophone);
        //initialize game's starting room
        player.setCurrentRoom(outside);
    }
     
    private void printWelcome()
    {
        System.out.println("Welcome to LHS adventure!");
        System.out.println("At the moment, it's a very boring adventure");
        System.out.println("There are items stored around the school.");
        System.out.println("Every Item has its own weight. YOU NEED A WEIGHT OF 46 TO WIN!");
        System.out.println("Type 'help' if you need help\n");
        System.out.println("You are here >>> " + player.getCurrentRoom().getDescription());
    }
     
    private void printHelp()
    {
        System.out.println("You are alone, you are lost.");
        System.out.println("You wander around LHS.");
        System.out.println("Your commands are >>> " + Command.getCommandsString());
    }
     
    /** Takes a Command object and attempts to process it as a game command */
    private boolean processCommand(Command command) {
    	boolean finished = false;
        if (command.isUnknown()){
        	System.out.println("I don't know what you mean...");
        	return false;
        }
        String commandWord = command.getCommandWord();
        if (commandWord.equalsIgnoreCase("help")) {
        	printHelp();
        }
        if (commandWord.equalsIgnoreCase("go")) {
        	goRoom(command);
        }
        if (commandWord.equalsIgnoreCase("quit")) {
        	finished = quit(command);
        }
        if (commandWord.equalsIgnoreCase("back")) {
        	goBack();
        }
        if (commandWord.equalsIgnoreCase("take")) {
        	take(command);
        }
        if (commandWord.equalsIgnoreCase("drop")) {
        	drop(command);
        }
        return finished;
    }
     
    /** Attempts to move the player to another Room based on their Command */
    private void goRoom(Command command) {
        if (command.getSecondWord() == null){
        	System.out.println("Go where?"); 	
        }
        else {
        	String direction = command.getSecondWord();
        	if (direction.equalsIgnoreCase("back")) goBack();
        	Room nextRoom = null;
        	nextRoom = player.getCurrentRoom().getExit(direction.toLowerCase());
        	if (nextRoom == null && !direction.equalsIgnoreCase("back")) System.out.println("There is no door!");
        	else if (!direction.equalsIgnoreCase("back")){
        		player.setCurrentRoom(nextRoom);
        		System.out.println("\nYou are here >>> " + player.getCurrentRoom().getDescription());
        	}
        }
        	
    }
     
    /** Returns true if the user is wanting to quit */
    private boolean quit(Command command) {
        if (command.getSecondWord() != null) {
        	System.out.println("Quit what?");
        	return false;
        }
        System.out.println("Thanks for playing, goobbye.");
        return true;
    }
    
    public void goBack() {
    	player.setCurrentRoom(player.getPreviousRoom());
    	System.out.println("You are here >>> " + player.getCurrentRoom().getDescription());
    }
    public void drop(Command c) {
    	String item = c.getSecondWord();
    	if (item == null) System.out.println("Drop what?");
    	ArrayList<Item> items = player.getPlayerItems();
    	boolean hasItem = false;
    	Item toRemove = null;
    	for (Item i : items) {
    		if (item.equalsIgnoreCase(i.getDescription())){
    			hasItem = true;
    			toRemove = i;
    		}
    	}
    	if (hasItem) {
    		player.getCurrentRoom().addItem(toRemove);
    		player.dropItem(toRemove);
    	}
    	else System.out.println("That item is not in this room!");
    }
    public void take(Command c) {
    	String item = c.getSecondWord();
    	if (item == null) System.out.println("Take what?");
    	ArrayList<Item> items = player.getCurrentRoom().getItems();
    	boolean hasItem = false;
    	Item toRemove = null;
    	for (Item i : items) {
    		if (item.equalsIgnoreCase(i.getDescription())){
    			hasItem = true;
    			toRemove = i;
    		}
    	}
    	if (hasItem) {
    		player.getCurrentRoom().takeItem(toRemove);
    		player.takeItem(toRemove);
    	}
    	else System.out.println("That item is not in this room!");
    }
    /** This method starts the game, continuing until the user wants to quit */
    public void play() {
        printWelcome();
        boolean finished = false;
        while(!finished) {
        	if (player.getWeight() == 46) {
        		System.out.println("Congratulations! You have achieved a weight of 46.");
        		break;
        	}
        	System.out.println("\nThese are your exits >>> " + player.getCurrentRoom().getExitString());
        	System.out.println("These are the items in this Room >>> " + player.getCurrentRoom().roomItemsToString());
        	System.out.println("These are items on you >>>" + player.playerItemsToString());
        	System.out.print("This is your current weight >>>" + player.getWeight() + "\n>");
        	String line = in.nextLine();
        	Scanner tokenizer = new Scanner(line);
        	String command = null,second = null;
        	if (tokenizer.hasNext()) command = tokenizer.next();
        	if (tokenizer.hasNext()) second = tokenizer.next();
        	Command c = new Command(command,second);
        	finished = processCommand(c);
        }
    }
}