package com.youtube.spaceinvaders.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.youtube.spaceinvaders.MainGame;
import com.youtube.spaceinvaders.TextureManager;
import com.youtube.spaceinvaders.camera.OrthoCamera;

public class Player extends Entity {

	private final EntityManager entityManager;
	private long lastFire;
	private final OrthoCamera camera;

	public Player(Vector2 pos, Vector2 direction, EntityManager entityManager,
			OrthoCamera camera) {
		super(TextureManager.PLAYER, pos, direction);
		this.entityManager = entityManager;
		this.camera = camera;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() {
		pos.add(direction);
		int dir = 0;
		if (Gdx.input.isTouched()) {
			Vector2 touch = camera.unprojectCoordinates(Gdx.input.getX(),
					Gdx.input.getY());
			if (touch.x > MainGame.WIDTH / 2)
				dir = 1;
			else
				dir = 2;
		}
		if (Gdx.input.isKeyPressed(Keys.A) || dir == 2) // left
			setDirection(-300, 0);
		else if (Gdx.input.isKeyPressed(Keys.D) || dir == 1) // Right
			setDirection(300, 0);
		else
			setDirection(0, 0);

		if (Gdx.input.isKeyPressed(Keys.SPACE)) // Fire
		{
			if (System.currentTimeMillis() - lastFire >= 250) {
				entityManager.addEntities(new Missile(pos.cpy().add(25,
						TextureManager.PLAYER.getHeight())));
				lastFire = System.currentTimeMillis();
			}
		}
	}

}
