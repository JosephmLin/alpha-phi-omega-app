package alpha.phi.omega.app.controller;

import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class SignUpEventAsync extends AsyncTask<Void, Void, Void> {

	String url;
	String[] params;
	static String tag = "login_async";
	public SignUpEventAsync(String url, String[] params) {
		this.url = url;
		this.params = params;
	}

	@Override
	public Void doInBackground(Void... nothing) {
		Log.d(tag, "Thread Start.");
		JSONParser a = new JSONParser();
		//Posts Info to sign up for event
		a.postInfo(url, params);
		return null;
	}
}