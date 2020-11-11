package com.kuter.bcw.event;

import java.util.ArrayList;

public class EventHandlerRegistrar {
	private String mName;
	public ArrayList<EventHandler> events = new ArrayList<EventHandler>(); 
	
	public EventHandlerRegistrar(String name) {
		mName = name;
	}
	
	public void register(EventHandler handler) {
		events.add(handler);
	}
	
	public void remove(EventHandler handler) {
		events.remove(handler);
	}
	
	public void send() {
		for (EventHandler e : events) {
			e.onEvent(mName);
		}
	}
}
