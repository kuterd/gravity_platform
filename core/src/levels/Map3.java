package levels;

import com.badlogic.gdx.math.Vector2;
import com.kuter.bcw.WorldMan;
import com.kuter.bcw.map.Map;
import com.kuter.bcw.map.Utils;
import com.kuter.bcw.objects.Platform;
import com.kuter.bcw.objects.Player;

public class Map3 implements Map {
	
	@Override
	public void build(WorldMan wMan) {
		new Player(wMan, Platform.WIDTH_PADDED / 2, 3);
		Utils.buildPlatformLine(wMan, new Vector2(), 0, 4);
		Utils.buildPlatformLine(wMan, new Vector2(0, Platform.WIDTH_PADDED * 2), 90, 2)
		.getLast().setExit();
		Utils.buildPlatformLine(wMan,
				new Vector2(Platform.WIDTH_PADDED * 5 / 2, Platform.WIDTH_PADDED / 2), 90, 2);
	
	}
	

}
