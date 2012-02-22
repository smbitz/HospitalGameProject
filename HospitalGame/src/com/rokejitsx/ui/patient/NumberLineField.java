package com.rokejitsx.ui.patient;

import org.anddev.andengine.entity.primitive.Rectangle;

import android.util.Log;

public class NumberLineField extends Rectangle{

  private NumberField[] numbers;
  private int numberValue;
  public NumberLineField(int maxCount){
    super(0, 0, 0, 0);
    numbers = new NumberField[maxCount];
    int x = 0;
    for(int i = 0;i < maxCount;i++){
      NumberField number = new NumberField();
      number.setPosition(x, 0);
      attachChild(number);
      x += number.getWidth();
      numbers[i] = number;	
    }
    NumberField number = numbers[0];
    setWidth(number.getWidth() * maxCount);
    setHeight(number.getHeight());    
    setAlpha(0);
  }	
  
  
  public void setNumber(int number){
	numberValue = number;
    //int divine = 10;
    //Log.d("RokejitsX", "number = "+number);
    for(int i = numbers.length - 1;i >= 0;i--){
      NumberField numberField = numbers[i];
      
      int num = number % 10;
      /*if( i == numbers.length - 1){
        num = number % 10;	  
      }else{
        num = number % 10;        
        divine = divine * 10;         
      }*/ 
      
      
      numberField.setNumber(num);
      
      if(number == 0 && i < numbers.length - 1)      
        numberField.setVisible(false);
      else
    	numberField.setVisible(true);
      
      number = number / 10;
      
    }
    
    
    
  }
  
  public int getNumber(){
    return numberValue;	  
  }
}
