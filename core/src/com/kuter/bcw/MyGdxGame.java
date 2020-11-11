package com.kuter.bcw;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.kuter.bcw.screen.Play;

/**
 * @author kuter
 */

public class MyGdxGame extends Game {
	private static final float W_ASP = 16;
	private static final float H_ASP = 7;
	private static final float SCALE = 5;
	private static final float BIG_W_SCALE = 48;
	public static final float WIDTH = W_ASP * SCALE;
	public static final float HEIGHT = H_ASP * SCALE;
	
	public static final String U_RIPPLE_POS = "u_ripplePos";
	public static final String U_RIPPLE_TIME = "u_rippleTime";
	public static final String U_RIPPLE_MAX_RADIUS = "u_rippleMRadius";
	public static final String U_RIPPLE_INIT_RADIUS = "u_rippleInitRadius";

	public static final String U_RIPPLE_INV_CAMERA = "u_inv_camera";
	
	public ExtendViewport viewport;
	public ExtendViewport bigViewport;

	
	public ShapeRenderer shapeRenderer;
	public ShapeRenderer rippleShapeRenderer;
	public ShaderProgram rippleShader;
	public GoopRenderer goopRenderer;
	public SpriteBatch spriteBatchRenderer;
	public BitmapFont numberFont; 
	
	@Override
	public void create() {
		viewport = new ExtendViewport(WIDTH, HEIGHT);
		bigViewport = new ExtendViewport(W_ASP * BIG_W_SCALE, W_ASP * BIG_W_SCALE);
		
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		shapeRenderer = new ShapeRenderer();
		
		rippleShader = new ShaderProgram(Gdx.files.internal("ripple.vert"), Gdx.files.internal("ripple.frag"));
		rippleShapeRenderer = new ShapeRenderer(5000, rippleShader);
		goopRenderer = new GoopRenderer(rippleShader);
		spriteBatchRenderer = new SpriteBatch();
		
		numberFont = new BitmapFont(Gdx.files.internal("numbers$.fnt"));
		
		setScreen(new Play(this, 1));
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		bigViewport.update(width, height);
		super.resize(width, height);
	}

	@Override
	public void dispose() {
		
	}
}
