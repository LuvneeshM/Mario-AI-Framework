import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import engine.core.EventLogger;
import engine.core.MarioGame;
import engine.core.MarioResult;

public class PlayLevelsForMechanics {
	public static void printResults(MarioResult result) {
		System.out.println("****************************************************************");
		System.out.println("Game Status: " + result.getGameStatus().toString() + " Percentage Completion: "
				+ result.getCompletionPercentage());
		System.out.println("Lives: " + result.getCurrentLives() + " Coins: " + result.getCurrentCoins()
				+ " Remaining Time: " + (int) Math.ceil(result.getRemainingTime() / 1000f));
		System.out.println("Mario State: " + result.getMarioMode() + " (Mushrooms: " + result.getNumCollectedMushrooms()
				+ " Fire Flowers: " + result.getNumCollectedFireflower() + ")");
		System.out.println("Total Kills: " + result.getKillsTotal() + " (Stomps: " + result.getKillsByStomp()
				+ " Fireballs: " + result.getKillsByFire() + " Shells: " + result.getKillsByShell() + " Falls: "
				+ result.getKillsByFall() + ")");
		System.out.println("Bricks: " + result.getNumDestroyedBricks() + " Jumps: " + result.getNumJumps()
				+ " Max X Jump: " + result.getMaxXJump() + " Max Air Time: " + result.getMaxJumpAirTime());
		System.out.println("****************************************************************");
	}

	public static String getLevel(String filepath) {
		String content = "";
		try {
			content = new String(Files.readAllBytes(Paths.get(filepath)));
		} catch (IOException e) {
		}
		return content;
	}

	public static void main(String[] args) {
		MarioGame game = new MarioGame();
		// printResults(game.playGame(getLevel("levels/original/lvl-1.txt"),
		// 200, 0));
		ArrayList<Path> allFiles = new ArrayList<Path>();
	    try {
			Files.find(Paths.get("./levels/mechanic-experiments"), 999, (p, bfa) -> bfa.isRegularFile()).forEach(allFiles::add);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    for(Path file : allFiles) {
	    	if(file.toString().contains(".txt")){
	    		EventLogger.levelName = file.getFileName() + "";
	    		printResults(
	    				game.runGame(new agents.robinBaumgarten.Agent(), getLevel(file.toString()), 999, 0, true));
	    	}
			String name = file.toString();
			System.out.println(name);
	    }
		
	}
}
