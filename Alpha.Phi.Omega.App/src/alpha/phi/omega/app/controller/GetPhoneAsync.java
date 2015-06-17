package alpha.phi.omega.app.controller;

import org.json.JSONException;
import org.json.JSONObject;

import alpha.phi.omega.app.model.Event;
import android.os.AsyncTask;
import android.util.Log;

public class GetPhoneAsync extends AsyncTask<Void, Void, Void> {

	String url;
	String type;
	Event own_Event;
	static String tag = "login_async";
	public GetPhoneAsync(String url, String type, Event self_event) {
		this.url = url;
		this.type = type;
		own_Event = self_event;
	}

	@Override
	public Void doInBackground(Void... nothing) {
		Log.d(tag, "Thread Start.");
		JSONParser a = new JSONParser();
		//This sends a post request with the type of event. Each event type has a corresponding number saved on an online database.
		JSONObject json = a.getPhoneNumber(url, type);
		Log.d(tag, "JSON received");
		try{
			if (json.getInt("success") == 1)
			{
				Log.d(tag, "Events Received.");
				
				own_Event.setPhoneNumber(json.getString("phonenumber"));
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
