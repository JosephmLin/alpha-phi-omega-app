package alpha.phi.omega.app.controller;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class LogInAsync extends AsyncTask<Void, Void, Void> {

	String url;
	String[] params;
	static String tag = "login_async";
	ProgressDialog dialog = null;
	public LogInAsync(String url, String[] params, Activity login) {
		this.url = url;
		this.params = params;
		dialog = new ProgressDialog(login);

	}
	protected void onPreExecute() {
		dialog.setMessage("Logging In...");
		dialog.setCancelable(false);
		dialog.setInverseBackgroundForced(false);
		dialog.show();

		Log.d(tag, "Pre Execute");
	}
	@Override
	public Void doInBackground(Void... nothing) {
		Log.d(tag, "Thread Start.");
		JSONParser a = new JSONParser();
		JSONObject json = a.getJSONFromUrl(url, params);
		Log.d(tag, "JSON received");
		try{
			//This means that this was a valid user, therefore, the app should allow the user to log in.
			if (json.getInt("success") == 1)
			{
				Session.setLogIn(true);
				Log.d(tag, "Log in validated.");
				//This allows us to provide a reference not only for saving it on the json but also for posting back onto the database.
				Session.setUsername(params[1]);
				//This allows us to address them by name
				Session.setFirstName(json.getJSONObject("user").getString("fname"));
				//This allows us to know if they are a pledge or an active member.
				Session.setUserStatus(json.getJSONObject("user").getString("status"));
				Log.d(tag, "leaving thread");
				return null;
			}
		}
		catch(JSONException jse)
		{
			Log.d(tag, "jse exception");
			return null;
		}
		finally
		{
			Session.setPending(false);
		}
		Session.setLogIn(false);
		Log.d(tag, "leaving thread");
		return null;
	}
	protected void onPostExecute(Long result)
	{
		dialog.hide();
		Log.d(tag, "Async Task Completed.");
		Session.setPending(false);

	}
}