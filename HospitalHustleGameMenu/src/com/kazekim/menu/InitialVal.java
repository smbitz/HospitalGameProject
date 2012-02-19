package com.kazekim.menu;

import java.util.ArrayList;

public class InitialVal {
	public static final int CAMERA_WIDTH = 800;
	public static final int CAMERA_HEIGHT = 600;
	
	private static ArrayList<Integer> appearedStationList;
	
	public InitialVal(){
		appearedStationList = new ArrayList<Integer>();
	}
	
	public static ArrayList<Integer> getAppearedStationList(){
		return appearedStationList;
	}
	
	public static void setAppearedStationList(ArrayList<Integer> _appearedStationList){
		appearedStationList = _appearedStationList;
	}
	
}
