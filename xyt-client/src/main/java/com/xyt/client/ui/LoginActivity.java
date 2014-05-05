package com.xyt.client.ui;

import java.util.HashMap;
import java.util.Map;

import com.example.xiaoyuantong.R;
import com.xyt.client.backend.LoginDao;
import com.xyt.client.backend.LoginService;
import com.xyt.client.backend.LoginServiceImpl;
import com.xyt.client.util.PublicWay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;


@SuppressLint("WorldReadableFiles")
@SuppressWarnings("unused")
public class LoginActivity extends Activity 
{
	
//	private RequestQueue requestQueue; 
	private EditText edNumber, passWord; 
	private CheckBox remberPass, autoLogin; 
	private Button login,quit; 
	private SharedPreferences sp; 
	private ProgressDialog pd;
	private String result = "false";
	
	//static String number;
	@Override
    protected void onCreate(Bundle savedInstanceState) 
    { 
    	super.onCreate(savedInstanceState); 
    
    	
    	setContentView(R.layout.activity_login);
    	PublicWay.activityList.add(this);
    	edNumber = (EditText) findViewById(R.id.number); 
    //	number = edNumber.getText().toString();
    	passWord = (EditText) findViewById(R.id.password);
    	remberPass = (CheckBox) findViewById(R.id.remember); 
    	autoLogin = (CheckBox) findViewById(R.id.autoLogin); 
    	login = (Button) findViewById(R.id.login);
    	quit=(Button) findViewById(R.id.quit);
    	this.login.setOnClickListener(new ViewOcl());
    	this.quit.setOnClickListener(new ViewOcl1());
    	sp = getSharedPreferences("users",Context.MODE_WORLD_READABLE); 
    	
    	//
    	
    	// 从SharedPreferences里边取出记住密码的状态 
    	if (sp.getBoolean("ISCHECK", false)) 
    	{ 
    		// 将记住密码设置为被点击状态 
    		remberPass.setChecked(true); 
    		// 然后将值赋值给EditText 
    		edNumber.setText(sp.getString("oa_number", "")); 
    		passWord.setText(sp.getString("oa_pass", ""));
    		// 获取自动登录按钮的状态 
    		if (sp.getBoolean("AUTO_ISCHECK", false)) 
    		{ 
    			// 设置自动登录被点击 然后实现跳转 
    			autoLogin.setChecked(true); 
    			Intent intent1 = new Intent(LoginActivity.this, PersonalInfoActivity.class);
    			startActivity(intent1); 
    		} 
    	} 
    	// 将点击的checkBOx存入到users中 
    	remberPass.setOnCheckedChangeListener(new OnCheckedChangeListener() 
    	{ 
    		public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {  
                if (remberPass.isChecked()) {  
                      
                    System.out.println("记住密码已选中");  
                    sp.edit().putBoolean("ISCHECK", true).commit();  
                      
                }else {  
                      
                    System.out.println("记住密码没有选中");  
                    sp.edit().putBoolean("ISCHECK", false).commit();  
                      
                }  
  
            }  
    	}); 
    	
    	autoLogin.setOnCheckedChangeListener(new OnCheckedChangeListener() {  
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {  
                if (autoLogin.isChecked()) {  
                    System.out.println("自动登录已选中");  
                    sp.edit().putBoolean("AUTO_ISCHECK", true).commit();  
                } else {  
                    System.out.println("自动登录没有选中");  
                    sp.edit().putBoolean("AUTO_ISCHECK", false).commit();  
                }  
            }  
    	}); 
    }
	
