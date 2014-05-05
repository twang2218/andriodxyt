package com.example.xiaoyuantong;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TabHost;
import android.app.Activity;
import android.view.Menu;
/*
 * 下面的列表，失物招领界面的最下一排列表
 */

public class LostAndFoundTabActivity extends TabActivity implements OnCheckedChangeListener{
	
	private TabHost TabHost;
	private Intent mainIntent;
	private Intent alllandfIntent;
	private Intent writelandfIntent;
	private Intent mylandfIntent;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_lost_and_found_tab);
        
        //this.mainIntent = new Intent(this,MainActivity.class);
		this.mainIntent = new Intent(this,MainActivity.class);
        this.alllandfIntent = new Intent(this,AllLandFActivity.class);
        this.writelandfIntent = new Intent(this,WriteLAndFActivity.class);
        this.mylandfIntent = new Intent(this,MyLAndFActivity.class);
        
		((RadioButton) findViewById(R.id.main))
		.setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.allLandF))
		.setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.writeLandF))
		.setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.myLandF))
		.setOnCheckedChangeListener(this);
        
        setupIntent();
    }

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if(isChecked){
			switch (buttonView.getId()) {
			case R.id.main:
				//this.TabHost.setCurrentTabByTag("main_TAB");
				//break;
				startActivity(mainIntent);
			case R.id.allLandF:
				this.TabHost.setCurrentTabByTag("allLandF_TAB");
				break;
			case R.id.writeLandF:
				this.TabHost.setCurrentTabByTag("writeLandF_TAB");
				break;
			case R.id.myLandF:
				this.TabHost.setCurrentTabByTag("myLandF_TAB");
				break;
			}
		}
		
	}
	
	private void setupIntent() {
		this.TabHost = getTabHost();
		TabHost localTabHost = this.TabHost;

		//localTabHost.addTab(buildTabSpec("main_TAB", R.string.main_main,
				//R.drawable.main, this.mainIntent));

		localTabHost.addTab(buildTabSpec("allLandF_TAB", R.string.allLandF,
				R.drawable.news, this.alllandfIntent));

		localTabHost.addTab(buildTabSpec("writeLandF_TAB",
				R.string.writeLandF, R.drawable.news,
				this.writelandfIntent));

		localTabHost.addTab(buildTabSpec("myLandF_TAB", R.string.myLandF,
				R.drawable.news, this.mylandfIntent));
	}
	
	private TabHost.TabSpec buildTabSpec(String tag, int resLabel, int resIcon,
			final Intent content) {
		return this.TabHost.newTabSpec(tag).setIndicator(getString(resLabel),
				getResources().getDrawable(resIcon)).setContent(content);
	}
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lost_and_found_tab, menu);
		return true;
	}
}


