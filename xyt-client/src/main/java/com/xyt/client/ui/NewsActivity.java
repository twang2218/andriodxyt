package com.xyt.client.ui;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.xiaoyuantong.R;

import android.util.Log;
import android.view.Menu;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

/*
 * 动态
 */
public class NewsActivity extends Activity {
	private Intent writeNewsIntent;
	private RequestQueue requestQueue; // 定义请求队列
	ListView list;
	ArrayList<HashMap<String, Object>> listItem;
	SimpleAdapter listItemAdapter;
	
	ImageButton writeBtn;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        this.writeNewsIntent = new Intent(this,WriteNewsActivity.class);
        //动态请求
        requestQueue = Volley.newRequestQueue(this); // 获取请求
		getJson();// 向后台发送请求，获取数据
		

        writeBtn = (ImageButton) findViewById(R.id.writeBtn);

        writeBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				startActivity(writeNewsIntent);
			}			
		});
        //绑定Layout里面的ListView
        list = (ListView) findViewById(R.id.ListView01);
        
        //生成动态数组，加入数据
        listItem = new ArrayList<HashMap<String, Object>>();
       
        	HashMap<String, Object> map = new HashMap<String, Object>();
        	map.put("ItemImage", R.drawable.friends);//图像资源的ID
        	map.put("ItemTitle", "");
        	map.put("ItemText", "");
        	listItem.add(map);
        
       //生成适配器的Item和动态数组对应的元素
      //  SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,//数据源 
        	
        
     
      
        listItemAdapter = new SimpleAdapter(this,listItem,//数据源
            R.layout.news,//ListItem的XML实现
            //动态数组与ImageItem对应的子项        
            new String[] {"ItemImage","ItemTitle", "ItemText"}, 
            //ImageItem的XML文件里面的一个ImageView,两个TextView ID
            new int[] {R.id.ItemImage,R.id.ItemTitle,R.id.ItemText}
        );
       
        //添加并且显示
        list.setAdapter(listItemAdapter);
        
        //添加点击
        list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				setTitle("点击第"+arg2+"个项目");
			}
		});
        
      //添加长按点击
        list.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
			
			@Override
			public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
				menu.setHeaderTitle("长按菜单-ContextMenu");   
				menu.add(0, 0, 0, "弹出长按菜单0");
				menu.add(0, 1, 0, "弹出长按菜单1");   
			}
		}); 
    }
	
	
	
	
	//长按菜单响应函数
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		setTitle("点击了长按菜单里面的第"+item.getItemId()+"个项目"); 
		return super.onContextItemSelected(item);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.news, menu);
		return true;
	}
	
	private void getJson() {
		// ��ʼ��volley

		String url = "http://192.168.20.1:8080/xiaoyuantong/dynamicAction!dynamicList.action";

		// ������ʹ��volley
		

		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.GET, url, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						try {
						
							// ���?�ص�JSON���
							Log.e("bbb", response.toString());
							JSONArray json = null;
							json = response.getJSONArray("dynamic");
						//	Log.e("date", json.toString());
							//String groupId = "";
							String massage = "";
							String createtime = "";
							String username = "";
							list = (ListView) findViewById(R.id.ListView01);
							listItem.clear();
							
						//	List<String> groupList = new ArrayList<String>();
							for (int j = 0; j < json.length(); j++) {
								//获取一列一列的对象
								HashMap<String, Object> map = new HashMap<String, Object>();
								JSONObject object = json.getJSONObject(j);
								createtime = object.opt("createtime").toString();
								massage = object.opt("massage").toString();
								username = object.opt("username").toString();
								Log.e("createtime", createtime);
								Log.e("massage", massage);
							// 
							     map.put("ItemImage", R.drawable.friends);//图像资源的ID
							     map.put("ItemTitle", username+":"+createtime);
							     map.put("ItemText", massage);
							     listItem.add(map);
								 listItemAdapter.notifyDataSetChanged();
							//	 listItemAdapter.notifyDataSetInvalidated();
								 list.setAdapter(listItemAdapter);
							}
							
						
						} catch (JSONException e1) {
							e1.printStackTrace();
						}
						
					}

			    

				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {
						// System.out.println("sorry,Error");
						Log.e("aaa", arg0.toString());
					}
				});
		requestQueue.add(jsonObjectRequest);

	}
	
	
}