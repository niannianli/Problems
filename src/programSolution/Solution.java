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
 * @author niannianli
 * 
 */

public class Solution {

	private int numberOfLines;
	private int numberOfWords;
	private List<Map.Entry<String, Integer>> resultList;

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

	public void run(File inputFile) {

		String content = readFile(inputFile);

		HashMap<String, Integer> hm = storeWordCount(content);

		resultList = getTop5(hm);

	}

	public String readFile(File inputFile) {

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

		try {
			while ((line = bReader.readLine()) != null) {
				if (!line.trim().isEmpty()) {
					numberOfLines++;
					sBuilder.append(line);

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

		String content = sBuilder.toString();
		return content;
	}

	public HashMap<String, Integer> storeWordCount(String content) {

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

		return hm;
	}

	public List<Map.Entry<String, Integer>> getTop5(HashMap<String, Integer> hm) {

		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(
				hm.entrySet());
		resultList = new LinkedList<Map.Entry<String, Integer>>();

		int i = 0;
		while (i < 5) {
			Map.Entry<String, Integer> entry = Collections.max(list,
					new NewComparator());
			String key = entry.getKey();
			// Integer value = entry.getValue();

			int index = list.indexOf(entry);
			list.remove(index);

			if (key.equals("of") || key.equals("and") || key.equals("the")
					|| key.equals("a") || key.equals("to")) {
				continue;
			}
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