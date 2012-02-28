package com.kazekim.data;

import java.util.ArrayList;

import org.anddev.andengine.util.ArrayUtils;

import com.kazekim.menu.InitialVal;

public class UserMissionSkeleton {
	private int plantNum=0;
	private int waterNum=0;
	private int foodNum=0;
	private int bedNum=0;
	private int tvNum=0;
	private int stationNum=0;
	
	private int fund=0;
	
	private ArrayList<Integer> missionStationList;
	private ArrayList<Integer> appearedStationList;
	private ArrayList<Boolean> checkNewStationList;
	
	private int nurseColorShirtRed   =0;
	private int nurseColorShirtGreen =0;
	private int nurseColorShirtBlue  =0;
	
	private int pharmacyValue_Cur=0;
	private int pharmacyValue_Max=300;
	private int salaryValue_Cur=0;
	private int salaryValue_Max=300;
	
	private static UserMissionSkeleton instance;
	
	public static UserMissionSkeleton getInstance(){
	  if(instance == null)
	    instance = new UserMissionSkeleton();	 
	  return instance;
	}
	
	public UserMissionSkeleton(){	
	  missionStationList = new ArrayList<Integer>();
	  checkNewStationList = new ArrayList<Boolean>();		
      if(InitialVal.getAppearedStationList()==null){
		appearedStationList = new ArrayList<Integer>();
		InitialVal.setAppearedStationList(appearedStationList);
	  }else{
		appearedStationList = InitialVal.getAppearedStationList();
	  }
	}
	
	//Set Number 
	
	public void setPlantNum(int plantNum){
		this.plantNum = plantNum;
	}
	
	public void setWaterNum(int waterNum){
		this.waterNum = waterNum;
	}
	
	public void setFoodNum(int foodNum){
		this.foodNum = foodNum;
	}
	
	public void setBedNum(int bedNum){
		this.bedNum = bedNum;
	}
	
	public void setTvNum(int tvNum){
		this.tvNum = tvNum;
	}
	
	public void setStationNum(int stationNum){
		this.stationNum = stationNum;
	}
	
	public int getPlantNum(){
		return plantNum;
	}
	
	public int getWaterNum(){
		return waterNum;
	}
	
	public int getFoodNum(){
		return foodNum;
	}
	
	public int getBedNum(){
		return bedNum;
	}
	
	public int getStationNum(){
		return stationNum;
	}
	
	public int getTVNum(){
		return tvNum;
	}
	
	public void increasePlantNum(){
		this.plantNum++;
	}
	
	public void increaseWaterNum(){
		this.waterNum++;
	}
	
	public void increaseFoodNum(){
		this.foodNum++;
	}
	
	public void increaseBedNum(){
		this.bedNum++;
	}
	
	public void increaseTvNum(){
		this.tvNum++;
	}
	
	public void increaseStationNum(){
		this.stationNum++;
	}
	
	public void decreasePlantNum(){
		this.plantNum--;
	}
	
	public void decreaseWaterNum(){
		this.waterNum--;
	}
	
	public void decreaseFoodNum(){
		this.foodNum--;
	}
	
	public void decreaseBedNum(){
		this.bedNum--;
	}
	
	public void decreaseTvNum(){
		this.tvNum--;
	}
	
	public void decreaseStationNum(){
		this.stationNum--;
	}
	
	// Funds
	
	public void setFund(int fund){
		this.fund = fund;
	}
	
	public int getFund(){
		return fund;
	}
	
	//Set Nurse's Color Shirt
	
	public void setColorNurseShirtRed(int nurseColorShirtRed){
		this.nurseColorShirtRed = nurseColorShirtRed;
	}
	
	public void setColorNurseShirtGreen(int nurseColorShirtGreen){
		this.nurseColorShirtGreen = nurseColorShirtGreen;
	}
	
	public void setColorNurseShirtBlue(int nurseColorShirtBlue){
		this.nurseColorShirtBlue = nurseColorShirtBlue;
	}
	
	public int getColorNurseShirtRed(){
		return nurseColorShirtRed;	
	}
	
	public int getColorNurseShirtGreen(){
		return nurseColorShirtGreen;	
	}
	
	public int getColorNurseShirtBlue(){
		return nurseColorShirtBlue;	
	}
	
	//Set Expenses
	
	public void setPharmacyCurValue(int pharmacyValue_Cur){
		this.pharmacyValue_Cur = pharmacyValue_Cur;
	}
	
	public void setPharmacyMaxValue(int pharmacyValue_Max){
		this.pharmacyValue_Max = pharmacyValue_Max;
	}
	
	public void setSalaryCurValue(int salaryValue_Cur){
		this.salaryValue_Cur = salaryValue_Cur;
	}
	
	public void setSalaryMaxValue(int salaryValue_Max){
		this.salaryValue_Max = salaryValue_Max;
	}
	
	public int getPharmacyCurValue(){
		return pharmacyValue_Cur;
	}
	
	public int getPharmacyMaxValue(){
		return pharmacyValue_Max;
	}
	
	public int getSalaryCurValue(){
		return salaryValue_Cur;
	}
	
	public int getSalaryMaxValue(){
		return salaryValue_Max;
	}
	
	//Manage Station List
	
	public void addMissionStation(Integer stationID){
		if(!missionStationList.contains(stationID)){
			missionStationList.add(stationID);
		}
		if(!appearedStationList.contains(stationID)){
			appearedStationList.add(stationID);
			checkNewStationList.add(true);
		}else{
			checkNewStationList.add(false);
		}
	}
	
	public void addAppearedStation(Integer stationID){
		appearedStationList.add(stationID);
	}
	
	public ArrayList<Integer> getMissionStation(){
		return missionStationList;
	}
	
	public Integer getMissionStationAtIndex(int index){
		return missionStationList.get(index);
	}
	
	public boolean getIsNewAtIndex(int index){
		return checkNewStationList.get(index);
	}
	
	public boolean getIsNewStation(Integer stationID){
		return checkNewStationList.get(missionStationList.indexOf(stationID));
	}
	
	public int getMissionStationNumCount(){
		return missionStationList.size();
	}
	
	public int getAppearedStationListNumCount(){
		return appearedStationList.size();
	}
	
}
