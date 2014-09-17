package com.murm.murmanskbus;

public class BusList {
	private static Bus[] array;
	private static String[] busNames;
	
	public BusList(){
		
		String[] busIDs = MainActivity.myJSONParser.getBusList();
		if (busIDs != null){
			array = new Bus[busIDs.length];		
			busNames = new String[busIDs.length];
			
			for (int i = 0; i<busIDs.length; i++){
				array[i] = new Bus(busIDs[i]);
				busNames[i] = array[i].getName();
			}
		}
	}
	public String[] getRouteNamesArray(){
		return busNames;
	}
	public Bus getBus(int index){
		return array[index];
	}
	
}
