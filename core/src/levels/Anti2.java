package levels;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.kuter.bcw.WorldMan;
import com.kuter.bcw.map.Map;
import com.kuter.bcw.map.Utils;
import com.kuter.bcw.objects.Platform;
import com.kuter.bcw.objects.Player;

public class Anti2 implements Map {

	@Override
	public void build(WorldMan wMan) {
		new Player(wMan, 0, 3);
		Vector2 right = Utils.buildPlatformLine(wMan, new Vector2(), 0, 3).getLast().getRightCorner();
		new Platform(wMan, right.x, 
				right.y + Platform.WIDTH_PADDED / 2, 90).setAnti();		
		Utils.buildPlatformLine(wMan, new Vector2(-20, 0), 90, 3)
				.getLast().setExit();	
	}

}
