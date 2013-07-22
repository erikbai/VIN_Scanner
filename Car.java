package com.KebSlot.vinscanner;

import android.graphics.Bitmap;

public class Car {

  
	private String vin_ = null;
	private String year_ = null;
	private String make_ = null;
	private String model_ = null;
	private String trim_ = null;
	private String origin_ = null;
	private String style_ = null;
	private String engine_ = null;
	
	private String name_; // memorable name, Ex: BMW for sale in Christiansburg
	
	private Bitmap myBitMap_;
	
	public Car(){
		
	}
	//Setter methods
	public void setVin(String vin){
		vin_ = vin;
	}
	
	public void setYear(String year){
		year_ = year;
	}
	
	public void setMake(String make){
		make_ = make;
	}
	
	public void setModel(String model){
		model_ = model;
	}
	
	public void setTrim(String trim){
		trim_ = trim;
	}
	
	public void setOrigin(String origin){
		origin_ = origin;
	}
	
	public void setStyle(String style){
		style_ = style;
	}
	
	public void setEngine(String engine){
		engine_ = engine;
	}
	
	public void setBitmap(Bitmap bmp){
		myBitMap_ = bmp;
	}
	//Getter Methods
	public String getVin(){
		return vin_;
	}
	
	public String getYear(){
		return year_;
	}
	
	public String getMake(){
		return make_;
	}
	
	public String getModel(){
		return model_;
	}
	
	public String getTrim(){
		return trim_;
	}
	
	public String getOrigin(){
		return origin_;
	}
	
	public String getStyle(){
		return style_;
	}
	
	public String getEngine(){
		return engine_;
	}
	
	
	public void setName(String name){
		name_ = name;
	}
	
	public String getName(){
		return name_;
	}
	
	public Bitmap getBitmap(){
		return myBitMap_;
	}
}	
