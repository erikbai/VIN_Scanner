package com.KebSlot.vinscanner;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayCar extends Activity implements OnClickListener{

  private int pos_;
	private MyApp appState_;
	private TextView name_;
	private TextView vin_;
	private TextView year_;
	private TextView make_;
	private TextView model_;
	private TextView trim_;
	private TextView origin_;
	private TextView style_;
	private TextView engine_;
	
	private Button shareButton_;
	
	private ImageView iView_;
	
	private String toSend_;
	private Car c_;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_car);
        setTitle("VIN Scanner");
        MyApp appState = ((MyApp)getApplicationContext());
    	appState_ = appState;
    	
    	name_ = (TextView) findViewById(R.id.nameView);
    	vin_ = (TextView) findViewById(R.id.vinView);
    	year_ = (TextView) findViewById(R.id.yearView);
    	make_ = (TextView) findViewById(R.id.makeView);
    	model_ = (TextView) findViewById(R.id.modelView);
    	trim_ = (TextView) findViewById(R.id.trimView);
    	origin_ = (TextView) findViewById(R.id.originView);
    	style_ = (TextView) findViewById(R.id.styleView);
    	engine_ = (TextView) findViewById(R.id.engineView);
    	
    	shareButton_ = (Button) findViewById(R.id.shareButt);
    	iView_ = (ImageView)findViewById(R.id.imageView1);
    	
    	Intent i = getIntent();
    	pos_ = i.getExtras().getInt("displayThis");
    	List<Car> tempL = appState_.getCars();
    	
    	c_ = tempL.get(pos_);
    	iView_.setImageBitmap(c_.getBitmap());
    	name_.setText("Name: " + c_.getName());
    	vin_.setText("VIN: " + c_.getVin());
    	year_.setText("Year: " + c_.getYear());
    	make_.setText("Make: " + c_.getMake());
    	model_.setText("Model: " + c_.getModel());
    	trim_.setText("Trim: " + c_.getTrim());
    	origin_.setText("Origin: " + c_.getOrigin());
    	style_.setText("Style: " + c_.getStyle());
    	engine_.setText("Engine: " + c_.getEngine());
    	
    	toSend_ = "Check this car out:\n";
    	toSend_ +=  "Name: " + c_.getName()  + "\n";
    	toSend_ +=  "VIN: " + c_.getVin()  + "\n";
    	toSend_ +=  "Year: " + c_.getYear()  + "\n";
    	toSend_ +=  "Make: " + c_.getMake()  + "\n";
    	toSend_ +=  "Model: " + c_.getModel()  + "\n";
    	toSend_ +=  "Trim: " + c_.getTrim()  + "\n";
    	toSend_ +=  "Origin: " + c_.getOrigin()  + "\n";
    	toSend_ +=  "Style: " + c_.getStyle()  + "\n";
    	toSend_ +=  "Engine: " + c_.getEngine()  + "\n";
    	shareButton_.setOnClickListener(this);
    	
    }

    public void onClick(View v) {
    
    	if(v.getId() == R.id.shareButt)
    	{
    		Intent sendIntent = new Intent(Intent.ACTION_VIEW);
    		sendIntent.setType("vnd.android-dir/mms-sms");
    		sendIntent.putExtra("sms_body", toSend_);
    		startActivity(sendIntent);
    	}
    }
}
