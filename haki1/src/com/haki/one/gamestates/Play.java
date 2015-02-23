package com.haki.one.gamestates;

import static com.haki.one.handlers.B2DVariables.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.haki.one.entities.Crystal;
import com.haki.one.entities.HUD;
import com.haki.one.entities.Player;
import com.haki.one.handlers.B2DVariables;
import com.haki.one.handlers.GameStateManager;
import com.haki.one.handlers.MyContactListener;
import com.haki.one.handlers.MyInput;
import com.haki.one.main.Game;

public class Play extends GameState {
	private World world;
	private boolean debug = true;
	private Box2DDebugRenderer debugRender;
	private OrthographicCamera b2dCamera;
	private MyContactListener contactListener;
	private TiledMap tiledMap;
	private OrthogonalTiledMapRenderer tileMapRenderer;
	private float tileSize;
	private Player player;
	private HUD hud;
	private Array<Crystal> crystals;

	public Play(GameStateManager gsm) {

		super(gsm);
		// Set up Box2d Stuff
		world = new World(new Vector2(0, -9.81f), true);
		world.setContactListener(contactListener = new MyContactListener());
		debugRender = new Box2DDebugRenderer();

		// createPlayer
		createPlayer();

		// createTiles
		createTiles();

		// create crystals
		createCrystals();

		b2dCamera = new OrthographicCamera();
		b2dCamera.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT / PPM);

