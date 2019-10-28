package survey;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tools.IO;

public class NextGameFrame extends JFrame implements KeyListener {
	private static final long serialVersionUID = 1L;
	public JLabel nextGameLabel;
	public JButton againButton;
	public JButton nextButton;
	public JPanel pane;

	public NextGameFrame() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Next Game?");
		this.setLocation(300, 100);
		this.addKeyListener(this);
	}

	@Override
	protected void frameInit() {
		super.frameInit();

		againButton = new JButton("Same Level");
		againButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Runner.mouseClick = RunnerEnum.SAME;
			}
		});
		againButton.setFocusable(false);
		
		nextButton = new JButton("Next Level");
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Runner.mouseClick = RunnerEnum.NEXT;
			}
		});
		
		IO reader = new IO();
		String[] lines = reader.readFile("tutorial/nextgame.txt");
		String text = "";
		for (String s : lines) {
			text += s.trim();
		}
		nextGameLabel = new JLabel(text);
		nextGameLabel.setHorizontalAlignment(JLabel.CENTER);

		pane = (JPanel) getContentPane();
		pane.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		pane.setAlignmentY(JPanel.CENTER_ALIGNMENT);
		GroupLayout gl = new GroupLayout(pane);
		pane.setLayout(gl);

		pane.setToolTipText("Content pane");
		gl.setAutoCreateContainerGaps(true);
		gl.setAutoCreateGaps(true);

		gl.setHorizontalGroup(gl.createParallelGroup(Alignment.CENTER).addComponent(nextGameLabel)
				.addComponent(againButton).addGap(300));

		gl.setVerticalGroup(
				gl.createSequentialGroup().addComponent(nextGameLabel).addGap(20).addComponent(againButton));

		pack();
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
