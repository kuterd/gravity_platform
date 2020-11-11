package com.kuter.bcw.script;

import java.util.ArrayList;
import java.util.Arrays;

public class MultiplexerOp extends BaseOp {
	private boolean shouldEnd;
	private ArrayList<BaseOp> mOps = new ArrayList<BaseOp>();

	public MultiplexerOp(BaseOp[] ops) {
		mOps.addAll(Arrays.asList(ops));
		shouldEnd = true;
	}
	
	//To be used by event based script runners
	public MultiplexerOp(boolean shouldEnd) {
		this.shouldEnd = shouldEnd;
	}
	
	public void addOp(BaseOp op) {
		mOps.add(op);
	}

	public void removeAll() {
		mOps.clear();
	}
	
	@Override
	protected boolean onRun() {
		for (int i = mOps.size() - 1; i >= 0; i--) {
			BaseOp op = mOps.get(i);
			if (op.run())
				mOps.remove(op);
		}
		
		return shouldEnd && mOps.size() == 0;
	}
	


}
