package com.example.xiaoyuantong;

import java.util.Map;

import android.util.Log;

public class LoginDao implements LoginService {

	public boolean login(Map<String, String> map) {
		// TODO Auto-generated method stub
		boolean flag = false;
		String result = HttpClientUtils.sendHttpclientPost(
				CommonUrl.Login_URL, map, "gbk");
		Log.i("result", result);
		if (result.contains("成功")) {
			flag=true;
		}
		return flag;
	}
}
