package com.kuter.bcw.script;

public class ScriptOp extends BaseOp {
	private BaseOp[] mOps;
	private int mCurrentOp;
	
	public ScriptOp(BaseOp[] ops) {
		mOps = ops;
	}
	
	@Override
	protected boolean onRun() {
		if (mOps[mCurrentOp].run()) {
			if (++mCurrentOp == mOps.length)
				return true;
		}

		return false;
	}
}
