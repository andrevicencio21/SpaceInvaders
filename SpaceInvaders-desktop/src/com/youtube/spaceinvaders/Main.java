package com.youtube.spaceinvaders;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "SpaceInvaders";
		cfg.width = MainGame.WIDTH;
		cfg.height = MainGame.HEIGHT;
		
		new LwjglApplication(new MainGame(), cfg);
	}
}
