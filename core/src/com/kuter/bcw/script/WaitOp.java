package com.kuter.bcw.script;

public class WaitOp extends BaseOp {
	protected int mCurrentTime = 0;
	protected int mWillWait;
	
	
	public WaitOp(int willWait) {
		mWillWait = willWait;
	}
	
	@Override
	protected boolean onRun() {
		mCurrentTime++;
		return mCurrentTime >= mWillWait;
	}
	
}
