package transferFormat;

import userInterface.User;
import fileFormatter.CSVFormatter;
import fileFormatter.XMLFormatter;

/**
 * I only create this class for test, for both directions for xml and csv file;
 * 
 * if we are going to use more file formats, please use the Factory design
 * pattern to set our file inputFormat and outputFormat, then use this detail
 * function to do the transfer.
 * 
 * Or abstract class: transferNow(String inputFileFormat, String
 * outputFileFormat);
 * 
 * I have one more inputFilePath here, because I use a UserInterface to open,
 * modify and save my file
 * 
 * So this class is considered as a controller to connect my model(only datas)
 * and my view(only user interface)
 */
public class TransferDetailImplement {

	public void transferNow(String inputFilePath, String outputFilePath) throws Exception {
		
		TransferProcess tc;
		TransferFromToFiles formatTransfer;
		
		// From xml to xml; csv to csv; xml to csv; csv to xml
		if (inputFilePath.endsWith(".xml") && outputFilePath.endsWith(".xml")) {
			
			System.out.println("did I get here? xml to xml");
			tc = new TransferProcess();
			tc.setSource(new XMLFormatter(inputFilePath));
			tc.setTarget(new XMLFormatter(outputFilePath));
	
			tc.setFilter(User.filter1);
			tc.setSorter(User.sorter1);
			
			formatTransfer = new TransferFromToFiles();
			formatTransfer.transfer(tc);
			System.out.println("did I finish here? xml to xml");
		} else if (inputFilePath.endsWith(".txt")
				&& outputFilePath.endsWith(".txt")) {

			System.out.println("did I get here? txt to txt, csv to csv");
			
			tc = new TransferProcess();
			tc.setSource(new CSVFormatter(inputFilePath));
			tc.setTarget(new CSVFormatter(outputFilePath));
			
			tc.setFilter(User.filter1);
			tc.setSorter(User.sorter1);
			
			formatTransfer = new TransferFromToFiles();
			formatTransfer.transfer(tc);
			
			System.out.println("did I finish here? txt to txt, csv to csv");
			
		} else if (inputFilePath.endsWith(".xml")
				&& outputFilePath.endsWith(".txt")) {
			System.out.println("did I get here? xml to txt");
			tc = new TransferProcess();
			tc.setSource(new XMLFormatter(inputFilePath));
			tc.setTarget(new CSVFormatter(outputFilePath));
			
			tc.setFilter(User.filter1);
			tc.setSorter(User.sorter1);
			
			formatTransfer = new TransferFromToFiles();
			formatTransfer.transfer(tc);
			System.out.println("did I finish here? xml to txt");
			
		} else if (inputFilePath.endsWith(".txt")
				&& outputFilePath.endsWith(".xml")) {
	
			System.out.println("did I get here? txt to xml");
			tc = new TransferProcess();
			
			tc.setSource(new CSVFormatter(inputFilePath));
			tc.setTarget(new XMLFormatter(outputFilePath));
			
			tc.setFilter(User.filter1);
			tc.setSorter(User.sorter1);
			
			formatTransfer = new TransferFromToFiles();
			formatTransfer.transfer(tc);
			System.out.println("did I finishe here? txt to xml");
		} else {
			try {
				
			}catch( Exception e) {
				System.out.println("there is something wrong!");
			}
		}
	}
}