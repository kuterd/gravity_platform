package com.kuter.bcw.script;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class RotateAraundByOp extends BaseOp {
	private Body mBody;
	private float mTarget;
	private float mRotated;
	private float mSpeed;
	private Vector2 mAraund;
	private Vector2 mTargetPos;
	
	public RotateAraundByOp(Body body, Vector2 araund, float angle, float speed) {
		mBody = body;
		mAraund = araund;
		mTarget = angle;
		mSpeed = speed;
		mTargetPos = new Vector2(mBody.getPosition());
	}
	
	@Override
	protected boolean onRun() {
		mSpeed = Math.min(mSpeed, Math.abs(mTarget) - Math.abs(mRotated));
		float spd = mTarget < 0 ? -mSpeed : mSpeed;
		mRotated += spd;
		mTargetPos.sub(mAraund).rotate(spd).add(mAraund);
		
		Vector2 speed = new Vector2(mTargetPos).sub(mBody.getPosition()).scl(60);
		if(speed.len() > 120) {
			mBody.setTransform(mTargetPos, spd * MathUtils.degreesToRadians + mBody.getAngle());
		} else {
			mBody.setLinearVelocity(speed);
			mBody.setAngularVelocity(spd * 60 * MathUtils.degreesToRadians);
		}
		
		
		return mSpeed < 0.01f;
	}

}
