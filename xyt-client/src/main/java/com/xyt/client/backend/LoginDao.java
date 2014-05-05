package com.xyt.client.backend;

import java.util.Map;

import android.util.Log;

import com.xyt.client.util.CommonUrl;
import com.xyt.client.util.HttpClientUtils;

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
