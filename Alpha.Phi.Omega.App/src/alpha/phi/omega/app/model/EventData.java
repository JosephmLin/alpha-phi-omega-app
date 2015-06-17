package alpha.phi.omega.app.model;

import alpha.phi.omega.app.controller.GetEventDataAsync;

import java.util.ArrayList;

import android.content.Context;
import android.os.NetworkOnMainThreadException;
import android.util.Log;

public class EventData {
	private static ArrayList<Event> eventArray = new ArrayList<Event>();
	private static final String FILENAME = "EventData.json";
	private static final String tag = "EventDataTag";
	private static boolean events_received;
	//starts the asynchronous task to the database.
	public static ArrayList<Event> loadAllEventsFromHTTP(Context context) {
		
		events_received = false;
		Log.d(tag, "Load All Events Attempt");
		// Building Parameters

		String httpURL = "http://www.apousc.org/android/";
		
		Log.d(tag, "Declared Private Variables");
		try{
			GetEventDataAsync async = new GetEventDataAsync(httpURL);
			async.execute();
		}
		catch(NetworkOnMainThreadException nomte)
		{
			Log.d(tag, "NOMTE EXCEPTION");
			return null;
		}
		catch (Exception e) {
			Log.d(tag, "In thread connection drop");
			return null;
		}
		Thread.yield();
		return getEvents();
	}
	//These ensure that the UI thread received it.
	public static void setEvents(ArrayList<Event> all_events)
	{
		EventData.eventArray = all_events;
		events_received = true;
	}
	
	public static ArrayList<Event> getEvents()
	{
		while(events_received == false)
		{
			try{
				Thread.sleep(100);
			}catch(InterruptedException ie)
			{
				Log.e(tag, "INTERRUPT MY SLEEEEP");
			}
		}
		return eventArray;
	}
}