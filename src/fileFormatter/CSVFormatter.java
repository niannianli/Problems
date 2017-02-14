package fileFormatter;

import jAXBLibrary.Sites;
import jAXBLibrary.Sites.Site;
import jAXBLibrary.Sites.Site.Hosts;
import jAXBLibrary.Sites.Site.Hosts.Host;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import fileFormatter.ColumnName.SortingType;
import static java.util.Arrays.asList;

/**
 * I downloaded this file from online resource, it is mainly about file
 * read/write, input/output
 * 
 * Because we get the .txt file as CSV file, so simply file read/write can
 * easily parse this file(comma separated)
 * 
 * If we do have a .csv file, we have a 3rd party library: openCSV, which is in
 * my Referenced Libraries: opencsv-2.3.jar, I will explore more about this
 * library later...
 */
public class CSVFormatter extends AbstractFormatter {

	/*
	 * These are are column names from the file, Here we just use them as a
	 * default value, In real world situation, just read the first line in the
	 * file, we can get them all.
	 */
	private final String DEFAULT_HEADER_LINE = "site_id, site_name, site_location, host_id, host_name, "
			+ "ip_address, operative_system, load_avg_1min, load_avg_5min, load_avg_15min";

	/*
	 * header, real header value
	 */
	private List<String> header = new ArrayList<String>(
			asList(DEFAULT_HEADER_LINE.split(", ")));

	/*
	 * csvSites are defined data (we are work with these data) for csv format.
	 */
	private List<List<String>> csvSites = new LinkedList<List<String>>();

	// These are defined in our ColumnName.java together with their real value
	enum COLUMN_POSITION {
		SITE_ID, SITE_NAME, SITE_LOCATION, HOST_ID, HOST_NAME, IP_ADDRESS, OPERATIVE_SYSTEM, LOAD_AVG_1MIN, LOAD_AVG_5MIN, LOAD_AVG_15MIN;
	}

