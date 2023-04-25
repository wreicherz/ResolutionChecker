package de.db1337.ResolutionCheck;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class VideoResolutionCheckerGUI {

	private JFrame frame;
	private JLabel folderLabel;
	private JButton folderButton;
	private JList<String> resultListView;
	private JScrollPane resultScrollPane;
	private JButton searchButton;
	private JPanel mainPanel;
	private JPanel folderPanel;
	private JPanel resultPanel;
	private List<String> errorMessages;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				VideoResolutionCheckerGUI window = new VideoResolutionCheckerGUI();
				window.frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public VideoResolutionCheckerGUI() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame("Low Resolution Video Finder");
		frame.setBounds(100, 100, 400, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainPanel = new JPanel();
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BorderLayout(0, 0));

		folderPanel = new JPanel();
		mainPanel.add(folderPanel, BorderLayout.NORTH);

		folderLabel = new JLabel("Select a folder:");
		folderPanel.add(folderLabel);

		folderButton = new JButton("Browse");
		folderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int result = fileChooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					File folder = fileChooser.getSelectedFile();
					folderLabel.setText(folder.getAbsolutePath());
				}
			}
		});
		folderPanel.add(folderButton);
		String[] resolutions = { "240", "480", "720", "1080", "2160" };
		JComboBox<String> resolutionDropdown = new JComboBox<>(resolutions);
		folderPanel.add(resolutionDropdown);

		searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String folderPath = folderLabel.getText();
				File folder = new File(folderPath);
				Integer minheight = Integer.valueOf(resolutions[resolutionDropdown.getSelectedIndex()]);
				VideoResolutionUtil util = new VideoResolutionUtil();
				errorMessages = util.startProcessingParallel(minheight, util.findVideos(folder));
				resultListView.setListData(errorMessages.toArray(new String[0]));
			}
		});
		folderPanel.add(searchButton);

		resultPanel = new JPanel();
		mainPanel.add(resultPanel, BorderLayout.CENTER);
		resultPanel.setLayout(new BorderLayout(0, 0));

		resultScrollPane = new JScrollPane();
		resultPanel.add(resultScrollPane, BorderLayout.CENTER);

		resultListView = new JList<String>();
		resultScrollPane.setViewportView(resultListView);
	}

	
}