package com.kuter.bcw.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.kuter.bcw.MyGdxGame;
import com.kuter.bcw.Ripple;
import com.kuter.bcw.WorldMan;
import com.kuter.bcw.event.EventHandlerRegistrar;
import com.kuter.bcw.event.EventMan;
import com.kuter.bcw.script.BaseOp;
import com.kuter.bcw.script.MultiplexerOp;
import com.kuter.bcw.script.PlatformScriptBuilder;
import com.kuter.bcw.script.Scriptable;
import com.kuter.bcw.script.ScriptableGroup;

public class Platform implements Drawable, Scriptable<Platform>, ColorableObject, ContactInterface, BodylyObject {
	private static final Color BUTTON_COLOR = Color.ORANGE;
	private static final Color ANTI_COLOR = Color.BLACK;
	private static final Color EXIT_COLOR = Color.GREEN;
	private static final Color NONE_COLOR = Color.GRAY;
	private static final Color NORMAL_COLOR = Color.WHITE;
	private static final Color TRAMPOLINE_COLOR = Color.FIREBRICK;
	
	
	private static final String ON_PLAYER_TOUCH = "onPlayerTouch";
	
	private static final float RIPPLE_INIT_RADIUS = 0.3f;
	private static final float RIPPLE_MAX_RADIUS = 3;

	protected static final float SCALE = 1f / 48f;
	public static final float WIDTH = 3.8f;
	public static final float WIDTH_PADDED = WIDTH + 0.4f;
	public static final float HEIGHT = 0.8f;
	
	public enum TouchResponse {
		ADJUST, ADJUST_ANTI, WIN, NONE, JUMP_HIGH;
	}
	
	public class State {
		public Vector2 pos;
		public float angle;
		public Color color;
		
		
	}
	
	protected static void renderPlatform(ShapeRenderer shapeRenderer, Vector2 pos,  float angleRad, float width, Color color) {
		shapeRenderer.translate(pos.x, pos.y, 0);
		shapeRenderer.rotate(0, 0, 1, angleRad * MathUtils.radiansToDegrees);

		shapeRenderer.set(ShapeType.Filled);
		shapeRenderer.setColor(color);

		shapeRenderer.rect(-width / 2, -HEIGHT / 2, width, HEIGHT);
		shapeRenderer.circle(-width / 2, 0, HEIGHT / 2, 20);
		shapeRenderer.circle(width / 2, 0, HEIGHT / 2, 20);

		shapeRenderer.identity();
	}
	
	private float mWidth;
	private WorldMan mWorldManager;
	public Body body;	
	
	private Color mColor = Color.WHITE;
	private Ripple mRipple = null;
	private EventHandlerRegistrar mOnPlayerTouch = new EventHandlerRegistrar(ON_PLAYER_TOUCH);
	private MultiplexerOp mMultiplexer = new MultiplexerOp(false);

	public boolean mMakeRipples = true;
	
	public TouchResponse touchResponse = TouchResponse.ADJUST;
	
	public Platform(WorldMan worldMan, float x, float y) {
		this(worldMan, x, y, WIDTH, 0);
	}
	
	public Platform(WorldMan worldMan, float x, float y, float angle) {
		this(worldMan, x, y, WIDTH, angle);
	}
	
	public Platform(WorldMan worldMan, float x, float y, float width, float angle) {
		this.mWorldManager = worldMan;
		this.mWidth = width - HEIGHT;

		BodyDef def = new BodyDef();
		def.type = BodyType.KinematicBody;
		
		def.angle = angle * MathUtils.degreesToRadians;
		def.position.set(x, y);
		this.body = this.mWorldManager.world.createBody(def);

		PolygonShape rect = new PolygonShape();
		rect.setAsBox(this.mWidth / 2, 32 * SCALE / 2);
		this.body.createFixture(rect, 0.5f);

		CircleShape c1 = new CircleShape();
		c1.setRadius(HEIGHT / 2);
		c1.setPosition(new Vector2(this.mWidth / 2, 0));
		this.body.createFixture(c1, 0.5f);

		CircleShape c2 = new CircleShape();
		c2.setRadius(HEIGHT / 2);
		c2.setPosition(new Vector2(-this.mWidth / 2, 0));
		this.body.createFixture(c2, 0.5f);

		rect.dispose();
		c1.dispose();
		c2.dispose();

		this.body.setUserData(this);
		this.body.setGravityScale(0);
		this.body.setFixedRotation(true);
		
		mWorldManager.register(this);
	}

	
	
