package com.KebSlot.vinscanner;

import org.apache.http.HttpResponse;

public interface HttpCallback {

  public void onResponse(HttpResponse resp, String content);
	public void onError(Exception e);
	
}
