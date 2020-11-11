package com.kuter.bcw.script;

public class ScriptWatcher {
	public interface ScriptWatcherListner {
		void onAllFinished();
	}
	
	public class ScriptWatcherOp extends BaseOp{
		@Override
		protected boolean onRun() {
			unfinishedObjectCount--;
			
			if (unfinishedObjectCount == 0 && scriptWatcherListner != null) 
				scriptWatcherListner.onAllFinished();
			
			return true;
		}
	}
	
	private ScriptWatcherListner scriptWatcherListner;
	private int unfinishedObjectCount = 0;
	
	
	public ScriptWatcherOp getOp() {
		unfinishedObjectCount++;
		return new ScriptWatcherOp();
	}

}
