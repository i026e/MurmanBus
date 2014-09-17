package com.murm.murmanskbus;

import android.util.Log;

public class MyTimeRepr {
	private final static int minsInDay = 1440;
	private final static int minsInHour = 60;
	private final static String am = "A.M.";
	private final static String pm = "P.M.";
	private final static String separator = ":";
	
	public static String minsAndHours(int minutes, boolean american){
		minutes %= minsInDay;		
		int hours = minutes/minsInHour;
		minutes %= minsInHour;
		String time;
		
		if (american){
			if (hours <= 12) time = String.valueOf(hours) + am;			
			else time = String.valueOf(hours-12) + pm;
		}
		else time = String.valueOf(hours)+separator;
		
		if (minutes < 10) time += "0" +String.valueOf(minutes);
		else time += String.valueOf(minutes);
		
		return time;
	}
	public static int minutes(int hours, int mins){
		return hours*minsInHour+mins;
	}
	public static int stringToMins(String str, String sep){
		int mins = -1;
		try {		
			String[] time = str.split(Constants.inputTimeSplitter, 2);	
			mins= minutes(Integer.parseInt(time[0]), Integer.parseInt(time[1]));
			
		} catch (Exception e) {
			Log.e(Constants.logTag, e.toString());
		}
		return mins;
	}
}
