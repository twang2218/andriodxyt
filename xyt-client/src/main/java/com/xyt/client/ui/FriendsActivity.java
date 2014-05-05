package com.xyt.client.ui;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ExpandableListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.xiaoyuantong.R;

public class FriendsActivity extends ExpandableListActivity {

	private RequestQueue requestQueue; // 定义请求队列
	//String[] groupname = { "", "", "", "" };

	List<String> group; // 组列表
	List<List<String>> child; // 子列表
	ContactsInfoAdapter adapter; // 数据适配器

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置为无标题
		setContentView(R.layout.activity_friends);
		getExpandableListView().setBackgroundResource(R.drawable.background);
		requestQueue = Volley.newRequestQueue(this); // 获取请求
		getJson();// 向后台发送请求，获取数据
		group = new ArrayList<String>();
		child = new ArrayList<List<String>>();
	
	//	initializeData();
		 // 设置拖动列表的时候 防止出现黑色背景
		getExpandableListView().setAdapter(new ContactsInfoAdapter());
		getExpandableListView().setCacheColorHint(0);
	}

	/**
	 * 初始化组、子列表数据
	 */
	/*
	 * private void init() {
	 * 
	 * requestQueue = Volley.newRequestQueue(this); getJson(); //
	 * textView.setText("hellopost");
	 * 
	 * }
	 */
/*	private void initializeData() {
		
		
		Log.e("mesg","meiyou");
		// 因为getjson是在
	//	Log.e("test", groupname[0]);
		// addInfo(groupname.get(0).toString(),new
		// String[]{"male","138123***","GuangZhou"});
	
		  
		  addInfo("lily",new String[]{"female","138123***","GuangZhou"});
		  addInfo("lucy",new String[]{"male","138123***","ShenZhen"});
		  addInfo("kate",new String[]{"female","138123***","ShangHai"});
		  addInfo("david",new String[]{"male","138231***","ZhanJiang"});
		 
	}*/

	/**
	 * 模拟给组、子列表添加数据
	 * 
	 * @param g
	 *            -group
	 * @param c
	 *            -child
	 */
	private void addInfo(String g, String[] c) {
		group.add(g);
		List<String> childitem = new ArrayList<String>();
		for (int i = 0; i < c.length; i++) {
			childitem.add(c[i]);
		}
		child.add(childitem);
		
		Log.e("addinfo","meiyou");
	}

	class ContactsInfoAdapter extends BaseExpandableListAdapter {

		// -----------------Child----------------//
		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return child.get(groupPosition).get(childPosition);
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return child.get(groupPosition).size();
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			String string = child.get(groupPosition).get(childPosition);
			return getGenericView(string);
		}

		// ----------------Group----------------//
		@Override
		public Object getGroup(int groupPosition) {
			return group.get(groupPosition);
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public int getGroupCount() {
			return group.size();
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			String string = group.get(groupPosition);
			return getGenericView(string);
		}

		// 创建组/子 视图
		public TextView getGenericView(String s) {
			// Layout parameters for the ExpandableListView
			AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
					ViewGroup.LayoutParams.FILL_PARENT, 40);

			TextView text = new TextView(FriendsActivity.this);
			text.setLayoutParams(lp);
			// Center the text vertically
			text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
			// Set the text starting position
			text.setPadding(70, 0, 0, 0);

			text.setText(s);
			return text;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return true;
		}

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.friends, menu);
		return true;
	}

	private void getJson() {
		// ��ʼ��volley
		//最新添加的获取本地ID
		SharedPreferences nu=getSharedPreferences("number",0);//login是存储文件  
		String number=nu.getString("studentId", "");
	//	System.out.println(number+"!");
		Log.v("cola","data="+number);
		
		//sp = getSharedPreferences("users",Context.MODE_WORLD_READABLE); 
	//	Context ctx = LoginActivity.this;
	//	SharedPreferences sp = ctx.getSharedPreferences("SP", MODE_PRIVATE);
		String userid =number;
		String url = "http://192.168.20.1:8080/xiaoyuantong/groupAction!GroupList.action?id="+userid;

		// ������ʹ��volley
		

		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.GET, url, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						try {
							group = new ArrayList<String>();
							child = new ArrayList<List<String>>();
							// ���?�ص�JSON���
							Log.e("bbb", response.toString());
							// JSONArray json=response.toJSONArray(names);
							// JSONArray json = JsonArrayRequest;
							// 根据前面的标注获取字符串
							/*
							 * Object it = response.opt("0");
							 * groupname[0]=it.toString();
							 * Log.e("0",it.toString());
							 * addInfo(groupname[0],new
							 * String[]{"female","138123***","GuangZhou"});
							 */
							
							JSONArray json = null;
							json = response.getJSONArray("data");
						//	Log.e("date", json.toString());
							//String groupId = "";
							String groupName = "";
							String groupId = "";
						//	List<String> groupList = new ArrayList<String>();
							for (int j = 0; j < json.length(); j++) {
								JSONObject object = json.getJSONObject(j);
								groupId = object.opt("groupId").toString();
								groupName = object.opt("groupName").toString();
							//	Log.e("groupname", groupName);
								JSONArray members = object.getJSONArray("grouplist");
								String [] grouplist = new String[members.length()];
								for (int k = 0 ; k < members.length(); k ++) {
									String member = members.opt(k).toString();
								//	Log.e("member", member);
									grouplist[k]=member;
								//	Log.e("member",grouplist[k]);
								    }
								addInfo(groupName,grouplist);
							//    System.out.println("第" + j++ + "组包含信息为：id=" + groupId + ",组名=" + groupName + "，包含成员：" + groupList.toString());
							}
							
							getExpandableListView().setAdapter(new ContactsInfoAdapter());
							getExpandableListView().setCacheColorHint(0);

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
