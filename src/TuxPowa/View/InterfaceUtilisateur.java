package TuxPowa.View;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class InterfaceUtilisateur {

	// Center panel
	JLabel videoPanel = new JLabel();
	JLabel frameIndicator = new JLabel();
	JPanel paneCenter = new JPanel();
	VideoPlayer videoPlayer = new VideoPlayer();
	final int paused = 0;
	final int playing = 1;
	final int endOfPlaying = 2;
	/**
	 * <p>Used to set the state of the video player.</p>
	 * <p>when videoState == 0, it's the state of paused;</p>
	 * <p>when videoState == 1, it's the state of playing;</p>
	 * <p>when videoState == 2, it's the state of end of playing;</p>
	 */
	int videoState = 0;

	// East panel
	JPanel paneEast = new JPanel();
	DefaultListModel<String> eventsListModel = new DefaultListModel<String>();
	JList<?> eventsText = new JList<>(eventsListModel);
	JScrollPane eventsShower = new JScrollPane(eventsText, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	JTextField scoreShower = new JTextField(5);

	// South panel
	JPanel paneSouth = new JPanel();
	JLabel videoProgressBarLabel = new JLabel("frames:");
	JButton playPauseButton = new JButton("play");
	JButton openFileButton = new JButton("open file");
	JButton treatButton = new JButton("treat it!");
	JTextField FrameLocator = new JTextField(3);
	JButton locateButton = new JButton("GO!");
	int startFrame = 0;
	int endFrame = 100;
	int currentFrame = 0;
	JSlider videoProgressBar = new JSlider(JSlider.HORIZONTAL, startFrame, endFrame, startFrame);

	// Main window
	ImageIcon currentImage = null;
	int UpdateRateMillisecond = 1000 / 24;
	String currentVideoName = null;
	JFrame mainWindow = new JFrame("Babyfoot analyste");
	Timer playingControlTimer = new Timer(UpdateRateMillisecond, new updateImageAction());

	/**
	 * It's the constructor of the class InterfaceUtilisateur. After the object
	 * is initialized, it will generate a window which contains a video player,
	 * a control panel and a information area.
	 * 
	 * @author ZHANG Heng, YAN Yutong
	 * 
	 * @version 1.0
	 * 
	 * @since 1.0
	 */
	public InterfaceUtilisateur() {
		configureCenterPanel();
		configureEastPanel();
		configureSouthPanel();
		configureMainWindow();
	}

	/**
	 * Configure the center panel of the main window. This part contains a video
	 * player.
	 * 
	 * @author ZHANG Heng, YAN Yutong
	 * 
	 * @see JList
	 * @see JScrollPane
	 * @see JTextField
	 * @see TitledBorder
	 * @see JPanel
	 * @see BorderLayout
	 * 
	 * @version 1.0
	 * 
	 * @since 1.0
	 */
	public void configureCenterPanel() {
		frameIndicator.setText("frame: 0");

		paneCenter.setLayout(new BorderLayout(0, 0));
		paneCenter.add(videoPanel, BorderLayout.CENTER);
		paneCenter.add(frameIndicator, BorderLayout.SOUTH);
	}

	/**
	 * Configure the right side panel of the main window. This part contains a
	 * list of events and shows the current score.
	 * 
	 * @author ZHANG Heng, YAN Yutong
	 * 
	 * @see JList
	 * @see JScrollPane
	 * @see JTextField
	 * @see TitledBorder
	 * @see JPanel
	 * @see BorderLayout
	 * 
	 * @version 1.0
	 * 
	 * @since 1.0
	 */
	public void configureEastPanel() {
		eventsShower.setBorder(new TitledBorder(new EtchedBorder(), "evenements:"));

		scoreShower.setEditable(false);
		scoreShower.setBorder(new TitledBorder(new EtchedBorder(), "score:"));

		paneEast.setLayout(new BorderLayout(20, 20));
		paneEast.setBorder(new TitledBorder(new EtchedBorder(), "live jeu"));
		paneEast.add(eventsShower, BorderLayout.CENTER);
		paneEast.add(scoreShower, BorderLayout.SOUTH);

	}

	/**
	 * Configure the down side panel of the main window. This part contains a
	 * process bar and several control button..
	 * 
	 * @author ZHANG Heng, YAN Yutong
	 * 
	 * @see JLabel
	 * @see JSlider
	 * @see Box
	 * @see JButton
	 * @see JTextField
	 * @see BorderLayout
	 * @see JPanel
	 * 
	 * @version 1.0
	 * 
	 * @since 1.0
	 */
	public void configureSouthPanel() {

		videoProgressBar.setPaintTicks(true);
		videoProgressBar.setMajorTickSpacing(endFrame / 10);
		videoProgressBar.setMinorTickSpacing(endFrame / 20);
		videoProgressBar.setPaintLabels(true);
		videoProgressBar.setEnabled(true);
		videoProgressBar.addChangeListener(new locateFrameAction());

		Box videoProgressBarShower = Box.createHorizontalBox();
		videoProgressBarShower.add(videoProgressBarLabel);
		videoProgressBarShower.add(videoProgressBar);

		playPauseButton.addActionListener(new playPauseAction());
		openFileButton.addActionListener(new openFileAction());
		locateButton.addActionListener(new locateFrameAction());

		Box buttonBarShower = Box.createHorizontalBox();
		buttonBarShower.add(openFileButton);
		buttonBarShower.add(playPauseButton);
		buttonBarShower.add(treatButton);
		buttonBarShower.add(FrameLocator);
		buttonBarShower.add(locateButton);

		paneSouth.setLayout(new BorderLayout(20, 20));
		paneSouth.add(videoProgressBarShower, BorderLayout.CENTER);
		paneSouth.add(buttonBarShower, BorderLayout.SOUTH);
		paneSouth.setBorder(new TitledBorder(new EtchedBorder(), "video control:"));
	}

	/**
	 * Configure the main window of this application. This fonction will combine
	 * the rightside part, the centre part and the down side part.
	 * 
	 * @author ZHANG Heng, YAN Yutong
	 * 
	 * @see JLabel
	 * @see JSlider
	 * @see Box
	 * @see JButton
	 * @see JTextField
	 * @see BorderLayout
	 * @see JPanel
	 * 
	 * @version 1.0
	 * 
	 * @since 1.0
	 */
	public void configureMainWindow() {
		mainWindow.setLayout(new BorderLayout(0, 0));
		mainWindow.add(paneCenter, BorderLayout.CENTER);
		mainWindow.add(paneEast, BorderLayout.EAST);
		mainWindow.add(paneSouth, BorderLayout.SOUTH);
		mainWindow.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		mainWindow.pack();
		mainWindow.setVisible(true);
	}

	/**
	 * Things to configure when we change the state into playing;
	 * 
	 * @author ZHANG Heng, YAN Yutong
	 * 
	 * @version 1.0
	 * 
	 * @since 1.0
	 */
	void changeToPlayState() {
		playingControlTimer.start();
		playPauseButton.setText("pause");
		videoState = playing;
	}

	/**
	 * Things to configure when we change the state into paused;
	 * 
	 * @author ZHANG Heng, YAN Yutong
	 * 
	 * @version 1.0
	 * 
	 * @since 1.0
	 */
	void changeToPausedState() {
		playingControlTimer.stop();
		playPauseButton.setText("play");
		videoState = paused;
	}

	/**
	 * Things to configure when we change the state into end of playing;
	 * 
	 * @author ZHANG Heng, YAN Yutong
	 * 
	 * @version 1.0
	 * 
	 * @since 1.0
	 */
	void changeToEndOfPlaying() {
		currentFrame = 0;
		frameIndicator.setText("frame: 0");
		playPauseButton.setText("replay");
		videoProgressBar.setValue(currentFrame);
		playingControlTimer.stop();
		videoState = endOfPlaying;
	}

	/**
	 * Reaction when we press the play/pause button
	 * 
	 * @author ZHANG Heng, YAN Yutong
	 * 
	 * @version 1.0
	 * 
	 * @since 1.0
	 */
	class playPauseAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (videoState == playing) {
				changeToPausedState();
			} else if (videoState == paused) {
				if (videoPlayer.isOpened()) {
					changeToPlayState();
				} else {
					JOptionPane.showMessageDialog(mainWindow, new JLabel("please open a video file first."), "Erreur",
							JOptionPane.ERROR_MESSAGE);
				}
			} else {
				if (videoPlayer.isOpened()) {
					videoPlayer.open(currentVideoName);
					changeToPlayState();
				} else {
					JOptionPane.showMessageDialog(mainWindow, new JLabel("please open a video file first."), "Erreur",
							JOptionPane.ERROR_MESSAGE);
				}
			}

		}
	}

	/**
	 * Reaction when we relocate the position of frame of the video
	 * 
	 * @author ZHANG Heng, YAN Yutong
	 * 
	 * @version 1.0
	 * 
	 * @since 1.0
	 */
	class locateFrameAction implements ActionListener, ChangeListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String pos = FrameLocator.getText();
			if (this.frameLocationAcceptable(pos)) {
				currentFrame = Integer.valueOf(pos);
				this.locateFrame();
			} else {
				JOptionPane.showMessageDialog(mainWindow, new JLabel("invalide input"), "Erreur",
						JOptionPane.ERROR_MESSAGE);
			}
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			if (videoState == paused) {
				currentFrame = videoProgressBar.getValue();
				this.locateFrame();
			}
		}

		/**
		 * Things to configure when we relocate the video.
		 * 
		 * @author ZHANG Heng, YAN Yutong
		 * 
		 * @version 1.0
		 * 
		 * @since 1.0
		 */
		void locateFrame() {
			videoProgressBar.setValue(currentFrame);
			frameIndicator.setText("frame: " + String.valueOf(currentFrame));
			videoPlayer.setCurrentPositionFrame(currentFrame);
			if (videoPlayer.hasNextImage()) {
				currentImage = new ImageIcon(videoPlayer.nextImage());
			}
			videoPanel.setIcon(currentImage);
			videoPanel.repaint();
		}

		/**
		 * To test if the value of frame is acceptable.
		 * 
		 * @param pos
		 *            value of frame
		 * 
		 * @return true if it's acceptable
		 * 
		 * @author ZHANG Heng, YAN Yutong
		 * 
		 * @version 1.0
		 * 
		 * @since 1.0
		 */
		boolean frameLocationAcceptable(String pos) {
			try {
				int i = Integer.valueOf(FrameLocator.getText());
				if ((i >= startFrame) && (i <= endFrame))
					return true;
			} catch (Exception e) {
				return false;
			}
			return false;
		}
	}

	/**
	 * To update the screen.
	 * 
	 * @author ZHANG Heng, YAN Yutong
	 * 
	 * @version 1.0
	 *
	 * @since 1.0
	 */
	class updateImageAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (videoPlayer.hasNextImage()) {
				videoProgressBar.setValue(currentFrame);
				frameIndicator.setText("frame: " + String.valueOf(currentFrame));
				currentImage = new ImageIcon(videoPlayer.nextImage());
				videoPanel.setIcon(currentImage);
				videoPanel.repaint();
				currentFrame++;
			} else {
				changeToEndOfPlaying();
			}
		}

	}

	/**
	 * Reaction when we press the open file button.
	 * 
	 * @author ZHANG Heng, YAN Yutong
	 * 
	 * @version 1.0
	 * 
	 * @since 1.0
	 * 
	 */
	class openFileAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			FileDialog openFileDialog = new FileDialog(mainWindow, "please choose a video to open", FileDialog.LOAD);
			openFileDialog.setVisible(true);
			if (this.fileChosenAcceptable(openFileDialog.getFile())) {
				videoPlayer.close();
				currentVideoName = openFileDialog.getDirectory() + openFileDialog.getFile();
				videoPlayer.open(currentVideoName);
				currentFrame = 0;
				endFrame = videoPlayer.getVideoLengthFrame();
				UpdateRateMillisecond = 1000 / videoPlayer.getVideoFrameRate();
				videoProgressBar.setMaximum(endFrame);
				videoProgressBar.setMajorTickSpacing(endFrame / 10);
				videoProgressBar.setMinorTickSpacing(endFrame / 20);
			} else {
				JOptionPane.showMessageDialog(mainWindow, new JLabel("please open a video file"), "Erreur",
						JOptionPane.ERROR_MESSAGE);
			}

		}

		/**
		 * To test if the value of frame is usable.
		 * 
		 * @param fileName
		 *            file name
		 * 
		 * @return true if it's usable
		 * 
		 * @author ZHANG Heng, YAN Yutong
		 * 
		 * @version 1.0
		 * 
		 * @since 1.0
		 */
		boolean fileChosenAcceptable(String fileName) {
			if (fileName == null) {
				return false;
			}
			String[] extensions = { ".MP4", ".avi", ".mkv" };
			for (String extension : extensions) {
				if (fileName.endsWith(extension)) {
					return true;
				}
			}
			return false;
		}
	}

	/**
	 * Test code
	 * 
	 * @param args doesn't matter
	 * 
	 * @author ZHANG Heng, YAN Yutong
	 * 
	 * @version 1.0
	 * 
	 * @since 1.0
	 */
	public static void main(String[] args) {

		InterfaceUtilisateur interfaceUtilisateur = new InterfaceUtilisateur();

		// test code...
		for (int i = 0; i < 40; i++) {
			interfaceUtilisateur.eventsListModel
					.addElement("TeamB a marqué un but" + System.getProperty("line.separator"));
		}
		interfaceUtilisateur.scoreShower.setText("02:03");
	}

}
