package com.murm.murmanskbus;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;


public class MyJSONParser {
	public final static String from_n = "from_north";
	public final static String from_s = "from_south";
	public final static String w_days = "w_days";
	public final static String saturday = "saturday";
	public final static String sunday = "sunday";
	
	private static JSONObject busData, busDepartures;
			
	public static String readJsonFile(Context context, int id) throws IOException{
		InputStream inputStream = context.getResources().openRawResource(id);				
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		int ctr = inputStream.read();	
		    
		while (ctr != -1) {
			byteArrayOutputStream.write(ctr);
		    ctr = inputStream.read();
		}
		inputStream.close();
		
		return byteArrayOutputStream.toString();
	}
	
	public static String[] JSON2StringArray(JSONArray array) throws JSONException{
		List<String> list = new ArrayList<String>();
		for(int i = 0; i < array.length(); i++){
		    list.add(array.getString(i));
		}
		return list.toArray(new String[0]);
	}
	public static int[] String2IntArray(String[] s){
		int[] array = new int[s.length];
		for (int i=0; i<s.length; i++){
			
			try {
				array[i] = Integer.parseInt(s[i]);
			}catch (Exception e) {
				array[i] = 0;
			}
			
		}
		return array;
	}
	public static int[] String2MyTime(String[] s){
		int[] array = new int[s.length];
		for (int i = 0; i< s.length; i++){
			array[i] = MyTimeRepr.stringToMins(s[i], Constants.inputTimeSplitter);
		}
		return array;
	}
	
	public MyJSONParser(Context context){
		//MyJSONParser
		try{
			busData = new JSONObject(readJsonFile(context, R.raw.data));
			busDepartures = new JSONObject(readJsonFile(context, R.raw.deparures));
		}catch (Exception e) {
		    e.printStackTrace();
		}
	}
	
	public String[] getBusList(){
		String[] s = {};
		try{
			JSONArray jArray = busData.getJSONArray("bus_list");
			s = JSON2StringArray(jArray);
		}catch (Exception e) {
		    e.printStackTrace();
		}
		return s;
	}
	
	private JSONObject getInfoBusJSON(String busID) throws JSONException{
		return busData.getJSONObject("bus_info").getJSONObject(busID);
	}
	
	public String getBusName(String busID){
		String s = "";
		try{
			s = getInfoBusJSON(busID).getString("name");
		}catch (Exception e) {
		    e.printStackTrace();
		}
		return s;
	}
	public String getBusNorthPt(String busID){
		String s = "";
		try{
			s = getInfoBusJSON(busID).getString("north");
		}catch (Exception e) {
		    e.printStackTrace();
		}
		return s;
	}
	public String getBusSouthPt(String busID){
		String s = "";
		try{
			s = getInfoBusJSON(busID).getString("south");
		}catch (Exception e) {
		    e.printStackTrace();
		}
		return s;
	}
	public String[] getStations(String busID, Constants.Directon dir){
		String[] result = {};
		String q = (dir == Constants.Directon.FROM_NORTH) ? from_n : from_s;
		
		
		
		try{
			JSONArray jArray = getInfoBusJSON(busID).getJSONObject(q).getJSONArray("stations");
			result = JSON2StringArray(jArray);
		}catch (Exception e) {
		    e.printStackTrace();
		}
		return result;
	}
	
	public int[] getDelays(String busID, Constants.Directon dir){
		String[] s = {};
		String q = (dir == Constants.Directon.FROM_NORTH) ? from_n : from_s;
		
		try{
			JSONArray jArray = getInfoBusJSON(busID).getJSONObject(q).getJSONArray("delays");
			s = JSON2StringArray(jArray);
		}catch (Exception e) {
		    e.printStackTrace();
		}
		return String2IntArray(s);
	}
	public int[] getDepartures(String busID, Constants.Directon dir, Constants.Day day){
		String[] s = {};
		String q = (dir == Constants.Directon.FROM_NORTH) ? from_n : from_s;
		String d = w_days;
		if (day == Constants.Day.SATURDAY) d = saturday;
		else if (day == Constants.Day.SUNDAY) d = sunday;
		
		try{
			JSONArray jArray = busDepartures.getJSONObject(busID).getJSONObject(q).getJSONObject(d).getJSONArray("departures");
			s = JSON2StringArray(jArray);
		}catch (Exception e) {
		    e.printStackTrace();
		}
		return String2MyTime(s);
		
	}
	public int[] getOffsets(String busID, Constants.Directon dir, Constants.Day day){
		String[] s = {};
		String q = (dir == Constants.Directon.FROM_NORTH) ? from_n : from_s;
		String d = w_days;
		if (day == Constants.Day.SATURDAY) d = saturday;
		else if (day == Constants.Day.SUNDAY) d = sunday;
		
		try{
			JSONArray jArray = busDepartures.getJSONObject(busID).getJSONObject(q).getJSONObject(d).getJSONArray("offsets");
			s = JSON2StringArray(jArray);
		}catch (Exception e) {
		    e.printStackTrace();
		}
		return String2IntArray(s);
		
	}

}
