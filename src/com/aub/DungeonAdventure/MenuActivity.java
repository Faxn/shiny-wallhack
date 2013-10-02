package com.aub.DungeonAdventure;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class MenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		findViewById(R.id.menu_launch_game_button).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent launchGame = new Intent(MenuActivity.this, GameActivity.class);
				startActivity(launchGame);
			}
			
		});
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

}
