package com.kuter.bcw.script;

import com.kuter.bcw.event.EventHandler;
import com.kuter.bcw.event.EventMan;

public interface Scriptable<T extends Scriptable<?>> {
	interface ScriptableEventHandler<T extends Scriptable<?>> {
		void onEvent(String name, T object);
	}
	
	class ScriptableEventHandlerAdapter<T extends Scriptable<?>> implements EventHandler {
		private ScriptableEventHandler<T> mHandler;
		private T mObj;
		public ScriptableEventHandlerAdapter(T obj, ScriptableEventHandler<T> handler) {
			mHandler = handler;
			mObj = obj;
		}
		
		@Override
		public void onEvent(String name) {
			mHandler.onEvent(name, mObj);
		}
	}
		
	void addScript(BaseOp op);
	T on(EventMan man, String name, ScriptableEventHandler<T> handler);
	void removeAllScripts();
}
