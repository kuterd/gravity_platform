package com.kuter.bcw.script;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class MoveByOp extends MoveOp {
	private Vector2 mMoveBy;
	public MoveByOp(Body body, Vector2 moveBy, float speed) {
		super(body, null, speed);
		mMoveBy = moveBy;
	}
	
	@Override
	protected boolean onRun() {
		if(mTargetPos == null) {
			mTargetPos = new Vector2(mBody.getPosition()).add(mMoveBy);
		}
		return super.onRun();
	}

}
