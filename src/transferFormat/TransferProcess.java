package transferFormat;

import java.util.LinkedHashMap;
import fileFormatter.AbstractFormatter;
import fileFormatter.ColumnName;

/**
 * The abstract way to show how our transfer works...
 */
public class TransferProcess {

	// 1, get source and target format...

	/*
	 * source format
	 */
	private AbstractFormatter source;

	/*
	 * target format
	 */
	private AbstractFormatter target;

	// 2, do our sort and filter...

	/*
	 * sorting conditions of the transfer from the target format point of view
	 */
	private LinkedHashMap<ColumnName, ColumnName.SortingType> sorter;

	/*
	 * filtering conditions of the transfer from the target format point of view
	 */
	private LinkedHashMap<ColumnName, Object> filter;

	// 3, getter and setters...

	public AbstractFormatter getSource() {
		return source;
	}

	public void setSource(AbstractFormatter source) {
		this.source = source;
	}

	public AbstractFormatter getTarget() {
		return target;
	}

	public void setTarget(AbstractFormatter target) {
		this.target = target;
	}

	public LinkedHashMap<ColumnName, ColumnName.SortingType> getSorter() {
		return sorter;
	}

	public void setSorter(
			LinkedHashMap<ColumnName, ColumnName.SortingType> sorter) {
		this.sorter = sorter;
	}

	public LinkedHashMap<ColumnName, Object> getFilter() {
		return filter;
	}

	public void setFilter(LinkedHashMap<ColumnName, Object> filter) {
		this.filter = filter;
	}
}