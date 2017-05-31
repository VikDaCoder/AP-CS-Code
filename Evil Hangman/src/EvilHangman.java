import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

public class EvilHangman
{
	private boolean debug;
	private Scanner in;
	private List<String> wordList;
	private int wordLength;
	private int remainingGuesses;
	private String solution;
	private String guessedLetters;


	/**
	 * Construct an EvilHangman object.
	 * @param fineName the name of the file that contains the word list.
	 * @param debug indicates if the size of the remaining word list
	 *        should be included in the toString result.
	 * @throws FileNotFoundException when the fileName file does not exist.
	 */
   public EvilHangman(String fileName, boolean debug) throws FileNotFoundException {
	   this.debug = debug;
	   in = new Scanner(System.in);
	   inputWords(fileName);
	   System.out.print("Number of guesses? >>> ");
	   this.remainingGuesses = in.nextInt();
	   this.solution = "";
	   for (int i = 0; i < wordLength; i++) {
		   this.solution += "-";
	   }
	   this.guessedLetters = "";
   }

	/**
	 * Plays one the game.  The user guesses letters until either
	 * they guess the word, or they run out of guesses.
	 * Game status is printed each turn and winning / losing
	 * information is printed at the conclusion of the game.
	 */
   public void playGame() {
	   while (solution.contains("-") && remainingGuesses > 0) {
		   System.out.println(this);
		   String guess = inputLetter();
		   guessedLetters += guess;
		   List<Partition> partitions = getPartitionList(guess);
		   removeSmallerPartitions(partitions);
		   wordList = getWordsFromOptimalPartition(partitions);
		   String hold = solution;
		   substitute(wordList.get(0), guess);
		   if (hold.equals(solution)) remainingGuesses--;
	   }
	   if (remainingGuesses > 0) {
		   System.out.println("You win, congratulations!");
		   System.out.println(solution);
	   }
	   else {
		   System.out.println("You lose, sorry!");
		   Random r = new Random();
		   System.out.println(wordList.get(r.nextInt(wordList.size())));
	   }
   }

	/**
	 * Creates and returns a status string that indicates the
	 * state of the game.
	 * @return the game status string.
	 */
   public String toString() {
		String ans = "Remaining guesses: " + this.remainingGuesses + "\nGuessed letters: " + this.guessedLetters + "\nSolution: " + this.solution;
		if (debug) ans += "\nRemaining words: " + this.wordList.size();
		return ans + "\n";
   }


	////////// PRIVATE HELPER METHODS //////////

	/**
	 * Helper method for the constructor:
	 * Inputs the word length from the user, reads in the words from
	 * the fileName file, and initializes the wordList instance variable
	 * with the words of the correct length.  This method loops until
	 * a valid word length is entered.
	 * @param fineName the name of the file that contains the word list.
	 * @throws FileNotFoundException when the fileName file does not exist.
	 */
   private void inputWords(String fileName) throws FileNotFoundException {
	   this.wordList = new ArrayList<>();
	   while (wordList.isEmpty()) {
		   System.out.print("Word length? >>> ");
		   this.wordLength = in.nextInt();
		   Scanner file = new Scanner(new File(fileName));
		   while (file.hasNext()) {
			   String next = file.next();
			   if (next.length() == this.wordLength) this.wordList.add(next);
		   }
		   if (wordList.isEmpty()) System.out.println("There are no words with " + wordLength + " letters.");
		   else file.close();
	   }
   }

	/**
	 * Helper method for playGame:
	 * Inputs a one-letter string from the user.
	 * Loops until the string is a one-character character in the range
	 * a-z or A-Z.
	 * @return the single-letter string converted to upper-case.
	 */
	private String inputLetter() {
		String letter = "";
		while (true) {
			System.out.print("Next letter? ");
			letter = in.next().toUpperCase();
			if (Pattern.matches("^[A-Z]$", letter)) break;
			System.out.println("Invalid input!");
		}
		return letter;
	}

	/**
	 * Helper method for getPartitionList:
	 * Uses word and letter to create a pattern.  The pattern string
	 * has the same number of letter as word.  If a character in
	 * word is the same as letter, the pattern is letter; Otherwise
	 * it's "-".
	 * @param word the word used to create the pattern
	 * @param letter the letter used to create the pattern
	 * @return the pattern
	 */
	public static String getPattern(String word, String letter) {
		word = word.toUpperCase();
		letter = letter.toUpperCase();
		String regex = "[^" + letter + "]";
		return word.replaceAll(regex, "-");
	}

	/**
	 * Helper method for playGame:
	 * Partitions each string in wordList based on their patterns.
	 * @param letter the letter used to find the pattern for
	 *        each word in wordList.
	 * @return the list of partitions.
	 */
	private List<Partition> getPartitionList(String letter) {
		List<Partition> list = new ArrayList<>();
		for (int i = 0; i < this.wordList.size(); i++) {
			String pattern = getPattern(wordList.get(i), letter);
			boolean added = false;
			for (int j = 0; j < list.size(); j++) {
				if (list.get(j).addIfMatches(pattern, wordList.get(i))) {
					added = true;
					break;
				}
			}
			if (!added) list.add(new Partition(pattern, wordList.get(i)));
		}
		return list;
	}

	/**
	 * Helper method for playGame:
	 * Removes all but the largest (most words) partitions from partitions.
	 * @param partitions the list of partitions.
	 *        Precondition: partitions.size() > 0
	 * Postcondition: partitions
	 * 1) contains all of the partitions with the most words.
	 * 2) does not contain any of the partitions with fewer than the most words.
	 */
	private void removeSmallerPartitions(List<Partition> partitions) {
		int max = 0;
		for (Partition p : partitions) {
			if (p.getWords().size() > max) max = p.getWords().size();
		}
		for (int i = 0; i < partitions.size(); i++) {
			if (partitions.get(i).getWords().size() < max) {
				partitions.remove(i);
				i--;
			}
		}
	}

	/**
	 * Helper method for playGame:
	 * Returns the words from one of the optimal partitions,
	 *    that is a partition with the most dashes in its pattern.
	 * @param partitions the list of partitions.
	 * @return the optimal partition.
	 */
	private List<String> getWordsFromOptimalPartition(List<Partition> partitions){
		Partition p = partitions.get(0);
		int dashes = p.getPatternDashCount();
		for (int i = 1; i < partitions.size(); i++) {
			if (partitions.get(i).getPatternDashCount() > dashes) {
				dashes = partitions.get(i).getPatternDashCount();
				p = partitions.get(i);
			}
		}
		return p.getWords();
	}

	/**
	 * Helper method for playGame:
	 * Creates a new string for solution.  If the ith letter of
	 * found equals letter, then the ith letter of solution is
	 * changed to letter; Otherwise it is unchanged.
	 * @param found the string to check for occurances of letter.
	 * @param letter the letter to check for in found.
	 */
	private void substitute(String found, String letter) {
		char[] c = solution.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (letter.charAt(0) == found.charAt(i)) {
				c[i] = found.charAt(i);
			}
		}
		solution = new String(c);
	}
}
