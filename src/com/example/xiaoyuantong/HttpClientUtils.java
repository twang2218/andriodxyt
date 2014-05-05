package com.example.xiaoyuantong;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientUtils {
	// 用全局变量因为HttpClient可以自动处理cookie，但必须保证是同一个HttpClient
	private static HttpClient httpClient;

	public static HttpClient getClient() {
		return httpClient;
	}

	public static void closeClient() {
		// 获得所有的链接资源，一般在所有的请求处理完成之后，才需要释放
		if (httpClient != null)
			httpClient.getConnectionManager().shutdown();
	}

	public static String sendHttpclientPost(String path,
			Map<String, String> map, String encode) {
		// TODO Auto-generated method stub
		// 填充表单的内容
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		// Key必须与action接收params的key一致
		if (map != null && !map.isEmpty()) {
			// 一个entry代表一个键值对，.getKey()获取键，getValue()获取值
			for (Map.Entry<String, String> entry : map.entrySet()) {
				pairs.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
			}
		}
		try {
			// 添加form表单的内容，和网页的jsp一样
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs,
					encode);
			// 使用post提交数据
			HttpPost httpPost = new HttpPost(path);
			httpPost.setEntity(entity);
			httpClient = new DefaultHttpClient();
			// HttpResponse获得服务器响应的所有消息
			HttpResponse response = httpClient.execute(httpPost);
			// 判断连接成功
			if (response.getStatusLine().getStatusCode() == 200) {
				// HttpEntity获得服务器响应回来的消息体(不包括HTTP HEAD)
				HttpEntity httpEntity = response.getEntity();
				// 自动分辨响应的内容的编码
				String defaultCharset = EntityUtils
						.getContentCharSet(httpEntity);
				// EntityUtils.toString将获得的消息体转换成String
				return EntityUtils.toString(httpEntity, defaultCharset);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 因为结果经常调用string的函数，所以没有返回null
		return "";

	}

	public static String sendHttpclientGet(HttpClient httpClient, String path) {

		try {// 利用HTTPGET 向服务器发起请求
				// 如果URL连接过程中302重定向，则会自动跳转到新的页面
			HttpGet get = new HttpGet(path);

			// HttpResponse获得服务器响应的所有消息
			HttpResponse response = httpClient.execute(get);

			// HttpEntity获得服务器响应回来的消息体(不包括HTTP HEAD)
			HttpEntity httpEntity = response.getEntity();

			if (httpEntity != null) {// 获取消息体的内容
				String defaultCharset = EntityUtils
						.getContentCharSet(httpEntity);
				// EntityUtils.toString将获得的消息体转换成String
				return EntityUtils.toString(httpEntity, defaultCharset);
			}

		} catch (Exception e) {
		}
		return "";// 返回"",但不是空值
	}

	public static String sendHttpclientGet(String path) {
		// HttpClient主要负责执行请求
		HttpClient httpClient = new DefaultHttpClient();
		return sendHttpclientGet(httpClient, path);
	}
}
