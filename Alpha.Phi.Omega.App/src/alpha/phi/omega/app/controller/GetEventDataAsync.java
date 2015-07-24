package alpha.phi.omega.app.controller;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import alpha.phi.omega.app.model.Event;
import alpha.phi.omega.app.model.EventData;

public class GetEventDataAsync extends AsyncTask<Void, Void, Void> {

	String url;
	static String tag = "login_async";
	public GetEventDataAsync(String url) {
		this.url = url;
	}

	@Override
	public Void doInBackground(Void... nothing) {
		Log.d(tag, "Thread Start.");
		JSONParser a = new JSONParser();
		//Sends a get request, which, from server side, returns all the active events.
		JSONObject json = a.getJSONFromGETRequest(url);
		//Get json object from the parser that decrypts the JSON sent back from the http request.
		Log.d(tag, "JSON received");
		try{
			if (json.getInt("success") == 1)
			{
				Log.d(tag, "Events Received.");
				ArrayList<Event> all_events = new ArrayList<Event>();
				JSONArray json_all_events = json.getJSONArray("events");
				for (int i = 0 ; i < json_all_events.length(); i++)
				{
					Event a_event = new Event(json_all_events.getJSONObject(i));
					//Populates the event array list.
					all_events.add(a_event);
					Log.d(tag, a_event.toString());
				}
				Log.d(tag, all_events.toString());
				//Sends array list to the event data class
				EventData.setEvents(all_events);
			}
		}
		catch(JSONException jse)
		{
			Log.e(tag, "jse exception");
			return null;
		}
		Log.d(tag, "leaving thread");
		return null;
	}
}
