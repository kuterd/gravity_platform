package com.kuter.bcw.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.kuter.bcw.WorldMan;
import com.kuter.bcw.objects.Platform.TouchResponse;

public class Player implements ContactInterface, ShapeRendererDrawable, BodylyObject {
	private static final double MAX_FLYTIME = 150;
	private static final float SPEED = 2f;
	private static final float FRICTION = 0.2f;
	private static final float RADIUS = 0.4f;

	private static final float JUMP_SPEED = 10;
	private static final float HIGH_JUMP_SPEED = 17;

	public WorldMan wm;
	public Body body;

	private Platform mLastPlatform = null;

	private double mFlyTime = 0;
	private Vector2 relativeSpeed = new Vector2();

	public Player(WorldMan wm, float x, float y) {
		this.wm = wm;

		BodyDef def = new BodyDef();
		def.type = BodyDef.BodyType.DynamicBody;
		def.position.set(new Vector2(x, y));
		def.fixedRotation = true;

		body = wm.world.createBody(def);

		CircleShape shape = new CircleShape();
		shape.setRadius(RADIUS);

		body.createFixture(shape, 0.8f);
		body.setUserData(this);
		
		wm.register(this);
		wm.setPlayer(this);
	}

	public void shapeRendererDraw() {
		wm.game.shapeRenderer.set(ShapeType.Filled);
		wm.game.shapeRenderer.setColor(Color.WHITE);
		wm.game.shapeRenderer.circle(body.getPosition().x, body.getPosition().y, RADIUS, 20);

		if ((mFlyTime++) > MAX_FLYTIME) {
			wm.reset();
		}
		relativeSpeed.x *= 1 - FRICTION;
		relativeSpeed.y += WorldMan.GRAVITY / 40;

		// Calculate and apply real speed based on relative speed
		body.setLinearVelocity(
				wm.getGravityDir().scl(relativeSpeed.y).add(wm.getGravityDir().rotate90(1).scl(relativeSpeed.x)));
	}

	public void move(boolean isRight) {
		relativeSpeed.x += SPEED * (isRight ? 1 : -1);
	}
	
	
	
	@Override
	public void onContact(Contact contact, Fixture a, Fixture b, Vector2 pos) {
		if (b.getBody().getUserData() instanceof Platform) {
			
			mFlyTime = 0;
			Platform platform = (Platform) b.getBody().getUserData();
			
			if (platform.touchResponse == TouchResponse.WIN)
				wm.next();
				
			
			Vector2 gravityNewDir = new Vector2(0, 1).rotateRad(b.getBody().getAngle());
			Vector2 len = b.getBody().getPosition().sub(a.getBody().getPosition()).scl(gravityNewDir);
			
			if (len.x + len.y < 0)
				gravityNewDir.scl(-1);
			if (platform.touchResponse == TouchResponse.ADJUST_ANTI)
				gravityNewDir.scl(-1);
			
			if (!wm.getGravityDir().epsilonEquals(gravityNewDir, 0.1f) &&
					platform.touchResponse == TouchResponse.NONE)
				return;
			
			//Don't allow the player to jump the opposite side of the platform.
			if (platform != mLastPlatform)
				wm.setGravityDir(gravityNewDir);
			mLastPlatform = platform;
			
			relativeSpeed.y = platform.touchResponse == TouchResponse.JUMP_HIGH ?
					-HIGH_JUMP_SPEED : -JUMP_SPEED;
			
			// To make anti platform transition more smooth.
			if (platform.touchResponse == TouchResponse.ADJUST_ANTI)
				relativeSpeed.y = -relativeSpeed.y;
			
		}
	}
	
	@Override
	public Body getBody() {
		return body;
	}

}
