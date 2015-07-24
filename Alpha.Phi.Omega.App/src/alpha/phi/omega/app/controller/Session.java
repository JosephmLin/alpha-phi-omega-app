package alpha.phi.omega.app.controller;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.util.Log;

public class Session {
	private static boolean loggedIn;
	private static String username;
	private static String password;
	private static String firstname;
	private static String userStatus;
	private static boolean isUserPending = false;
	private final static String FILENAME = "session.json";
	final static String tag = "Session Tag";
	public static void setLogIn(boolean isLoggedIn)
	{
		loggedIn = isLoggedIn;
	}
	public static void setPending(boolean userPending) {isUserPending = userPending;}
	public static boolean isUserLoggedIn()
	{
		return loggedIn;
	}
	public static boolean isLoginPending() {return isUserPending;}
	public static String getUsername()
	{
		return username;
	}
	public static String getPassword()
	{
		return password;
	}
	public static String getFirstName()
	{
		return firstname;
	}
	//Eventually User Status sshould be allowed to remove people from specific events and such.

	public static String getUserStatus()
	{
		return userStatus;
	}
	public static void setUserStatus(String status)
	{
		userStatus = status;
	}
	public static void setUsername(String username) {
		Session.username = username;
	}
	public static void setPassword(String password) {
		Session.password = password;
	}
	public static void setFirstName(String firstname)
	{
		Session.firstname = firstname;
	}
	//Loads Remember Me data.
	public static void loadJSONData(Context context)
	{
		BufferedReader reader = null;
		try {
			// open and read the file into a StringBuilder
			InputStream in = context.openFileInput(FILENAME);
			if (in != null) {

				reader = new BufferedReader(new InputStreamReader(in));
				StringBuilder jsonString = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					// line breaks are omitted and irrelevant
					jsonString.append(line);
				}
				JSONObject json = new JSONObject(jsonString.toString());
				username = (String)json.get("username");
				password = (String)json.get("password");

			}
			if (reader != null)
				reader.close();
		} catch (FileNotFoundException e) {
			// we will ignore this one, since it happens when we start with a new build. Also, not having this file should not be ground breaking.
			Log.e(tag, "" + e.getLocalizedMessage());
		} catch (IOException e) {
			// we will ignore this one, since it happens when we start with a new build.
			Log.e(tag, ""+e.getLocalizedMessage());
		} catch (JSONException e) {
			// we will ignore this one, since it happens when we start with a new build.
			Log.e(tag, ""+e.getLocalizedMessage());
		}
	}
	//Saves Remember Me Data.
	public static void saveJSONData(String save_username, String save_password, Context context)
	{
		username = save_username;
		password = save_password;
		Writer writer = null;
		OutputStream out;
		JSONObject json = new JSONObject();
		try {
			json.put("username", username);
			json.put("password", password);
			out = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
			writer = new OutputStreamWriter(out);
			writer.write(json.toString());
			writer.close();
		} 
		catch (JSONException je)
		{
			je.printStackTrace();
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
