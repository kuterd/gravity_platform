package levels;

import com.badlogic.gdx.math.Vector2;
import com.kuter.bcw.WorldMan;
import com.kuter.bcw.map.Map;
import com.kuter.bcw.map.Utils;
import com.kuter.bcw.objects.Player;

public class AntiIntroduction implements Map {

	@Override
	public void build(WorldMan wMan) {
		new Player(wMan, 0, 3);
		Utils.buildPlatformLine(wMan, new Vector2(), 0, 4)
				.getLast().setAnti();

		Utils.buildPlatformLine(wMan, new Vector2(0, 13), 0, 4)
				.getFirst().setExit();	
	}

}
