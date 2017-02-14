package fileFormatter;

import jAXBLibrary.Sites;
import jAXBLibrary.Sites.Site;
import jAXBLibrary.Sites.Site.Hosts.Host;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import fileFormatter.ColumnName.SortingType;

/**
 * Same as CSVFormatter, I download this file for online resource It is mainly
 * about read/write to parse the file
 * 
 * Especially, we use the library: JAXB to parse our XML file, which is better
 * than SAX and DOM in many aspects, this library is in my Referenced
 * Libraries/lib/jaxb-...
 */

/*
 * This class share the same logic as CSVFormatter, so I will not comment in
 * details, just the places where we use the JAXB library.
 */
public class XMLFormatter extends AbstractFormatter {
	private Sites sites;

	public XMLFormatter(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public void file2Obj() throws Exception {
		try {
			File file = new File(fileName);

			/**
			 * Using JAXBContext to convert XML data into a tree of Java content
			 * objects
			 */
			JAXBContext jaxbContext = JAXBContext.newInstance(Sites.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			sites = (Sites) jaxbUnmarshaller.unmarshal(file);
		} catch (JAXBException e) {
			e.printStackTrace();
			throw new Exception("errors in parsing xml file:" + fileName);
		}
	}

	@Override
	public void filtObj(LinkedHashMap<ColumnName, Object> filterMap) {
		if (filterMap == null || filterMap.size() == 0)
			return;

		// travers sites and filter
		ColumnName filterName;
		Object filterValue;
		for (Entry<ColumnName, Object> entry : filterMap.entrySet()) {
			filterName = entry.getKey();
			if (filterName == null)
				continue;
			filterValue = entry.getValue();
			filterSite(sites, filterName, filterValue);
		}
	}

	private void filterSite(Sites sites, ColumnName filterName,
			Object filterValue) {
		List<Site> siteList;
		siteList = sites.getSite();
		int i = 0;
		while (i < siteList.size()) {
			Site site = siteList.get(i);

			// delete and continue if meet conditions
			if (ColumnName.SITE_NAME.equals(filterName)) {
				if (!(site.getName().equals(filterValue))) {
					siteList.remove(i);
					continue;
				}
			} else if (ColumnName.SITE_ID.equals(filterName)) {
				if (!(site.getId().intValue() == ((Integer) filterValue))) {
					siteList.remove(i);
					continue;
				}
			} else if (ColumnName.SITE_LOCATION.equals(filterName)) {
				if (!(site.getLocation().equals(filterValue))) {
					siteList.remove(i);
					continue;
				}
			} else
				System.err.println(filterName.toString()
						+ " is not supported in this filtering.");
			i++;
		}
	}

	@Override
	public void sortObj(
			LinkedHashMap<ColumnName, ColumnName.SortingType> sortMap) {
		if (sortMap == null || sortMap.size() == 0)
			return;

		class HostComparator implements Comparator<Host> {
			LinkedHashMap<ColumnName, ColumnName.SortingType> map = new LinkedHashMap<ColumnName, ColumnName.SortingType>();

			public HostComparator(LinkedHashMap<ColumnName, SortingType> sortMap) {
				map = sortMap;
			}

			@Override
			public int compare(Host arg0, Host arg1) {
				for (Entry<ColumnName, SortingType> entry : map.entrySet()) { // support
																				// multiple
																				// sorts.
					if (entry.getKey() == null) {
						System.err.println("sorting filed name cannot be null");
						return 0;
					}
					@SuppressWarnings("unused")
					String sortName;
					sortName = "";
					ColumnName fnKey = entry.getKey();
					if (fnKey != null)
						sortName = fnKey.toString();
					String sortType = "";
					if (entry.getValue() != null)
						sortType = entry.getValue().toString();

					int ret = 0;
					int reverse = 1;
					if (sortType != null
							&& sortType.toString().equals("descending"))
						reverse = -1;

					if (ColumnName.HOST_ID.equals(fnKey)) {
						ret = reverse * (arg0.getId().compareTo(arg1.getId()));
					} else if (ColumnName.HOST_NAME.equals(fnKey)) {
						ret = reverse
								* (arg0.getHostName().compareTo(arg1
										.getHostName()));
					} else if (ColumnName.IP_ADDRESS.equals(fnKey)) {
						ret = reverse
								* (arg0.getIPAddress().compareTo(arg1
										.getIPAddress()));
					} else if (ColumnName.XML_OS.equals(fnKey)) {
						ret = reverse * (arg0.getOS().compareTo(arg1.getOS()));
					} else if (ColumnName.LOAD_AVG_1MIN.equals(entry.getKey())) {
						float tmp = reverse
								* (arg0.getLoadAvg1Min() - (arg1
										.getLoadAvg1Min()));
						ret = (tmp > 0) ? 1 : ((tmp < 0) ? -1 : 0);
					} else if (ColumnName.LOAD_AVG_5MIN.equals(fnKey)) {
						float tmp = reverse
								* (arg0.getLoadAvg5Min() - (arg1
										.getLoadAvg5Min()));
						ret = (tmp > 0) ? 1 : ((tmp < 0) ? -1 : 0);
					} else if (ColumnName.LOAD_AVG_15MIN.equals(fnKey)) {
						float tmp = reverse
								* (arg0.getLoadAvg15Min() - (arg1
										.getLoadAvg15Min()));
						ret = (tmp > 0) ? 1 : ((tmp < 0) ? -1 : 0);
					} else {
						continue; // not in host level fields, continue for next
									// one.
					}

					if (ret != 0)
						return ret;
				} // of map
				return 0;
			}
		}
		;

		class SieComparator implements Comparator<Site> {
			LinkedHashMap<ColumnName, ColumnName.SortingType> map = new LinkedHashMap<ColumnName, SortingType>();

			public SieComparator(LinkedHashMap<ColumnName, SortingType> sortMap) {
				map = sortMap;
			}

			@Override
			public int compare(Site arg0, Site arg1) {
				for (Map.Entry<ColumnName, ColumnName.SortingType> entry : map
						.entrySet()) { // support multiple sorts.
					if (entry.getKey() == null) {
						System.err.println("sorting filed name cannot be null");
						return 0;
					}
					@SuppressWarnings("unused")
					String sortName = "";
					ColumnName fnKey = entry.getKey();
					sortName = fnKey.toString();
					String sortType = "";
					if (entry.getValue() != null)
						sortType = entry.getValue().toString();

					int ret = 0;
					int reverse = 1;
					if (sortType != null && sortType.equals("descending"))
						reverse = -1;

					if (ColumnName.SITE_ID.equals(fnKey)) {
						ret = reverse * (arg0.getId().compareTo(arg1.getId()));
					} else if (ColumnName.SITE_NAME.equals(fnKey)) {
						ret = reverse
								* (arg0.getName().compareTo(arg1.getName()));
					} else if (ColumnName.SITE_LOCATION.equals(fnKey)) {
						ret = reverse
								* (arg0.getLocation().compareTo(arg1
										.getLocation()));
					} else
						continue;
					if (ret != 0)
						return ret;
				} // of map
				return 0;
			}
		}
		;

		List<Host> hostList;
		SieComparator siteComparator = new SieComparator(sortMap);
		Collections.sort(sites.getSite(), siteComparator);

		HostComparator hostComparator = new HostComparator(sortMap);
		for (int i = 0; i < sites.getSite().size(); i++) {
			hostList = sites.getSite().get(i).getHosts().getHost();
			Collections.sort(hostList, hostComparator);
		}
	}

	@Override
	public void obj2File() {
		OutputStream os = null;
		if (fileName == null || fileName.equals("")) {
			os = System.out;
		} else {
			File f = new File(fileName);
			try {
				os = new FileOutputStream(f);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				os = System.out;
			}
		}
		try {

			/**
			 * JAXBContext to convert a Java content tree back into XML data
			 */
			JAXBContext jaxbContext = JAXBContext.newInstance(Sites.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.TRUE);
			marshaller.marshal(sites, os);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void fromExchange(Sites sites) {
		this.sites = sites;
	}

	@Override
	public Sites toExchange() {
		return sites;
	}

}