package com.kuter.bcw.map;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.kuter.bcw.WorldMan;
import com.kuter.bcw.objects.Platform;

public class Utils {
	
	
	public static LinkedList<Platform> buildPlatformLine(WorldMan wMan, Vector2 pos, float angle, int length) {
		LinkedList<Platform> platforms = new LinkedList<Platform>();
	
		pos = new Vector2(pos);
		pos.add(new Vector2(1, 0).rotate(angle).setLength(Platform.WIDTH_PADDED / 2));
		
		float angleRad = angle * MathUtils.degreesToRadians;
		for (int i = 0; i < length; i++) {
			platforms.add(
				new Platform(wMan,
						pos.x + (float)Math.cos(angleRad) * (i * Platform.WIDTH_PADDED),
						pos.y + (float)Math.sin(angleRad) * (i * Platform.WIDTH_PADDED),
						angle
				)	
			);
		}
		
		return platforms;
	}
	
}
