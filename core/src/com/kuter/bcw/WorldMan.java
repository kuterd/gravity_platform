package com.kuter.bcw;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.kuter.bcw.event.EventMan;
import com.kuter.bcw.map.MapFactoryRegistrar;
import com.kuter.bcw.objects.ContactInterface;
import com.kuter.bcw.objects.Drawable;
import com.kuter.bcw.objects.Player;
import com.kuter.bcw.objects.ShapeRendererDrawable;
import com.kuter.bcw.screen.Play;

public class WorldMan {
	public static final float GRAVITY = 11f;
		
	public World world;
	public Play play;
	public MyGdxGame game;

	private Player mPlayer;
	private Vector2 mGravityDir = new Vector2(0, -1);
	private EventMan mEventMan = new EventMan();
	private boolean mPlayerMoveRight = false, mPlayerMoveLeft = false;
	
	private ArrayList<Drawable> mDrawables = new ArrayList<Drawable>();
	private ArrayList<ShapeRendererDrawable> mShapeRendererDrawables = new ArrayList<ShapeRendererDrawable>();
	private int mCurrentContact = 0;
	
	private int mMapId;
	
	public WorldMan(Play play, int mapId) {
		world = new World(new Vector2(0, -GRAVITY), true);
		this.game = play.game;
		this.play = play;
		mMapId = mapId;
		
		world.setContactListener(new ContactListener() {
			@Override
			public void beginContact(Contact contact) {
				Vector2 points[] = contact.getWorldManifold().getPoints();
				Vector2 pos = new Vector2();
				if (mCurrentContact < points.length)
					pos = points[mCurrentContact++];
				
				if (contact.getFixtureA().getBody().getUserData() instanceof ContactInterface) {
					((ContactInterface) contact.getFixtureA().getBody().getUserData())
						.onContact(contact, contact.getFixtureA(), contact.getFixtureB(), pos);
				}

				if (contact.getFixtureB().getBody().getUserData() instanceof ContactInterface) {
					((ContactInterface) contact.getFixtureB().getBody().getUserData())
						.onContact(contact, contact.getFixtureB(), contact.getFixtureA(), pos);
				}
				
			}

			@Override
			public void endContact(Contact contact) {
			}

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {

			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
			}

		});

		MapFactoryRegistrar.getInstance().loadMap(this, mapId);
	}
	

	public void register(Object object) {
		if (object instanceof Drawable)
			mDrawables.add((Drawable)object);
		if (object instanceof ShapeRendererDrawable)
			mShapeRendererDrawables.add((ShapeRendererDrawable)object);
		
	}
	public EventMan getEventMan() {
		return mEventMan;
	}
	
	public void setPlayer(Player player) {
		mPlayer = player;
	}
	
	public void reset() {
		play.game.setScreen(new Play(play.game, mMapId));
	}
	
	public void next() {
		play.game.setScreen(new Play(play.game, mMapId + 1));
	}
	
	public void setPlayerMoveRight(boolean right) {
		mPlayerMoveRight = right;
	}

	public void setPlayerMoveLeft(boolean left) {
		mPlayerMoveLeft = left;
	}

	public Player getPlayer() {
		return this.mPlayer;
	}

	public void update() {
		if (Gdx.input.isKeyPressed(Keys.A) || mPlayerMoveLeft) {
			mPlayer.move(false);
		} else if (Gdx.input.isKeyPressed(Keys.D) || mPlayerMoveRight) {
			mPlayer.move(true);
		}
		
		mCurrentContact = 0;
		world.step(1f / 60, 1, 1);
	}

	public void setGravityDir(Vector2 dir) {
		mGravityDir = new Vector2(dir);
		world.setGravity(dir.scl(GRAVITY));
	}

	public Vector2 getGravityDir() {
		return new Vector2(mGravityDir);
	}
	
	public void backgroundDraw() {
		game.shapeRenderer.set(ShapeType.Filled);
		Color b = Utils.hsv(((play.angle % 360 + 360) % 360) / 360f, 0.9f, 1);
		for (int x = 0; x < 50; x++) {
			for (int y = 0; y < 50;  y++) {	
				float rX = x * 5f - 60,
					rY = y * 5f - 60;
				
				if (!play.camera.frustum.pointInFrustum(rX, rY, 0))
					continue;
					
				boolean isSp = x % 3 == 0 && y % 3 == 0;
				if (isSp)
					game.shapeRenderer.setColor(b);
				else
					game.shapeRenderer.setColor(Color.GRAY);
				
				game.shapeRenderer.circle(rX, rY, isSp ? 0.3f : 0.1f, 10);
			}
		}
	}

	
	public void shapeRendererDraw() {
		for(ShapeRendererDrawable dr : mShapeRendererDrawables) {
			dr.shapeRendererDraw();
		}
	}
	
	public void drawableDraw() {
		for(Drawable dr : mDrawables) {
			dr.draw();
		}
	}

}
