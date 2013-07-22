package com.KebSlot.vinscanner;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;

public class MyApp extends Application {

  private String tempName_;
	private List<Car> myCars_ = new ArrayList<Car>();
	
	public void setTempName(String tempName){
		tempName_ = tempName;
	}
	
	public void addCar(Car car){
		myCars_.add(car);
	}
	
	public List<Car> getCars(){
		return myCars_;
	}
}
