package com.xyt.client.ui;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import java.util.regex.*;
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
import com.example.xiaoyuantong.R;

public class PersonalInfoActivity extends Activity {
	private Intent intent;
	private EditText userNameEditText;
	private String userName;
	private Spinner sexSpinner;
	private String sex;
	//private ArrayAdapter sexAdapter;
	private Spinner academySpinner;
	private String academy;
	//private ArrayAdapter academyAdapter;
	private Spinner majorSpinner;
	private String major;
	//private ArrayAdapter majorAdapter;
	private Spinner gradeSpinner;
	private String grade;
	//private ArrayAdapter gradeAdapter;
	private EditText emailEditText;
	private String Email;
	private EditText phoneNumberEditText;
	private String phoneNumber;
	private EditText homeTownEditText;
	private String homeTown;
	private EditText QQEditText;
	private String QQ;
	private Spinner graduatePlanSpinner;
	private String graduatePlan;
	//private ArrayAdapter graduatePlanAdapter;
	private Button reset;
	private Button register;
	
	private RequestQueue requestQueue ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_info);
		
		this.intent = new Intent(this,MainActivity.class);

		userNameEditText=(EditText)findViewById(R.id.etUserName);
		sexSpinner = (Spinner) findViewById(R.id.sex);
		//将可选内容与ArrayAdapter连接起来
		//sexAdapter = ArrayAdapter.createFromResource(this, R.array.sex, android.R.layout.simple_spinner_item);
		//将sexAdapter 添加到spinner中
		//sexSpinner.setAdapter(sexAdapter);
		academySpinner = (Spinner) findViewById(R.id.academy);
		//academyAdapter = ArrayAdapter.createFromResource(this, R.array.academy, android.R.layout.simple_spinner_item);
		//academySpinner.setAdapter(academyAdapter);
		majorSpinner = (Spinner) findViewById(R.id.major);
		//majorAdapter = ArrayAdapter.createFromResource(this, R.array.major, android.R.layout.simple_spinner_item);
		//majorSpinner.setAdapter(majorAdapter);
		gradeSpinner = (Spinner) findViewById(R.id.grade);
		//gradeAdapter = ArrayAdapter.createFromResource(this, R.array.grade, android.R.layout.simple_spinner_item);
		//gradeSpinner.setAdapter(gradeAdapter);
		emailEditText=(EditText)findViewById(R.id.etEmail);
		phoneNumberEditText=(EditText)findViewById(R.id.etPhoneNumber);
		homeTownEditText=(EditText)findViewById(R.id.etHomeTown);
		QQEditText=(EditText)findViewById(R.id.etQQ);
		graduatePlanSpinner = (Spinner) findViewById(R.id.graduatePlan);
		//graduatePlanAdapter = ArrayAdapter.createFromResource(this, R.array.graduatePlan, android.R.layout.simple_spinner_item);
		//graduatePlanSpinner.setAdapter(graduatePlanAdapter);
		reset = (Button) findViewById(R.id.reset);
		register = (Button) findViewById(R.id.register);
		
		//添加事件Spinner事件监听  
		sexSpinner.setOnItemSelectedListener(new SpinnerXMLSelectedListener());
		academySpinner.setOnItemSelectedListener(new SpinnerXMLSelectedListener());
		majorSpinner.setOnItemSelectedListener(new SpinnerXMLSelectedListener());
		gradeSpinner.setOnItemSelectedListener(new SpinnerXMLSelectedListener());
		graduatePlanSpinner.setOnItemSelectedListener(new SpinnerXMLSelectedListener());
		
		//设置默认值
		sexSpinner.setVisibility(View.VISIBLE);
		academySpinner.setVisibility(View.VISIBLE);
		majorSpinner.setVisibility(View.VISIBLE);
		gradeSpinner.setVisibility(View.VISIBLE);
		graduatePlanSpinner.setVisibility(View.VISIBLE);
		
		reset.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				userNameEditText.setText("");
				sexSpinner.setSelection(0);
				academySpinner.setSelection(0);
				majorSpinner.setSelection(0);
				gradeSpinner.setSelection(0);
				emailEditText.setText("");
				phoneNumberEditText.setText("");
				homeTownEditText.setText("");
				QQEditText.setText("");
				graduatePlanSpinner.setSelection(0);
			}
		});
		register.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				/*ifRegisterSuccess();
				if (flag) {
					register();
				}*/
				if (ifRegisterSuccess()) {
					post();
					startActivity(intent);
				}
			}

		});
		
        //(处理学院的显示) 
        //将可选内容与ArrayAdapter的连接(从资源数组文件中获取数据) 
        ArrayAdapter<CharSequence> adapter =  ArrayAdapter.createFromResource(this, R.array.academy, android.R.layout.simple_spinner_item); 
        //new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, provinces); 
        //设置下拉列表的风格 
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
        //将数据绑定到Spinner视图上 
        academySpinner.setAdapter(adapter);
        //添加条目被选中监听器 
        academySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { 
            @Override 
            public void onItemSelected(AdapterView<?> parent, View view,int position, long id) { 
                //parent既是province对象 
                Spinner spinner = (Spinner)parent; 
                String pro =  (String)spinner.getItemAtPosition(position); 

                ArrayAdapter<CharSequence> majorAdapter = ArrayAdapter.createFromResource (PersonalInfoActivity.this, R.array.academy, android.R.layout.simple_spinner_item); 
                 //new  ArrayAdapter<CharSequence> 
                      //           (MainActivity.this,android.R.layout.simple_spinner_item, cities); 
                //获取所在省含有哪些市(从资源数组文件中获取数据) 
                if(pro.equals("信息与安全工程学院")){ 
                	majorAdapter = ArrayAdapter.createFromResource (PersonalInfoActivity.this, R.array.xinxi, android.R.layout.simple_spinner_item); 
                }else if(pro.equals("会计学院")){ 
                	majorAdapter = ArrayAdapter.createFromResource (PersonalInfoActivity.this, R.array.kuaiji, android.R.layout.simple_spinner_item); 
                }else if(pro.equals("金融学院")){ 
                	majorAdapter = ArrayAdapter.createFromResource (PersonalInfoActivity.this, R.array.jinrong, android.R.layout.simple_spinner_item); 
                } 
                //绑定数据到Spinner(City)上 
                majorSpinner.setAdapter(majorAdapter); 
            } 

               @Override 
               public void onNothingSelected(AdapterView<?> parent) { 
                
               } 
                   
        }); 
	}

	//使用XML形式操作
	class SpinnerXMLSelectedListener implements OnItemSelectedListener{
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
			//view2.setText("你使用什么样的手机："+adapter2.getItem(arg2));
			//Toast.makeText(PersonalInfoActivity.this, sexAdapter.getItem(arg2).toString(), Toast.LENGTH_SHORT).show();
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			
		}
		
	}
	
	/*
	 * 判断用户注册输入是否规范 录入信息验证 验证是否通过
	 */

	private boolean ifRegisterSuccess(){
    	//获取用户输入的信息
    	userName=userNameEditText.getText().toString();
    	Email=emailEditText.getText().toString();	
    	sex=sexSpinner.getSelectedItem().toString();
    	academy=academySpinner.getSelectedItem().toString();
    	major=majorSpinner.getSelectedItem().toString();
    	grade=gradeSpinner.getSelectedItem().toString();
    	phoneNumber=phoneNumberEditText.getText().toString();
    	homeTown=homeTownEditText.getText().toString();
    	QQ=QQEditText.getText().toString();
    	graduatePlan=graduatePlanSpinner.getSelectedItem().toString();
    	
    	if("".equals(userName)){
    		//用户输入用户名
    		Toast.makeText(PersonalInfoActivity.this, "真实姓名不能为空", Toast.LENGTH_SHORT).show();
    		//userNameEditText.setText("用户名不能为空");
    		//flag=false;
    		return false;
    	}else if (!("".equals(Email))&&!EmailFormat(Email)) {
    		Toast.makeText(PersonalInfoActivity.this, "邮箱格式不正确", Toast.LENGTH_LONG).show();
    		//flag=false;
    		return false;
        }else if (!("".equals(phoneNumber))&&!PhoneFormat(phoneNumber)) {
    		Toast.makeText(PersonalInfoActivity.this, "手机号码格式不正确", Toast.LENGTH_LONG).show();
    		//flag=false;
    		return false;
        }else{
        	//return flag;
        	Toast.makeText(PersonalInfoActivity.this, "个人资料注册成功", Toast.LENGTH_SHORT).show();
        	return true;
        }
    }
	
	//邮箱模式匹配
    private boolean EmailFormat(String Email) {//邮箱判断正则表达式
    	Pattern EmailPattern = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher EmailM = EmailPattern.matcher(Email);
    	return EmailM.matches();
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
		getMenuInflater().inflate(R.menu.personal_info, menu);
		return true;
	}
	
	private void post() {	 
		 requestQueue = Volley.newRequestQueue(this);    
		 getJson();
		 
		 //获取输入的数据，然后提交到后台
	 }
	 
	 void getJson(){  
    	//��ʼ��volley
    		 
    	 String url = "http://192.168.20.1:8080/xiaoyuantong/userAction!reg.action";
    	 
    	  JsonObjectRequest jsonObjectRequest ;
//������ʹ��volley
    	  JSONObject jsonObject=new JSONObject() ;
    	  
        // Map<String, String> map = new HashMap<String, String>();
    	  try {
			jsonObject.put("name", userName);
			jsonObject.put("sex", sex);
			jsonObject.put("college", academy);
			jsonObject.put("major",major);
			jsonObject.put("grade", grade);
			jsonObject.put("mail", Email);
			jsonObject.put("tel", phoneNumber);
			jsonObject.put("qq", QQ);
			jsonObject.put("hometown", homeTown);
			//加了毕业计划
			jsonObject.put("graduatePlan", graduatePlan);
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
						/*	Object it = response.opt("success");
							
							Log.e("success",it.toString());*/

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
