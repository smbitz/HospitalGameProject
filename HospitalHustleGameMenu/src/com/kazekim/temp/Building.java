package com.kazekim.temp;

import com.rokejitsx.data.resource.ImageResource;

public class Building implements ImageResource{
	  public final static int NONE				= 0;
	  public final static int BED				= 1;
	  public final static int CHAIR				= 2; 
	  public final static int XRAY				= 3;
	  public final static int QUICKTREAT		= 4;
	  public final static int GLASSDOOR			= 5;
	  public final static int OUTSIDE			= 6;
	  public final static int TRIAGE			= 7;
	  public final static int ELEVATOR 			= 8;
	  public final static int OUTSIDE_ELEVATOR	= 9;
	  public final static int LAUNDRY			= 10;
	  public final static int PHARMACY			= 11;
	  public final static int AMBULANCE			= 12;
	  public final static int PLANT				= 13;
	  public final static int CLOSET			= 14;
	  public final static int WATER				= 15;
	  public final static int FOOD				= 16;	
	  public final static int TAC				= 17;
	  public final static int GARBAGE			= 18;
	  public final static int PHYSIOTHERAPY		= 19;
	  public final static int OPHTHALMOLOGY		= 20;
	  public final static int PSYCHIATRY		= 21;
	  public final static int CHEMOTHERAPY		= 22;
	  public final static int BABY_SCAN			= 23;
	  public final static int DENTIST			= 24;
	  public final static int CARDIOLOGY		= 25;
	  public final static int OPERATION			= 26;
	  public final static int CLOSET1			= 27;
	  public final static int CLOSET2			= 28;
	  public final static int CLOSET3			= 29;
	  public final static int CLOSET4			= 30;
	  public final static int ULTRASCAN			= 31;
	  public final static int UPPER_PHARMACY	= 32;
	  public final static int STRETCHER			= 33;
	  public final static int TELEVISION		= 34;
	  public final static int HELICOPTER		= 35;
	  public final static int MAX_BUILDING		= 36;	
	  
	  public final static String[] buildingImgNameList = {    
	    null,						// NONE
	    MONTAGE_CAMAS,				// BED
	    MONTAGE_CHAIR,				// CHAIR
	    MONTAGE_XRAY,   			// XRAY
	    MONTAGE_QUICKTREAT,			// QUICKTREAT
	    MONTAGE_GLASSDOOR,			// GLASSDOOR
	    null,						// OUTSIDE
	    MONTAGE_MEDICOTRIAGEM,		// TRIAGE
	    MONTAGE_PORTAS_ELEVADORES,	// ELEVATOR
	    null,						// OUTSIDE_ELEVATOR;
	    MONTAGE_ROUPA,				// LAUNDRY
	    MONTAGE_FARMACIA_MEDICO,	// PHARMACY
	    MONTAGE_AMBULANCE,			// AMBULANCE
	    MONTAGE_PLANT,				// PLANT
	    null,						// CLOSET
	    MONTAGE_WATER,				// WATER
	    MONTAGE_FOOD,				// FOOD	
	    MONTAGE_TAC,				// TAC
	    null,						// GARBAGE
	    MONTAGE_PHYSIOTHERAPY,		// PHYSIOTHERAPY
	    MONTAGE_OPHTHALMOLOGY,		// OPHTHALMOLOGY
	    MONTAGE_PSYCHIATRY,			// PSYCHIATRY
	    MONTAGE_CHEMOTHERAPY,		// CHEMOTHERAPY
	    MONTAGE_BABYSCAN,			// BABY_SCAN
	    MONTAGE_DENTIST,			// DENTIST
	    MONTAGE_CARDIO,				// CARDIOLOGY
	    MONTAGE_OPERATION,			// OPERATION
	    null,						// CLOSET1
	    null,						// CLOSET2
	    null,						// CLOSET3
	    null,						// CLOSET4
	    MONTAGE_ULTRASCAN,			// ULTRASCAN
	    null,						// UPPER_PHARMACY
	    null,						// STRETCHER
	    null,						// TELEVISION
	    MONTAGE_HELICOPTER,			// HELICOPTER
	  };
	 
}
