package com.kuter.bcw.script;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class MoveOp extends BaseOp {
	protected Body mBody;
	protected Vector2 mTargetPos;
	private float mSpeed;
	
	public MoveOp(Body body, Vector2 targetPos, float speed) {
		mBody = body;
		mTargetPos = targetPos;
		mSpeed = speed * 60;
	}
	
	@Override
	protected boolean onRun() {
		Vector2 dist = new Vector2(mTargetPos).sub(mBody.getPosition()).scl(60);
		float len = dist.len();
		
		if (len > mSpeed) {
			dist.setLength(mSpeed);
		}
		
		mBody.setLinearVelocity(new Vector2(dist));
		
		return len < 0.01;
	}
	
}
