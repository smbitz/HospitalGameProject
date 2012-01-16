package com.rokejitsx.data.xml.global;

import com.rokejitsx.data.xml.DataHolder;

public class Sliders extends DataHolder{

  public final static String SALARY_SLIDER_DIV = "SalarySliderDiv";	
  public final static String SALARY_SLIDER_0 = "SalarySlider0";
  public final static String SALARY_SLIDER_1 = "SalarySlider1";
  public final static String SALARY_SLIDER_2 = "SalarySlider2";
  public final static String SALARY_SLIDER_3 = "SalarySlider3";
  public final static String SALARY_SLIDER_4 = "SalarySlider4";
  
  private final static String[] keys = {
    SALARY_SLIDER_DIV,
    SALARY_SLIDER_0,
    SALARY_SLIDER_1,
    SALARY_SLIDER_2,
    SALARY_SLIDER_3,
    SALARY_SLIDER_4	  
  };
  
  public Sliders(){
    super(GlobalsXmlReader.GLOBAL_SLIDERS, keys);	  
  }
	
}
