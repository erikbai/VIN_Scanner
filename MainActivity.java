package com.KebSlot.vinscanner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.google.zxing.*;

import android.text.format.Time;
import android.util.Log;

public class MainActivity extends Activity implements OnClickListener, HttpCallback{
  
	private Button scanButton_;
	private Button viewButton_;
	private MyApp appState_;
	
	Bitmap mImageBitmap_;
	
	private String vin_;

	private int counter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("VIN Scanner");
        MyApp appState = ((MyApp)getApplicationContext());
    	appState_ = appState;
        scanButton_ = (Button)findViewById(R.id.scanButt);
        viewButton_ = (Button)findViewById(R.id.viewButt);
        counter  = 0;
        vin_ = null;
        
        scanButton_.setOnClickListener(this);
        viewButton_.setOnClickListener(this);
    }


	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.scanButt){
			Intent in = new Intent("com.google.zxing.client.android.SCAN");
			in.putExtra("SCAN_MODE", "PRODUCT_MODE");
			startActivityForResult(in, 0);
		}
		if(v.getId() == R.id.viewButt){
			Intent in = new Intent(this, ViewActivity.class);
			startActivity(in);
		}
		
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent in){
		if(requestCode == 0 && counter == 0){
			if(resultCode == RESULT_OK){
				String contents = in.getStringExtra("SCAN_RESULT");
				String format = in.getStringExtra("SCAN_RESULT_FORMAT");
				Log.i("contents",contents);
				Log.i("format", format);
				counter++;
				//vin_ = contents;
				vin_ = "1HGEG8557SL022455";
				/*Map<String,String> params = new HashMap<String, String>();
				params.put("vin", "1HGEG8557SL022455");
				params.put("key", "QX86F96ZNM6KGSW");
				params.put("format", "json");

				Http http = new Http();
				http.post("https://api.vinaudit.com/query.php", params, this);*/
				Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			    startActivityForResult(takePictureIntent, 0);
				
			} else if(resultCode == RESULT_CANCELED){
				
			}
		}
		else if(counter == 1){
			if(resultCode == RESULT_OK){
				Bundle extras = in.getExtras();
				mImageBitmap_ = (Bitmap) extras.get("data");
				counter = 0;
				Map<String,String> params = new HashMap<String, String>();
				params.put("vin", vin_);
				params.put("key", "QX86F96ZNM6KGSW");
				params.put("format", "json");
				Http http = new Http();
				http.post("https://api.vinaudit.com/query.php", params, this);
				String root = Environment.getExternalStorageDirectory().toString();
				File myDir = new File(root + "/Pictures");
				ByteArrayOutputStream bytes = new ByteArrayOutputStream();
				mImageBitmap_.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
				File file = new File(myDir, vin_ +".jpg");
				
				if(file.exists()){
					file.delete();
				}
				
				try {
					
					FileOutputStream fo = new FileOutputStream(file);
					fo.write(bytes.toByteArray());
					fo.flush();
					fo.close();
					MediaStore.Images.Media.insertImage(getContentResolver(), 
							file.getPath(), file.getName(), file.getName());

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			else if(resultCode == RESULT_CANCELED)
			{
				
			}
		}
		else 
		{
			//should never reach here
		}
	}
	
	public void onResponse(HttpResponse resp, String content) {
		if(content != null){
			
			String[] contents = content.split("OK");
			content = contents[1];
			
			
			JSONObject jsonobj = null;
			String vin = null;
			String year = null;
			String make = null;
			String model = null;
			String trim = null;
			String origin = null;
			String style = null;
			String engine = null;
			
			try {
				jsonobj = new JSONObject(content);
				jsonobj = jsonobj.getJSONObject("attributes");
				vin = jsonobj.getString("VIN");
				year = jsonobj.getString("Year");
				make = jsonobj.getString("Make");
				model = jsonobj.getString("Model");
				trim = jsonobj.getString("Trim");
				origin = jsonobj.getString("Made In");
				style = jsonobj.getString("Style");
				engine = jsonobj.getString("Engine");
				
				Car c = new Car();
				c.setVin(vin);
				c.setYear(year);
				c.setMake(make);
				c.setModel(model);
				c.setTrim(trim);
				c.setOrigin(origin);
				c.setStyle(style);
				c.setEngine(engine);
				c.setBitmap(mImageBitmap_);
				
				Time now = new Time();
				now.setToNow();
				String name = make + " " + model + " " + year + " ";
				name += now.year + now.month + now.monthDay
						+ now.hour + now.minute + now.second;
				c.setName(name);
				
				appState_.addCar(c);
				Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
				v.vibrate(10000);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}


	public void onError(Exception e) {
		// TODO Auto-generated method stub
		
	}
}

