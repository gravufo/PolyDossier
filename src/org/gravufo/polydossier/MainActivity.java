package org.gravufo.polydossier;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity
{
	final EditText txtUsername = (EditText) findViewById(R.id.txtUsername);
	final EditText txtBirthdate = (EditText) findViewById(R.id.txtBirthdate);
	final EditText txtPassword = (EditText) findViewById(R.id.txtPassword);

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void connect(View v)
	{
		try
		{
			// Address to authenticate with
			URL address = new URL("https://www4.polymtl.ca/servlet/ValidationServlet");
			URLConnection connection = address.openConnection();
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
			writer.write("code=" + txtUsername.getText() + "&nip=" + txtPassword.getText() + "&naissance=" + txtBirthdate.getText());
			writer.flush();
			
			InputStreamReader reader = new InputStreamReader(connection.getInputStream());
			String response = "";
			
			for(int i = 0; i != -1; i = reader.read())
			{
				response += (char) i;
			}
		}
		catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
