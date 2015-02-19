package com.youtube.spaceinvaders.entity;

import com.badlogic.gdx.math.Vector2;
import com.youtube.spaceinvaders.MainGame;
import com.youtube.spaceinvaders.TextureManager;

public class Missile extends Entity {

	public Missile(Vector2 pos) {
		super(TextureManager.MISSILE, pos, new Vector2(0, 5));
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() {
		pos.add(direction);

	}

	public boolean checkEnd() {
		return pos.y >= MainGame.HEIGHT;
	}

}
