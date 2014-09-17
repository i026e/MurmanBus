package com.murm.murmanskbus;


public class Constants {	
	public static final String logTag = "MurmanskBus";
	public static final String am = "A.M.";
	public static final String pm = "P.M.";
	public static final String inputTimeSplitter = "\\.";
	
	public static enum Day{
		WORKDAY, SATURDAY,SUNDAY
	};
	
	public static enum Directon{
		FROM_NORTH, FROM_SOUTH
	};
	
	public static int intDirectionRepr(Directon d){
		if (d == Directon.FROM_NORTH) return 0;
		else return 1;
	}
	public static Directon DirectonDirRepr(int d){
		if (d == 0) return Directon.FROM_NORTH;
		else return Directon.FROM_SOUTH;
	}
	
}
