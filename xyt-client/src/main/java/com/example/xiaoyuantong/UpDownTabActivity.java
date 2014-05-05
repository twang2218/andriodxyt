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

public class UpDownTabActivity extends TabActivity implements OnCheckedChangeListener{
	
	private TabHost TabHost;
	private Intent mainIntent;
	private Intent uploadIntent;
	private Intent downloadIntent;
	private Intent messagesIntent;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_up_down_tab);
        
        //this.mainIntent = new Intent(this,MainActivity.class);
		this.mainIntent = new Intent(this,MainActivity.class);
        this.uploadIntent = new Intent(this,UploadActivity.class);
        this.downloadIntent = new Intent(this,DownLoadActivity.class);
        this.messagesIntent = new Intent(this,MessageActivity.class);
        
		((RadioButton) findViewById(R.id.main))
		.setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.upload))
		.setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.download))
		.setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.messages))
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
			case R.id.upload:
				this.TabHost.setCurrentTabByTag("upload_TAB");
				break;
			case R.id.download:
				this.TabHost.setCurrentTabByTag("download_TAB");
				break;
			case R.id.messages:
				this.TabHost.setCurrentTabByTag("messages_TAB");
				break;
			}
		}
		
	}
	
	private void setupIntent() {
		this.TabHost = getTabHost();
		TabHost localTabHost = this.TabHost;

		//localTabHost.addTab(buildTabSpec("main_TAB", R.string.main_main,
				//R.drawable.main, this.mainIntent));

		localTabHost.addTab(buildTabSpec("upload_TAB", R.string.upload,
				R.drawable.upload, this.uploadIntent));

		localTabHost.addTab(buildTabSpec("download_TAB",
				R.string.download, R.drawable.download,
				this.downloadIntent));

		localTabHost.addTab(buildTabSpec("messages_TAB", R.string.messages,
				R.drawable.messages, this.messagesIntent));
	}
	
	private TabHost.TabSpec buildTabSpec(String tag, int resLabel, int resIcon,
			final Intent content) {
		return this.TabHost.newTabSpec(tag).setIndicator(getString(resLabel),
				getResources().getDrawable(resIcon)).setContent(content);
	}
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.upload, menu);
		return true;
	}
}


