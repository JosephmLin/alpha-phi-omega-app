package alpha.phi.omega.app.model;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.ParseException;
import org.json.JSONException;
import org.json.JSONObject;

public class Event implements Serializable
{
	//These fields are returned from HTTP.
	private static final String JSON_event_name = "name";
	private static final String JSON_description = "desc";
	private static final String JSON_location = "location";
	private static final String JSON_address = "address";
	private static final String JSON_start = "start";
	private static final String JSON_end = "end";
	private static final String JSON_event_id = "ID";
	private static final String JSON_event_type = "type";
	//	private static final String JSON_users = "users";
	//This information is necessary for running a lot of post and get requests. Also populates the adapters and such.
	String event_name;
	String description;
	String location;
	String start;
	String end;
	String event_id;
	String address;
	String event_type;
	String phonenumber;
	public Event(){
		super();
	}
	public Event(JSONObject JSON_event) throws JSONException
	{
		event_name = JSON_event.getString(JSON_event_name);
		description = JSON_event.getString(JSON_description);
		location = JSON_event.getString(JSON_location);
		start = JSON_event.getString(JSON_start);
		end = JSON_event.getString(JSON_end);
		event_id = JSON_event.getString(JSON_event_id);
		address = JSON_event.getString(JSON_address);
		event_type = JSON_event.getString(JSON_event_type);
	}
	public void setPhoneNumber(String phonum)
	{
		phonenumber= phonum;
	}
	public String getPhoneNumber()
	{
		return phonenumber;
	}
	public String getName() {
		return event_name;
	}
	public String getDescription() {
		return description;
	}
	public String getLocation() {
		return location;
	}
	public String getID()
	{
		return event_id;
	}
	public String getAddress()
	{
		return address;
	}
	public String getEventType()
	{
		return event_type;
	}
	public String getStart (){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		Calendar cal = Calendar.getInstance();
		try
		{
			Date date = format.parse(start);
			cal.setTime(date);
		}
		catch(java.text.ParseException pe)
		{
			pe.printStackTrace();
		}

		return cal.get(Calendar.MONTH)+1 +"/" + cal.get(Calendar.DAY_OF_MONTH);
	}
	public String getEnd () throws java.text.ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		Calendar cal = Calendar.getInstance();
		try
		{
			Date date = format.parse(end);
			cal.setTime(date);
		}
		catch(ParseException pe)
		{
			pe.printStackTrace();
		}
		//This is the value provided for the event page
		return cal.get(Calendar.MONTH)+1 +"/" + cal.get(Calendar.DAY_OF_MONTH);
	}
	//This provides the values for the event details, providing a specific time.
	public String getStartTime()
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		Calendar cal = Calendar.getInstance();
		try
		{
			Date date = format.parse(start);
			cal.setTime(date);
		}
		catch(java.text.ParseException pe)
		{
			pe.printStackTrace();
		}
		if (cal.get(Calendar.MINUTE) < 10)
		{
			return cal.get(Calendar.MONTH)+1 +"/" + cal.get(Calendar.DAY_OF_MONTH) + " " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + "0";
		}
		else
		{
			return cal.get(Calendar.MONTH)+1 +"/" + cal.get(Calendar.DAY_OF_MONTH) + " " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE);
		}
		
	}
	public String getEndTime()
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		Calendar cal = Calendar.getInstance();
		try
		{
			Date date = format.parse(end);
			cal.setTime(date);
		}
		catch(java.text.ParseException pe)
		{
			pe.printStackTrace();
		}

		if (cal.get(Calendar.MINUTE) < 10)
		{
			return cal.get(Calendar.MONTH)+1 +"/" + cal.get(Calendar.DAY_OF_MONTH) + " " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + "0";
		}
		else
		{
			return cal.get(Calendar.MONTH)+1 +"/" + cal.get(Calendar.DAY_OF_MONTH) + " " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE);
		}
		
	}
	public String toString()
	{
		return event_name;
	}

}

