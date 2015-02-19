package com.youtube.spaceinvaders.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.youtube.spaceinvaders.MainGame;
import com.youtube.spaceinvaders.TextureManager;
import com.youtube.spaceinvaders.camera.OrthoCamera;
import com.youtube.spaceinvaders.screen.GameOverScreen;
import com.youtube.spaceinvaders.screen.ScreenManager;

public class EntityManager {

	private final Array<Entity> entities = new Array<Entity>();
	private final Player player;

	public EntityManager(int amount, OrthoCamera camera) {
		player = new Player(new Vector2(230, 50), new Vector2(0, 0), this, camera);
		for (int i = 0; i < amount; i++) {
			float x = MathUtils.random(0,
					MainGame.WIDTH - TextureManager.ENEMY.getWidth());
			float y = MathUtils.random(MainGame.HEIGHT, MainGame.HEIGHT * 3);
			float speed = MathUtils.random(2, 5);
			addEntities(new Enemy(new Vector2(x, y), new Vector2(0, -speed)));
		}
	}

	public void update() {
		for (Entity e : entities)
			e.update();
		player.update();
		for(Missile m : getMissile()){
			if(m.checkEnd()){
				entities.removeValue(m, false);
			}
		}
		checkCollision();
	}

	public void render(SpriteBatch sb) {
		for (Entity e : entities)
			e.render(sb);
		player.render(sb);
	}

	public void addEntities(Entity entity) {
		entities.add(entity);
	}
	private void checkCollision(){
		for(Enemy e: getEnemies()){
			for(Missile m: getMissile()){
				if(e.getBounds().overlaps(m.getBounds())){
					entities.removeValue(e, false);
					entities.removeValue(m, true);
					if(gameOver()){
						ScreenManager.setScreen(new GameOverScreen(true));
					}
				}
			}
			if(e.getBounds().overlaps(player.getBounds())){
				//end game lost
				ScreenManager.setScreen(new GameOverScreen(false));
			}
		}
	}

	private Array<Enemy> getEnemies() {
		Array<Enemy> ret = new Array<Enemy>();
		for (Entity e : entities)
			if (e instanceof Enemy)
				ret.add((Enemy) e);
		return ret;
	}
	private Array<Missile> getMissile() {
		Array<Missile> ret = new Array<Missile>();
		for (Entity e : entities)
			if (e instanceof Missile)
				ret.add((Missile) e);
		return ret;
	}

	public boolean gameOver() {
		return getEnemies().size <= 0;
	}
}
