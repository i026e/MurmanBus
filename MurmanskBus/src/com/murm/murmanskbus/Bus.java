package com.murm.murmanskbus;

public class Bus {		
	private final static String SEPRTR = "->";
	private final static MyJSONParser myJSONParser = MainActivity.myJSONParser;
	
	private String routeID, Name, northPt, southPt;
		
	public Bus(String routeID){
		this.routeID = routeID;
		Name = myJSONParser.getBusName(routeID);
		
		northPt =  myJSONParser.getBusNorthPt(routeID);
		southPt = myJSONParser.getBusSouthPt(routeID);
	}
	
	public String getRouteID(){
		return this.routeID;
	}
	public String getName(){
		return Name;
	}
	public String endPts(Constants.Directon direction){
		if (direction == Constants.Directon.FROM_NORTH)return northPt + SEPRTR + southPt;		
		return  southPt + SEPRTR + northPt;
	}
	
	public String[] getStationsList(Constants.Directon direction){
		return myJSONParser.getStations(routeID, direction);				
	}
	public int[] getDelaysList(Constants.Directon direction){
		return myJSONParser.getDelays(routeID, direction);
		
	}
	public int[] getArrivalsList(Constants.Directon direction, int stationIndex, Constants.Day day){
		int[] departures = myJSONParser.getDepartures(routeID, direction, day);
		int[] startOffsets = getStartOffset(direction,day);
		int[] delays = getDelaysList(direction);
		
		//Кол-во рейсов, начинающихся с остановки до текущей
		int numStations = 0;
		for (int offset : startOffsets){
			if (offset <= stationIndex) numStations++;
		}
		
		int[] arrivals = new int[numStations];
		
		for(int i=0, j =0; i < departures.length; i++){
			if (startOffsets[i] <= stationIndex){
				arrivals[j++] = departures[i] + delays[stationIndex] - delays[startOffsets[i]];
			}
		}		
		return arrivals;
	}
	public int[] getStartOffset(Constants.Directon direction,Constants.Day day){
		return myJSONParser.getOffsets(routeID, direction, day);
	}
}
