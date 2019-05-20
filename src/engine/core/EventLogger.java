package engine.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.SortedSet;
import java.util.TreeSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import engine.helper.EventType;
import engine.helper.SpriteType;

public class EventLogger {
	static int world_num = 1;

	private static ArrayList<String> actionToAdd(String actionName, int marioState, float x, float y, int time){
		ArrayList<String> to_log = new ArrayList<String>(); 
		to_log.add("Action: " + actionName);
		to_log.add("Status: " + marioState);
		to_log.add("X: " + x);
		to_log.add("Y: " + y);
		to_log.add("Time: " + time);
		return to_log;
	}

	public static JSONObject addEventsToJSONObj(ArrayList<MarioEvent> gameEvents) {
		JSONArray actions = new JSONArray();
		JSONObject obj = new JSONObject();
		int currTimeStamp = 0;

		//loop through all game events
		float startX = -100;
		float startY = -100;
		for(MarioEvent e : gameEvents) {
			if(e.getTime() != currTimeStamp) {
				if(actions.size() != 0) {
					obj.put(currTimeStamp, actions);
					actions = new JSONArray();
				}
				currTimeStamp = e.getTime();
			}
			if(e.getEventType() == EventType.STOMP_KILL.getValue()) {
				ArrayList<String> to_log = actionToAdd("Enemy Stomp",  e.getMarioState(), e.getMarioX(), e.getMarioY(), e.getTime());
				actions.add(to_log);
			}
			if(e.getEventType() == EventType.SHELL_KILL.getValue()) {
				ArrayList<String> to_log = actionToAdd("Enemy Shell Kill",  e.getMarioState(), e.getMarioX(), e.getMarioY(), e.getTime());
				actions.add(to_log);
			}
			if(e.getEventType() == EventType.FIRE_KILL.getValue()) {
				ArrayList<String> to_log = actionToAdd("Enemy Fire Kill",  e.getMarioState(), e.getMarioX(), e.getMarioY(), e.getTime());
				actions.add(to_log);
			} 
			if(e.getEventType() == EventType.FALL_KILL.getValue()) {
				ArrayList<String> to_log = actionToAdd("Enemy Fall Kill",  e.getMarioState(), e.getMarioX(), e.getMarioY(), e.getTime());
				actions.add(to_log);
			}
			if(e.getEventType() == EventType.COLLECT.getValue()) {
				if( e.getEventParam() == SpriteType.MUSHROOM.getValue() ) {
					ArrayList<String> to_log = actionToAdd("Mushroom Powerup Collected From Block",  e.getMarioState(), e.getMarioX(), e.getMarioY(), e.getTime());
					actions.add(to_log);
				}
				else if(e.getEventParam() == SpriteType.FIRE_FLOWER.getValue()) {
					ArrayList<String> to_log = actionToAdd("Mushroom Powerup Collected From Block",  e.getMarioState(), e.getMarioX(), e.getMarioY(), e.getTime());
					actions.add(to_log);
				}
				else if(e.getEventParam() == 15) { //coin on floor
					ArrayList<String> to_log = actionToAdd("Coin Collected From Floor",  e.getMarioState(), e.getMarioX(), e.getMarioY(), e.getTime());
					actions.add(to_log);
				}
				else if(e.getEventParam() == 11) { //coin question block
					ArrayList<String> to_log = actionToAdd("Coin Collected From Block",  e.getMarioState(), e.getMarioX(), e.getMarioY(), e.getTime());
					actions.add(to_log);
				}
				else if(e.getEventParam() == 49) { //invisible coin block
					ArrayList<String> to_log = actionToAdd("Coin Collected From Block",  e.getMarioState(), e.getMarioX(), e.getMarioY(), e.getTime());
					actions.add(to_log);
				}
				else if(e.getEventParam() == 7) { //coin block
					ArrayList<String> to_log = actionToAdd("Coin Collected From Block",  e.getMarioState(), e.getMarioX(), e.getMarioY(), e.getTime());
					actions.add(to_log);
				}
			}
			if(e.getEventType() == EventType.BUMP.getValue() && e.getEventParam() == MarioForwardModel.OBS_BRICK && e.getMarioState() > 0) {
				ArrayList<String> to_log = actionToAdd("Block Breaking",  e.getMarioState(), e.getMarioX(), e.getMarioY(), e.getTime());
				actions.add(to_log);
			}
			if(e.getEventType() == EventType.JUMP.getValue()) {
				if(e.getEventParam() == 0) { //take off
					startX = e.getMarioX();
					startY = e.getMarioY();
				}
				if(e.getEventParam() == -1) { //start falling, do math and record for the y
					if(Math.abs(e.getMarioY() - startY) >= 60) {
						ArrayList<String> to_log = actionToAdd("High Jump",  e.getMarioState(), e.getMarioX(), e.getMarioY(), e.getTime());
						actions.add(to_log);
					}
					else {
						ArrayList<String> to_log = actionToAdd("Low Jump",  e.getMarioState(), e.getMarioX(), e.getMarioY(), e.getTime());
						actions.add(to_log);
					}
					startY = -100;
				}
			}
			if(e.getEventType() == EventType.LAND.getValue()) { //landed, do math and record for the x
				if(Math.abs(e.getMarioX() - startX) > 80.0) {
					ArrayList<String> to_log = actionToAdd("Long Jump",  e.getMarioState(), e.getMarioX(), e.getMarioY(), e.getTime());
					actions.add(to_log);
				}
				else {
					ArrayList<String> to_log = actionToAdd("Short Jump",  e.getMarioState(), e.getMarioX(), e.getMarioY(), e.getTime());
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
//		if(obj.size() == 0) {
//			System.out.println("Nothing to record, returning");
//			return;
//		}
		String fn = "logs/event_log_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		
		String filename = fn + "_" + world_num + ".json";
		world_num++;
		//make a new file
		File file = new File(filename);
		try {
			file.createNewFile();
		} catch (IOException e1) {
			System.out.println("Could not make a new file.");
			e1.printStackTrace();
		}
		try (FileWriter fileW = new FileWriter(filename, false)) {
			fileW.write(obj.toJSONString());
			System.out.println("Successfully Copied JSON Object to File " + filename);
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
