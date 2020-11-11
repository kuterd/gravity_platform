package com.kuter.bcw.script;

import com.kuter.bcw.objects.Platform;

public class ChangeGravityHandler extends BaseOp {
	private Platform mPlatform;
	private Platform.TouchResponse mHandler;
	
	public ChangeGravityHandler(Platform platform, Platform.TouchResponse handler) {
		mPlatform = platform;
		mHandler = handler;
	}
	
	@Override
	protected boolean onRun() {
		mPlatform.touchResponse = mHandler;
		return true;
	}

}
