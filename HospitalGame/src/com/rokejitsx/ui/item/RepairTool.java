package com.rokejitsx.ui.item;

public class RepairTool extends Item{

  public RepairTool() {
	super(REPAIR_TOOL, -1);	
  }

  @Override
  public Item deepCopy() {	
	return new RepairTool();
  }

}
