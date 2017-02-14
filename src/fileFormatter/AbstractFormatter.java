package fileFormatter;

import jAXBLibrary.Sites;
import java.util.LinkedHashMap;

/**
 * Project requirement: The API only needs to handle CSV and XML formats, but
 * you should be able to plug in new formats with minimal changes to the code
 * (or configuration).
 * 
 * We use an abstract class to add flexibility to our code: 1, we have
 * CSVFormatter class and XMLFormatter class to handle CSV and XML formats; 2,
 * if we need a new file format, just add a newFileTypeFormatter.java class in
 * this package, 3, we do not need to affect other code.
 */
public abstract class AbstractFormatter {

	/**
	 * The following process goes the same as our UserInterface: 1, we open a
	 * file and read(select local file) 2, let the users select sort or filter,
	 * what they need(set sorter and filter);
	 * 
	 * TRANSFER AND SAVE 3, we get the data of the format users requested (after
	 * sort and filter)(from Sites) 4, we write the file with the data format
	 * users need
	 */

	/*
	 * what we need is the fileName of our file without knowing the fileFormat
	 */
	protected String fileName;

	/*
	 * read from input file to get our data(object)
	 */
	public abstract void file2Obj() throws Exception;

	/*
	 * filter data operation
	 */
	public abstract void filtObj(LinkedHashMap<ColumnName, Object> filterMap);

	/*
	 * sort data operation
	 */
	public abstract void sortObj(
			LinkedHashMap<ColumnName, ColumnName.SortingType> sortMap);

	/**
	 * the data format we needed write to Sites then we read this data format
	 * from Sites
	 * 
	 * @param sites
	 */
	/*
	 * output from data of this format to data in Sites
	 */
	public abstract void fromExchange(Sites sites);

	/*
	 * input from data in Sites to data of this format.
	 */
	public abstract Sites toExchange();

	/*
	 * finally, write to output file from the data we get
	 */
	public abstract void obj2File();

	public String getFileName() {
		return fileName;
	}
}