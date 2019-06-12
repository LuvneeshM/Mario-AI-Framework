package survey;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;



public class Runner {
    public static int numLevels;
    public static int playLevels;

    public static Random random;
    public static int chosenGame;
    public static int chosenLevel;
    public static double win;
    public static double score;
    public static double time;
    public static boolean submissionDone;

    public static ArrayList<String> games;

    public static RunnerEnum mouseClick;

    public static String firstGenerator;
    public static String secondGenerator;
    public static String firstLevelNumber;
    public static String secondLevelNumber;
    public static String tutorialLevelNumber;
    public static boolean playedFirst;
    public static boolean playedSecond;
    public static boolean playedThird;
    
    public static String id;
    
    public static int totalPlayed;

    public static void main(String[] args) {
		numLevels = 5;
		playLevels = 1;
		chosenGame = -1;
		
		// make ID
		id = UUID.randomUUID().toString().replace("-", "");
	
		File[] files = new File("examples/games/").listFiles();
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
		HashMap<String, RunnerFrame> frames = new HashMap<>();
		for (String g : games) {
		    frames.put(g, new RunnerFrame(SurveyText.getSurveyText(g), "dictionaries/" + g + "-dictionary.png"));
		    frames.get(g).setVisible(false);
		    frames.get(g).setFocusable(false);
		    frames.get(g).setTitle(g);
		}
		reasonFrame.setVisible(true);
		reasonFrame.setFocusable(true);
		while (mouseClick == RunnerEnum.NONE) {
		    System.out.print("");
		}
		mouseClick = RunnerEnum.NONE;
		reasonFrame.setVisible(false);
	
		submissionDone = true;
		while (true) {
		    while(!submissionDone) {
		    	System.out.print("");
		    }
		    for (String g : games) {
				frames.get(g).setVisible(false);
				frames.get(g).setFocusable(false);
		    }
		    chosenGame = (chosenGame+1) % games.size();
		    frames.get(games.get(chosenGame)).setVisible(true);
		    frames.get(games.get(chosenGame)).setFocusable(true);
		    frames.get(games.get(chosenGame)).pack();
			ArrayList<Integer> levels = new ArrayList<Integer>();
	
		    for (int i = 0; i < playLevels; i++) {

				submissionDone = false;
				totalPlayed = 0;
				
		
				do {
					chosenLevel = (totalPlayed) % 5;
					levels.add(chosenLevel);
				    frames.get(games.get(chosenGame)).setSubmitEnable(totalPlayed > 2);
//				    frames.get(games.get(chosenGame)).setPlayEnable(totalPlayed <= 2);
				    mouseClick = RunnerEnum.NONE;
				    frames.get(games.get(chosenGame)).setVisible(true);
				    frames.get(games.get(chosenGame)).setFocusable(true);
				    while (mouseClick == RunnerEnum.NONE) {
				    	System.out.print("");
				    }
				    switch (mouseClick) {
					    case TUTORIAL:
						for (String g : games) {
						    frames.get(g).setVisible(false);
						}
						playGoodDesignGame();
						playedFirst = true;
						playedSecond = true;
						playedThird = true;
						totalPlayed++;
						break;
					    default:
						break;
				    }
				} while (mouseClick != RunnerEnum.SUBMIT);
				
//				// we just clicked submit, so set all radio buttons to the chosen game's buttons
//			    frames.get(games.get(chosenGame)).age.getSelection()

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
		String gameFile = "examples/games/" + games.get(Runner.chosenGame) + ".txt";
		String levelFile = "examples/levels/" + games.get(Runner.chosenGame) + "_lvl" + chosenLevel + ".txt";
		String mechanicsFile = games.get(Runner.chosenGame) + "/human/" + chosenLevel + "/0" + "/interactions/interaction.json";
		String resultsFile = games.get(Runner.chosenGame) + "/human/" + chosenLevel + "/0" + "/result/result.json";
		double[] result;
		try {
			VisualDemonstrationInterfacer vdi = new VisualDemonstrationInterfacer(games.get(Runner.chosenGame), false);
			ArrayList<BunchOfGames> bogs = new ArrayList<>();
			BunchOfGames game = new BunchOfGames(gameFile, levelFile, "human", "" + chosenLevel, "0");
			bogs.add(game);
			String[] human = {"human"};
			vdi.runBunchOfGames(bogs, human, chosenLevel, 0);
	//		result = ArcadeMachine.playOneGame(gameFile, levelFile, actionFile,
	//			Runner.random.nextInt(Integer.MAX_VALUE));
	//		Runner.win = result[0];
	//		Runner.score = result[1];
	//		Runner.time = result[2];
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
}
