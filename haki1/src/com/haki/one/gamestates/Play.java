package com.haki.one.gamestates;

import static com.haki.one.handlers.B2DVariables.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.haki.one.Game;
import com.haki.one.handlers.B2DVariables;
import com.haki.one.handlers.GameStateManager;

public class Play extends GameState {
	private World world;
	private Box2DDebugRenderer debugRender;
	private OrthographicCamera b2dCamera;

	public Play(GameStateManager gsm) {

		super(gsm);
		world = new World(new Vector2(0, -9.81f), true);
		debugRender = new Box2DDebugRenderer();

		// Create Platform
		BodyDef bdef = new BodyDef();
		bdef.position.set(160 / PPM, 10 / PPM);
		bdef.type = BodyType.StaticBody;
		// Static Body = unaffected by forces
		// dynamic body = gets affected by forces
		// kinematic body = don't get affected by world forces but can change
		// velocity

		Body body = world.createBody(bdef);

		PolygonShape shape = new PolygonShape();// creating shape
		shape.setAsBox(200 / PPM, 5 / PPM);
		FixtureDef fdef = new FixtureDef(); // Fixture Def
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVariables.BIT_GROUND;
		fdef.filter.maskBits = B2DVariables.BIT_BOX | B2DVariables.BIT_BALL;
		body.createFixture(fdef);

		// create falling box
		bdef.position.set(160 / PPM, 200 / PPM);
		bdef.type = BodyType.DynamicBody;
		body = world.createBody(bdef);
		shape.setAsBox(5 / PPM, 5 / PPM);
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVariables.BIT_BOX;
		fdef.filter.maskBits = B2DVariables.BIT_GROUND | B2DVariables.BIT_BALL;
		body.createFixture(fdef);

		// create ball
		bdef.position.set(153 / PPM, 220 / PPM);
		body = world.createBody(bdef);
		CircleShape cshape = new CircleShape();
		cshape.setRadius(5 / PPM);
		fdef.shape = cshape;
		fdef.filter.categoryBits = B2DVariables.BIT_BALL;
		fdef.filter.maskBits = B2DVariables.BIT_GROUND | B2DVariables.BIT_BOX
				| B2DVariables.BIT_BALL;
		body.createFixture(fdef);

		// BALL FUN
		/*
		 * for (int i = 0; i < 300; i++) {
		 * bdef.position.set(MathUtils.random(5/PPM, 200/PPM), 50/PPM); body =
		 * world.createBody(bdef); cshape.setRadius(2 / PPM); fdef.shape =
		 * cshape; fdef.restitution = MathUtils.random(0.90f, 0.98f);
		 * fdef.density = MathUtils.random(0.90f, 0.98f);
		 * fdef.filter.categoryBits = B2DVariables.BIT_BALL;
		 * fdef.filter.maskBits = B2DVariables.BIT_GROUND | B2DVariables.BIT_BOX
		 * | B2DVariables.BIT_BALL; body.createFixture(fdef); }
		 */

		// setup b2d cam
		b2dCamera = new OrthographicCamera();
		b2dCamera.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT / PPM);

	}

	@Override
	public void handleInput() {

	}

	@Override
	public void update(float dt) {
		world.step(dt, 6, 2);

	}

	@Override
	public void render() {
		// clear screen
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// draw box2d world
		debugRender.render(world, b2dCamera.combined);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
