package levels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.kuter.bcw.WorldMan;
import com.kuter.bcw.map.Map;
import com.kuter.bcw.objects.Platform;
import com.kuter.bcw.objects.Platform.TouchResponse;
import com.kuter.bcw.objects.Player;
import com.kuter.bcw.script.BaseOp;
import com.kuter.bcw.script.ColorOp;
import com.kuter.bcw.script.MoveByOp;
import com.kuter.bcw.script.MoveOp;
import com.kuter.bcw.script.MultiplexerOp;
import com.kuter.bcw.script.PlatformScriptBuilder;
import com.kuter.bcw.script.RotateOp;
import com.kuter.bcw.script.ScriptOp;
import com.kuter.bcw.script.Scriptable.ScriptableEventHandler;
import com.kuter.bcw.script.SendEventOp;
import com.kuter.bcw.script.WaitOp;

public class Map1 implements Map {	
	public static final int SIDE_L = 4;
	@Override
	public void build(final WorldMan wMan) {
		new Player(wMan, 0, 3);
		
		for (int i =  0; i < SIDE_L; i++) {
			new Platform(wMan, i * Platform.WIDTH_PADDED, 0);
		}
		
		for(int i = 0; i < SIDE_L; i++) {
			Platform p = new Platform(wMan, 
					(SIDE_L - 0.5f) * Platform.WIDTH_PADDED,
					(i + 0.5f)* Platform.WIDTH_PADDED, 90);		
			if (i == SIDE_L - 1) {
				p.setExit();
			}
		}	
		
		
		
		
	}

}
