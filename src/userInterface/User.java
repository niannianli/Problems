package userInterface;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.LinkedHashMap;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;
import transferFormat.TransferDetailImplement;
import java.awt.Component;
import java.awt.GridLayout;
import fileFormatter.ColumnName;
import javax.swing.JTextArea;

public class User extends JFrame {

	private static final long serialVersionUID = 1L;

	// Main pane, every component here
	private JPanel contentPane;

	// for sort and filter selection

	// sort Name: ColumnName.HOST_ID for example
	private JList list;
	// sort Type: ColumnName.ASCENDING for example
	private JList list_3;
	// filter Name: ColumnName.SITE_NAME for example
	private JList list_1;
	// filter Content: Object: "NY-01" for example
	private JList list_5;
	// transfer to: output file format: csv(.txt) or xml(.xml)
	private JList list_2;

	private String inputFilePath; // parameter for our
	private File outputFile; // transferNow(inputFilePath,
	// outputFilePath) function
	JTextArea textArea;

	public static LinkedHashMap<ColumnName, ColumnName.SortingType> sorter1;
	public static LinkedHashMap<ColumnName, Object> filter1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					User frame = new User();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public User() {

		// main panel: contentPane
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		// filter and sorter
		filter1 = new LinkedHashMap<ColumnName, Object>();
		sorter1 = new LinkedHashMap<ColumnName, ColumnName.SortingType>();

		// panel to select our file
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);

		JButton btnSelectFilecsvOr = new JButton("Select Local File(.txt(csv file) or .xml)");
		btnSelectFilecsvOr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JFileChooser openFile = new JFileChooser();
				int returnVal = openFile.showOpenDialog(contentPane);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File inputFile = openFile.getSelectedFile();
					inputFile.setReadable(true);
					inputFile.setWritable(true);
					inputFilePath = inputFile.getAbsolutePath();
					System.out.println("Input File Path " + inputFilePath);

