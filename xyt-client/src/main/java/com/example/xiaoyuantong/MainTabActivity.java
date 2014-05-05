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

public class MainTabActivity extends TabActivity implements OnCheckedChangeListener{
	
	private TabHost TabHost;
	private Intent mainIntent;
	private Intent friendsIntent;
	private Intent newsIntent;
	private Intent topicIntent;
	private Intent groupIntent;
	private Intent searchIntent;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main_tab);
        
        //this.mainIntent = new Intent(this,MainActivity.class);
		this.mainIntent = new Intent(this,MainActivity.class);
        this.friendsIntent = new Intent(this,FriendsActivity.class);
        this.newsIntent = new Intent(this,NewsActivity.class);
        this.topicIntent = new Intent(this,TopicActivity.class);
        this.groupIntent = new Intent(this,GroupActivity.class);
        this.searchIntent = new Intent(this,SearchActivity.class);
        
		((RadioButton) findViewById(R.id.main))
		.setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.friends))
		.setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.news))
		.setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.topic))
		.setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.group))
		.setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.search))
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
			case R.id.friends:
				this.TabHost.setCurrentTabByTag("friends_TAB");
				break;
			case R.id.news:
				this.TabHost.setCurrentTabByTag("news_TAB");
				break;
			case R.id.topic:
				this.TabHost.setCurrentTabByTag("topic_TAB");
				break;
			case R.id.group:
				this.TabHost.setCurrentTabByTag("group_TAB");
				break;
			case R.id.search:
				this.TabHost.setCurrentTabByTag("search_TAB");
				break;
			}
		}
		
	}
	
	private void setupIntent() {
		this.TabHost = getTabHost();
		TabHost localTabHost = this.TabHost;

		//localTabHost.addTab(buildTabSpec("main_TAB", R.string.main_main,
				//R.drawable.main, this.mainIntent));

		localTabHost.addTab(buildTabSpec("friends_TAB", R.string.main_friends,
				R.drawable.friends, this.friendsIntent));

		localTabHost.addTab(buildTabSpec("news_TAB",
				R.string.main_news, R.drawable.news,
				this.newsIntent));

		localTabHost.addTab(buildTabSpec("topic_TAB", R.string.main_topic,
				R.drawable.topic, this.topicIntent));

		localTabHost.addTab(buildTabSpec("group_TAB", R.string.main_group,
				R.drawable.group, this.groupIntent));
		
		localTabHost.addTab(buildTabSpec("search_TAB", R.string.main_search,
				R.drawable.search, this.searchIntent));

	}
	
	private TabHost.TabSpec buildTabSpec(String tag, int resLabel, int resIcon,
			final Intent content) {
		return this.TabHost.newTabSpec(tag).setIndicator(getString(resLabel),
				getResources().getDrawable(resIcon)).setContent(content);
	}
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_tab, menu);
		return true;
	}
}

