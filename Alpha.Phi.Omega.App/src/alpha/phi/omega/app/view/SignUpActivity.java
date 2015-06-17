package alpha.phi.omega.app.view;

import alpha.phi.omega.app.controller.Session;
import alpha.phi.omega.app.controller.UserFunctions;
import alpha.phi.omega.app.model.Event;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends Activity {
	public static String EXTRA_EVENT = "alpha.phi.omega.app.signupactivity.extra_event";
	TextView text_title;
	TextView text_location;
	TextView text_date_time;
	RadioGroup radio_drive_group;
	LinearLayout drive_layout;
	EditText number_can_drive;
	CheckBox take_lead;
	Button button_sign_up;
	Event this_event;
	String lead_string_val = "0";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		text_title = (TextView)findViewById(R.id.textView_sign_up_event_name);
		text_location = (TextView)findViewById(R.id.textView_sign_up_location);
		text_date_time = (TextView)findViewById(R.id.textView_sign_up_date_time);
		radio_drive_group = (RadioGroup)findViewById(R.id.radioGroup_drive);
		drive_layout = (LinearLayout)findViewById(R.id.layout_drive_num);
		number_can_drive = (EditText)findViewById(R.id.editText_num_can_drive);
		take_lead = (CheckBox)findViewById(R.id.checkBox_take_lead);
		button_sign_up = (Button)findViewById(R.id.button_sign_up_signmeup);
		drive_layout.setVisibility(View.INVISIBLE);
		take_lead.setVisibility(View.INVISIBLE);
		//If not driving, make it so that you can't see the option to drive.
		radio_drive_group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch(checkedId)
				{
				case R.id.radio_yes:
					drive_layout.setVisibility(View.VISIBLE);
					return;
				case R.id.radio_no:
					drive_layout.setVisibility(View.INVISIBLE);
					number_can_drive.setText("" + 0);
					return;
				}
			}
		});
		Intent i = getIntent();
		this_event = (Event)i.getSerializableExtra(EXTRA_EVENT);
		text_title.setText(this_event.getName());
		text_location.setText(this_event.getLocation());
		text_date_time.setText(this_event.getStartTime() + " to " + this_event.getEndTime());
		if (Session.getUserStatus() == "1") //1 is Pledge. Pledges have the privilege of taking lead.
			//Lead is a role that pledges can take.
		{
			take_lead.setVisibility(View.VISIBLE);
		}
		button_sign_up.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Send an asynchronous event with the form data.
				UserFunctions.signUpForEvent(this_event.getID(), number_can_drive.getText().toString(), lead_string_val);
				Toast.makeText(getApplicationContext(), "Thank you for signing up!", Toast.LENGTH_SHORT).show();
				Intent i = new Intent(getApplicationContext(), EventPageActivity.class);
				startActivity(i);
			}
		});
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case R.id.menu_sign_up_back:
			Intent i = new Intent();
			startActivity(i);
			finish();
			return true;
		case R.id.menu_sign_up_log_out:
			Intent i2 = new Intent(getApplicationContext(), LoginPageActivity.class);
			UserFunctions.logoutUser();
			startActivity(i2);
			finish();
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater infl = getMenuInflater();
		infl.inflate(R.menu.menu_sign_up, menu);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public void onBackPressed() //This essentially helps me clear the back stack
	{
		Intent i = new Intent(getApplicationContext(), EventPageActivity.class);
		startActivity(i);
		finish();
	}
}
