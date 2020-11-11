package com.kuter.bcw.script;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;

public class RotateByOp extends RotateOp {
	private boolean mIsFirst = true;
	private float mRotateBy;
	
	public RotateByOp(Body body, float rotateBy, float speed) {
		super(body, 0, speed);
		mRotateBy = rotateBy;
	}

	@Override
	protected boolean onRun() {
		if (mIsFirst) {
			mTarget = mBody.getAngle() * MathUtils.radiansToDegrees + mRotateBy;
		}
		mIsFirst = false;
		
		return super.onRun();
	}
	
	

}
