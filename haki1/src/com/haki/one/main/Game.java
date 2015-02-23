package com.haki.one.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.haki.one.handlers.Content;
import com.haki.one.handlers.GameStateManager;
import com.haki.one.handlers.MyInput;
import com.haki.one.handlers.MyInputProcessor;

public class Game implements ApplicationListener {
	private OrthographicCamera camera;
	private OrthographicCamera hudCamera;
	private SpriteBatch batch;
	public static final String TITLE = "Haki One";
	public static final int V_WIDTH = 320, V_HEIGHT = 240;
	public static final int SCALE = 2;
	private GameStateManager gsm;
	public static final float STEP = 1 / 60f;
	public static Content res;

	public void create() {

		Gdx.input.setInputProcessor(new MyInputProcessor());
		res = new Content();

		// load bunny
		res.loadTexture("res/images/bunny.png", "bunny");
		// load crystal
		res.loadTexture("res/images/crystal.png", "crystal");
		// load hud
		res.loadTexture("res/images/hud.png", "hud");

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

		Gdx.graphics.setTitle(TITLE + "FPS: " + Gdx.graphics.getFramesPerSecond() );
		gsm.update(STEP);
		gsm.render();
		MyInput.update();
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
