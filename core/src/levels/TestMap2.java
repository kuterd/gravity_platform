package levels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.kuter.bcw.WorldMan;
import com.kuter.bcw.map.Map;
import com.kuter.bcw.objects.Platform;
import com.kuter.bcw.objects.Player;
import com.kuter.bcw.objects.Platform.TouchResponse;
import com.kuter.bcw.script.ColorOp;
import com.kuter.bcw.script.MoveOp;
import com.kuter.bcw.script.PlatformScriptBuilder;
import com.kuter.bcw.script.RotateAraundByOp;
import com.kuter.bcw.script.RotateByOp;
import com.kuter.bcw.script.Scriptable;
import com.kuter.bcw.script.Scriptable.ScriptableEventHandler;
import com.kuter.bcw.script.ScriptableGroup;
import com.kuter.bcw.script.ScriptableGroup.GroupEventHandler;

public class TestMap2 implements Map {
	private boolean rotate1;
	@Override
	public void build(final WorldMan wMan) {
		rotate1 = false;
		wMan.setPlayer(new Player(wMan, 0, 3));
		
		final ScriptableGroup<Platform> group1 = new ScriptableGroup<Platform>();
		group1.onForAll(wMan.getEventMan(), "rotate1", new GroupEventHandler<Platform>() {
			@Override
			public void onEvent(String name, Platform object, int i) {
				object.removeAllScripts();
				object.addScript(new RotateAraundByOp(object.body, 
						group1.get(0).getLeftCorner(), 90 * (rotate1 ? -1 : 1), 4));
			}
		});

		final ScriptableGroup<Platform> group2 = new ScriptableGroup<Platform>();
		group2.onForAll(wMan.getEventMan(), "rotate2", new GroupEventHandler<Platform>() {
			@Override
			public void onEvent(String name, Platform object, int i) {
				i++;
				object.addScript(new RotateAraundByOp(object.body,
						group2.get(0).getLeftCorner(), 45, 2));
			}
		});
		
		final ScriptableGroup<Platform> group3 = new ScriptableGroup<Platform>();
		group3.onForAll(wMan.getEventMan(), "rotate3", new GroupEventHandler<Platform>() {
			@Override
			public void onEvent(String name, Platform object, int i) {
				i++;
				object.addScript(new RotateAraundByOp(object.body,
						group3.get(0).getLeftCorner(), 45, 2f));
			}
		});
		
		
		new Platform(wMan, 0, 0);
		new Platform(wMan, Platform.WIDTH_PADDED, 0)
		.onTouch(new ScriptableEventHandler<Platform>() {
			@Override
			public void onEvent(String name, Platform object) {
				wMan.getEventMan().send("rotate1");
				rotate1 = !rotate1;
			}
		}).setColor(Color.ORANGE);
		
		for (int i = 0; i < 20; i++) {
			Platform p = new Platform(wMan, (i + 2) * Platform.WIDTH_PADDED, 0).addToGroup(group1);
			if (i > 5) p.addToGroup(group2);
			if (i > 10) p.addToGroup(group3);
			
			if(i == 5) {
				p.onTouch(new ScriptableEventHandler<Platform>() {
					@Override
					public void onEvent(String name, Platform object) {
						wMan.getEventMan().send("rotate2");
						
					}
					
				}).setColor(Color.ORANGE);
			}
			
			if(i == 10) {
				p.onTouch(new ScriptableEventHandler<Platform>() {
					@Override
					public void onEvent(String name, Platform object) {
						wMan.getEventMan().send("rotate3");
					}
					
				}).setColor(Color.ORANGE);
			}
			
		}
		
		
	
	}

}
