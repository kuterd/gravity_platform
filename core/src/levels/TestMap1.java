package levels;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.kuter.bcw.WorldMan;
import com.kuter.bcw.map.Map;
import com.kuter.bcw.objects.Platform;
import com.kuter.bcw.objects.Player;
import com.kuter.bcw.script.BaseOp;
import com.kuter.bcw.script.ScriptOp;
import com.kuter.bcw.script.Scriptable;

public class TestMap1 implements Map {
	private static final float RADIUS = 10;
	private static final int PIECES = 9;
	
	@Override
	public void build(WorldMan wMan) {
		new Player(wMan, 0, 3);
		
		for (int i = 0; i < PIECES; i++) {
			float angle = 180f / PIECES * i + 180,
					angleRad = angle * MathUtils.degreesToRadians;
			Platform p = new Platform(wMan,
				(float)Math.cos(angleRad) * RADIUS,
				(float)Math.sin(angleRad) * RADIUS,
				angle + 90
			);
			p.on(wMan.getEventMan(), "init", new Scriptable.ScriptableEventHandler<Platform>() {
				@Override
				public void onEvent(String name, Platform object) {
					object.addScript(
							object.
							getScriptBuilder()
							.rotateAroundBy(new Vector2(), 360, 7)
							.done()
					);
				}
			});
		}
		wMan.getEventMan().send("init");
	}

}
