import java.util.ArrayList;

public class Player {
	private Room currentRoom;
	private Room previousRoom;
	ArrayList<Item> onPlayer;
	int extraWeight;
	public Player() {
		onPlayer = new ArrayList<>();
		extraWeight = 0;
	}
	public Room getCurrentRoom() {return currentRoom;}
	public Room getPreviousRoom() {return previousRoom;}
	public void setCurrentRoom(Room n) {
		if (currentRoom != null) previousRoom = currentRoom;
		currentRoom = n;
	}
	public void dropItem(Item i) {
		onPlayer.remove(i);
		extraWeight -= i.getWeight();
	}
	public void takeItem(Item i) {
		onPlayer.add(i);
		extraWeight += i.getWeight();
	}
	public ArrayList<Item> getPlayerItems() {return onPlayer;}
	public int getWeight() {return extraWeight;}
	public String playerItemsToString() {
    	String ans = "";
    	for (Item i : onPlayer) {
    		ans += i.getDescription() + ", ";
    	}
    	if (ans.length() > 0) return ans.substring(0,ans.length() - 2);
    	return "[]";
    }
}