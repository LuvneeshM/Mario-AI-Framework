package engine.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import engine.helper.EventType;
import engine.helper.SpriteType;

public class EventLogger {

	public static String levelName = "test";
	
	static int run_number = 0;
	
	private static HashMap<String, String> actionToAdd(String actionName, int marioState, float x, float y, int time) {
		HashMap<String, String> to_log = new HashMap<String,String>();
		to_log.put("Action", actionName);
		to_log.put("Status", "" + marioState);
		to_log.put("X", "" + x);
		to_log.put("Y", "" + y);
		to_log.put("Time", "" + time);
		return to_log;
	}

	public static JSONObject addEventsToJSONObj(ArrayList<MarioEvent> gameEvents) {
		// variables
		JSONObject obj = new JSONObject();
		JSONArray actions = new JSONArray();
		int currTimeStamp = 0;

		// loop through all game events

		float startX = -100;
		float startY = -100;
		for (MarioEvent e : gameEvents) {
			if (e.getTime() != currTimeStamp) {
				if (actions.size() != 0) {
					obj.put(currTimeStamp, actions);
					actions = new JSONArray();
				}
				currTimeStamp = e.getTime();
			}
			if (e.getEventType() == EventType.STOMP_KILL.getValue()) {
				HashMap<String, String> to_log = actionToAdd("Stomp Kill", e.getMarioState(), e.getMarioX(), e.getMarioY(),
						e.getTime());
				actions.add(to_log);
			}
			if (e.getEventType() == EventType.SHELL_KILL.getValue()) {
				HashMap<String, String> to_log = actionToAdd("Shell Kill", e.getMarioState(), e.getMarioX(),
						e.getMarioY(), e.getTime());
				actions.add(to_log);
			}
			if (e.getEventType() == EventType.FIRE_KILL.getValue()) {
				HashMap<String, String> to_log = actionToAdd("Fire Kill", e.getMarioState(), e.getMarioX(),
						e.getMarioY(), e.getTime());
				actions.add(to_log);
			}
			if (e.getEventType() == EventType.FALL_KILL.getValue()) {
				HashMap<String, String> to_log = actionToAdd("Fall Kill", e.getMarioState(), e.getMarioX(),
						e.getMarioY(), e.getTime());
				actions.add(to_log);
			}
			if (e.getEventType() == EventType.COLLECT.getValue()) {
				if (e.getEventParam() == SpriteType.MUSHROOM.getValue()) {
					HashMap<String, String> to_log = actionToAdd("Mario Mode", e.getMarioState(),
							e.getMarioX(), e.getMarioY(), e.getTime());
					actions.add(to_log);
				} else if (e.getEventParam() == SpriteType.FIRE_FLOWER.getValue()) {
					HashMap<String, String> to_log = actionToAdd("Mario Mode", e.getMarioState(),
							e.getMarioX(), e.getMarioY(), e.getTime());
					actions.add(to_log);
				} else if (e.getEventParam() == 15) { // coin on floor
					HashMap<String, String> to_log = actionToAdd("Coins Collected", e.getMarioState(),
							e.getMarioX(), e.getMarioY(), e.getTime());
					actions.add(to_log);
				} else if (e.getEventParam() == 11) { // coin question block
					HashMap<String, String> to_log = actionToAdd("Coins Collected", e.getMarioState(),
							e.getMarioX(), e.getMarioY(), e.getTime());
					actions.add(to_log);
				} else if (e.getEventParam() == 49) { // invisible coin block
					HashMap<String, String> to_log = actionToAdd("Coins Collected", e.getMarioState(),
							e.getMarioX(), e.getMarioY(), e.getTime());
					actions.add(to_log);
				} else if (e.getEventParam() == 7) { // coin block
					HashMap<String, String> to_log = actionToAdd("Coins Collected", e.getMarioState(),
							e.getMarioX(), e.getMarioY(), e.getTime());
					actions.add(to_log);
				}
			}
			if (e.getEventType() == EventType.BUMP.getValue()) {
				if(e.getEventParam() == MarioForwardModel.OBS_BRICK) {
					HashMap<String, String> to_log = actionToAdd("Bumping Brick Block", e.getMarioState(), e.getMarioX(),
							e.getMarioY(), e.getTime());
					actions.add(to_log);
				}
				if ((e.getEventParam() == MarioForwardModel.OBS_QUESTION_BLOCK)) {
					HashMap<String, String> to_log = actionToAdd("Bumping Question Block", e.getMarioState(), e.getMarioX(),
							e.getMarioY(), e.getTime());
					actions.add(to_log);
				}
			}
					
				
			
			if (e.getEventType() == EventType.JUMP.getValue()) {
				if (e.getEventParam() == 0) { // take off
					startX = e.getMarioX();
					startY = e.getMarioY();
					HashMap<String, String> to_log = actionToAdd("Mario Jumps", e.getMarioState(), e.getMarioX(),
							e.getMarioY(), e.getTime());
					actions.add(to_log);
				}
				if (e.getEventParam() == -1) { // start falling, do math and
												// record for the y
					if (Math.abs(e.getMarioY() - startY) >= 60) {
						HashMap<String, String> to_log = actionToAdd("High Jump", e.getMarioState(), e.getMarioX(),
								e.getMarioY(), e.getTime());
						actions.add(to_log);
					} else {
						HashMap<String, String> to_log = actionToAdd("Low Jump", e.getMarioState(), e.getMarioX(),
								e.getMarioY(), e.getTime());
						actions.add(to_log);
					}
					startY = -100;
				}
			}
			if (e.getEventType() == EventType.LAND.getValue()) { // landed, do
																	// math and
																	// record
																	// for the x
				if (Math.abs(e.getMarioX() - startX) > 80.0) {
					HashMap<String, String> to_log = actionToAdd("Long Jump", e.getMarioState(), e.getMarioX(), e.getMarioY(),
							e.getTime());
					actions.add(to_log);
				} else {
					HashMap<String, String> to_log = actionToAdd("Short Jump", e.getMarioState(), e.getMarioX(),
							e.getMarioY(), e.getTime());
					actions.add(to_log);
				}
				startX = -100;
			}
		}
		
		return obj;

	}

	public static void bulkWrite(ArrayList<MarioEvent> gameEvents) {
		//variables 
		JSONObject obj = addEventsToJSONObj(gameEvents);
		if(obj.size() == 0) {
			System.out.println("\tNothing to record, returning");
			return;
		}
		
		String fn = "logs/event_log_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		String world_num = EventLogger.levelName;

		String filename = fn + "_" + run_number + ".json";
		run_number++;
		// make a new file
		File logDir = new File("logs");
		if(!logDir.exists()) {
			logDir.mkdir();
		}
//		File file = new File(fn);
//		try {
//			file.createNewFile();
//		} catch (IOException e1) {
//			System.out.println("Could not make a new file.");
//			e1.printStackTrace();
//		}
		try (FileWriter fileW = new FileWriter(filename, false)) {
			fileW.write(obj.toJSONString());
			System.out.println("\tSuccessfully Copied JSON Object to File " + filename);
//			System.out.println("JSON Object: " + obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String[] getPlayedMechanics(ArrayList<MarioEvent> gameEvents) {
		JSONObject obj = addEventsToJSONObj(gameEvents);
		SortedSet<Integer> keys = new TreeSet<>(obj.keySet());
		ArrayList<String> to_return = new ArrayList <String>();
		for (Integer key : keys) {
			String value = obj.get(key).toString();
			to_return.add(value);			
		}

		return to_return.toArray(new String[0]);
	}

}
