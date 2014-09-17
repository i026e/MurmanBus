package com.murm.murmanskbus;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class StationsActivity extends Activity{
	//private int[] delays;
	private int routeIndex;
	private Constants.Directon direction;
	private BusList busList = MainActivity.busList;
	private ListView lv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stations);
		
		Intent intent = getIntent();
		routeIndex = intent.getIntExtra("routeIndex", -1);
		direction = (Constants.Directon) intent.getSerializableExtra("direction");
		
		//delays = busList.getBus(routeIndex).getDelaysList(direction);
		
		lv = (ListView)findViewById(R.id.stationsListView);
		new initListViewAdapterTask().execute();
		
		
	}
	
	private class initListViewAdapterTask extends AsyncTask<String, Integer, Long>{

		private ArrayAdapter<String> arrayAdapter;
		@Override
		protected Long doInBackground(String... params) {
			arrayAdapter = new ArrayAdapter<String>(getBaseContext(), R.layout.station_item, 
					busList.getBus(routeIndex).getStationsList(direction));
			return null;
		}
		protected void onPostExecute(Long result) {
			lv.setAdapter(arrayAdapter);

			lv.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,
						long id) {
					// TODO Auto-generated method stub					
					showTable((int)id);				
				}});
		}
	}
	
	private void showTable(int stationIndex){
		Intent intent = new Intent(this, TableActivity.class);
		intent.putExtra("routeIndex", routeIndex);
		intent.putExtra("direction", direction);
		intent.putExtra("stationIndex", stationIndex);
		//intent.putExtra("delay", delays[stationIndex]);
		startActivity(intent);
	}

}
