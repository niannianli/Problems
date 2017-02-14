package programSolution;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author niannianli Many comments in this program, in order to do testing
 *         while programming Solution.java is the final program to run.
 * 
 */

public class SolutionWithFullComments {

	/*
	 * After running this program, user could get the specific result that they
	 * need anytime later.
	 */

	// Find number of unique lines
	private int numberOfLines;

	// Find number of unique words
	private int numberOfWords;

	// Find the 5 most common words, excluding stopwords (of, and, the, a, to)
	private List<Map.Entry<String, Integer>> resultList;

	// getters and setter
	public int getNumberOfLines() {
		return numberOfLines;
	}

	public void setNumberOfLines(int numberOfLines) {
		this.numberOfLines = numberOfLines;
	}

	public int getNumberOfWords() {
		return numberOfWords;
	}

	public void setNumberOfWords(int numberOfWords) {
		this.numberOfWords = numberOfWords;
	}

	public List<Map.Entry<String, Integer>> getList() {
		return resultList;
	}

	public void setList(List<Map.Entry<String, Integer>> resultList) {
		this.resultList = resultList;
	}

	// Main program
	public void run(File inputFile) {

		long start1 = System.currentTimeMillis();
		String content = readFile(inputFile);
		long start2 = System.currentTimeMillis();

		long time1 = start2 - start1;
		System.out.println("It takes " + time1 + "ms to read the entire file.");

		HashMap<String, Integer> hm = storeWordCount(content);
		long start3 = System.currentTimeMillis();

		long time2 = start3 - start2;
		System.out.println("It takes " + time2
				+ "ms to store words into HashMap.");

		resultList = getTop5(hm);
		long start4 = System.currentTimeMillis();

		long time3 = start4 - start3;
		System.out.println("It takes " + time3
				+ "ms to get top 5 frequency words.");
		System.out.println();
	}

	public String readFile(File inputFile) {

		/*
		 * Read the file, get the number of lines, store all the contents in a
		 * StringBuffer for later use
		 */
		FileReader fReader = null;
		try {
			fReader = new FileReader(inputFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BufferedReader bReader = new BufferedReader(fReader);
		StringBuilder sBuilder = new StringBuilder();
		String line = "";
		numberOfLines = 0;

		// Empty line is not treated as a line, other lines are all unique
		try {
			while ((line = bReader.readLine()) != null) {
				if (!line.trim().isEmpty()) {
					numberOfLines++;
					sBuilder.append(line);

					/*
					 * we need this to avoid two words in two lines become one
					 * word
					 */
					sBuilder.append(" ");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bReader != null)
				try {
					bReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (fReader != null)
				try {
					fReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		// got the number of unique lines
		System.out.println("number of unique lines: " + numberOfLines);
		String content = sBuilder.toString();
		return content;
	}

	public HashMap<String, Integer> storeWordCount(String content) {

		/*
		 * Put all words to HashMap, every key is different, so the size of
		 * Hashmap's keySet is the numberOfWords or just count as we store the
		 * words into HashMap
		 */
		Pattern pattern = Pattern.compile("[a-zA-Z]+");
		Matcher matcher = pattern.matcher(content);
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		String word = "";
		numberOfWords = 0;
		while (matcher.find()) {
			word = matcher.group().toLowerCase();
			if (!hm.containsKey(word)) {
				numberOfWords++;
				hm.put(word, 0);
			}
			hm.put(word, hm.get(word) + 1);
		}

		System.out.println("number of unique words: " + numberOfWords);
		System.out.println("number of unique words: " + numberOfWords + " == "
				+ hm.keySet().size());

		return hm;
	}

	public List<Map.Entry<String, Integer>> getTop5(HashMap<String, Integer> hm) {

		/*
		 * to get top 5, we need a list here, because we have to get the max
		 * value using Compare(); HashMap only accept values(), ketSet() as
		 * parameters in Collections.max();
		 * 
		 * we do not use Collections.sort(), O(nlogn) time, because we are not
		 * required to sort all the words; we only need top 5, use
		 * Collections.max(), O(n) time,
		 */
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(
				hm.entrySet());

		/*
		 * if two words are with same amount, they are treated as 2 items in top
		 * 5; if the fifth word has the same amount as the sixth word, ignore
		 * the sixth word and the following words that with the same amount
		 */

		// In order to avoid ConcurrentModificationException, we could use
		// synchronized keyword in a block

		/*
		 * List<Map.Entry<String, Integer>> listNew =
		 * Collections.synchronizedList(list); synchronized(listNew) { }
		 */

		/*
		 * we do not need that, if we need all the Entries in HashMap, sort the
		 * HashMap is better. Synchronized key word affects the efficiency.
		 */

		// return this list the the user
		resultList = new LinkedList<Map.Entry<String, Integer>>();

		int i = 0;
		while (i < 5) {

			Map.Entry<String, Integer> entry = Collections.max(list,
					new NewComparator());
			String key = entry.getKey();
			Integer value = entry.getValue();

			// remove this item from the list
			int index = list.indexOf(entry);
			list.remove(index);

			// excluding stopwords (of, and, the, a, to)
			if (key.equals("of") || key.equals("and") || key.equals("the")
					|| key.equals("a") || key.equals("to")) {
				continue;
			}

			// we got the word and the frequency of the word
			System.out.println(key + " : " + value);
			resultList.add(entry);
			i++;
		}

		return resultList;
	}

	class NewComparator implements Comparator<Map.Entry<String, Integer>> {
		public int compare(Entry<String, Integer> current,
				Entry<String, Integer> previous) {
			return current.getValue().compareTo(previous.getValue());
		}
	}
}