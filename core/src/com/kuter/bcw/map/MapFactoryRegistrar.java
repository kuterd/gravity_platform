package com.kuter.bcw.map;

import java.util.ArrayList;

import com.kuter.bcw.WorldMan;
import com.kuter.bcw.map.Map;

import levels.Anti2;
import levels.AntiIntroduction;
import levels.Map1;
import levels.Map2;
import levels.Map3;
import levels.Map4;
import levels.Mapx;
import levels.TestMap1;
import levels.TestMap2;
import levels.Mapxxx;

public class MapFactoryRegistrar {
	private static MapFactoryRegistrar instance;
	public static MapFactoryRegistrar getInstance() {
		if (instance == null) {
			instance = new MapFactoryRegistrar();
		}
		return instance;
	}
	
	ArrayList<Map.Factory> factorys = new ArrayList<Map.Factory>();

	public void loadMap(WorldMan wMan, int id) {
		factorys.get(id).createAndBuild(wMan);
	}
	
	public int getMapCount() {
		return factorys.size();
	}
	
	public MapFactoryRegistrar() {
		factorys.add(new Map.Factory() {
			@Override
			public Map create() {
				return new Map1();
			}
		});
	
		factorys.add(new Map.Factory() {
			@Override
			public Map create() {
				return new Map2();
			}
		});
	
		factorys.add(new Map.Factory() {
			@Override
			public Map create() {
				return new Map3();
			}
		});
		
		factorys.add(new Map.Factory() {
			@Override
			public Map create() {
				return new Map4();
			}
		});
		
		factorys.add(new Map.Factory() {
			@Override
			public Map create() {
				return new AntiIntroduction();
			}
		});
		
		factorys.add(new Map.Factory() {
			@Override
			public Map create() {
				return new Anti2();
			}
		});
		

		factorys.add(new Map.Factory() {
			@Override
			public Map create() {
				return new Mapx();
			}
		});
		
		factorys.add(new Map.Factory() {
			@Override
			public Map create() {
				return new Mapxxx();
			}
		});
		factorys.add(new Map.Factory() {
			@Override
			public Map create() {
				return new TestMap2();
			}
		});

	
	}


}
