package survey;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import engine.core.EventLogger;
import engine.core.MarioGame;
import engine.core.MarioResult;

public class Runner {
	public static int numLevels;
	public static int playLevels;

	public static Random random;
	public static String chosenGame;
	public static int chosenLevel;
	public static double win;
	public static double score;
	public static double time;
	public static boolean submissionDone;

	public static ArrayList<String> games;

	public static RunnerEnum mouseClick;

	public static String id;

	public static int totalPlayed;
	public static int level;

	public static void main(String[] args) {
		level = 0;
		numLevels = 4;
		playLevels = 1;
		chosenGame = "";

		// make ID
		id = UUID.randomUUID().toString().replace("-", "");

		File[] files = new File("levels/mechanic-experiments/").listFiles();
		games = new ArrayList<String>();
		for (File f : files) {
			String temp = f.getName().substring(0, f.getName().lastIndexOf('.'));
			if (temp.trim().length() > 0)
				games.add(temp);
		}
		Collections.shuffle(games);
		random = new Random();
		mouseClick = RunnerEnum.NONE;

		ReasonFrame reasonFrame = new ReasonFrame();
		RunnerFrame runnerFrame = new RunnerFrame(SurveyText.getSurveyText());
		NextGameFrame nextGameFrame = new NextGameFrame();
		reasonFrame.setVisible(true);
		reasonFrame.setFocusable(true);
		while (mouseClick == RunnerEnum.NONE) {
			System.out.print("");
		}
		mouseClick = RunnerEnum.NONE;
		reasonFrame.setVisible(false);
		runnerFrame.setVisible(false);
		runnerFrame.setSubmitEnable(false);
		submissionDone = true;
		boolean done = false;
		while (true) {
			System.out.println("boop");
			while (!submissionDone) {
				System.out.print("");
			}
			int selected = random.nextInt(games.size());
			chosenGame = games.remove(selected);
			ArrayList<Integer> levels = new ArrayList<Integer>();
			do {
				playGoodDesignGame();
				totalPlayed++;
				nextGameFrame.setVisible(true);
				while (mouseClick == RunnerEnum.NONE) {
			    	System.out.print("");
			    }
			    switch (mouseClick) {
				    case TUTORIAL:
						playGoodDesignGame();
						totalPlayed++;
						break;
				    case SAME:
				    	playGoodDesignGame();
						totalPlayed++;
						break;
				    case NEXT:
				    	selected = random.nextInt(games.size());
				    	if (games.size() > 0) {
				    		chosenGame = games.remove(selected);
				    	} else {
				    		done = true;
				    	}
						break;
				    default:
						break;
			    }
			} while (done != true);
			runnerFrame.setVisible(true);
			runnerFrame.setFocusable(true);
			runnerFrame.setSubmitEnable(true);
			while(mouseClick != RunnerEnum.DONE) {
				
			}
		}

	}

	public static int getNumberOfFiles(String filePath, String fileName) {
		File[] files = new File(filePath).listFiles();
		int number = 0;
		if (files == null) {
			return number;
		}
		for (File f : files) {
			if (f.getName().contains(fileName)) {
				number += 1;
			}
		}

		return number;
	}

	public static void playGoodDesignGame() {
		double[] result;
		try {
			// play a level
			MarioGame game = new MarioGame();
			String level = "./levels/mechanic-experiments/" + Runner.chosenGame + ".txt";
		    EventLogger.levelName = Runner.chosenGame;

		    MarioResult results = game.runGame(new agents.robinBaumgarten.Agent(), getLevel(level), 999, 0, true);

			String name = Runner.chosenGame;
			System.out.println(name);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static String getLevel(String filepath) {
		String content = "";
		try {
			content = new String(Files.readAllBytes(Paths.get(filepath)));
		} catch (IOException e) {
		}
		return content;
	}
}
