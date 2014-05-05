package com.example.xiaoyuantong;

import java.util.Map;

import android.util.Log;
/*
 * 与loginDao相似，但是不同
 */
public class LoginServiceImpl implements LoginService {

	public boolean login(Map<String, String> map) {
		// TODO Auto-generated method stub
		boolean flag = false;
		String result = HttpClientUtils.sendHttpclientPost(
				CommonUrl.Login_URL2, map, "gbk");
		Log.i("result", result);
		if (result.contains("true")) {
			flag=true;
		}
		return flag;
	}
}
