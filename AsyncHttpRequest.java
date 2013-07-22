package com.KebSlot.vinscanner;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

public class AsyncHttpRequest extends AsyncTask<Void, Object, Object> {

  private HttpUriRequest request_;

	private HttpCallback callback_;
	
	private HttpResponse response_;
	
	private Exception exception_;
	
	private String content_;

	public AsyncHttpRequest(HttpUriRequest request, HttpCallback callback) {
		super();
		request_ = request;
		callback_ = callback;
	}

	@Override
	protected Object doInBackground(Void... params) {
		try{
			HttpClient client = new DefaultHttpClient();
			response_ = client.execute(request_);
			
			String val = "Result of request to ["+request_.getURI()+"]\nStatus Code:"+response_.getStatusLine().getStatusCode()+"\n";
			val += response_.getStatusLine().getReasonPhrase();
			String line = null;
			
			
			InputStream is = response_.getEntity().getContent();
			
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			
			val = val + sb.toString();
			
			content_ = val;
			
		}catch(Exception e){
			exception_ = e;
		}
		
		return response_;
	}

	@Override
	protected void onPostExecute(Object result) {
		super.onPostExecute(result);
		
		if(exception_ != null){
			callback_.onError(exception_);
		}
		else {
			callback_.onResponse(response_,content_);
		}
	}

}

