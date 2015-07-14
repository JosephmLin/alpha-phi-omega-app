package alpha.phi.omega.app.view;

import alpha.phi.omega.app.R;
import alpha.phi.omega.app.controller.Session;
import alpha.phi.omega.app.controller.UserFunctions;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginPageActivity extends Activity {
	static String LOGIN_TAG = "Login Page Activity Tag";
	EditText login_username;
	EditText login_password;
	CheckBox login_remember_me;
	Button login_button;
	String username;
	String password;
	private static int Intent_Request_LogOut = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		String remembered_username = "";
		String remembered_password = "";
		Session.setLogIn(false);
		Session.loadJSONData(getApplicationContext());
		remembered_username = Session.getUsername();
		remembered_password = Session.getPassword();

		login_username = (EditText)findViewById(R.id.usernameEditText);
		login_password = (EditText)findViewById(R.id.passwordEditText);
		login_remember_me = (CheckBox)findViewById(R.id.checkBox_remember_me);
		login_button = (Button)findViewById(R.id.button_google_maps);
		if (remembered_username != "");
		{
			//Load data from JSON if remember_username exists.
			login_username.setText(remembered_username);
			login_password.setText(remembered_password);
			login_remember_me.setChecked(true);
		}

		Log.d(LOGIN_TAG, "Main Page Created");
		login_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(LOGIN_TAG, "EXTRACTING USERNAME AND PASSWORD");
				username = login_username.getText().toString();
				password = login_password.getText().toString();
				// TODO Auto-generated method stub;
				if (apo_login())
				{
					Log.d(LOGIN_TAG, "LOGIN SUCCESSFUL");
					//Save if Remember Me is checked
					if (login_remember_me.isChecked())
					{
						//This saves the username and password only if successful.
						Session.saveJSONData(username, password, getApplicationContext());
						//Save it to JSON
					}
					//Start the next activity
					Toast.makeText(getApplicationContext(), "Login Success - Welcome " + Session.getFirstName(), Toast.LENGTH_SHORT).show();
					Intent i = new Intent(getApplicationContext(), EventPageActivity.class);
					startActivity(i);
					finish();
				}
				else
				{
					Log.d(LOGIN_TAG, "LOGIN INVALID");
					//Throw a toast saying invalid. Consider making a little red text appear on the bottom of the login screen
					Toast.makeText(getApplicationContext(), "Login Failed - Please try again!", Toast.LENGTH_SHORT).show();
				}

			}
		});
	}
	private boolean apo_login()
	{
		Log.d(LOGIN_TAG, "Going into User Functions");
		//UserFunctions has the changes Session boolean to allow for log in.
		UserFunctions.loginUser(username, password, getApplicationContext());
		
		return Session.isUserLoggedIn();
	}
	@Override //Clears the back stack 100%
	public void onBackPressed()
	{
		System.exit(0);
	}

	/*
		Additional potential functionality includes making an API call to the excel sheet which displays the current number of hours completed. However, all of this information should be stored on the database as well.
		The issue might be with the people failing to abide by the choice to do more work. So I need to think of something simpler.

		There should be a Google Doc link. Maybe make a new database thing with a Google link. Make a small webpage to allow the VP of Membership to update the current Master Doc link. It should be pretty quick to do. Maybe might have to write a script for that.
		This Google Doc link will speak also to this app's master doc section. This should make the updating process of the master doc fairly simple.
	 */
}

