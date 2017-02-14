package unitTests;

import static org.junit.Assert.assertTrue;
import jAXBLibrary.Sites;
import jAXBLibrary.Sites.Site;
import jAXBLibrary.Sites.Site.Hosts.Host;

import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Test;

import transferFormat.TransferFromToFiles;
import transferFormat.TransferProcess;
import fileFormatter.CSVFormatter;
import fileFormatter.ColumnName;
import fileFormatter.XMLFormatter;

/**
 * 1, wrote the output that should come out with our input 2, use JUnit library
 * to do the unit testing 3, all input, output files are in the same package:
 * unitTests
 */
public class UnitTests {

	@Test
	public void testXmlToCsvSort() throws Exception {

		LinkedHashMap<ColumnName, ColumnName.SortingType> sorter;
		LinkedHashMap<ColumnName, Object> filter;
		TransferFromToFiles formatTransfer;
		TransferProcess tc;

		/**
		 * Test case #1: Generate CSV output from XML input, output must be
		 * sorted by operative system, then by host_id descending.
		 */
		String inputXMLFile = "src//unitTests//ipsoft_perf_counters_xml_sample_data.xml";
		String outputCSVFile = "src//unitTests//csv_sortByoperativeSystem_sortByhostIdDescending_output1.txt";

		System.out.println("test xml to csv transfer, only sort");

		sorter = new LinkedHashMap<ColumnName, ColumnName.SortingType>();
		sorter.put(ColumnName.XML_OS, ColumnName.SortingType.ASCENDING);
		sorter.put(ColumnName.HOST_ID, ColumnName.SortingType.DESCENDING);

		filter = new LinkedHashMap<ColumnName, Object>();

		tc = new TransferProcess();
		tc.setSource(new XMLFormatter(inputXMLFile));
		tc.setTarget(new CSVFormatter(outputCSVFile));
		tc.setFilter(filter);
		tc.setSorter(sorter);
		formatTransfer = new TransferFromToFiles();
		formatTransfer.transfer(tc);

		// read from csv output file to test if we get the correct result
		CSVFormatter aa = new CSVFormatter(outputCSVFile);
		aa.file2Obj();
		Sites sites = aa.toExchange();
		for (Site site : sites.getSite()) {
			List<Host> hostList = site.getHosts().getHost();
			for (int i = 1; i < hostList.size(); i++) {
				Host curr = hostList.get(i);
				Host prev = hostList.get(i - 1);
				if (curr.getOS().equals(prev.getOS())) {
					assertTrue(curr.getId().compareTo((prev.getId())) <= 0); // if
																				// OS
																				// equals,
																				// host
																				// id
																				// descending.
				} else {
					assertTrue(curr.getOS().compareTo(prev.getOS()) >= 0); // OS
																			// ascending...
				}
			}
		}
	}

	@Test
	public void testToXmlTOCsvFilterSort() throws Exception {
		LinkedHashMap<ColumnName, ColumnName.SortingType> sorter;
		LinkedHashMap<ColumnName, Object> filter;
		TransferFromToFiles formatTransfer;
		TransferProcess tc;

		/**
		 * Test case #2: Generate CSV output from XML input, filtering by
		 * site_name=�NY-01� and sort by host_name ascending.
		 */
		String inputXMLFile = "src//unitTests//ipsoft_perf_counters_xml_sample_data.xml";
		String outputCSVFile = "src//unitTests//csv_filterBysiteName_sortByhostNameAscending_output2.txt";

		System.out.println("test xml to csv transfer, filter and sort");

		filter = new LinkedHashMap<ColumnName, Object>();
		filter.put(ColumnName.SITE_NAME, "NY-01");

		sorter = new LinkedHashMap<ColumnName, ColumnName.SortingType>();
		sorter.put(ColumnName.HOST_NAME, ColumnName.SortingType.ASCENDING);

		tc = new TransferProcess();
		tc.setSource(new XMLFormatter(inputXMLFile));
		tc.setTarget(new CSVFormatter(outputCSVFile));
		tc.setFilter(filter);
		tc.setSorter(sorter);
		formatTransfer = new TransferFromToFiles();
		formatTransfer.transfer(tc);

		// read from CSV output file to test if we get the right result
		CSVFormatter aa = new CSVFormatter(outputCSVFile);
		aa.file2Obj();
		Sites sites = aa.toExchange();
		int i = 0;
		@SuppressWarnings("unused")
		Site prev = null;
		for (Site site : sites.getSite()) {
			assertTrue(site.getName().equals("NY-01")); // filter right site
														// name
			if (i == 0) {
				prev = site;
				i++;
				continue;
			} else {
				List<Host> hostList = site.getHosts().getHost();
				for (i = 1; i < hostList.size(); i++) {
					Host curr = hostList.get(i);
					Host prevh = hostList.get(i - 1);
					assertTrue(curr.getHostName().compareTo((prevh.getHostName())) >= 0); // host
																							// name
																							// ascending.
				}
			}
		}
	}

	@Test
	public void testXmlToXmlSort() throws Exception {
		LinkedHashMap<ColumnName, ColumnName.SortingType> sorter;
		LinkedHashMap<ColumnName, Object> filter;
		TransferFromToFiles formatTransfer;
		TransferProcess tc;

		/**
		 * Test case #3: Generate XML output from XML input, sorted by load
		 * average 1min, then host_id descending
		 */
		String inputXMLFile = "src//unitTests//ipsoft_perf_counters_xml_sample_data.xml";
		String outputXMLFile = "src//unitTests//xml_sortByloadAvg1Min_sortByhostIdDescending_output3.xml";

		System.out.println("test xml to xml transfer, only sort");

		filter = new LinkedHashMap<ColumnName, Object>();

		sorter = new LinkedHashMap<ColumnName, ColumnName.SortingType>();
		sorter.put(ColumnName.LOAD_AVG_1MIN, ColumnName.SortingType.ASCENDING);
		sorter.put(ColumnName.HOST_ID, ColumnName.SortingType.DESCENDING);

		tc = new TransferProcess();
		tc.setSource(new XMLFormatter(inputXMLFile));
		tc.setTarget(new XMLFormatter(outputXMLFile));
		tc.setFilter(filter);
		tc.setSorter(sorter);
		formatTransfer = new TransferFromToFiles();
		formatTransfer.transfer(tc);

		// read from xml output file to test if we get the right result
		XMLFormatter aa = new XMLFormatter(outputXMLFile);
		aa.file2Obj();
		Sites sites = aa.toExchange();
		int i = 0;
		for (Site site : sites.getSite()) {
			List<Host> hostList = site.getHosts().getHost();
			for (i = 1; i < hostList.size(); i++) {
				Host curr = hostList.get(i);
				Host prevh = hostList.get(i - 1);
				if (curr.getLoadAvg1Min() == prevh.getLoadAvg1Min()) {
					assertTrue(curr.getId().compareTo((prevh.getId())) <= 0); // host
																				// id
																				// descending.
				} else
					assertTrue(curr.getLoadAvg1Min() >= prevh.getLoadAvg1Min()); // load_avg_1min
																					// ascending
			}
		}
	}
}
