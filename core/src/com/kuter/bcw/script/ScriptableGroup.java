package com.kuter.bcw.script;

import java.util.ArrayList;

import com.kuter.bcw.event.EventHandler;
import com.kuter.bcw.event.EventMan;

public class ScriptableGroup<T extends Scriptable<T>> {
	public interface GroupEventHandler<T extends Scriptable<T>> {
		void onEvent(String name, T member, int i);
	}
	
	private ArrayList<T> members = new ArrayList<T>();
	
	public void add(T obj) {
		members.add(obj);
	}
	
	public T get(int i) {
		return members.get(i);
	}
	
	public void onForAll(EventMan eMan, String name, final GroupEventHandler<T> handler) {
		eMan.on(name, new EventHandler() {
			@Override
			public void onEvent(String name) {
				int i = 0;
				for(T member : members) {
					handler.onEvent(name, member, i);
					i++;
				}
			}
			
		});
	}
	
}
