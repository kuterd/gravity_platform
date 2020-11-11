package levels;

import com.badlogic.gdx.math.Vector2;
import com.kuter.bcw.WorldMan;
import com.kuter.bcw.map.Map;
import com.kuter.bcw.map.Utils;
import com.kuter.bcw.objects.Platform;
import com.kuter.bcw.objects.Player;

public class Mapxxx implements Map {

	@Override
	public void build(WorldMan wMan) {
		new Player(wMan, 2, 3);
		
		new Platform(wMan, 0, Platform.WIDTH_PADDED / 2, 90).setAnti();
		Vector2 p = Utils.buildPlatformLine(wMan, new Vector2(), 0, 3).getLast().getRightCorner();
		
		
		new Platform(wMan, p.x, 
				p.y + Platform.WIDTH_PADDED / 2, 90).setNone();	
		
		Vector2 p2 = new Platform(wMan, p.x - Platform.WIDTH_PADDED / 2, 
				p.y + Platform.WIDTH_PADDED * 2).getLeftCorner();
		
		Vector2 p3 = new Platform(wMan,
				p2.x - Platform.WIDTH_PADDED / 2,
				p2.y + Platform.WIDTH_PADDED * 2).getRightCorner();
		
		new Platform(wMan, p3.x, p3.y - Platform.WIDTH_PADDED / 2, 90).setAnti();
		
		Utils.buildPlatformLine(wMan, new Vector2(-8, 0), 90, 4).getFirst().setExit();
		

	}

}
