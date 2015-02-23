package com.haki.one.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.haki.one.handlers.Animation;
import com.haki.one.handlers.B2DVariables;

public class B2DSprite {
	protected Body body;
	protected Animation animation;
	protected float width;
	protected float height;

	public B2DSprite(Body body) {
		this.body = body;
		animation = new Animation();
	}

	public void setAnimation(TextureRegion[] tr, float delay) {
		animation.setFrames(tr, delay);
		width = tr[0].getRegionWidth();
		height = tr[0].getRegionWidth();
	}

	public void update(float dt) {
		animation.update(dt);
	}

	public void render(SpriteBatch batch) {
		batch.begin();
		batch.draw(animation.getFrame(), body.getPosition().x
				* B2DVariables.PPM - width / 2, body.getPosition().y
				* B2DVariables.PPM - height / 2);

		batch.end();
	}

	public Body getBody() {
		return body;
	}
	public Vector2 getPosition(){
		return body.getPosition();
	}
	public float getWidth(){
		return width;
	}
	public float getHeight(){
		return height;
	}
}