					System.out.println("File to open " + inputFile);
				} else {
					System.out.println("Operation is CANCELLED.");
					try {
						User frame = new User();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		panel.add(btnSelectFilecsvOr);

		// panel_3 to set sort and filter, we put it here,
		// because we may set sort and filter for more than once in one pass
		JPanel panel_3 = new JPanel();
		contentPane.add(panel_3, BorderLayout.SOUTH);

		JButton btnTransferAndSave = new JButton("Set Sort or Filter");
		btnTransferAndSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ColumnName sortName = null;

				try {

					if (list.getSelectedValue().equals("host_id")) {
						sortName = ColumnName.HOST_ID;
					} else if (list.getSelectedValue().equals("host_name")) {
						sortName = ColumnName.HOST_NAME;
					} else if (list.getSelectedValue().equals("operative_system")) {

						if (inputFilePath.endsWith(".xml")) {
							sortName = ColumnName.XML_OS;
						} else if (inputFilePath.endsWith(".txt")) {
							sortName = ColumnName.CSV_OS;
						}

					} else if (list.getSelectedValue().equals("load_avg_1min")) {
						sortName = ColumnName.LOAD_AVG_1MIN;
					} else if (list.getSelectedValue().equals("none")) {
						sortName = null;
					} else {
						JOptionPane.showMessageDialog(contentPane, "You forgot to select a sortName or none");
					}

				} catch (Exception e1) {
					e1.printStackTrace();
				}

				// test message
				System.out.println("test sortName success!");

				ColumnName.SortingType sortType = null;

				try {

					if (list_3.getSelectedValue().equals("ascending")) {
						sortType = ColumnName.SortingType.ASCENDING;
					} else if (list_3.getSelectedValue().equals("descending")) {
						sortType = ColumnName.SortingType.DESCENDING;
					} else if (list_3.getSelectedValue().equals("none")) {

						// set default behavior is ascending
						sortType = ColumnName.SortingType.ASCENDING;
					} else {
						JOptionPane.showMessageDialog(contentPane, "You fogot to select a sortType or none");
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				// test message
				System.out.println("test sortType success.");

				// if we get our sortName and sortType, we can set our sorter
				// if we click again, we will set again
				if (sortName != null && sortType != null) {
					sorter1.put(sortName, sortType);
				}

				// test message
				System.out.println("test sorter success.");

				ColumnName filterName = null;

				try {

					if (list_1.getSelectedValue().equals("site_name")) {
						filterName = ColumnName.SITE_NAME;
					} else if (list_1.getSelectedValue().equals("none")) {
						filterName = null;
					} else {
						JOptionPane.showMessageDialog(contentPane, "You forgot to select a filterName or none");
					}

				} catch (Exception e1) {
					e1.printStackTrace();
				}

				// test message
				System.out.println("test filterName success.");

				Object filterContent = "";

				try {

					if (list_5.getSelectedValue().equals("NY-01")) {
						filterContent = "NY-01";
					} else if (list_5.getSelectedValue().equals("CA-01")) {
						filterContent = "CA-01";
					} else if (list_5.getSelectedValue().equals("none")) {
						filterContent = "";
					} else {
						JOptionPane.showMessageDialog(contentPane, "You forgot to select a filterContent or none");
					}

				} catch (Exception e1) {
					e1.printStackTrace();
				}

				// test message
				System.out.println("test filterContent success.");

				// set filter
				if (filterName == null || filterContent.equals("")) {

					// no not use filter
					System.out.println("empty filter");
				} else {
					filter1.put(filterName, filterContent);
				}
			}
		});

		panel_3.add(btnTransferAndSave);

		// after our sorter and filter set, we begin transfer file format
		JButton btnSaveNewFile = new JButton("Save New File");
		btnSaveNewFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String outputFileFormat = null;

				try {

					if (list_2.getSelectedValue().equals("csv")) {
						outputFileFormat = "csv";
					} else if (list_2.getSelectedValue().equals("xml")) {
						outputFileFormat = "xml";
					} else {
						JOptionPane.showMessageDialog(contentPane,
								"You forgot to select an output format: xml or csv.");
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

				// only not null, we can go one step further
				if (outputFileFormat != null) {

					String outputFileName = null;

					try {

						if ((outputFileFormat.equals("xml"))) {
							outputFileName = "output.xml";
						} else if (outputFileFormat.equals("csv")) {
							outputFileName = "output.txt";
						} else {
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

					// only not null, we can go one step further
					if (outputFileName != null) {

						outputFile = null;

						try {
							String outputFilePath = inputFilePath.substring(0, inputFilePath.length() - 4) + "_"
									+ outputFileName;
									// outputFile = new File(outputFilePath);

							// test output file create success
							outputFile = new File(outputFilePath);
							outputFile.createNewFile();
							outputFile.setReadable(true);
							outputFile.setWritable(true);
							System.out.println("outputFileName" + outputFile.getName());

							TransferDetailImplement tDImplement = new TransferDetailImplement();
							tDImplement.transferNow(inputFilePath, outputFilePath);
							// outputFile = new File();
							System.out.println("outputFilePath" + outputFilePath);
							outputFile = new File(outputFilePath);

						} catch (Exception e) {
						}

						JFileChooser saveFile = new JFileChooser();

						saveFile.setSelectedFile(outputFile);

						int returnVal = saveFile.showSaveDialog(contentPane);

						if (returnVal == JFileChooser.APPROVE_OPTION) {

							outputFile = saveFile.getSelectedFile();
							System.out.println("File to save: " + outputFile);

						} else {
							System.out.println("Operation is CANCELLED.");
							try {
								User frame = new User();
								frame.setVisible(true);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}

		});
		panel_3.add(btnSaveNewFile);

		JButton btnDoItAgain = new JButton("Do It Again");
		btnDoItAgain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							User frame = new User();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});

		JPanel panel_9 = new JPanel();
		panel_3.add(panel_9);

		textArea = new JTextArea();
		panel_9.add(textArea);

		panel_3.add(btnDoItAgain);

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				System.exit(0);
			}
		});
		panel_3.add(btnExit);

		JPanel panel_4 = new JPanel();
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.Y_AXIS));
		contentPane.add(panel_4, BorderLayout.EAST);

		JLabel lblTransferOption = new JLabel("Transfer To:");
		lblTransferOption.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_4.add(lblTransferOption);

		list_2 = new JList();
		list_2.setModel(new AbstractListModel() {

			private static final long serialVersionUID = 1L;
			String[] values = new String[] { "csv", "xml" };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});

		list_2.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		panel_4.add(list_2);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.WEST);
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));

		JPanel panel_5 = new JPanel();
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.Y_AXIS));
		panel_1.add(panel_5);

		JLabel lblNewLabel = new JLabel("Sort Name:   ");
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_5.add(lblNewLabel);

		list = new JList();
		list.setModel(new AbstractListModel() {

			private static final long serialVersionUID = 1L;
			String[] values = new String[] { "host_id", "host_name", "operative_system", "load_avg_1min", "none" };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});

		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		panel_5.add(list);

		JPanel panel_6 = new JPanel();
		panel_6.setLayout(new BoxLayout(panel_6, BoxLayout.Y_AXIS));
		panel_1.add(panel_6);

		JLabel lblNewLabel_1 = new JLabel("Sort Type:   ");
		lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_6.add(lblNewLabel_1);

		list_3 = new JList();
		list_3.setModel(new AbstractListModel() {

			private static final long serialVersionUID = 1L;
			String[] values = new String[] { "ascending", "descending", "none" };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});

		list_3.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		panel_6.add(list_3);

		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.CENTER);

		JPanel panel_7 = new JPanel();
		panel_7.setLayout(new BoxLayout(panel_7, BoxLayout.Y_AXIS));
		panel_2.add(panel_7);

		JLabel lblFilterName = new JLabel("Filter Name:   ");
		panel_7.add(lblFilterName);

		list_1 = new JList();
		list_1.setAlignmentX(Component.LEFT_ALIGNMENT);
		list_1.setModel(new AbstractListModel() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			String[] values = new String[] { "site_name", "none" };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});

		list_1.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		panel_7.add(list_1);

		JPanel panel_8 = new JPanel();
		panel_8.setLayout(new BoxLayout(panel_8, BoxLayout.Y_AXIS));
		panel_2.add(panel_8);

		JLabel lblFilterContent = new JLabel("Filter Content:   ");
		panel_8.add(lblFilterContent);

		list_5 = new JList();
		list_5.setAlignmentX(Component.LEFT_ALIGNMENT);
		list_5.setModel(new AbstractListModel() {

			private static final long serialVersionUID = 1L;
			String[] values = new String[] { "NY-01", "CA-01", "none" };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});

		list_5.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		panel_8.add(list_5);
	}
}