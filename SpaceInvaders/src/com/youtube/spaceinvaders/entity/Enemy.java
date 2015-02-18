package com.youtube.spaceinvaders.entity;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.youtube.spaceinvaders.MainGame;
import com.youtube.spaceinvaders.TextureManager;

public class Enemy extends Entity {

	public Enemy(Vector2 pos, Vector2 direction) {
		super(TextureManager.ENEMY, pos, direction);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() {
		pos.add(direction);
		if(pos.y <= -TextureManager.ENEMY.getHeight()){
			float x = MathUtils.random(0, MainGame.WIDTH - TextureManager.ENEMY.getWidth());
			pos.set(x, MainGame.HEIGHT);
		}
		
	}

}
