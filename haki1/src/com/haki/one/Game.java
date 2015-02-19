package com.haki.one;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.haki.one.handlers.GameStateManager;

public class Game implements ApplicationListener {
	private OrthographicCamera camera;
	private OrthographicCamera hudCamera;
	private SpriteBatch batch;
	public static final String TITLE = "Haki One";
	public static final int V_WIDTH = 320, V_HEIGHT = 240;
	public static final int SCALE = 2;
	private GameStateManager gsm;
	public static final float STEP = 1 / 60f;
	private float accum;

	


	public void create() {
		
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, V_WIDTH, V_HEIGHT);
		hudCamera = new OrthographicCamera();
		hudCamera.setToOrtho(false, V_WIDTH, V_HEIGHT);
		batch = new SpriteBatch();
		gsm = new GameStateManager(this);
		

	
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void render() {
		accum += Gdx.graphics.getDeltaTime();
		while(accum >= STEP){
			accum -= STEP;
			gsm.update(STEP);
			gsm.render();
		}
	
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
	
	public SpriteBatch getSpriteBatch() {
		return batch;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public OrthographicCamera getHudCamera() {
		return hudCamera;
	}
}
