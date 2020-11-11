package levels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.kuter.bcw.WorldMan;
import com.kuter.bcw.map.Map;
import com.kuter.bcw.map.Utils;
import com.kuter.bcw.objects.Platform;
import com.kuter.bcw.objects.Platform.TouchResponse;
import com.kuter.bcw.objects.Player;

public class Mapx implements Map {

	@Override
	public void build(WorldMan wMan) {
		new Player(wMan, Platform.WIDTH_PADDED / 2, 3);

		Utils.buildPlatformLine(wMan, new Vector2(), 90, 2)
		.getLast().setAnti();
		
		Vector2 last = Utils.buildPlatformLine(wMan, new Vector2(), 0, 4)
				.getLast().getRightCorner();
		
		Platform pp = null;
		for (Platform p : Utils.buildPlatformLine(wMan, last, 90, 4)) {
			p.setNone();
			pp = p;
		}
		
		pp.setExit();
		
	}

}
