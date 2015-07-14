package alpha.phi.omega.app.controller;
import alpha.phi.omega.app.model.Event;
import android.content.Context;
import android.os.NetworkOnMainThreadException;
import android.util.Log;

public class UserFunctions {

	private static LogInAsync logInAsyncThread;
	private static SignUpEventAsync signUpAsync;
	private static GetPhoneAsync getPhoneAsync;

	// Testing in localhost using wamp or xampp 
	// use http://10.0.2.2/ to connect to your localhost ie http://localhost/


	private static String ITP341_FINALPROJECT_LOGIN_TAG= "login";
	private static String ITP341_FINALPROJECT_SIGNUP_TAG= "signup_user_to_event";
	private static String SESSION_TAG = "ITP341.FINALPROJECT.SESSION TAG";
	private static String[] params;
	static String httpURL;
	/**
	 * function make Login Request
	 * @param email
	 * @param password
	 * */
	//Sends an asynchronous task request. Also waits for each request to return before logging in.
	public static void loginUser(String username, String password, Context context){
		Log.d(SESSION_TAG, "Starting Log In Attempt");
		// Building Parameters

		httpURL = "http://www.apousc.org/android/";
		params = new String[3];
		params[0] = ITP341_FINALPROJECT_LOGIN_TAG;
		params[1] = username;
		params[2] = password;
		
		Log.d(SESSION_TAG, "Declared Private Variables" + params.toString());
		try{
			//This functionality needs to be changed in order to adapt for the assumption of inconsistent internet onnectivity. Perhaps have a loading screen while this occurs. If, after x amount of time there is no response, pass by an invalid3
			logInAsyncThread = new LogInAsync(httpURL, params);
			logInAsyncThread.execute();
			logInAsyncThread.get();
			return;
		}
		catch(NetworkOnMainThreadException nomte)
		{
			Log.d(SESSION_TAG, "NOMTE EXCEPTION");
			return;
		}
		catch (Exception e) {
			Log.d(SESSION_TAG, "In thread connection drop");
			return;
		}
	}
	//Gets the phone number after a request. Also waits until task complete
	public static void getPhone(Event this_event){
		Log.d(SESSION_TAG, "Starting Log In Attempt");
		// Building Parameters

		httpURL = "http://www.apousc.org/android/";

		
		Log.d(SESSION_TAG, "Declared Private Variables" + params.toString());
		try{
			getPhoneAsync = new GetPhoneAsync(httpURL, this_event.getEventType(), this_event );
			getPhoneAsync.execute();
			getPhoneAsync.get();
			return;
		}
		catch(NetworkOnMainThreadException nomte)
		{
			Log.d(SESSION_TAG, "NOMTE EXCEPTION");
			return;
		}
		catch (Exception e) {
			Log.d(SESSION_TAG, "In thread connection drop");
			return;
		}
	}
	/**
	 * Function to logout user
	 * Reset Database
	 * */
	public static boolean logoutUser(){
		Session.setLogIn(false);
		return true;
	}
	//This allows the user to sign up for events with information. UI thread waits for this to compelte as well.
	public static void signUpForEvent(String eventID, String drive, String lead)
	{
		Log.d(SESSION_TAG, "Starting Log In Attempt");
		// Building Parameters

		httpURL = "http://www.apousc.org/android/";
		params = new String[5];
		params[0] = ITP341_FINALPROJECT_SIGNUP_TAG;
		params[1] = Session.getUsername();
		params[2] = eventID;
		params[3] = drive;
		params[4] = lead;
		
		Log.d(SESSION_TAG, "Declared Private Variables" + params.toString());
		try{
			signUpAsync = new SignUpEventAsync(httpURL, params);
			signUpAsync.execute();
			signUpAsync.get();
			return;
		}
		catch(NetworkOnMainThreadException nomte)
		{
			Log.d(SESSION_TAG, "NOMTE EXCEPTION");
			return;
		}
		catch (Exception e) {
			Log.d(SESSION_TAG, "In thread connection drop");
			return;
		}
	}
}
