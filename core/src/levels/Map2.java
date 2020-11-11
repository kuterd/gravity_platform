package levels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.kuter.bcw.WorldMan;
import com.kuter.bcw.map.Map;
import com.kuter.bcw.map.Utils;
import com.kuter.bcw.objects.Platform;
import com.kuter.bcw.objects.Player;
import com.kuter.bcw.objects.Platform.TouchResponse;

public class Map2 implements Map {
	@Override
	public void build(final WorldMan wMan) {
		new Player(wMan, 0, 3);
		
		Vector2 last = Utils.buildPlatformLine(wMan, new Vector2(), 0, 4)
				.getLast().getRightCorner();
		last = Utils.buildPlatformLine(wMan, last, 130, 4)
				.getLast().getRightCorner();
		Utils.buildPlatformLine(wMan, last, 180, 4).getLast().setExit();
	}

}
