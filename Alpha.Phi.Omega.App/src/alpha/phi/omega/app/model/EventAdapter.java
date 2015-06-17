package alpha.phi.omega.app.model;


import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class EventAdapter extends BaseAdapter{
	Activity context;
	int layoutResourceId;
	ArrayList<Event> all_events;
	public EventAdapter(Activity context, int layoutResourceId, ArrayList<Event> all_events) 
	{
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.all_events = all_events;
	}
	public int getCount()
	{
		return all_events.size();
	}
	public Event getItem(int position)
	{
		return all_events.get(position);
	}
	public long getItemId(int position)
	{
		return position;
	}
	public View getView(int position, View view, ViewGroup parent) {


		LayoutInflater inflater = context.getLayoutInflater();
		View gridItem;
		 
		if (view == null) {
 
			gridItem =  inflater.inflate(layoutResourceId, parent, false);

			// set view attributes
			//Populates the adapter
			TextView eventTitle = (TextView) gridItem.findViewById(R.id.eventTileName);
			TextView dateTitle = (TextView) gridItem.findViewById(R.id.eventTileDate);
			Event a = getItem(position);
			eventTitle.setText(a.getName());
			dateTitle.setText(a.getStart());
 
		} 
		else gridItem = (View) view;
		
 
		return gridItem;
	}
}