	public CSVFormatter(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Here I show some example of how to use openCSV for later study and use
	 * this library... They are not related to our code work here... But take a
	 * look to learn, They share the same logic, only with a libray, our work is
	 * done much easier...
	 */

	/*
	 * Reader token by token... public void opencsvTest() { String csvFilename =
	 * fileName; CSVReader csvReader = null; try { csvReader = new CSVReader(new
	 * FileReader(csvFilename)); } catch (FileNotFoundException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } String[] row = null;
	 * try { while((row = csvReader.readNext()) != null) {
	 * System.out.println(row[0] + " # " + row[1] + " #  " + row[2]); } } catch
	 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace();
	 * } try { csvReader.close(); } catch (IOException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } }
	 */

	/*
	 * Reader all, then iterate... String[] row = null; String csvFilename =
	 * "C:\\work\\sample.csv"; CSVReader csvReader = new CSVReader(new
	 * FileReader(csvFilename)); List content = csvReader.readAll();
	 * 
	 * for (Object object : content) { row = (String[]) object;
	 * 
	 * System.out.println(row[0] + " # " + row[1] + " #  " + row[2]); }
	 * csvReader.close();
	 */

	/*
	 * Writer taken by token... String csv = "C:\\output.csv"; CSVWriter writer
	 * = new CSVWriter(new FileWriter(csv));
	 * 
	 * String [] country = "India#China#United States".split("#");
	 * 
	 * writer.writeNext(country);
	 * 
	 * writer.close();
	 */

	/*
	 * Writer(all) an array of data... String csv = "C:\\output2.csv"; CSVWriter
	 * writer = new CSVWriter(new FileWriter(csv));
	 * 
	 * List<String[]> data = new ArrayList<String[]>(); data.add(new String[]
	 * {"India", "New Delhi"}); data.add(new String[] {"United States",
	 * "Washington D.C"}); data.add(new String[] {"Germany", "Berlin"});
	 * 
	 * writer.writeAll(data);
	 * 
	 * writer.close();
	 */

	/*
	 * Same as we do in our code in use in this file, we have ColumnPosition to
	 * map our real metadata ColumnPositionMappingStrategy strat = new
	 * ColumnPositionMappingStrategy(); strat.setType(Country.class); String[]
	 * columns = new String[] {"countryName", "capital"}; // the fields to bind
	 * do in your JavaBean strat.setColumnMapping(columns);
	 * 
	 * CsvToBean csv = new CsvToBean();
	 * 
	 * String csvFilename = "C:\\sample.csv"; CSVReader csvReader = new
	 * CSVReader(new FileReader(csvFilename));
	 * 
	 * List list = csv.parse(strat, csvReader);
	 * 
	 * for (Object object : list) { Country country = (Country) object;
	 * System.out.println(country.getCapital()); }
	 */

	/*
	 * We have small data to deal with here, so simple file reader/writer can
	 * finishe this job
	 */

	// Read our header...
	BufferedReader br;

	public void file2Header(String fileName) {
		FileReader file;
		try {
			file = new FileReader(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		br = new BufferedReader(file);
		String line = "";
		header.clear();
		try {
			if ((line = br.readLine()) != null) {
				String[] items = line.split(",");
				for (int i = 0; i < items.length; i++) {
					header.add(items[i].trim());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Now implement the methods in our abstract class
	 */

	// file2Obj()
	@Override
	public void file2Obj() throws Exception {
		FileReader file;
		try {
			file = new FileReader(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new Exception("errors in parsing csv file:" + fileName);
		}
		br = new BufferedReader(file);
		String line = "";
		header.clear();
		try {
			int lineNo = 1;
			while ((line = br.readLine()) != null) {
				String[] items = line.split(",");
				if (lineNo == 1) { // first line is the header line.
					for (int i = 0; i < items.length; i++) {
						header.add(items[i].trim());
					}
					lineNo++;
					continue;
				}
				List<String> lineItems = new ArrayList<String>();
				for (int i = 0; i < items.length; i++) {
					lineItems.add(items[i].trim());
				}
				csvSites.add(lineItems);
				lineNo++;
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("errors in parsing csv file:" + fileName);
		}
	}

	// obj2File()
	@Override
	public void obj2File() {
		BufferedWriter bw = null;
		FileWriter file = null;
		if (fileName == null || "".equals(fileName)) {
			bw = new BufferedWriter(new OutputStreamWriter(System.out));
		} else {
			try {
				file = new FileWriter(fileName);
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
			bw = new BufferedWriter(file);
		}
		String line = "";
		for (int i = 0; i < header.size(); i++) {
			line += header.get(i);
			if (i != header.size() - 1)
				line += ", ";
		}
		try {
			bw.write(line);
			bw.newLine();
		} catch (IOException e1) {
			e1.printStackTrace();
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		// output to csv file content.
		for (int i = 0; i < csvSites.size(); i++) {
			line = "";
			List<String> tmp = csvSites.get(i);
			for (int j = 0; j < tmp.size(); j++) {
				line += tmp.get(j);
				if (j != tmp.size() - 1)
					line += ", ";
			}
			try {
				bw.write(line);
				bw.newLine();
			} catch (IOException e1) {
				e1.printStackTrace();
				return;
			}
		}
		try {
			bw.flush();
			if (file != null) {
				file.close();
				bw.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// filtObj(LinkedHashMap<ColumnName, Object> filterMap)
	@Override
	public void filtObj(LinkedHashMap<ColumnName, Object> filterMap) {
		if (filterMap == null || filterMap.size() == 0)
			return;

		// Traverse sites and filter
		String filterName;
		Object filterValue;
		for (Entry<ColumnName, Object> entry : filterMap.entrySet()) {
			filterName = entry.getKey().toString();
			filterValue = entry.getValue();
			filterSite(filterName, filterValue);
		}
	}

	private void filterSite(Object filterName, Object filterValue) {
		String strName = (String) filterName;
		String strValue = (String) filterValue;
		int index = -1;
		for (int i = 0; i < header.size(); i++) {
			if (header.get(i).equals(strName)) {
				index = i;
				break;
			}
		}
		if (index == -1) {
			System.err.println("fileter name is not found:" + strName);
			return;
		}
		int i = 0;
		while (i < csvSites.size()) {
			if (!csvSites.get(i).get(index).equals(strValue)) {
				csvSites.remove(i);
				continue;
			}
			i++;
		}
	}

	// sortObj(LinkedHashMap<ColumnName, ColumnName.SortingType> sortMap)
	@Override
	public void sortObj(
			LinkedHashMap<ColumnName, ColumnName.SortingType> sortMap) {
		if (sortMap == null || sortMap.size() == 0)
			return;

		class LineComparator implements Comparator<List<String>> {
			LinkedHashMap<ColumnName, ColumnName.SortingType> map = new LinkedHashMap<ColumnName, SortingType>();

			public LineComparator(LinkedHashMap<ColumnName, SortingType> sortMap) {
				map = sortMap;
			}

			@Override
			public int compare(List<String> arg0, List<String> arg1) {
				for (Entry<ColumnName, SortingType> entry : map.entrySet()) { // support
																				// multiple
																				// layer
																				// sorts.
					if (entry.getKey() == null) {
						System.err.println("sorting filed name cannot be null");
						return 0;
					}
					String sortName = "";
					if (entry.getKey() != null)
						sortName = entry.getKey().toString();
					String sortType = "";
					if (entry.getValue() != null)
						sortType = entry.getValue().toString();

					int ret = 0;
					int reverse = 1;
					if (sortType != null && sortType.equals("descending"))
						reverse = -1;

					int index = -1;
					for (int i = 0; i < header.size(); i++) {
						if (header.get(i).equals(sortName)) {
							index = i;
							break;
						}
					}
					if (index == -1) {
						System.err.println("sorting filed name" + sortName
								+ " does not exist");
						continue; // sort name does not exist.
					}
					ret = reverse
							* (arg0.get(index).compareTo(arg1.get(index)));
					if (ret != 0)
						return ret;
				} // of map
				return 0;
			}
		}
		;
		LineComparator siteComparator = new LineComparator(sortMap);
		Collections.sort(csvSites, siteComparator);
	}

	// fromExchange(Sites sites)
	@Override
	public void fromExchange(Sites sites) {

		// get data from xmlTransfer object and populate header and csvSites in
		// this class.
		csvSites.clear();
		List<String> line = new ArrayList<String>();
		List<Site> siteList = sites.getSite();
		for (int i = 0; i < siteList.size(); i++) {
			Site site = siteList.get(i);
			line.clear();
			line.add(COLUMN_POSITION.SITE_ID.ordinal(), site.getId().toString());
			line.add(COLUMN_POSITION.SITE_NAME.ordinal(), site.getName());
			line.add(COLUMN_POSITION.SITE_LOCATION.ordinal(),
					site.getLocation());
			List<Host> hostList = site.getHosts().getHost();
			for (int j = 0; j < hostList.size(); j++) {
				Host host = hostList.get(j);
				line.add(COLUMN_POSITION.HOST_ID.ordinal(), host.getId()
						.toString());
				line.add(COLUMN_POSITION.HOST_NAME.ordinal(),
						host.getHostName());
				line.add(COLUMN_POSITION.IP_ADDRESS.ordinal(),
						host.getIPAddress());
				line.add(COLUMN_POSITION.OPERATIVE_SYSTEM.ordinal(),
						host.getOS());
				line.add(COLUMN_POSITION.LOAD_AVG_1MIN.ordinal(),
						Float.toString(host.getLoadAvg1Min()));
				line.add(COLUMN_POSITION.LOAD_AVG_5MIN.ordinal(),
						Float.toString(host.getLoadAvg5Min()));
				line.add(COLUMN_POSITION.LOAD_AVG_15MIN.ordinal(),
						Float.toString(host.getLoadAvg15Min()));
				List<String> copyInserted = new ArrayList<String>(line);
				csvSites.add(copyInserted);
				for (int k = 3; k < line.size();)
					line.remove(line.size() - 1);
			}
		}
	}

	// toExchange()
	@Override
	public Sites toExchange() {

		// populate data in this class to XmlTransfer class, sites.
		Sites sites = new Sites();
		for (int i = 0; i < csvSites.size(); i++) {
			List<String> line = csvSites.get(i);
			List<Site> siteList = sites.getSite();
			int index = -1;
			for (int j = 0; j < siteList.size(); j++) {
				if (siteList.get(j).getId().toString()
						.equals(line.get(COLUMN_POSITION.SITE_ID.ordinal()))) {

					// siteId matches
					index = j;
					break;
				}
			}
			if (index == -1) { // new site and add to list;
				Site site = new Site();
				site.setId(Short.valueOf(line.get(COLUMN_POSITION.SITE_ID
						.ordinal())));
				site.setName(line.get(COLUMN_POSITION.SITE_NAME.ordinal()));
				site.setLocation(line.get(COLUMN_POSITION.SITE_LOCATION
						.ordinal()));
				siteList.add(site);
			}
			Site currentSite = (index == -1) ? siteList
					.get(siteList.size() - 1) : siteList.get(index);
			Hosts hosts = currentSite.getHosts();
			if (hosts == null) {
				hosts = new Hosts();
				currentSite.setHosts(hosts);
			}
			List<Host> hostList = hosts.getHost();
			index = -1;
			for (int j = 0; j < hostList.size(); j++) {
				if (hostList.get(j).getId().toString()
						.equals(line.get(COLUMN_POSITION.HOST_ID.ordinal()))) {
					index = j;
					break;
				}
			}
			if (index == -1) { // new host and add to list;
				Host host = new Host();
				host.setId(Short.valueOf(line.get(COLUMN_POSITION.HOST_ID
						.ordinal())));
				host.setHostName(line.get(COLUMN_POSITION.HOST_NAME.ordinal()));
				host.setIPAddress(line.get(COLUMN_POSITION.IP_ADDRESS.ordinal()));
				host.setOS(line.get(COLUMN_POSITION.OPERATIVE_SYSTEM.ordinal()));
				host.setLoadAvg1Min(Float.valueOf(line
						.get(COLUMN_POSITION.LOAD_AVG_1MIN.ordinal())));
				host.setLoadAvg5Min(Float.valueOf(line
						.get(COLUMN_POSITION.LOAD_AVG_5MIN.ordinal())));
				host.setLoadAvg15Min(Float.valueOf(line
						.get(COLUMN_POSITION.LOAD_AVG_15MIN.ordinal())));
				hostList.add(host);
			}
		}
		return sites;
	}

}