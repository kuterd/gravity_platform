package com.kuter.bcw;

import com.badlogic.gdx.math.Vector2;

public class Ripple {
	private static final int RIPPLE_DURATION = 25;

	private int mTime = 0;
	private Vector2 mLocalPos;

	public Ripple(Vector2 pos) {
		this.mLocalPos = pos;
	}
	
	public boolean update() {
		if (mTime >= RIPPLE_DURATION) return true;
		mTime++;
		return false;
	}
	
	public float getTime() {
		return mTime / (float)RIPPLE_DURATION;
	}

	public Vector2 getLocalPos() {
		return new Vector2(mLocalPos);
	}

	
	
	
	
	
}
