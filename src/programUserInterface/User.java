package programUserInterface;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Map;
import programSolution.SolutionWithFullComments;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;

/**
 * 
 * @author niannianli
 *
 */

public class User extends JFrame {

	/**
	 * user interface
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private File inputFile;
	private SolutionWithFullComments ps;
	private JTextArea txtrHiPleaseUpload;

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
		setTitle("Text Process");
		setForeground(Color.ORANGE);
		setBackground(new Color(255, 204, 102));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);

		JButton btnUploadFile = new JButton("Upload File");
		btnUploadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JFileChooser openFile = new JFileChooser();
				int returnVal = openFile.showOpenDialog(contentPane);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					inputFile = openFile.getSelectedFile();
					inputFile.setReadable(true);
					inputFile.setWritable(true);

					System.out.println("Input File Path "
							+ inputFile.getAbsolutePath());
					System.out.println("File to open " + inputFile.getName());
					System.out.println();
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
		panel.add(btnUploadFile);

		JPanel panel_3 = new JPanel();
		panel.add(panel_3);

		// begin to process our file
		JButton btnProcessFile = new JButton("Process File");
		btnProcessFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (inputFile == null) {
					JOptionPane.showMessageDialog(null,
							"Please upload your file first!", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					ps = new SolutionWithFullComments();
					ps.run(inputFile);
				}

				txtrHiPleaseUpload.append("Processing file, done...");
				txtrHiPleaseUpload.append("\n");
			}
		});
		panel_3.add(btnProcessFile);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.WEST);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));

		// get the number of unique lines from ProgramSolution class
		btnNewButton = new JButton("number of unique lines");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (ps != null) {
					txtrHiPleaseUpload.append(String
							.valueOf("The number of unique lines is "
									+ ps.getNumberOfLines()));
					txtrHiPleaseUpload.append("\n");
					txtrHiPleaseUpload.append("\n");
				} else {
					JOptionPane.showMessageDialog(null,
							"Please process your file first!", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panel_1.add(btnNewButton);

		btnNewButton_1 = new JButton("number of unique words");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ps != null) {
					txtrHiPleaseUpload.append(String
							.valueOf("The number of unique words is "
									+ ps.getNumberOfWords()));
					txtrHiPleaseUpload.append("\n");
					txtrHiPleaseUpload.append("\n");
				} else {
					JOptionPane.showMessageDialog(null,
							"Please process your file first!", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}

		});
		panel_1.add(btnNewButton_1);

		btnNewButton_2 = new JButton("the 5 most common words");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (ps != null) {
					txtrHiPleaseUpload.append("The 5 most common words are: ");
					txtrHiPleaseUpload.append("\n");
					for (Map.Entry<String, Integer> entry : ps.getList()) {
						txtrHiPleaseUpload.append(entry.getKey() + " : "
								+ entry.getValue());
						txtrHiPleaseUpload.append("\n");
					}
					txtrHiPleaseUpload.append("\n");
				} else {
					JOptionPane.showMessageDialog(null,
							"Please process your file first!", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panel_1.add(btnNewButton_2);

		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.CENTER);

		txtrHiPleaseUpload = new JTextArea();
		txtrHiPleaseUpload
				.setText("Hi, please upload your file, process you file, then check the number of unique lines, words, the words with top 5 frequency.\nThanks.\n\n\n");
		txtrHiPleaseUpload.setColumns(30);
		txtrHiPleaseUpload.setLineWrap(true);
		txtrHiPleaseUpload.setRows(17);
		txtrHiPleaseUpload.setTabSize(200);
		txtrHiPleaseUpload.setWrapStyleWord(true);
		panel_2.add(txtrHiPleaseUpload);

		JScrollPane scroll = new JScrollPane(txtrHiPleaseUpload,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		panel_2.add(scroll);

	}
}