import java.util.List;
import java.util.ArrayList;

public class Partition
{
	private String wordPattern;
	private List<String> wordList;

	/**
	 * Construct a Partition object with no words.
	 * @param pattern the pattern for the partition.
	 */
	public Partition(String pattern) {
		this.wordPattern = pattern;
		this.wordList = new ArrayList<>();
	}

	/**
	 * Construct a Partition object with one given word.
	 * @param pattern the pattern for the partition.
	 * @param word the initial word for the partition.
	 */
	public Partition(String pattern, String word) {
		this.wordPattern = pattern;
		this.wordList = new ArrayList<>();
		this.wordList.add(word);
	}

	/**
	 * Adds word to the partition IFF the pattern matches the given one.
	 * @param pattern the pattern to check for a match.
	 * @param word the word to add if the patterns match.
	 * @returns true if word is added; false otherwise.
	 */
	public boolean addIfMatches(String pattern, String word) {
		if(!pattern.equals(wordPattern)) return false;
		wordList.add(word);
		return true;
	}

	/**
	 * @return the pattern's words.
	 */
	public List<String> getWords()
	{
		return this.wordList;	// REPLACE
	}

	/**
	 * @return the number of dashs in the partition's pattern.
	 */
	public int getPatternDashCount() {
		return wordPattern.length() - wordPattern.replace("-", "").length();
	}
}