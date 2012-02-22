package com.rokejitsx.audio;

import org.anddev.andengine.audio.BaseAudioManager;

import android.media.AudioManager;
import android.media.SoundPool;

public class MySoundManager extends BaseAudioManager<MySound>{
  // ===========================================================
  // Constants
  // ===========================================================

  private static final int MAX_SIMULTANEOUS_STREAMS_DEFAULT = 20;

	// ===========================================================
	// Fields
	// ===========================================================

	private final SoundPool mSoundPool;

	// ===========================================================
	// Constructors
	// ===========================================================

	public MySoundManager() {
		this(MAX_SIMULTANEOUS_STREAMS_DEFAULT);
	}

	public MySoundManager(final int pMaxSimultaneousStreams) {
		this.mSoundPool = new SoundPool(pMaxSimultaneousStreams, AudioManager.STREAM_MUSIC, 0);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public SoundPool getSoundPool() {
   	  return this.mSoundPool;
	}

 	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	@Override
	public void releaseAll() {
		super.releaseAll();

		this.mSoundPool.release();
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
