package com.kuter.bcw.script;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.kuter.bcw.WorldMan;
import com.kuter.bcw.objects.Platform;

public class PlatformScriptBuilder extends ScriptBuilder<PlatformScriptBuilder> {
	private WorldMan mWorldMan;
	private Platform mPlatform;
	
	public PlatformScriptBuilder(WorldMan worldManager, Platform platform) {
		mWorldMan = worldManager;
		mPlatform = platform;
	}
	
	public PlatformScriptBuilder goTo(Vector2 pos, float speed) {
		pushOp(new MoveOp(mPlatform.getBody(), pos, speed));
		return this;
	}
	
	public PlatformScriptBuilder goBy(Vector2 pos, float speed) {
		pushOp(new MoveByOp(mPlatform.getBody(), pos, speed));
		return this;
	}
	
	public PlatformScriptBuilder rotateTo(float angle, float speed) {
		pushOp(new RotateOp(mPlatform.body, angle, speed));
		return this;
	}
	
	public PlatformScriptBuilder rotateAroundBy(Vector2 around, float angle, float speed) {
		pushOp(new RotateAraundByOp(mPlatform.body, around, angle, speed));
		return this;
	}
	public PlatformScriptBuilder setColor(Color color, int tick) {
		pushOp(new ColorOp(mPlatform, color, tick));
		return this;
	}
	
	public PlatformScriptBuilder sendEvent(String name) {
		pushOp(new SendEventOp(mWorldMan.getEventMan(), name));
		return this;
	}
}
