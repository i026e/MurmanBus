package com.murm.murmanskbus;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TabHost;

public class TableActivity extends Activity{
	BusList busList = MainActivity.busList;
	private Constants.Directon direction;
	private int routeIndex, stationIndex;//delay, 
	private GridView gvW, gvSat,gvSun; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_table);
		
		
		
		initTabs();
		initData();
		
		
		gvW = (GridView)findViewById(R.id.gridViewTable_Work);
		gvSat = (GridView)findViewById(R.id.gridViewTable_Satur);
		gvSun = (GridView)findViewById(R.id.gridViewTable_Sunday);	
		
		new SetGridAdaptersTask().execute();
		
		//gvW.setAdapter(new ArrayAdapter<String>(this, R.layout.station_item, 
				//getDepartures(Constants.Day.WORKDAY)));
		//gvSat.setAdapter(new ArrayAdapter<String>(this, R.layout.station_item, 
				//getDepartures(Constants.Day.SATURDAY)));
		//gvSun.setAdapter(new ArrayAdapter<String>(this, R.layout.station_item, 
				//getDepartures(Constants.Day.SUNDAY)));
	}
	private class SetGridAdaptersTask extends AsyncTask<String, Integer, Long>{

		private ArrayAdapter<String> adapterW, adapterSat, adapterSun;
		@Override
		protected Long doInBackground(String... arg0) {
			adapterW = new ArrayAdapter<String>(getBaseContext(), R.layout.station_item, 
					getDepartures(Constants.Day.WORKDAY));
			adapterSat = new ArrayAdapter<String>(getBaseContext(), R.layout.station_item, 
					getDepartures(Constants.Day.SATURDAY));
			adapterSun = new ArrayAdapter<String>(getBaseContext(), R.layout.station_item, 
					getDepartures(Constants.Day.SUNDAY));			
			return null;
		}
		protected void onPostExecute(Long result) {
			gvW.setAdapter(adapterW);
			gvSat.setAdapter(adapterSat);
			gvSun.setAdapter(adapterSun);
		}
		
	}
	
	
	
	private String[] getDepartures(Constants.Day day){
		int[] depTimes = busList.getBus(routeIndex).getArrivalsList(direction, stationIndex, day);
		
		String[] departures = new String[depTimes.length];
		for (int i = 0; i < depTimes.length; i++){ 			
			departures[i] = MyTimeRepr.minsAndHours(depTimes[i], false);
		}
		return departures;
	}
	
	private void initData(){
		Intent intent = getIntent();
		routeIndex = intent.getIntExtra("routeIndex", -1);
		direction = (Constants.Directon) intent.getSerializableExtra("direction");
		//delay = intent.getIntExtra("delay", 0);
		stationIndex = intent.getIntExtra("stationIndex", 0);
	}
	private void initTabs(){
		TabHost tabs = (TabHost) findViewById(android.R.id.tabhost);
		tabs.setup();
		
		TabHost.TabSpec spec = tabs.newTabSpec("work");
		spec.setContent(R.id.tab1);
		spec.setIndicator("Будни");
		tabs.addTab(spec);
		
		spec = tabs.newTabSpec("saturday");
		spec.setContent(R.id.tab2);
		spec.setIndicator("Суббота");
		tabs.addTab(spec);

		spec = tabs.newTabSpec("sunday");
		spec.setContent(R.id.tab3);
		spec.setIndicator("Воскресенье");
		tabs.addTab(spec);

		tabs.setCurrentTab(0);
	}
}
