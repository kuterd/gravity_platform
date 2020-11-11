package com.kuter.bcw;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

public class GoopRenderer {
	private static final float EFFECT = 0.04f;
	private static final float RIPPLE_INIT_RADIUS = 10;
	private static final float RIPPLE_MAX_RADIUS = 200;
	
	private Matrix4 mProjectModelView;
	private Matrix4 mInvProjectModelView;
	private ImmediateModeRenderer20 mRenderer;
	private ShaderProgram mProgram;
	public GoopRenderer(ShaderProgram program) {
		mRenderer = new ImmediateModeRenderer20(1000, false, true, 0, program);
		mProjectModelView = new Matrix4().idt();
		mProgram = program;
	}
	
	private void vector(Vector2 pos) {
		mRenderer.vertex(pos.x, pos.y, -10.11f);	
	}
	
	
	
	public void setProjectModelView(Matrix4 mProjectModelView) {
		this.mProjectModelView = mProjectModelView;
		this.mInvProjectModelView = new Matrix4(mProjectModelView).inv();
	}
	
	
	public float getRadius(float radius, float time, float seg) {
		float rotation = (float) (Math.cos(time * Math.PI * 2));
		
		float pass1 = (float)Math.cos(seg * Math.PI * 20 + rotation * Math.PI)
				* (float)Math.cos(time * Math.PI * 10);
		float pass2 = (float)Math.cos(seg * Math.PI * 10 + (1 - rotation) * Math.PI);
		float pass3 = (float)Math.cos(seg * Math.PI * 8 + (rotation) * Math.PI * 8);
		return radius * (1 - EFFECT) + (pass2 + pass1 + pass3) / 3 * radius * EFFECT;
		//
	}

	public void drawGoop(Ripple mRipple, Color color, Vector2 pos, float radius,  float time) {
		mProgram.begin();
		
		mProgram.setUniformMatrix(MyGdxGame.U_RIPPLE_INV_CAMERA, mInvProjectModelView);
		mProgram.setUniformf(MyGdxGame.U_RIPPLE_MAX_RADIUS, RIPPLE_MAX_RADIUS);
		mProgram.setUniformf(MyGdxGame.U_RIPPLE_INIT_RADIUS, RIPPLE_INIT_RADIUS);
		
		
		if (mRipple != null) {	
			mProgram.setUniformf(MyGdxGame.U_RIPPLE_POS, new Vector2(mRipple.getLocalPos()).add(pos));
			mProgram.setUniformf(MyGdxGame.U_RIPPLE_TIME, mRipple.getTime());
		} else {
			mProgram.setUniformf(MyGdxGame.U_RIPPLE_POS,
					new Vector2(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY));
			mProgram.setUniformf(MyGdxGame.U_RIPPLE_TIME, 0);
		}
		mRenderer.begin(mProjectModelView, GL20.GL_TRIANGLES);

		int segCount = 100;
		final float segRad = (float)((Math.PI * 2) / segCount);
		
		
		mRenderer.color(color);
		for (int i = 0; i < segCount; i++) {
			int bI = ((i - 1) + segCount) % segCount;
			vector(new Vector2(getRadius(radius, time, (float)bI / segCount), 0)
					.rotateRad(segRad * bI).add(pos));
			mRenderer.color(color);
			vector(new Vector2(getRadius(radius, time, (float)i / segCount), 0)
					.rotateRad(segRad * i).add(pos));
			mRenderer.color(color);
			vector(pos);
			mRenderer.color(color);
		}
		mRenderer.flush();
	}
}
