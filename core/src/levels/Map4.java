package levels;

import com.badlogic.gdx.math.MathUtils;
import com.kuter.bcw.WorldMan;
import com.kuter.bcw.map.Map;
import com.kuter.bcw.objects.Platform;
import com.kuter.bcw.objects.Player;

public class Map4 implements Map {

	private static final float RADIUS = 10;
	private static final int PIECES = 9;
	@Override
	public void build(WorldMan wMan) {
		new Player(wMan, 0, -6);
		for (int i = 0; i < PIECES; i++) {
			float angle = 180f / (PIECES - 1) * i + 90,
					angleRad = angle * MathUtils.degreesToRadians;
			Platform p = new Platform(wMan,
				(float)Math.cos(angleRad) * RADIUS,
				(float)Math.sin(angleRad) * RADIUS,
				angle + 90
			);
			
			if (i == 0)
				p.setTrampoline();
		}
		
		new Platform(wMan, Platform.WIDTH_PADDED / 2,0, 90).setExit();
	}

}
