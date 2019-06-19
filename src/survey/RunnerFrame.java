package survey;

import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.ImageIcon;

import tools.IO;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;

public class RunnerFrame extends JFrame implements KeyListener {
	private static final long serialVersionUID = 1L;

	public static String ageValue = "-1";
	public static String genderValue = "unknown";
	public static String gamerValue = "-1";

	public JSeparator submissionSep;
	public JLabel optionalLabel;
	public JLabel tutorialLabel;
	public JButton startButton;
	public ButtonGroup gender;
	public ButtonGroup age;
	public ButtonGroup gamer;
	public ButtonGroup game;
	private JLabel question1;
	private JLabel question2;
	private JLabel question3;
	private JLabel question4;
	public JPanel genderPanel;
	public JPanel agePanel;
	public JPanel gamerPanel;
	public JPanel gamePanel;
	private JCheckBox[] checkboxes;

	private JButton submitButton;


	private ArrayList<JRadioButton> radioButtons;

	public RunnerFrame(String[] mechanics) {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocation(300, 100);
		this.addKeyListener(this);
		this.checkboxes = new JCheckBox[mechanics.length];
		for (int i = 0; i < mechanics.length; i++) {
			this.checkboxes[i] = new JCheckBox(mechanics[i]);
		}

		radioButtons = new ArrayList<JRadioButton>();

		startButton = new JButton("Play Level");
		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Runner.mouseClick = RunnerEnum.TUTORIAL;
			}
		});
		startButton.setFocusable(false);

		optionalLabel = new JLabel(
				"<html><div align=\"center\">(Mandatory) Play to understand the game.<br/>Use the only the ARROW KEYS and SPACE BAR as controls</div></html>");
		optionalLabel.setHorizontalAlignment(JLabel.CENTER);

		tutorialLabel = new JLabel();
		tutorialLabel.setHorizontalAlignment(JLabel.CENTER);

		submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Runner.mouseClick = RunnerEnum.SUBMIT;
				// replyToGoogleForm();
				saveToTSV();
			}
		});
		submitButton.setFocusable(false);
		question1 = new JLabel("How do you identify yourself?");

		// Demographics groups
		gender = new ButtonGroup();
		age = new ButtonGroup();
		gamer = new ButtonGroup();
		game = new ButtonGroup();

		JRadioButton male = new JRadioButton("male");
		JRadioButton female = new JRadioButton("female");
		JRadioButton nonbi = new JRadioButton("non-binary");
		JRadioButton other = new JRadioButton("other");
		male.setActionCommand("0");
		female.setActionCommand("1");
		nonbi.setActionCommand("2");
		other.setActionCommand("3");
		genderPanel = new JPanel((LayoutManager) new FlowLayout(FlowLayout.CENTER));
		gender.add(female);
		gender.add(male);
		gender.add(nonbi);
		gender.add(other);
		genderPanel.add(female);
		genderPanel.add(male);
		genderPanel.add(nonbi);
		genderPanel.add(other);
		radioButtons.add(female);
		radioButtons.add(male);
		radioButtons.add(other);

		question2 = new JLabel("Which age group do you fit within?");
		JRadioButton firstGroup = new JRadioButton("<25");
		JRadioButton secondGroup = new JRadioButton("25-34");
		JRadioButton thirdGroup = new JRadioButton("35-44");
		JRadioButton fourthGroup = new JRadioButton("45-54");
		JRadioButton fifthGroup = new JRadioButton("55+");
		firstGroup.setActionCommand("0");
		secondGroup.setActionCommand("1");
		thirdGroup.setActionCommand("2");
		fourthGroup.setActionCommand("3");
		fifthGroup.setActionCommand("4");

		agePanel = new JPanel((LayoutManager) new FlowLayout(FlowLayout.CENTER));
		age.add(firstGroup);
		age.add(secondGroup);
		age.add(thirdGroup);
		age.add(fourthGroup);
		age.add(fifthGroup);
		agePanel.add(firstGroup);
		agePanel.add(secondGroup);
		agePanel.add(thirdGroup);
		agePanel.add(fourthGroup);
		agePanel.add(fifthGroup);
		radioButtons.add(firstGroup);
		radioButtons.add(secondGroup);
		radioButtons.add(thirdGroup);
		radioButtons.add(fourthGroup);
		radioButtons.add(fifthGroup);

		question3 = new JLabel("Which type of gamer best describes you?");
		JRadioButton firstGamer = new JRadioButton("Don't play games");
		JRadioButton secondGamer = new JRadioButton("Casual gamer");
		JRadioButton thirdGamer = new JRadioButton("Play quite often");
		JRadioButton fourthGamer = new JRadioButton("Play games everyday");
		firstGamer.setActionCommand("0");
		secondGamer.setActionCommand("1");
		thirdGamer.setActionCommand("2");
		fourthGamer.setActionCommand("3");
		gamerPanel = new JPanel((LayoutManager) new FlowLayout(FlowLayout.CENTER));
		gamer.add(firstGamer);
		gamer.add(secondGamer);
		gamer.add(thirdGamer);
		gamer.add(fourthGamer);
		gamerPanel.add(firstGamer);
		gamerPanel.add(secondGamer);
		gamerPanel.add(thirdGamer);
		gamerPanel.add(fourthGamer);
		radioButtons.add(firstGamer);
		radioButtons.add(secondGamer);
		radioButtons.add(thirdGamer);
		radioButtons.add(fourthGamer);

		question4 = new JLabel("Which level did you like playing the most?");
		JRadioButton firstGame = new JRadioButton("1");
		JRadioButton secondGame = new JRadioButton("2");
		JRadioButton thirdGame = new JRadioButton("3");
		JRadioButton fourthGame = new JRadioButton("4");

		firstGame.setActionCommand("0");
		secondGame.setActionCommand("1");
		thirdGame.setActionCommand("2");
		fourthGame.setActionCommand("3");
		gamePanel = new JPanel((LayoutManager) new FlowLayout(FlowLayout.CENTER));
		game.add(firstGame);
		game.add(secondGame);
		game.add(thirdGame);
		game.add(fourthGame);
		gamePanel.add(firstGame);
		gamePanel.add(secondGame);
		gamePanel.add(thirdGame);
		gamePanel.add(fourthGame);
		radioButtons.add(firstGame);
		radioButtons.add(secondGame);
		radioButtons.add(thirdGame);
		radioButtons.add(fourthGame);

		submissionSep = new JSeparator();

		JPanel pane = (JPanel) getContentPane();
		pane.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		pane.setAlignmentY(JPanel.CENTER_ALIGNMENT);
		GroupLayout gl = new GroupLayout(pane);
		pane.setLayout(gl);

		pane.setToolTipText("Content pane");
		gl.setAutoCreateContainerGaps(true);
		gl.setAutoCreateGaps(true);

		ParallelGroup horizontalGroup = gl.createParallelGroup(Alignment.CENTER).addComponent(tutorialLabel)
				.addComponent(optionalLabel).addComponent(startButton).addComponent(submissionSep).addComponent(question1);

		horizontalGroup.addComponent(genderPanel).addComponent(question2);
		horizontalGroup.addComponent(agePanel).addComponent(question3);
		// horizontalGroup.addComponent(gamerPanel);

		horizontalGroup.addComponent(gamerPanel).addComponent(submissionSep).addComponent(question4).addComponent(gamePanel);

		horizontalGroup = horizontalGroup.addComponent(submitButton);

		SequentialGroup verticalGroup = gl.createSequentialGroup().addComponent(tutorialLabel).addComponent(optionalLabel)
				.addComponent(startButton).addComponent(submissionSep).addComponent(question1).addComponent(genderPanel)
				.addComponent(question2).addComponent(agePanel).addComponent(question3).addComponent(gamerPanel)
				.addComponent(submissionSep).addComponent(question4).addComponent(gamePanel);

		// for (JCheckBox box : this.checkboxes) {
		// verticalGroup = verticalGroup.addComponent(box);
		// }

		verticalGroup = verticalGroup.addComponent(submitButton);

		gl.setVerticalGroup(verticalGroup);
		gl.setHorizontalGroup(horizontalGroup);
		pack();
	}

	public void setSubmitEnable(boolean enable) {
		submitButton.setEnabled(enable);
		agePanel.setEnabled(enable);
		genderPanel.setEnabled(enable);
		gamerPanel.setEnabled(enable);
		for (JCheckBox box : this.checkboxes) {
			box.setEnabled(enable);
		}
		for (JRadioButton button : this.radioButtons) {
			button.setEnabled(enable);
		}


		question1.setEnabled(enable);
		question2.setEnabled(enable);
		question3.setEnabled(enable);
		question4.setEnabled(enable);
	}

	public void setPlayEnable(boolean enable) {
		optionalLabel.setEnabled(enable);
		startButton.setEnabled(enable);
	}

	public void resetCheckBoxes() {
		this.setSubmitEnable(false);
		for (JCheckBox box : this.checkboxes) {
			box.setSelected(false);
		}


		setPlayEnable(true);
	}

	public void staticizeRadioButtons() {
		ageValue = age.getSelection().getActionCommand();
		genderValue = gender.getSelection().getActionCommand();
		gamerValue = gamer.getSelection().getActionCommand();

	}

	private void replyToGoogleForm() {
		this.setSubmitEnable(false);

		IO linkReader = new IO();
		String[] data = linkReader.readFile("submissionLinks.txt");
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Can not connect to the server! Check your internet connection");
		}
		Runner.submissionDone = true;
		this.resetCheckBoxes();
	}

	public void saveToTSV() {
		this.setSubmitEnable(false);
		System.out.println("Saving to TSV!");
		try {

			FileWriter fos;
			fos = new FileWriter("results.tsv", true);
			PrintWriter dos = new PrintWriter(fos);
			dos.print("blank\t");
			dos.print(Runner.id + "\t");
			dos.print(Runner.games.get(Runner.chosenGame) + "\t");
			dos.print(getMechs(0) + "\t" + getMechs(1) + "\t" + getMechs(2) + "\t");
			dos.print(getChoices() + "\t");
			dos.print(getResults(0) + "\t" + getResults(1) + "\t" + getResults(2) + "\t");
			dos.print(getActions(0) + "\t" + getActions(1) + "\t" + getActions(2) + "\t");
			dos.print(gender.getSelection().getActionCommand() + "\t");
			dos.print(age.getSelection().getActionCommand() + "\t");
			dos.print(gamer.getSelection().getActionCommand() + "\n");
			dos.close();
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Runner.submissionDone = true;
		this.resetCheckBoxes();
	}

	public String getMechs(int level) {
		String actionFile = Runner.games.get(Runner.chosenGame) + "/human/" + level + "/0/interactions/interaction.json";
		String result = "";
		IO reader = new IO();
		String[] lines = reader.readFile(actionFile);
		for (int i = 0; i < lines.length; i++) {
			result += lines[i] + ",";
		}
		return result;
	}

	public String getActions(int level) {
		String actionFile = Runner.games.get(Runner.chosenGame) + "/human/" + level + "/0/actions/actions.json";
		String result = "";
		IO reader = new IO();
		String[] lines = reader.readFile(actionFile);
		for (int i = 0; i < lines.length; i++) {
			result += lines[i] + ",";
		}
		return result;
	}

	public String getResults(int level) {
		String actionFile = Runner.games.get(Runner.chosenGame) + "/human/" + level + "/0/result/result.json";
		String result = "";
		IO reader = new IO();
		String[] lines = reader.readFile(actionFile);
		for (int i = 0; i < lines.length; i++) {
			result += lines[i] + ",";
		}
		return result;
	}

	public String getChoices() {
		String result = "";
		// for (JCheckBox box : this.checkboxes) {
		// if (box.isSelected()) {
		// result += box.getText() + ",";
		// }
		// }


		return result;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}
}
