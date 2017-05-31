import java.io.IOException;
 
public class MazeRunner {
    public static void main(String args[]) throws IOException {
    	Maze zero = new Maze("maze0.txt");
    	Maze one = new Maze("maze1.txt");
        Maze two = new Maze("maze2.txt");
        Maze three = new Maze("maze3.txt");
        Maze four = new Maze("maze4.txt");
        Maze five = new Maze("maze5.txt");
        zero.solve();
        one.solve();
        two.solve();
        three.solve();
        four.solve();
        five.solve();
    }
}