	@Override
	public void draw() {
		body.setLinearVelocity(0, 0);
		mMultiplexer.run();
		
		mWorldManager.game.rippleShader.begin();
		if (mRipple != null && !mRipple.update()) {
			mWorldManager.game.rippleShader.setUniformf("u_ripplePos",
					mRipple.getLocalPos().rotateRad(body.getAngle()).add(body.getPosition()));
			
			mWorldManager.game.rippleShader
				.setUniformf("u_rippleTime", mRipple.getTime());
			mWorldManager.game.rippleShader
				.setUniformf(MyGdxGame.U_RIPPLE_MAX_RADIUS, RIPPLE_MAX_RADIUS);
			mWorldManager.game.rippleShader
				.setUniformf(MyGdxGame.U_RIPPLE_INIT_RADIUS, RIPPLE_INIT_RADIUS);
			
		} else {
			mRipple = null;
			mWorldManager.game.rippleShader.setUniformf("u_ripplePos",
					new Vector2(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY));
			mWorldManager.game.rippleShader.setUniformf("u_rippleTime", 0);
		}
		

		mWorldManager.game.rippleShapeRenderer.setAutoShapeType(true);
		mWorldManager.game.rippleShapeRenderer.begin();
		renderPlatform(mWorldManager.game.rippleShapeRenderer, body.getPosition(), body.getAngle(),
				mWidth, mColor);
		mWorldManager.game.rippleShapeRenderer.end();
		
	}
	
	public Platform setAngle(float angle) {
		body.setTransform(body.getPosition(), angle * MathUtils.degreesToRadians);
		return this;
	}
	
	public boolean getMakeRipples() {
		return mMakeRipples;
	}

	public void setMakeRipples(boolean makeRipples) {
		this.mMakeRipples = makeRipples;
	}

	@Override
	public void addScript(BaseOp op) {
		mMultiplexer.addOp(op);
	}

	@Override
	public Platform on(EventMan man, String name, ScriptableEventHandler<Platform> handler) {
		Scriptable.ScriptableEventHandlerAdapter<Platform> adapt = 
				new Scriptable.ScriptableEventHandlerAdapter<Platform>(this, handler);
		man.on(name, adapt);	
		
		return this;
	}

	
	@Override
	public void removeAllScripts() {
		mMultiplexer.removeAll();
	}
		
	public PlatformScriptBuilder getScriptBuilder() {
		return new PlatformScriptBuilder(mWorldManager, this);
	}
	
	public Platform addToGroup(ScriptableGroup<Platform> group) {
		group.add(this);
		return this;
	}
	
	public Platform setTouchResponse(TouchResponse handler) {
		touchResponse = handler;
		return this;
	}
	
	public Platform onTouch(ScriptableEventHandler<Platform> handler) {
		Scriptable.ScriptableEventHandlerAdapter<Platform> adapt = 
				new Scriptable.ScriptableEventHandlerAdapter<Platform>(this, handler);
		
		mOnPlayerTouch.register(adapt);
		return this;
	}
	
	public Vector2 getLeftCorner() {
		return new Vector2(-WIDTH_PADDED / 2, 0).rotateRad(body.getAngle()).add(body.getPosition());
	}
	public Vector2 getRightCorner() {
		return new Vector2(WIDTH_PADDED / 2, 0).rotateRad(body.getAngle()).add(body.getPosition());
	}
	
	
	@Override
	public Color getColor() {
		return mColor;
	}

	@Override
	public void setColor(Color color) {
		mColor = color;
	}

	@Override
	public void onContact(Contact contact, Fixture a, Fixture b, Vector2 pos) {
		if (b.getBody().getUserData() instanceof Player) {
			mOnPlayerTouch.send();
			
			if (mMakeRipples) {
				this.mRipple = new Ripple(
					new Vector2(pos).sub(body.getPosition()).rotateRad(-body.getAngle())
				);
			}
		
		}
	}
	@Override
	public Body getBody() {
		return body;
	}
	
	public Platform setButton() {
		setColor(BUTTON_COLOR);
		setTouchResponse(TouchResponse.ADJUST);
		return this;
	}
	
	public Platform setNormal() {
		setColor(NORMAL_COLOR);
		setTouchResponse(TouchResponse.ADJUST);
		return this;
	}
	
	public Platform setAnti() {
		setColor(ANTI_COLOR);
		setTouchResponse(TouchResponse.ADJUST_ANTI);
		return this;
	}
	
	public Platform setExit() {
		setColor(EXIT_COLOR);
		setTouchResponse(TouchResponse.WIN);
		return this;
	}
	
	public Platform setNone() {
		setColor(NONE_COLOR);
		setTouchResponse(TouchResponse.NONE);
		return this;
	}
	
	public Platform setTrampoline() {
		setColor(TRAMPOLINE_COLOR);
		setTouchResponse(TouchResponse.JUMP_HIGH);
		return this;
	}
}
