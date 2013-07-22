package com.KebSlot.vinscanner;

import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class Http {

  public void get(String url, HttpCallback callback){
		
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet();
			request.setURI(new URI(url));
			HttpResponse response = client.execute(request);
			
			AsyncHttpRequest t = new AsyncHttpRequest(request,callback);
			t.execute();
			
		} catch (Exception e) {
			
		}
		
	}
	
	public void post(String url, Map<String,String> params, HttpCallback callback){
		HttpPost post = new HttpPost(url);
		
		List<NameValuePair> data = new ArrayList<NameValuePair>(params.size());
		for(String key : params.keySet()){
			data.add(new BasicNameValuePair(key, params.get(key)));
		}
		
		try{
			
			HttpPost request = new HttpPost(url);
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(data);
			request.setEntity(entity);
			
			
			AsyncHttpRequest t = new AsyncHttpRequest(request,callback);
			t.execute();
			
		}catch(Exception e){
			callback.onError(e);
		}
		
	}

	public String encodeUrlParams(Map<String,String> params){
		String encoded = "";
		
		boolean first = true;
		for(String key : params.keySet()){
			if(!first){
				encoded += "&";
			}
			else {
				first = false;
			}
			encoded += URLEncoder.encode(key)+"="+URLEncoder.encode(params.get(key));
		}
		
		return encoded;
	}
}
