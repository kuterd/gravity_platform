package com.kuter.bcw.map;

import com.kuter.bcw.WorldMan;

public interface Map {
	public abstract class Factory {
		public void createAndBuild(WorldMan wMan) {
			create().build(wMan);
		}
		
		public abstract Map create();
	}
	void build(WorldMan wMan);
}
