package com.kuter.bcw.script;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.kuter.bcw.Utils;

public class RotateOp extends BaseOp {	
	private float mSpeed;
	protected Body mBody;
	protected float mTarget;
	
	public RotateOp(Body body, float angle, float speed) {
		mBody = body;
		mTarget = angle;
		mSpeed = speed * 60;
	}
	
	
	@Override
	protected boolean onRun() {
		float dist = Utils.angleDist(mBody.getAngle() * MathUtils.radiansToDegrees, mTarget) * 60;
		float aDist = Math.abs(dist);
		
		
		if (aDist > mSpeed) {
			if (dist < 0)
				dist = -mSpeed;
			else
				dist = mSpeed;
		}
		
		mBody.setAngularVelocity(dist * MathUtils.degreesToRadians);
		
		return aDist < 0.01;
	}

}
