package alpha.phi.omega.app.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.NetworkOnMainThreadException;
import android.util.Log;

/*
 * This class does a lot.
 * It runs 4 different HTTP Request:
 * 1) getJSONFromGETRequest: This sends a get request, which returns all of the events currently existing.
 * 2) getJSONFromURL: This sends a POST request that gets the JSON. This checks the username and password
 * 3) postInfo: this allows the user to sign up for events.
 * 4) getPhoneNumber: this allows the app to get the phone number corresponding to each event type.
 */
public class JSONParser {
	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";
	
	private static String tag = "JSON PARSER TAG";
	// constructor
	public JSONParser() {

	}

	//Get All Events
	public JSONObject getJSONFromGETRequest(String url_string)
	{
		HttpURLConnection urlConnection = null;
		BufferedReader reader = null;

		try{
			URL url = new URL(url_string);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setDoInput(true);
			urlConnection.setRequestMethod("GET");
			urlConnection.setConnectTimeout(30000);
			urlConnection.setRequestProperty("Content-Type", "application/json");
			urlConnection.connect();

			InputStream is= urlConnection.getInputStream();
			if(is == null)
			{
				return null;
			}

			reader = new BufferedReader(new InputStreamReader(is));
			String line = null;
			String total_line = "";
			Log.d(tag, "Starting the parsing process");

			try{
				while ((line = reader.readLine()) != null)
				{
					total_line += line;
				}
				if (total_line.length() == 0) {
					is.close();
					return null;
				}
				is.close();
			}
			catch (IOException ioe)
			{
				Log.d(tag, "IOE");
			}
			// try parse the string to a JSON object
			try {
				jObj = new JSONObject(total_line);
			} catch (JSONException e) {
				Log.e("JSON Parser", "Error parsing data " + e.toString());
			}

			// return JSON String

		} 
		catch(NetworkOnMainThreadException nomte)
		{
			Log.e(tag, "NOMTE EXCEPTION", nomte);
		}
		catch(MalformedURLException mue)
		{
			Log.e(tag, "Malformed URL", mue);
		}
		catch (Exception e) {
			Log.e(tag, "In thread connection drop", e);
		}
		finally
		{
			if (urlConnection!= null)
				urlConnection.disconnect();
			if (reader!=null)
			{
				try{
					reader.close();
				}
				catch(final IOException e)
				{
					Log.e(tag, "ERROR CLOSING STREAM", e);
				}
			}
		}

		return jObj;
	}
	//Should probably redo this as a hashmap for the parameters
	//This function tries to log on.
	public JSONObject getJSONFromUrl(String url_string, String[] params) {
		HttpURLConnection urlConnection = null;
		BufferedReader reader = null;
		try{
			URL url = new URL(url_string);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setDoInput(true);
			urlConnection.setDoOutput(true);
			urlConnection.setRequestMethod("GET");
			urlConnection.setConnectTimeout(30000);
			urlConnection.setRequestProperty("Content-Type", "application/json");
			urlConnection.connect();
			OutputStream os = new BufferedOutputStream(urlConnection.getOutputStream());
			JSONObject obj = new JSONObject();
			obj.put("tag", params[0]);
			obj.put("username", params[1]);
			obj.put("password", params[2]);
			os.write(obj.toString().getBytes());
			os.flush();
			os.close();


			InputStream is= urlConnection.getInputStream();

			if(is == null)
			{
				return null;
			}

			reader = new BufferedReader(new InputStreamReader(is));
			String line = null;
			String total_line = "";
			Log.d(tag, "Starting the parsing process");

			try{
				while ((line = reader.readLine()) != null)
				{
					total_line += line;
				}
				is.close();
			}
			catch (IOException ioe)
			{
				Log.d(tag, "IOE");
			}
			// try parse the string to a JSON object
			try {
				jObj = new JSONObject(total_line);
			} catch (JSONException e) {
				Log.e("JSON Parser", "Error parsing data " + e.toString());
			}

			// return JSON String

		}
		catch(NetworkOnMainThreadException nomte)
		{
			Log.d(tag, "NOMTE EXCEPTION");
		}
		catch (Exception e) {
			Log.d(tag, "In thread connection drop");
		}
		finally
		{
			if (urlConnection!= null)
				urlConnection.disconnect();
			if (reader!=null)
			{
				try{
					reader.close();
				}
				catch(final IOException e)
				{
					Log.e(tag, "ERROR CLOSING STREAM", e);
				}
			}
		}

		return jObj;
		// Making HTTP request

		// try parse the string to a JSON object

	}
	public void postInfo(String url_string, String[] params) {
		// Making HTTP request
		HttpURLConnection urlConnection = null;
		BufferedReader reader = null;
		try {
			// defaultHttpClient
			URL url = new URL(url_string);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setDoInput(true);
			urlConnection.setDoOutput(true);
			urlConnection.setRequestMethod("GET");
			urlConnection.setConnectTimeout(30000);
			urlConnection.setRequestProperty("Content-Type", "application/json");
			urlConnection.connect();

			JSONObject obj = new JSONObject();
			obj.put("tag", params[0]);
			obj.put("username", params[1]);
			obj.put("eventid", params[2]);
			obj.put("drive", params[3]);
			obj.put("lead", params[4]);

			OutputStream os = urlConnection.getOutputStream();
			os.write(obj.toString().getBytes());
			os.flush();
			os.close();

		} catch(NetworkOnMainThreadException nomte)
		{
			Log.d(tag, "NOMTE EXCEPTION");
		}
		catch (UnsupportedEncodingException e)
		{
			Log.d(tag, "UNSUPPORTED ENCODING EXCEPTION");
			e.printStackTrace();
		} catch (IOException e)
		{
			Log.d(tag, "IO EXCEPTION");
			e.printStackTrace();
		}
		catch (JSONException je)
		{
			Log.d(tag, "JSON Exception");
			je.printStackTrace();
		}
		finally
		{
			if (urlConnection!= null)
				urlConnection.disconnect();
			if (reader!=null)
			{
				try{
					reader.close();
				}
				catch(final IOException e)
				{
					Log.e(tag, "ERROR CLOSING STREAM", e);
				}
			}
		}
	}
	public JSONObject getPhoneNumber(String url_string, String type)
	{
		HttpURLConnection urlConnection = null;
		BufferedReader reader = null;
		InputStream is = null;
		// Making HTTP request
		try {
			// defaultHttpClient
			URL url = new URL(url_string);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setDoInput(true);
			urlConnection.setDoOutput(true);
			urlConnection.setRequestMethod("GET");
			urlConnection.setConnectTimeout(30000);
			urlConnection.setRequestProperty("Content-Type", "application/json");
			urlConnection.connect();

			JSONObject obj = new JSONObject();
			obj.put("tag", "get_phone");
			obj.put("type", type);

			OutputStream os = urlConnection.getOutputStream();
			os.write(obj.toString().getBytes());
			os.flush();
			os.close();

			is = urlConnection.getInputStream();
			Log.d(tag, "NO EXCEPTION! Input stream a-OK!");
			BufferedReader reader2 = new BufferedReader(new InputStreamReader(is));
			String line2 = null;
			String total_line = "";
			Log.d(tag, "Starting the parsing process");
			while ((line2 = reader2.readLine()) != null)
			{
				total_line += line2;
			}
			is.close();
			Log.d("JSON", total_line);
			json = total_line;
			Log.d(tag, "JSON to change to JSONObject:" + json);
			jObj = new JSONObject(json);
			Log.d(tag, jObj.toString());
			// return JSON String
			return jObj;
		} catch(NetworkOnMainThreadException nomte)
		{
			Log.d(tag, "NOMTE EXCEPTION");
		}
		catch (UnsupportedEncodingException e) 
		{
			Log.d(tag, "UNSUPPORTED ENCODING EXCEPTION");
			e.printStackTrace();
		} catch (IOException e) 
		{
			Log.d(tag, "IO EXCEPTION");
			e.printStackTrace();
		}
		catch (JSONException je)
		{
			Log.d(tag, "JSON Exception");
			je.printStackTrace();
		}
		finally
		{
			if (urlConnection!= null)
				urlConnection.disconnect();
			if (reader!=null)
			{
				try{
					reader.close();
				}
				catch(final IOException e)
				{
					Log.e(tag, "ERROR CLOSING STREAM", e);
				}
			}
		}

		Log.d(tag, "INVALID JSON OBJECT");
		// return JSON String
		return new JSONObject();
	}
}
