package com.youtube.spaceinvaders.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.youtube.spaceinvaders.camera.OrthoCamera;
import com.youtube.spaceinvaders.entity.EntityManager;


public class GameScreen extends Screen {
	private OrthoCamera camera;
	private EntityManager entityManager;

	@Override
	public void create() {
		camera = new OrthoCamera();
		entityManager = new EntityManager(10, camera);
		
	}

	public void update() {
		camera.update();
		entityManager.update();
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(camera.combined);
		sb.begin();
		entityManager.render(sb);
		sb.end();

	}

	@Override
	public void resize(int width, int height) {
		camera.resize();
	}

	@Override
	public void dispose() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

}
