package com.hoperun.chorny.customlistview.utils;

import java.io.IOException;
import java.net.URLEncoder;
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

import android.util.Log;
/**
 * created by xiaoyu.zhang at 2017/2/25
 */
public class WebUtils {

	/**
	 * get method
	 * @param path
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static byte [] sendHttpGet(String path,Map<String,String> params) throws Exception{
		
		StringBuffer sb = new StringBuffer(path);
		if( params!=null && !params.isEmpty() ){
			
			sb.append("?");
			for( Map.Entry<String, String> entry:params.entrySet() ){
				
				sb.append(entry.getKey());
				sb.append("=");
				sb.append(URLEncoder.encode(entry.getValue(), "utf-8"));
				sb.append(entry.getValue());
				sb.append("&");

			}
			
			sb = sb.deleteCharAt(sb.length()-1);
			Log.i("xiaoyu",sb.toString());
}
		
				HttpClient client = new DefaultHttpClient();  //浏览器对象
				HttpGet get = new HttpGet(path);
				HttpResponse resp = client.execute(get);
				if(resp.getStatusLine().getStatusCode()==200){
					
					HttpEntity entity = resp.getEntity();
					
					byte [] data = EntityUtils.toByteArray(entity);
					return data;
				}else{
					return null;
				}
				}


	/**
	 * post method
	 * @param path
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static byte[] sendHttpPost(String path,Map<String,String> params) throws Exception{
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(path);
		if(  params!=null && !params.isEmpty() ){
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			
			UrlEncodedFormEntity formFactory = new UrlEncodedFormEntity(list,"utf-8");
			for( Map.Entry<String, String> entry:params.entrySet() ){
				
				list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				
			}
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(list,"utf-8");
			post.setEntity(formEntity);	
			
		}
		HttpResponse resp = client.execute(post);
		if( resp.getStatusLine().getStatusCode()==200 ){
			HttpEntity entity = resp.getEntity();
			return EntityUtils.toByteArray(entity);
		}
		return null;
		
		
		
	}

}

