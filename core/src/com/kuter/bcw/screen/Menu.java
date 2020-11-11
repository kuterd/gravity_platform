package com.kuter.bcw.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.kuter.bcw.GoopRenderer;
import com.kuter.bcw.MyGdxGame;
import com.kuter.bcw.Ripple;

public class Menu implements Screen {
	private static final float DOT_DISTANCE = 200;
	private static final float DOT_RADIUS = 2;
	private static final float GOOP_RADIUS = 70;
	private static final float GOOP_BUTTON_DISTANCE = 130;
	private static final int FAKE_LEVEL_COUNT = 100;
	private static final int FREE_LEVELS = 10;
	private static final int TIME = 1000;
	
	private class Goop {
		int time;
		Ripple ripple;
	
		public Goop() {
			time = (int)(Math.random() * 100);
		}
		
		public void update(boolean chosen) {
			
			time = (time + (chosen ? 5 : 1)) % TIME;
			if (ripple != null && ripple.update())
				ripple = null;
		}
	}
	
	private MyGdxGame mGame;
	private Goop mButtons[];
	private float mCurrentOffset;
	private float mScrollSpeed = 0;
	private int mTarget = 0;
	
	//Overrides target calculations
	private boolean mClicked = false;
	
	private boolean beingMoved = false;
	
	public Menu(MyGdxGame game) {
		mGame = game;
		mButtons = new Goop[FAKE_LEVEL_COUNT + 1];
		
		for(int i = 0; i < FAKE_LEVEL_COUNT + 1; i++) {
			mButtons[i] = new Goop();
		}
		
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(new InputProcessor() {
			float pDist;
			float lastPos = 0;
			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
				if(pDist < 10) {
					Vector2 result = mGame.bigViewport.unproject(new Vector2(screenX, screenY));
					
					int target = (int)Math.floor(
							(result.x - mCurrentOffset + GOOP_RADIUS)
							/ ((GOOP_RADIUS + GOOP_BUTTON_DISTANCE)));
					System.out.println(target);
					
					float goopX = target * (GOOP_RADIUS + GOOP_BUTTON_DISTANCE);
					float distanceX = goopX - (result.x + mCurrentOffset);
					
					if (target >= 0 && target < mButtons.length 
							&& (result.y < GOOP_RADIUS && result.y > -GOOP_RADIUS)) {
						mTarget = target;
						mClicked = true;
						
						mButtons[target].ripple = new Ripple(result.sub(goopX + mCurrentOffset, 0));
						
					}
					
					
				}
				beingMoved = false;
				return true;
			}
			
			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				float dist = (screenX - lastPos);
				pDist += Math.abs(dist);
				beingMoved = true;

				mCurrentOffset += dist;
				lastPos = screenX;
				
				return true;
			}
			
			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				pDist = 0;
				lastPos = screenX;
				return false;
			}
			
			@Override
			public boolean scrolled(int amount) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean mouseMoved(int screenX, int screenY) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean keyUp(int keycode) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean keyTyped(char character) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean keyDown(int keycode) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		
		
	}
	
	private int getLevelIdFromGoop(int i) {
		if (i > FREE_LEVELS) return i - 1;
		else if (i == FREE_LEVELS) return -1;
		else return i;
	}

	@Override
	public void render(float delta) {		
		Gdx.gl.glClearColor(0.05f, 0.05f, 0.5f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		mGame.shapeRenderer.setProjectionMatrix(mGame.bigViewport.getCamera().combined);
		mGame.shapeRenderer.begin(ShapeType.Filled);
		
		
		for (int x = 0; x < mGame.bigViewport.getWorldWidth() / DOT_DISTANCE + 1; x++) {
			for (int y = 0; y < mGame.bigViewport.getWorldHeight() / DOT_DISTANCE + 1; y++) {
				mGame.shapeRenderer.circle(
						x * DOT_DISTANCE - mGame.bigViewport.getWorldWidth() / 2
							+ (mCurrentOffset * 0.4f % DOT_DISTANCE),
						y * DOT_DISTANCE - mGame.bigViewport.getWorldHeight() / 2,
						DOT_RADIUS
				);
			}	
		}
		mGame.shapeRenderer.end();
		
		
		mGame.spriteBatchRenderer.setProjectionMatrix(mGame.bigViewport.getCamera().combined);
		mGame.goopRenderer.setProjectModelView(mGame.bigViewport.getCamera().combined);
		
		mGame.numberFont.setColor(Color.DARK_GRAY);
		
		int targetCan = (int)Math.round(-mCurrentOffset / (GOOP_RADIUS + GOOP_BUTTON_DISTANCE));
		targetCan = Math.min(targetCan, mButtons.length);
		targetCan = Math.max(targetCan, 0);
		
		if (!mClicked)
			mTarget = targetCan;
		
		if (mClicked && mTarget == targetCan)
			mClicked = false;
		
		if (!beingMoved)
			mCurrentOffset +=  (-(GOOP_RADIUS + GOOP_BUTTON_DISTANCE) * mTarget - mCurrentOffset) / 10;
		
		
		for (int i = 0; i < mButtons.length; i++) {
			Goop b = mButtons[i];
			
			b.update(false);
			
			float goopX = i * (GOOP_RADIUS + GOOP_BUTTON_DISTANCE) + mCurrentOffset;

			mGame.goopRenderer.drawGoop(b.ripple, Color.WHITE,
					new Vector2(goopX, 0), GOOP_RADIUS , ((float)b.time / TIME));
			int lv = getLevelIdFromGoop(i);
			String str = lv == -1 ? "$" : "" + (lv + 1);
			GlyphLayout ly = new GlyphLayout(mGame.numberFont, str);
			
			mGame.spriteBatchRenderer.begin();
			mGame.numberFont.draw(mGame.spriteBatchRenderer, str, goopX - ly.width / 2, ly.height / 2 + 5);	
			mGame.spriteBatchRenderer.end();	
		}
		
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