    class ViewOcl implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
            LoginMain();
		}
    }
    
    public void showMsg1(String value)
	{
		AlertDialog.Builder dialog=new AlertDialog.Builder(this);
		dialog.setTitle("系统提示");
		dialog.setMessage(value);
		dialog.setPositiveButton("确定",new DialogInterface.OnClickListener(){

			public void onClick(DialogInterface dialog,int which){
				// TODO Auto-generated method stub
				dialog.dismiss();
				for(int i=0;i<PublicWay.activityList.size();i++)
	    		{
	    			if (null != PublicWay.activityList.get(i)) 
	    			{
	    				PublicWay.activityList.get(i).finish();
	    			}
	    		}
			}
		});
		dialog.setNegativeButton("取消",new DialogInterface.OnClickListener(){

			public void onClick(DialogInterface dialog, int which){
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		dialog.create().show();
	}
    
    class ViewOcl1 implements View.OnClickListener
    {
    	  
    	@Override
    	public void onClick(View v) 
    	{
    	    // TODO Auto-generated method stub
    		//Toast.makeText(LoginActivity.this, "退出应用", Toast.LENGTH_LONG).show();
    		//遍历Activity集合，关闭所有集合内的Activity
    		/*for(int i=0;i<PublicWay.activityList.size();i++)
    		{
    			if (null != PublicWay.activityList.get(i)) 
    			{
    				PublicWay.activityList.get(i).finish();
    			}
    		}*/
    		showMsg1("确定退出吗？");
    	}
    }

    public void showMsg(String value)
	{
		AlertDialog.Builder dialog=new AlertDialog.Builder(this);
		dialog.setTitle("系统提示");
		dialog.setMessage(value);
		dialog.setPositiveButton("确定",new DialogInterface.OnClickListener(){

			public void onClick(DialogInterface dialog,int which){
				// TODO Auto-generated method stub
				dialog.dismiss();
				
				MyThread m = new MyThread();
				new Thread(m).start();
				
			/*	Intent intent1=new Intent();
				LoginService loginServicedb=new LoginServiceImpl();
				
				if (true){
					//如果返回true，则进入主界面
					intent1.setClass(LoginActivity.this, MainActivity.class);	
			
				}else{
					//否则进入注册界面
					intent1.setClass(LoginActivity.this, PersonalInfoActivity.class);
				}
				startActivity(intent1);*/
			}
		});
		dialog.setNegativeButton("取消",new DialogInterface.OnClickListener(){

			public void onClick(DialogInterface dialog, int which){
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		dialog.create().show();
	}
    


    class MyHandler extends Handler{
    	public MyHandler(){
    		
    	}
    	public MyHandler(Looper L){
    		super(L);
    	}
    	
    	
    	public void handleMessage(Message msg){
    		
    	/*	Bundle b = msg.getData();
    		String color = b.getString("color");*/
    	}
    	
    
    	
    }
    
	class MyThread implements Runnable{
		
		public void run(){
			Intent intent1=new Intent();
			LoginService loginServicedb=new LoginServiceImpl();
			Map<String, String> map=new HashMap<String, String>();
	    	map.put("userid", edNumber.getText().toString());
	    	Log.e("userid", edNumber.getText().toString());
	    	Log.e("login","runnable");
			if (loginServicedb.login(map)){
				//如果返回true，则进入主界面
				intent1.setClass(LoginActivity.this, MainActivity.class);
		
			}else{
				//否则进入注册界面
				intent1.setClass(LoginActivity.this, PersonalInfoActivity.class);
			}
			startActivity(intent1);
		}
	}
	
	
    
    
    @SuppressWarnings("unchecked")
	protected void LoginMain() 
    { 
		// 将信息存入到users里面 
		//ed.putString("oa_name", username.getText().toString()); 
		//ed.commit(); 
		if (TextUtils.isEmpty(edNumber.getText().toString())) 
		{ 
			Toast.makeText(this, "请输入学号", Toast.LENGTH_LONG).show(); 
			return; 
		} 
		if (TextUtils.isEmpty(passWord.getText().toString())) 
		{ 
			Toast.makeText(this, "请输入密码", Toast.LENGTH_LONG).show(); 
			return; 
		} 
		Map<String, String> map=new HashMap<String, String>();
    	map.put("stuid", edNumber.getText().toString());
    	map.put("pwd", passWord.getText().toString());
    	Log.e("number", edNumber.getText().toString());
    	new LoginAsyn().execute(map);
    	
	}  
    
   
    
    public class LoginAsyn extends AsyncTask<Map<String, String>, Integer, Boolean>
    {
		/*
         * 这两个参数在AsyncTask声明的泛型参数列表中指定，
         * 第一个为doInBackground接受的参数
         * 第二个为显示进度的参数，第第三个为doInBackground返回和onPostExecute传入的参数。
         */
        @Override
        protected void onPreExecute() 
        {
            // 该方法将在执行实际的后台操作前被UI thread调用,如在界面上显示一个进度条。
            super.onPreExecute();
            pd=new ProgressDialog(LoginActivity.this);//这个必不可少
            pd.setTitle("稍等");//设置标题
            pd.setMessage("正在登录");//设置提示信息
            pd.show();
        }

		@Override
		protected Boolean doInBackground(Map<String, String>... params) 
		{
			// TODO Auto-generated method stub
			
			LoginService loginService=new LoginDao();
			//不仅在教务办匹配成功，在数据库也能找到
			return loginService.login(params[0]);
		}

		@Override
		protected void onPostExecute(Boolean result) 
		{
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pd.dismiss();
			if(result)
			{
				String studentId = edNumber.getText().toString();  
	            String passwordValue = passWord.getText().toString(); 
				if(remberPass.isChecked())  
                {  
                 //记住用户名、密码、  
                  Editor editor = sp.edit();  
                  editor.putString("oa_number", studentId);  
                  editor.putString("oa_pass",passwordValue);  
                  editor.commit();  
                }  
				showMsg("确定登录吗？");
				//Intent intent = new Intent(LoginActivity.this, PersonalInfoActivity.class); 
				//startActivity(intent); 
			}
			else 
			{
				Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_LONG).show();
			}
			
			Editor nu = getSharedPreferences("number",0).edit();
			nu.putString("studentId", edNumber.getText().toString());
			
			nu.commit();
			
		}
	}
    
   
	
    
    
}
    
