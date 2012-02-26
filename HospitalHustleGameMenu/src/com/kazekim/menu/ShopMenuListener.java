package com.kazekim.menu;

public interface ShopMenuListener {
	public void onContinueButtonClick(ShopMenu shopMenu);  	
	public void onBuyButtonClick(ShopMenu shopMenu,int buildingNumber);
	public void onSellButtonClick(ShopMenu shopMenu,int buildingNumber);
}