		// set up hud
		hud = new HUD(player);
	}

	@Override
	public void handleInput() {
		if (MyInput.isPressed(MyInput.BUTTON1)) {

			if (contactListener.isPlayerOnGround()) {
				player.getBody().applyForceToCenter(0, 300, true);
			}
		}
		// Switch Block Color
		if (MyInput.isPressed(MyInput.BUTTON2)) {
			switchBlocks();
		}
		if (MyInput.isPressed()) {
			if (MyInput.x < Gdx.graphics.getWidth() / 2) {
				switchBlocks();
			} else
				playerJump();
		}

	}

	private void playerJump() {
		if (contactListener.isPlayerOnGround()) {
			player.getBody().setLinearVelocity(
					player.getBody().getLinearVelocity().x, 0);
			player.getBody().applyForceToCenter(0, 300, true);
		
		}
	}

	@Override
	public void update(float dt) {
		handleInput();
		world.step(Game.STEP, 1, 1);
		// remove crystals
		Array<Body> bodiesToRemove = contactListener.getBodiesToRemove();
		for (int i = 0; i < bodiesToRemove.size; i++) {
			Body b = bodiesToRemove.get(i);
			crystals.removeValue((Crystal) b.getUserData(), true);
			world.destroyBody(b);
			player.collectCrystal();
		}
		bodiesToRemove.clear();
		player.update(dt);
		for (int i = 0; i < crystals.size; i++) {
			crystals.get(i).update(dt);
		}

	}

	@Override
	public void render() {
		// clear screen
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// set camera to follow player
		camera.position.set(player.getPosition().x * PPM + Game.V_WIDTH / 4,
				Game.V_HEIGHT / 2, 0);
		camera.update();

		// draw tile map
		tileMapRenderer.setView(camera);
		tileMapRenderer.render();

		// draw player
		batch.setProjectionMatrix(camera.combined);
		player.render(batch);

		// draw crystals
		for (int i = 0; i < crystals.size; i++) {
			crystals.get(i).render(batch);
		}
		// draw hud
		batch.setProjectionMatrix(hudCamera.combined);
		hud.render(batch);

		// draw box2d world
		if (debug)
			b2dCamera.position.set(player.getPosition().x + Game.V_WIDTH / 4
					/ PPM, Game.V_HEIGHT / 2 / PPM, 0);
		b2dCamera.update();
		debugRender.render(world, b2dCamera.combined);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void createPlayer() {
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		PolygonShape shape = new PolygonShape();
		bdef.position.set(160 / PPM, 200 / PPM);
		bdef.type = BodyType.DynamicBody;
		bdef.linearVelocity.set(1, 0);
		Body body = world.createBody(bdef);
		shape.setAsBox(14 / PPM, 14 / PPM);
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVariables.BIT_PLAYER;
		fdef.filter.maskBits = B2DVariables.BIT_RED | B2DVariables.BIT_CRYSTAL;
		body.createFixture(fdef).setUserData("player");

		// create foot sensor
		shape.setAsBox(14 / PPM, 4 / PPM, new Vector2(0, -14 / PPM), 0);
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVariables.BIT_PLAYER;
		fdef.isSensor = true;
		body.createFixture(fdef).setUserData("playerFoot");

		// createPlayer
		player = new Player(body);
		body.setUserData(player);
		shape.dispose();
	}

	public void createTiles() {
		tiledMap = new TmxMapLoader().load("maps/testmap.tmx");
		tileMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

		tileSize = tiledMap.getProperties().get("tilewidth", Integer.class);
		TiledMapTileLayer layer;
		layer = (TiledMapTileLayer) tiledMap.getLayers().get("red");

		createLayer(layer, B2DVariables.BIT_RED);
		layer = (TiledMapTileLayer) tiledMap.getLayers().get("green");
		createLayer(layer, B2DVariables.BIT_GREEN);
		layer = (TiledMapTileLayer) tiledMap.getLayers().get("blue");
		createLayer(layer, B2DVariables.BIT_BLUE);

	}

	private void createCrystals() {
		crystals = new Array<Crystal>();
		MapLayer layer = tiledMap.getLayers().get("crystals");
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		for (MapObject mo : layer.getObjects()) {
			if (mo instanceof EllipseMapObject) {
				Ellipse e = ((EllipseMapObject) mo).getEllipse();
				float x = e.x / PPM;
				float y = e.y / PPM;
				bdef.type = BodyType.StaticBody;
				bdef.position.set(x, y);
				CircleShape circle = new CircleShape();
				circle.setRadius(8 / PPM);
				fdef.shape = circle;
				fdef.isSensor = true;
				fdef.filter.categoryBits = B2DVariables.BIT_CRYSTAL;
				fdef.filter.maskBits = B2DVariables.BIT_PLAYER;
				Body body = world.createBody(bdef);
				body.createFixture(fdef).setUserData("crystal");
				Crystal c = new Crystal(body);
				crystals.add(c);
				body.setUserData(c);
				circle.dispose();
			}
		}
	}

	private void createLayer(TiledMapTileLayer layer, short bits) {
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		for (int row = 0; row < layer.getHeight(); row++) {
			for (int col = 0; col < layer.getWidth(); col++) {
				// get cell
				Cell cell = layer.getCell(col, row);

				// check cell
				if (cell == null)
					continue;
				if (cell.getTile() == null)
					continue;
				// create a body and fixture
				bdef.type = BodyType.StaticBody;
				bdef.position.set((col + 0.5f) * tileSize / PPM, (row + 0.5f)
						* tileSize / PPM);
				ChainShape cs = new ChainShape();
				Vector2[] v = new Vector2[3];
				v[0] = new Vector2(-tileSize / 2 / PPM, -tileSize / 2 / PPM);
				v[1] = new Vector2(-tileSize / 2 / PPM, tileSize / 2 / PPM);
				v[2] = new Vector2(tileSize / 2 / PPM, tileSize / 2 / PPM);

				cs.createChain(v);
				fdef.friction = 0;
				fdef.shape = cs;
				fdef.filter.categoryBits = bits;
				fdef.filter.maskBits = B2DVariables.BIT_PLAYER;
				fdef.isSensor = false;
				world.createBody(bdef).createFixture(fdef);
				cs.dispose();

			}
		}
	}

	private void switchBlocks() {
		Filter filter = player.getBody().getFixtureList().first()
				.getFilterData();
		short bits = filter.maskBits;

		// switch to next Color
		// RED - GREEN - BLUE - RED
		if ((bits & B2DVariables.BIT_RED) != 0) {
			bits &= ~B2DVariables.BIT_RED;
			bits |= B2DVariables.BIT_GREEN;
		} else if ((bits & B2DVariables.BIT_GREEN) != 0) {
			bits &= ~B2DVariables.BIT_GREEN;
			bits |= B2DVariables.BIT_BLUE;
		} else if ((bits & B2DVariables.BIT_BLUE) != 0) {
			bits &= ~B2DVariables.BIT_BLUE;
			bits |= B2DVariables.BIT_RED;
		}
		// set new mask bits
		filter.maskBits = bits;
		player.getBody().getFixtureList().first().setFilterData(filter);
		// set new mask bits for foot
		filter = player.getBody().getFixtureList().get(1).getFilterData();
		bits &= ~B2DVariables.BIT_CRYSTAL;
		player.getBody().getFixtureList().get(1).setFilterData(filter);

	}
}
