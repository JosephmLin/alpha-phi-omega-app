package alpha.phi.omega.app.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
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
	static DefaultHttpClient httpClient;
	static HttpGet httpGet;
	static HttpPost httpPost;
	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";
	RequestQueue queue = Volley.newRequestQueue(this);
	private static String tag = "JSON PARSER TAG";
	// constructor
	public JSONParser() {

	}
	public JSONObject getJSONFromGETRequest(String url)
	{

		// defaultHttpClient
		try{
			httpClient = new DefaultHttpClient();
			httpGet = new HttpGet(url);
			Log.d(tag, "start");
			HttpResponse httpResponse = httpClient.execute(httpGet);
			Log.d(tag, "Executed");
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();
		} 
		catch(NetworkOnMainThreadException nomte)
		{
			Log.d(tag, "NOMTE EXCEPTION");
		}
		catch (Exception e) {
			Log.d(tag, "In thread connection drop");
		}
		Log.d(tag, "Event Get HTTP Complete");

		BufferedReader reader2 = new BufferedReader(new InputStreamReader(is));
		String line = null;
		String total_line = "";
		Log.d(tag, "Starting the parsing process");

		try{
			while ((line = reader2.readLine()) != null)
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
		return jObj;
	}
	public JSONObject getJSONFromUrl(String url, String[] params) {

		// Making HTTP request
		try {
			// defaultHttpClient
			httpClient = new DefaultHttpClient();
			httpPost = new HttpPost(url);
			JSONObject obj = new JSONObject();
			obj.put("tag", params[0]);
			obj.put("username",params[1]);
			obj.put("password",params[2]);
			httpPost.setEntity(new StringEntity(obj.toString(), "UTF-8"));
			Log.d(tag, "THREAD START");

			HttpResponse httpResponse = httpClient.execute(httpPost);
			Log.d(tag, "HTTP EXECUTE");

			HttpEntity httpEntity = httpResponse.getEntity();
			Log.d(tag, "GET ENTITY");

			is = httpEntity.getContent();
			Log.d(tag, "NO EXCEPTION! Input stream a-OK!");

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
		BufferedReader reader2 = new BufferedReader(new InputStreamReader(is));
		String line2 = null;
		String total_line = "";
		Log.d(tag, "Starting the parsing process");

		try{
			while ((line2 = reader2.readLine()) != null)
			{
				total_line += line2;
			}
		}
		catch (IOException ioe)
		{
			Log.d(tag, "IOE");
		}
		try{
			is.close();
			Log.d("JSON", total_line);
			json = total_line;
		} catch (IOException e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		try {
			Log.d(tag, "JSON to change to JSONObject:" + json);
			jObj = new JSONObject(json); 
			Log.d(tag, jObj.toString());
			// return JSON String
			return jObj;
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}
		Log.d(tag, "INVALID JSON OBJECT");
		// return JSON String
		return new JSONObject();

	}
	public void postInfo(String url, String[] params) {

		// Making HTTP request
		try {
			// defaultHttpClient
			httpClient = new DefaultHttpClient();
			httpPost = new HttpPost(url);
			JSONObject obj = new JSONObject();
			obj.put("tag", params[0]);
			obj.put("username",params[1]);
			obj.put("eventid",params[2]);
			obj.put("drive",params[3]);
			obj.put("lead",params[4]);
			httpPost.setEntity(new StringEntity(obj.toString(), "UTF-8"));
			Log.d(tag, "THREAD START");

			HttpResponse httpResponse = httpClient.execute(httpPost);
			Log.d(tag, "HTTP EXECUTE");

			HttpEntity httpEntity = httpResponse.getEntity();
			Log.d(tag, "GET ENTITY");

			is = httpEntity.getContent();
			Log.d(tag, "NO EXCEPTION! Input stream a-OK!");

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
	}
	public JSONObject getPhoneNumber(String url, String type)
	{
		// Making HTTP request
		try {
			// defaultHttpClient
			httpClient = new DefaultHttpClient();
			httpPost = new HttpPost(url);
			JSONObject obj = new JSONObject();
			obj.put("tag", "get_phone");
			obj.put("type",type);
			httpPost.setEntity(new StringEntity(obj.toString(), "UTF-8"));
			Log.d(tag, "THREAD START");

			HttpResponse httpResponse = httpClient.execute(httpPost);
			Log.d(tag, "HTTP EXECUTE");

			HttpEntity httpEntity = httpResponse.getEntity();
			Log.d(tag, "GET ENTITY");

			is = httpEntity.getContent();
			Log.d(tag, "NO EXCEPTION! Input stream a-OK!");

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
		BufferedReader reader2 = new BufferedReader(new InputStreamReader(is));
		String line2 = null;
		String total_line = "";
		Log.d(tag, "Starting the parsing process");

		try{
			while ((line2 = reader2.readLine()) != null)
			{
				total_line += line2;
			}
		}
		catch (IOException ioe)
		{
			Log.d(tag, "IOE");
		}
		try{
			is.close();
			Log.d("JSON", total_line);
			json = total_line;
		} catch (IOException e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		try {
			Log.d(tag, "JSON to change to JSONObject:" + json);
			jObj = new JSONObject(json); 
			Log.d(tag, jObj.toString());
			// return JSON String
			return jObj;
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}
		Log.d(tag, "INVALID JSON OBJECT");
		// return JSON String
		return new JSONObject();
	}
}
