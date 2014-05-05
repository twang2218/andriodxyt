package com.example.xiaoyuantong;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.xiaoyuantong.PersonalInfoActivity.SpinnerXMLSelectedListener;

public class WriteLAndFActivity extends Activity {
	private Spinner LandFTipSpinner;
	private String LandFTip;
	private Spinner thingsTypeSpinner;
	private String thingsType;
	private EditText describeEditText;
	private String describe;
	private EditText QQEditText;
	private String QQ;
	private EditText phoneEditText;
	private String phone;
	private Button submit;
	
	private String serverIp = "192.168.1.79";
	private String serverPort = "8080";
	private Bundle bundle;
	private String httpStr;
	private String postStr;
	private String questStr;

	private RequestQueue requestQueue ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_write_land_f);
		
		LandFTipSpinner = (Spinner) findViewById(R.id.LandFTip);
		thingsTypeSpinner = (Spinner) findViewById(R.id.ThingsType);
		describeEditText=(EditText)findViewById(R.id.etDescribe);
		QQEditText=(EditText)findViewById(R.id.etQQ);
		phoneEditText=(EditText)findViewById(R.id.etPhone);
		submit = (Button) findViewById(R.id.submit);
		
		//添加事件Spinner事件监听  
    	LandFTipSpinner.setOnItemSelectedListener(new SpinnerXMLSelectedListener());
    	thingsTypeSpinner.setOnItemSelectedListener(new SpinnerXMLSelectedListener());
    	
		//设置默认值
    	LandFTipSpinner.setVisibility(View.VISIBLE);
    	thingsTypeSpinner.setVisibility(View.VISIBLE);
    	
		submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (ifRegisterSuccess()) {
					//register();
					init();
				}
			}

		});
		
		//获取前台的东西提交到后台
		init();
	}

	//使用XML形式操作
	class SpinnerXMLSelectedListener implements OnItemSelectedListener{
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
			
		}

		public void onNothingSelected(AdapterView<?> arg0) {
				
		}
			
	}
	
	private boolean ifRegisterSuccess(){
		describe=describeEditText.getText().toString();
    	phone=phoneEditText.getText().toString();
    	QQ=QQEditText.getText().toString();
    	LandFTip=LandFTipSpinner.getSelectedItem().toString();
    	thingsType=thingsTypeSpinner.getSelectedItem().toString();
    	
    	if("".equals(describe)){
    		Toast.makeText(WriteLAndFActivity.this, "物品描述不能为空", Toast.LENGTH_SHORT).show();
    		return false;
    	}else if ("".equals(phone)){
    		Toast.makeText(WriteLAndFActivity.this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
    		return false;
        }else if (!PhoneFormat(phone)) {
    		Toast.makeText(WriteLAndFActivity.this, "手机号码格式不正确", Toast.LENGTH_LONG).show();
    		return false;
        }else{
        	Toast.makeText(WriteLAndFActivity.this, "失物招领信息发表成功", Toast.LENGTH_SHORT).show();
        	return true;
        }
    }
    
    //电话号码模式匹配
    private boolean PhoneFormat(String phone) {//电话判断正则表达式
    	if(phone.length()!=11){
    		return false;
    	}else
    		return true;
    }

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.write_land, menu);
		return true;
	}
	
	 private void init() {
		 requestQueue = Volley.newRequestQueue(this);    
		 getJson();
		 //获取填入的数据，然后提交到后台
	 }
	 
	 private void getJson(){
	    	//��ʼ��volley
	    		String userid = "1109030121";
	    	 String url = "http://192.168.20.1:8080/xiaoyuantong/findandlostAction!addFindAndLost.action";
	    	 
	    	  JsonObjectRequest jsonObjectRequest ;
	//������ʹ��volley
	    	  JSONObject jsonObject=new JSONObject() ;
	    	  
	        // Map<String, String> map = new HashMap<String, String>();
	    	  try {
	    		  //userid需要从数据库读取
				jsonObject.put("userid", userid);
				jsonObject.put("flag", LandFTip);
				jsonObject.put("state", "未领取");
				jsonObject.put("category", thingsType);
				jsonObject.put("content", describe);
				//createTime时间当前读取
			//	jsonObject.put("createTime", "未领取");
				//加了phone和QQ
				jsonObject.put("phone", phone);
				jsonObject.put("QQ", QQ);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	Log.e("post",jsonObject.toString());
	        
	         try{
	    	 jsonObjectRequest = new JsonObjectRequest(
	                   Request.Method.POST, url, jsonObject,
	                   new Response.Listener<JSONObject>() {
	                       @Override
	                       public void onResponse(JSONObject response) {
	                           //���?�ص�JSON���
	                           Log.e("bbb", response.toString());
	                      
	                           //根据前面的标注获取字符串
								Object it = response.opt("success");
								
								Log.e("success",it.toString());

	                               }
	                       
	                   }, new Response.ErrorListener() {
	                       @Override
	                       public void onErrorResponse(VolleyError arg0) {
	                           // System.out.println("sorry,Error");
	                           Log.e("aaa", arg0.toString());
	                       }
	                   });
	    	 
	    	    requestQueue.add(jsonObjectRequest);
	         } catch (Exception e) {
	             e.printStackTrace();
	             System.out.println(e + ""); 
	         } 
	         requestQueue.start();
	       
	       
	    	
	    }
	 
	
	

}
