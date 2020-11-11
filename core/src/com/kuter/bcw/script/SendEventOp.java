package com.kuter.bcw.script;

import com.kuter.bcw.event.EventMan;

public class SendEventOp extends BaseOp {
	private EventMan mEvMan;
	private String mName;
	
	public SendEventOp(EventMan evMan, String name) {
		mEvMan = evMan;
		mName = name;
	}
	
	@Override
	protected boolean onRun() {
		mEvMan.send(mName);
		return true;
	}

}
