package com.xyt.client.ui;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.content.Intent;
import android.view.Window;
import android.widget.ImageView;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.xiaoyuantong.R;

public class MainActivity extends Activity{
	private Intent FriendsIntent;
	private Intent SourceIntent;
	private Intent LostAndFoundIntent;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        
        //this.mainIntent = new Intent(this,MainActivity.class);
		this.FriendsIntent = new Intent(this,MainTabActivity.class);
        this.SourceIntent = new Intent(this,UpDownTabActivity.class);
        this.LostAndFoundIntent = new Intent(this,LostAndFoundTabActivity.class);
        
		((ImageView) findViewById(R.id.friendsButton))
		.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				startActivity(FriendsIntent);
			}			
		});
		((ImageView) findViewById(R.id.resourceButton))
		.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				startActivity(SourceIntent);
			}			
		});
		((ImageView) findViewById(R.id.thingsButton))
		.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				startActivity(LostAndFoundIntent);
			}			
		});
    }


	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}


