package survey;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import engine.core.EventLogger;
import engine.core.MarioGame;
import engine.core.MarioResult;

public class Runner {
	public static int playLevels;

	public static Random random;
	public static String chosenGame;
	public static int experimentID;
	public static int chosenLevel;
	public static double win;
	public static double score;
	public static double time;
	public static boolean submissionDone;
	public static ArrayList<String> games;
	public static RunnerEnum mouseClick;
	public static String id;
	public static int totalPlayed;
	public static boolean one, two, three;

	public static void main(String[] args) {
		playLevels = 1;
		chosenGame = "";
		one = false;
		two = false;
		three = false;
		// make ID
		id = UUID.randomUUID().toString().replace("-", "");
		
		File[] folders = new File("levels/mechanic-experiments/").listFiles();
		ArrayList<ArrayList<String>> experiments = new ArrayList<ArrayList<String>>();
		games = new ArrayList<String>();                                                                                                                                                                                                                                                                                                                                                         
		int count = 0;
		for (int i = 0; i < folders.length; i++) {
			File f = folders[i];
			File[] levels = new File(f.getPath()).listFiles();
			experiments.add(new ArrayList<String>());
			for (int j = 0; j < levels.length; j++) {
				File level = levels[j];
				experiments.get(i).add(level.getPath());
			}
			count++;
		}
		Collections.shuffle(experiments);
		random = new Random();
		mouseClick = RunnerEnum.NONE;

		ReasonFrame reasonFrame = new ReasonFrame();
		RunnerFrame runnerFrame = new RunnerFrame(SurveyText.getSurveyText());
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
		experimentID = 0;
		
		
		while (true) {
			// Clear old log directory after every submit
			try {
				Runner.deleteDirectoryStream(Paths.get("logs"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			do {
				runnerFrame.setVisible(true);
				runnerFrame.setFocusable(true);
			    if (one && two && three) {
			    	runnerFrame.setSubmitEnable(true);
			    }
			    // essentially just cycle until we get a mouse click that means something
				while (mouseClick == RunnerEnum.NONE) {
			    	System.out.print("");
			    }
				
			    switch (mouseClick) {
				    case TUTORIAL:
				    	mouseClick = RunnerEnum.NONE;
						playGoodDesignGame();
						totalPlayed++;
						runnerFrame.setVisible(true);
						runnerFrame.setFocusable(true);
						break;
				    case LEVEL_1:
				    	mouseClick = RunnerEnum.NONE;
						runnerFrame.setVisible(false);
						runnerFrame.setFocusable(false);
				    	chosenGame = experiments.get(experimentID).get(0);
				    	playGoodDesignGame();
						one = true;
						totalPlayed++;
						break;
				    case LEVEL_2:
				    	mouseClick = RunnerEnum.NONE;
						runnerFrame.setVisible(false);
						runnerFrame.setFocusable(false);
						chosenGame = experiments.get(experimentID).get(1);
				    	playGoodDesignGame();
				    	two = true;
						totalPlayed++;
						break;
				    case LEVEL_3:
				    	mouseClick = RunnerEnum.NONE;
						runnerFrame.setVisible(false);
						runnerFrame.setFocusable(false);
						chosenGame = experiments.get(experimentID).get(2);
				    	playGoodDesignGame();
				    	three = true;
						totalPlayed++;
						break;	
				    default:
						break;
			    }
			} while (done != true);
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
		try {
			// play a level
			MarioGame game = new MarioGame();
			String level = Runner.chosenGame;
			File levelPath = new File(level);
		    EventLogger.levelName = levelPath.getName().substring(0, levelPath.getName().length() - 4);
		    System.out.println(level);
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
	
	static void deleteDirectoryStream(Path path) throws IOException {
		  Files.walk(path)
		    .sorted(Comparator.reverseOrder())
		    .map(Path::toFile)
		    .forEach(File::delete);
		}
}
