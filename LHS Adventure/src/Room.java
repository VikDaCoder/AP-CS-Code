import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Room 
{
    private String description;
    private HashMap<String,Room> exits;
    private ArrayList<Item> inRoom;
    public Room(String desc) {
        description = desc;
        exits = new HashMap<>();
        inRoom = new ArrayList<>();
    }	
    public void setExits(String direction, Room neighbor) {
        exits.put(direction.toLowerCase(), neighbor);
    }
    public String getExitString() {
    	String result = "";
    	Set<String> keys = exits.keySet();       
    	for (String dir : keys) {	
    		result += " " + dir;         
    	}         
    	return result;
    }
    public Room getExit(String direction) {
    	return exits.get(direction);
    }
    public String getDescription() {return description;}
    public void takeItem(Item i) {inRoom.remove(i);}
    public void addItem(Item i) {inRoom.add(i);}
    public ArrayList<Item> getItems() {return inRoom;}
    public String roomItemsToString() {
    	String ans = "";
    	for (Item i : inRoom) {
    		ans += i.getDescription() + ", ";
    	}
    	if (ans.length() > 0) return ans.substring(0,ans.length() - 2);
    	return "[]";
    }
}