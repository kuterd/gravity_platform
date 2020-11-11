package com.kuter.bcw.script;

public abstract class BaseOp {
	private boolean mIsDone = false;
	
	protected abstract boolean onRun();
	public boolean isDone() { return mIsDone; }
	public boolean run() {
		if(!mIsDone) mIsDone = onRun();
		return mIsDone;
	}	
}
