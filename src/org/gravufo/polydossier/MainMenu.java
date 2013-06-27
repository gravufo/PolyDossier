package org.gravufo.polydossier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainMenu extends Activity
{
	
	enum MenuOption
	{
		PERSONAL_INFO()
		{
			public void displayInfo()
			{
				
			}
		},
		
		LAST_REPORT_CARD()
		{
			public void displayInfo()
			{
				
			}
		},
		
		SEMESTER_GRADES()
		{
			public void displayInfo()
			{
				
			}
		},
		
		PERSONAL_SCHEDULE()
		{
			public void displayInfo()
			{
				
			}
		},
		
		ATTENDANCE_CERTIFICATE()
		{
			public void displayInfo()
			{
				
			}
		},
		
		FINANCIAL_RECORD()
		{
			public void displayInfo()
			{
				
			}
		};
		
		public abstract void displayInfo();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);

		ArrayList<String> sessionValues = new ArrayList<String>();
		final ListView listView = (ListView) findViewById(R.id.mainMenuList);
		
		sessionValues = getIntent().getStringArrayListExtra("org.gravufo.polydossier.sessionValues");
		
		String[] values = new String[]{"Renseignements personnels", "Dernier bulletin cumulatif", "Notes finales du trimestre",
				"Horaire personnel", "Attestation de fréquentation scolaire", "Dossier financier"};
		final ArrayList<String> list = new ArrayList<String>(Arrays.asList(values));
		final Map<String, MenuOption> optionMap = new HashMap<String, MenuOption>();
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
		
		optionMap.put(values[0], MenuOption.PERSONAL_INFO);
		optionMap.put(values[1], MenuOption.LAST_REPORT_CARD);
		optionMap.put(values[2], MenuOption.SEMESTER_GRADES);
		optionMap.put(values[3], MenuOption.PERSONAL_SCHEDULE);
		optionMap.put(values[4], MenuOption.ATTENDANCE_CERTIFICATE);
		optionMap.put(values[5], MenuOption.FINANCIAL_RECORD);
		
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
			{
				final String item = (String) parent.getItemAtPosition(position);

				optionMap.get(item).displayInfo();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
