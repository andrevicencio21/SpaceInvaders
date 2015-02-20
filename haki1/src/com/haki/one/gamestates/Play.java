package com.haki.one.gamestates;

import static com.haki.one.handlers.B2DVariables.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.haki.one.Game;
import com.haki.one.handlers.B2DVariables;
import com.haki.one.handlers.GameStateManager;
import com.haki.one.handlers.MyContactListener;
import com.haki.one.handlers.MyInput;

public class Play extends GameState {
	private World world;
	private Box2DDebugRenderer debugRender;
	private OrthographicCamera b2dCamera;
	private Body playerBody;

	public Play(GameStateManager gsm) {

		super(gsm);
		world = new World(new Vector2(0, -9.81f), true);
		world.setContactListener(new MyContactListener());
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
		fdef.filter.maskBits = B2DVariables.BIT_PLAYER;
		Fixture fixture = body.createFixture(fdef);
		fixture.setUserData("ground");

		// create PLAYER
		bdef.position.set(160 / PPM, 200 / PPM);
		bdef.type = BodyType.DynamicBody;
		playerBody = world.createBody(bdef);
		shape.setAsBox(5 / PPM, 5 / PPM);
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVariables.BIT_PLAYER;
		fdef.filter.maskBits = B2DVariables.BIT_GROUND;
		playerBody.createFixture(fdef).setUserData("player");
		//create foot sensor
		shape.setAsBox(2 /PPM, 2 /PPM, new Vector2(0, -5/PPM), 0);
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVariables.BIT_PLAYER;
		fdef.filter.maskBits = B2DVariables.BIT_GROUND;
		fdef.isSensor = true;
		playerBody.createFixture(fdef).setUserData("playerFoot");

		b2dCamera = new OrthographicCamera();
		b2dCamera.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT / PPM);

	}

	@Override
	public void handleInput() {

	}

	@Override
	public void update(float dt) {
		handleInput();
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
