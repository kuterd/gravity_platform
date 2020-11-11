package com.kuter.bcw.script;

import com.badlogic.gdx.graphics.Color;
import com.kuter.bcw.objects.ColorableObject;

public class ColorOp extends WaitOp {
	private ColorableObject mObject;
	private Color mTarget;
	private Color mOriginalColor;
	
	public ColorOp(ColorableObject object, Color target, int tick) {
		super(tick);
		mObject = object;
		mTarget = target;
		
		mOriginalColor = new Color(object.getColor());
	}
	
	@Override
	protected boolean onRun() {
		boolean result = super.onRun();
		mObject.setColor(mOriginalColor.lerp(mTarget, (float)(this.mCurrentTime) / mWillWait));
		return result;
	}

}
