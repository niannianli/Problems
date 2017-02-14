package transferFormat;

import java.util.LinkedHashMap;
import fileFormatter.AbstractFormatter;
import fileFormatter.ColumnName;
import fileFormatter.ColumnName.SortingType;

/**
 * We transfer from one file format to another
 * Just define them as source and target file
 */
public class TransferFromToFiles {

	public void transfer(TransferProcess transferCritera) {
		if (transferCritera == null)
			return;

		AbstractFormatter source = transferCritera.getSource();
		AbstractFormatter target = transferCritera.getTarget();

		if (source == null || target == null || source == target) {
			System.err.println("Format source/target not properly set.");
			return;
		}

		try {
			source.file2Obj();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("error in parsing source file:"
					+ source.getFileName());
			return;
		}

		target.fromExchange(source.toExchange());
		LinkedHashMap<ColumnName, Object> filterMap = transferCritera
				.getFilter();
		LinkedHashMap<ColumnName, SortingType> sortMap = transferCritera
				.getSorter();
		target.filtObj(filterMap);
		target.sortObj(sortMap);
		target.obj2File();
	}
}