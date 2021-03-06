package alpha.phi.omega.app.view;

import alpha.phi.omega.app.R;
import alpha.phi.omega.app.controller.Session;
import alpha.phi.omega.app.controller.UserFunctions;
import alpha.phi.omega.app.model.Event;
import alpha.phi.omega.app.model.EventAdapter;
import alpha.phi.omega.app.model.EventData;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class EventPageActivity extends ListActivity {
	//Consider expanding this to allow them to sort through previous past events as well.
	ArrayList<Event> allEvents;
	private EventAdapter adapter;
	private String tag = "event page activity tag";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_page);
		Log.d(tag, "content view established");
		allEvents = EventData.loadAllEventsFromHTTP(getApplicationContext());
		Log.d(tag, "events loaded from HTTP");
		//Need to highlight green if signed up.
		//Need to look up what this does. (Adapter)
		//EventAdapter fills
		adapter = new EventAdapter(this, R.layout.event_tile, allEvents);
		//Populates the adapter with the necessary information.
		setListAdapter(adapter);
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		//Launches event
		super.onListItemClick(l, v, position, id);
		Intent i = new Intent(getApplicationContext(),
				EventDetailsActivity.class);
		i.putExtra(EventDetailsActivity.EXTRA_EVENT,
				allEvents.get(position));

		startActivity(i);
		

	}
	@Override
	protected void onPause() {
		// Called after onStart() as Activity comes to foreground.
		super.onPause();
//		NoteData.saveNoteData(allEvents, getApplicationContext());

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case R.id.menu_event_page_log_out:
			Intent i2 = new Intent(getApplicationContext(), LoginPageActivity.class);
			UserFunctions.logoutUser();
			startActivity(i2);
			finish();
			return true;
		case R.id.menu_event_page_master_doc:
			String url = "https://docs.google.com/spreadsheets/d/1EDrMQ5FyLnP7a4u2GfGDOieZcqIflkS84bChskD_7bo/edit?usp=sharing";
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse(url));
			startActivity(i);
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater infl = getMenuInflater();
		infl.inflate(R.menu.menu_event_page, menu);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public void onBackPressed() //Logout. This will also clear the backstack.
	{
		Intent i2 = new Intent(getApplicationContext(), LoginPageActivity.class);
		UserFunctions.logoutUser();
		startActivity(i2);
		finish();
	}
}
