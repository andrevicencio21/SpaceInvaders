package com.haki.one.gamestates;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.haki.one.handlers.GameStateManager;

public class Play extends GameState {
	private BitmapFont font = new BitmapFont();

	public Play(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	public void handleInput() {

	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render() {

		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		// RENDER AREA
		font.draw(batch, "PLAY", 100, 100);

		batch.end();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
