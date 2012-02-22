package com.rokejitsx.audio;

import java.io.FileDescriptor;
import java.io.IOException;

import org.anddev.andengine.audio.sound.Sound;
import org.anddev.andengine.audio.sound.SoundFactory;
import org.anddev.andengine.audio.sound.SoundManager;

import android.content.Context;

public class MySoundFactory {

  // ===========================================================
  // Constants
  // ===========================================================

  // ===========================================================
  // Fields
  // ===========================================================

  private static String sAssetBasePath = "";

  // ===========================================================
  // Constructors
  // ===========================================================

  // ===========================================================
  // Getter & Setter
  // ===========================================================

  /**
  * @param pAssetBasePath must end with '<code>/</code>' or have <code>.length() == 0</code>.
  */
	public static void setAssetBasePath(final String pAssetBasePath) {
  	  if(pAssetBasePath.endsWith("/") || pAssetBasePath.length() == 0) {
  	    sAssetBasePath = pAssetBasePath;
	  } else {
	    throw new IllegalStateException("pAssetBasePath must end with '/' or be lenght zero.");
  	  }
	}

	public static void reset() {
 	  SoundFactory.setAssetBasePath("");
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	public static MySound createSoundFromPath(final MySoundManager pSoundManager, final String pPath) throws IOException {
	  final int soundID = pSoundManager.getSoundPool().load(pPath, 1);
	  final MySound sound = new MySound(pSoundManager, soundID);
	  pSoundManager.add(sound);
	  return sound;
	}

	public static MySound createSoundFromAsset(final MySoundManager pSoundManager, final Context pContext, final String pAssetPath) throws IOException {
 	  final int soundID = pSoundManager.getSoundPool().load(pContext.getAssets().openFd(sAssetBasePath + pAssetPath), 1);
	  final MySound sound = new MySound(pSoundManager, soundID);
 	  pSoundManager.add(sound);
	  return sound;
	}

	public static MySound createSoundFromResource(final MySoundManager pSoundManager, final Context pContext, final int pSoundResID) {
	  final int soundID = pSoundManager.getSoundPool().load(pContext, pSoundResID, 1);
	  final MySound sound = new MySound(pSoundManager, soundID);
	  pSoundManager.add(sound);
	  return sound;
	}

	public static MySound createSoundFromFileDescriptor(final MySoundManager pSoundManager, final FileDescriptor pFileDescriptor, final long pOffset, final long pLength) throws IOException {
	  final int soundID = pSoundManager.getSoundPool().load(pFileDescriptor, pOffset, pLength, 1);
	  final MySound sound = new MySound(pSoundManager, soundID);
	  pSoundManager.add(sound);
	  return sound;
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
