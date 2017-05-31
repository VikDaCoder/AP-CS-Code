import java.io.File;
import java.io.IOException;
import java.util.Scanner;
 
public class Maze
{
    private int[][] maze;
    private int[][] copy;
    private boolean exitFound;
 
    /** Make sure to look at the format of the mazes in the maze text files */
    public Maze(String fileName) throws IOException
    {
        Scanner in = new Scanner(new File(fileName)); //for getting next int from the maze text file
        int dim = in.nextInt();
        in.nextLine();
        maze = new int[dim][dim];
        for (int r = 0; r < maze.length; r++) {
        	for (int c = 0; c < maze[r].length; c++) {
        		maze[r][c] = in.nextInt();
        	}
        }
        copy = maze;
        in.close();
    }
     
    /**
     * Helper method, makes the interface a little cleaner
     * 
     * The user doesn't have to know the solver should start at 0, 0
     * 
     * Also responsible for printing the final state of the maze
     */
    public void solve() {				
        System.out.println(this);
        this.hasExitPath(0, 0, 1);
        if (!exitFound) System.out.println("No Exit Found\n\n");
    }
 
    public void hasExitPath(int r, int c, int steps) {
        if (r < maze.length && r >= 0 && c < maze.length && c >= 0 && maze[r][c] == 1) {
        	copy[r][c] = 2;
        	if (c == maze.length - 1) {
        		exitFound = true;
        		System.out.println("Number of steps >>>" + steps + "\n\n");
        	}
        	else {
        		this.hasExitPath(r, c + 1, steps + 1);
        		this.hasExitPath(r - 1, c, steps + 1);
        		this.hasExitPath(r + 1, c, steps + 1);
        		this.hasExitPath(r, c - 1, steps + 1);
        	}
        }
        else return;
    }
 
    public String toString()
    {
        String output = "";
        for (int r = 0; r < maze.length; r++) {
        	for (int c = 0; c < maze[r].length; c++) {
        		output += "" + maze[r][c] + " ";
        	}
        	output += "\n";
        }
        return output;
    }
}