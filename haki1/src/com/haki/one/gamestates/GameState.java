package com.haki.one.gamestates;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.haki.one.Game;
import com.haki.one.handlers.GameStateManager;

public abstract class GameState {
	protected GameStateManager gsm;
	protected Game game;
	protected SpriteBatch batch;
	protected OrthographicCamera camera;
	protected OrthographicCamera hudCamera;
	
	protected GameState(GameStateManager gsm){
		this.gsm = gsm;
		game = gsm.getGame();
		batch = game.getSpriteBatch();
		camera = game.getCamera();
		hudCamera = game.getHudCamera();
	}
	public abstract void handleInput();
	public abstract void update(float dt);
	public abstract void render();
	public abstract void dispose();
	
	
}
