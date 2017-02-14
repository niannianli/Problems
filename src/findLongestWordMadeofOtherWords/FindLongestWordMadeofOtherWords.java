package findLongestWordMadeofOtherWords;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * 
 * @author Niannian Li
 * 
 */
public class FindLongestWordMadeofOtherWords {

	public static void main(String[] args) {

		Scanner s = new Scanner(System.in);

		System.out.println("Please input your fileName(the whole path) here: ");
		System.out.println("For example, in my own test, it is: /Users/niannianli/Desktop/wordsforproblem.txt");
		String fileName = s.next();

		System.out.println("Please create a new fileName(the whole path) to store the result here: ");
		System.out.println("For example, in my own test, it is: /Users/niannianli/Desktop/output.txt");
		String outputFileName = s.next();

		FindLongestWordMadeofOtherWords f = new FindLongestWordMadeofOtherWords();

		/*
		 * get current time: before we run our function
		 */
		long t1 = System.currentTimeMillis();

		/*
		 * running our function
		 */
		f.findLongestWord(fileName, outputFileName);

		/*
		 * get current time: after we run our function
		 */
		long t2 = System.currentTimeMillis();

		/*
		 * t = t2 - t1, serve as an approximate evaluation of the running time
		 */
		long t = t2 - t1;
		System.out.println("We used approximate " + t + " milliseconds running this program");
	}

	public void findLongestWord(String fileName, String outputFileName) {

		FileReader fr = null;
		try {
			fr = new FileReader(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		BufferedReader br = new BufferedReader(fr);
		String line = null;

		/*
		 * use HashSet to store the string elements in the file
		 */
		HashSet<String> hs = new HashSet<String>();
		try {
			while ((line = br.readLine()) != null) {

				/*
				 * HashSet does not contain duplicate values
				 */
				if (hs.contains(line)) {
				} else {
					hs.add(line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		/*
		 * use TreeSet to store the elements, 
		 * so they can be stored as sorted from longest length to shortest length,
		 * because we need to get the longest element that is made up by other elements, 
		 * so deal with longest element first
		 * 
		 * MyComp() is a nested class to define our comparison details in TreeSet,
		 * we sort in non-increasing order 
		 * check the code at the end of our current public class: FindLongestWordMadeofOtherWords
		 */
		TreeSet<String> ts = new TreeSet<String>(new MyComp());

		/*
		 * after we add all the elements from hs to our TreeSet ts, it's sorted
		 */
		for (String key : hs) {
			ts.add(key);
		}

		/*
		 * write our final result to a new file
		 */
		FileWriter fw = null;
		File newFile = new File(outputFileName);
		try {
			if (!newFile.exists()) {
				newFile.createNewFile();
			}
			fw = new FileWriter(newFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		BufferedWriter bw = new BufferedWriter(fw);

		/*
		 * count is the : 
		 * "Total count of how many of the words in the list can be constructed of other words in the list."
		 */
		int count = 0;
		try {

			/*
			 * begin from the longest element in our TreeSet ts
			 */
			for (String key : ts) {
				hs.remove(key);
                
          	   /*
          	    * if current element is made up of other elements in the HashSet hs (which stores our original elements)	
          	    * print it out and write it into our output file
          	    */
				if (isMadeUp(key, hs)) {
					count++;
					System.out.println(key + " current total is: " + count);
					bw.write(key + " " + count);
					bw.newLine();
				}

				hs.add(key);
			}
			
			/*
			 * after check every element in the file, 
			 * we give the total count of words that can be constructed
			 */
			System.out.println("the count of words that can be constructed is: " + count);
			bw.write("the count of words that can be constructed is: " + count);
			bw.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {

				/*
				 * if we have to overwrite previous data in the output file, use
				 * bw.flush();
				 */
				bw.flush();

				/*
				 * try to release memory
				 */
				hs.clear();
				ts.clear();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isMadeUp(String key, HashSet<String> hs) {
        
		/* 
		 * base case for recursive algorithm
		 */
		if (hs.contains(key))
			return true;

		for (int i = 1; i < key.length(); i++) {
			String checking = key.substring(0, i);
			String remaining = key.substring(i);

			/*
			 * this is where the recursive algorithm used
			 */
			if (hs.contains(checking) && isMadeUp(remaining, hs)) {
				return true;
			}
		}
		return false;
	}

	/*
	 * this is a nested class to define our comparison details in TreeSet,
	 * non-increasing order
	 */
	class MyComp implements Comparator<String> {

		@Override
		public int compare(String str1, String str2) {

			if (str1.length() < str2.length())
				return 1;
			else
				return -1;
		}
	} // end of MyComp class

} // end of public class FindLongestWordMadeofOtherWords 
