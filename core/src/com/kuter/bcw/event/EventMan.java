package com.kuter.bcw.event;

import java.util.HashMap;

public class EventMan {
	private HashMap<String, EventHandlerRegistrar> mRegistrars = new HashMap<String, EventHandlerRegistrar>();
	
	public void on(String name, EventHandler event) {
		EventHandlerRegistrar registrar = mRegistrars.get(name);
		if(registrar == null) {
			registrar = new EventHandlerRegistrar(name);
			mRegistrars.put(name, registrar);	
		}
		registrar.register(event);
	}
	
	public boolean exists(String name) {
		return mRegistrars.get(name) != null;
	}
	
	public void send(String name) {
		EventHandlerRegistrar registrar = mRegistrars.get(name); 
		if(registrar != null) {
			registrar.send();
		}
	}
}
