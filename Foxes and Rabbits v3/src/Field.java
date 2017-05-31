import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represent a rectangular grid of field positions.
 * Each position is able to store a single animal.
 */
public class Field
{    
    // The depth and width of the field.
    private int depth, width;
    // Storage for the animals.
    private Object[][] field;

    /**
     * Represent a field of the given dimensions.
     * @param depth The depth of the field.
     * @param width The width of the field.
     */
    public Field(int depth, int width) {
        this.depth = depth;
        this.width = width;
        field = new Object[width][depth];
    }
    
    public Field(Field another) {
    	this.depth = another.depth;
    	this.width = another.width;
    	field = new Object[width][depth];
    	for (int r = 0; r < field.length; r++) {
        	for (int c = 0; c < field[r].length; c++) {
        		field[r][c] = another.getObjectAt(r, c);
        	}
        }
    }
    
    /**
     * Empty the field.
     */
    public void clear() {
        for (int r = 0; r < field.length; r++) {
        	for (int c = 0; c < field[r].length; c++) {
        		field[r][c] = null;
        	}
        }
    }
    
    /**
     * Place an animal at the given location.
     * If there is already an animal at the location it will
     * be lost.
     * @param animal The animal to be placed.
     * @param row Row coordinate of the location.
     * @param col Column coordinate of the location.
     */
    public void place(Object animal, int row, int col) {
        Location l = new Location(row, col);
        place(animal,l);
    }
    
    /**
     * Place an animal at the given location.
     * If there is already an animal at the location it will
     * be lost.
     * @param animal The animal to be placed.
     * @param location Where to place the animal.
     */
    public void place(Object animal, Location location) {
        field[location.getRow()][location.getCol()] = animal;
    }
    
    /**
     * Return the animal at the given location, if any.
     * @param location Where in the field.
     * @return The animal at the given location, or null if there is none.
     */
    public Object getObjectAt(Location location) {
        return field[location.getRow()][location.getCol()];
    }
    
    /**
     * Return the animal at the given location, if any.
     * @param row The desired row.
     * @param col The desired column.
     * @return The animal at the given location, or null if there is none.
     */
    public Object getObjectAt(int row, int col) {
        return field[row][col];
    }
    
    /**
     * Generate a shuffled List of locations adjacent
     * to the given one. The list will not include the location itself.
     * All locations will lie within the grid.
     * @param location The location from which to generate adjacencies.
     * @return A list of locations adjacent to that given.
     */
    public List<Location> adjacentLocations(Location location) {
    	List<Location> adj = new ArrayList<>();
    	int r = location.getRow();
    	int c = location.getCol();
    	if (r >= depth || r < 0 || c >= width || c < 0) return null;
    	if (r < depth - 1) adj.add(new Location(r + 1,c));
    	if (r >= 1) adj.add(new Location(r - 1,c));
    	if (c < width - 1) adj.add(new Location(r,c + 1));
    	if (c >= 1) adj.add(new Location(r, c - 1));
    	if (r < depth - 1 && c < width - 1) adj.add(new Location(r + 1, c + 1));
    	if (r < depth - 1 && c >= 1) adj.add(new Location(r + 1, c - 1));
    	if (r >= 1 && c < width - 1) adj.add(new Location(r - 1, c + 1));
    	if (r >= 1 && c >= 1) adj.add(new Location(r - 1, c - 1));
    	Collections.shuffle(adj);
    	return adj;
    }
    
    /**
     * Try to find a free location that is adjacent to the
     * given location. If there is none, then return the current
     * location if it is free. If not, return null.
     * The returned location will be within the valid bounds
     * of the field.
     * @param location The location from which to generate an adjacency.
     * @return A valid location within the grid area. This may be the
     *         same object as the location parameter (if it's free), or null if all
     *         locations around are full.
     */
    public Location freeAdjacentLocation(Location location) {
    	List<Location> adj = adjacentLocations(location);
        Location ans = null;
        for (Location l : adj) {
        	if (field[l.getRow()][l.getCol()] == null) {
        		ans = l;
        		break;
        	}
        }
        if (ans == null && field[location.getRow()][location.getCol()] == null) ans = location;
        return ans;
    }
    
    /**
     * Generate a random location that is adjacent to the
     * given location, or is the same location.
     * The returned location will be within the valid bounds
     * of the field.
     * @param location The location from which to generate an adjacency.
     * @return A valid location within the grid area. This
     *         may be the same object as the location parameter.
     */
    public Location randomAdjacentLocation(Location location) {
    	List<Location> adj = adjacentLocations(location);
    	Random r = new Random();
    	return adj.get(r.nextInt(adj.size()));
    }

    /**
     * Return the depth of the field.
     * @return The depth of the field.
     */
    public int getDepth() {
        return depth;
    }
    
    /**
     * Return the width of the field.
     * @return The width of the field.
     */
    public int getWidth() {
       return width;
    }
    
    @Override
    public String toString() {
    	String ans = "";
    	for (int r = 0; r < width; r++) {
    		for (int c = 0; c < depth; c++) {
    			if (field[r][c] == null) ans += "N ";
    			else ans += "O ";
    		}
    		ans += "\n";
    	}
    	ans += "\n";
    	return ans;
    }
}