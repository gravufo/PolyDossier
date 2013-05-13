package org.gravufo.polydossier;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class LoginScreen extends Activity
{
	EditText txtUsername;
	EditText txtBirthdate;
	EditText txtPassword;
	CheckBox checkBoxAutoLogin;
	Pattern p = Pattern.compile("<input type=\"hidden\" name=\"(.*?)\" value=\"(.*?)\">");

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_screen);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		txtUsername = (EditText) findViewById(R.id.txtUsername);
		txtBirthdate = (EditText) findViewById(R.id.txtBirthdate);
		txtPassword = (EditText) findViewById(R.id.txtPassword);
		checkBoxAutoLogin = (CheckBox) findViewById(R.id.checkBoxAutoLogin);
		return true;
	}

	/**
	 * Initiates a login attempt based on the current input fields
	 * 
	 * @param v
	 */
	public void connect(View v)
	{
		try
		{
			new Thread(new Runnable()
			{
				public void run()
				{
					String responseBody = "";
					Matcher m;
					Looper.prepare();
					// do
					// {
					Log.i("info", "Attempting login");

					// Create a new HttpClient and Post Header
					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost("https://www4.polymtl.ca/servlet/ValidationServlet");

					// Add your data
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
					nameValuePairs.add(new BasicNameValuePair("code", txtUsername.getText().toString()));
					nameValuePairs.add(new BasicNameValuePair("nip", txtPassword.getText().toString()));
					nameValuePairs.add(new BasicNameValuePair("naissance", txtBirthdate.getText().toString()));
					try
					{
						httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					}
					catch (UnsupportedEncodingException e)
					{
						e.printStackTrace();
					}

					try
					{
						HttpResponse response = httpclient.execute(httppost);
						responseBody = EntityUtils.toString(response.getEntity());
						longInfo(responseBody);
					}
					catch (ClientProtocolException e1)
					{
						e1.printStackTrace();
					}
					catch (IOException e1)
					{
						e1.printStackTrace();
					}

					m = p.matcher(responseBody);

					// } while (!m.lookingAt());
					if (m.find())
					{
						Log.i("info", "Login successful");

						onLoginSuccess(m);
					}
					else
					{
						LoginScreen.this.runOnUiThread(new Runnable()
						{
							public void run()
							{
								AlertDialog alertDialog = new AlertDialog.Builder(LoginScreen.this).create();
								alertDialog.setTitle("Tentative de connexion");
								alertDialog.setMessage("La connexion a échouée. Veuillez revérifier vos données.");
								alertDialog.show();
							}
						});
					}
				}
			}).start();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Function that will find, separate and store the relevant login data. Call
	 * this function ONLY if your login was successful and your data is valid.
	 * 
	 * @param data
	 *            Contains all the regex filtered data returned by the web login
	 *            server
	 */
	private void onLoginSuccess(Matcher data)
	{
		LinkedList<String> sessionValues = new LinkedList<String>();
		
		data.reset();
		int i = 0;
		while (data.find())
		{
			Log.i("counter", Integer.toString(i++));
			sessionValues.add(data.group());
		}

		Log.i("info", sessionValues.get(3));

	}

	public static void longInfo(String str)
	{
		if (str.length() > 4000)
		{
			Log.i("info", str.substring(0, 4000));
			longInfo(str.substring(4000));
		}
		else
			Log.i("info", str);
	}
	
	/**
	 * Erases all the fields
	 * 
	 * @param v
	 */
	public void cancel(View v)
	{
		txtBirthdate.setText("");

		txtPassword.setText("");

		txtUsername.setText("");
		
		checkBoxAutoLogin.setChecked(false);
	}
}
