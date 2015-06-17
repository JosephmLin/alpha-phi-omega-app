package alpha.phi.omega.app.view;

import alpha.phi.omega.app.controller.Session;
import alpha.phi.omega.app.controller.UserFunctions;
import alpha.phi.omega.app.model.Event;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EventDetailsActivity extends Activity {
	public static String EXTRA_EVENT = "alpha.phi.omega.app.view.extra_event";
	TextView label_event_description;
	TextView label_event_location;
	TextView label_event_date_time;
	TextView label_event_name;
	Button button_google_maps;
	Button button_sign_up;
	Button button_call_officer;
	Event this_event;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_details);
		//Get all elements
		
		label_event_name = (TextView)findViewById(R.id.textView_event_detail_name);
		label_event_name.setTypeface(null, Typeface.BOLD);
		label_event_location = (TextView)findViewById(R.id.textView_event_detail_location);
		label_event_location.setTypeface(null, Typeface.ITALIC);
		label_event_description = (TextView)findViewById(R.id.textView_event_detail_desc);
		label_event_date_time = (TextView)findViewById(R.id.textView_sign_up_date_time);
		button_google_maps = (Button)findViewById(R.id.button_google_maps);
		button_sign_up = (Button)findViewById(R.id.button_event_details_sign_up);
		button_call_officer = (Button)findViewById(R.id.call_officer);
		//Get the event from the intent
		Intent data = getIntent();
		this_event = (Event)data.getSerializableExtra(EXTRA_EVENT);
		//Change the Title Bar
		getActionBar().setTitle(this_event.getName());
		//Populate page
		label_event_name.setText("Event Name:" + this_event.getName());
		
		label_event_location.setText("Location:" + this_event.getLocation() + " @ " + this_event.getAddress());
		label_event_date_time.setText("Time:" + this_event.getStartTime() + " — " + this_event.getEndTime());
		label_event_description.setText("Description:" + this_event.getDescription());
		//Get Google Maps Application
		button_google_maps.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				//Looks up the address inputed online. This is dependant upon the actual people documenting the location.
				String address ="";
				try{
					address = label_event_location.getText().toString(); // Get address
					address = address.replace(' ', '+');
					
					Intent geoIntent = new Intent (android.content.Intent.ACTION_VIEW, Uri.parse ("geo:0,0?q=" + address)); // Prepare intent
					startActivity(geoIntent); // Initiate lookup
				}
				catch(Exception e)
				{
					Toast.makeText(getApplicationContext(), "Exception:" + e.getCause(), Toast.LENGTH_LONG).show();
					Toast.makeText(getApplicationContext(), "ADDRESS:" + address, Toast.LENGTH_LONG).show();
				}
			}
		});
		button_sign_up.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
				i.putExtra(SignUpActivity.EXTRA_EVENT, this_event);
				startActivity(i);
			}
		});
		button_call_officer.setOnClickListener(new OnClickListener() {
			//This pulls out the phone app with a phone number with the option to call them.
			@Override
			public void onClick(View v) {
				UserFunctions.getPhone(this_event);
				//Checks if telephone is available then pulls out phone. (Taken from stack overflow).
				if (isTelephonyEnabled())
					startDialActivity(this_event.getPhoneNumber());
			}
			
			private void startDialActivity(String phone){
			    Intent intent = new Intent(Intent.ACTION_DIAL);
			    intent.setData(Uri.parse("tel:"+phone));
			    startActivity(intent);
			}
			
			private boolean isTelephonyEnabled(){
			    TelephonyManager tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
			    return tm != null && tm.getSimState()==TelephonyManager.SIM_STATE_READY;
			}
		});

	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case R.id.menu_event_details_back:
			Intent i = new Intent(getApplicationContext(), EventPageActivity.class);
			startActivity(i);
			return true;
		case R.id.menu_event_details_logout:
			Intent i2 = new Intent(getApplicationContext(), LoginPageActivity.class);
			UserFunctions.logoutUser();
			startActivity(i2);
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater infl = getMenuInflater();
		infl.inflate(R.menu.menu_event_details, menu);
		return super.onCreateOptionsMenu(menu);
	}
	@Override //Go back to Event Page, while clearing backstack
	public void onBackPressed()
	{
		Intent i = new Intent(getApplicationContext(), EventPageActivity.class);
		startActivity(i);
		finish();
	}
}
