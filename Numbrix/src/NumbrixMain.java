import java.io.FileNotFoundException;
 
public class NumbrixMain
{
    /**
     * Constructs a Numbrix object and uses it to solve a Numbrix puzzle.
     * @throws FileNotFoundException when fileName file does not exist.
     */
    public static void main(String[] args) throws FileNotFoundException
    {
        Numbrix puzzle = new Numbrix("Data.txt");
        System.out.println(puzzle);
        puzzle.solve();
    }
}
