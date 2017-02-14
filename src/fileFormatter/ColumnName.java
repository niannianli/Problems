package fileFormatter;

/**
 * We do not use a sql database to deal with data, which may be much easier to
 * use But we only have small data with easy operations, in XML or CSV file With
 * parsing the file, we can easily map our data from the file to our Java
 * beans(not exactly beans here) So we can get the meta data we needed according
 * to their column name, which is easy to get just by reading from the file
 * 
 * We can add more names here is we have more... But I think we have a easier
 * way to do this(like some library or framework(Hibernate?) to deal with
 * data... I will do more research to figure this out...
 */
public enum ColumnName {

	// Column Name in csv all lowercase
	// Column Name in xml all first uppercase together with lowercase

	// Here we make them all UPPERCASE to ingnoreCase();

	// Compare the two test files(.txt and .xml provided in the code exercise)
	// : we can use ignoreCase to get when they have same name

	SITE_ID("site_id"), SITE_NAME("site_name"), SITE_LOCATION("site_location"),

	HOST_ID("host_id"), HOST_NAME("host_name"),

	IP_ADDRESS("ip_address"),

	// but they use different words for operative_system: so we define two of
	// them
	CSV_OS("operative_system"), 
	XML_OS("OS"),

	LOAD_AVG_1MIN("load_avg_1min"), LOAD_AVG_5MIN("load_avg_5min"), LOAD_AVG_15MIN(
			"load_avg_15min");

	// We have two sorting types
	public enum SortingType {

		ASCENDING("ascending"), DESCENDING("descending");

		// Constructor
		SortingType(String type) {
			sortType = type;
		}

		// Override
		private String sortType;

		public String toString() {
			return sortType;
		}
	};

	// same as above, column change...
	private String columnName;

	ColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getFieldName() {
		return columnName;
	}

	public String toString() {
		return columnName;
	}
}