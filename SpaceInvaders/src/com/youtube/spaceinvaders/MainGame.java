package com.youtube.spaceinvaders;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.youtube.spaceinvaders.camera.OrthoCamera;
import com.youtube.spaceinvaders.screen.GameScreen;
import com.youtube.spaceinvaders.screen.ScreenManager;

public class MainGame implements ApplicationListener {
	public static int WIDTH = 480, HEIGHT = 800;
	private SpriteBatch batch;
	private OrthoCamera camera;

	@Override
	public void create() {
		
		batch = new SpriteBatch();
		camera = new OrthoCamera();
		ScreenManager.setScreen(new GameScreen());

	}

	@Override
	public void dispose() {
		if (ScreenManager.getCurrentScreen() != null)
			ScreenManager.getCurrentScreen().dispose();
		batch.dispose();

	}

	@Override
	public void render() {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (ScreenManager.getCurrentScreen() != null)
			ScreenManager.getCurrentScreen().update();

		if (ScreenManager.getCurrentScreen() != null)
			ScreenManager.getCurrentScreen().render(batch);

	}

	@Override
	public void resize(int width, int height) {
		if (ScreenManager.getCurrentScreen() != null)
			ScreenManager.getCurrentScreen().resize((int)camera.viewportWidth, (int)camera.viewportHeight);
	}

	@Override
	public void pause() {
		if (ScreenManager.getCurrentScreen() != null)
			ScreenManager.getCurrentScreen().pause();
	}

	@Override
	public void resume() {
		if (ScreenManager.getCurrentScreen() != null)
			ScreenManager.getCurrentScreen().resume();
	}
}
