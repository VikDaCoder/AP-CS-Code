/**
 * Represent a location in a rectangular grid.
 */
public class Location
{
    // Row and column positions.
    private int row;
    private int col;

    /**
     * Represent a row and column.
     * @param row The row.
     * @param col The column.
     */
    public Location(int row, int col) {
    	this.row = row;
    	this.col = col;
    }
    
    /**
     * Implement content equality (how to tell if two Locations are the same)
     */
    @Override
    public boolean equals(Object obj) {
    	int r = ((Location) obj).getRow();
    	int c = ((Location) obj).getCol();
    	return row == r && col == c;
    }
    
    /**
     * Return a string of the form row,column
     * @return A string representation of the location.
     */
    @Override
    public String toString()
    {
        return "<" + row + ", " + col + ">";
    }
    
    /**
     * Use the top 16 bits for the row value and the bottom for
     * the column. Except for very big grids, this should give a
     * unique hash code for each (row, col) pair.
     * @return A hashcode for the location.
     */
    public int hashCode(){
        
        return (row << 16) + col;  //don't mess with this
    }
    
    /**
     * @return The row.
     */
    public int getRow(){
        return row;
    }
    
    /**
     * @return The column.
     */
    public int getCol(){
        return col;
    }
}