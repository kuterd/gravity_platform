package com.kuter.bcw.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.kuter.bcw.MyGdxGame;
import com.kuter.bcw.Utils;
import com.kuter.bcw.WorldMan;
import com.kuter.bcw.objects.Player;

public class Play implements Screen {
	private static final Vector2 MAX_CAM_DIST = new Vector2(5f, 5f);

	public OrthographicCamera camera = new OrthographicCamera();
	public float angle = 0;
	
	public MyGdxGame game;
	private Box2DDebugRenderer mDebugRenderer;
	private WorldMan mWorldManager;

	private boolean mIsDebugRendererEnabled = false;

	public Play(MyGdxGame game, int id) {
		this.game = game;

		mDebugRenderer = new Box2DDebugRenderer();
		mWorldManager = new WorldMan(this, id);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(new InputProcessor() {

			@Override
			public boolean keyDown(int keycode) {
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
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				Vector2 touch = game.viewport.unproject(new Vector2(screenX, screenY));

				if (touch.x < 0) {
					mWorldManager.setPlayerMoveLeft(true);
				} else {
					mWorldManager.setPlayerMoveRight(true);
				}

				return true;
			}

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
				Vector2 touch = game.viewport.unproject(new Vector2(screenX, screenY));

				if (touch.x < 0) {
					mWorldManager.setPlayerMoveLeft(false);
				} else {
					mWorldManager.setPlayerMoveRight(false);
				}

				return true;
			}

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean mouseMoved(int screenX, int screenY) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean scrolled(int amount) {
				// TODO Auto-generated method stub
				return false;
			}

		});

	}

	@Override
	public void render(float delta) {
		Player player = mWorldManager.getPlayer();

		if (Gdx.input.isKeyJustPressed(Keys.L)) {
			mIsDebugRendererEnabled = !mIsDebugRendererEnabled;
		}
		if (Gdx.input.isKeyJustPressed(Keys.R)) {
			mWorldManager.reset();
		}

		float angleN = Utils.angleDist(angle, -mWorldManager.world.getGravity().angle() - 90) / 20;
		angle += angleN;
		camera.rotate(angleN);
		
		Vector2 camPlayerDist = player.body.getPosition().sub(camera.position.x, camera.position.y);
		if ((float)Math.abs(camPlayerDist.x) > MAX_CAM_DIST.x)
			camPlayerDist.x -= Math.signum(camPlayerDist.x) * MAX_CAM_DIST.x;
		else
			camPlayerDist.x *= 0.1f;

		if ((float)Math.abs(camPlayerDist.y) > MAX_CAM_DIST.y)
			camPlayerDist.y -= Math.signum(camPlayerDist.y) * MAX_CAM_DIST.y;
		else
			camPlayerDist.y *= 0.1f;	
		
		camera.translate(camPlayerDist);
		camera.update();

		mWorldManager.update();

		//Color c = Utils.setHsv(new Color(), 1 - (mGame.angle % 360) / 360, 0.8f, 1f);

		//Gdx.gl.glClearColor(0.75f, 0.75f, 0.75f, 1);
		Gdx.gl.glClearColor(0.03f, 0.03f, 0.2f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.rippleShader.begin();
		game.rippleShader.setUniformMatrix("u_inv_camera", new Matrix4(camera.combined).inv());
		
		game.shapeRenderer.setAutoShapeType(true);
		game.shapeRenderer.begin();
		game.shapeRenderer.setProjectionMatrix(camera.combined);

		mWorldManager.backgroundDraw();

		game.shapeRenderer.end();

		game.shapeRenderer.setAutoShapeType(true);
		game.shapeRenderer.begin();
		game.shapeRenderer.setProjectionMatrix(camera.combined);

		mWorldManager.shapeRendererDraw();

		game.shapeRenderer.end();

		game.rippleShapeRenderer.setProjectionMatrix(camera.combined);
		mWorldManager.drawableDraw();
		
		if (mIsDebugRendererEnabled)
			mDebugRenderer.render(mWorldManager.world, camera.combined);

	}

	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(false, game.viewport.getWorldWidth(), game.viewport.getWorldHeight());
		camera.position.set(game.viewport.getWorldWidth() / 2, game.viewport.getWorldHeight() / 2, 0);

		camera.update();
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
