package com.kazekim.menu;

import com.kazekim.ui.TextButton;

public interface PauseMenuListener {
	public void onResumeButtonClick(PauseMenu pauseMenu);  	
	public void onRestartButtonClick(PauseMenu pauseMenu);
	public void onAchievementButtonClick(PauseMenu pauseMenu);
	public void onOptionsButtonClick(PauseMenu pauseMenu);
	public void onMainMenuButtonClick(PauseMenu pauseMenu);
	
}
